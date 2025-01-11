public class FibHeapTester {
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
    public static void main(String[] args) {
        FibonacciHeap fh = new FibonacciHeap();
        insertTests(fh);


    }
}
