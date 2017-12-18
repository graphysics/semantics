package semantics.java;

import java.awt.BorderLayout;
import javax.swing.*;

import semantics.java.api.SemanticAPI;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;
	private JMenu jMenu = null;
	private JMenuItem jMenuItemExit = null;
	private JMenu jMenuTest = null;
	private JMenuItem jMenuItemCustomerDictionary = null;

	private ParserTestFrame parserTestFrame;
	private JMenuItem jMenuItemTestSphinx = null;
	private JMenuItem jMenuItemTestCreateRecognizer = null;
	private JMenuItem jMenuItemTestCreateFrontEnd = null;
	private JTextField txtUrl;
	private JLabel lblNewLabel;
	private JMenuItem menuItemParser;
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
			jContentPane.setLayout(null);
			jContentPane.add(getTxtUrl());
			jContentPane.add(getLblNewLabel());
			
			JRadioButton rdbtnServer = new JRadioButton("NLP Server");
			rdbtnServer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setUrl("nlp.fundsea.cn");
				}
			});
			rdbtnServer.setBounds(6, 65, 121, 23);
			jContentPane.add(rdbtnServer);
			
			JRadioButton rdbtnLocal = new JRadioButton("local");
			rdbtnLocal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setUrl("localhost");
				}
			});
			rdbtnLocal.setBounds(160, 65, 121, 23);
			jContentPane.add(rdbtnLocal);
			ButtonGroup group = new ButtonGroup();
			group.add(rdbtnLocal);
			group.add(rdbtnServer);
		}
		return jContentPane;
	}

	void setUrl(String service) {
		this.txtUrl.setText("http://"+ service + ":10085/");
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
			jMenuTest.add(getMenuItemParser());
			jMenuTest.add(getJMenuItemCustomerDictionary());
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
	 * This method initializes jMenuItemCustomerDictionary	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemCustomerDictionary() {
		if (jMenuItemCustomerDictionary == null) {
			jMenuItemCustomerDictionary = new JMenuItem();
			jMenuItemCustomerDictionary.setText("Customer Dictionary");
			jMenuItemCustomerDictionary.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					demoCustomerDictionary();
				}
			});
		}
		return jMenuItemCustomerDictionary;
	}

	void demoCustomerDictionary() {
		if(!verifyLogin())
			return;
		CustomerDictionaryFrame frame = new CustomerDictionaryFrame();
		frame.setVisible(true);
	}

	void demoParser() {
		if(!verifyLogin())
			return;
		ParserTestFrame testparserframe = new ParserTestFrame();
		testparserframe.setVisible(true);
		
	}

	boolean verifyLogin() {
		
		SemanticAPI.getSemanticAPI().setServiceUrl(this.getTxtUrl().getText());
		try {
			if(SemanticAPI.getSemanticAPI().getSession().getToken()==null) {
				if(!this.doLogin())
					return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Expcetion:" + e);
			return false;
		}
		return true;
	}
	private boolean doLogin() {
		SemanticAPI.getSemanticAPI().setServiceUrl(this.txtUrl.getText());
		LoginDialog login = new LoginDialog();
		login.setModal(true);
		login.setVisible(true);
		return login.getResult();
	}

	private JTextField getTxtUrl() {
		if (txtUrl == null) {
			txtUrl = new JTextField();
			txtUrl.setBounds(0, 31, 751, 21);
			txtUrl.setText("http://nlp.fundsea.cn:10085/");
			txtUrl.setColumns(10);
		}
		return txtUrl;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Semantics API Service");
			lblNewLabel.setBounds(0, 10, 153, 15);
		}
		return lblNewLabel;
	}
	private JMenuItem getMenuItemParser() {
		if (menuItemParser == null) {
			menuItemParser = new JMenuItem();
			menuItemParser.setText("Parser");
			menuItemParser.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					demoParser();
				}
			});
		}
		return menuItemParser;
	}
}  

