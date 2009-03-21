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
class FunctionInvocationTemplate extends MethodInvocationTemplate< edu.cmu.cs.dennisc.alice.ast.MethodInvocation > {
	//	private static FunctionBorder singletonBorder = new FunctionBorder();
	//
	public FunctionInvocationTemplate( edu.cmu.cs.dennisc.alice.ast.AbstractMethod method ) {
		super( method );
		setBackground( getIDE().getColorForASTClass( edu.cmu.cs.dennisc.alice.ast.MethodInvocation.class ) );
		setForeground( java.awt.Color.GRAY );
		//		setBorder( FunctionInvocationTemplate.singletonBorder );
	}
	@Override
	protected java.awt.Component createSubjectComponent( Factory factory ) {
		return new org.alice.ide.ast.ExpressionPane( factory, this.getMethodInvocation() ) {
			@Override
			protected boolean isKnurlDesired() {
				return true;
			}
		};
	}
}
