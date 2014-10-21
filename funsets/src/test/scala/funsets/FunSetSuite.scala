package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  
  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }
  
  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   * 
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   * 
   *   val s1 = singletonSet(1)
   * 
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   * 
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   * 
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
      assert(!contains(s1, 2), "Singleton")
      assert(!contains(s1, 3), "Singleton")
      assert(contains(s2, 2), "Singleton")
      assert(!contains(s2, 1), "Singleton")
      assert(!contains(s2, 3), "Singleton")
      assert(contains(s3, 3), "Singleton")
      assert(!contains(s3, 2), "Singleton")
      assert(!contains(s3, 1), "Singleton")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }
  
  test("intersec contains only common elements") {
    new TestSets {
      val s12 = union(s1, s2)
      val s13 = union(s1, s3)
      val s123 = union(s12,s13)
      val s12ints13 = intersect(s12, s13)
      val s12intss123 = intersect(s12, s123)
      
      assert(contains(s12ints13, 1), "intersect union 12 and union 13")
      assert(!contains(s12ints13, 2), "intersect union 12 and union 13")
      assert(!contains(s12ints13, 3), "intersect union 12 and union 13")
      
      assert(contains(s12intss123, 1), "intersect union 12 and union 13")
      assert(contains(s12intss123, 2), "intersect union 12 and union 13")
      assert(!contains(s12intss123, 3), "intersect union 12 and union 13")
    }
  }
  
  test("diff contains elements on s1 that are not in s2") {
    new TestSets {
      val s12 = union(s1, s2)
      val s13 = union(s1, s3)
      val itersecS12S13 = diff(s12, s13)
      
      assert(contains(itersecS12S13, 2), "itersecS12S13")
      assert(!contains(itersecS12S13, 1), "itersecS12S13")
      assert(!contains(itersecS12S13, 3), "itersecS12S13")
      assert(!contains(itersecS12S13, 4), "itersecS12S13")
      assert(!contains(itersecS12S13, 0), "itersecS12S13")
      assert(!contains(itersecS12S13, -1), "itersecS12S13")
    }
  }
  
  test("filter takes out elements from s1 for which p holds") {
    new TestSets {
      val s12 = union(s1, s2)
      val s13 = union(s1, s3)
      val s123 = union(s12, s13)
      
      val p = (x: Int) => x%2 == 0
      
      assert(contains(s123, 1), "itersecS12S13")
      assert(contains(s123, 2), "filter")
      assert(contains(s123, 3), "filter")
      
      assert(!contains(filter(s123, p), 1), "filter")
      assert(contains(filter(s123, p), 2), "filter")
      assert(!contains(filter(s123, p), 3), "filter")
      
      assert(contains(filter(s123, s12), 1), "filter")
      assert(contains(filter(s123, s12), 2), "filter")
      assert(!contains(filter(s123, s12), 3), "filter")
      
    }
  }

  test("forall checks that for all elements from s1 p holds") {
    new TestSets {
      val s12 = union(s1, s2)
      val s13 = union(s1, s3)
      val s123 = union(s12, s13)
      
      val p = (x: Int) => x < 4
     
      assert(contains(s123, 1), "contains 1")
      assert(contains(s123, 2), "contains 2")
      assert(contains(s123, 3), "contains 3")

      assert(p(1), "1<4")
      assert(p(2), "2<4")
      assert(p(3), "3<4")

      assert(forall(s123, p), "all elements smaller than 4")
      assert(!forall(s123, (x: Int) => x < 3), "all elements smaller than 3")
      assert(!forall(s123, (x: Int) => x > 3), "all elements bigger than 3")
    
    }
  }

  test("exists checks that for one element from s1 p holds") {
    new TestSets {
      val s12 = union(s1, s2)
      val s13 = union(s1, s3)
      val s123 = union(s12, s13)
      
      val p = (x: Int) => x < 4
     
      assert(contains(s123, 1), "contains 1")
      assert(contains(s123, 2), "contains 2")
      assert(contains(s123, 3), "contains 3")

      assert(p(1), "1<4")
      assert(p(2), "2<4")
      assert(p(3), "3<4")

      assert(exists(s123, p), "one element smaller than 4")
      assert(exists(s123, (x: Int) => x < 3), "one element smaller than 3")
      assert(exists(s123, (x: Int) => x < 2), "one element smaller than 2")
      assert(!exists(s123, (x: Int) => x < 1), "no element smaller than 1")
      assert(exists(s123, (x: Int) => x > 1), "one element bigger than 1")
    
    }
  }

  test("exists checks map works") {
    new TestSets {
      val s12 = union(s1, s2)
      val s13 = union(s1, s3)
      val s123 = union(s12, s13)
      
      val p = (x: Int) => 2*x
     
      assert(contains(s123, 1), "contains 1")
      assert(contains(s123, 2), "contains 2")
      assert(contains(s123, 3), "contains 3")

      assert(p(1)===2, "2*1")
      assert(p(2)===4, "2*2")
      assert(p(3)===6, "2*3")
      
      val s123mapP = map(s123,p)
      
      assert(!contains(map(s1,p), 1), "map(s1,p) not contains 1")
      assert(contains(map(s1,p), 2), "map(s1,p) contains 2")
      

      assert(!contains(s123mapP, 1), "s123mapP not contains 1")
      assert(contains(s123mapP, 2), "s123mapP contains 2")
      assert(!contains(s123mapP, 3), "s123mapP not contains 3")
      assert(contains(s123mapP, 4), "s123mapP contains 4")
      assert(contains(s123mapP, 6), "s123mapP contains 6")
 
      assert(forall(s123mapP, (x: Int) => x%2==0))
    }
  }
  
  test("displays contents of a set works") {
    new TestSets {
      val s12 = union(s1, s2)
      val s13 = union(s1, s3)
      val s123 = union(s12, s13)
      
      val p = (x: Int) => 2*x
     
      assert(contains(s123, 1), "contains 1")
      assert(contains(s123, 2), "contains 2")
      assert(contains(s123, 3), "contains 3")

      assert(p(1)===2, "2*1")
      assert(p(2)===4, "2*2")
      assert(p(3)===6, "2*3")
      
      val s123mapP = map(s123,p)
      
      assert(FunSets.toString(s1) === "{1}", "soString s1")
      assert(FunSets.toString(s12) === "{1,2}", "soString s12")
      assert(FunSets.toString(s123) === "{1,2,3}", "soString s123")
      assert(FunSets.toString(s123mapP) === "{2,4,6}", "soString s123mapP")
     
    }
  }
  
  
}
