import java.util.*;


public class experiment {

    public static int[] generateRandomArray(int n) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }

        Collections.shuffle(list);

        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = list.get(i);
        }

        return array;
    }
    
    public static void exp1(int size){
        int[] arr = generateRandomArray(size);
        FibonacciHeap fh = new FibonacciHeap();
        // start time counting
        long startTime = System.nanoTime();

        for(int i = 0; i < size; i++){
            fh.insert(i, "info for: " + arr[i]);
        }
        fh.deleteMin();
        // end time counting
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("size: " + fh.size() + ", links: " + fh.totalLinks() + ", cuts: "+ fh.totalCuts() + ", time: " + duration);
    }

    public static void exp2(int size){
        int[] arr = generateRandomArray(size);
        FibonacciHeap fh = new FibonacciHeap();
        // start time counting
        long startTime = System.nanoTime();
        for(int i = 0; i < size; i++){
            fh.insert(i, "info for: " + arr[i]);
        }
        for (int i = 0; i < (size/2) ; i++) {
            fh.deleteMin();
        }

        //end time counting
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("size: " + fh.size() + ", links: " + fh.totalLinks() + ", cuts: "+ fh.totalCuts() + ", time: " + duration);
    }

    public static void exp3(int size){
        int[] arr = generateRandomArray(size);
        FibonacciHeap fh = new FibonacciHeap();
        int j = size;
        int min_size = (int) Math.pow(2, 5) - 1;
        // start time counting
        long startTime = System.nanoTime();
        for(int i = 0; i < size; i++){
            fh.insert(i, "info for: " + arr[i]);
        }

        while (j > min_size) {
            fh.deleteMin();
            j--;
        }

        //end time counting
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("size: " + fh.size() + ", links: " + fh.totalLinks() + ", cuts: "+ fh.totalCuts() + ", time: " + duration);
    }

    public static void main(String[] args) {
        int size;
        for(int i = 1; i <= 5; i++){
            size = (int)Math.pow(3, i + 7) - 1;
            exp1(size);
            exp2(size);
            exp3(size);
        }
    }
}
