/*
Replace each node in binary tree with the sum of its inorder predecessor and successor */
package learnings.tree;

import java.util.ArrayList;

public class BinaryTreeSumPreSucc {

    Node head;

    public BinaryTreeSumPreSucc() {
        head = null;
    }

    private class WrapInt {
        int value;
        public WrapInt(int value) {
            this.value = value;
        }
    }

    public void preOrderTraversal(Node node) {
        if (node == null) return;
        System.out.println(node.value);
        preOrderTraversal(node.left);
        preOrderTraversal(node.right);
    }

    private void getInOrderArray(Node node, ArrayList<Integer> list) {
        if (node == null) return;
        getInOrderArray(node.left, list);
        list.add(node.value);
        getInOrderArray(node.right, list);
    }

    private void replaceTreeValueWithSum(Node node, ArrayList<Integer> list, WrapInt i) {
        if (node == null) return;
        replaceTreeValueWithSum(node.left, list, i);
        node.value = list.get(i.value - 1) + list.get(i.value + 1);
        i.value++;
        replaceTreeValueWithSum(node.right, list, i);
    }

    public void replaceNodeWithSumPreAndSucc(Node head) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        getInOrderArray(head,list);
        list.add(0);
        WrapInt theInt = new WrapInt(1);
        replaceTreeValueWithSum(head,list,theInt);
    }

    public static void main(String[] args) {
        BinaryTreeSumPreSucc tree = new BinaryTreeSumPreSucc();
        tree.head = new Node(1);
        tree.head.left = new Node(2);
        tree.head.right = new Node(3);
        tree.head.left.left = new Node(4);
        tree.head.left.right = new Node(5);
        tree.head.right.left = new Node(6);
        tree.head.right.right = new Node(7);
        System.out.println("Previous tree: ");
        tree.preOrderTraversal(tree.head);

        tree.replaceNodeWithSumPreAndSucc(tree.head);

        System.out.println("Sum tree: ");
        tree.preOrderTraversal(tree.head);
    }

}
