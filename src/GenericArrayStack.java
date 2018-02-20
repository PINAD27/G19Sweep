/**
*
* @author Faizaan Chishtie
* @author Aidan Charles
*/
public class GenericArrayStack<E> implements Stack<E> {

   private int pos = 0;
   private E[] stack;
   // Constructor
   @SuppressWarnings("unchecked")
    public GenericArrayStack( int capacity ) {
      this.stack = (E[])new Object[capacity];
    }

    // Returns true if this ArrayStack is empty
    public boolean isEmpty() {
      return this.pos == 0;
    }

    public void push( E elem ) {
      this.stack[this.pos] = elem;
      this.pos++;
    }

    public E pop() {
      E elem = this.stack[this.pos-1];
      this.stack[this.pos-1] = null;
      return elem;
    }

    public E peek() {
      E top = pop();
      push(top);
      return top;
    }
}
