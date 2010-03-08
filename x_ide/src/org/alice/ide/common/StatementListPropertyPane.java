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
package org.alice.ide.common;

import org.alice.ide.codeeditor.EmptyStatementListAfforance;

/**
 * @author Dennis Cosgrove
 */
public class StatementListPropertyPane extends AbstractListPropertyPane< edu.cmu.cs.dennisc.alice.ast.StatementListProperty > {
	public static final int INTRASTICIAL_PAD = 4;
	public StatementListPropertyPane( Factory factory, final edu.cmu.cs.dennisc.alice.ast.StatementListProperty property ) {
		super( factory, javax.swing.BoxLayout.PAGE_AXIS, property );
		
		int pad;
		if( property.getOwner() instanceof edu.cmu.cs.dennisc.alice.ast.DoTogether ) {
			pad = 0;
		} else {
			pad = INTRASTICIAL_PAD;
		}
		this.setLayout( new edu.cmu.cs.dennisc.swing.PaddedBoxLayout( this, javax.swing.BoxLayout.PAGE_AXIS, pad ) );
		
//		this.addMouseListener( new java.awt.event.MouseListener() {
//			public void mouseClicked( final java.awt.event.MouseEvent e ) {
//				final alice.ide.IDE ide = alice.ide.IDE.getSingleton();
//				if( ide != null ) {
//					//final StatementListPropertyPane statementListPropertyPane = getStatementListPropertyPaneUnder( e, createStatementListPropertyPaneInfos( null ) );
//					final StatementListPropertyPane statementListPropertyPane = StatementListPropertyPane.this;
//					if( statementListPropertyPane != null ) {
//						ide.promptUserForStatement( e, new edu.cmu.cs.dennisc.task.TaskObserver< edu.cmu.cs.dennisc.alice.ast.Statement >() {
//							public void handleCompletion( edu.cmu.cs.dennisc.alice.ast.Statement statement ) {
//								java.awt.Point p = e.getPoint();
//								//p = javax.swing.SwingUtilities.convertPoint( e.getComponent(), p, statementListPropertyPane );
//								statementListPropertyPane.getProperty().add( statementListPropertyPane.calculateIndex( p ), statement );
//								ide.markChanged( "statement" );
//							}
//							public void handleCancelation() {
//							}
//						} );
//					}
//				}
//			}
//			public void mouseEntered( java.awt.event.MouseEvent e ) {
//			}
//			public void mouseExited( java.awt.event.MouseEvent e ) {
//			}
//			public void mousePressed( java.awt.event.MouseEvent e ) {
//			}
//			public void mouseReleased( java.awt.event.MouseEvent e ) {
//			}
//		} );
	}
	
	public java.awt.Rectangle getDropBounds() {
		java.awt.Rectangle rv = javax.swing.SwingUtilities.getLocalBounds( this );
		final int DELTA = this.getFont().getSize() + 4;
		rv.y -= DELTA;
		rv.height += DELTA;
		return rv;
	}

	
	@Override
	protected java.awt.Component createComponent( Object instance ) {
		edu.cmu.cs.dennisc.alice.ast.Statement statement = (edu.cmu.cs.dennisc.alice.ast.Statement)instance;
		return this.getFactory().createStatementPane( statement, getProperty() );
	}
	
	@Override
	protected void refresh() {
		super.refresh();
		int bottom;
		if( this.getComponentCount() == 0 ) {
			this.add( new EmptyStatementListAfforance() );
			bottom = 0;
		} else {
			bottom = 4;
		}
		this.setBorder( javax.swing.BorderFactory.createEmptyBorder( 4, 0, bottom, 16 ) );
		repaint();
	}
	public boolean isFigurativelyEmpty() {
		return this.getComponentCount() == 0 || this.getComponent( 0 ) instanceof EmptyStatementListAfforance;
	}

	private int getCenterYOfComponentAt( int i ) {
		java.awt.Component componentI = this.getComponent( i );
		return componentI.getY() + componentI.getHeight() / 2;
	}
	public int calculateIndex( java.awt.Point p ) {
		if( isFigurativelyEmpty() ) {
			return 0;
		} else {
			for( int i = 0; i < this.getComponentCount(); i++ ) {
				int yCenterI = this.getCenterYOfComponentAt( i );
				if( p.y < yCenterI ) {
					return i;
				}
			}
			return this.getComponentCount();
		}
	}

}
