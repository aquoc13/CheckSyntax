package Server;

import Services.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ServerManagerGUI extends javax.swing.JFrame {
    private static final Image icon = new ImageIcon("image/server_icon.png").getImage();
    private User toUser;
    private boolean lockEvent = false;
    private String tutorial;

    private final String tutorial_for_all = "Enter command ...\n\n"
            + "Command (for all): \n"
            + "#ALL message... - Chat all.\n"
            + "#CLEAR - Clear all user history.\n"
            + "#STOP - Stop all user.\n"
            + "#SPENT.";

    private final String tutorial_for_online = "Enter message or command ...\n\n"
            + "Command (for online): \n"
            + "#BAN - Ban user.\n"
            + "#STOP - Close connect with user.\n"
            + "#CLEAR - Clear user history.\n"
            + "#SESSION - renew user session.";

    private final String tutorial_for_offline = "Enter command ...\n\n"
            + "Command (for offline): \n"
            + "#BAN - Ban user.\n"
            + "#UNBAN - Unban user.\n"
            + "#CLEAR - Clear user history.\n"
            + "#SESSION - renew user session.";

    public ServerManagerGUI() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Server manager - CheckSyntax");
        setIconImage(icon);
        _btnExecute.setEnabled(false);
        _btnList.setEnabled(false);
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
        textField = new javax.swing.JTextField();
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
        jLabel1.setText("");
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        jLabel2.setText("To IP/UID:");

        _btnCheck.setIcon(new javax.swing.ImageIcon("image/check_icon.png")); // NOI18N
        _btnCheck.setFocusPainted(false);
        _btnCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btnCheckActionPerformed(evt);
            }
        });

        jLabel3.setText("Message:");

        textField.setFont(new java.awt.Font("roboto", Font.BOLD, 16));
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textField.setForeground(new Color(142, 142, 142));
        String ph_textField = "Check user status first";
        textField.setText(ph_textField);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(textField.getText().equalsIgnoreCase(ph_textField)){
                    textField.setText("");
                    textField.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(textField.getText().trim().equals("")) {
                    textField.setText(ph_textField);
                    textField.setForeground(new Color(142, 142, 142));
                }
            }
        });

        textField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                resetStatus();
            }
            public void removeUpdate(DocumentEvent e) {
                resetStatus();
            }
            public void insertUpdate(DocumentEvent e) {
                resetStatus();
            }
        });

        messageArea.setColumns(20);
        messageArea.setRows(3);
        messageArea.setFont(new java.awt.Font("roboto", Font.PLAIN, 16));
        messageArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        messageArea.setForeground(new Color(142, 142, 142));
        messageArea.setTabSize(2);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        tutorial = tutorial_for_all;
        messageArea.setText(tutorial);
        messageArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(messageArea.getText().equalsIgnoreCase(tutorial)){
                    messageArea.setText("");
                    messageArea.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(messageArea.getText().trim().equals("")){
                    messageArea.setText(tutorial);
                    messageArea.setForeground(new Color(142, 142, 142));
                }
            }
        });
        messageArea.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {}
            public void removeUpdate(DocumentEvent e) {
                if (toUser != null) return;
                _btnExecute.setEnabled(messageArea.getText().startsWith("#"));
            }
            public void insertUpdate(DocumentEvent e) {
                if (toUser != null) return;
                _btnExecute.setEnabled(messageArea.getText().startsWith("#"));
            }
        });
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
                                    .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        //lblPassword.setForeground(Color.white);
        JPasswordField pfPassword = new JPasswordField(19);
        DefaultCaret caret = (DefaultCaret) pfPassword.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        pfPassword.setText("hellokitty"); // << password ở đây

        //pfPassword.setBackground(new Color(61, 72, 96));
        //pfPassword.setForeground(Color.white);
        lblPassword.setLabelFor(pfPassword);
        JButton btnGet = new JButton("Start");
        btnGet.setMargin(new Insets(5,30,5,30));
        //btnGet.setBackground(new Color(13, 21, 37));
        btnGet.setFont(new Font("Roboto", Font.BOLD, 14));
        //btnGet.setForeground(Color.white);
        btnGet.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGet.setFocusPainted(false);

        btnGet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Server.keyStore_password = new String(pfPassword.getPassword());
                //hash password nhập vào và kiểm tra
                String hash = StringUtils.applySha256(Server.keyStore_password, Server.KEY_STORE_SALT);
                if (hash.equals(Server.KEY_STORE_HASH)) {
                    frame.dispose();
                    Server.manager.setVisible(true);
                    Main.run();
                } else {
                    pfPassword.setText("");
                    lblPassword.setText("Wrong! ");
                }
            }
        });

        JPanel panel = new JPanel();
        //panel.setBackground(new Color(13, 21, 37));
        panel.setLayout(new FlowLayout());
        panel.add(lblPassword);
        panel.add(pfPassword);
        panel.add(btnGet);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(300, 140);
        frame.setIconImage(icon);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void _btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        UserTable();
    }

    private void _btnExecuteActionPerformed(java.awt.event.ActionEvent evt) {
        if (messageArea.getText().isEmpty() || messageArea.getText().equalsIgnoreCase(tutorial)) return;
        if (messageArea.getText().startsWith("#")) {
            String command = messageArea.getText();

            if (command.toUpperCase().startsWith("#ALL")) {
                for (User u:Server.users) {
                    String message = messageArea.getText().trim().replaceAll("(?i)#all ", "");
                    sendChat(message, u);
                    jLabel1.setText("SENT ALL");
                }
            }
            else if (toUser == null) {
                switch (command.toUpperCase()) {
                    case "#CLEAR":
                        clearAllUser();
                        jLabel1.setText("CLEARED");
                        break;

                    case "#STOP":
                        stopAllUser();
                        jLabel1.setText("STOPPED !");
                        break;

                    case "#SPENT":
                        jLabel1.setText("API: " + Compiler.getCreditSpent() + " / 200");
                        break;

                    default:
                        jLabel1.setText("WRONG !");
                        break;
                }
            }
            else {
                switch (command.toUpperCase()) {
                    case "#BAN":
                        banUser();
                        jLabel1.setText("BANNED");
                        break;
                    case "#UNBAN":
                        unbanUser();
                        jLabel1.setText("UNBANNED");
                        break;
                    case "#STOP":
                        stopUser(toUser);
                        jLabel1.setText("STOP");
                        _btnCheck.doClick();
                        break;
                    case "#CLEAR":
                        clearUser(toUser);
                        jLabel1.setText("CLEAR");
                        break;
                    case "#SESSION":
                        renewSession();
                        jLabel1.setText("RENEW");
                        break;

                    default:
                        jLabel1.setText("WRONG !");
                        break;
                }
            }
        } else {
            sendChat(messageArea.getText(), toUser);
            jLabel1.setText("SENT");
        }
    }

    private void sendChat(String message, User to) {
        try {
            String packet = Server.messageHandle("CHAT", message, to);
            Socket socket = to.getSocket();
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.write(packet);
            out.newLine();
            out.flush();
        } catch (Exception ignored) {}
    }

    private void stopAllUser() {
        for (User u:Server.users) {
            stopUser(u);
        }
    }

    private void clearAllUser() {
        for (User u:Server.users) {
            clearUser(u);
        }
    }

    private void banUser() {
        if (toUser.getStatus().equals("banned"))
            return;
        sendChat("u got banned.", toUser);
        stopUser(toUser);
        toUser.setSessionTime(-1);
        toUser.setStatus("banned");
        if (Server.users.remove(toUser))
            Server.users.add(toUser);

        String getIP = toUser.getSocket().getInetAddress().getHostAddress();
        Server.banList.putIfAbsent(getIP, toUser.getUID());
        try {
            toUser.getSocket().close();
        } catch (IOException ignored) {}
        _btnCheck.doClick();
    }

    private void unbanUser() {
        if (!toUser.getStatus().equals("banned"))
            return;
        toUser.setStatus("offline");
        renewSession();
        String getIP = toUser.getSocket().getInetAddress().getHostAddress();
        Server.banList.remove(getIP);
        _btnCheck.doClick();
    }

    private void stopUser(User to) {
        if (!to.getStatus().equals("online"))
            return;
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(to.getSocket().getOutputStream()));
            out.write("stop");
            out.newLine();
            out.flush();
            to.getSocket().close();
        } catch (IOException ignored) {}
    }

    private void clearUser(User to) {
        to.getRequestList().clear();
        to.getResponseList().clear();
        to.getDateList().clear();
    }

    private void renewSession() {
        if (toUser.getStatus().equals("banned"))
            return;
        toUser.setSessionTime(System.currentTimeMillis());
    }

    private void _btnListActionPerformed(java.awt.event.ActionEvent evt) {
        if (toUser.getResponseList().isEmpty()) {
            JOptionPane.showMessageDialog(this,"Currently no history !","Empty", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFrame frame = new JFrame(toUser.getUID() + " - CheckSyntax");

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane();
        JScrollPane jScrollPane1 = new JScrollPane();
        JTable table = new JTable() {
            final DefaultTableCellRenderer renderRight = new DefaultTableCellRenderer();
            { // initializer block
                renderRight.setHorizontalAlignment(SwingConstants.RIGHT);
            }
            @Override
            public TableCellRenderer getCellRenderer (int arg0, int arg1) {
                return renderRight;
            }
        };
        table.setDragEnabled(false);

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[] columnData = new String [] {"ID", "Request Packet", "Response Packet", "Created Date"};
        model.setColumnIdentifiers(columnData);
        Object[] rowData = new Object[4];
        for (int i = 0; i < toUser.getDateList().size(); i++) {
            try {
                rowData[0] = model.getRowCount() + 1;
                rowData[1] = toUser.getRequestList().get(i);
                rowData[2] = toUser.getResponseList().get(i);
                rowData[3] = toUser.getDateList().get(i);
                model.addRow(rowData);
            } catch (Exception ignored) {}
        }

        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(225);
        table.getColumnModel().getColumn(2).setPreferredWidth(225);
        table.getColumnModel().getColumn(3).setPreferredWidth(155);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String date = table.getValueAt(table.getSelectedRow(), 3).toString();
                    String data1 = table.getValueAt(table.getSelectedRow(), 1).toString();
                    String data2 = table.getValueAt(table.getSelectedRow(), 2).toString();
                    textArea.setText("Packet created at: " + date + "\n\n");
                    textArea.append("Data send:\n" + gson.toJson(JsonParser.parseString(data1)) + "\n\n");
                    textArea.append("Data receive:\n" + gson.toJson(JsonParser.parseString(data2)) + "\n");
                } catch (Exception ignored) {}
            }
        });

        jScrollPane.setViewportView(table);
        jScrollPane.setPreferredSize(new Dimension(628,200));
        jScrollPane1.setViewportView(textArea);
        jScrollPane1.setPreferredSize(new Dimension(628,300));

        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);
        panel.add(jScrollPane);
        panel.add(jScrollPane1);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon("image/icon.png").getImage());
        frame.setAlwaysOnTop(true);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void _btnCheckActionPerformed(java.awt.event.ActionEvent evt) {
        String input = textField.getText();
        if ((toUser = checkStatus(input)) != null)
            apply();
        else {
            tutorial = tutorial_for_all;
            jLabel2.setText("To IP: NOT FOUND");
            textField.setBackground(new Color(231, 76, 60));
        }
        textField.setForeground(Color.white);
    }

    private User checkStatus(String input) {
        for (User u : Server.users) {
            String userIP = u.getSocket().getInetAddress().getHostAddress()
                    + ":" + u.getSocket().getPort();
            if (userIP.contains(input)
            || u.getUID().equals(input)) {
                _btnList.setEnabled(true);
                _btnExecute.setEnabled(true);
                return u;
            }
        }
        return null;
    }

    private void resetStatus() {
        if (lockEvent)
            return;

        jLabel2.setText("To IP/UID: unchecked");
        textField.setBackground(Color.white);
        textField.setForeground(Color.black);
        tutorial = tutorial_for_all;
        messageArea.setForeground(new Color(142, 142, 142));
        messageArea.setText(tutorial);
        if (toUser != null) {
            toUser = null;
            enableExecute(false);
        }
    }

    private void apply() {
        lockEvent = true;
        jLabel1.setText("");

        jLabel2.setText("To UID: " + toUser.getStatus()
                + " at " + toUser.getSocket().getInetAddress().getHostAddress()
                + ":" + toUser.getSocket().getPort());

        textField.setText(toUser.getUID());
        textField.setForeground(Color.white);
        if (toUser.getStatus().equalsIgnoreCase("online")) {
            if (toUser.getSessionTime() == -1) {
                textField.setBackground(new Color(231, 231, 60));
                jLabel2.setText(jLabel2.getText() + " (Expired)");
            }
            else textField.setBackground(new Color(117, 236, 99));

            tutorial = tutorial_for_online;
            enableExecute(true);
        } else {
            textField.setBackground(new Color(231, 76, 60));
            tutorial = tutorial_for_offline;
        }
        messageArea.setForeground(new Color(142, 142, 142));
        messageArea.setText(tutorial);
        lockEvent = false;
    }

    private void enableExecute(boolean isEnable) {
        if (isEnable) {
            _btnExecute.setEnabled(true);
            _btnList.setEnabled(true);
        } else {
            _btnExecute.setEnabled(false);
            _btnList.setEnabled(false);
        }
    }

    private void UserTable() {
        if (Server.users.isEmpty()) {
            JOptionPane.showMessageDialog(this,"There are currently no users !","Empty", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JScrollPane jScrollPane = new JScrollPane();
        JTable table = new JTable() {
            final DefaultTableCellRenderer renderRight = new DefaultTableCellRenderer();
            { // initializer block
                renderRight.setHorizontalAlignment(SwingConstants.RIGHT);
            }
            @Override
            public TableCellRenderer getCellRenderer (int arg0, int arg1) {
                return renderRight;
            }
        };
        table.setDragEnabled(false);

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[] columnData = new String [] {"#", "UID", "IP", "Secret Key", "Timer", "Status", "Date", "Memory"};
        model.setColumnIdentifiers(columnData);
        Object[] rowData = new Object[8];
        for (User u : Server.users) {
            rowData[0] = model.getRowCount() + 1;
            rowData[1] = u.getUID();
            rowData[2] = u.getSocket().getInetAddress().getHostAddress() + ":" + u.getSocket().getPort();
            rowData[3] = u.getSecretKey();
            String time = "Over";
            if (u.getSessionTime() != -1) {
                long milliseconds = System.currentTimeMillis() - u.getSessionTime();
                String minutes = String.valueOf(TimeUnit.MILLISECONDS.toMinutes(milliseconds));
                String seconds = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60);
                if (Integer.parseInt(minutes) < 10) {
                    minutes = "0" + minutes;
                }
                if (Integer.parseInt(seconds) < 10) {
                    seconds = "0" + seconds;
                }
                time = minutes + ":" + seconds;
            }
            rowData[4] = time;
            rowData[5] = u.getStatus();
            rowData[6] = u.getModifiedDate();
            rowData[7] = (u.getRequestList().size() + u.getResponseList().size()) + " packet";
            model.addRow(rowData);
        }
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(250);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(130);
        table.getColumnModel().getColumn(4).setPreferredWidth(50);
        table.getColumnModel().getColumn(5).setPreferredWidth(50);
        table.getColumnModel().getColumn(6).setPreferredWidth(200);
        table.getColumnModel().getColumn(7).setPreferredWidth(70);

        jScrollPane.setViewportView(table);
        jScrollPane.setPreferredSize(new Dimension(873,200));
        int result = JOptionPane.showConfirmDialog(this, jScrollPane, "User List", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            List<User> userList = new ArrayList<>(Server.users);
            toUser = userList.get(table.getSelectedRow());
            apply();
        }
    }

    private javax.swing.JButton _btnAdd;
    public javax.swing.JButton _btnCheck;
    private javax.swing.JButton _btnExecute;
    private javax.swing.JButton _btnList;
    public javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextArea messageArea;
    public javax.swing.JTextField textField;
}
