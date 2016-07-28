package io.github.riwcwt;

public class BinaryTree {
	private Node top;

	public Node addNode(Node node) {
		if (top == null) {
			top = node;
			return node;
		} else {
			return this.addNode(node, top);
		}
	}

	private Node addNode(Node node, Node top) {
		if (node.getId() == top.getId()) {
			return top;
		}
		if (node.getId() < top.getId()) {
			if (top.getLeft() == null) {
				top.setLeft(node);
				return node;
			} else {
				return this.addNode(node, top.getLeft());
			}
		} else {
			if (top.getRight() == null) {
				top.setRight(node);
				return node;
			} else {
				return this.addNode(node, top.getRight());
			}
		}
	}

	public Node deleteNode(Node node) {
		if (top != null) {
			Node current = top;
			Node parent = null;
			boolean leftNode = true;
			while (current != null && current.getId() != node.getId()) {
				parent = current;
				if (current.getId() < node.getId()) {
					leftNode = true;
					current = current.getLeft();
				} else {
					leftNode = false;
					current = current.getRight();
				}
			}
			if (current == null) {
				return null;
			}
			if (current.getLeft() == null && current.getRight() == null) {
				if (parent == null) {
					top = null;
				} else {
					if (leftNode) {
						parent.setLeft(null);
					} else {
						parent.setRight(null);
					}
				}
			} else if (current.getLeft() != null && current.getRight() == null) {
				if (parent == null) {
					top = current.getLeft();
				} else {
					parent.setLeft(current.getLeft());
				}
			} else if (current.getLeft() == null && current.getRight() != null) {
				if (parent == null) {
					top = current.getRight();
				} else {
					parent.setRight(current.getRight());
				}
			} else {

			}
			return current;
		}
		return null;
	}

}

class Node {
	private Node left;
	private Node right;
	private int id;

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}