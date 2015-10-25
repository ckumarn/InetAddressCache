package com.squarespace;

import com.squarespace.AddressCache;
import java.net.InetAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;


public class InetCache implements AddressCache {
    // Actual cache, where head is most recent elements and tail is oldest
    protected LinkedList<InetAddress> cacheList;

    // boolean to determine if cache is still valid
    protected boolean closed;

    // timer for cleanup
    Timer cleanupTimer;

//    HashMap to check for existence of element; trades runtime for space complexity
//    HashMap<InetAddress,InetAddress> cacheExistence = new HashMap<InetAddress, InetAddress>();

    /**
    * Overloaded Constructor: create cache with default 5 second cleanup period
    */
    public InetCache() {
       this(5);
    }

    /** 
    * Overloaded Constructor: create cache with specified cleanup period
    */
    public InetCache(int seconds) {
        this.cacheList = new LinkedList<InetAddress>();
        this.closed = false;
        this.cleanupTimer = new Timer();
        this.cleanupTimer.schedule(new removeTask(), 0, seconds * 1000);
    }

    /**
    * TimerTask that for cleanupTimer that cleans cache
    */
    class removeTask extends TimerTask {
        public void run() {
            if (!closed) {
                removeOldest();   // call safe remove function of cache
            }
        }
    }

    /**
     * Adds the given {@link InetAddress} and returns {@code true} on success.
     */
    public boolean offer(InetAddress address) {
        if (closed) {return false;}
        synchronized(cacheList) {
            if (cacheList.contains(address)) {
                cacheList.remove(address);
            }
            cacheList.addFirst(address);
            cacheList.notify();     // wakes up any waiting threads
        }
        return true;
    }

    /**
     * Returns {@code true} if the given {@link InetAddress}
     * is in the {@link AddressCache}.
     */
    public boolean contains(InetAddress address) {
        if (closed) {return false;}
        return cacheList.contains(address);
    }

    /**
     * Removes the given {@link InetAddress} and returns {@code true}
     * on success.
     */
    public boolean remove(InetAddress address) {
        if (closed) {return false;}
        if (cacheList.contains(address)) {
            cacheList.remove(address);
            return true;
        }
        return false;
    }

    /**
     * Returns the most recently added {@link InetAddress} and returns
     * {@code null} if the {@link AddressCache} is empty.
     */
    public InetAddress peek() {
        if (closed) { return null; }
        return cacheList.peekFirst();
    }

    /**
     * Removes and returns the most recently added {@link InetAddress} and
     * returns {@code null} if the {@link AddressCache} is empty.
     */
    public InetAddress remove() {
        if (closed) { return null; }
        return cacheList.pollFirst();
    }


    /**
     * Retrieves and removes the most recently added {@link InetAddress},
     * waiting if necessary until an element becomes available.
     */
    public InetAddress take() throws InterruptedException {
        if (closed) { return null; }
        synchronized(cacheList) {
            while (cacheList.size() == 0) {
                cacheList.wait();   // waits to be woken up by offer()
            }
        }
        return cacheList.pollFirst();
    }

    /**
     * Closes the {@link AddressCache} and releases all resources.
     * Always be sure to close() cache at end of use or else timer will run indefinitely
     */
    public void close() {
        cacheList = null;
        closed = true;
        
        cleanupTimer.cancel();
        cleanupTimer.purge();
    }

    /**
     * Returns the number of elements in the {@link AddressCache}.
     */
    public int size() {
        if (closed) { return -1; }
        return cacheList.size();
    }

    /**
     * Returns {@code true} if the {@link AddressCache} is empty.
     */
    public boolean isEmpty() {
        if (closed) { return true; }
        return cacheList.size() == 0;
    }

    /**
    * Returns and removes least recently added {@link InetAddress}
    */
    public InetAddress removeOldest() {
        if (closed) {return null; }
        return cacheList.pollLast();
    }

}