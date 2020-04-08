package com.kanven.algorithm.tree;

import java.util.List;

/**
 * 平衡二叉树
 * 
 * @author kanven
 *
 * @param <E>
 */
public class AVL<E extends Comparable<E>> extends BST<E> {

	private AVL() {

	}

	@Override
	protected Node<E> createNode(E value) {
		return new AVLNode<E>(value);
	}

	@Override
	protected void afterAdd(Node<E> node) {
		// node节点是新增的节点
		Node<E> cursor = node;
		while ((cursor = cursor.parent()) != null) {
			AVLNode<E> parent = (AVLNode<E>) cursor;
			if (parent.isBalance()) {
				// 平衡，更新高度
				if (!parent.updateHigh()) {
					// 高度无变化，不需要往上继续操作
					break;
				}
			} else {
				// 恢复平衡，此时的节点为失衡的节点，即平衡因子为-2或2的节点
				rebalance(parent);
				break;
			}
		}
	}

	/**
	 * 恢复平衡
	 * 
	 * @param node
	 */
	protected void rebalance(AVLNode<E> node) {
		if (node.balanceFactor() > 0) {
			// LL 或 LR
			AVLNode<E> left = (AVLNode<E>) node.left();
			if (left.balanceFactor() > 0) {
				// LL
				rotateRight(node);
				// 平衡后重新计算高度
				node.updateHigh();
				left.updateHigh();
			} else {
				// LR
				rotateLeft(left);
				left.updateHigh();
				AVLNode<E> parent = (AVLNode<E>) rotateRight(node);
				// 平衡后重新计算高度
				node.updateHigh();
				parent.updateHigh();
			}
		} else {
			// RR 或 RL
			AVLNode<E> right = (AVLNode<E>) node.right();
			if (right.balanceFactor() < 0) {
				// RR
				rotateLeft(node);
				// 平衡后重新计算高度
				node.updateHigh();
				right.updateHigh();
			} else {
				// RL
				rotateRight(right);
				right.updateHigh();
				AVLNode<E> parent = (AVLNode<E>) rotateLeft(node);
				// 重新计算高度
				node.updateHigh();
				parent.updateHigh();
			}
		}
	}

	/**
	 * 左旋转(RR)
	 * 
	 * @param node
	 *            平衡因子为2的节点
	 * @return 恢复平衡后的父节点
	 */
	protected Node<E> rotateLeft(Node<E> node) {
		Node<E> parent = node.parent();
		Node<E> right = node.right();
		node.right(right.left());
		if (right.left() != null) {
			right.left().parent(node);
		}
		right.left(node);
		node.parent(right);
		right.parent(parent);
		if (parent == null) {
			root = right;
		} else {
			if (parent.compareTo(node) > 0) {
				parent.left(right);
			} else {
				parent.right(right);
			}
		}
		return right;
	}

	/**
	 * 右旋转(LL)
	 * 
	 * @param node
	 */
	protected Node<E> rotateRight(final Node<E> node) {
		Node<E> parent = node.parent();
		Node<E> left = node.left();
		node.left(left.right());
		if (left.right() != null) {
			left.right().parent(node);
		}
		left.right(node);
		node.parent(left);
		left.parent(parent);
		if (parent == null) {
			root = left;
		} else {
			if (parent.compareTo(node) > 0) {
				parent.left(left);
			} else {
				parent.right(left);
			}
		}
		return left;
	}

	@Override
	protected void afterRemove(Node<E> node) {
		while (node != null) {
			AVLNode<E> n = (AVLNode<E>) node;
			// 删除节点后，受影响节点平衡
			if (n.isBalance()) {
				// 更新高度
				if (!n.updateHigh()) {
					// 高度没有发生变化
					break;
				}
			} else {
				// 删除节点，可能导致父节点失衡，最坏情况会出现logN次旋转
				// 注意删除节点进行旋转平衡，可能导致高度降低一个高度
				rebalance(n);
			}
			node = node.parent();
		}
	}

	private static class AVLNode<E extends Comparable<E>> extends Node<E> {

		private int high = 1;

		public AVLNode(E value) {
			super(value);
		}

		public AVLNode(E value, Node<E> parent) {
			super(value, parent);
		}

		/**
		 * 更新节点高度
		 * 
		 * @return true 高度右变更；false 高度没有变更
		 */
		public boolean updateHigh() {
			int lh = high(left());
			int rh = high(right());
			int old = high;
			high = Math.max(lh, rh) + 1;
			return old != high;
		}

		/**
		 * 判断节点是否平衡
		 * 
		 * @return true : 平衡；false ： 失衡
		 */
		public boolean isBalance() {
			return Math.abs(balanceFactor()) <= 1;
		}

		/**
		 * 平衡因子
		 * 
		 * @return
		 */
		public int balanceFactor() {
			int lh = high(left());
			int rh = high(right());
			return lh - rh;
		}

		private int high(Node<E> node) {
			int high = 0;
			if (node != null) {
				high = ((AVLNode<E>) node).high;
			}
			return high;
		}

	}

	public static void main(String[] args) {
		int[] els = { 6, 1, 4, 2, 3, 7, 5, 10, 9, 8 };
		AVL<Integer> avl = new AVL<>();
		for (int e : els) {
			avl.add(e);
		}
		List<Integer> alls = avl.traversal();
		for (int a : alls) {
			System.out.println(a);
		}
		System.out.println("==============");
		avl.remove(5);
		alls = avl.traversal();
		for (int a : alls) {
			System.out.println(a);
		}
		System.out.println("==============");
		avl.remove(6);
		alls = avl.traversal();
		for (int a : alls) {
			System.out.println(a);
		}
	}

}
