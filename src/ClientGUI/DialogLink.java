package ClientGUI;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author danganhquoc
 */
public class DialogLink extends MoveJFrame {
    public static String latestURL = "";
    private static boolean isOK = false;

    /** Creates new form DialogLink */
    public DialogLink() {
        initComponents();
    }

    public static boolean isOK() {
        if (isOK) {
            isOK = false;
            return true;
        }
        return false;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new JPanel();
        jLabel2 = new JLabel();
        input = new JTextField();
        _btnCancel = new JButton();
        _btnOK = new JButton();
        background = new JLabel();

        jPanel1.setLayout(new AbsoluteLayout());

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setBackground(new Color(255, 255, 255));
        jLabel2.setFont(new Font("Roboto", 0, 12)); // NOI18N
        jLabel2.setForeground(new Color(255, 255, 255));
        jLabel2.setText("input your URL");
        jPanel1.add(jLabel2, new AbsoluteConstraints(27, 27, -1, -1));

        input.setText(latestURL);
        jPanel1.add(input, new AbsoluteConstraints(27, 50, 345, 44));

        _btnCancel.setBackground(new Color(237, 245, 255));
        _btnCancel.setFont(new Font("Roboto", 0, 12)); // NOI18N
        _btnCancel.setForeground(new Color(0, 107, 252));
        _btnCancel.setText("CANCEL");
        _btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                _btnCancelActionPerformed(evt);
            }
        });
        jPanel1.add(_btnCancel, new AbsoluteConstraints(100, 110, 100, 44));

        _btnOK.setBackground(new Color(0, 107, 252));
        _btnOK.setFont(new Font("Roboto", 0, 12)); // NOI18N
        _btnOK.setForeground(new Color(255, 255, 255));
        _btnOK.setText("OK");
        _btnOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                _btnOKActionPerformed(evt);
            }
        });
        jPanel1.add(_btnOK, new AbsoluteConstraints(210, 110, 80, 44));

        background.setIcon(new ImageIcon("image/dialog.png")); // NOI18N
        jPanel1.add(background, new AbsoluteConstraints(0, 0, 400, 180));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void _btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__btnOKActionPerformed
        latestURL = input.getText();
        isOK = true;
        this.dispose();
    }//GEN-LAST:event__btnOKActionPerformed

    private void _btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__btnCancelActionPerformed
        isOK = false;
        this.dispose();
    }//GEN-LAST:event__btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _btnCancel;
    private javax.swing.JButton _btnOK;
    private javax.swing.JLabel background;
    private javax.swing.JTextField input;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

}
