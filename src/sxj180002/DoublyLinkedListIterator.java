/** @author Sakshi Jain, Prit Thakkar
 *  DoublyLinkedListIterator: for educational purpose only
 */

package sxj180002;

public interface DoublyLinkedListIterator<T> {
    boolean hasPrevious();
    T previous();
    void add(T x);
}
