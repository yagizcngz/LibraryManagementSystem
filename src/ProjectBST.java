/**
 * Usage: Indexes books by title for sorted searching
 */
public class ProjectBST {

    private static class BSTNode {
        String key;
        Book value;
        BSTNode left, right;

        BSTNode(String key, Book value) {
            this.key = key;
            this.value = value;
            left = right = null;
        }
    }

    private BSTNode root;

    // Adds a new book in the correct alphabetical place
    public void insert(String key, Book value) {
        root = insertRec(root, key, value);
    }
    // Check where to place the new book according to alphabetical order
    private BSTNode insertRec(BSTNode root, String key, Book value) {
        if (root == null) return new BSTNode(key, value);

        if (key.compareToIgnoreCase(root.key) < 0)
            root.left = insertRec(root.left, key, value);
        else if (key.compareToIgnoreCase(root.key) > 0)
            root.right = insertRec(root.right, key, value);

        return root;
    }
    // Searching data
    public Book search(String key) {
        return searchRec(root, key);
    }

    private Book searchRec(BSTNode root, String key) {
        if (root == null) return null;
        if (root.key.equalsIgnoreCase(key)) return root.value;

        if (key.compareToIgnoreCase(root.key) < 0)
            return searchRec(root.left, key);
        else
            return searchRec(root.right, key);
    }
    // Print all the book by alphabetical order
    public void printInOrder() {
        inOrderRec(root);
    }

    private void inOrderRec(BSTNode root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.println(root.value.toString());
            inOrderRec(root.right);
        }
    }
}