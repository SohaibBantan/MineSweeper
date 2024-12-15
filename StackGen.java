
public interface StackGen <T> {


    public void push(T o); // adds an object o to the top of a stack

    public T pop(); //  removes and returns from top of stack

    public T top(); // returns top

    public boolean isEmpty(); // returns boolean true when stack is empty

} 