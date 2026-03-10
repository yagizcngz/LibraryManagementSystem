/**
 * Implements Comparable for Priority Queue
 */
public class Book implements Comparable<Book> {
    String bookId;
    String title;
    boolean isAvailable;
    int borrowCount;
    // stores the line of students waiting for this specific book, ProjectQueue is used for it
    ProjectQueue<Member> waitlist;

    public Book(String bookId, String title) {
        this.bookId = bookId;
        this.title = title;
        this.isAvailable = true;
        this.borrowCount = 0;
        // initializing the empty queue to avoid Null Pointer Exception
        this.waitlist = new ProjectQueue<>();
    }
    // checking if there are any errors with override
    @Override
    public int compareTo(Book other) {
        // create higher to lower order with (other,this) (by swapping their place) so most popular book goes to top
        return Integer.compare(other.borrowCount, this.borrowCount);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (Borrows: %d) - %s",
                bookId, title, borrowCount, isAvailable ? "Available" : "Checked Out");
    }
}