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

package edu.cmu.cs.dennisc.croquet;

/**
 * @author Dennis Cosgrove
 */
public abstract class CascadeMenu< FB > extends AbstractCascadeFillIn< FB, CascadeMenuContext<FB> > {
	private class InternalBlank extends CascadeBlank< FB > {
		public InternalBlank() {
			super(java.util.UUID.fromString( "2f562397-a298-46da-bf8d-01a4bb86da3a" ) );
		}
		@Override
		protected java.util.List< edu.cmu.cs.dennisc.croquet.AbstractCascadeFillIn > updateChildren( java.util.List< edu.cmu.cs.dennisc.croquet.AbstractCascadeFillIn > rv, edu.cmu.cs.dennisc.croquet.CascadeBlankContext< FB > context ) {
			CascadeMenu.this.updateBlankChildren( rv, context );
			return rv;
		}
	}
	private final InternalBlank blank = new InternalBlank();
	public CascadeMenu( java.util.UUID id ) {
		super( id );
	}
	public CascadeBlank< FB > getBlank() {
		return this.blank;
	}
//	@Override
//	public CascadeBlank<FB>[] getBlanks() {
//		return new CascadeBlank[] { this.blank };
//	}
	protected abstract java.util.List< edu.cmu.cs.dennisc.croquet.AbstractCascadeFillIn > updateBlankChildren( java.util.List< edu.cmu.cs.dennisc.croquet.AbstractCascadeFillIn > rv, edu.cmu.cs.dennisc.croquet.CascadeBlankContext< FB > context );
	//protected abstract void addChildrenToBlank( java.util.List< edu.cmu.cs.dennisc.croquet.AbstractCascadeFillIn > rv, edu.cmu.cs.dennisc.croquet.CascadeBlankContext< FB > context, CascadeBlank<FB> blank );
	private edu.cmu.cs.dennisc.croquet.AbstractCascadeFillInContext< FB,?,? > getSelectedFillInContext( CascadeMenuContext<FB> context ) {
		edu.cmu.cs.dennisc.croquet.CascadeBlankContext< FB > blankContext = context.getInternalBlankContext();
		return blankContext.getSelectedFillInContext();
	}
	@Override
	public FB getTransientValue( CascadeMenuContext<FB> context ) {
		return this.getSelectedFillInContext( context ).getTransientValue();
	}
	@Override
	public FB createValue( CascadeMenuContext<FB> context ) {
		return this.getSelectedFillInContext( context ).createValue();
	}
	@Override
	protected javax.swing.JComponent createMenuItemIconProxy( CascadeMenuContext< FB > context ) {
		return new javax.swing.JLabel( this.getDefaultLocalizedText() );
	}
//	@Override
//	public javax.swing.Icon getMenuItemIcon( CascadeMenuContext<FB> context ) {
//		return null;
//	}
//	@Override
//	public String getMenuItemText( CascadeMenuContext<FB> context ) {
//		return this.getDefaultLocalizedText();
//	}
}
