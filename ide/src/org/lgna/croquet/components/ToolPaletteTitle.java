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
package org.lgna.croquet.components;

/**
 * @author Dennis Cosgrove
 */
/* package-private */class ToolPaletteTitle extends BooleanStateButton<javax.swing.AbstractButton> {
	private static class ToolPaletteTitleButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
		@Override
		public void paint( java.awt.Graphics g, javax.swing.JComponent c ) {
			java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;

			javax.swing.AbstractButton b = (javax.swing.AbstractButton)c;
			javax.swing.ButtonModel buttonModel = b.getModel();

			java.awt.Rectangle r = javax.swing.SwingUtilities.getLocalBounds( c );
			java.awt.Color background = c.getBackground();
			if( buttonModel.isPressed() ) {
				g2.setPaint( background.darker() );
				g2.fillRect( 0, 0, b.getWidth(), b.getHeight() );
			} else {
				double brightnessScale;
				if( buttonModel.isRollover() ) {
					brightnessScale = 1.2;
				} else {
					brightnessScale = 1.1;
				}
				java.awt.Color HIGHLIGHT_COLOR = edu.cmu.cs.dennisc.java.awt.ColorUtilities.scaleHSB( background, 1.0, 1.0, brightnessScale );
				java.awt.Color SHADOW_COLOR = edu.cmu.cs.dennisc.java.awt.ColorUtilities.scaleHSB( background, 1.0, 1.0, 0.8 );
				edu.cmu.cs.dennisc.java.awt.GraphicsUtilities.fillGradientRectangle( g2, r, SHADOW_COLOR, HIGHLIGHT_COLOR, background, 0.4f );
			}
			super.paint( g, c );
		}
	}

	private static class ArrowIcon extends edu.cmu.cs.dennisc.javax.swing.icons.AbstractArrowIcon {
		public ArrowIcon( int size ) {
			super( size );
		}

		public void paintIcon( java.awt.Component c, java.awt.Graphics g, int x, int y ) {
			javax.swing.AbstractButton button = (javax.swing.AbstractButton)c;
			javax.swing.ButtonModel buttonModel = button.getModel();
			Heading heading;
			if( buttonModel.isSelected() || buttonModel.isPressed() ) {
				heading = Heading.SOUTH;
			} else {
				heading = Heading.EAST;
			}
			java.awt.geom.GeneralPath path = this.createPath( x, y, heading );
			java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;
			java.awt.Paint fillPaint;
			java.awt.Paint drawPaint = java.awt.Color.BLACK;
			if( buttonModel.isPressed() ) {
				fillPaint = java.awt.Color.WHITE;
			} else {
				if( buttonModel.isRollover() ) {
					fillPaint = java.awt.Color.YELLOW;
				} else {
					fillPaint = java.awt.Color.DARK_GRAY;
					drawPaint = null;
				}
			}
			Object antialiasingValue = g2.getRenderingHint( java.awt.RenderingHints.KEY_ANTIALIASING );
			g2.setRenderingHint( java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON );

			g2.setPaint( fillPaint );
			g2.fill( path );
			if( drawPaint != null ) {
				g2.setPaint( drawPaint );
				g2.draw( path );
			}
			g2.setRenderingHint( java.awt.RenderingHints.KEY_ANTIALIASING, antialiasingValue );
		}
	}

	private static final ArrowIcon ARROW_ICON = new ArrowIcon( 12 );

	public ToolPaletteTitle( org.lgna.croquet.BooleanState booleanState ) {
		super( booleanState );
	}

	@Override
	protected javax.swing.AbstractButton createAwtComponent() {
		javax.swing.AbstractButton rv = new javax.swing.JToggleButton() {
			@Override
			protected void paintComponent( java.awt.Graphics g ) {
				java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;
				super.paintComponent( g );

				int x = 4;
				int height = this.getHeight();
				int iconHeight = ARROW_ICON.getIconHeight();
				int y = ( height - iconHeight ) / 2;
				ARROW_ICON.paintIcon( this, g2, x, y );
			}

			@Override
			public void updateUI() {
				this.setUI( new ToolPaletteTitleButtonUI() );
			}
		};
		rv.setRolloverEnabled( true );
		rv.setBorder( javax.swing.BorderFactory.createEmptyBorder( 2, 4 + ARROW_ICON.getIconWidth(), 2, 2 ) );
		return rv;
	}
}
