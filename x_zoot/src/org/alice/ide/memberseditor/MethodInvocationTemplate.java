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
abstract class DragSourcePane extends zoot.ZDragComponent {
	public DragSourcePane() {
		this.setDragAndDropOperation( org.alice.ide.IDE.getSingleton().getDragAndDropOperation() );
	}
	protected org.alice.ide.IDE getIDE() {
		return org.alice.ide.IDE.getSingleton();
	}
}


abstract class Template extends DragSourcePane {
	public Template() {
		this.setAlignmentX( 0.0f );
	}
	@Override
	public java.awt.Dimension getMaximumSize() {
		return getPreferredSize();
	}
	@Override
	public java.awt.Component getSubject() {
		return this.getComponent( 0 );
	}
	protected abstract edu.cmu.cs.dennisc.alice.ast.Node getNode();
	protected abstract java.awt.Component createSubjectComponent( Factory factory );
	public void setExpression( edu.cmu.cs.dennisc.alice.ast.Expression expression ) {
		//java.awt.Component nodeComponent = getIDE().getTemplatesFactory().createComponent( this.getNode() );
		java.awt.Component subject = createSubjectComponent( getIDE().getTemplatesFactory() );
		this.removeAll();
		//this.setSubject( subject );
		this.add( subject );
	}
}

/**
 * @author Dennis Cosgrove
 */
abstract class MethodInvocationTemplate<E> extends Template {
	private edu.cmu.cs.dennisc.alice.ast.MethodInvocation methodInvocation;

	public MethodInvocationTemplate( edu.cmu.cs.dennisc.alice.ast.AbstractMethod method ) {
		this.methodInvocation = org.alice.ide.ast.NodeUtilities.createIncompleteMethodInvocation( null, method );
		this.setOpaque( false );
		//this.setLayout( new edu.cmu.cs.dennisc.awt.ExpandAllToBoundsLayoutManager() );
		this.setLayout( new java.awt.GridLayout( 1, 1 ) );
	}

	@Override
	public edu.cmu.cs.dennisc.alice.ast.Node getNode() {
		return this.getMethodInvocation();
	}
	protected edu.cmu.cs.dennisc.alice.ast.MethodInvocation getMethodInvocation() {
		return this.methodInvocation;
	}
	
	@Override
	public void setExpression( edu.cmu.cs.dennisc.alice.ast.Expression expression ) {
		this.methodInvocation.expression.setValue( expression );
		super.setExpression( expression );
	}
}
