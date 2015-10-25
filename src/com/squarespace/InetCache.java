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
    protected LinkedList<InetAddress> cacheList = new LinkedList<InetAddress>();

    // boolean to determine if cache is still valid
    protected boolean closed = false;

    // timer for cleanup
    Timer cleanupTimer = new Timer();

    // HashMap to check for existence of element; trades runtime for space complexity
    HashMap<InetAddress,InetAddress> cacheExistence = new HashMap<InetAddress, InetAddress>();

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
        this.cleanupTimer.schedule(new removeTask(), seconds * 1000, seconds * 1000);
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
     * Runtime: O(1)
     */
    public boolean offer(InetAddress address) {
        if (closed) {return false;}
        synchronized(cacheList) {
            if (cacheExistence.containsKey(address)) {
                cacheList.remove(address);
            }
            cacheExistence.put(address,address);
            cacheList.addFirst(address);
            cacheList.notify();     // wakes up any waiting threads
        }
        return true;
    }

    /**
     * Returns {@code true} if the given {@link InetAddress}
     * is in the {@link AddressCache}.
     * Runtime: O(1)
     */
    public boolean contains(InetAddress address) {
        if (closed) {return false;}
        return cacheExistence.containsKey(address);
    }

    /**
     * Removes the given {@link InetAddress} and returns {@code true}
     * on success.
     * Runtime: O(n)
     */
    public boolean remove(InetAddress address) {
        if (closed) {return false;}
        if (cacheExistence.containsKey(address)) {
            cacheExistence.remove(address);
            cacheList.remove(address);
            return true;
        }
        return false;
    }

    /**
     * Returns the most recently added {@link InetAddress} and returns
     * {@code null} if the {@link AddressCache} is empty.
     * Runtime: O(1)
     */
    public InetAddress peek() {
        if (closed) { return null; }
        return cacheList.peekFirst();
    }

    /**
     * Removes and returns the most recently added {@link InetAddress} and
     * returns {@code null} if the {@link AddressCache} is empty.
     * Runtime: O(1)
     */
    public InetAddress remove() {
        if (closed) { return null; }
        InetAddress retVal = cacheList.pollFirst();
        if (retVal != null) {
            cacheExistence.remove(retVal);
        }
        return retVal;
    }


    /**
     * Retrieves and removes the most recently added {@link InetAddress},
     * waiting if necessary until an element becomes available.
     * Runtime: O(1)
     */
    public InetAddress take() throws InterruptedException {
        if (closed) { return null; }
        synchronized(cacheList) {
            while (cacheList.size() == 0) {
                cacheList.wait();   // waits to be woken up by offer()
            }
        }
        InetAddress retVal = cacheList.pollFirst();
        cacheExistence.remove(retVal);
        return retVal;
    }

    /**
     * Closes the {@link AddressCache} and releases all resources.
     * Always be sure to close() cache at end of use or else timer will run indefinitely
     * Runtime: O(1)
     */
    public void close() {
        cacheList = null;
        cacheExistence = null;
        closed = true;
        
        cleanupTimer.cancel();
        cleanupTimer.purge();
    }

    /**
     * Returns the number of elements in the {@link AddressCache}.
     * Runtime: O(1)
     */
    public int size() {
        if (closed) { return -1; }
        return cacheList.size();
    }

    /**
     * Returns {@code true} if the {@link AddressCache} is empty.
     * Runtime: O(1)
     */
    public boolean isEmpty() {
        if (closed) { return true; }
        return cacheList.size() == 0;
    }

    /**
    * Returns and removes least recently added {@link InetAddress}
    * Runtime: O(1)
    */
    public InetAddress removeOldest() {
        if (closed) {return null; }
        InetAddress retVal = cacheList.pollLast();
        if (retVal != null) {
            cacheExistence.remove(retVal);
        }
        return retVal;
    }

}