import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FibonacciHeapTestChat {

    // בדיקות עבור insert
    @Test
    void testInsertSingleNode() {
        FibonacciHeap heap = new FibonacciHeap();
        FibonacciHeap.HeapNode node = heap.insert(5, "info");
        assertNotNull(node);
        assertEquals(5, node.key);
        assertEquals("info", node.info);
        assertEquals(1, heap.size());
    }

    @Test
    void testInsertDuplicateKeys() {
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(10, "info1");
        heap.insert(10, "info2");
        assertEquals(2, heap.size());
    }

    // בדיקות עבור findMin
    @Test
    void testFindMinEmptyHeap() {
        FibonacciHeap heap = new FibonacciHeap();
        assertNull(heap.findMin());
    }

    @Test
    void testFindMinAfterInsert() {
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(20, "info1");
        heap.insert(10, "info2");
        heap.insert(30, "info3");
        assertEquals(10, heap.findMin().key);
    }

    // בדיקות עבור deleteMin
    @Test
    void testDeleteMinOnEmptyHeap() {
        FibonacciHeap heap = new FibonacciHeap();
        heap.deleteMin();
        assertEquals(0, heap.size());
    }

    @Test
    void testDeleteMinSingleElement() {
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(10, "info");
        heap.deleteMin();
        assertEquals(0, heap.size());
        assertNull(heap.findMin());
    }

    // בדיקות עבור decreaseKey

    @Test
    void testDecreaseKeyValidScenario() {
        FibonacciHeap heap = new FibonacciHeap();
        FibonacciHeap.HeapNode node = heap.insert(20, "info");
        heap.insert(30, "info2");
        heap.decreaseKey(node, 15); // 20 -> 5
        assertEquals(5, heap.findMin().key);
    }

    // בדיקות עבור delete
    @Test
    void testDeleteSingleElement() {
        FibonacciHeap heap = new FibonacciHeap();
        FibonacciHeap.HeapNode node = heap.insert(10, "info");
        heap.delete(node);
        assertEquals(0, heap.size());
        assertNull(heap.findMin());
    }

    @Test
    void testDeleteNonMinimalElement() {
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(10, "info1");
        FibonacciHeap.HeapNode node = heap.insert(20, "info2");
        heap.delete(node);
        assertEquals(10, heap.findMin().key);
        assertEquals(1, heap.size());
    }

    // בדיקות עבור meld
    @Test
    void testMeldEmptyHeaps() {
        FibonacciHeap heap1 = new FibonacciHeap();
        FibonacciHeap heap2 = new FibonacciHeap();
        heap1.meld(heap2);
        assertEquals(0, heap1.size());
        assertNull(heap1.findMin());
    }

    @Test
    void testMeldWithNonEmptyHeap() {
        FibonacciHeap heap1 = new FibonacciHeap();
        FibonacciHeap heap2 = new FibonacciHeap();
        heap1.insert(10, "info1");
        heap2.insert(5, "info2");
        heap1.meld(heap2);
        assertEquals(2, heap1.size());
        assertEquals(5, heap1.findMin().key);
    }

    // בדיקות עבור size
    @Test
    void testSizeAfterMultipleOperations() {
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(10, "info1");
        heap.insert(20, "info2");
        heap.deleteMin();
        heap.insert(30, "info3");
        assertEquals(2, heap.size());
    }

    // בדיקות עבור numTrees
    @Test
    void testNumTreesAfterInsert() {
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(10, "info1");
        heap.insert(20, "info2");
        assertEquals(2, heap.numTrees());
    }

    @Test
    void testNumTreesAfterDeleteMin() {
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(10, "info1");
        heap.insert(20, "info2");
        heap.deleteMin();
        assertEquals(1, heap.numTrees());
    }

    // בדיקות עבור totalLinks
    @Test
    void testTotalLinksInitial() {
        FibonacciHeap heap = new FibonacciHeap();
        assertEquals(0, heap.totalLinks());
    }

    @Test
    void testTotalLinksAfterOperations() {
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(10, "info1");
        heap.insert(20, "info2");
        heap.insert(30, "info3");
        heap.deleteMin(); // אמור לבצע חיבור
        assertTrue(heap.totalLinks() > 0);
    }

    // בדיקות עבור totalCuts
    @Test
    void testTotalCutsInitial() {
        FibonacciHeap heap = new FibonacciHeap();
        assertEquals(0, heap.totalCuts());
    }

    @Test
    void testTotalCutsAfterDecreaseKey() {
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(10, "info1");
        heap.insert(2, "info2");
        heap.insert(30, "info3");
        FibonacciHeap.HeapNode node = heap.insert(50, "info4");
        heap.deleteMin();
        heap.decreaseKey(node, 30); // פעולה שמביאה לחיתוך

        assertTrue(heap.totalCuts() > 0);
    }
}
