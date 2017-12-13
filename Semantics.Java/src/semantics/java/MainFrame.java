package semantics.java;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import semantics.java.api.SemanticAPI;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;
	private JMenu jMenu = null;
	private JMenuItem jMenuItemExit = null;
	private JMenu jMenuTest = null;
	private JMenuItem jMenuItemTestFrontEnd = null;

	private ParserTestFrame parserTestFrame;
	private JMenuItem jMenuItemTestSphinx = null;
	private JMenuItem jMenuItemTestCreateRecognizer = null;
	private JMenuItem jMenuItemTestCreateFrontEnd = null;
	private JTextField txtUrl;
	/**
	 * This is the default constructor
	 */
	public MainFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(767, 472);
		this.setJMenuBar(getJJMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("Semantics API Demonstration");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getTxtUrl(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getJMenu());
			jJMenuBar.add(getJMenuTest());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenu() {
		if (jMenu == null) {
			jMenu = new JMenu();
			jMenu.setText("文件");
			jMenu.add(getJMenuItemExit());
		}
		return jMenu;
	}

	/**
	 * This method initializes jMenuItemExit	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemExit() {
		if (jMenuItemExit == null) {
			jMenuItemExit = new JMenuItem();
			jMenuItemExit.setText("退出");
		}
		return jMenuItemExit;
	}

	/**
	 * This method initializes jMenuTest	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuTest() {
		if (jMenuTest == null) {
			jMenuTest = new JMenu();
			jMenuTest.setText("演示");
			jMenuTest.add(getJMenuItemTestParser());
//			jMenuTest.add(getJMenuItemTestCreateRecognizer());
//			jMenuTest.add(getJMenuItemTestCreateFrontEnd());
		}
		return jMenuTest;
	}

	private ParserTestFrame getParserTestFrame(){
		if(this.parserTestFrame==null){
			this.parserTestFrame=new ParserTestFrame();
		}
		return this.parserTestFrame;
	}
	/**
	 * This method initializes jMenuItemTestFrontEnd	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemTestParser() {
		if (jMenuItemTestFrontEnd == null) {
			jMenuItemTestFrontEnd = new JMenuItem();
			jMenuItemTestFrontEnd.setText("Parser");
			jMenuItemTestFrontEnd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					demoParser();
				}
			});
		}
		return jMenuItemTestFrontEnd;
	}


	void demoParser() {
		SemanticAPI.getSemanticAPI().setServiceUrl(this.getTxtUrl().getText());
		ParserTestFrame testparserframe = new ParserTestFrame();
		testparserframe.setVisible(true);
		
	}

	private JTextField getTxtUrl() {
		if (txtUrl == null) {
			txtUrl = new JTextField();
			txtUrl.setText("http://localhost:8080/");
			txtUrl.setColumns(10);
		}
		return txtUrl;
	}
}  

