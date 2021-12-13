package ClientGUI;

import Services.FileHandler;

import javax.swing.*;

/**
 *
 * @author danganhquoc
 */
public class DialogFile extends MoveJFrame {
    public static String latestFile = "";
    private static boolean isOK = false;
    /**
     * Creates new form DialogFile
     */
    public DialogFile() {
        initComponents();
        setTitle("Choose file - CheckSyntax");
    }

    public static boolean isOK() {
        if (isOK) {
            isOK = false;
            return true;
        }
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        input = new javax.swing.JTextField();
        _btnCancel = new javax.swing.JButton();
        _btnOK = new javax.swing.JButton();
        _btnOpenFile = new javax.swing.JButton();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Choose your file");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 27, -1, -1));

        input.setText(latestFile);
        jPanel1.add(input, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 50, 290, 44));

        _btnCancel.setBackground(new java.awt.Color(237, 245, 255));
        _btnCancel.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        _btnCancel.setForeground(new java.awt.Color(0, 107, 252));
        _btnCancel.setText("CANCEL");
        _btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btnCancelActionPerformed(evt);
            }
        });
        jPanel1.add(_btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 100, 44));

        _btnOK.setBackground(new java.awt.Color(0, 107, 252));
        _btnOK.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        _btnOK.setForeground(new java.awt.Color(255, 255, 255));
        _btnOK.setText("OK");
        _btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btnOKActionPerformed(evt);
            }
        });
        jPanel1.add(_btnOK, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 80, 44));

        _btnOpenFile.setIcon(new javax.swing.ImageIcon("image/more-icon.png")); // NOI18N
        _btnOpenFile.setFocusPainted(false);
        _btnOpenFile.setContentAreaFilled(false);
        _btnOpenFile.setBorderPainted(false);
        _btnOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btnOpenFileActionPerformed(evt);
            }
        });
        jPanel1.add(_btnOpenFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 50, 60, 40));

        background.setIcon(new javax.swing.ImageIcon("image/dialog-link.png")); // NOI18N
        jPanel1.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 180));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void _btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        isOK = false;
        this.dispose();
    }

    private void _btnOKActionPerformed(java.awt.event.ActionEvent evt) {
        latestFile = input.getText();
        isOK = true;
        this.dispose();
    }

    private void _btnOpenFileActionPerformed(java.awt.event.ActionEvent evt) {
        String path = FileHandler.fileChooser(this);
        input.setText(path);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _btnCancel;
    private javax.swing.JButton _btnOK;
    private javax.swing.JButton _btnOpenFile;
    private javax.swing.JLabel background;
    private javax.swing.JTextField input;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
