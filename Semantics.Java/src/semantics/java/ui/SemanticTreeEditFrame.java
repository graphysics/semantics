package semantics.java.ui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import semantics.java.api.*;
import semantics.java.ui.TreeTransferHandler.IDragDroppedListener;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SemanticTreeEditFrame extends JDialog {
	private ArrayList<Aode> Nodes;
	private ParseCase ParseCase;

	public SemanticTreeEditFrame(ParseCase parsecase, ArrayList<Aode> nodes) {
		this();
		this.ParseCase = parsecase;
		this.Nodes = nodes;
		this.showAodes();
	}

	private final JToolBar toolBar = new JToolBar();
	JButton jbuttonOpen = new JButton("Open");
	JButton jbuttonSave = new JButton("Submit");
	JButton jbuttonExit = new JButton("Exit");
	GrammarTree tree = new GrammarTree();

	public SemanticTreeEditFrame() {

		this.setSize(600, 600);
		getContentPane().add(tree, BorderLayout.CENTER);
		getContentPane().add(toolBar, BorderLayout.NORTH);
		toolBar.add(jbuttonOpen);
		jbuttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSubmit();
			}
		});
		toolBar.add(jbuttonSave);
		toolBar.add(jbuttonExit);
	}
	
	void doSubmit() {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)this.tree.getModel().getRoot();
		if(root.getChildCount()!=1) {
			JOptionPane.showMessageDialog(this, "Please finish edit/disambuation before submit!");
			return;
		}
		CheckNode phrase = (CheckNode)root.getFirstChild();
		Aode node = (Aode)phrase.tag;
		try {
			SemanticAPI.getSemanticAPI().getParser().TutorPhrase(this.ParseCase.CaseId, node);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Expcetion:" + e);
		}
	}

	private void showAodes() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Ambiguous!");
		for (int i = 0; i < this.Nodes.size(); i++) {
			Aode node = this.Nodes.get(i);
			root.add(this.makeTreeNode(node));
		}
		DefaultTreeModel model = new DefaultTreeModel(root);
		this.tree.setModel(model);
		tree.setDragEnabled(true);
		tree.setDropMode(DropMode.ON_OR_INSERT);
		TreeTransferHandler handler = new TreeTransferHandler();
		handler.addDragDroppedListener(new IDragDroppedListener() {

			@Override
			public void onDragDropped(JTree tree, DefaultMutableTreeNode[] movedNodes) {
				onNodeMoved(movedNodes[0]);				
			}
			
		});
		tree.setTransferHandler(handler);
		tree.expandTree();
	}
	
	void onNodeMoved(DefaultMutableTreeNode movedTreeNode) {
		try {
			CheckNode checknode = (CheckNode)movedTreeNode;
			Aode movedAode = (Aode)checknode.tag;
			CheckNode target = (CheckNode)checknode.getParent();
			Aode targetAode = (Aode)target.tag;
			if(movedAode.getParent()!=null)
				movedAode.getParent().removeChild(movedAode);
			targetAode.addChild(movedAode);
			while(target.getParent()!=null) {
				Aode targetParent = (Aode)target.tag;
				targetParent.Label += movedAode.Label;
				target.setUserObject(targetParent.Label);
				TreeNode parent = target.getParent(); 
				if(!parent.getClass().equals(CheckNode.class))
					break;
				target = (CheckNode)parent;
			}
			this.tree.invalidate();
		}
		catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Expcetion:" + e);
		}
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
