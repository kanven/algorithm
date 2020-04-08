package com.kanven.algorithm.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 二叉搜索树
 * 
 * @author kanven
 *
 * @param <E>
 */
public class BST<E extends Comparable<E>> {

	protected Node<E> root;

	private int size;

	public BST() {

	}

	public void add(E value) {
		if (value == null) {
			return;
		}
		if (root == null) {
			this.root = createNode(value);
			afterAdd(root);
		} else {
			Node<E> node = createNode(value);
			int rs = 0;
			Node<E> parent;
			Node<E> cursor = root;
			do {
				parent = cursor;
				rs = cursor.compareTo(node);
				if (rs > 0) {
					cursor = cursor.left();
				} else if (rs < 0) {
					cursor = cursor.right();
				} else {
					return;
				}
			} while (cursor != null);
			node.parent(parent);
			if (rs > 0) {
				parent.left(node);
			} else {
				parent.right(node);
			}
			afterAdd(node);
		}
		size++;
	}

	protected Node<E> createNode(E value) {
		return new Node<E>(value);
	}

	protected void afterAdd(Node<E> node) {

	}

	public boolean contain(E value) {
		if (value == null) {
			return false;
		}
		return search(value) == null ? false : true;
	}

	private Node<E> search(E value) {
		Node<E> cursor = root;
		while (cursor != null) {
			int rs = cursor.value().compareTo(value);
			if (rs == 0) {
				break;
			}
			if (rs > 0) {
				cursor = cursor.left();
			} else {
				cursor = cursor.right();
			}
		}
		return cursor;
	}

	/**
	 * 删除指定值的节点
	 * 
	 * @param value
	 */
	public void remove(E value) {
		// 查找节点
		Node<E> node = search(value);
		if (node == null) {
			return;
		}
		// 1、度为0，即节点为叶子节点，则直接删除
		if (node.left() == null && node.right() == null) {
			Node<E> parent = node.parent();
			node.remove();
			afterRemove(parent);
		} else if (node.left() != null && node.right() != null) {
			// 2、度为2，即节点有左子树、右子树
			Node<E> prede = predecessor(node);
			Node<E> parent = prede.parent();
			// 删除前继节点，注意：需要对前继节点左子树进行处理
			Node<E> left = prede.left();
			if (left != null) {
				left.parent(parent);
			}
			// 设置前继节点的左子树为其父节点的孩子节点
			if (parent.left().equals(prede)) {
				parent.left(left);
			}
			if (parent.right().equals(left)) {
				parent.right(left);
			}
			// 更改节点值
			node.value(prede.value());
			// 释放前继节点
			prede.remove();
			afterRemove(parent);
		} else {
			// 3、度为1，即节点只有左子树或右子树
			Node<E> parent = node.parent();
			Node<E> child = null;
			if (node.left() != null) {
				// 存在左子树
				child = node.left();
				// 设置父节点
				child.parent(parent);
			} else {
				// 存在右子树
				child = node.right();
				// 设置父节点
				child.parent(parent);
			}
			// 设置后续节点与父节点关系

			// 删除的是根节点
			if (parent == null) {
				root = child;
			} else {
				// 删除的不是根节点
				if (node.equals(parent.left())) {
					parent.left(child);
				} else {
					parent.right(child);
				}
			}
			// 删除节点
			node.remove();
			afterRemove(parent);
		}
		size--;
	}

	protected void afterRemove(Node<E> node) {

	}

	/**
	 * 获取节点前继节点 <br>
	 * 前继节点是中序遍历形成的序列中节点的前一个节点，譬如中序遍历后形成的序列：1，3，5，8，9，10，则9的前继节点为8 </br>
	 * <br>
	 * 在二叉搜索树中查找的方式是：该节点的左子树中的最右节点 </br>
	 * 
	 * @param node
	 * @return
	 */
	Node<E> predecessor(Node<E> node) {
		Node<E> cursor = node.left();
		Node<E> parent = null;
		while (cursor != null) {
			parent = cursor;
			cursor = cursor.right();
		}
		return parent;
	}

	/**
	 * 获取节点后继节点 <br>
	 * 后继节点是中序遍历形成的序列中节点的后一个节点，譬如中序遍历后形成的序列：1，3，5，8，9，10，则9的后继节点为10 </br>
	 * <br>
	 * 在二叉搜索树中查找的方式是：该节点的右子树中的最左节点 </br>
	 * 
	 * @param node
	 * @return
	 */
	Node<E> successor(Node<E> node) {
		Node<E> cursor = node.right();
		Node<E> parent = null;
		while (cursor != null) {
			parent = cursor;
			cursor = cursor.left();
		}
		return parent;
	}

	/**
	 * 中序遍历 <br>
	 * 前序遍历：父节点、左子树、右子树 </br>
	 * <br>
	 * 中序遍历：左子树、父节点、右子树 </br>
	 * <br>
	 * 后序遍历:左子树、右子树、父节点 </br>
	 * 
	 * @return
	 */
	List<E> traversal() {
		List<E> elements = new ArrayList<E>(size);
		// 使用栈来代替递归实现
		Stack<Node<E>> stack = new Stack<>();
		Node<E> cursor = root;
		while (cursor != null || !stack.isEmpty()) {
			if (cursor != null) {
				stack.push(cursor);
				cursor = cursor.left();
			} else {
				Node<E> node = stack.pop();
				elements.add(node.value());
				cursor = node.right();
			}
		}
		return elements;
	}

	public static void main(String[] args) {
		int[] els = { 6, 1, 4, 2, 3, 7, 5, 10, 9, 8 };
		BST<Integer> bst = new BST<>();
		for (int e : els) {
			bst.add(e);
		}
		List<Integer> items = bst.traversal();
		for (Integer item : items) {
			System.out.println(item);
		}
		System.out.println("====================");
		System.out.println(bst.contain(4));
		System.out.println("====================");
		bst.remove(3);
		items = bst.traversal();
		for (Integer item : items) {
			System.out.println(item);
		}
		System.out.println("====================");
		bst.remove(6);
		items = bst.traversal();
		for (Integer item : items) {
			System.out.println(item);
		}
	}

}
