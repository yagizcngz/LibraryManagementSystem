import java.util.Random;

/**
 * Manages all Projects data structures.
 */
public class LibrarySystem {
    // Data Structures
    private final ProjectHashTable<String, Book> bookCatalog;
    private final ProjectHashTable<String, Member> memberDirectory;
    private final ProjectBST titleIndex;
    private final ProjectPriorityQueue<Book> popularBooks;
    private final ProjectStack<String> actionHistory;

    private static final long STUDENT_ID = 230315076L;

    public LibrarySystem() {
        this.bookCatalog = new ProjectHashTable<>();
        this.memberDirectory = new ProjectHashTable<>();
        this.titleIndex = new ProjectBST();
        this.popularBooks = new ProjectPriorityQueue<>();
        this.actionHistory = new ProjectStack<>();

        initializeDataWithID();
    }
    // using my student ID as a seed for random numbers
    private void initializeDataWithID() {
        System.out.println(">>> Initializing with Student ID: " + STUDENT_ID);
        Random rand = new Random(STUDENT_ID);

        int initialCount = (int)(STUDENT_ID % 5) + 5;

        for (int i = 0; i < initialCount; i++) {
            String id = "B" + (1000 + i);
            char letter = (char)('A' + rand.nextInt(26));
            String title = "Course " + letter;
            addBookInternal(id, title);
        }
    }

    private void addBookInternal(String id, String title) {
        Book newBook = new Book(id, title);
        bookCatalog.put(id, newBook);
        titleIndex.insert(title, newBook);
        popularBooks.add(newBook);
    }

    public void addBook(String id, String title) {
        // checking if book ID contains only integers
        for (int i = 0; i < id.length(); i++) {
            char c = id.charAt(i);
            if (!Character.isDigit(c)) {
                System.out.println("Error: Invalid Book ID '" + id + "'. Book IDs must contain only integers.");
                return;
            }
        }

        // checking if there is duplicate book ID
        if (bookCatalog.containsKey(id)) {
            Book existing = bookCatalog.get(id);
            System.out.println("Error: Book ID '" + id + "' is already used by \"" + existing.title + "\".");
            return;
        }

        addBookInternal(id, title);
        // action history for undoing action later
        actionHistory.push("ADD_BOOK:" + id);
        System.out.println("Success: Added \"" + title + "\"");
    }

    public void registerMember(String id, String name) {
        // check if ID contains only integers
        for (int i = 0; i < id.length(); i++) {
            char c = id.charAt(i);
            if (!Character.isDigit(c)) {
                System.out.println("Error: Invalid Member ID '" + id + "'. IDs must contain only integers.");
                return;
            }
        }

        // check if Name contains any numbers
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isDigit(c)) {
                System.out.println("Error: Invalid Name '" + name + "'. Names cannot contain numbers.");
                return;
            }
        }

        // checking if there is duplicate Member ID
        if (memberDirectory.containsKey(id)) {
            Member existing = memberDirectory.get(id);
            System.out.println("Error: Member ID '" + id + "' is already taken by " + existing.name + ".");
            return;
        }

        // if all checks pass, register the member
        Member m = new Member(id, name);
        memberDirectory.put(id, m);
        actionHistory.push("REG_MEMBER:" + id);
        System.out.println("Success: Registered " + name);
    }

    public void borrowBook(String memId, String bookId) {
        Member mem = memberDirectory.get(memId);
        Book book = bookCatalog.get(bookId);

        // checking if IDs are registered
        if (mem == null) {
            System.out.println("Error: Member ID '" + memId + "' not found.");
            return;
        }
        if (book == null) {
            System.out.println("Error: Book ID '" + bookId + "' not found.");
            return;
        }

        if (book.isAvailable) {
            book.isAvailable = false;

            // increase popularity
            book.borrowCount++;

            // telling the heap to move this book up
            popularBooks.updatePriority(book);

            mem.borrowedBooks.add(book);
            actionHistory.push("BORROW:" + memId + ":" + bookId);
            System.out.println("Success: " + mem.name + " borrowed \"" + book.title + "\"");
        } else {
            book.waitlist.enqueue(mem);
            System.out.println("Notice: Book is currently checked out.");
            System.out.println("Action: " + mem.name + " has been added to the waitlist.");
        }
    }

    public void returnBook(String memId, String bookId) {
        Member mem = memberDirectory.get(memId);
        Book book = bookCatalog.get(bookId);

        // checking if the ID is valid
        if (mem == null) {
            System.out.println("Error: Cannot return. Member ID '" + memId + "' does not exist.");
            return;
        }
        if (book == null) {
            System.out.println("Error: Cannot return. Book ID '" + bookId + "' does not exist.");
            return;
        }

        // checking if member doesn't have this book
        if (mem.borrowedBooks.remove(book)) {
            book.isAvailable = true;
            System.out.println("Success: \"" + book.title + "\" has been returned.");

            if (!book.waitlist.isEmpty()) {
                Member next = book.waitlist.dequeue();
                System.out.println(">>> Waitlist Alert! Auto-assigning book to next in line: " + next.name);
                borrowBook(next.memberId, bookId);
            }
        } else {
            System.out.println("Error: " + mem.name + " does not have the book \"" + book.title + "\" currently borrowed.");
        }
    }
    // checking waitlist of a specific book
    public void viewWaitlist(String bookId) {
        Book book = bookCatalog.get(bookId);

        if (book == null) {
            System.out.println("Error: Book ID '" + bookId + "' not found.");
            return;
        }

        if (book.waitlist.isEmpty()) {
            System.out.println("Waitlist for \"" + book.title + "\" is currently empty.");
        } else {
            // using the peek method
            Member nextInLine = book.waitlist.peek();
            int totalWaiting = book.waitlist.size();

            System.out.println("--- Waitlist Status ---");
            System.out.println("Book: " + book.title);
            System.out.println("Next person in line: " + nextInLine.name + " (ID: " + nextInLine.memberId + ")");
            System.out.println("Total people waiting: " + totalWaiting);
        }
    }

    public void searchByTitle(String title) {
        Book result = titleIndex.search(title);
        if (result != null) {
            System.out.println("Found: " + result);
        } else {
            System.out.println("Search Result: No book found with title \"" + title + "\"");
        }
    }

    public void showCatalog() {
        System.out.println("--- Library Catalog ---");
        titleIndex.printInOrder();
    }

    public void showPopular() {
        Book b = popularBooks.peek();
        if (b != null) {
            System.out.println("Most Popular Book: \"" + b.title + "\" with " + b.borrowCount + " borrows.");
        } else {
            System.out.println("No analytics data available yet.");
        }
    }

    public void undo() {
        if (actionHistory.isEmpty()) {
            System.out.println("Error: No actions to undo.");
            return;
        }
        String action = actionHistory.pop();
        // splits string into 2 parts by the : part using split() method
        String[] parts = action.split(":");

        switch (parts[0]) {
            case "ADD_BOOK":
                // remove book ID
                bookCatalog.remove(parts[1]);
                System.out.println("Undo: Removed book " + parts[1]);
                break;

            case "REG_MEMBER":
                // remove member ID
                memberDirectory.remove(parts[1]);
                System.out.println("Undo: Removed member " + parts[1]);
                break;

            case "BORROW":
                // reversing the borrow is returning the book
                returnBook(parts[1], parts[2]);
                System.out.println("Undo: Reversed borrow for " + parts[2]);
                break;

            default:
                System.out.println("Unknown action type: " + parts[0]);
                break;
        }
    }
}