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
        // add time counting
        for(int i = 0; i < size; i++){
            fh.insert(i, "info for: " + arr[i]);
        }
        fh.deleteMin();

        System.out.println("size: " + fh.size() + ", links: " + fh.totalLinks() + ", cuts: "+ fh.totalCuts());
    }

    public static void exp2(int size){
        int[] arr = generateRandomArray(size);
        FibonacciHeap fh = new FibonacciHeap();
        // add time counting
        for(int i = 0; i < size; i++){
            fh.insert(i, "info for: " + arr[i]);
        }
        for (int i = 0; i < (size/2) ; i++) {
            fh.deleteMin();
        }

        System.out.println("size: " + fh.size() + ", links: " + fh.totalLinks() + ", cuts: "+ fh.totalCuts());
    }


    public static void main(String[] args) {

    }
}
