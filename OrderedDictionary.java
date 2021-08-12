/**
 * @author Rowena Shi
 */

// This class implements the methods of OrderedDictionaryADT

public class OrderedDictionary implements OrderedDictionaryADT{
	
	private Node root;
	
	public OrderedDictionary() {
		root = new Node(null);
	}
	
	// invoked my smallest and returns smallest node
	private Node smallestNode(Node r) {
		Node current;
		if(r.isLeaf()) {
			return null;
		}
		else {
			current = r;
			while(!current.getLeftChild().isLeaf()) {
				current = current.getLeftChild();
			}
		}
	    return current;
	}
	
    /* Returns the DataItem with smallest key in the ordered dictionary. 
    Returns null if the dictionary is empty.  */
	public DataItem smallest() {
		if(smallestNode(root) != null) {
			return smallestNode(root).getDataItem();
		}
		else {
			return null;
		}
	}
	
	// invoked by largest and returns largest node
	private Node largestNode(Node r) {
		Node current;
		if(r.isLeaf()) {
			return null;
		}
		else {
			current = r;
			while(!current.getRightChild().isLeaf()) {
				current = current.getRightChild();
			}
		}
	    return current;
	}
	
    /* Returns the DataItem with largest key in the ordered dictionary. 
    Returns null if the dictionary is empty.  */
	public DataItem largest() {
		if(largestNode(root) != null) {
			return largestNode(root).getDataItem();
		}
		else {
			return null;
		}
	}
	
	// invoked by get and returns a node with key k
	private Node getNode(Key k, Node r) {
		if(r.isLeaf()) {
			return r;
		}
		else {
			if(k.compareTo(r.getDataItem().getKey()) == 0) {
				return r;
			}
			else if(k.compareTo(r.getDataItem().getKey()) == -1) {
				return getNode(k, r.getLeftChild());
			}
			else return getNode(k, r.getRightChild());
		}
	}
	
	/* Returns the DataItem object with the Key as k, or it returns null if such 
    a DataItem is not in the dictionary. */
	public DataItem get(Key k) {
		if(getNode(k, root) != null) {
			return getNode(k, root).getDataItem();
		}
		else {
			return null;
		}
	}
	
	// invoked by put: inserts a node
	private void putNode(DataItem d, Node r) throws DictionaryException {
    	Node current = getNode(d.getKey(), r);
		if(current.isLeaf()) {
			current.setData(d);
			Node leftChild = new Node(current, null, null, null);
			Node rightChild = new Node(current, null, null, null);
			current.setLeftChild(leftChild);
			current.setRightChild(rightChild);
			leftChild.setParent(current);
			rightChild.setParent(current);
		}
		else {
			throw new DictionaryException("putNode failed: node already exists\n");
		}
	}

    /* Inserts the DataItem d into the ordered dictionary. It throws a DictionaryException 
       if a DataItem with the same Key attribute as d is already in the dictionary. */
    public void put (DataItem d) throws DictionaryException {
    	Node current = getNode(d.getKey(), root);
    	if(current.isLeaf()) {
    		putNode(d, current);
    	}
    	else {
    		throw new DictionaryException("put failed: node already exists\n");
    	}
    }
    
    // invoked by remove: removes node
    private void removeNode(Node r, Key k) throws DictionaryException {
    	if (!r.isLeaf()) { // checks if node is internal/root
    		if(r.getLeftChild().isLeaf()) {
    			Node currentRightChild = r.getRightChild();
    			Node currentLeftChild = r.getLeftChild();
    			Node currentParent = r.getParent();
    			if(r.isRoot()) {
    				r = currentRightChild;
    				root = r;
    			}
    			else {
    				if(k.compareTo(currentParent.getDataItem().getKey()) > 0) { // checks to see if child is right or left of parents
    					currentParent.setRightChild(currentRightChild);
        				currentRightChild.setParent(currentParent);
    				}
    				else {
    					currentParent.setLeftChild(currentLeftChild);
        				currentLeftChild.setParent(currentParent);
    				}
    				r = null; // deletes r
    			}
    		}
    		else if(r.getRightChild().isLeaf()) {
    			Node currentLeftChild = r.getLeftChild();
    			Node currentRightChild = r.getRightChild();
    			Node currentParent = r.getParent();
    			if(r.isRoot()) {
    				r = currentLeftChild;
    				root = r;
    			}
    			else {
    				if(k.compareTo(currentParent.getDataItem().getKey()) > 0) { // checks to see if child is right or left of parents
    					currentParent.setRightChild(currentRightChild);
        				currentRightChild.setParent(currentParent);
    				}
    				else {
    					currentParent.setLeftChild(currentLeftChild);
        				currentLeftChild.setParent(currentParent);
    				}
    				r = null; // deletes r
    			}
    		}
    		else {
    			Node small = smallestNode(r.getRightChild());
    			r.setData(small.getDataItem());
    			removeNode(small, small.getDataItem().getKey());
    		}
    	}
    	else {
    		throw new DictionaryException("removeNode failed: node does not exist\n");
    	}
    }

    /*  Removes the DataItem with the same Key attribute as k from the dictionary. It throws a 
        DictionaryException if such a DataItem is not in the dictionary. */
    public void remove (Key k) throws DictionaryException {
    	Node current = getNode(k, root);
    	if (!current.isLeaf()) {
    		removeNode(current, k);
    	}
    	else {
    		throw new DictionaryException("remove failed: node does not exist\n");
    	}
    }
    
    // invoked by successor: returns a node
    private Node successorNode(Node r, Key k) {
    	if(r.isLeaf()) {
    		return null;
    	}
    	else {
    		Node current = getNode(k, r);
    		if (!current.isLeaf() && !current.getRightChild().isLeaf()) {
    			return smallestNode(current.getRightChild());
    		}
    		else {
    			Node currentParent = current.getParent();
    			if(currentParent == null) {
    				return null;
    			}
    			int i = k.compareTo(currentParent.getDataItem().getKey());
    			while(i > 0) {
    				current = currentParent;
    				currentParent = currentParent.getParent();
    				if(currentParent == null) {
    					return null;
    				}
    				i = current.getDataItem().getKey().compareTo(currentParent.getDataItem().getKey());
    			}
    			return currentParent;
    		}
    	}
    }

    /* Returns the successor of k (the DataItem from the ordered dictionary 
       with smallest key larger than k); it returns null if the given key has
       no successor. Note that the given key k DOES NOT need to be in the dictionary. */
    public DataItem successor (Key k) {
    	if(root.isLeaf()) {
    		return null;
    	}
    	else {
    		return successorNode(root, k).getDataItem();
    	}
    }
    
    // invoked by predecessor: returns a node
    private Node predecessorNode(Node r, Key k) {
    	if(r.isLeaf()) {
    		return null;
    	}
    	else {
    		Node current = getNode(k, r);
    		if (!current.isLeaf() && !current.getLeftChild().isLeaf()) {
    			return largestNode(current.getLeftChild());
    		}
    		else {
    			Node currentParent = current.getParent();
    			if(currentParent == null) {
    				return null;
    			}
    			int i = k.compareTo(currentParent.getDataItem().getKey());
    			while(i < 0) {
    				current = currentParent;
    				currentParent = currentParent.getParent();
    				if(currentParent == null) {
    					return null;
    				}
    				i = current.getDataItem().getKey().compareTo(currentParent.getDataItem().getKey());
    			}
    			return currentParent;
    		}
    	}
    }

    /* Returns the predecessor of k (the DataItem from the ordered dictionary 
       with largest key smaller than k; it returns null if the given key has 
       no predecessor. Note that the given key k DOES NOT need to be in the dictionary.  */
    public DataItem predecessor (Key k) {
    	if(root.isLeaf()) {
    		return null;
    	}
    	else {
    		return predecessorNode(root, k).getDataItem();
    	}
    }
}
