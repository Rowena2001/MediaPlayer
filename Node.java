/**
 * @author Rowena Shi
 */

// This class represents the Node object in OrderedDictionary

public class Node {
	
	Node parent;
	Node leftChild;
	Node rightChild;
	DataItem data;
	
	public Node(Node p, Node lC, Node rC, DataItem d) {
		parent = p;
		leftChild = lC;
		rightChild = rC;
		data = d;
	}
	
	public Node(DataItem d) {
		parent = null;
		leftChild = null;
		rightChild = null;
		data = d;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public Node getLeftChild() {
		return leftChild;
	}
	
	public Node getRightChild() {
		return rightChild;
	}
	
	public DataItem getDataItem() {
		return data;
	}
	
	public void setParent(Node p) {
		parent = p;
	}
	
	public void setLeftChild(Node lC) {
		leftChild = lC;
	}
	
	public void setRightChild(Node rC) {
		rightChild = rC;
	}
	
	public void setData(DataItem d) {
		data = d;
	}
	
	public boolean isLeaf() { // checks if node is a leaf (returns true if node does not have any children)
		if(rightChild == null && leftChild == null) {
			return true;
		}
		else return false;
	}
	
	public boolean isRoot() { // checks if node is a root (returns true if ndoe does not have a parent)
		if(parent == null) {
			return true;
		}
		else return false;
	}
	
}
