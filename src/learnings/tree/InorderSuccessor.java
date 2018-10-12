package learnings.tree;

class ListNode extends Node {
    Node next;

    public ListNode(int value) {
        super(value);
        this.next = null;
    }
}

public class InorderSuccessor {
    ListNode head, next;

    public InorderSuccessor() {
        head = null;
    }

    void populateNext(Node node)
    {
        ListNode listnode = null;
        if (node != null && node instanceof ListNode) {
            listnode = (ListNode)node;
        }
        // The first visited node will be the rightmost node
        // next of the rightmost node will be NULL
        if (listnode != null)
        {
            // First set the next pointer in right subtree
            populateNext(listnode.right);

            // Set the next as previously visited node in reverse Inorder
            listnode.next = next;

            // Change the prev for subsequent node
            next = listnode;

            // Finally, set the next pointer in left subtree
            populateNext(listnode.left);
        }
    }

    Node getStartNode(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public static void main(String[] args) {
        InorderSuccessor tree = new InorderSuccessor();
        tree.head = new ListNode(1);
        tree.head.left = new ListNode(2);
        tree.head.right = new ListNode(3);
        tree.head.left.left = new ListNode(4);
        //tree.head.left.right = new ListNode(5);
        tree.head.right.left = new ListNode(6);
        tree.head.right.right = new ListNode(7);

        tree.populateNext(tree.head);

        // Let us see the populated values
        ListNode ptr = (ListNode) tree.getStartNode(tree.head);
        while (ptr != null)
        {
            // -1 is printed if there is no successor
            int print = (ListNode)ptr.next != null ? ptr.next.value : -1;
            System.out.println("Next of " + ptr.value + " is: " + print);
            ptr = (ListNode) ptr.next;
        }
    }
}

