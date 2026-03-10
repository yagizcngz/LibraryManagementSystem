/**
 * Used for holding data about the person and keeping a list of the books they currently have checked out
 */
public class Member {
    String memberId;
    String name;
    ProjectLinkedList<Book> borrowedBooks; // RENAMED

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        // ProjectLinkedList is used for borrowed books and initialization to avoid Null Pointer Exception
        this.borrowedBooks = new ProjectLinkedList<>(); // RENAMED
    }
}