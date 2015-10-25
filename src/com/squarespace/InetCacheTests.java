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
        InetCache testCache = new InetCache();
        assertTrue(testCache.isEmpty());

        InetAddress addr = InetAddress.getByName("127.0.0.1");
        InetAddress addr2 = InetAddress.getByName("127.0.0.2");
        testCache.offer(addr);
        testCache.offer(addr2);
        assertTrue(!testCache.isEmpty());

        assertEquals("Shoud have 2 elements", 2, testCache.size());

        InetAddress retAddr = testCache.remove();
        assertEquals("Should be same address", retAddr, addr2);
        testCache.remove();
        assertTrue(testCache.isEmpty());
    }

}
