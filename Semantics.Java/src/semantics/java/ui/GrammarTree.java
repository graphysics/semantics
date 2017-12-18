package semantics.java.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.tree.*;

@SuppressWarnings("serial")
public class GrammarTree extends JTree {

	public GrammarTree() {
		this.setCellRenderer(new CheckRenderer());
		this.addMouseListener(new NodeSelectionListener(this));
	}

	/**
	 * ��ȫչ��һ��JTree
	 * 
	 * @param tree
	 *            JTree
	 */
	public void expandTree() {
		TreeNode root = (TreeNode) this.getModel().getRoot();
		expandAll(new TreePath(root), true);
	}

	class NodeSelectionListener extends MouseAdapter {
		JTree tree;

		NodeSelectionListener(JTree tree) {
			this.tree = tree;
		}

		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			int row = tree.getRowForLocation(x, y);
			TreePath path = tree.getPathForRow(row);
			// TreePath path = tree.getSelectionPath();
			if (path != null) {
				CheckNode node = (CheckNode) path.getLastPathComponent();
				boolean isSelected = !(node.isSelected());
				node.setSelected(isSelected);
				if (node.getSelectionMode() == CheckNode.DIG_IN_SELECTION) {
					if (isSelected) {
						tree.expandPath(path);
					} else {
						tree.collapsePath(path);
					}
				}
				((DefaultTreeModel) tree.getModel()).nodeChanged(node);
				// I need revalidate if node is root. but why?
				if (row == 0) {
					tree.revalidate();
					tree.repaint();
				}
			}
		}
	}

	/**
	 * ��ȫչ����ر�һ����,���ڵݹ�ִ��
	 * 
	 * @param tree
	 *            JTree
	 * @param parent
	 *            ���ڵ�
	 * @param expand
	 *            Ϊtrue���ʾչ����,����Ϊ�ر�������
	 */
	public void expandAll(TreePath parent, boolean expand) {
		// Traverse children
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				this.expandAll(path, expand);
			}
		}

		// Expansion or collapse must be done bottom-up
		if (expand) {
			this.expandPath(parent);
		} else {
			this.collapsePath(parent);
		}
	}
}
