
public class ThreadedBinaryTree {

	private ThreadedNode _root;
	private ThreadedNode _median;
	private boolean _isEven = true;
	private int _numOfNodes;

	public ThreadedBinaryTree() {
		this._root = null;
		this._median = null;
		this._numOfNodes = 0;

	}

	/**
	 * 
	 * @param node if its only one node in the tree , so he is threaded to himself.
	 */
	public ThreadedBinaryTree(ThreadedNode node) {
		this._root = node;
		this._isEven = false;
		this._median = node;
		this._numOfNodes = 1;
		this._root.setThreadedLeft(true);
		this._root.setThreadedRight(true);
		this._root.setLeft(node);
		this._root.setRight(node);

	}


	public ThreadedNode getRoot() {
		return this._root;
	}
	public void setRoot(ThreadedNode node)
	{
		this._root = node;
		this._isEven = false;
		this._median = node;
		this._numOfNodes = 1;
		this._root.setThreadedLeft(true);
		this._root.setThreadedRight(true);
		this._root.setLeft(node);
		this._root.setRight(node);

	}

	public void setIsEven(boolean b) {
		this._isEven = b;
	}

	public boolean isEven() {
		return this._isEven;
	}

	public ThreadedNode getMedian() {
		return this._median;
	}

	public void setMedian(ThreadedNode node) {
		this._median = node;
	}

	public int getNumOfNodes() {
		return this._numOfNodes;
	}

	public void setNumOfNodes(int numOfNodes) {
		this._numOfNodes = numOfNodes;

		// after changing the number of the nodes we have to detemine if the num is even
		// or odd.
		if (numOfNodes % 2 == 0)
			this.setIsEven(true);
		else if (numOfNodes % 2 != 0)
			this.setIsEven(false);
	}

	/**
	 * 
	 * @param node we will check the key of the node , for know if we have to
	 *             increase the median or not.
	 * @param num  , by the number of the nodes we will know if we delete or insert.
	 */
	private void updateMedian(ThreadedNode node, int num) {
		// if we insert.
		if (num > this.getNumOfNodes()) {
			this.setNumOfNodes(num);
			// if the new node is greater than the current median and the num of the nodes is odd
			// we have to increase our median.
			// else if its even we dont have to increase the median becasue we take the n/2
			// element
			// (n = the number of the nodes.)
			// if we insert node that the median is bigger than him and the num of the nodes
			// is even,we have to
			// go back for the precedessor.
			if ((node.getKey() > this.getMedian().getKey()) && (!this.isEven()))
				this.setMedian(this.findSuccessor(this.getMedian()));
			else if ((node.getKey() < this.getMedian().getKey()) && (this.isEven()))
				this.setMedian(this.findPredecessor(this.getMedian()));
		}
		// if we deleted the node.
		else {
			
			if (node == this.getMedian() && this.isEven()) {
				
				this.setMedian(this.findSuccessor(this.getMedian()));
				
			} else if (node == this.getMedian() && !this.isEven()) {
				
				this.setMedian(this.findPredecessor(this.getMedian()));
				
			} else if (node.getKey() > this.getMedian().getKey() && !this.isEven()) {
				
				this.setMedian(this.findPredecessor(this.getMedian()));
				
			} else if (node.getKey() < this.getMedian().getKey() && this.isEven()) {
				
				this.setMedian(this.findSuccessor(this.getMedian()));
			}
			
			this.setNumOfNodes(num);

		}

	}

	/**
	 * 
	 * @param node
	 * @return the deleted node.
	 */
	public ThreadedNode delete(ThreadedNode node) {

		if(node == null)
			return null;
		
		ThreadedNode parentNode = node.getParent();
		ThreadedNode nodeSuccessor = this.findSuccessor(node);
		ThreadedNode nodePrecedessor = this.findPredecessor(node);
		ThreadedNode nodeForReturn = new ThreadedNode(node.getKey(), node.getValue());
	

		this.updateMedian(node, this.getNumOfNodes() - 1);

		// if he is leaf we will change his parent.
		// case 1
		if (node.isLeaf()) {

			if (node == this.getRoot())
				node = null;
			// we are going to delete the node , but if we have a threadednode to left/right
			// that the deleting node is his successor or precedssor we have to update that.
			// 15
			// \
			// 16 like in this example : 18.getLeft == 16
			// \ and we are going to delete 16 , so we will find 16 precedessor
			// 20 and set the left of 18 to 15.
			// / \
			// 18 25
			else if (parentNode.getLeft() == node) {
				parentNode.setThreadedLeft(true);
				parentNode.setLeft(nodePrecedessor);
			}
			else if (parentNode.getRight() == node) {

				parentNode.setThreadedRight(true);
				parentNode.setRight(nodePrecedessor);
			}

			
			else if (nodeSuccessor.isThreadedLeft())
				nodeSuccessor.setLeft(this.findPredecessor(node));

			// the same thing for the precedessor.
			else if (nodePrecedessor.isThreadedRight())
				nodePrecedessor.setRight(nodeSuccessor);

	} 

		

		// case 2 : the node has 1 son.
		else if ((node.isThreadedLeft() && !node.isThreadedRight())
				|| (node.isThreadedRight() && !node.isThreadedLeft())) {
			// he has left son
			if (!node.isThreadedLeft()) {

				// if the node is the root.
				if (parentNode != null) {

					if (parentNode.getLeft() == node) {

						parentNode.setLeft(node.getLeft());
						node.getLeft().setParent(parentNode);

					} else if (parentNode.getRight() == node) {

						parentNode.setRight(node.getLeft());
						node.getLeft().setParent(parentNode);

					}
					// to the precedessor has new successor
					// , because we delete from the tree the old one.
					// if (nodePrecedessor.isThreadedRight())
					// nodePrecedessor.setRight(nodeSuccessor);

				} else {
					ThreadedNode newRoot = new ThreadedNode(node.getLeft().getKey(), node.getLeft().getValue());
					this._root = newRoot;
				}
			}
			// he has right son
			else if (!node.isThreadedRight()) {
				// if the node is the root so his parent is null.
				if (parentNode != null) {
					if (parentNode.getLeft() == node) {

						parentNode.setLeft(node.getRight());
						node.getRight().setParent(parentNode);

					} else if (parentNode.getRight() == node) {

						parentNode.setRight(node.getRight());
						node.getRight().setParent(parentNode);

					}
					if (nodeSuccessor.isThreadedLeft()) {

						nodeSuccessor.setLeft(nodePrecedessor);
					}

				} else {
					ThreadedNode newRoot = new ThreadedNode(node.getRight().getKey(), node.getRight().getValue());
					this._root = newRoot;
				}
			}
		}
		// case 3 : the node has 2 sons
		// , so we have to replace him with one of his successors.
		else if (!node.isThreadedLeft() && !node.isThreadedRight()) {
			// find successor with at least 1 son.
			while (!nodeSuccessor.isThreadedLeft() && !nodeSuccessor.isThreadedRight()) {
				nodeSuccessor = this.findSuccessor(nodeSuccessor);
			}
			ThreadedNode parentNodeSuccessor = nodeSuccessor.getParent();
			if (nodeSuccessor.isLeaf()) {
				if (parentNodeSuccessor.getRight() == nodeSuccessor) {
					parentNodeSuccessor.setThreadedRight(true);
					node.setKey(nodeSuccessor.getKey());
					node.setValue(nodeSuccessor.getValue());
					parentNodeSuccessor.setRight(this.findSuccessor(parentNodeSuccessor));

				} else if (parentNodeSuccessor.getLeft() == nodeSuccessor) {
					parentNodeSuccessor.setThreadedLeft(true);
					node.copy(nodeSuccessor);
					parentNodeSuccessor.setLeft(node);

				}

			} else if (nodeSuccessor.isThreadedLeft()) {
				// set the new parent of the right subtree of the node successor.
				nodeSuccessor.getRight().setParent(parentNodeSuccessor);
				parentNodeSuccessor.setRight(nodeSuccessor.getRight());
				// now changing the deleting node.
				node.copy(nodeSuccessor);

			} else if (nodeSuccessor.isThreadedRight()) {
				nodeSuccessor.getLeft().setParent(parentNodeSuccessor);
				parentNodeSuccessor.setLeft(nodeSuccessor.getLeft());
				node.copy(nodeSuccessor);
			}

		}


		return nodeForReturn;

	}

	/**
	 * running time : o(h)
	 * 
	 * @param node for insert. update the median.
	 * 
	 */
	public void insert(ThreadedNode node) {
		if(this.getRoot() == null)
		{
			this.setRoot(node);
			this.updateMedian(node,1);
		}
		else
		{
		
		ThreadedNode pointer = this.findParentForInserting(node);

		if (pointer.getKey() < node.getKey()) {
			pointer.setRight(node);
			node.setParent(pointer);
			pointer.setThreadedRight(false);

		} else if (pointer.getKey() > node.getKey()) {
			pointer.setLeft(node);
			node.setParent(pointer);
			pointer.setThreadedLeft(false);
		}
		node.setLeft(this.findPredecessor(node));
		node.setRight(this.findSuccessor(node));
		// increase the number of the nodes in the for median.
		int numOfNodes = this.getNumOfNodes() + 1;

		this.updateMedian(node, numOfNodes);
		}

	}

	/**
	 * running time : o(h)
	 * 
	 * @param nodeForInsert
	 * @return the parent for the node;
	 */

	public ThreadedNode findParentForInserting(ThreadedNode nodeForInsert) {
		ThreadedNode pointer = this.getRoot();
		// search the place for the new node.
		while (!pointer.isThreadedLeft() || !pointer.isThreadedRight()) {

			if (pointer.getKey() > nodeForInsert.getKey()) {
				if (pointer.isThreadedLeft()) // if we have nothing left.
					return pointer;
				pointer = pointer.getLeft();
			} else if (pointer.getKey() < nodeForInsert.getKey()) {
				if (pointer.isThreadedRight()) // if we have nothing right
					return pointer;
				pointer = pointer.getRight();
			}

		}
		return pointer;
	}

	/**
	 * running time : o(h)
	 * 
	 * @param node
	 * @return his successor
	 */
	public ThreadedNode findSuccessor(ThreadedNode node) {

		// if he is the maximum so him self is the successor.
		if (node == this.getMax())
			return node;

		// if the right sub-tree is not empty so the successor is the min of this
		// sub-tree
		if (!node.isThreadedRight())
			return this.getMin(node.getRight());

		ThreadedNode parentPointer = node.getParent();
		ThreadedNode pointer = node;
		// parentpointer != null its only for the parent of the root,he is the only null
		// that we have in the tree.
		while ((parentPointer != null) && (parentPointer.getRight() == pointer)) {
			pointer = parentPointer;
			parentPointer = parentPointer.getParent();
		}
		return parentPointer;

	}

	/**
	 * 
	 * @param node
	 * @return his precedessor.
	 */

	public ThreadedNode findPredecessor(ThreadedNode node) {
		// if the node is the minimum so him self is the predecessor.
		if (node == this.getMin())
			return node;
		// if left sub-tree is not empty so the maximum of the sub tree is the
		// precedessor.
		else if (!node.isThreadedLeft())
			return this.getMax(node.getLeft());

		// if the left sub tree of node is empty
		ThreadedNode parentPointer = node.getParent();
		ThreadedNode pointer = node;

		while ((parentPointer != null) && parentPointer.getLeft() == pointer) {
			parentPointer = parentPointer.getParent();
			pointer = pointer.getParent();
		}

		return parentPointer;

	}

	/**
	 * 
	 * @param node
	 * @return Min key from this node.
	 */

	public ThreadedNode getMin(ThreadedNode node) {

		ThreadedNode currentNode = node;
		while (!currentNode.isThreadedLeft() && currentNode.getLeft() != currentNode) {
			currentNode = currentNode.getLeft();
		}
		return currentNode;

	}

	/**
	 * 
	 * @return Min key of the tree.
	 */
	public ThreadedNode getMin() {
		// if the root is the minimum ,return root.
		if (this.getRoot().isThreadedLeft())
			return this.getRoot();
		return getMin(this.getRoot().getLeft());

	}

	/**
	 * 
	 * @param node
	 * @return Max key from this node.
	 */

	public ThreadedNode getMax(ThreadedNode node) {

		ThreadedNode currentNode = node;
		while (!currentNode.isThreadedRight() && currentNode.getRight() != currentNode) {
			currentNode = currentNode.getRight();
		}
		return currentNode;

	}

	/**
	 * 
	 * @return Max key of the tree
	 */
	public ThreadedNode getMax() {
		// if the root is the maximum ,return root.
		if (this.getRoot().isThreadedRight())
			return this.getRoot();
		return getMax(this.getRoot().getRight());

	}

	public void inOrderTreeWalk() {
		this.inOrderTreeWalk(this.getRoot());

	}

	private void inOrderTreeWalk(ThreadedNode node) {
		if (node != null) {
			if (!node.isThreadedLeft()) {
				this.inOrderTreeWalk(node.getLeft());
			}
			System.out.println(node);
			if (!node.isThreadedRight()) {
				this.inOrderTreeWalk(node.getRight());
			}

		}

	}
	public void preOrderTreeWalk()
	{
		this.preOrderTreeWalk(this.getRoot());
	}
	private void preOrderTreeWalk(ThreadedNode node)
	{
		
		if(node != null)
		{
			System.out.println(node);
			if(!node.isThreadedLeft())
			{
				this.preOrderTreeWalk(node.getLeft());
			}
			if(!node.isThreadedRight())
			{
				this.preOrderTreeWalk(node.getRight());
			}
			
		}
		
	}
	public void postOrederTreeWalk()
	{
		this.postOrederTreeWalk(this.getRoot());
	}
	private void postOrederTreeWalk(ThreadedNode node)
	{
		
		if(node != null)
		{
			if(!node.isThreadedLeft())
			{
				this.postOrederTreeWalk(node.getLeft());
			}
			if(!node.isThreadedRight())
			{
				this.postOrederTreeWalk(node.getRight());
			}
			System.out.println(node);
			
		}
	}
	public ThreadedNode search(int key)
	{
		if(this.getRoot() == null)
		{
			return null;
		}
		
		
		ThreadedNode pointer = this.getRoot();
		
		while(key != pointer.getKey())
		{
			if((key < pointer.getKey() && pointer.isThreadedLeft()) || (key > pointer.getKey() && pointer.isThreadedRight()))
			return null;
		
		else if(key < pointer.getKey() && !pointer.isThreadedLeft())
				pointer = pointer.getLeft();
			else if(key > pointer.getKey() && !pointer.isThreadedRight())
				pointer = pointer.getRight();
		}
		
		return pointer;
		
	}

	
}
