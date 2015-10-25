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
		


		// test print outs
		// for (int i =1; i < 6; i++) {
		// 	// try {Thread.sleep(1000);} catch Exception;
		// 	try{
		// 		System.out.println("sleeping");
		// 		Thread.sleep(1000);
		// 	} catch (Exception e) {}
		// 	System.out.println("System running at second: " + i);
		// }
		// System.exit(0);
   }
}  