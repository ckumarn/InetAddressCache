#Overview

You're provided with an interface for a fictional InetAddress cache. The cache has a "Last-In-First-Out" (LIFO) retrieval policy and a "First-In-First-Out" (FIFO) eviction policy. Methods such as peek(), remove() and take() retrieve the most recently added element and an internal cleanup task that in periodic intervals removes the oldest elements from the cache.


#Instructions

* The goal is to write the most beautiful and functionally correct code possible
* Create an Implementation for AddressCache
* Document the Asymptotic complexity of each method
* A cleanup task runs every 5 seconds to evict expired address's
* The caching time of an element must be configurable by the user
* Include any tests that you have written to check the correctness
* You should design for optimal asymptotic performance
* You're free to use any Java Technology including Java 8
* Please email your solution in a zip file with the format: AddressCache_YourName.zip


#Implementation

* InetCache implements AddressCache and is stored internally as a linked list of InetAddresses. New InetAddresses are added to the front of the list. LIFO retrieval handled by polling elements from the head of the list. FIFO eviction handled by polling elements from the tail of the list.
* Overloaded constructor allows for user to either specify their own cache eviction time, or use the default time of 5 seconds. Note: InetCache must always be closed() after usage or timer will continue to run.
* Eviction task handled by a Timer in the InetCache, and a TimerTask that calls the InetCache function which removes the oldest element.
* Unit testing done with JUnit.

* Upcoming: Hashmap for existence?


#Usage

*In src, compile with 
	> javac com/squarespace/*.java

* In src, run tests with 
	> java com/squarespace/InetCacheTester

