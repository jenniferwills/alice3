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
package org.alice.ide.memberseditor;

/**
 * @author Dennis Cosgrove
 */
class TypeFunctionsPane extends AbstractTypeMethodsPane {
	public TypeFunctionsPane( edu.cmu.cs.dennisc.alice.ast.AbstractType type ) {
		super( type );
	}
	@Override
	protected edu.cmu.cs.dennisc.property.ListProperty< ? >[] getListPropertiesToListenTo( edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInAlice type ) {
		return new edu.cmu.cs.dennisc.property.ListProperty[] { type.methods, type.constructors };
	}
	@Override
	protected zoot.ZButton createCreateAndAddMemberButton( edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInAlice type ) {
		return new zoot.ZButton( new org.alice.ide.operations.ast.CreateAndAddFunctionOperation( type ) );
	}
	@Override
	protected zoot.ZButton createEditConstructorButton( edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInAlice type ) {
		return new zoot.ZButton( new org.alice.ide.operations.ast.FocusCodeOperation( type.getDeclaredConstructor() ) );
	}
	@Override
	protected javax.swing.JComponent createFunctionTemplate( edu.cmu.cs.dennisc.alice.ast.AbstractMethod method ) {
		MethodInvocationTemplate rv = new FunctionInvocationTemplate( method );
		rv.setExpression( getIDE().createInstanceExpression() );
		return rv;
		//		return new zoot.ZLabel( method.toString() );
	}
	@Override
	protected javax.swing.JComponent createProcedureTemplate( edu.cmu.cs.dennisc.alice.ast.AbstractMethod method ) {
		return null;
	}
}
