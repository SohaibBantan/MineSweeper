
public class Stack1Gen <T> implements StackGen <T> {

    

    public Stack1Gen () {}

    

    public void push(T o) {
        start = new NGen <T> (o, start);
    }

    public T pop() {
        if (start == null)
            throw new RuntimeException("Tried to pop an empty stack");
        else {
            T data = start.getData();
            start = start.getNext();
            return data;
        }
    }

    public T top() {
        if (start == null)
            return null;
        else return start.getData();
    }

    public boolean isEmpty() {
        if (start == null)
            return true;
        else return false;
    }


    private NGen <T> start = null;

}  
