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
package org.alice.ide.editorstabbedpane;

/**
* @author Dennis Cosgrove
*/
//todo
class EditMethodOperation extends org.alice.ide.operations.InconsequentialActionOperation {
	private edu.cmu.cs.dennisc.alice.ast.MethodDeclaredInAlice method;

	public EditMethodOperation( edu.cmu.cs.dennisc.alice.ast.MethodDeclaredInAlice method ) {
		this.method = method;
		StringBuffer sb = new StringBuffer();
		sb.append( "Edit " );
		sb.append( this.method.getName() );
		if( this.method == org.alice.ide.IDE.getSingleton().getFocusedCode() ) {
			sb.append( "    (current)" );
		}
		this.putValue( javax.swing.Action.NAME, sb.toString() );
	}
	@Override
	protected void performInternal(edu.cmu.cs.dennisc.zoot.ActionContext actionContext) {
		this.getIDE().setFocusedCode( this.method );
	}
}
