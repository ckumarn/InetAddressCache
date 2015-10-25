package com.squarespace;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.Assert.*;
//
//d
import org.junit.Test;


public class InetCacheTests {

    @Test
    public void testbasiccachesizes() throws Exception{
    	System.out.println("--- testbasiccachesizes ---");
    	InetCache testCache = new InetCache();
        try {
	        assertTrue(testCache.isEmpty());

	        InetAddress addr = InetAddress.getByName("127.0.0.1");
	        InetAddress addr2 = InetAddress.getByName("127.0.0.2");
	        testCache.offer(addr);
	        testCache.offer(addr2);
	        assertTrue(!testCache.isEmpty());

	        assertEquals("Should have 2 elements", 2, testCache.size());

	        InetAddress retAddr = testCache.remove();
	        assertEquals("Should be same address", retAddr, addr2);
	        testCache.remove();
	        assertTrue(testCache.isEmpty());
	    } finally {
	    	testCache.close(); 	// always close the cache
	    }
    }

    @Test
    public void testautocleanup() throws Exception {
    	System.out.println("--- testautocleanup (~10 sec) ---");
    	InetCache testCache = new InetCache();
    	try {

	    	InetAddress addr = InetAddress.getByName("127.0.0.1");
	        InetAddress addr2 = InetAddress.getByName("127.0.0.2");

	        testCache.remove();

	        testCache.offer(addr);
	        testCache.offer(addr2);
	        
	        Thread.sleep(5500); 	// 5.5 seconds to allow for some hardware threading error
	        assertEquals("Shoud have 1 element", 1, testCache.size());
	        assertEquals("Should have removed the older element", addr2, testCache.peek());
	        Thread.sleep(5500); 	// 5.5 seconds to allow for some hardware threading error
	        assertTrue("testCache should be empty", testCache.isEmpty());
	    } finally {
	        testCache.close(); 	// always close the cache
	    }
    }

    @Test
    public void testmoveoffertofrontoflist() throws Exception {
    	System.out.println("--- testmoveoffertofrontoflist ---");
    	InetCache testCache = new InetCache();

    	try {

	    	InetAddress addr = InetAddress.getByName("127.0.0.1");
	        InetAddress addr2 = InetAddress.getByName("127.0.0.2");

	        testCache.offer(addr);
	        testCache.offer(addr2);
	        testCache.offer(addr);
	        assertEquals("Should change order after offering twice", addr, testCache.peek());

	    } finally {
        	testCache.close();	// always close the cache
        }
    }

    @Test
    public void testcontainsandremove() throws Exception {
    	System.out.println("--- testcontainsandremove ---");
    	InetCache testCache = new InetCache();

    	try {

	    	InetAddress addr = InetAddress.getByName("127.0.0.1");
	        InetAddress addr2 = InetAddress.getByName("127.0.0.2");
	        InetAddress addr3 = InetAddress.getByName("127.0.0.3");

	        testCache.offer(addr);
	        testCache.offer(addr2);
	        assertTrue(testCache.contains(addr));
	        assertTrue(testCache.contains(addr2));
	        assertTrue(!testCache.contains(addr3));

	        assertTrue(testCache.remove(addr2));
	        assertTrue(!testCache.contains(addr2));

	    } finally {
        	testCache.close();	// always close the cache
        }
    }

    @Test
    public void testtake() throws Exception {
    	System.out.println("--- testtake ---");
    	InetCache testCache = new InetCache();

    	try {

	    	InetAddress addr = InetAddress.getByName("127.0.0.1");
	    	testCache.offer(addr);
	    	assertTrue(!testCache.isEmpty());
	    	testCache.take();
	    	assertTrue(testCache.isEmpty());
	    } finally {
	    	testCache.close(); 	// always close the cache
	    }
    }



}

