package recfun

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CountChangeSuite extends FunSuite {
  import Main.countChange
  test("countChange: example given in instructions") {
    assert(countChange(4,List(1,2)) === 3)
  }

  test("countChange: sorted CHF") {
    assert(countChange(300,List(5,10,20,50,100,200,500)) === 1022)
  }

  test("countChange: no pennies") {
    assert(countChange(301,List(5,10,20,50,100,200,500)) === 0)
    assert(countChange(302,List(5,10,20,50,100,200,500)) === 0)
    assert(countChange(303,List(5,10,20,50,100,200,500)) === 0)
    assert(countChange(304,List(5,10,20,50,100,200,500)) === 0)
    //assert(countChange(305,List(5,10,20,50,100,200,500)) != 0)
  }
  
  test("if biggest coing bigger than number it can be taken out"){
    assert(countChange(4,List(5)) === 0)
  }
  
  test("if money is zero there is just one way to give change"){
    assert(countChange(0,List(1,10,5)) === 1)
  }

  test("if no coins there is no way to give change"){
    assert(countChange(4,List()) === 0)
  }


  test("countChange: unsorted CHF") {
    assert(countChange(300,List(500,5,50,100,20,200,10)) === 1022)
  }
}
