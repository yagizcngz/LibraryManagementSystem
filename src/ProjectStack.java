/**
 * Usage: Stores action strings for the system undo feature
 */
public class ProjectStack<T> {
    private final ProjectLinkedList<T> list;

    public ProjectStack() {
        this.list = new ProjectLinkedList<>();
    }

    // Adding to the top
    public void push(T data) {
        list.addFirst(data);
    }

    // Removing from the top
    public T pop() {
        return list.removeFirst();
    }

    // To prevent error when there is nothing to undo in undo method
    public boolean isEmpty() {
        return list.isEmpty();
    }
}