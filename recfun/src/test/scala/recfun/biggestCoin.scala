package recfun

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BiggestCoinSuite extends FunSuite {
  import Main.biggestCoin
  test("biggestCoin: example given in instructions") {
    assert(biggestCoin(List(1,2,3), 0) === 3)
  }

  test("biggestCoin: sorted CHF") {
    assert(biggestCoin(List(5,10,20,50,100,200,500), 0) === 500)
  }

  test("biggestCoin: unsorted CHF") {
    assert(biggestCoin(List(500,5,50,100,20,200,10), 0) === 500)
  }
  
  test("biggestCoin: coherent with List.sorted") {
    assert(biggestCoin(List(500,5,50,100,20,200,10), 0) === List(500,5,50,100,20,200,10).sortWith((x, y) => x > y).head)
  }
  
}
