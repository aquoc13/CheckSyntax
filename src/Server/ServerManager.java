package Server;

import Services.StringUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author danganhquoc
 */
public class ServerManager extends javax.swing.JFrame {

    public ServerManager() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Server manager - CheckSyntax");
        setIconImage(new ImageIcon("image/icon.png").getImage());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        _btnAdd = new javax.swing.JButton();
        _btnExecute = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        _btnList = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        textArea = new javax.swing.JTextField();
        _btnCheck = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        _btnAdd.setIcon(new javax.swing.ImageIcon("image/add.png")); // NOI18N
        _btnAdd.setFocusPainted(false);
        _btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btnAddActionPerformed(evt);
            }
        });

        _btnExecute.setIcon(new javax.swing.ImageIcon("image/play_arrow.png")); // NOI18N
        _btnExecute.setText("Execute");
        _btnExecute.setFocusPainted(false);
        _btnExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btnExecuteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        _btnList.setIcon(new javax.swing.ImageIcon("image/list_alt.png")); // NOI18N
        _btnList.setFocusPainted(false);
        _btnList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btnListActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel1.setText("TEXT");

        jLabel2.setText("To IP:");

        _btnCheck.setIcon(new javax.swing.ImageIcon("image/where_to_vote.png")); // NOI18N
        _btnCheck.setFocusPainted(false);
        _btnCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btnCheckActionPerformed(evt);
            }
        });

        jLabel3.setText("Message:");

        textArea.setFont(new java.awt.Font("roboto", Font.BOLD, 18));

        messageArea.setColumns(20);
        messageArea.setRows(3);
        messageArea.setFont(new java.awt.Font("roboto", 0, 18));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(messageArea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(_btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(_btnList, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(191, 191, 191)
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(_btnExecute, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(textArea, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(_btnCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(_btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(_btnList, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                    .addComponent(jLabel1)
                    .addComponent(_btnExecute))
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textArea, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_btnCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Giao diện nhập và xử lý password khi chạy server
     */
    public static void AuthenticationFrame() {
        //nhập pass và mở server ở cổng MAIN_PORT(5000)
        JFrame frame = new JFrame("Authentication");
        JLabel lblPassword = new JLabel("Password: ");
        lblPassword.setForeground(Color.white);
        JPasswordField pfPassword = new JPasswordField(19);

        pfPassword.setText("hellokitty"); // << password ở đây

        pfPassword.setBackground(new Color(61, 72, 96));
        pfPassword.setForeground(Color.white);
        lblPassword.setLabelFor(pfPassword);
        JButton btnGet = new JButton("Start");
        btnGet.setMargin(new Insets(5,30,5,30));
        btnGet.setBackground(new Color(13, 21, 37));
        btnGet.setFont(new Font("Roboto", Font.BOLD, 14));
        btnGet.setForeground(Color.white);
        btnGet.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGet.setFocusPainted(false);

        btnGet.addActionListener(e -> {
            String keyStore_password;
            keyStore_password = new String(pfPassword.getPassword());
            //hash password nhập vào và kiểm tra
            String hash = StringUtils.applySha256(keyStore_password, Server.KEY_STORE_SALT);
            if (hash.equals(Server.KEY_STORE_HASH)) {
                frame.dispose();
                Server.manager.setVisible(true);
                Main.run(keyStore_password);
            } else {
                pfPassword.setText("");
                lblPassword.setText("Wrong! ");
            }
        });

        JPanel panel = new JPanel();
        panel.setBackground(new Color(13, 21, 37));
        panel.setLayout(new FlowLayout());
        panel.add(lblPassword);
        panel.add(pfPassword);
        panel.add(btnGet);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(300, 140);
        frame.setIconImage(new ImageIcon("image/icon.png").getImage());
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void _btnAddActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void _btnExecuteActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void _btnListActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void _btnCheckActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private javax.swing.JButton _btnAdd;
    private javax.swing.JButton _btnCheck;
    private javax.swing.JButton _btnExecute;
    private javax.swing.JButton _btnList;
    public javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextArea messageArea;
    public javax.swing.JTextField textArea;
}
