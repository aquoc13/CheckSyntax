package ClientGUI;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import Client.Client;
import Services.StringUtils;
import com.inet.jortho.PopupListener;
import com.inet.jortho.SpellChecker;
import com.inet.jortho.SpellCheckerOptions;
import org.drjekyll.fontchooser.FontDialog;
import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.rsta.ui.CollapsibleSectionPanel;
import org.fife.rsta.ui.GoToDialog;
import org.fife.rsta.ui.SizeGripIcon;
import org.fife.rsta.ui.search.FindDialog;
import org.fife.rsta.ui.search.ReplaceDialog;
import org.fife.rsta.ui.search.ReplaceToolBar;
import org.fife.rsta.ui.search.SearchEvent;
import org.fife.rsta.ui.search.SearchListener;
import org.fife.rsta.ui.search.FindToolBar;
import org.fife.ui.rsyntaxtextarea.ErrorStrip;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;
import org.fife.ui.rtextarea.SearchResult;


/**
 * An application that demonstrates use of the RSTAUI project.  Please don't
 * take this as good application design; it's just a simple example.<p>
 *
 * Unlike the library itself, this class is public domain.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public final class Editor extends JFrame implements SearchListener {
	private CollapsibleSectionPanel csp;
	private RSyntaxTextArea textArea;
	private RTextScrollPane sp;
	private FindDialog findDialog;
	private ReplaceDialog replaceDialog;
	private FindToolBar findToolBar;
	private ReplaceToolBar replaceToolBar;
	private StatusBar statusBar;


	public Editor() {
		initSearchDialogs();

		JPanel contentPane = new JPanel(new BorderLayout());
		setContentPane(contentPane);
		csp = new CollapsibleSectionPanel();
		contentPane.add(csp);

		setJMenuBar(createMenuBar());

		statusBar = new StatusBar();
		statusBar.setLabel(GUI.TextCounter);
		contentPane.add(statusBar, BorderLayout.SOUTH);

		textArea = new RSyntaxTextArea(30, 100);
		textArea.setCodeFoldingEnabled(true);
		textArea.setAutoIndentEnabled(true);
		textArea.setTabSize(3);
		textArea.setUseSelectedTextColor(true);
		textArea.setMarkOccurrences(true);

		sp = new RTextScrollPane(textArea);
		csp.add(sp);

		ErrorStrip errorStrip = new ErrorStrip(textArea);
		contentPane.add(errorStrip, BorderLayout.LINE_END);

		LanguageSupportFactory.get().register(textArea);

		SpellChecker.register(textArea);

		SpellCheckerOptions sco = new SpellCheckerOptions();
		sco.setCaseSensitive(true);
		sco.setSuggestionsLimitMenu(10);
		JPopupMenu popup = SpellChecker.createCheckerPopup(sco);
		textArea.addMouseListener(new PopupListener(popup));

		setTitle("CheckSyntax Editor");
		setIconImage(new ImageIcon("image/icon.png").getImage());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);

		textArea.getDocument().addDocumentListener(new DocumentListener() {
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
				int lineCount = StringUtils.getWrappedLines(textArea);
				int textCount = textArea.getText().length();
				int wordCount = textArea.getText().split("\\s").length;
				GUI.TextCounter = "Col: " + textCount + " / "
						+ "Word: " + wordCount + " / "
						+ "Ln: " + lineCount;
				statusBar.setLabel(GUI.TextCounter);
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				String code;
				if ((code = textArea.getText()).isEmpty()) {
					code = GUI.CodeHolder;
					Client.Frame.sourceCode.setForeground(new Color(162, 162, 162));
				} else Client.Frame.sourceCode.setForeground(Color.black);
				Client.Frame.sourceCode.setText(code);
				Client.Frame.sourceCode.setFont(textArea.getFont());
				Client.Frame.sourceCode.setEnabled(true);
				Client.Frame._btnUpFile1.setEnabled(true);
				Client.Frame._btnLink.setEnabled(true);
				Client.Frame.selectedBox.setEnabled(true);
			}
		});
	}


	private void addItem(Action a, ButtonGroup bg, JMenu menu) {
		JRadioButtonMenuItem item = new JRadioButtonMenuItem(a);
		bg.add(item);
		menu.add(item);
	}


	private JMenuBar createMenuBar() {

		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("Search");
		menu.add(new JMenuItem(new ShowFindDialogAction()));
		menu.add(new JMenuItem(new ShowReplaceDialogAction()));
		menu.add(new JMenuItem(new GoToLineAction()));
		menu.addSeparator();

		int ctrl = getToolkit().getMenuShortcutKeyMask();
		int shift = InputEvent.SHIFT_MASK;
		KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_F, ctrl|shift);
		Action a = csp.addBottomComponent(ks, findToolBar);
		a.putValue(Action.NAME, "Show Find Search Bar");
		menu.add(new JMenuItem(a));

		ks = KeyStroke.getKeyStroke(KeyEvent.VK_H, ctrl|shift);
		a = csp.addBottomComponent(ks, replaceToolBar);
		a.putValue(Action.NAME, "Show Replace Search Bar");
		menu.add(new JMenuItem(a));

		JMenuItem hide = new JMenuItem("Hide bar");
		hide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				csp.hideBottomComponent();
			}
		});
		menu.add(hide);

		mb.add(menu);

		menu = new JMenu("Theme");
		ButtonGroup bg = new ButtonGroup();
		LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
		for (LookAndFeelInfo info : infos) {
			addItem(new LookAndFeelAction(info), bg, menu);
		}
		mb.add(menu);
		menu.addSeparator();
		JMenuItem font = new JMenuItem("Fonts setting");
		font.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FontDialog dialog = new FontDialog(Editor.this, "Setting editor fonts - CheckSyntax", true);
				dialog.setSelectedFont(textArea.getFont());
				dialog.setLocationRelativeTo(null);
				dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				dialog.setIconImage(new ImageIcon("image/icon.png").getImage());
				dialog.setVisible(true);
				if (!dialog.isCancelSelected()) {
					textArea.setFont(dialog.getSelectedFont());
					statusBar.setLabel("Changed font.");
				}
			}
		});
		menu.add(font);

		return mb;

	}


	@Override
	public String getSelectedText() {
		return textArea.getSelectedText();
	}


	/**
	 * Creates our Find and Replace dialogs.
	 */
	private void initSearchDialogs() {

		findDialog = new FindDialog(this, this);
		replaceDialog = new ReplaceDialog(this, this);

		// This ties the properties of the two dialogs together (match case,
		// regex, etc.).
		SearchContext context = findDialog.getSearchContext();
		replaceDialog.setSearchContext(context);

		// Create tool bars and tie their search contexts together also.
		findToolBar = new FindToolBar(this);
		findToolBar.setSearchContext(context);
		replaceToolBar = new ReplaceToolBar(this);
		replaceToolBar.setSearchContext(context);

	}


	/**
	 * Listens for events from our search dialogs and actually does the dirty
	 * work.
	 */
	@Override
	public void searchEvent(SearchEvent e) {

		SearchEvent.Type type = e.getType();
		SearchContext context = e.getSearchContext();
		SearchResult result;

		switch (type) {
			default: // Prevent FindBugs warning later
			case MARK_ALL:
				result = SearchEngine.markAll(textArea, context);
				break;
			case FIND:
				result = SearchEngine.find(textArea, context);
				if (!result.wasFound() || result.isWrapped()) {
					UIManager.getLookAndFeel().provideErrorFeedback(textArea);
				}
				break;
			case REPLACE:
				result = SearchEngine.replace(textArea, context);
				if (!result.wasFound() || result.isWrapped()) {
					UIManager.getLookAndFeel().provideErrorFeedback(textArea);
				}
				break;
			case REPLACE_ALL:
				result = SearchEngine.replaceAll(textArea, context);
				JOptionPane.showMessageDialog(null, result.getCount() +
						" occurrences replaced.");
				break;
		}

		String text;
		if (result.wasFound()) {
			text = "Text found; occurrences marked: " + result.getMarkedCount();
		}
		else if (type==SearchEvent.Type.MARK_ALL) {
			if (result.getMarkedCount()>0) {
				text = "Occurrences marked: " + result.getMarkedCount();
			}
			else {
				text = "";
			}
		}
		else {
			text = "Text not found";
		}
		statusBar.setLabel(text);
	}

    /**
     * Opens the "Go to Line" dialog.
     */
	private class GoToLineAction extends AbstractAction {

		GoToLineAction() {
			super("Go To Line...");
			int c = getToolkit().getMenuShortcutKeyMask();
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_L, c));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (findDialog.isVisible()) {
				findDialog.setVisible(false);
			}
			if (replaceDialog.isVisible()) {
				replaceDialog.setVisible(false);
			}
			GoToDialog dialog = new GoToDialog(Editor.this);
			dialog.setMaxLineNumberAllowed(textArea.getLineCount());
			dialog.setVisible(true);
			int line = dialog.getLineNumber();
			if (line>0) {
				try {
					textArea.setCaretPosition(textArea.getLineStartOffset(line-1));
				} catch (BadLocationException ble) { // Never happens
					UIManager.getLookAndFeel().provideErrorFeedback(textArea);
					ble.printStackTrace();
				}
			}
		}

	}


    /**
     * Changes the Look and Feel.
     */
	private class LookAndFeelAction extends AbstractAction {

		private LookAndFeelInfo info;

		LookAndFeelAction(LookAndFeelInfo info) {
			putValue(NAME, info.getName());
			this.info = info;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				TextArea temp = new TextArea();
				switch (info.getName()) {
					case "FlatLaf Dark":
						textArea.setSelectedTextColor(new Color(128, 135, 135));
						textArea.setCurrentLineHighlightColor(new Color(43, 166, 214));
						textArea.setCaretColor(Color.white);

						break;

					case "FlatLaf Darcula":
						textArea.setCurrentLineHighlightColor(new Color(102, 40, 201));
						textArea.setCaretColor(Color.white);
						break;

					default:
						textArea.setCurrentLineHighlightColor(new Color(255, 255, 170));
						textArea.setCaretColor(Color.black);
						break;
				}
				textArea.setForeground(temp.getForeground());
				textArea.setBackground(temp.getBackground());
				UIManager.setLookAndFeel(info.getClassName());
				SwingUtilities.updateComponentTreeUI(Editor.this);
				if (findDialog!=null) {
					findDialog.updateUI();
					replaceDialog.updateUI();
				}
				pack();
			} catch (RuntimeException re) {
				throw re; // FindBugs
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}


    /**
     * Shows the Find dialog.
     */
	private class ShowFindDialogAction extends AbstractAction {

		ShowFindDialogAction() {
			super("Find...");
			findDialog.setIconImage(new ImageIcon("image/icon.png").getImage());
			int c = getToolkit().getMenuShortcutKeyMask();
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F, c));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (replaceDialog.isVisible()) {
				replaceDialog.setVisible(false);
			}
			findDialog.setVisible(true);
		}

	}


    /**
     * Shows the Replace dialog.
     */
	private class ShowReplaceDialogAction extends AbstractAction {

		ShowReplaceDialogAction() {
			super("Replace...");
			replaceDialog.setIconImage(new ImageIcon("image/icon.png").getImage());
			int c = getToolkit().getMenuShortcutKeyMask();
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_H, c));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (findDialog.isVisible()) {
				findDialog.setVisible(false);
			}
			replaceDialog.setVisible(true);
		}

	}


    /**
     * The status bar for this application.
     */
	private static class StatusBar extends JPanel {

		private final JLabel label;

		StatusBar() {
			label = new JLabel();
			setLayout(new BorderLayout());
			setBorder(new EmptyBorder(0, 5, 0, 0));
			add(label, BorderLayout.LINE_START);
			add(new JLabel(new SizeGripIcon()), BorderLayout.LINE_END);
		}

		void setLabel(String label) {
			this.label.setText(label);
		}
	}

	public void setSourceCode(String text) {
		if (text.equalsIgnoreCase(GUI.CodeHolder))
			text = "";
		textArea.setText(text);
	}

	public void setSourceFont(Font font) {
		textArea.setFont(font);
	}

	public void setSourceLanguage(String language) {
		textArea.setSyntaxEditingStyle(language);
	}
}
