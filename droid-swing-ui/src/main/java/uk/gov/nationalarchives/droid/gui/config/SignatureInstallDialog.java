/*
 * Copyright (c) 2016, The National Archives <pronom@nationalarchives.gov.uk>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following
 * conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of the The National Archives nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package uk.gov.nationalarchives.droid.gui.config;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.openide.util.NbBundle;

/**
 *
 * @author rflitcroft
 */
public class SignatureInstallDialog extends JDialog {
    
    /** Cancel response. */
    public static final int CANCEL = 0;
    
    /** OK response. */
    public static final int OK = 1;
    
    private static final long serialVersionUID = 3915864617591112628L;

    private int response = CANCEL;
    
    /** 
     * Creates new form SignatureInstallDialog.
     * @param parent the parent window
     */
    public SignatureInstallDialog(Window parent) {
        super(parent);
        initComponents();

        pack();

        setLocationRelativeTo(parent);
    }
    
    /**
     * @return the response
     */
    public int getResponse() {
        return response;
    }
    
    /**
     * @return the signatureFile
     */
    public String getSignatureFilename() {
        return fileNameTextBox.getText();
    }
    
    /**
     * 
     * @return true if the selected file should be used as the new default
     */
    public boolean isDefault() {
        return setDefaultCheckbox.isSelected();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {


        fileChooser = new JFileChooser();
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        fileNameTextBox = new JTextField();
        browseButton = new JButton();
        setDefaultCheckbox = new JCheckBox();
        cancelButton = new JButton();
        okButton = new JButton();

        fileChooser.setApproveButtonText(NbBundle.getMessage(SignatureInstallDialog.class, "SignatureInstallDialog.fileChooser.approveButtonText")); // NOI18N
        fileChooser.setDialogTitle(NbBundle.getMessage(SignatureInstallDialog.class, "SignatureInstallDialog.fileChooser.dialogTitle")); // NOI18N
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(NbBundle.getMessage(SignatureInstallDialog.class, "SignatureInstallDialog.title")); // NOI18N
        setModal(true);




        jPanel1.setBorder(BorderFactory.createEtchedBorder());

        jLabel1.setText(NbBundle.getMessage(SignatureInstallDialog.class, "SignatureInstallDialog.jLabel1.text")); // NOI18N
        fileNameTextBox.setText(NbBundle.getMessage(SignatureInstallDialog.class, "SignatureInstallDialog.fileNameTextBox.text")); // NOI18N
        browseButton.setText(NbBundle.getMessage(SignatureInstallDialog.class, "SignatureInstallDialog.browseButton.text")); // NOI18N
        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        setDefaultCheckbox.setText(NbBundle.getMessage(SignatureInstallDialog.class, "SignatureInstallDialog.setDefaultCheckbox.text")); // NOI18N
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(browseButton, Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel1)
                        .addPreferredGap(ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
                            .addComponent(setDefaultCheckbox)
                            .addComponent(fileNameTextBox, GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(fileNameTextBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(browseButton)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(setDefaultCheckbox)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cancelButton.setText(NbBundle.getMessage(SignatureInstallDialog.class, "SignatureInstallDialog.cancelButton.text")); // NOI18N
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText(NbBundle.getMessage(SignatureInstallDialog.class, "SignatureInstallDialog.okButton.text")); // NOI18N
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(okButton)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );

        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {cancelButton, okButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        layout.linkSize(SwingConstants.VERTICAL, new Component[] {cancelButton, okButton});

    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        response = CANCEL;
        setVisible(false);
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void browseButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            final File selectedFile = fileChooser.getSelectedFile();
            fileNameTextBox.setText(selectedFile == null ? "" : selectedFile.getAbsolutePath());
        }
    }//GEN-LAST:event_browseButtonActionPerformed

    private void okButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        response = OK;
        setVisible(false);
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_okButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton browseButton;
    private JButton cancelButton;
    private JFileChooser fileChooser;
    private JTextField fileNameTextBox;
    private JLabel jLabel1;
    private JPanel jPanel1;
    private JButton okButton;
    private JCheckBox setDefaultCheckbox;
    // End of variables declaration//GEN-END:variables

}
