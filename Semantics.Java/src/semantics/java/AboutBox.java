package semantics.java;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AboutBox extends JDialog{
	JTextArea textArea = new JTextArea();
	JButton btnClose = new JButton("Close");
	public AboutBox() {
		this.setSize(500, 300);
		getContentPane().setLayout(null);
		
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		btnClose.setBounds(190, 211, 93, 23);
		getContentPane().add(btnClose);
		
		textArea.setBounds(27, 51, 393, 118);
		getContentPane().add(textArea);
		
		JLabel lblCurrentDirectory = new JLabel("Current Directory:");
		lblCurrentDirectory.setBounds(26, 31, 121, 15);
		getContentPane().add(lblCurrentDirectory);
		
		this.textArea.setText(System.getProperty("user.dir"));
		this.textArea.setLineWrap(true);
	}
	
	void close() {
		this.dispose();
	}
}
