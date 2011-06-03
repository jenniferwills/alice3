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

package org.lgna.croquet;

/**
 * @author Dennis Cosgrove
 */
public abstract class CascadeItem< F, S extends org.lgna.croquet.history.CascadeItemStep<F,?,S> > extends MenuItemPrepModel {
	public CascadeItem( java.util.UUID id ) {
		//super( Application.CASCADE_GROUP, id );
		super( id );
	}
	@Override
	protected void localize() {
	}
	public boolean isInclusionDesired( S context ) {
		return true;
	}
	public boolean isAutomaticallySelectedWhenSoleOption() {
		return true;
	}
	
	@Override
	public org.lgna.croquet.components.MenuItemContainer createMenuItemAndAddTo( org.lgna.croquet.components.MenuItemContainer rv ) {
		rv.addCascadeMenuItem( new org.lgna.croquet.components.CascadeMenuItem( this ) );
		return rv;
	}
	
//	public abstract CascadeBlank<B>[] getBlanks();
	public abstract F getTransientValue( S context );
	public abstract F createValue( S context );

	private javax.swing.JComponent menuProxy = null;
	private javax.swing.Icon icon = null;
	protected abstract javax.swing.JComponent createMenuItemIconProxy( S context );
//	protected javax.swing.JComponent createMenuProxy() {
//		return new javax.swing.JLabel( "todo: override getMenuProxy" );
//	}
	protected javax.swing.JComponent getMenuProxy( S context ) {
		//System.err.println( "todo: cache getMenuProxy()" );
		//todo
		if( this.menuProxy != null ) {
			//pass
		} else {
			this.menuProxy = this.createMenuItemIconProxy( context );
		}
		return this.menuProxy;
	}
	public javax.swing.Icon getMenuItemIcon( S context ) {
		if( this.icon != null ) {
			//pass
		} else {
			javax.swing.JComponent component = this.getMenuProxy( context );
			if( component != null ) {
				edu.cmu.cs.dennisc.javax.swing.SwingUtilities.invalidateTree( component );
				edu.cmu.cs.dennisc.javax.swing.SwingUtilities.doLayoutTree( component );
//				edu.cmu.cs.dennisc.javax.swing.SwingUtilities.validateTree( component );
//				edu.cmu.cs.dennisc.javax.swing.SwingUtilities.doLayoutTree( component );
//				edu.cmu.cs.dennisc.javax.swing.SwingUtilities.revalidateTree( component );
//				edu.cmu.cs.dennisc.javax.swing.SwingUtilities.doLayoutTree( component );
				java.awt.Dimension size = component.getPreferredSize();
				if( size.width > 0 && size.height > 0 ) {
					this.icon = edu.cmu.cs.dennisc.javax.swing.SwingUtilities.createIcon( component );
				} else {
					this.icon = null;
				}
			} else {
				this.icon = null;
			}
		}
		return this.icon;
	}
	public String getMenuItemText( S context ) {
		return null;
	}
	
	protected String getTutorialItemText() {
		return this.getDefaultLocalizedText();
	}

	@Override
	protected StringBuilder updateTutorialStepText( StringBuilder rv, org.lgna.croquet.history.Step< ? > step, org.lgna.croquet.edits.Edit< ? > edit, org.lgna.croquet.UserInformation userInformation ) {
		rv.append( "Select <strong>" );
		rv.append( this.getTutorialItemText() );
		rv.append( "</strong>." );
		return rv;
	}
//	public abstract javax.swing.Icon getMenuItemIcon( C context );
//	public abstract String getMenuItemText( C context );
}
