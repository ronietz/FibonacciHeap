import com.sun.source.tree.Tree;

/**
 * FibonacciHeap
 *
 * An implementation of Fibonacci heap over positive integers.
 *
 */
public class FibonacciHeap
{
	public HeapNode min;
	public int size;
	public int numOfTrees;
	public int links;

	/**
	 *
	 * Constructor to initialize an empty heap.
	 *
	 */
	public FibonacciHeap()
	{
		this.min = null;
		this.size = 0;
		this.numOfTrees = 0;
		this.links = 0;
	}

	/**
	 *
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapNode.
	 *
	 */
	public HeapNode insert(int key, String info)
	{
		//set new node with key and info, all pointers are null
		HeapNode node = new HeapNode(key, info);

		//insert to empty heap
		if (this.size == 0) {
			min = node;
		}

		//insert before min and set pointers
		else {
			node.next = min;
			node.prev = min.prev;
			min.prev = node;
			node.prev.next = node;
		}

		//check if new key is min key
		if (key < min.key){
			min = node;
		}

		//increase size of heap
		this.size++;

		//set number of trees in heap
		this.numOfTrees++;

		//return pointer to new node
		return node;
	}

	/**
	 * 
	 * Return the minimal HeapNode, null if empty.
	 *
	 */
	public HeapNode findMin()
	{
		return min; // should be replaced by student code
	}

	/**
	 *
	 * connect two trees in the same rank
	 *
	 */
	public void connectTreesSameRank(HeapNode root1, HeapNode root2)
	{
		//set smaller root as new root
		if (root1.key < root2.key){
			//set root2 as first child of root1
			//set root2 next to current last child
			root1.child.prev.next = root2;
			root2.prev = root1.child.prev;

			//set root2 prev to current first child
			root1.child.prev = root2;
			root2.next = root1.child;

			//set parent-child pointers
			root1.child = root2;
			root2.parent = root1;

			// set rank
			//TO DO!!
			//return new root
			//TO DO!!
		}
		else {
			//set root1 as first child of root2
			//set root1 next to current last child
			root2.child.prev.next = root1;
			root1.prev = root2.child.prev;

			//set root1 prev to current first child
			root2.child.prev = root1;
			root1.next = root2.child;

			//set parent-child pointers
			root2.child = root1;
			root1.parent = root2;

			// set rank
			//TO DO!!
			//return new root
			//TO DO!!
		}

	}

	/**
	 * 
	 * Delete the minimal item
	 *
	 */
	public void deleteMin()
	{
		//save pointer to first child of min, to start with when succesive linking
		HeapNode root = this.min.child;

		//cut min from his children and move them to trees list as roots
		if (min != null) {
			// move min children to roots list
			min.child.prev.next = min.next;
			min.next.prev = min.child.prev;

			min.child.prev = min.prev;
			min.prev.next = min.child;


			//cut min node from his children
			HeapNode minChildNode = this.min.child;
			for (int i = 0; i < this.min.rank; i++) {
				minChildNode.parent = null;
				minChildNode = minChildNode.next;
			}

		}
		//do Successive Linking to all trees in heap and find new min
		int numOfBuckets = (int) Math.floor(Math.log(this.size + 1)) + 1;
		HeapNode[] buckets = new HeapNode[numOfBuckets];

		for (int i = 0; i < this.numOfTrees; i++) {
			// DO TO - CHANGE to while new_root.rank != null -> connect trees
			if (buckets[root.rank] == null) {
				buckets[root.rank] = root;
			}
			else {
				//new_root = connectTreesSameRank(buckets[root.rank], root);
				buckets[root.rank] = null;
				//buckets[new_root.rank]
			}
		}

		//set size
		this.size--;

		//set number of trees

	}

	/**
	 * 
	 * pre: 0<diff<x.key
	 * 
	 * Decrease the key of x by diff and fix the heap. 
	 * 
	 */
	public void decreaseKey(HeapNode x, int diff) 
	{
		x.key = x.key - diff;
		// if there is a violation cut the child
		if(x.key < x.parent.key){
			this.cascadingCut(x,x.parent);
		}
	}


	/**
	 *
	 *
	 * cut the necessary modes from the heap
	 *
	 */
	public void cascadingCut(HeapNode x, HeapNode parent){
		this.cut(x,parent);
		if (parent.parent != null){
			if(!parent.mark){
				parent.mark = true;
			}else{
				this.cascadingCut(parent,parent.parent);
			}
		}
	}


	/**
	 *
	 *
	 * cut the x from its parent and make it new root and update the min pointer
	 *
	 */
	public void cut(HeapNode x, HeapNode parent){
		x.parent = null;
		x.mark = false;
		parent.rank = parent.rank - 1;
		if(x.next == x){
			parent.child = null;
		}else{
			parent.child = x.next;
			x.prev.next = x.next;
			x.next.prev = x.prev;
		}
		this.makeRoot(x);
	}


	/**
	 *
	 *
	 * make x node a new root in the heap
	 *
	 */
	public void makeRoot(HeapNode x) {
		// add the two heaps
		HeapNode minNext = this.min.next;
		x.next = this.min.next;
		this.min.next.prev = x;
		this.min.next = x;
		x.prev = this.min;

		// update the min value if needed
		if(x.key < this.min.key){
			this.min = x;
		}
		numOfTrees++;
	}
	/**
	 * 
	 * Delete the x from the heap.
	 *
	 */
	public void delete(HeapNode x) 
	{    
		return; // should be replaced by student code
	}


	/**
	 * 
	 * Return the total number of links.
	 * 
	 */
	public int totalLinks()
	{
		return links; // should be replaced by student code
	}


	/**
	 * 
	 * Return the total number of cuts.
	 * 
	 */
	public int totalCuts()
	{
		return 0; // should be replaced by student code
	}


	/**
	 * 
	 * Meld the heap with heap2
	 *
	 */
	public void meld(FibonacciHeap heap2)
	{
		if(heap2.numOfTrees > 0) {
			// add the two heaps
			HeapNode minNext = this.min.next;
			this.min.next = heap2.min;
			heap2.min.prev = this.min;
			heap2.min.prev.next = minNext;
			minNext.prev = heap2.min.prev;

			// update the min value if needed
			if (heap2.min.key < this.min.key) {
				this.min = heap2.min;
			}

			this.size += heap2.size();
			this.numOfTrees += heap2.numOfTrees;
		}
	}

	/**
	 * 
	 * Return the number of elements in the heap
	 *   
	 */
	public int size()
	{
		return this.size; // should be replaced by student code
	}


	/**
	 * 
	 * Return the number of trees in the heap.
	 * 
	 */
	public int numTrees()
	{
		return size; // should be replaced by student code
	}

	/**
	 * Class implementing a node in a Fibonacci Heap.
	 *  
	 */
	public static class HeapNode {
		public int key;
		public String info;
		public HeapNode child;
		public HeapNode next;
		public HeapNode prev;
		public HeapNode parent;
		public int rank;
		public boolean mark;

		public HeapNode(int key, String info) {
			this.key = key;
			this.info = info;
			this.parent = null;
			this.child = null;
			this.next = this;
			this.prev = this;
			this.mark = false;
			this.rank = 0;
		}
	}
}
