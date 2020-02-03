/*******************************************************************************
 * Copyright (c) 2006, 2016, Carnegie Mellon University. All rights reserved.
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

package org.alice.netbeans.palette.items.views;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.JTextComponent;

import org.alice.netbeans.palette.items.DoTogether;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;

public class DoTogetherCustomizer extends javax.swing.JPanel {

  private Dialog dialog = null;
  private DialogDescriptor descriptor = null;
  private boolean dialogOK = false;

  DoTogether doTogether;
  JTextComponent target;

  /** Creates new form DoTogetherCustomizer */
  public DoTogetherCustomizer(DoTogether doTogether, JTextComponent target) {
    this.doTogether = doTogether;
    this.target = target;

    initComponents();

    this.jSpinner1.setModel(new SpinnerNumberModel(2, 2, 100, 1));
  }

  public boolean showDialog() {

    dialogOK = false;

    String displayName = "Do Together";
    descriptor = new DialogDescriptor(this, displayName, true, DialogDescriptor.OK_CANCEL_OPTION, DialogDescriptor.OK_OPTION, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (descriptor.getValue().equals(DialogDescriptor.OK_OPTION)) {
          evaluateInput();
          dialogOK = true;
        }
        dialog.dispose();
      }
    });

    dialog = DialogDisplayer.getDefault().createDialog(descriptor);
    dialog.setVisible(true);
    repaint();

    return dialogOK;

  }

  private void evaluateInput() {
    int count = ((Integer) jSpinner1.getValue()).intValue();
    doTogether.setRunnableCount(count);

  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    jLabel1 = new javax.swing.JLabel();
    jSpinner1 = new javax.swing.JSpinner();

    setLayout(new java.awt.GridBagLayout());

    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jLabel1.setText(org.openide.util.NbBundle.getMessage(DoTogetherCustomizer.class, "DoTogetherCustomizer.jLabel1.text")); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.insets = new java.awt.Insets(10, 10, 4, 6);
    add(jLabel1, gridBagConstraints);

    jSpinner1.setMinimumSize(new java.awt.Dimension(39, 20));
    jSpinner1.setPreferredSize(new java.awt.Dimension(39, 20));
    jSpinner1.setValue(new Integer(2));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.insets = new java.awt.Insets(10, 0, 4, 10);
    add(jSpinner1, gridBagConstraints);
  } // </editor-fold>//GEN-END:initComponents

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel jLabel1;
  private javax.swing.JSpinner jSpinner1;
  // End of variables declaration//GEN-END:variables

}
