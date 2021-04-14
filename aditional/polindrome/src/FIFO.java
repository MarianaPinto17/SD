/*
 * MARIANA PINTO, nmec84792
 * Distributed Systems, UA, MIECT, 2020/2021
 * General Problems - Exercise 1:
 * Write a program that, given a string read from the keyboard, checks if it is a palindrome (a special
 * word which reads the same, whether one starts from the left and moves to the right, or the other way
 * around).
 * Suggestion – Build your solution using a FIFO and a stack.
 */


public class FIFO {
    //storage
    private char [] fifo;
    //pointer to the first empty location
    private int inpointer;
    //pointer to the last occupied location
    private int outpointer;
    //signaling FIFO empty state
    private boolean empty;

    /**
     *   FIFO instantiation.
     *   The instantiation only takes place if the FIFO size is meaningful (greater than zero).
     *   No error is reported.
     *
     *     @param nElem FIFO size
     */
    public FIFO (int nElem) {
        // not empty
        if (nElem > 0){
            fifo = new char [nElem];
            inpointer = outpointer = 0;
            empty = true;
        }
    }

    /**
     *   FIFO insertion.
     *   A character is written into it.
     *   If the FIFO is full, nothing happens. No error is reported.
     *
     *    @param val character to be written
     */
    public void in (char val) {
        // not full or is empty
        if ((inpointer != outpointer) || empty){
            fifo[inpointer] = val;
            inpointer = (inpointer + 1) % fifo.length;
            empty = false;
        }
    }

    /**
     *   FIFO retrieval.
     *   A character is read from it.
     *   If the FIFO is empty, the <code>null</code> character is returned. No error is reported.
     *
     *    @return first character that was written
     */
    public char out(){
        // default returned character
        char val = '\0';
        if (!empty){
            val = fifo[outpointer];
            outpointer = (outpointer + 1) % fifo.length;
            empty = (inpointer == outpointer);
        }
        return val;
    }
}