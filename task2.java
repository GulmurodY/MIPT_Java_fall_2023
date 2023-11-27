import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    static class node {
        int value;
        node left;
        node right;

        public node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    static class BinarySearchTree {
        node root;

        public BinarySearchTree() {
            root = null;
        }

        public void insert(int value) {
            root = insertRecursive(root, value);
        }

        private node insertRecursive(node currentNode, int value) {
            if (currentNode == null) {
                currentNode = new node(value);
                return currentNode;
            }
            if (value < currentNode.value) {
                currentNode.left = insertRecursive(currentNode.left, value);
            } else if (value > currentNode.value) {
                currentNode.right = insertRecursive(currentNode.right, value);
            }
            return currentNode;
        }

        public void findLeaves(node node, ArrayList<Integer> result) {
            if (node != null) {
                if (node.left == null && node.right == null) {
                    result.add(node.value);
                } else {
                    findLeaves(node.left, result);
                    findLeaves(node.right, result);
                }
            }
        }
    }

    private static void readInputAndBuildTree(Scanner scanner, BinarySearchTree tree) {
        int inputValue = scanner.nextInt();
        while (inputValue != 0) {
            tree.insert(inputValue);
            inputValue = scanner.nextInt();
        }
    }

    private static void printLeavesList(ArrayList<Integer> leavesList) {
        for (int leafValue : leavesList) {
            System.out.println(leafValue);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BinarySearchTree tree = new BinarySearchTree();

        readInputAndBuildTree(scanner, tree);

        ArrayList<Integer> leavesList = new ArrayList<>();
        tree.findLeaves(tree.root, leavesList);

        printLeavesList(leavesList);
    }
}
//https://contest.yandex.ru/contest/51733/run-report/91797135/
