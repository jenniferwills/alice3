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
package org.alice.ide.operations.ast;

/**
 * @author Dennis Cosgrove
 */
public abstract class AbstractDeclareFieldOperation extends org.alice.ide.operations.AbstractActionOperation {
	protected abstract edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInAlice getOwnerType();
	protected abstract edu.cmu.cs.dennisc.pattern.Tuple2<edu.cmu.cs.dennisc.alice.ast.FieldDeclaredInAlice, Object> createFieldAndInstance( edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInAlice ownerType );
	protected abstract boolean isInstanceValid();
	public AbstractDeclareFieldOperation() {
		super( edu.cmu.cs.dennisc.alice.Project.GROUP_UUID );
	}
	public final void perform( edu.cmu.cs.dennisc.zoot.ActionContext actionContext ) {
		edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInAlice ownerType = this.getOwnerType();
		final edu.cmu.cs.dennisc.pattern.Tuple2<edu.cmu.cs.dennisc.alice.ast.FieldDeclaredInAlice, Object> tuple = this.createFieldAndInstance( ownerType );
		if( tuple != null ) {
			edu.cmu.cs.dennisc.alice.ast.FieldDeclaredInAlice field = tuple.getA();
			if( field != null ) {
				class Edit extends edu.cmu.cs.dennisc.zoot.AbstractEdit {
					private edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInAlice ownerType;
					private edu.cmu.cs.dennisc.alice.ast.FieldDeclaredInAlice field;
					private int index;
					
					public Edit( edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInAlice ownerType, edu.cmu.cs.dennisc.alice.ast.FieldDeclaredInAlice field, int index ) {
						this.ownerType = ownerType;
						this.field = field;
						this.index = index;
					}
					@Override
					public void doOrRedo( boolean isDo ) {
						this.ownerType.fields.add( this.index, this.field );
						if( isInstanceValid() ) {
							getIDE().getSceneEditor().handleFieldCreation(ownerType, tuple.getA(), tuple.getB(), isDo );
						}
					}
					@Override
					public void undo() {
						if( this.ownerType.fields.get( this.index ) == this.field ) {
							this.ownerType.fields.remove( this.index );
						} else {
							throw new javax.swing.undo.CannotUndoException();
						}
					}
					@Override
					protected StringBuffer updatePresentation(StringBuffer rv, java.util.Locale locale) {
						rv.append( "declare:" );
						edu.cmu.cs.dennisc.alice.ast.Node.safeAppendRepr(rv, field, locale);
						return rv;
					}
				}
				int index = ownerType.fields.size();
				actionContext.commitAndInvokeDo( new Edit( ownerType, field, index ) );
			} else {
				actionContext.cancel();
			}
		} else {
			actionContext.cancel();
		}
	}
}
