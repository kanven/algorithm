package com.kanven.algorithm.tree;

/**
 * 节点
 * 
 * @author kanven
 *
 * @param <E>
 */
class Node<E extends Comparable<E>> implements Comparable<Node<E>> {

	private Node<E> paranet;

	private Node<E> left;

	private Node<E> right;

	protected E value;

	public Node(E value) {
		this.value = value;
	}

	public Node(E value, Node<E> parent) {
		this(value);
		this.paranet = parent;
	}

	public E value() {
		return this.value;
	}

	public void value(E value) {
		this.value = value;
	}

	public Node<E> left() {
		return this.left;
	}

	public void left(Node<E> left) {
		this.left = left;
	}

	public boolean isLeft() {
		return paranet.left == this;
	}

	public Node<E> right() {
		return this.right;
	}

	public void right(Node<E> right) {
		this.right = right;
	}

	public boolean isRight() {
		return paranet.right == this;
	}

	public Node<E> sibling() {
		if (parent().left() == this) {
			return parent().right();
		}
		return parent().left();
	}

	public void parent(Node<E> parent) {
		this.paranet = parent;
	}

	public Node<E> parent() {
		return this.paranet;
	}

	public void remove() {
		if (paranet != null) {
			if (paranet.left == this) {
				paranet.left = null;
			} else if (paranet.right == this) {
				paranet.right = null;
			}
		}
		paranet = null;
		left = null;
		right = null;
	}

	@Override
	public int compareTo(Node<E> node) {
		return value.compareTo(node.value);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Node)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		Node<E> node = (Node<E>) obj;
		return value.equals(node.value);
	}

	@Override
	public String toString() {
		return "Node [value=" + value + "]";
	}

}
