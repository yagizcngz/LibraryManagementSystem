import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibrarySystem sys = new LibrarySystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Library Menu (ID: 230315076) ---");
            System.out.println("1. Add Book");
            System.out.println("2. Register Member");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Book ");
            System.out.println("6. Show Catalog ");
            System.out.println("7. Show Popular Book ");
            System.out.println("8. Undo Last Action");
            System.out.println("9. View Waitlist"); // NEW
            System.out.println("10. Exit");           // MOVED

            System.out.print("Select Option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter Book ID: ");
                    String id = sc.nextLine();
                    System.out.print("Enter Title: ");
                    String t = sc.nextLine();
                    sys.addBook(id, t);
                    break;

                case "2":
                    System.out.print("Enter Member ID: ");
                    String mId = sc.nextLine();
                    System.out.print("Enter Name: ");
                    String n = sc.nextLine();
                    sys.registerMember(mId, n);
                    break;

                case "3":
                    System.out.print("Enter Member ID: ");
                    String bm = sc.nextLine();
                    System.out.print("Enter Book ID: ");
                    String bb = sc.nextLine();
                    sys.borrowBook(bm, bb);
                    break;

                case "4":
                    System.out.print("Enter Member ID: ");
                    String rm = sc.nextLine();
                    System.out.print("Enter Book ID: ");
                    String rb = sc.nextLine();
                    sys.returnBook(rm, rb);
                    break;

                case "5":
                    System.out.print("Enter Exact Title: ");
                    sys.searchByTitle(sc.nextLine());
                    break;

                case "6":
                    sys.showCatalog();
                    break;

                case "7":
                    sys.showPopular();
                    break;

                case "8":
                    sys.undo();
                    break;

                case "9":
                    System.out.print("Enter Book ID: ");
                    sys.viewWaitlist(sc.nextLine());
                    break;

                case "10":
                    System.out.println("Exiting System...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}