/**
 * Copyright (c) 2006-2012, Carnegie Mellon University. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution.
 *
 * 3. Products derived from the software may not be called "Alice", nor may 
 *    "Alice" appear in their name, without prior written permission of 
 *    Carnegie Mellon University.
 *
 * 4. All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement: "This product includes software 
 *    developed by Carnegie Mellon University"
 *
 * 5. The gallery of art assets and animations provided with this software is 
 *    contributed by Electronic Arts Inc. and may be used for personal, 
 *    non-commercial, and academic use only. Redistributions of any program 
 *    source code that utilizes The Sims 2 Assets must also retain the copyright
 *    notice, list of conditions and the disclaimer contained in 
 *    The Alice 3.0 Art Gallery License.
 * 
 * DISCLAIMER:
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.  
 * ANY AND ALL EXPRESS, STATUTORY OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,  FITNESS FOR A 
 * PARTICULAR PURPOSE, TITLE, AND NON-INFRINGEMENT ARE DISCLAIMED. IN NO EVENT 
 * SHALL THE AUTHORS, COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, PUNITIVE OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND 
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING FROM OR OTHERWISE RELATING TO 
 * THE USE OF OR OTHER DEALINGS WITH THE SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.alice.ide.croquet.models.project.views;

import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;

import org.alice.ide.croquet.models.project.FindComposite;
import org.alice.ide.croquet.models.project.SearchObjectNode;
import org.alice.ide.croquet.models.project.TreeNodesAndManagers.SearchObject;
import org.lgna.croquet.components.BorderPanel;
import org.lgna.croquet.components.GridPanel;
import org.lgna.croquet.components.List;
import org.lgna.croquet.components.ScrollPane;
import org.lgna.croquet.components.TextArea;
import org.lgna.croquet.components.Tree;

import edu.cmu.cs.dennisc.math.GoldenRatio;

/**
 * @author Matt May
 */
public class FindView extends BorderPanel {

	final TextArea searchBox;
	private InputMap inputMap;
	private final Object left;
	private final Object right;

	public FindView( FindComposite composite ) {
		super( composite );
		searchBox = composite.getSearchState().createTextArea();
		inputMap = searchBox.getAwtComponent().getInputMap();
		left = inputMap.get( KeyStroke.getKeyStroke( "LEFT" ) );
		right = inputMap.get( KeyStroke.getKeyStroke( "RIGHT" ) );
		this.addPageStartComponent( searchBox );
		GridPanel panel = GridPanel.createGridPane( 1, 2 );
		panel.setPreferredSize( GoldenRatio.createWiderSizeFromHeight( 250 ) );
		List<SearchObject> searchResultsList = composite.getSearchResults().createList();
		Tree<SearchObjectNode> referencesTreeList = composite.getReferenceResults().createTree();
		referencesTreeList.setRootVisible( false );
		searchResultsList.setBorder( BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );
		referencesTreeList.setBorder( BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );
		panel.addComponent( new ScrollPane( searchResultsList ) );
		searchResultsList.setCellRenderer( new org.alice.ide.croquet.models.project.views.renderers.SearchResultListCellRenderer() );
		//		referencesTreeList.setCellRenderer( new org.alice.ide.croquet.models.project.views.renderers.SearchReferencesTreeCellRenderer() );
		//		resultReferencesList.setCellRenderer( new ListCellRenderer<Expression>() {
		//
		//			public Component getListCellRendererComponent( JList<? extends Expression> list, Expression value, int index, boolean isSelected, boolean cellHasFocus ) {
		//				return PreviewAstI18nFactory.getInstance().createComponent( value ).getAwtComponent();
		//			}
		//
		//		} );
		panel.addComponent( new ScrollPane( referencesTreeList ) );
		this.addCenterComponent( panel );
		searchBox.addKeyListener( composite.getKeyListener() );
		searchResultsList.getAwtComponent().setFocusable( false );
		referencesTreeList.getAwtComponent().setFocusable( false );
	}

	public void enableLeftAndRight() {
		inputMap.put( KeyStroke.getKeyStroke( "LEFT" ), left );
		inputMap.put( KeyStroke.getKeyStroke( "RIGHT" ), right );
	}

	public void disableLeftAndRight() {
		inputMap.put( KeyStroke.getKeyStroke( "LEFT" ), "NONE" );
		inputMap.put( KeyStroke.getKeyStroke( "RIGHT" ), "NONE" );
	}
}
