package com.squarespace;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.LinkedList;

//import static org.junit.Assert.assertEquals;
//
//d
import org.junit.Test;


public class CacheTester {

    @Test
    public void testbasiccachesize() {
        InetCache testCache = new InetCache();
        assertEquals("testCache should be empty", true, testCache.isEmpty());
    }

}

