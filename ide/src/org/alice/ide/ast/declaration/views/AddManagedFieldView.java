/*
 * Copyright (c) 2006-2010, Carnegie Mellon University. All rights reserved.
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
package org.alice.ide.ast.declaration.views;

/**
 * @author Dennis Cosgrove
 */
public class AddManagedFieldView extends AddFieldView {
	public AddManagedFieldView( org.alice.ide.ast.declaration.AddManagedFieldComposite composite ) {
		super( composite );
	}
	@Override
	protected org.lgna.croquet.components.BorderPanel createMainComponent() {
		org.lgna.croquet.components.BorderPanel rv = super.createMainComponent();
		org.alice.ide.ast.declaration.AddManagedFieldComposite composite = (org.alice.ide.ast.declaration.AddManagedFieldComposite)this.getComposite();
		final java.util.List<org.lgna.croquet.CustomItemState<org.lgna.project.ast.Expression>> states = composite.getInitialPropertyValueExpressionStates();
		if( states.size() > 0 ) {
			final org.alice.ide.x.AstI18nFactory factory = org.alice.ide.x.PreviewAstI18nFactory.getInstance();
			org.lgna.croquet.components.RowSpringPanel propertiesPanel = new org.lgna.croquet.components.RowSpringPanel() {
				@Override
				protected void appendRows( java.util.List<org.lgna.croquet.components.SpringRow> rows ) {
					for( org.lgna.croquet.CustomItemState<org.lgna.project.ast.Expression> state : states ) { 
						rows.add( new org.lgna.croquet.components.LabeledSpringRow( state.getSidekickLabel(), new org.alice.ide.croquet.components.ExpressionDropDown( state, factory ) ) );
					}
				}
			};
			
			
			org.lgna.croquet.components.ToolPalette toolPalette = composite.getInitialPropertyValuesExpandedState().createToolPalette( propertiesPanel );
			//java.awt.Color innerColor = new java.awt.Color( 191, 191, 255 );
			//java.awt.Color outerColor = edu.cmu.cs.dennisc.java.awt.ColorUtilities.shiftHSB( innerColor, 0.0, 0.0, 0.1 );
			//propertiesPanel.setBackgroundColor( innerColor );
			//toolPalette.setBackgroundColor( outerColor );

			rv.addCenterComponent( new org.lgna.croquet.components.BorderPanel.Builder().center( toolPalette ).pageStart( org.lgna.croquet.components.BoxUtilities.createVerticalSliver( 24 ) ).pageEnd( org.lgna.croquet.components.BoxUtilities.createVerticalSliver( 16 ) ).build() );
		}
		return rv;
	}
}
