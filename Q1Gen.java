
public class Q1Gen <T> implements QGen <T> {

    public Q1Gen () {}
    public void add(T o) {

        if (size == 0) {
          front = new NGen <T> (o, null);
          rear = front;
        }
        else {
          rear.setNext(new NGen <T> (o, null));
          rear = rear.getNext();
        }
        size++;
    }

    public T remove() {

        T answer;

        if (size == 0)
          throw new RuntimeException("Removing from empty queue"); 
        
        answer = front.getData();
        front = front.getNext();
        size--;
        if (size == 0)
          rear = null;
        return answer;
    }

    public int length() {
        return size;
    }

    private int size;
    private NGen <T> front;
    private NGen <T> rear;

}  

