/*
 * MARIANA PINTO, nmec84792
 * Distributed Systems, UA, MIECT, 2020/2021
 * General Problems - Exercise 1:
 * Write a program that, given a string read from the keyboard, checks if it is a palindrome (a special
 * word which reads the same, whether one starts from the left and moves to the right, or the other way
 * around).
 * Suggestion – Build your solution using a FIFO and a stack.
 */

import java.util.Scanner;
/**
 *    Palindrome detection using a character stack and a character FIFO for temporary storage.
 */
public class palindrome {
    /**
     *    Main program, implemented by the <code>main</code> method of the data type.
     *
     *      @param args runtime parameter list
     */
    public static void main(String [] args){
        String word;

        //reads the word from keyboard
        Scanner mp = new Scanner(System.in);
        System.out.println("What is the word?");
        word = mp.next();
        // no input
        if (word==null) word="";

        /* its parallel storage (character by character) both in the stack and the FIFO */
        Stack stack = new Stack(word.length());     // stack instantiation
        FIFO fifo = new FIFO(word.length());        // FIFO instantiation
        for (int i=0; i<word.length(); i++){
            // A character is written into stack
            stack.push(word.charAt(i));
            // A character is written into it FIFO
            fifo.in(word.charAt(i));
        }

        /* successive parallel reading (character by character) of the stored values both in the stack and the FIFO
       and their comparision */
        for (int i = 0; i < word.length(); i++)
            // reads values from stack and FIFO and compares results
            if (stack.pop() != fifo.out()) {
                System.out.println("It is not a palindrome!");
                return;
            }
        System.out.println("It is a palindrome!");
    }
}