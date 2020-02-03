/*******************************************************************************
 * Copyright (c) 2006, 2015, Carnegie Mellon University. All rights reserved.
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
 *******************************************************************************/
package edu.cmu.cs.dennisc.javax.swing.plaf;

import edu.cmu.cs.dennisc.java.awt.ColorUtilities;
import edu.cmu.cs.dennisc.java.lang.SystemUtilities;

import javax.swing.Icon;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;

/**
 * @author Dennis Cosgrove
 */
class CloseIcon implements Icon {
  private static final Color BASE_COLOR = new Color(127, 63, 63);
  private static final Color HIGHLIGHT_COLOR = ColorUtilities.shiftHSB(BASE_COLOR, 0, 0, +0.25f);
  private static final Color PRESS_COLOR = ColorUtilities.shiftHSB(BASE_COLOR, 0, 0, -0.125f);

  private boolean isFilled = false;
  private boolean isHighlighted = false;
  private boolean isPressed = false;

  public boolean isFilled() {
    return this.isFilled;
  }

  public void setFilled(boolean isFilled) {
    this.isFilled = isFilled;
  }

  public boolean isHighlighted() {
    return this.isHighlighted;
  }

  public void setHighlighted(boolean isHighlighted) {
    if (this.isHighlighted != isHighlighted) {
      this.isHighlighted = isHighlighted;
    }
  }

  public boolean isPressed() {
    return this.isPressed;
  }

  public void setPressed(boolean isPressed) {
    if (this.isPressed != isPressed) {
      this.isPressed = isPressed;
    }
  }

  @Override
  public int getIconWidth() {
    return 14;
  }

  @Override
  public int getIconHeight() {
    return getIconWidth();
  }

  @Override
  public void paintIcon(Component c, Graphics g, int x0, int y0) {
    Graphics2D g2 = (Graphics2D) g;

    float size = Math.min(getIconWidth(), getIconWidth()) * 0.9f;

    float w = size;
    float h = size * 0.25f;
    float xC = -w * 0.5f;
    float yC = -h * 0.5f;
    RoundRectangle2D.Float rr = new RoundRectangle2D.Float(xC, yC, w, h, h, h);

    Area area0 = new Area(rr);
    Area area1 = new Area(rr);

    AffineTransform m0 = new AffineTransform();
    m0.rotate(Math.PI * 0.25);
    area0.transform(m0);

    AffineTransform m1 = new AffineTransform();
    m1.rotate(Math.PI * 0.75);
    area1.transform(m1);

    area0.add(area1);

    AffineTransform m = new AffineTransform();
    m.translate(x0 + (getIconWidth() / 2), y0 + (getIconWidth() / 2));
    area0.transform(m);

    Paint prevPaint = g2.getPaint();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    if (isHighlighted()) {
      if (isPressed()) {
        g2.setPaint(PRESS_COLOR);
      } else {
        g2.setPaint(HIGHLIGHT_COLOR);
      }
    } else {
      g2.setPaint(Color.WHITE);
    }
    if (this.isFilled) {
      g2.fill(area0);
      g2.setPaint(Color.BLACK);
    } else {
      g2.setPaint(Color.DARK_GRAY);
    }
    g2.draw(area0);
    g2.setPaint(prevPaint);
  }
}

public class TabbedPaneUI extends BasicTabbedPaneUI {
  private static final int NORTH_AREA_PAD = 8;
  private static final int EAST_TAB_PAD = 48;
  private Stroke selectedStroke = new BasicStroke(3.0f);
  private Stroke normalStroke = new BasicStroke(1.0f);

  private CloseIcon closeIcon = new CloseIcon();

  class CloseIconMouseAdapter implements MouseListener, MouseMotionListener {
    private Rectangle buffer = new Rectangle();

    private int getTabIndex(MouseEvent e) {
      return TabbedPaneUI.this.tabForCoordinate(TabbedPaneUI.this.tabPane, e.getX(), e.getY());
    }

    private boolean isWithinCloseIcon(MouseEvent e) {
      if (TabbedPaneUI.this.tabPane.isEnabled()) {
        int index = getTabIndex(e);
        TabbedPaneUI.this.setRolloverTab(index);
        if (index >= 0) {
          if (TabbedPaneUI.this.isCloseButtonDesiredAt(index)) {
            TabbedPaneUI.this.getTabBounds(index, buffer);
            int xMin = (buffer.x + buffer.width) - (EAST_TAB_PAD / 2);
            int xMax = xMin + closeIcon.getIconWidth();
            int yMin = (buffer.y + (buffer.height / 2)) - 4;
            int yMax = yMin + closeIcon.getIconHeight();
            return ((xMin < e.getX()) && (e.getX() < xMax)) && ((yMin < e.getY()) && (e.getY() < yMax));
          }
        }
      }
      return false;
    }

    private void updateRollover(MouseEvent e) {
      closeIcon.setHighlighted(isWithinCloseIcon(e));
      TabbedPaneUI.this.repaintTab(this.getTabIndex(e));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      updateRollover(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
      closeIcon.setPressed(isWithinCloseIcon(e));
      tabPane.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      if (closeIcon.isPressed() && isWithinCloseIcon(e)) {
        closeTab(getTabIndex(e), e);
      }
      closeIcon.setPressed(false);
      tabPane.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
      updateRollover(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
  }

  private Color contentAreaColor;

  public TabbedPaneUI(Color contentAreaColor) {
    this.contentAreaColor = contentAreaColor;
  }

  @Override
  protected void installDefaults() {
    super.installDefaults();
    Font normalFont = this.tabPane.getFont();
    this.tabPane.setFont(normalFont.deriveFont(Font.ITALIC));

    this.tabAreaInsets.set(0, 0, 0, 0);
    this.tabInsets.set(3, 2, 3, 2);
    this.selectedTabPadInsets.set(0, 0, 0, 0);
    if (SystemUtilities.isMac()) {
      this.contentBorderInsets.set(0, 0, 0, 0);
    } else {
      this.contentBorderInsets.set(2, 2, 0, 0);
    }
    //this.lightHighlight = java.awt.Color.YELLOW;
  }

  private GeneralPath addToPath(GeneralPath rv, float x, float y, float width, float height, float a, boolean isContinuation) {
    float x0 = (x + width) - (EAST_TAB_PAD / 2);
    float x1 = x + width + EAST_TAB_PAD;
    //x0 += EAST_TAB_PAD;
    float cx0 = x0 + (EAST_TAB_PAD * 0.75f);
    float cx1 = x0;

    float y0 = y + NORTH_AREA_PAD;
    float y1 = y + height; // + this.contentBorderInsets.top;
    float cy0 = y0;
    float cy1 = y1;

    float xA = x + a;
    float yA = y + a;

    if (isContinuation) {
      rv.lineTo(x1, y1);
    } else {
      rv.moveTo(x1, y1);
    }
    rv.curveTo(cx1, cy1, cx0, cy0, x0, y0);
    rv.lineTo(xA, y0);
    rv.quadTo(x, y0, x, yA);
    rv.lineTo(x, y1);

    return rv;
  }

  @Override
  protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int width, int height, boolean isSelected) {
    Graphics2D g2 = (Graphics2D) g;
    Color prev = g.getColor();
    try {
      //      if( isSelected ) {
      //        g.setColor( this.lightHighlight );
      //        g2.setStroke( this.selectedStroke );
      //        g2.setClip( x, y, width + EAST_TAB_PAD, height + this.contentBorderInsets.top );
      //      } else {
      g.setColor(this.shadow);
      g2.setStroke(this.normalStroke);
      //      }
      GeneralPath path = this.addToPath(new GeneralPath(), x, y, width, height, height * 0.4f, false);
      g2.draw(path);
    } finally {
      g.setColor(prev);
    }
  }

  @Override
  protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int width, int height, boolean isSelected) {
    Color prev = g.getColor();
    try {
      int tabRolloverIndex = this.getRolloverTab();
      Component component = this.tabPane.getComponentAt(tabIndex);
      Color color = component.getBackground();
      if (isSelected) {
        //pass
      } else {
        color = color.darker();
        if (tabIndex == tabRolloverIndex) {
          //pass
        } else {
          color = color.darker();
        }
      }
      g.setColor(color);
      Graphics2D g2 = (Graphics2D) g;
      //g2.setClip( x-100, y, width+200, height + this.contentBorderInsets.top );
      GeneralPath path = addToPath(new GeneralPath(), x, y, width, height, height * 0.4f, false);
      //g2.translate( x, y );
      g2.fill(path);
      //g2.translate( -x, -y );
      if (isCloseButtonDesiredAt(tabIndex)) {
        if (this.getRolloverTab() == tabIndex) {
          this.closeIcon.setFilled(true);
        } else {
          this.closeIcon.setFilled(false);
        }
        this.closeIcon.paintIcon(this.tabPane, g2, (x + width) - (EAST_TAB_PAD / 2), (y + (height / 2)) - 4);
      }
    } finally {
      g.setColor(prev);
    }
  }

  @Override
  protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
    Color prev = g.getColor();
    try {
      //g.setColor( this.darkShadow );
      //      java.awt.Color color = javax.swing.UIManager.getColor( "TabbedPane.contentAreaColor" );
      //      g.setColor( color );
      g.setColor(this.contentAreaColor);
      Rectangle bounds = g.getClipBounds();
      g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
    } finally {
      g.setColor(prev);
    }
    super.paintTabArea(g, tabPlacement, selectedIndex);
  }

  @Override
  protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
  }

  private void repaintTab(int index) {
    if (index >= 0) {
      Rectangle bounds = this.getTabBounds(index, new Rectangle());

      //todo: investigate
      bounds.x -= EAST_TAB_PAD;
      bounds.width += EAST_TAB_PAD * 2;

      this.tabPane.repaint(bounds);
    }
  }

  @Override
  protected void setRolloverTab(int nextRollOverTab) {
    int prevRollOverTab = this.getRolloverTab();
    super.setRolloverTab(nextRollOverTab);
    if (prevRollOverTab != nextRollOverTab) {
      repaintTab(prevRollOverTab);
      repaintTab(nextRollOverTab);
    }
  }

  protected boolean isCloseButtonDesiredAt(int index) {
    return true;
  }

  protected void closeTab(int index, MouseEvent e) {
    tabPane.remove(index);
  }

  private CloseIconMouseAdapter closeIconMouseAdapter = new CloseIconMouseAdapter();

  @Override
  protected void installListeners() {
    super.installListeners();
    this.tabPane.addMouseListener(closeIconMouseAdapter);
    this.tabPane.addMouseMotionListener(closeIconMouseAdapter);
  }

  @Override
  protected void uninstallListeners() {
    super.uninstallListeners();
    this.tabPane.removeMouseListener(closeIconMouseAdapter);
    this.tabPane.removeMouseMotionListener(closeIconMouseAdapter);
  }

  @Override
  protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
    Color prev = g.getColor();
    try {
      Component component = this.tabPane.getSelectedComponent();
      if (component != null) {
        g.setColor(component.getBackground());
        g.fillRect(x, y, w, h);
        if (selectedIndex >= 0) {
          Rectangle boundsTab = getTabBounds(this.tabPane, selectedIndex);
          int xA = boundsTab.x;
          //int xB = boundsTab.x + boundsTab.width + EAST_TAB_PAD / 2;

          final float Y_DELTA = 0.0f;
          float y0 = y - Y_DELTA;
          GeneralPath path = new GeneralPath();
          path.moveTo(x + w, y0);
          //path.lineTo( xB, y );
          this.addToPath(path, xA, (y0 - boundsTab.height) + Y_DELTA, boundsTab.width, boundsTab.height - Y_DELTA, boundsTab.height * 0.4f, true);
          path.lineTo(x, y0);
          path.lineTo(x, y + h);

          Graphics2D g2 = (Graphics2D) g;
          g2.setStroke(this.selectedStroke);
          g.setColor(this.lightHighlight);
          g2.draw(path);
        }
      }
    } finally {
      g.setColor(prev);
    }
  }

  @Override
  protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
    int rv = super.calculateTabHeight(tabPlacement, tabIndex, fontHeight);
    return rv + NORTH_AREA_PAD;
  }

  @Override
  protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
    int rv = super.calculateTabWidth(tabPlacement, tabIndex, metrics);
    //rv += EAST_TAB_PAD;
    rv += EAST_TAB_PAD / 2;
    return rv;
  }

  @Override
  protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
    Color prev = g.getColor();
    try {
      g.setColor(Color.BLACK);
      int x = textRect.x;
      x -= EAST_TAB_PAD / 4;
      g.drawString(title, x, textRect.y + textRect.height);
      //super.paintText( g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected );
    } finally {
      g.setColor(prev);
    }
  }

  @Override
  protected void paintIcon(Graphics g, int tabPlacement, int tabIndex, Icon icon, Rectangle iconRect, boolean isSelected) {
    if (icon != null) {
      int x = iconRect.x;
      x -= EAST_TAB_PAD / 4;
      int y = iconRect.y;
      y += NORTH_AREA_PAD;
      Color prev = g.getColor();
      try {
        icon.paintIcon(this.tabPane, g, x, y);
      } finally {
        g.setColor(prev);
      }
    } else {
      super.paintIcon(g, tabPlacement, tabIndex, icon, iconRect, isSelected);
    }
  }
}
