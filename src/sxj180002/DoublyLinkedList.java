/** @author Sakshi Jain, Prit Thakkar
 *  DoublyLinkedList linked list: for educational purpose only
 */
package sxj180002;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DoublyLinkedList<T> extends SinglyLinkedList<T> {

    static class Entry<E> extends SinglyLinkedList.Entry<E> {
        Entry<E> prev;
        Entry(E x, Entry<E> next, Entry<E> prev) {
            super(x, next);
            this.prev = prev;
        }
    }

    public DoublyLinkedList() {
        head = new DoublyLinkedList.Entry<>(null, null, null);
        tail = head;
        size = 0;
    }

    public Iterator<T> iterator() { return new DLLIterator(); }

    protected class DLLIterator extends SinglyLinkedList<T>.SLLIterator implements DoublyLinkedListIterator<T> {

        DLLIterator() {
            super();
        }

        public boolean hasPrevious(){
            return ((DoublyLinkedList.Entry)cursor).prev != null;
        }

        public T previous() {
            cursor = prev;
            if (((DoublyLinkedList.Entry)prev).prev == null) {
                throw new NoSuchElementException();
            } else {
                prev = ((DoublyLinkedList.Entry)prev).prev;
                ready = true;
            }
            return cursor.element;
        }

        public void remove() {

            super.remove();

            if (cursor != tail) {
                Entry<T> tmp = (Entry<T>) cursor.next;
                tmp.prev = (Entry<T>) cursor;
            }
            prev = (DoublyLinkedList.Entry)((Entry<T>) prev).prev;
        }

        public void add(T x){
            add(new Entry<T>(x, null, null));
        }

        //change add function as it will added after cursor
        public void add(Entry<T> ent) {
            ent.prev = (Entry<T>) cursor;
            if (cursor == tail) {
                tail = ent;
            } else {
                ent.next = cursor.next;
                Entry<T> tmp = (Entry<T>) cursor.next;
                tmp.prev = ent;
            }
            cursor.next = ent;
            cursor = cursor.next;
            ready = false;
            size++;
        }
    }

    public void add(T x){
        add(new Entry<T>(x, null, null));
    }

    //this is the method when we want to add element at the end of the list
    public void add(Entry<T> ent) {
        tail.next = ent;
        ent.prev = (DoublyLinkedList.Entry)tail;
        tail = tail.next;
        size++;
    }

    public static void main(String[] args) {
        int n = 10;
        if (args.length > 0){
            n = Integer.parseInt((args[0]));
        }

        DoublyLinkedList<Integer> dll = new DoublyLinkedList<>();
        for(int i = 1; i <= n; i++) {
            dll.add(Integer.valueOf(i));
        }
        dll.printList();

        Iterator<Integer> it = dll.iterator();
        Scanner in = new Scanner(System.in);
        whileloop:
        while (in.hasNext()) {
            int com = in.nextInt();
            switch (com) {
                case 1: // Move to next element and print it
                    if (it.hasNext()) {
                        System.out.println(it.next());
                        dll.printList();
                    } else {
                        break whileloop;
                    }
                    break;
                case 2: //Move to previous element and print it
                    if (((DoublyLinkedListIterator)it).hasPrevious()) {
                        System.out.println(((DoublyLinkedListIterator) it).previous());
                    } else {
                        break whileloop;
                    }
                    break ;
                case 3: //Add element after the cursor
                    ((DoublyLinkedListIterator)it).add(Integer.valueOf(in.nextInt()));
                    dll.printList();
                    break;
                case 4: // remove element
                    it.remove();
                    dll.printList();
                    break;
                case 5: //print list
                    dll.printList();
                    break ;
                default:
                    break whileloop;
            }
        }
        dll.printList();
        dll.unzip();
        dll.printList();
    }
}

/* Sample input:
1 1 1 2 3 77 2 4 5
Sample output:
0: 1 2 3 4 5 6 7 8 9 10
1
1
10: 1 2 3 4 5 6 7 8 9 10

1
2
10: 1 2 3 4 5 6 7 8 9 10

1
3
10: 1 2 3 4 5 6 7 8 9 10

2
2

3
77
11: 1 2 77 3 4 5 6 7 8 9 10

2
1

4
10: 2 77 3 4 5 6 7 8 9 10

5
10: 2 77 3 4 5 6 7 8 9 10
 */
