
public interface QGen <T> {


    void add(T o); // adds an object o to a queue

    T remove(); // removes and returns the object with priority
 
    int length(); // returns size of queue

}  
