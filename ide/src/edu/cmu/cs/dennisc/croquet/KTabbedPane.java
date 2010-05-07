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

package edu.cmu.cs.dennisc.croquet;

/**
 * @author Dennis Cosgrove
 */
public class KTabbedPane extends KComponent< javax.swing.JTabbedPane > {
	@Override
	protected javax.swing.JTabbedPane createJComponent() {
		return new javax.swing.JTabbedPane();
	}
	public void addTab( String title, KComponent< ? > component ) {
		this.getJComponent().addTab( title, component.getJComponent() );
	}
	public void addTab( String title, javax.swing.Icon icon, KComponent< ? > component ) {
		this.getJComponent().addTab( title, icon, component.getJComponent() );
	}
	public void setTitleAt( int index, String title ) {
		this.getJComponent().setTitleAt( index, title );
	}
	
	public java.awt.Color getContentAreaColor() {
		return java.awt.Color.MAGENTA;
	}
	
	@Deprecated
	public void closeTab( int index, java.awt.event.MouseEvent e ) {
		throw new RuntimeException( "todo" );
	}
	@Deprecated
	public void remove( int index ) {
		throw new RuntimeException( "todo" );
	}
	@Deprecated
	public void removeAll() {
		throw new RuntimeException( "todo" );
	}
	@Deprecated
	public void updateUI() {
		throw new RuntimeException( "todo" );
	}
	@Deprecated
	protected edu.cmu.cs.dennisc.javax.swing.plaf.TabbedPaneUI createTabbedPaneUI() {
		throw new RuntimeException( "todo" );
	}
	@Deprecated
	public boolean isCloseButtonDesiredAt( int index ) {
		throw new RuntimeException( "todo" );
	}
	

	@Deprecated
	public int getSelectedIndex() {
		return this.getJComponent().getSelectedIndex();
	}
	@Deprecated
	public KComponent< ? > getSelectedComponent() {
		return KComponent.lookup( this.getJComponent().getSelectedComponent() );
	}
	@Deprecated
	public void setSelectedComponent( KComponent< ? > selectedComponent ) {
		this.getJComponent().setSelectedComponent( selectedComponent.getJComponent() );
	}
	@Deprecated
	public int indexOfComponent( KComponent< ? > component ) {
		return this.getJComponent().indexOfComponent( component.getJComponent() );
	}
	
	@Deprecated
	public void addChangeListener( javax.swing.event.ChangeListener listener ) {
		throw new RuntimeException( "todo" );
	}
	@Deprecated
	public void setTabCloseOperation( AbstractActionOperation operation ) {
		throw new RuntimeException( "todo" );
	}
}
