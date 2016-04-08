package com.hzth.myapp.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树demo
 * 
 * @author tianyl
 * 
 */
public class BinaryTreeDemo {

	public static void main(String[] args) {
		Node root = getNodes();
		horizontalPrint(root);
	}

	private static void horizontalPrint(Node root) {
		List<Node> nodes = new ArrayList<>();
		nodes.add(root);
		while (nodes.size() > 0) {
			Node curNode = nodes.remove(0);
			if (curNode != null) {
				System.out.println(curNode.getName());
				if (curNode.getLeft() != null) {
					nodes.add(curNode.getLeft());
				}
				if (curNode.getRigth() != null) {
					nodes.add(curNode.getRigth());
				}
			}
		}
	}

	private static Node getNodes() {
		Node root = new Node("A",
				new Node("B", new Node("D", new Node("H"), new Node("I")), new Node("E", new Node("G"), new Node("K"))),
				new Node("C", new Node("F", new Node("L"), new Node("M")), new Node("G", new Node("N"), new Node("O")))
				);
		return root;
	}
}

class Node {

	private String name;

	private Node left;

	private Node rigth;

	public Node(String name) {
		this.name = name;
	}

	public Node(String name, Node left, Node rigth) {
		super();
		this.name = name;
		this.left = left;
		this.rigth = rigth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRigth() {
		return rigth;
	}

	public void setRigth(Node rigth) {
		this.rigth = rigth;
	}

}