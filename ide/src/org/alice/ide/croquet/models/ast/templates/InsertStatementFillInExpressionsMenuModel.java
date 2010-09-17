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

package org.alice.ide.croquet.models.ast.templates;

/**
 * @author Dennis Cosgrove
 */
abstract class InsertStatementFillInExpressionsMenuModel extends org.alice.ide.croquet.models.ast.AbstractFillInExpressionOrExpressionsMenuModel {
	private org.alice.ide.codeeditor.BlockStatementIndexPair blockStatementIndexPair;
	private edu.cmu.cs.dennisc.alice.ast.AbstractType< ?, ?, ? >[] desiredValueTypes;
	public InsertStatementFillInExpressionsMenuModel( java.util.UUID id, org.alice.ide.codeeditor.BlockStatementIndexPair blockStatementIndexPair, edu.cmu.cs.dennisc.alice.ast.AbstractType< ?, ?, ? >... desiredValueTypes ) {
		super( id );
		this.blockStatementIndexPair = blockStatementIndexPair;
		this.desiredValueTypes = desiredValueTypes;
	}
	protected abstract String getTitleAt( int index, java.util.Locale locale );
	@Override
	protected edu.cmu.cs.dennisc.cascade.Blank createCascadeBlank() {
		java.util.Locale locale = java.util.Locale.getDefault();
		if( this.desiredValueTypes.length > 1 ) {
			final edu.cmu.cs.dennisc.cascade.FillIn< ? > fillIn = org.alice.ide.IDE.getSingleton().getCascadeManager().createExpressionsFillIn( this.desiredValueTypes, false );
			final int N = fillIn.getChildren().size();
			for( int i=0; i<N; i++ ) {
				edu.cmu.cs.dennisc.cascade.Blank blank = fillIn.getBlankAt( i );
				blank.setTitle( this.getTitleAt( i, locale ) );
			}
			edu.cmu.cs.dennisc.cascade.Blank rv = new edu.cmu.cs.dennisc.cascade.ForwardingBlank( fillIn );
			return rv;
		} else {
			edu.cmu.cs.dennisc.cascade.Blank rv = new org.alice.ide.cascade.ExpressionBlank( this.desiredValueTypes[ 0 ] );
			rv.setTitle( this.getTitleAt( 0, locale ) );
			return rv;
		}
	}
	@Override
	public final org.alice.ide.codeeditor.BlockStatementIndexPair getBlockStatementIndexPair() {
		return this.blockStatementIndexPair;
	}
	
	@Override
	public edu.cmu.cs.dennisc.alice.ast.Expression getPreviousExpression() {
		return null;
	}
	@Override
	protected edu.cmu.cs.dennisc.croquet.Group getItemGroup() {
		return edu.cmu.cs.dennisc.alice.Project.GROUP;
	}
	protected abstract edu.cmu.cs.dennisc.alice.ast.Statement createStatement( edu.cmu.cs.dennisc.alice.ast.Expression[] value );
	public edu.cmu.cs.dennisc.croquet.Edit< ? extends edu.cmu.cs.dennisc.croquet.ActionOperation > createEdit( Object value, edu.cmu.cs.dennisc.croquet.ActionOperationContext context ) {
		edu.cmu.cs.dennisc.alice.ast.Expression[] expressions;
		if( value instanceof edu.cmu.cs.dennisc.alice.ast.Expression ) {
			expressions = new edu.cmu.cs.dennisc.alice.ast.Expression[] { (edu.cmu.cs.dennisc.alice.ast.Expression)value };
		} else {
			expressions = (edu.cmu.cs.dennisc.alice.ast.Expression[])value;
		}
		return new org.alice.ide.croquet.edits.ast.InsertStatementEdit( blockStatementIndexPair.getBlockStatement(), blockStatementIndexPair.getIndex(), this.createStatement( expressions ), expressions );
	}
	@Override
	public edu.cmu.cs.dennisc.croquet.Edit< ? > createTutorialCompletionEdit( edu.cmu.cs.dennisc.croquet.Edit< ? > originalEdit, edu.cmu.cs.dennisc.croquet.Retargeter retargeter ) {
		org.alice.ide.croquet.edits.ast.InsertStatementEdit insertStatementEdit = (org.alice.ide.croquet.edits.ast.InsertStatementEdit)originalEdit;
		edu.cmu.cs.dennisc.alice.ast.Expression[] expressions = insertStatementEdit.getInitialExpressions();
		edu.cmu.cs.dennisc.alice.ast.Statement replacementStatement = this.createStatement( expressions );
		return insertStatementEdit.createTutorialCompletionEdit( retargeter, replacementStatement );
	}
}
