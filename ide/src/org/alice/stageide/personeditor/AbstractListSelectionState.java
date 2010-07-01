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
package org.alice.stageide.personeditor;

/**
 * @author Dennis Cosgrove
 */
abstract class AbstractListSelectionState<E> extends edu.cmu.cs.dennisc.croquet.ListSelectionState< E > {
	public AbstractListSelectionState( java.util.UUID individualId, E... items ) {
		super( edu.cmu.cs.dennisc.croquet.Application.INHERIT_GROUP, individualId );
		this.setListData( -1, items );
		this.addValueObserver( new edu.cmu.cs.dennisc.croquet.ListSelectionState.ValueObserver< E >() {
			public void changed(E nextValue) {
				E item = AbstractListSelectionState.this.getSelectedItem();
				if( item != null ) {
					AbstractListSelectionState.this.handleSelectionChange( item );
				}
			}
		} );
	}
	@Override
	protected void encodeValue(edu.cmu.cs.dennisc.codec.BinaryEncoder binaryEncoder, E value) {
		throw new RuntimeException( "todo" );
	}
	@Override
	protected E decodeValue( edu.cmu.cs.dennisc.codec.BinaryDecoder binaryDecoder ) {
		throw new RuntimeException( "todo" );
	}

	public void setToRandomValue() {
		assert this.getItemCount() > 0;
		this.setSelectedItem( this.getItemAt( 0 ) );
		edu.cmu.cs.dennisc.print.PrintUtilities.println( "todo: setToRandomValue" );
	}
	protected abstract void handleSelectionChange( E value );
	protected int getVisibleRowCount() {
		return 1;
	}
	@Override
	public edu.cmu.cs.dennisc.croquet.List<E> createList() {
		edu.cmu.cs.dennisc.croquet.List<E> rv = super.createList();
		rv.setLayoutOrientation( edu.cmu.cs.dennisc.croquet.List.LayoutOrientation.HORIZONTAL_WRAP );
		rv.setVisibleRowCount( this.getVisibleRowCount() );
		rv.setBackgroundColor( edu.cmu.cs.dennisc.croquet.FolderTabbedPane.DEFAULT_BACKGROUND_COLOR );
		//rv.getAwtComponent().setOpaque( false );
		return rv;
	}	
}
