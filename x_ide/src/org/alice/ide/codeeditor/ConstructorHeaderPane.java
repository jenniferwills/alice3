/*
 * Copyright (c) 2006-2009, Carnegie Mellon University. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 
 * 3. Products derived from the software may not be called "Alice",
 *    nor may "Alice" appear in their name, without prior written
 *    permission of Carnegie Mellon University.
 * 
 * 4. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    "This product includes software developed by Carnegie Mellon University"
 */
package org.alice.ide.codeeditor;

/**
 * @author Dennis Cosgrove
 */
class ConstructorHeaderPane extends AbstractCodeHeaderPane {
	public ConstructorHeaderPane( edu.cmu.cs.dennisc.alice.ast.ConstructorDeclaredInAlice constructorDeclaredInAlice, javax.swing.JComponent parametersPane ) {
		super( constructorDeclaredInAlice );
		if( org.alice.ide.IDE.getSingleton().isJava() ) {
			this.add( new org.alice.ide.common.TypeComponent( constructorDeclaredInAlice.getDeclaringType() ) );
			this.add( edu.cmu.cs.dennisc.croquet.CroquetUtilities.createLabel( "()" ) );
		} else {
			this.add( edu.cmu.cs.dennisc.croquet.CroquetUtilities.createLabel( "declare " ) );
			javax.swing.JLabel label = edu.cmu.cs.dennisc.croquet.CroquetUtilities.createLabelWithScaledFont( "constructor", 1.5f );
			this.add( label );
			this.add( edu.cmu.cs.dennisc.croquet.CroquetUtilities.createLabel( " on class " ) );
			this.add( new org.alice.ide.common.TypeComponent( constructorDeclaredInAlice.getDeclaringType() ) );
			this.add( parametersPane );
		}
	}
}
