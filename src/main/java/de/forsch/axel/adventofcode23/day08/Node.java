package de.forsch.axel.adventofcode23.day08;

public class Node {

	public final String id;
	private Node right;
	private Node left;

	public Node(String id) {
		this.id = id;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public Node getLeft() {
		return left;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", left=" + left.id + ", right=" + right.id + "]";
	}
}
