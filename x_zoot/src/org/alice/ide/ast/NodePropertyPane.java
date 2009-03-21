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
package org.alice.ide.ast;

/**
 * @author Dennis Cosgrove
 */
public class NodePropertyPane<E extends edu.cmu.cs.dennisc.alice.ast.NodeProperty< ? >> extends AbstractPropertyPane< E > {
	public NodePropertyPane( Factory factory ) {
		super( factory, javax.swing.BoxLayout.LINE_AXIS );
	}
	public NodePropertyPane( Factory factory, E property ) {
		super( factory, javax.swing.BoxLayout.LINE_AXIS, property );
	}
	@Override
	protected void refresh() {
		this.removeAll();
		this.add( this.getFactory().createComponent( getProperty().getValue() ) );
	}
}
