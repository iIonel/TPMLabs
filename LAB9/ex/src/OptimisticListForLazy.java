import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OptimisticListForLazy<T> {
    private Node head;

    public OptimisticListForLazy() {
        this.head = new Node(Integer.MIN_VALUE);
        this.head.next = new Node(Integer.MAX_VALUE);
    }

    public boolean add(T item) {
        int key = item.hashCode();
        while (true) {
            Node pred = this.head;
            Node current = pred.next;
            while (current.key < key) {
                pred = current;
                current = current.next;
            }
            pred.lock();
            try {
                current.lock();
                try {
                    if (validate(pred, current)) {
                        if (current.key == key) {
                            return false; // present
                        } else {
                            Node entry = new Node(item);
                            entry.next = current;
                            pred.next = entry;
                            return true; // not present
                        }
                    }
                } finally {
                    current.unlock();
                }
            } finally {
                pred.unlock();
            }
        }
    }

    public boolean remove(T item) {
        int key = item.hashCode();
        while (true) {
            Node pred = this.head;
            Node current = pred.next;
            while (current.key < key) {
                pred = current;
                current = current.next;
            }
            pred.lock();
            try {
                current.lock();
                try {
                    if (validate(pred, current)) {
                        if (current.key == key) {
                            pred.next = current.next;
                            return true;
                        } else {
                            return false;
                        }
                    }
                } finally {
                    current.unlock();
                }
            } finally {
                pred.unlock();
            }
        }
    }

    public boolean contains(T item) {
        int key = item.hashCode();
        while (true) {
            Node pred = this.head;
            Node current = pred.next;
            while (current.key < key) {
                pred = current;
                current = current.next;
            }
            try {
                pred.lock();
                current.lock();
                if (validate(pred, current)) {
                    return (current.key == key);
                }
            } finally {
                pred.unlock();
                current.unlock();
            }
        }
    }

    private boolean validate(Node pred, Node current) {
        Node entry = head;
        while (entry.key <= pred.key) {
            if (entry == pred)
                return pred.next == current;
            entry = entry.next;
        }
        return false;
    }

    private class Node {
        T item;
        int key;
        Node next;
        Lock lock;

        Node(T item) {
            this.item = item;
            this.key = item.hashCode();
            lock = new ReentrantLock();
        }

        Node(int key) {
            this.key = key;
            lock = new ReentrantLock();
        }

        void lock() {
            lock.lock();
        }

        void unlock() {
            lock.unlock();
        }
    }
}