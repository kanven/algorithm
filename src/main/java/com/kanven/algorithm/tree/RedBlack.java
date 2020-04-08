package com.kanven.algorithm.tree;

/**
 * 红黑树
 * 
 * <ul>
 * 红黑树性质：
 * <li>1、节点要么是红色要么是黑色；</li>
 * <li>2、根节点为黑色；</li>
 * <li>3、红色节点子节点为黑色,红色节点父节点为黑色；</li>
 * <li>4、叶子节点（<b>叶子节点是NULL节点，是虚拟的节点</b>）均为黑色</li>
 * <li>5、任意节点到叶子节点经过的黑色节点数目相同；</li>
 * </ul>
 * <b>另外：</b>
 * <ul>
 * <li>红黑树可以用4阶B树等价交换，转换后B树节点为：红、黑、红；</li>
 * <li>红黑树添加、删除节点，一定要对照4阶B树；</li>
 * <li>红黑树添加的节点默认为红色（根节点除外），加快树的平衡收敛；</li>
 * </ul>
 * 
 * @author kanven
 *
 */
public class RedBlack<E extends Comparable<E>> extends BST<E> {

	/**
	 * 节点颜色
	 * 
	 * @author kanven
	 *
	 */
	private static enum Color {
		RED, BLACK;
	}

	/**
	 * 创建节点
	 */
	@Override
	protected Node<E> createNode(E e) {
		return new RBNode<E>(e, Color.RED);
	}

	/**
	 * 添加叶子节点后自平衡操作
	 * <ul>
	 * <b>一、对照4阶B树，节点只可能加在最底层，最底层存在以下四种情况：</b>
	 * <li>1.1、红、黑、红；</li>
	 * <li>1.2、红、黑；</li>
	 * <li>1.3、黑、红；</li>
	 * <li>1.4、黑；</li>
	 * </ul>
	 * <ul>
	 * <b>二、对应4阶B树的四种情况，添加节点位置存在12种可能：</b>
	 * <li>2.1、4阶B树第一种情况，有4种可能；</li>
	 * <li>2.2、4阶B树第二种情况，有3种可能；</li>
	 * <li>2.3、4阶B树第三种情况，有3种可能；</li>
	 * <li>2.4、4阶B树第四种情况，有2种可能；</li>
	 * </ul>
	 * <ul>
	 * <b>三、红黑树添加情况梳理：</b>
	 * <li>3.1、父节点为黑色节点，不需要做任何处理，存在4种可能（4阶B树2.4左右节点，2.3左节点，2.2右节点）；</li>
	 * <li>3.2、父节点为红色节点，需要调整，存在8种可能（4阶B树2.1左右节点，2.2左节点，2.3右节点）；
	 * <ul>
	 * <b>第2类又可以细分为两大类：</b>
	 * <li>3.2.1、4阶B树第2、3种情况 <br>
	 * 此类情况,在4阶B树中可以合为一个节点并分为：LL,LR,RR,RL四种情况，通过旋转、变色可以达成；</br>
	 * </li>
	 * <li>3.2.2、4阶B树第1种情况 <br>
	 * 此类情况，在4阶B树中相当于上溢，最坏情况可至根节点；</br>
	 * </li>
	 * </ul>
	 * </li>
	 * </ul>
	 * <ul>
	 * <b>四、红黑树添加情况梳理实现细节：</b>
	 * <li>4.1、第2类中2.1、2.2的区分方式是：父节点的兄弟节点是否是红色（空节点是黑色）；</li>
	 * <li>4.2、2.2上溢，可选节点有两个，最好方式是选择父节点做为上溢节点；</li>
	 * <li>4.3、2.2上溢，可能导致其父节点溢出，<b>这是一个递归情况，可以转化为新节点的添加</b>；</li>
	 * <li>4.4、2.2上溢，最坏情况下可以至跟节点，只需要将根节点染成黑色，保证红黑树性质；</li>
	 * </ul>
	 */
	@Override
	protected void afterAdd(Node<E> node) {
		Node<E> cursor = node;
		while (cursor != null) {
			Node<E> parent = cursor.parent();
			if (parent == null) {
				// 根节点
				black(cursor);
				return;
			}
			// 3.1
			if (isBlack(parent)) {
				// 父节点是黑色
				return;
			}
			// 3.2
			// 父节点的兄弟节点
			Node<E> uncle = parent.sibling();
			Node<E> gp = parent.parent();
			red(gp);
			if (isBlack(uncle)) {
				// 3.2.1
				// 旋转
				if (parent.isLeft()) {
					if (cursor.isLeft()) {
						// LL
						Node<E> nd = rotateRight(gp);
						black(nd);
					} else {
						// LR
						Node<E> nd = rotateLeft(parent);
						rotateRight(gp);
						black(nd);
						red(parent);
					}
				} else {
					if (cursor.isLeft()) {
						// RL
						Node<E> nd = rotateRight(parent);
						rotateLeft(gp);
						black(nd);
						red(parent);
					} else {
						// RR
						Node<E> nd = rotateLeft(gp);
						black(nd);
					}
				}
				return;
			} else {
				// 3.2.2
				// 上溢处理
				black(parent);
				black(uncle);
				cursor = gp;
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

	/**
	 * 删除节点
	 * <ul>
	 * <b>一、对照4阶B树，删除节点一定发生在叶子节点，叶子节点存在四种情况：</b>
	 * <li>1.1、红、黑、红；</li>
	 * <li>1.2、红、黑；</li>
	 * <li>1.3、黑、红；</li>
	 * <li>1.4、黑；</li>
	 * </ul>
	 * <ul>
	 * <b>二、删除操作</b>
	 * <li>2.1 删除节点为红色（1.1、1.2、1.3），无需任何操作；</li>
	 * <li>2.2 删除节点为黑色：
	 * <ul>
	 * <li>2.2.1、度为2（1.1），前继（后继）取代；</li>
	 * <li>2.2.2、度为1（1.2、1.3）即叶子节点为红色，叶子节点替换（从4阶B树角度看）；</li>
	 * <li>2.2.3、度为0（1.4）即为叶子节点（叶子节点均为黑色），情况十分复杂；</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * <ul>
	 * <b>三、删除节点为黑色叶子节点分解</b> <br>
	 * 从4阶B树的角度分析，此时出现下溢,有两种处理方法：向兄弟节点借维持平衡或向下合并</br>
	 * <li>3.1、向兄弟节点借：
	 * <ul>
	 * <li>3.1.1、可以直接借： <br>
	 * 兄弟节点必须是黑色（从4阶B树角度分析，如果兄弟节点是红色，则其父节点是黑色，两个节点不在同一层，元素借用无法实现）</br>
	 * <ul>
	 * <li>3.1.1.1、兄弟节点存在至少一个红色子节点（有元素可借），进行旋转操作；</li>
	 * <li>3.1.1.2、兄弟节点子节点均为黑色（没有元素可借），进行合并操作：
	 * <ul>
	 * <li>3.1.1.2.1、父节点为红色，父节点和兄弟节点合并；</li>
	 * <li>3.1.1.2.2、父节点为黑色，父节点和兄弟节点合并，相当于删除父节点，<b>重复2.2操作流程</b>；</li>
	 * </ul>
	 * </li>
	 * <ul>
	 * </li>
	 * <li>3.1.2、不能直接借 <br>
	 * <b>兄弟节点为红色（红、黑）</b>，兄弟节点和父节点旋转变色，转换为3.1.1； </br>
	 * </li>
	 * </ul>
	 * </li>
	 * </ul>
	 * 
	 */
	@Override
	protected void afterRemove(Node<E> node) {

	}

	private void black(Node<E> node) {
		RBNode<E> rb = (RBNode<E>) node;
		rb.color = Color.BLACK;
	}

	private void red(Node<E> node) {
		RBNode<E> rb = (RBNode<E>) node;
		rb.color = Color.RED;
	}

	private boolean isRed(Node<E> node) {
		if (node == null)
			return false;
		RBNode<E> rb = (RBNode<E>) node;
		return rb.isRed();
	}

	private boolean isBlack(Node<E> node) {
		return !isRed(node);
	}

	private static class RBNode<E extends Comparable<E>> extends Node<E> {

		private Color color = Color.RED;

		public RBNode(E e) {
			super(e);
		}

		public RBNode(E e, Color color) {
			super(e);
			this.color = color;
		}

		public boolean isRed() {
			return color == Color.RED;
		}

		@Override
		public int compareTo(Node<E> node) {
			return value.compareTo(node.value);
		}

		@Override
		public String toString() {
			return "RBNode [color=" + color + ",value=" + value() + "]";
		}

	}

	public static void main(String[] args) {
		int[] eles = { 62, 52, 92, 96, 53, 95, 13, 63, 34, 82, 76, 54, 9, 68, 39 };
		RedBlack<Integer> rb = new RedBlack<>();
		for (int e : eles) {
			rb.add(e);
		}
		for (int e : rb.traversal()) {
			System.out.println(e);
		}
	}

}
