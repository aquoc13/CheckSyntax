package ClientGUI;

import Client.Client;
import Server.ServerDataPacket;
import Services.FileHandler;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author danganhquoc
 */
public class GUI extends MoveJFrame {

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        this.setLocationRelativeTo(null);
        if (Client.checkConnection()) {
            process.append(Client.SUCCESS_CONNECT + "\n");
        } else {
            process.append(Client.FAIL_CONNECT + "\n");
            process.append("Click Run to reconnect !\n");
        }
    }
    
    static boolean maximized = true;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        subTitle = new javax.swing.JLabel();
        close = new javax.swing.JLabel();
        _btnFormat = new javax.swing.JButton();
        selectedBox = new javax.swing.JComboBox<>();
        _btnUpFile1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        prettifyCode = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        sourceCode = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        _btnLink = new javax.swing.JButton();
        _btnDownload = new javax.swing.JButton();
        _btnRun = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        input = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        process = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        compiler = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        title.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        title.setForeground(new java.awt.Color(250, 250, 250));
        title.setText("CODESYNTAX");
        jPanel1.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        subTitle.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        subTitle.setForeground(new java.awt.Color(189, 189, 189));
        subTitle.setText("Java, Python, C# and C compiler, syntax checking tool. ");
        jPanel1.add(subTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        close.setIcon(new javax.swing.ImageIcon("image/close.png")); // NOI18N
        close.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        jPanel1.add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 30, -1, -1));

        _btnFormat.setBackground(new java.awt.Color(239, 94, 29));
        _btnFormat.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        _btnFormat.setForeground(new java.awt.Color(255, 255, 255));
        _btnFormat.setIcon(new javax.swing.ImageIcon("image/format_paint.png")); // NOI18N
        _btnFormat.setText("format");
        _btnFormat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _btnFormat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btnFormatActionPerformed(evt);
            }
        });
        jPanel1.add(_btnFormat, new org.netbeans.lib.awtextra.AbsoluteConstraints(544, 196, 131, 42));

        selectedBox.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        selectedBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Java", "Python", "C#", "C++" }));
        selectedBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(selectedBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 230, 47));

        _btnUpFile1.setFocusPainted(false);
        _btnUpFile1.setBackground(new java.awt.Color(0, 121, 239));
        _btnUpFile1.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        _btnUpFile1.setForeground(new java.awt.Color(255, 255, 255));
        _btnUpFile1.setIcon(new javax.swing.ImageIcon("image/cloud_upload.png")); // NOI18N
        _btnUpFile1.setText("Upload your file");
        _btnUpFile1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _btnUpFile1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btnUpFile1ActionPerformed(evt);
            }
        });
        jPanel1.add(_btnUpFile1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 166, 42));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Source code:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, 28));

        prettifyCode.setBackground(new java.awt.Color(61, 72, 96));
        prettifyCode.setColumns(20);
        prettifyCode.setForeground(new java.awt.Color(255, 255, 255));
        prettifyCode.setRows(5);
        jScrollPane1.setViewportView(prettifyCode);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(364, 244, 311, 299));

        sourceCode.setBackground(new java.awt.Color(61, 72, 96));
        sourceCode.setColumns(20);
        sourceCode.setForeground(new java.awt.Color(255, 255, 255));
        sourceCode.setRows(5);
        jScrollPane2.setViewportView(sourceCode);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 244, 318, 299));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Prettifier Code");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(364, 210, -1, -1));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Input:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 568, -1, -1));

        _btnLink.setBackground(new java.awt.Color(13, 21, 37));
        _btnLink.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        _btnLink.setForeground(new java.awt.Color(255, 255, 255));
        _btnLink.setIcon(new javax.swing.ImageIcon("image/link.png")); // NOI18N
        _btnLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _btnLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btnLinkActionPerformed(evt);
            }
        });
        jPanel1.add(_btnLink, new org.netbeans.lib.awtextra.AbsoluteConstraints(635, 555, 41, 40));

        _btnDownload.setBackground(new java.awt.Color(13, 21, 37));
        _btnDownload.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        _btnDownload.setForeground(new java.awt.Color(255, 255, 255));
        _btnDownload.setIcon(new javax.swing.ImageIcon("image/save_alt.png")); // NOI18N
        _btnDownload.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _btnDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btnDownloadActionPerformed(evt);
            }
        });
        jPanel1.add(_btnDownload, new org.netbeans.lib.awtextra.AbsoluteConstraints(588, 555, 41, 40));

        _btnRun.setBackground(new java.awt.Color(4, 74, 21));
        _btnRun.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        _btnRun.setForeground(new java.awt.Color(108, 216, 131));
        _btnRun.setIcon(new javax.swing.ImageIcon("image/run.png")); // NOI18N
        _btnRun.setText("RUN");
        _btnRun.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btnRunActionPerformed(evt);
            }
        });
        jPanel1.add(_btnRun, new org.netbeans.lib.awtextra.AbsoluteConstraints(484, 555, 98, 40));

        input.setBackground(new java.awt.Color(45, 55, 74));
        input.setColumns(20);
        input.setForeground(new java.awt.Color(255, 255, 255));
        input.setRows(5);
        jScrollPane3.setViewportView(input);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 601, 650, -1));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Compiler");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 700, -1, -1));

        process.setBackground(new java.awt.Color(27, 35, 51));
        process.setColumns(20);
        process.setForeground(new java.awt.Color(255, 255, 255));
        process.setRows(5);
        process.setEditable(false);
        jScrollPane4.setViewportView(process);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(431, 719, -1, 160));

        compiler.setBackground(new java.awt.Color(27, 35, 51));
        compiler.setColumns(20);
        compiler.setForeground(new java.awt.Color(255, 255, 255));
        compiler.setRows(5);
        compiler.setEditable(false);
        jScrollPane5.setViewportView(compiler);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 719, 385, 160));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Process");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(431, 697, -1, -1));

        background.setIcon(new javax.swing.ImageIcon("image/background.png")); // NOI18N
        jPanel1.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 6, 690, 900));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
        try {
            if (Client.checkConnection()) {
                Client.send(Client.BREAK_CONNECT_KEY);
                Client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }//GEN-LAST:event_closeMouseClicked

    private void _btnFormatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__btnFormatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event__btnFormatActionPerformed

    private void _btnUpFile1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__btnUpFile1ActionPerformed
        DialogFile dialogFile = new DialogFile();
        dialogFile.setVisible(true);
        dialogFile.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                if (DialogFile.isOK()) {
                    DialogFile.latestFile = checkNullExtension(DialogFile.latestFile);
                    process.append("Open: " + DialogFile.latestFile + "\n");

                    String fileType = FileHandler.getFileExtension(DialogFile.latestFile);
                    process.append("Type: " + fileType + "\n");
                    if (Arrays.asList(FileHandler.supportedExtension).contains(fileType)) {
                        int typeIndex = Arrays.asList(FileHandler.supportedExtension).indexOf(fileType);
                        selectedBox.setSelectedIndex(typeIndex);
                        process.append("Selected language " + selectedBox.getSelectedItem() + "\n");
                    } else process.append("File language isn't supported\n");

                    String code = FileHandler.read(DialogFile.latestFile);
                    if (code != null && !code.isEmpty()) {
                        sourceCode.setText(code);
                        process.append("Import success.\n");
                    } else process.append("Import fail: File empty.\n");


                }
            }
        });
    }//GEN-LAST:event__btnUpFile1ActionPerformed

    private void _btnLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__btnLinkActionPerformed
        DialogLink dialogLink = new DialogLink();
        dialogLink.setVisible(true);
        dialogLink.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                if (DialogLink.isOK()) {
                    String fileType = FileHandler.getFileExtension(DialogLink.latestURL);
                    process.append("Connect: " + DialogLink.latestURL + "\n");

                    if (Arrays.asList(FileHandler.supportedExtension).contains(fileType)) {
                        int typeIndex = Arrays.asList(FileHandler.supportedExtension).indexOf(fileType);
                        selectedBox.setSelectedIndex(typeIndex);
                        process.append("Selected language " + selectedBox.getSelectedItem() + "\n");
                    } else process.append("File language isn't supported" + "\n");

                    String code = FileHandler.readURL(DialogLink.latestURL);
                    if (code != null && !code.isEmpty()) {
                        sourceCode.setText(code);
                        process.append("Import success." + "\n");
                    } else process.append("Import fail: Web empty." + "\n");
                }
            }
        });
    }//GEN-LAST:event__btnLinkActionPerformed

    private void _btnDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__btnDownloadActionPerformed
        DialogFile dialogFile = new DialogFile();
        dialogFile.setVisible(true);
        dialogFile.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                if (DialogFile.isOK()) {
                    DialogFile.latestFile = checkNullExtension(DialogFile.latestFile);
                    process.append("Open: " + DialogFile.latestFile + "\n");

                    String code = prettifyCode.getText();
                    FileHandler.write(DialogFile.latestFile, code);
                    process.append("Export success." + "\n");
                }
            }
        });
    }//GEN-LAST:event__btnDownloadActionPerformed

    private void _btnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__btnRunActionPerformed
        try {
            int selectedIndex = selectedBox.getSelectedIndex();
            String language = Client.supportedLanguage[selectedIndex];
            String versionIndex = Client.versionIndex;
            String script = sourceCode.getText();
            String stdin = input.getText();

            String clientDataPacket = Client.requestHandle(language,versionIndex,stdin,script);
            Client.send(clientDataPacket);

            String response = Client.receive();
            ServerDataPacket serverPacket = Client.responseHandle(response);
            compiler.append(serverPacket.pack());

        } catch (IOException | NullPointerException e) {
            if (!Client.checkConnection()) {
                try {
                    Client.connectServer();
                    Client.send("");
                } catch (IOException f) {
                    process.append(Client.FAIL_CONNECT + "\n");
                    process.append("Try again !" + "\n");
                    return;
                }
                process.append(Client.SUCCESS_CONNECT + "\n");
            }
        }
    }//GEN-LAST:event__btnRunActionPerformed

    /**
     * Kiếm tra path có đuôi file không nếu không tự thêm đuôi định dạng vào dựa theo ngôn ngữ đang chọn
     * @param path đường dẫn file.
     * @return path sau khi xử lý.
     */
    private String checkNullExtension(String path) {
        String fileType = FileHandler.getFileExtension(path);
        if (fileType == null || fileType.isEmpty()) {
            int selectedIndex = selectedBox.getSelectedIndex();
            String selectedType = Arrays.asList(FileHandler.supportedExtension).get(selectedIndex);
            return path + "." + selectedType;
        }
        return path;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
                
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _btnDownload;
    private javax.swing.JButton _btnFormat;
    private javax.swing.JButton _btnLink;
    private javax.swing.JButton _btnRun;
    private javax.swing.JButton _btnUpFile1;
    private javax.swing.JLabel background;
    private javax.swing.JLabel close;
    private javax.swing.JTextArea compiler;
    private javax.swing.JTextArea input;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextArea prettifyCode;
    private javax.swing.JTextArea process;
    private javax.swing.JComboBox<String> selectedBox;
    private javax.swing.JTextArea sourceCode;
    private javax.swing.JLabel subTitle;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}