package project;
public class Stack {
	Node top;

    public void push(Voter voter) {
        Node newNode = new Node(voter);
        newNode.next = top;
        top = newNode;
    }

    public Node pop() {
        if (isEmpty()) {
            return null;
        }
        Node node = top;
        top = top.next;
        return node;
    }

    public boolean isEmpty() {
        return top == null;
    }
}
