package recfun

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SmallestCoinSuite extends FunSuite {
  import Main.smallestCoin
  test("smallestCoin: example given in instructions") {
    assert(smallestCoin(List(1,2,3), 0) === 1)
  }

  test("smallestCoin: sorted CHF") {
    assert(smallestCoin(List(5,10,20,50,100,200,500), 0) === 5)
  }

  test("smallestCoin: unsorted CHF") {
    assert(smallestCoin(List(500,5,50,100,20,200,10), 0) === 5)
  }
}
