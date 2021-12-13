package ClientGUI;

import Client.Client;
import Client.ClientListener;
import Services.FileHandler;
import Services.StringUtils;
import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.PopupListener;
import com.inet.jortho.SpellChecker;
import com.inet.jortho.SpellCheckerOptions;
import org.drjekyll.fontchooser.FontDialog;
import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

public class GUI extends MoveJFrame {
    private static final String CodeHolder = "Enter your source code here.";
    private static final String InputHolder = "Enter your input here.";
    private static final String COPY_BEAUTY = "Copied from Beautifier code to clipboard.";
    private static final String COPY_SOURCE = "Copied from Source code to clipboard.";
    private static String TextCounter;

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        setTitle("CheckSyntax");
        setLocationRelativeTo(null);
        setEnabled(false);

        SpellChecker.setUserDictionaryProvider(new FileUserDictionary());
        try {
            SpellChecker.registerDictionaries(new File("dictionary/").toURI().toURL(), "en");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        SpellChecker.register(sourceCode);
        SpellChecker.register(prettifyCode);

        SpellCheckerOptions sco = new SpellCheckerOptions();
        sco.setCaseSensitive(true);
        sco.setSuggestionsLimitMenu(10);
        JPopupMenu popup = SpellChecker.createCheckerPopup(sco);
        sourceCode.addMouseListener(new PopupListener(popup));
        prettifyCode.addMouseListener(new PopupListener(popup));
    }
    
    static boolean maximized = true;

    public void appendProcess(String text) {
        Calendar calendar = Calendar.getInstance();
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));

        if (calendar.get(Calendar.HOUR_OF_DAY) < 10) //số time bé hơn 10 thì thêm 0 vào trước
            hour = "0" + hour;
        if (calendar.get(Calendar.MINUTE) < 10)
            minute = "0" + minute;

        String time = "[" + hour + ":" + minute + "] ";
        process.append(time + text + "\n");
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
        title = new javax.swing.JLabel();
        subTitle = new javax.swing.JLabel();
        close = new javax.swing.JLabel();
        _btnFormat = new javax.swing.JButton();
        selectedBox = new javax.swing.JComboBox<>();
        _btnUpFile1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        prettifyCode = new RSyntaxTextArea();
        jScrollPane1 = new RTextScrollPane(prettifyCode);
        sourceCode = new RSyntaxTextArea();
        jScrollPane2 = new RTextScrollPane(sourceCode);
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
        _btnRestart = new javax.swing.JButton();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        title.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        title.setForeground(new java.awt.Color(250, 250, 250));
        title.setText("CODESYNTAX");
        jPanel1.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        subTitle.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        subTitle.setForeground(new java.awt.Color(189, 189, 189));
        subTitle.setText("Java, Python, PHP, C and C++ compiler, syntax checking tool. ");
        jPanel1.add(subTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        close.setIcon(new javax.swing.ImageIcon("image/close1.png")); // NOI18N
        close.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        jPanel1.add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 20, -1, -1));

        _btnFormat.setBackground(new java.awt.Color(65,105,225));
        _btnFormat.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        _btnFormat.setForeground(new java.awt.Color(255, 255, 255));
        _btnFormat.setIcon(new javax.swing.ImageIcon("image/format.png")); // NOI18N
        _btnFormat.setText("FORMAT");
        _btnFormat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _btnFormat.setFocusPainted(false);
        _btnFormat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btnFormatActionPerformed(evt);
            }
        });
        jPanel1.add(_btnFormat, new org.netbeans.lib.awtextra.AbsoluteConstraints(847, 184, 131, 42));

        selectedBox.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        selectedBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Java", "Python", "PHP", "C", "C++" }));
        selectedBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectedBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    switch (Objects.requireNonNull(selectedBox.getSelectedItem()).toString()) {
                        case "Java":
                            sourceCode.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
                            break;

                        case "Python":
                            sourceCode.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
                            break;

                        case "PHP":
                            sourceCode.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PHP);
                            break;

                        case "C":
                        case "C++":
                            sourceCode.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
                            break;
                    }
                    prettifyCode.setSyntaxEditingStyle(sourceCode.getSyntaxEditingStyle());
                }
            }
        });
        jPanel1.add(selectedBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 230, 47));

        _btnUpFile1.setFocusPainted(false);
        _btnUpFile1.setBackground(new java.awt.Color(0, 121, 239));
        _btnUpFile1.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        _btnUpFile1.setForeground(new java.awt.Color(255, 255, 255));
        _btnUpFile1.setIcon(new javax.swing.ImageIcon("image/cloud_upload.png")); // NOI18N
        _btnUpFile1.setText("Upload your file");
        _btnUpFile1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _btnUpFile1.setFocusPainted(false);
        _btnUpFile1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btnUpFile1ActionPerformed(evt);
            }
        });
        jPanel1.add(_btnUpFile1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 166, 42));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Source code:");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!sourceCode.getText().equalsIgnoreCase(CodeHolder)) {
                    StringUtils.copyToClipboard(sourceCode.getText());
                    compiler.setText(COPY_SOURCE);
                }
            }
        });
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 205, -1, 28));

        prettifyCode.setBackground(new Color(232, 232, 232));
        prettifyCode.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        prettifyCode.setCodeFoldingEnabled(true);
        prettifyCode.setAutoIndentEnabled(true);
        prettifyCode.setTabSize(sourceCode.getTabSize());
        prettifyCode.setUseSelectedTextColor(true);
        prettifyCode.setHighlightCurrentLine(false);
        jScrollPane1.setFoldIndicatorEnabled(false);
        jScrollPane1.setLineNumbersEnabled(false);
        prettifyCode.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                prettifyCode.setHighlightCurrentLine(true);
                prettifyCode.setBackground(Color.white);
                jScrollPane1.setFoldIndicatorEnabled(true);
                jScrollPane1.setLineNumbersEnabled(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                prettifyCode.setHighlightCurrentLine(false);
                prettifyCode.setBackground(new Color(232, 232, 232));
                jScrollPane1.setFoldIndicatorEnabled(false);
                jScrollPane1.setLineNumbersEnabled(false);
            }
        });
        LanguageSupportFactory.get().register(prettifyCode);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(512, 244, 466, 299));

        sourceCode.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        sourceCode.setCodeFoldingEnabled(true);
        sourceCode.setAutoIndentEnabled(true);
        sourceCode.setTabSize(3);
        sourceCode.setUseSelectedTextColor(true);
        sourceCode.setHighlightCurrentLine(false);
        sourceCode.setText(CodeHolder);
        sourceCode.setForeground(new Color(162, 162, 162));

        sourceCode.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                countText();
            }
            public void removeUpdate(DocumentEvent e) {
                countText();
            }
            public void insertUpdate(DocumentEvent e) {
                countText();
            }

            public void countText() {
                if ((compiler.getText().isEmpty()
                || compiler.getText().equalsIgnoreCase(TextCounter)
                || compiler.getText().equalsIgnoreCase(COPY_SOURCE)
                || compiler.getText().equalsIgnoreCase(COPY_BEAUTY))
                && !sourceCode.getText().equals(CodeHolder)) {
                    compiler.setForeground(Color.white);
                    int lineCount = StringUtils.getWrappedLines(sourceCode);
                    int textCount = sourceCode.getText().length();
                    int wordCount = sourceCode.getText().split("\\s").length;
                    TextCounter = "Col: " + textCount + " / "
                                + "Word: " + wordCount + " / "
                                + "Ln: " + lineCount;
                    compiler.setText(TextCounter);
                }
            }
        });

        sourceCode.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(sourceCode.getText().equalsIgnoreCase(CodeHolder)) {
                    sourceCode.setHighlightCurrentLine(true);
                    sourceCode.setText("");
                    sourceCode.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(sourceCode.getText().trim().equals("")) {
                    sourceCode.setHighlightCurrentLine(false);
                    sourceCode.setText(CodeHolder);
                    sourceCode.setForeground(new Color(162, 162, 162));
                }
            }
        });
        LanguageSupportFactory.get().register(sourceCode);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 244, 466, 299));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Beautifier code: ");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!prettifyCode.getText().isEmpty()) {
                    StringUtils.copyToClipboard(prettifyCode.getText());
                    compiler.setText(COPY_BEAUTY);
                }
            }
        });
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(512, 210, -1, -1));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Input:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 549, -1, -1));

        _btnLink.setBackground(new java.awt.Color(13, 21, 37));
        _btnLink.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        _btnLink.setForeground(new java.awt.Color(255, 255, 255));
        _btnLink.setIcon(new javax.swing.ImageIcon("image/link.png")); // NOI18N
        _btnLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _btnLink.setFocusPainted(false);
        _btnLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btnLinkActionPerformed(evt);
            }
        });
        jPanel1.add(_btnLink, new org.netbeans.lib.awtextra.AbsoluteConstraints(937, 577, 41, 40));

        _btnDownload.setBackground(new java.awt.Color(13, 21, 37));
        _btnDownload.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        _btnDownload.setForeground(new java.awt.Color(255, 255, 255));
        _btnDownload.setIcon(new javax.swing.ImageIcon("image/save_alt.png")); // NOI18N
        _btnDownload.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _btnDownload.setFocusPainted(false);
        _btnDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btnDownloadActionPerformed(evt);
            }
        });
        jPanel1.add(_btnDownload, new org.netbeans.lib.awtextra.AbsoluteConstraints(884, 577, 41, 40));

        _btnRun.setBackground(new java.awt.Color(4, 74, 21));
        _btnRun.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        _btnRun.setForeground(new java.awt.Color(108, 216, 131));
        _btnRun.setIcon(new javax.swing.ImageIcon("image/run.png")); // NOI18N
        _btnRun.setText("RUN");
        _btnRun.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _btnRun.setFocusPainted(false);
        _btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btnRunActionPerformed(evt);
            }
        });
        jPanel1.add(_btnRun, new org.netbeans.lib.awtextra.AbsoluteConstraints(721, 577, 98, 40));

        input.setBackground(new java.awt.Color(45, 55, 74));
        input.setColumns(20);
        input.setForeground(new Color(182, 182, 182));
        input.setRows(4);
        input.setCaretColor(Color.white);
        input.setFont(new Font("Roboto", Font.PLAIN, 16));
        input.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        input.setTabSize(0);
        input.setText(InputHolder);
        input.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(input.getText().equalsIgnoreCase(InputHolder)){
                    input.setText("");
                    input.setForeground(new java.awt.Color(255, 255, 255));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(input.getText().trim().equals("")){
                    input.setText(InputHolder);
                    input.setForeground(new Color(182, 182, 182));
                }
            }
        });
        jScrollPane3.setViewportView(input);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 577, 650, -1));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Compiler");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                compiler.setText("");
                compiler.setForeground(Color.white);
            }
        });
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 678, -1, -1));

        process.setBackground(new java.awt.Color(27, 35, 51));
        process.setColumns(20);
        process.setForeground(new java.awt.Color(255, 255, 255));
        process.setRows(5);
        process.setEditable(false);
        process.setFont(new Font("Roboto", Font.PLAIN, 16));
        process.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        DefaultCaret caret = (DefaultCaret) process.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        jScrollPane4.setViewportView(process);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(696, 695, 282, 178));

        compiler.setBackground(new java.awt.Color(27, 35, 51));
        compiler.setColumns(20);
        compiler.setForeground(new java.awt.Color(255, 255, 255));
        compiler.setRows(5);
        compiler.setEditable(false);
        compiler.setFont(new Font("Consolas", 0, 14));
        compiler.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jScrollPane5.setViewportView(compiler);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 695, 650, 178));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Process");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(696, 678, -1, -1));

        _btnRestart.setBackground(new java.awt.Color(13, 21, 37));
        _btnRestart.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        _btnRestart.setForeground(new java.awt.Color(255, 255, 255));
        _btnRestart.setIcon(new javax.swing.ImageIcon("image/format_paint.png")); // NOI18N
        _btnRestart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _btnRestart.setFocusPainted(false);
        _btnRestart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btnRestartActionPerformed(evt);
            }
        });
        jPanel1.add(_btnRestart, new org.netbeans.lib.awtextra.AbsoluteConstraints(831, 577, 41, 40));

        background.setIcon(new javax.swing.ImageIcon("image/background.png")); // NOI18N
        jPanel1.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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

    /**
     * Event nút tắt Client
     */
    private void closeMouseClicked(java.awt.event.MouseEvent evt) {
        try {
            //kiểm tra nếu có kết nối thì phải gửi "bye" và close socket trước khi tắt giao diện
            if (Client.checkConnection()) {
                Client.send(Client.BREAK_CONNECT_KEY);
                Client.close();
            }
        } catch (IOException ignored) {}
        this.dispose();
    }

    /**
     * Event nút Format
     */
    private void _btnFormatActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // lấy dữ liệu từ giao diện
            int selectedIndex = selectedBox.getSelectedIndex();
            String description = "FORMAT";
            String language = Client.supportedLanguage[selectedIndex];
            String script = sourceCode.getText().replace(CodeHolder,"");
            String stdin = "";

            //xử lý rồi đóng gói thành clientDataPacket dạng json.
            Client.currentDataPacket = ClientListener.requestHandle(description, language, stdin, script);
            Client.send(Client.currentDataPacket);
        } catch (IOException | NullPointerException e) {
            appendProcess(Client.FAIL_CONNECT);
        }
    }

    /**
     * Event nút upload file
     */
    private void _btnUpFile1ActionPerformed(java.awt.event.ActionEvent evt) {
        JFrame frame = this;
        frame.setEnabled(false);
        DialogFile dialogUpFile = new DialogFile();
        dialogUpFile.setLocationRelativeTo(frame);
        dialogUpFile.setVisible(true);

        dialogUpFile.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                if (DialogFile.isOK() && !DialogFile.latestFile.isEmpty()) {
                    process.append("\n");
                    DialogFile.latestFile = FileHandler.checkNullExtension(DialogFile.latestFile);
                    appendProcess("Open: " + DialogFile.latestFile);

                    String fileType = FileHandler.getFileExtension(DialogFile.latestFile);

                    if (!fileType.equals("jpg")
                            && Arrays.asList(FileHandler.supportedExtension).contains(fileType)) {
                        int typeIndex = Arrays.asList(FileHandler.supportedExtension).indexOf(fileType);
                        selectedBox.setSelectedIndex(typeIndex);
                    }
                    else appendProcess("File language isn't supported.");

                    if (fileType.equals("jpg")) {
                        try {
                            Client.sendImage(DialogFile.latestFile);
                            DialogFile.latestFile = DialogFile.latestFile.replace(".jpg","");
                        } catch (IOException ignored) {
                            appendProcess("Import fail: Cant convert image.");
                            frame.setEnabled(true);
                            frame.toFront();
                        }
                    }
                    else {
                        String code = FileHandler.read(DialogFile.latestFile);
                        if (!code.isEmpty()) {
                            sourceCode.setText(code);
                            sourceCode.setForeground(Color.black);
                            appendProcess("Import success.");
                        }
                        else appendProcess("Import fail: File empty.");
                    }
                }
                frame.setEnabled(true);
                frame.toFront();
            }
        });
    }

    /**
     * Event nút link web
     */
    private void _btnLinkActionPerformed(java.awt.event.ActionEvent evt) {
        JFrame frame = this;
        frame.setEnabled(false);
        DialogLink dialogLink = new DialogLink();
        dialogLink.setLocationRelativeTo(frame);
        dialogLink.setVisible(true);

        dialogLink.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                if (DialogLink.isOK() && !DialogLink.latestURL.isEmpty()) {
                    process.append("\n");
                    String fileType = FileHandler.getFileExtension(DialogLink.latestURL);
                    appendProcess("Connect: " + DialogLink.latestURL + "");

                    if (!fileType.equals("jpg")
                            && Arrays.asList(FileHandler.supportedExtension).contains(fileType)) {
                        int typeIndex = Arrays.asList(FileHandler.supportedExtension).indexOf(fileType);
                        selectedBox.setSelectedIndex(typeIndex);
                    }
                    else appendProcess("File language isn't supported." + "");

                    if (fileType.equals("jpg")) {
                        try {
                            Client.sendImage(DialogLink.latestURL);
                            DialogLink.latestURL = DialogLink.latestURL.replace(".jpg","");
                        } catch (IOException f) {
                            f.printStackTrace();
                            appendProcess("Import fail: Cant convert web image.");
                            frame.setEnabled(true);
                            frame.toFront();
                        }
                    }
                    else {
                        String code = FileHandler.readURL(DialogLink.latestURL);
                        if (!code.isEmpty()) {
                            sourceCode.setText(code);
                            sourceCode.setForeground(Color.black);
                            appendProcess("Import success." + "");
                        } else appendProcess("Import fail: Web empty." + "");
                    }
                }
                frame.setEnabled(true);
                frame.toFront();
            }
        });
    }

    /**
     * Event nút tải vè
     */
    private void _btnDownloadActionPerformed(java.awt.event.ActionEvent evt) {
        JFrame frame = this;
        frame.setEnabled(false);
        DialogFile dialogSaveFile = new DialogFile();
        dialogSaveFile.setLocationRelativeTo(frame);
        dialogSaveFile.setVisible(true);

        dialogSaveFile.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                if (DialogFile.isOK() && !DialogFile.latestFile.isEmpty()) {
                    process.append("\n");
                    DialogFile.latestFile = FileHandler.checkNullExtension(DialogFile.latestFile);
                    appendProcess("Open: " + DialogFile.latestFile);

                    String code = prettifyCode.getText();
                    FileHandler.write(DialogFile.latestFile, code, false);
                    appendProcess("Export success.");
                }
                frame.setEnabled(true);
                frame.toFront();
            }
        });
    }

    /**
     * Event nút setting font
     */
    private void _btnRestartActionPerformed(java.awt.event.ActionEvent evt) {
        FontDialog dialog = new FontDialog(this, "Setting Fonts - CheckSyntax", true);
        dialog.setSelectedFont(sourceCode.getFont());
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setIconImage(new ImageIcon("image/icon.png").getImage());
        dialog.setBackground(new Color(13, 21, 37));
        dialog.setVisible(true);
        if (!dialog.isCancelSelected()) {
            sourceCode.setFont(dialog.getSelectedFont());
            prettifyCode.setFont(dialog.getSelectedFont());
            appendProcess("Changed font.");
        }
    }

    /**
     * Event nút RUN
     */
    private void _btnRunActionPerformed(java.awt.event.ActionEvent evt) {
        this.setEnabled(false);
        try {
            //lấy dữ liệu từ giao diện
            int selectedIndex = selectedBox.getSelectedIndex();
            String description = "COMPILE";
            String language = Client.supportedLanguage[selectedIndex];
            String script = sourceCode.getText().replace(CodeHolder,"");
            String stdin = input.getText().replace(InputHolder,"");

            //xử lý và đóng gói thành clientDataPacket dạng json
            String clientDataPacket = ClientListener.requestHandle(description, language, stdin, script);
            Client.send(Client.currentDataPacket);
            System.out.println("Sent packet: " + clientDataPacket + "\n");
            appendProcess("Sent request.");
        } catch (IOException | NullPointerException e) {
            //exception throw khi ko thể kết nói tối server.
            try {
                //Thực hiện kết nối lại
                Client.close();
                Client.connectServer();
            } catch (IOException f) {
                //Throw exception nếu kết nối lại thất bại
                appendProcess(Client.FAIL_CONNECT);
                this.setEnabled(true);
                return;
            }
            appendProcess(Client.SUCCESS_CONNECT);
        }
        this.setEnabled(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _btnDownload;
    private javax.swing.JButton _btnFormat;
    private javax.swing.JButton _btnLink;
    private javax.swing.JButton _btnRestart;
    private javax.swing.JButton _btnRun;
    private javax.swing.JButton _btnUpFile1;
    private javax.swing.JLabel background;
    private javax.swing.JLabel close;
    public javax.swing.JTextArea compiler;
    public javax.swing.JTextArea input;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private RTextScrollPane jScrollPane1;
    private RTextScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    public RSyntaxTextArea prettifyCode;
    public javax.swing.JTextArea process;
    public javax.swing.JComboBox<String> selectedBox;
    public RSyntaxTextArea sourceCode;
    private javax.swing.JLabel subTitle;
    private javax.swing.JLabel title;
    // End of variables declaration
    // End of variables declaration//GEN-END:variables
}
