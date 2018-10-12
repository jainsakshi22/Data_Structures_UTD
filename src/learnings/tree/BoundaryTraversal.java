package learnings.tree;

public class BoundaryTraversal {
    Node head;

    public BoundaryTraversal() {
        head = null;
    }

    void printBoundaryTraversal(Node head) {

        if (head != null) {
            System.out.println(head.value);
            printLeft(head.left);

            //Split this to handle case when there is only root node
            printLeaves(head.left);
            printLeaves(head.right);

            printRight(head.right);
        }
    }

    private void printLeft(Node head) {

        if (head != null) {
            if (head.left != null) {
                System.out.println(head.value);
                printLeft(head.left);
            } else if (head.right != null) {
                System.out.println(head.value);
                printLeft(head.right);
            }
        }

    }

    private void printRight(Node head) {

        if (head != null) {
            if (head.right != null) {
                printRight(head.right);
                System.out.println(head.value);
            } else if (head.left != null) {
                printRight(head.left);
                System.out.println(head.value);
            }
        }
    }

    private void printLeaves(Node head) {

        if (head != null) {

            printLeaves(head.left);
            if (head.left == null && head.right == null) {
                System.out.println(head.value);
            }
            printLeaves(head.right);
        }
    }

    public static void main(String[] args) {
        BoundaryTraversal tree = new BoundaryTraversal();
        tree.head = new Node(20);
        tree.head.left = new Node(8);
        tree.head.left.left = new Node(4);
        tree.head.left.right = new Node(12);
        tree.head.left.right.left = new Node(10);
        tree.head.left.right.right = new Node(14);
        tree.head.left.right.right.left = new Node(3);
        tree.head.right = new Node(22);
        tree.head.right.right = new Node(25);
        tree.head.right.right.left = new Node(1);
        tree.head.right.right.left.left = new Node(2);
        tree.printBoundaryTraversal(tree.head);
    }
}
