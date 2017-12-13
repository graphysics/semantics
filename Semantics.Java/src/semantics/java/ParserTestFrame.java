package semantics.java;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import semantics.java.api.*;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ParserTestFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTree tree;

	public ParserTestFrame() {
		this.setTitle("Parser Demon");
		this.setSize(800, 600);
		getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(0, 0, 784, 21);
		this.getContentPane().add(textField);
		textField.setColumns(10);

		tree = new JTree();
		tree.setBounds(0, 21, 300, 540);
		this.getContentPane().add(tree);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(332, 82, 105, 21);
		getContentPane().add(menuBar);

		JMenuItem mntmNewMenuItem = new JMenuItem("MenuItem2");
		menuBar.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("New menu item");
		menuBar.add(mntmNewMenuItem_1);

		JButton btnParse = new JButton("Parse");
		btnParse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doParse();
			}
		});
		btnParse.setBounds(301, 41, 93, 23);
		getContentPane().add(btnParse);
	}

	void doParse() {
		try {
			String input = textField.getText();
			String words = input.trim();
			if (words.length() != input.length())
				textField.setText(words);
			if (words.length() == 0) {
				JOptionPane.showMessageDialog(this, "«Î ‰»Î”Ôæ‰");
			}
			ISemanticParser parser = SemanticAPI.getSemanticAPI().getParser();
			APINode result = parser.Parse(words);
			this.showAPINode(result);
		} catch (Exception exp) {
			exp.printStackTrace();
			JOptionPane.showMessageDialog(this, "Expcetion:" + exp);
		}
	}

	private void showAPINode(APINode result) {
		DefaultMutableTreeNode root = makeTreeNode(result);
		DefaultTreeModel  model = new DefaultTreeModel(root);
		this.tree.setModel(model);		
	}
	
	DefaultMutableTreeNode makeTreeNode(APINode node) {
		DefaultMutableTreeNode treenode = new DefaultMutableTreeNode(node.Label);
		if(node.Children!=null) {
			for(int i=0;i< node.Children.size();i++) {	
			APINode child = node.Children.get(i);
			DefaultMutableTreeNode childtreenode = makeTreeNode(child);
			treenode.add(childtreenode);
			}
		}
		return treenode;
	}
}
