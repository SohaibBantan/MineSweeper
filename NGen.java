public class NGen <T> {
  
    
    public NGen () {}

    public NGen (T o, NGen<T> link) {
        data = o;
        next = link;
    }


    public T getData() {
        return data;
    }

    public void setData(T o) {
        data = o;
    }

    public NGen<T> getNext() {
        return next;
    }

    public void setNext(NGen<T> link) {
        next = link;
    }


    private T data;
    private NGen<T> next;

}  
