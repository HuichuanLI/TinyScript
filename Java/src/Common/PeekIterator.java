package Common;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * Author : lihuichuan
 * Time   : 2020/11/28
 */
public class PeekIterator<T> implements Iterator<T> {

    private Iterator<T> it;

    private LinkedList<T> queueCache = new LinkedList<>();
    private LinkedList<T> stackPutBacks = new LinkedList<>();

    private final static int CACHE_SIZE = 10;
    private T _endToken = null;

    

    public PeekIterator(Stream<T> stream) {
        it = stream.iterator();
    }


    public PeekIterator(Stream<T> stream, T endToken) {
        it = stream.iterator();
        _endToken = endToken;
    }


    public T peek() {
        if (this.stackPutBacks.size() > 0) {
            return this.stackPutBacks.getFirst();
        }
        if (!it.hasNext()) {
            return null;
        }
        T val = next();
        this.putBack();
        return val;

    }

    // 缓存:A -> B -> C -> D
    // 放回:D -> C -> B -> A

    public void putBack() {
        if (this.queueCache.size() > 0) {
            this.stackPutBacks.push(this.queueCache.pollLast());
        }
    }

    @Override
    public boolean hasNext() {
        return this.stackPutBacks.size() > 0 || it.hasNext();
    }

    @Override
    public T next() {
        T val = null;
        if (this.stackPutBacks.size() > 0) {
            val = this.stackPutBacks.pop();
        } else {
            val = it.next();
        }
        while (queueCache.size() > CACHE_SIZE - 1) {
            queueCache.pop();
        }
        queueCache.add(val);
        return val;
    }
}
