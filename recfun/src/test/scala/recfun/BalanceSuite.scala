package recfun

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BalanceSuite extends FunSuite {
  import Main.balance

  test("balance: '(if (zero? x) max (/ 1 x))' is balanced") {
    assert(balance("(if (zero? x) max (/ 1 x))".toList))
  }

  test("balance: 'I told him ...' is balanced") {
    assert(balance("I told him (that it's not (yet) done).\n(But he wasn't listening)".toList))
  }

  test("balance: ':-)' is unbalanced") {
    assert(!balance(":-)".toList))
  }

  test("balance: counting is not enough") {
    assert(!balance("())(".toList))
  }
  
  test("balance: empty string") {
    assert(balance("".toList))
  }
  
  test("a series of complicated combinations"){
    assert(!balance(":-)hola)".toList))
    assert(!balance(":-)lola(())(())".toList))
    assert(balance("(((()())())(()()))".toList))
    assert(balance("gadsfd (a(gc(dag(asdf)ads(adsf))asdf(as)dfa)sdg(d()g(sd)a)f)asgasd".toList))
    assert(!balance("(((()())())(()())".toList))
    assert(!balance("(((()())))(()()))".toList))
    assert(!balance(")".toList))
    assert(!balance("(".toList))
    assert(balance("la lluvia en Sevilla es una pura maravilla".toList))
  }
}
