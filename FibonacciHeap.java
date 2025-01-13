import com.sun.source.tree.Tree;

import java.util.Arrays;
import java.util.Comparator;

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
	public int cuts;

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
		this.cuts = 0;
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
	 * returns pointer to new root
	 *
	 */
	public HeapNode connectTreesSameRank(HeapNode root1, HeapNode root2)
	{
		HeapNode minRoot = root2;
		HeapNode maxRoot = root1;
		if (root1.key < root2.key) {
			minRoot = root1;
			maxRoot = root2;
		}

		if (minRoot.rank != 0) {
			//set maxRoot as first child of minRoot
			//set maxRoot next to current last child
			minRoot.child.prev.next = maxRoot;
			maxRoot.prev = minRoot.child.prev;

			//set maxRoot prev to current first child
			minRoot.child.prev = maxRoot;
			maxRoot.next = minRoot.child;
		}
		else {
			maxRoot.next = maxRoot;
			maxRoot.prev = maxRoot;
		}

		//set parent-child pointers
		minRoot.child = maxRoot;
		maxRoot.parent = minRoot;

		// set rank
		minRoot.rank++;

		//update links
		this.links++;

		//return new root
        return minRoot;
    }
	/*
	*
	* delete min - only cut him from his kids and move them to the trees list as roots
	*
	 */

	private void deleteMinWithoutConsolidating(){
		//check if currMin have children
		// cut currMin from his children and move them to trees list as roots
		if (this.min.rank != 0) {
			// move min children to roots list
			this.min.child.prev.next = this.min.next;
			this.min.next.prev = this.min.child.prev;

			this.min.child.prev = this.min.prev;
			this.min.prev.next = this.min.child;


			//cut currMin node from his children
			HeapNode minChildNode = this.min.child;
			for (int i = 0; i < this.min.rank; i++) {
				minChildNode.parent = null;
				minChildNode = minChildNode.next;
			}

		}
		else{
			// cut min from roots list
			this.min.prev.next = this.min.next;
			this.min.next.prev = this.min.prev;
		}

	}

	/**
	 * 
	 * Delete the minimal item
	 *
	 */
	public void deleteMin()
	{
		// check if heap empty - no min to delete
		if (this.size == 0) {
			return;
		}

		//save pointer to first child of min or next root in list, to start with when succesive linking
		HeapNode currRoot;
		if (this.min.rank != 0) {
			 currRoot = this.min.child;
		}
		else {
			// min have no kids, save pointer to next node
			currRoot = this.min.next;
		}

		deleteMinWithoutConsolidating();

		//set new numOfTrees
		this.numOfTrees = this.numOfTrees - 1 + this.min.rank;

		//do Successive Linking to all trees in heap and find new min
		int numOfBuckets = (int) Math.floor(Math.log(this.size + 1)) + 1;
		HeapNode[] buckets = new HeapNode[numOfBuckets];

		int initialNumOfTrees = this.numOfTrees;
		HeapNode[] initialTreesArr = new HeapNode[initialNumOfTrees];
		HeapNode firstNode = currRoot;
		int j = 0;
		do {
			currRoot = currRoot.next;
			initialTreesArr[j] = currRoot;
			j++;
		}
		while (currRoot != firstNode);

		for (int i = 0; i < initialNumOfTrees; i++) {
			currRoot = initialTreesArr[i];
			//connect until only one tree in the same rank
			while (buckets[currRoot.rank] != null){
				currRoot = connectTreesSameRank(buckets[currRoot.rank], currRoot);
				buckets[currRoot.rank - 1] = null;
				// change numOfTrees
				this.numOfTrees--;
			}
			buckets[currRoot.rank] = currRoot;
		}

		// connect roots to a list and find new min
		// remove null buckets
		HeapNode[] treesArray = new HeapNode[this.numOfTrees];
		j = 0;
        for (HeapNode bucket : buckets) {
            if (bucket != null) {
                treesArray[j] = bucket;
                j++;
            }
        }
		// connect and find min
		if (treesArray.length > 0) {
			int minKey = treesArray[0].key;
			this.min = treesArray[0];
			currRoot = treesArray[0];
			HeapNode nextRoot;

			//set pointers
			currRoot.parent = null;
			currRoot.prev = treesArray[treesArray.length - 1];
			treesArray[treesArray.length - 1].next = currRoot;

			for (int i = 1; i < treesArray.length; i++) {
				nextRoot = treesArray[i];
				//check if root is the new min
				if (nextRoot.key < minKey) {
					minKey = nextRoot.key;
					this.min = nextRoot;
				}

				// set pointers
				currRoot.next = nextRoot;
				nextRoot.prev = currRoot;
				currRoot = nextRoot;
				nextRoot.parent = null;
			}
		}
		else {
			min = null;
		}

		//set size
		this.size--;
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
		//save pointer to min
		HeapNode currMin = this.min;

		//decrease key of x to bellow min.key
		decreaseKey(x, (x.key - currMin.key + 1));
		int xRank = x.rank;

		//call deleteMin without Consolidating
		deleteMinWithoutConsolidating();

		//set min back to correct current min
		this.min = currMin;

		//set numOfTrees
		this.numOfTrees = this.numOfTrees - 1 + xRank;

		//set size
		this.size--;

	}


	/**
	 * 
	 * Return the total number of links.
	 * 
	 */
	public int totalLinks()
	{
		return this.links;
	}


	/**
	 * 
	 * Return the total number of cuts.
	 * 
	 */
	public int totalCuts()
	{
		return this.cuts;
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
		return this.size;
	}


	/**
	 * 
	 * Return the number of trees in the heap.
	 * 
	 */
	public int numTrees()
	{
		return this.numOfTrees;
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
