/**
 * Usage: Manages student waitlists for books
 */
public class ProjectQueue<T> {
    private final ProjectLinkedList<T> list;

    public ProjectQueue() {
        this.list = new ProjectLinkedList<>();
    }

    // Adding to the back
    public void enqueue(T data) {
        list.add(data);
    }

    // Adding to the front
    public T dequeue() {
        return list.removeFirst();
    }

    // Look at the front element without removing it
    public T peek() {
        return list.getFirst();
    }
    // Check if the waitlist is empty before dequeueing them
    public boolean isEmpty() {
        return list.isEmpty();
    }
    // to show total people waiting in the waitlist
    public int size() {
        return list.size();
    }
}