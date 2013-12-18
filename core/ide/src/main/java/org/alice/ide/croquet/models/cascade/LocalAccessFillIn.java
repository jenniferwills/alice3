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
package org.alice.ide.croquet.models.cascade;

/**
 * @author Dennis Cosgrove
 */
public class LocalAccessFillIn extends ExpressionFillInWithoutBlanks<org.lgna.project.ast.LocalAccess> {
	private static java.util.Map<org.lgna.project.ast.UserLocal, LocalAccessFillIn> map = edu.cmu.cs.dennisc.java.util.Maps.newHashMap();

	public static LocalAccessFillIn getInstance( org.lgna.project.ast.UserLocal value ) {
		synchronized( map ) {
			LocalAccessFillIn rv = map.get( value );
			if( rv != null ) {
				//pass
			} else {
				rv = new LocalAccessFillIn( value );
				map.put( value, rv );
			}
			return rv;
		}
	}

	private final org.lgna.project.ast.LocalAccess transientValue;

	private LocalAccessFillIn( org.lgna.project.ast.UserLocal local ) {
		super( java.util.UUID.fromString( "a1e1b9a6-cd25-4e61-af59-955492c0d885" ) );
		this.transientValue = this.createValue( local );
	}

	private org.lgna.project.ast.LocalAccess createValue( org.lgna.project.ast.UserLocal local ) {
		return new org.lgna.project.ast.LocalAccess( local );
	}

	@Override
	public org.lgna.project.ast.LocalAccess createValue( org.lgna.croquet.imp.cascade.ItemNode<? super org.lgna.project.ast.LocalAccess, Void> node, org.lgna.croquet.history.TransactionHistory transactionHistory ) {
		return this.createValue( this.transientValue.local.getValue() );
	}

	@Override
	public org.lgna.project.ast.LocalAccess getTransientValue( org.lgna.croquet.imp.cascade.ItemNode<? super org.lgna.project.ast.LocalAccess, Void> node ) {
		return this.transientValue;
	}

	@Override
	protected org.alice.ide.croquet.resolvers.NodeStaticGetInstanceKeyedResolver<LocalAccessFillIn> createResolver() {
		return new org.alice.ide.croquet.resolvers.NodeStaticGetInstanceKeyedResolver<LocalAccessFillIn>( this, org.lgna.project.ast.UserLocal.class, this.transientValue.local.getValue() );
	}

	@Override
	protected String getTutorialItemText() {
		return this.transientValue.local.getValue().getName();
	}
}
