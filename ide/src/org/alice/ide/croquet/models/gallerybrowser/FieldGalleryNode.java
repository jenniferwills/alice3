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

package org.alice.ide.croquet.models.gallerybrowser;

/**
 * @author Dennis Cosgrove
 */
public class FieldGalleryNode extends DeclarationGalleryNode< org.lgna.project.ast.AbstractField > {
	private static java.util.Map< org.lgna.project.ast.AbstractField, FieldGalleryNode > map = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();
	public static FieldGalleryNode getInstance( org.lgna.project.ast.AbstractField field ) {
		FieldGalleryNode rv = map.get( field );
		if( rv != null ) {
			//pass
		} else {
			rv = new FieldGalleryNode( field );
			map.put( field, rv );
		}
		return rv;
	}
	private final javax.swing.Icon largeIcon;
	private FieldGalleryNode( org.lgna.project.ast.AbstractField field ) {
		super( java.util.UUID.fromString( "4c21c31c-49a5-46dd-82d6-28f0055c30b4" ), field );
		Class<?> resourceClass = ((org.lgna.project.ast.JavaType)this.getDeclaration().getValueType()).getClassReflectionProxy().getReification();
		java.awt.image.BufferedImage thumbnail = org.lgna.story.resourceutilities.ModelResourceUtilities.getThumbnail(resourceClass, this.getDeclaration().getName());
		if( thumbnail != null ) {
			this.largeIcon = new javax.swing.ImageIcon(thumbnail);
		} else {
			this.largeIcon = null;
		}
	}
	@Override
	public GalleryNode getParent() {
		return getDeclarationNodeInstance( this.getDeclaration().getDeclaringType() );
	}
	@Override
	public int getChildCount() {
		return 0;
	}
	@Override
	public GalleryNode getChild( int index ) {
		return null;
	}
	@Override
	public int getIndexOfChild( GalleryNode child ) {
		return 0;
	}
	
	@Override
	public javax.swing.Icon getSmallIcon() {
		return null;
	}
	@Override
	public javax.swing.Icon getLargeIcon() {
		return this.largeIcon;
	}

	@Override
	public org.lgna.croquet.Model getDropModel( org.lgna.croquet.history.DragStep step, org.lgna.croquet.DropSite dropSite ) {
		org.lgna.project.ast.AbstractField field = this.getDeclaration();
		org.lgna.project.ast.AbstractType< ?, ?, ? > valueType = field.getValueType();
		org.lgna.project.ast.AbstractConstructor constructor = org.alice.ide.croquet.models.gallerybrowser.RootGalleryNode.getInstance().getConstructorForArgumentType( valueType );
		return org.alice.ide.croquet.models.declaration.SpecifiedManagedFieldDeclarationOperation.getInstance( constructor, field );
	}
	@Override
	public org.lgna.croquet.Model getLeftButtonClickModel() {
		return this.getDropModel( null, null );
	}
}
