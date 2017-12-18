package semantics.java;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import semantics.java.api.Aoken;
import semantics.java.api.SemanticAPI;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class LoginDialog extends JDialog{
	private JTextField txtClientName;
	private JTextField txtPassword;
	public LoginDialog() {
		setTitle("Client Login");
		this.setSize(500, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Client Name");
		lblNewLabel.setBounds(111, 56, 105, 15);
		getContentPane().add(lblNewLabel);
		
		txtClientName = new JTextField();
		txtClientName.setText("vip@fundsea.cn");
		txtClientName.setBounds(213, 53, 105, 21);
		getContentPane().add(txtClientName);
		txtClientName.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(111, 94, 80, 15);
		getContentPane().add(lblNewLabel_1);
		
		txtPassword = new JTextField();
		txtPassword.setText("vip");
		txtPassword.setBounds(213, 91, 105, 21);
		getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doLogin();
			}
		});
		btnLogin.setBounds(186, 161, 93, 23);
		getContentPane().add(btnLogin);
	}
	
	void doLogin() {
		String username = this.txtClientName.getText();
		String password = this.txtPassword.getText();
		try {
			Aoken t = SemanticAPI.getSemanticAPI().Login(username, password);
			if(t!=null) {
				SemanticAPI.getSemanticAPI().getSession().assignToken(t);
				this.result = true;
			}
			this.dispatchEvent(new WindowEvent(this,WindowEvent.WINDOW_CLOSING) );
		} catch (Exception exp) {
			exp.printStackTrace();
			JOptionPane.showMessageDialog(this, "Expcetion:" + exp);
		}
		
	}

	boolean result = false;
	public boolean getResult() {
		return result;
	}
}
