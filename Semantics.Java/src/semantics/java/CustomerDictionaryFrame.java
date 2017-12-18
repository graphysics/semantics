package semantics.java;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import semantics.java.api.*;

import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.awt.event.ActionEvent;

public class CustomerDictionaryFrame extends JFrame{
	private JTable table;
	private JTextField textNewWord;
	private JTextField textCategory;
	private JTextField textSimilar;
	public CustomerDictionaryFrame() {
		setTitle("Customer Dictionary");
		this.setSize(800, 600);
		getContentPane().setLayout(null);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setPreferredScrollableViewportSize(new Dimension(20, 400));
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLocation(22, 97);
		scrollPane.setSize(600, 400);
		scrollPane.setViewportView(table);
		getContentPane().add(scrollPane);
		
		textNewWord = new JTextField();
		textNewWord.setBounds(80, 10, 174, 21);
		getContentPane().add(textNewWord);
		textNewWord.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("\u8BCD\u8BED");
		lblNewLabel.setBounds(16, 13, 54, 15);
		getContentPane().add(lblNewLabel);
		
		textCategory = new JTextField();
		textCategory.setBounds(80, 38, 328, 21);
		getContentPane().add(textCategory);
		textCategory.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("\u7C7B\u578B");
		lblNewLabel_1.setBounds(16, 41, 54, 15);
		getContentPane().add(lblNewLabel_1);
		
		JLabel label = new JLabel("新词的类型，如‘特朗普’的类型为‘个人’");
		label.setBounds(423, 41, 314, 15);
		getContentPane().add(label);
		
		JButton btnRegisterWord = new JButton("注册新词");
		btnRegisterWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doRegisterWord();
			}
		});
		btnRegisterWord.setBounds(280, 9, 127, 23);
		getContentPane().add(btnRegisterWord);
		
		JLabel label_1 = new JLabel("\u76F8\u4F3C\u8BCD");
		label_1.setBounds(16, 69, 54, 15);
		getContentPane().add(label_1);
		
		textSimilar = new JTextField();
		textSimilar.setColumns(10);
		textSimilar.setBounds(80, 66, 328, 21);
		getContentPane().add(textSimilar);
		
		JLabel label_2 = new JLabel("相似词指词义接近或相同，并且用法相同的词语");
		label_2.setBounds(423, 69, 314, 15);
		getContentPane().add(label_2);
		this.showDict();
	}
	
	void showDict() {
		List<KeyValuePair> words = null;
		try {
			words = SemanticAPI.getSemanticAPI().GetCustomerDictionary();
			Object[][] data = new Object[words.size()][2];
			for (int i = 0; i < words.size(); i++) {
				Object pair = words.get(i);
				LinkedHashMap map = (LinkedHashMap) pair;
				data[i][0] = map.get("Key");
				data[i][1] = map.get("Value");
			}
			DefaultTableModel model = new DefaultTableModel(data, new String[] { "Id", "Sentence" });
			table.setModel(model);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.toString());
		}
	}
	
	void doRegisterWord() {
		try {
			Aesult result = SemanticAPI.getSemanticAPI().RegisterWord(this.textNewWord.getText(), this.textCategory.getText(), this.textSimilar.getText());
			if(result.IsSuccess)
				JOptionPane.showMessageDialog(this, "词语注册成功");
			else
			{
				String msg =  "词语注册失败，";
				if(result.Failure!=null)
					msg +=result.Failure.Type + ":" + result.Failure.Message;
				JOptionPane.showMessageDialog(this, msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.toString());
		}
	}
}
