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

package org.alice.stageide.gallerybrowser;

/**
 * @author Dennis Cosgrove
 */
public class GalleryBrowser extends org.lgna.croquet.components.BorderPanel {
//	private static class Initializer implements org.lgna.croquet.components.PathControl.Initializer< org.alice.ide.croquet.models.gallerybrowser.GalleryNode > {
//		public org.lgna.croquet.ActionOperation configure( org.lgna.croquet.ActionOperation rv, org.alice.ide.croquet.models.gallerybrowser.GalleryNode treeNode ) {
//			rv.setName( treeNode.getText() );
//			rv.setSmallIcon( treeNode.getSmallIcon() );
//			return rv;
//		}
//		public org.lgna.croquet.Operation< ? > getOperationForLeaf( org.alice.ide.croquet.models.gallerybrowser.GalleryNode treeNode ) {
//			org.alice.ide.croquet.models.gallerybrowser.FieldGalleryNode fieldGalleryNode = (org.alice.ide.croquet.models.gallerybrowser.FieldGalleryNode)treeNode;
//			org.lgna.project.ast.AbstractField field = fieldGalleryNode.getDeclaration();
//			org.lgna.project.ast.AbstractType< ?, ?, ? > valueType = field.getValueType();
//			org.lgna.project.ast.AbstractConstructor constructor = org.alice.ide.croquet.models.gallerybrowser.RootGalleryNode.SINGLETON.getConstructorForArgumentType( valueType );
//			return org.alice.ide.croquet.models.declaration.SpecifiedManagedFieldDeclarationOperation.getInstance( constructor, field );
//		}
//	}
	public GalleryBrowser() {
		this.addComponent( new org.lgna.croquet.components.PathControl( org.alice.ide.croquet.models.gallerybrowser.GalleryResourceTreeSelectionState.getInstance() ), Constraint.NORTH );
		this.addComponent( new GalleryDirectoryView(), Constraint.CENTER );

		org.alice.stageide.croquet.models.gallerybrowser.CreateFieldFromPersonResourceOperation createTypeFromPersonResourceOperation = org.alice.stageide.croquet.models.gallerybrowser.CreateFieldFromPersonResourceOperation.getInstance();
		org.lgna.croquet.components.Button createPersonButton = createTypeFromPersonResourceOperation.createButton();
		createPersonButton.setHorizontalTextPosition( org.lgna.croquet.components.HorizontalTextPosition.CENTER );
		createPersonButton.setVerticalTextPosition( org.lgna.croquet.components.VerticalTextPosition.BOTTOM );

		createTypeFromPersonResourceOperation.setSmallIcon(edu.cmu.cs.dennisc.javax.swing.IconUtilities.createImageIcon(GalleryBrowser.class.getResource("images/create_person.png")));
		
		this.addComponent( createPersonButton, Constraint.LINE_START );
		
		org.lgna.croquet.components.BorderPanel lineEndPanel = new org.lgna.croquet.components.BorderPanel();
		lineEndPanel.addComponent( org.alice.stageide.croquet.models.gallerybrowser.CreateMyInstanceOperation.getInstance().createButton(), Constraint.PAGE_START );
		this.addComponent( lineEndPanel, Constraint.LINE_END );
	}
}
