package semantics.java;

import semantics.java.ui.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import semantics.java.api.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;

public class ParserTestFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private GrammarTree tree;
	JButton btnParse;
	private JTable table;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel;
	private JButton buttonTutor;
	private JLabel lblAction;
	JPanel panelOutput;

	public ParserTestFrame() {
		this.setTitle("Parser Demon");
		this.setSize(1200, 600);
		getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(81, 11, 578, 21);
		this.getContentPane().add(textField);
		textField.setColumns(10);

		tree = new GrammarTree();
		tree.setBounds(462, 97, 300, 453);
		this.getContentPane().add(tree);
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("empty")));

		btnParse = new JButton("Parse");
		btnParse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doParse();
			}
		});
		btnParse.setBounds(681, 10, 93, 23);
		getContentPane().add(btnParse);

		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setPreferredScrollableViewportSize(new Dimension(420, 200));
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selected = table.getSelectionModel().getMinSelectionIndex();
				if (selected >= 0) {
					String sentence = (String) table.getModel().getValueAt(selected, 1);
					textField.setText(sentence);
				}

			}

		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLocation(22, 97);
		scrollPane.setSize(400, 454);
		scrollPane.setViewportView(table);
		getContentPane().add(scrollPane);

		lblNewLabel_1 = new JLabel(
				"Java应用中某些输入法中文输入可能有问题。如不能输入中文，请在别的应用(如记事本)中输入中文后拷贝复制过来。");
		lblNewLabel_1.setBounds(60, 39, 633, 21);
		getContentPane().add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("Copus");
		lblNewLabel_2.setBounds(22, 82, 68, 15);
		getContentPane().add(lblNewLabel_2);

		lblNewLabel_3 = new JLabel("Parsed Tree");
		lblNewLabel_3.setBounds(462, 82, 81, 15);
		getContentPane().add(lblNewLabel_3);

		lblNewLabel = new JLabel("请输入语句");
		lblNewLabel.setBounds(10, 14, 81, 15);
		getContentPane().add(lblNewLabel);
		
		buttonTutor = new JButton(" \u6307\u6B63");
		buttonTutor.setEnabled(false);
		buttonTutor.setBounds(694, 72, 68, 23);
		getContentPane().add(buttonTutor);
		buttonTutor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doTutor();
			}
		});
		
		lblAction = new JLabel("");
		lblAction.setForeground(Color.RED);
		lblAction.setBounds(134, 59, 525, 15);
		getContentPane().add(lblAction);
		
		JLabel lblNewLabel_4 = new JLabel("JSON");
		lblNewLabel_4.setBounds(787, 82, 54, 15);
		getContentPane().add(lblNewLabel_4);
		
		textAreaJson = new JTextArea();
		textAreaJson.setBounds(797, 97, 377, 453);
		textAreaJson.setLineWrap(true);
	    panelOutput = new JPanel();
	    panelOutput.setBounds(785, 97, 389, 453);
	    panelOutput.add(new JScrollPane(textAreaJson));
	    
		getContentPane().add(panelOutput);
		this.showCorpus();

	}

	void showCorpus() {
		List<KeyValuePair> sentences;
		try {
			sentences = SemanticAPI.getSemanticAPI().GetCorpusSentences();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.toString());
			return;
		}
		Object[][] data = new Object[sentences.size()][2];
		for (int i = 0; i < sentences.size(); i++) {
			Object pair = sentences.get(i);
			LinkedHashMap map = (LinkedHashMap) pair;
			data[i][0] = map.get("Key");
			data[i][1] = map.get("Value");
		}
		DefaultTableModel model = new DefaultTableModel(data, new String[] { "Id", "Sentence" });
		table.setModel(model);
		TableColumn column  = table.getColumnModel().getColumn(1);
		column.setPreferredWidth(100);
	}

	ParseCase currentCase;
	private JTextArea textAreaJson;
	void doParse() {
		try {
			String input = textField.getText();
			String words = input.trim();
			if (words.length() != input.length())
				textField.setText(words);
			if (words.length() == 0) {
				JOptionPane.showMessageDialog(this, "请输入语句");
			}
			this.buttonTutor.setEnabled(false);
			this.lblAction.setText("");
			ISemanticParser parser = SemanticAPI.getSemanticAPI().getParser();
			this.currentCase = parser.Parse(words);
			this.onParseResult();
		} catch (Exception exp) {
			exp.printStackTrace();
			JOptionPane.showMessageDialog(this, "Expcetion:" + exp);
		}
	}

	void onParseResult() throws Exception {
		this.textAreaJson.setText(this.currentCase.getJson());
		if (currentCase.IsSuccess)
			this.showAPINode(currentCase.Result);
		else if (currentCase.AmbiguousResults != null) {
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("Ambiguous!");
			for (int i = 0; i < currentCase.AmbiguousResults.size(); i++) {
				Aode node = currentCase.AmbiguousResults.get(i);
				node.rectifyHierarchy(1);
				root.add(this.makeTreeNode(node));
			}
			DefaultTreeModel model = new DefaultTreeModel(root);
			this.tree.setModel(model);
	        tree.setDragEnabled(true);
	        tree.setDropMode(DropMode.ON_OR_INSERT);
	        tree.setTransferHandler(new TreeTransferHandler());
			this.buttonTutor.setEnabled(true);
			this.lblAction.setText("发现不能解析的句法歧义！请选择有效的短语来指正。");
		}else if(currentCase.WordSenseAmbiguities!=null) {		
			String usecase =  currentCase.WordSenseAmbiguities.get(0).UseCase;
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("Use case:" + usecase);
			for(int j=0;j<currentCase.WordSenseAmbiguities.size();j++){
				WordSenseAmbiguity wsa =currentCase.WordSenseAmbiguities.get(j);
				DefaultMutableTreeNode wordnode = new DefaultMutableTreeNode(wsa.Word);
				root.add(wordnode);
				for (int i = 0; i < wsa.Ambiguities.size(); i++) {
					Sense sense = wsa.Ambiguities.get(i);
					CheckNode treenode = new CheckNode(sense);
					wordnode.add(treenode);
				}
			}
			DefaultTreeModel model = new DefaultTreeModel(root);
			this.tree.setModel(model);
			JOptionPane.showMessageDialog(this, currentCase.Failure.Type + ":" + currentCase.Failure.Message);
			this.buttonTutor.setEnabled(true);
			this.lblAction.setText("发现不能处理的多义词歧义！请选择有效的词义来指正。");
		}
		else {
			this.tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Failed!")));
			if (currentCase.Failure != null) {
				JOptionPane.showMessageDialog(this, currentCase.Failure.Message);
			}
		}
		this.tree.expandTree();
	}
	private void doTutor() {
		try {
			if(this.currentCase.FailureType== ParseCase.EnumFailureType.PhraseStructureAmbiguities){
				this.tutorPhraseStructure();
				return;
			}
			int[] senseids = new int[this.currentCase.WordSenseAmbiguities.size()];
			for(int i=0;i<senseids.length;i++)
				senseids[i]= -1;
			DefaultMutableTreeNode root = (DefaultMutableTreeNode)this.tree.getModel().getRoot();
			
			DefaultMutableTreeNode word = (DefaultMutableTreeNode)root.getFirstChild();
			int i = 0;
			while(word!=null) {
				CheckNode sensenode = (CheckNode)word.getFirstChild();
				Sense selected = null;
				while(sensenode!=null) {
					if(sensenode.isSelected()) {
						if(selected!=null)
							throw new Exception("Multiple sense selected!");
						selected = (Sense)sensenode.getUserObject();
					}
					sensenode = (CheckNode)sensenode.getNextSibling();
				}
				if(selected==null){
							JOptionPane.showMessageDialog(this, "请选择一个词义作为有效词义！");
							return;
						}
				senseids[i] = selected.Id;
				i++;
				word = word.getNextSibling();
			}
			ISemanticParser parser = SemanticAPI.getSemanticAPI().getParser();
			String s = String.format("%d",  senseids[0]);
			for(i=1;i<senseids.length;i++)
				s +=","+senseids[i];
			this.currentCase = parser.TutorWSD(this.currentCase.CaseId, this.currentCase.Failure.Type, s);
			if(this.currentCase!=null)
				JOptionPane.showMessageDialog(this, "Tutor successed!");
			this.onParseResult();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Expcetion:" + e);
		}
	}

	private void tutorPhraseStructure() {
		ArrayList<Aode> nodes = new ArrayList<Aode>();
		for(Aode node : this.currentCase.AmbiguousResults)
			nodes.add(node.copy());
		SemanticTreeEditFrame editor = new SemanticTreeEditFrame(this.currentCase, nodes); 
		editor.setVisible(true);
	}

	private void showAPINode(Aode result) {
		DefaultMutableTreeNode root = null;
		if (result == null) {
			root = new DefaultMutableTreeNode("Parse failed!");
		} else
			root = makeTreeNode(result);
		DefaultTreeModel model = new DefaultTreeModel(root);
		this.tree.setModel(model);
		this.tree.expandTree();
	}

	CheckNode makeTreeNode(Aode node) {
		CheckNode treenode = new CheckNode(node.Label);
		treenode.tag = node;
		if (node.Children != null) {
			for (int i = 0; i < node.Children.size(); i++) {
				Aode child = node.Children.get(i);
				CheckNode childtreenode = makeTreeNode(child);
				treenode.add(childtreenode);
			}
		}
		return treenode;
	}
}
