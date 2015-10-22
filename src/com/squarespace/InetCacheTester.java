package com.squarespace;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class InetCacheTester {

   public static void main(String[] args) {
      // Result result = JUnitCore.runClasses(InetCacheTests.class);
      // for (Failure failure : result.getFailures()) {
      //    System.out.println(failure.toString());
      // }
      // System.out.println(result.wasSuccessful());
   		JUnitCore junit = new JUnitCore();
		Result result = junit.run(InetCacheTests.class);
		for (Failure failure : result.getFailures()) {
        	System.out.println(failure.toString());
      	}
		System.out.println("PASSED ALL TESTS: " + result.wasSuccessful());
   }
}  