import java.util.Arrays;

/**
 * Usage: Keeps track of "Most Popular Books"
 */
public class ProjectPriorityQueue<T extends Comparable<T>> {
    private T[] heap;
    private int size;

    @SuppressWarnings("unchecked")
    public ProjectPriorityQueue() {
        this.heap = (T[]) new Comparable[20];
        this.size = 0;
    }

    public void add(T element) {
        // if the array is full, we double the size
        if (size >= heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
        heap[size] = element;
        // place the new item to it's correct position
        heapUp(size);
        size++;
    }

    public T peek() {
        return size == 0 ? null : heap[0];
    }

    // check if the newly added child is more popular than the parent and swaps places if correct
    private void heapUp(int index) {
        int parentIndex = (index - 1) / 2;
        if (index > 0 && heap[index].compareTo(heap[parentIndex]) < 0) {
            swap(index, parentIndex);
            heapUp(parentIndex);
        }
    }
    // updates the position of a specific element, used for borrowCount
    public void updatePriority(T element) {
        for (int i = 0; i < size; i++) {
            // Find the element in the heap
            if (heap[i] == element) {
                // bubble it up to its new correct position
                heapUp(i);
                return;
            }
        }
    }

    // used to exchange two values in the array for bubbling up
    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}