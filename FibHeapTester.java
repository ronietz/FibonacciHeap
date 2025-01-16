
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
        System.out.println(indent + "- Node [Key=" + node.key + ", Rank=" + node.rank + ", Mark=" + node.mark + ", Info= " + node.info + "]");

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

    public static void insertTests() {
        System.out.println("----insert Tests----");
        FibonacciHeap fh = new FibonacciHeap();

        System.out.println("----insert to empty heap test---");
        fh.insert(10, "roni's information");
        assertEquals(10, fh.min.key, "Error empty insert - wrong min key");
        assertEquals(1, fh.size(), "Error empty insert - wrong size");

        System.out.println("----insert new min test---");
        fh.insert(5, "roni's information");
        assertEquals(5, fh.min.key, "Error insert new min - wrong min key");
        assertEquals(2, fh.size(), "Error insert new min - wrong size");

        System.out.println("----insert same key test---");
        fh.insert(10, "another info with 10 key");
        assertEquals(3, fh.size(), "Error insert same key - wrong size");

        System.out.println("TEST INSERT PASSED!");
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

    public static void deleteMinTests() {
        System.out.println("-----deleteMin test-----");
        FibonacciHeap fh = new FibonacciHeap();
        fh.insert(30, "C");
        fh.insert(50, "E");
        fh.insert(60, "F");
        fh.insert(10, "A");
        fh.insert(80, "H");
        fh.insert(70, "G");
        fh.insert(20, "B");
        fh.insert(40, "D");

        System.out.println("-----deleteMin after inserts test-----");
        fh.deleteMin();
        assertEquals(20, fh.min.key, "Error deleteMin - wrong min key");
        assertEquals(7, fh.size(), "Error deleteMin - wrong size");
        assertEquals(3, fh.numOfTrees, "Error deleteMin - wrong numOfTrees");
        printHeap(fh);

        System.out.println("-----deleteMin after deleteMin test-----");
        fh.deleteMin();
        assertEquals(30, fh.min.key, "Error deleteMin - wrong min key");
        assertEquals(6, fh.size(), "Error deleteMin - wrong size");
        assertEquals(2, fh.numOfTrees, "Error deleteMin - wrong numOfTrees");
        printHeap(fh);

        System.out.println("-----deleteMin after few actions test-----");
        FibonacciHeap fh2 = new FibonacciHeap();
        fh2.insert(3, "C");
        fh2.insert(5, "E");
        fh2.insert(60, "F");
        fh.meld(fh2);
        printHeap(fh);
        fh.deleteMin();
        assertEquals(5, fh.min.key, "Error deleteMin - wrong min key");
        assertEquals(8, fh.size(), "Error deleteMin - wrong size");
        assertEquals(1, fh.numOfTrees, "Error deleteMin - wrong numOfTrees");
        printHeap(fh);

        System.out.println("-----deleteMin 2 keys with minimum val test-----");
        fh.insert(5, "another 5");
        fh.deleteMin();
        assertEquals(5, fh.min.key, "Error deleteMin - wrong min key");
        assertEquals(8, fh.size(), "Error deleteMin - wrong size");
        assertEquals(1, fh.numOfTrees, "Error deleteMin - wrong numOfTr");
        printHeap(fh);

        System.out.println("-----deleteMin after deacreseKey test-----");
        fh.decreaseKey(fh.min, 2);
        fh.deleteMin();
        assertEquals(30, fh.min.key, "Error deleteMin - wrong min key");
        assertEquals(7, fh.size(), "Error deleteMin - wrong size");
        assertEquals(3, fh.numOfTrees, "Error deleteMin - wrong numOfTrees");
        printHeap(fh);

        System.out.println("TEST DELETE MIN PASSED!");
    }

    public static void deleteTests() {
        System.out.println("-----delete tests-----");
        FibonacciHeap fh = new FibonacciHeap();
        FibonacciHeap.HeapNode node1 = fh.insert(10, "A");
        FibonacciHeap.HeapNode node2 = fh.insert(20, "B");
        FibonacciHeap.HeapNode node3 = fh.insert(30, "C");
        FibonacciHeap.HeapNode node4 = fh.insert(50, "D");
        fh.delete(node3);
        printHeap(fh);

    }

    public static void cutsTests(){
        System.out.println("-----cuts tests-----");
        FibonacciHeap fh = new FibonacciHeap();
        FibonacciHeap.HeapNode node1 = fh.insert(10, "A");
        FibonacciHeap.HeapNode node2 = fh.insert(20, "B");
        FibonacciHeap.HeapNode node3 = fh.insert(30, "C");
        FibonacciHeap.HeapNode node4 = fh.insert(50, "D");
        FibonacciHeap.HeapNode node5 = fh.insert(60, "F");
        FibonacciHeap.HeapNode node6 = fh.insert(20, "another 20");
        FibonacciHeap.HeapNode node7 = fh.insert(30, "another 30");

        System.out.println("-----cuts after deleteMin test-----");
        fh.deleteMin();
        assertEquals(0, fh.totalCuts(), "Error deleteMin - wrong totalCuts");

        System.out.println("-----cuts after few deleteMin test-----");
        fh.deleteMin();
        fh.deleteMin();

        assertEquals(3, fh.totalCuts(), "Error deleteMin - wrong totalCuts");

        System.out.println("-----cuts after delete test-----");
        FibonacciHeap.HeapNode node8 = fh.insert(15, " ");
        FibonacciHeap.HeapNode node9 = fh.insert(46, " ");
        FibonacciHeap.HeapNode node10 = fh.insert(57, " ");
        FibonacciHeap.HeapNode node11 = fh.insert(68, " ");
        FibonacciHeap.HeapNode node12 = fh.insert(3, " ");

        fh.deleteMin();
        fh.delete(node3);
        assertEquals(5, fh.totalCuts(), "Error deleteMin - wrong totalCuts");

        System.out.println("-----cuts after decreaseKey test-----");
        //TO DO
    }

    public static void main(String[] args) {
        insertTests();
        FibHeapTester.testMeld();
        FibHeapTester.testMeldEmptyHeap();
        //FibHeapTester.testCascadingCut();
        deleteMinTests();
        //deleteTests();
//        cutsTests();


    }
}
