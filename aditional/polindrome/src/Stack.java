/*
 * MARIANA PINTO, nmec84792
 * Distributed Systems, UA, MIECT, 2020/2021
 * General Problems - Exercise 1:
 * Write a program that, given a string read from the keyboard, checks if it is a palindrome (a special
 * word which reads the same, whether one starts from the left and moves to the right, or the other way
 * around).
 * Suggestion – Build your solution using a FIFO and a stack.
 */

/**
 * Stack that saves characters from a given string
 */
public class Stack {
    // storage
    private char [] stack;
    // pointer to the first empty location
    private int stackpointer;

    /**
     *   Stack instantiation.
     *   The instantiation only takes place if the stack size is meaningful (greater than zero).
     *   No error is reported.
     *
     *     @param nElem stack size -> number of elements of a string
     */
    public Stack(int nElem){
        if (nElem>0){
            stack = new char [nElem];
            stackpointer = 0;
        }
    }

    /**
     *   Stack push.
     *   A character is written into it.
     *   If the stack is full, nothing happens. No error is reported.
     *
     *    @param val character to be written
     */
    public void push (char val){
        // if first empty location < stack length
        if (stackpointer < stack.length){
            // writes char on stack[]
            stack[stackpointer] = val;
            // pointer increment
            stackpointer += 1;
        }
    }

    /**
     *   Stack pop.
     *   A character is read from it.
     *   If the stack is empty, the <code>null</code> character is returned. No error is reported.
     *
     *    @return last character that was written
     */
    public char pop () {
        // default returned character
        char val = '\0';
        // if stack not empty
        if (stackpointer > 0) {
            // pointer decrement
            stackpointer -= 1;
            val = stack[stackpointer];
        }
        return val;
    }
}