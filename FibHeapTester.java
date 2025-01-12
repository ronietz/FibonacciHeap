
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FibHeapTester {
    public static void printHeapAsDetailedTree(FibonacciHeap heap) {
        if (heap.min == null) {
            System.out.println("The Fibonacci Heap is empty.");
            return;
        }

        System.out.println("Fibonacci Heap (Detailed Tree Representation):");
        FibonacciHeap.HeapNode current = heap.min;
        int treeIndex = 1;
        do {
            System.out.println("Tree " + treeIndex + " (Root Key: " + current.key + "):");
            printTreeWithChildren(current, "  ");
            current = current.next;
            treeIndex++;
        } while (current != heap.min);
    }

    private static void printTreeWithChildren(FibonacciHeap.HeapNode node, String indent) {
        // info of current node
        System.out.println(indent + "- Node [Key=" + node.key + ", Rank=" + node.rank + ", Mark=" + node.mark + "]");

        // show Node's children if exists
        if (node.child != null) {
            System.out.println(indent + "  Children of Node " + node.key + ":");
            FibonacciHeap.HeapNode child = node.child;
            do {
                printTreeWithChildren(child, indent + "    ");
                child = child.next;
            } while (child != node.child);
        } else {
            System.out.println(indent + "  No children.");
        }
    }
    public static void printHeap(FibonacciHeap heap) {
        printHeapAsDetailedTree(heap);
    }

    public static void insertTests(FibonacciHeap fh) {
        System.out.println("----insert Tests----");

        System.out.println("----insert to empty heap test---");
        fh.insert(10, "roni's information");
        if (fh.min.key != 10 && fh.size != 1){
            System.out.println("Error in inserting first key");
        }
        else {
            System.out.println("PASSED!");
        }

        System.out.println("----insert new min test---");
        fh.insert(5, "roni's information");
        if (fh.min.key != 5 && fh.size != 2){
            System.out.println("Error in inserting new min key");
        }
        else {
            System.out.println("PASSED!");
        }

        System.out.println("----insert same key test---");
        fh.insert(10, "another info with 10 key");
        if (fh.size != 3){
            System.out.println("Error in inserting same key");
        }
        else {
            System.out.println("PASSED!");
        }

    }


    public static void testMeld() {
        FibonacciHeap heap1 = new FibonacciHeap();
        FibonacciHeap heap2 = new FibonacciHeap();

        // Insert elements into heap1
        heap1.insert(10, "A");
        heap1.insert(20, "B");

        // Insert elements into heap2
        heap2.insert(5, "C");
        heap2.insert(30, "D");

        // Perform meld
        heap1.meld(heap2);

        // Validate the properties of the melded heap
        assertEquals(4, heap1.size(), "Heap size after meld is incorrect.");
        assertEquals(4, heap1.numTrees(), "Number of trees after meld is incorrect.");
        assertEquals(5, heap1.findMin().key, "Minimum key after meld is incorrect.");

        System.out.println("TEST MELD PASSED!");
    }

    public static void testMeldEmptyHeap() {
        FibonacciHeap heap1 = new FibonacciHeap();
        FibonacciHeap heap2 = new FibonacciHeap();

        // Insert elements into heap1
        heap1.insert(15, "E");
        heap1.insert(25, "F");

        // Meld with an empty heap2
        heap1.meld(heap2);

        // Validate the properties of the melded heap
        assertEquals(2, heap1.size(), "Heap size after melding with an empty heap is incorrect.");
        assertEquals(15, heap1.findMin().key, "Minimum key after melding with an empty heap is incorrect.");

        System.out.println("TEST EMPTY MELD PASSED!");
    }

    public static void testCascadingCut() {
        FibonacciHeap heap = new FibonacciHeap();

        // Insert elements into the heap
        FibonacciHeap.HeapNode node1 = heap.insert(10, "A");
        FibonacciHeap.HeapNode node2 = heap.insert(20, "B");
        FibonacciHeap.HeapNode node3 = heap.insert(30, "C");

        // Link nodes to create a parent-child relationship
        heap.connectTreesSameRank(node1, node2);
        node2.parent = node1;
        heap.connectTreesSameRank(node1, node3);
        node3.parent = node1;

        // Decrease key of node3 to trigger cascading cut
        heap.decreaseKey(node3, 25);

        // Validate cascading cut operation
        assertNull(node3.parent, "Node3 parent should be null after cascading cut.");
        assertEquals(node3, heap.findMin(), "Node3 should be the new min after cascading cut.");
        assertFalse(node3.mark, "Node3 should not be marked after cascading cut.");
        assertEquals(2, heap.numTrees(), "Number of trees after cascading cut is incorrect.");

        System.out.println("TEST CASCADING CUT PASSED!");
    }


    public static void main(String[] args) {
        FibonacciHeap fh = new FibonacciHeap();
        insertTests(fh);
        FibHeapTester.testMeld();
        FibHeapTester.testMeldEmptyHeap();
//        FibHeapTester.testCascadingCut();
        

    }
}
