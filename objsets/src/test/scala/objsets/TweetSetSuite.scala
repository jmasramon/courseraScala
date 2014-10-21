package objsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TweetSetSuite extends FunSuite {
  trait TestSets {
    val set1 = new Empty
    val set2 = set1.incl(new Tweet("a", "a body", 20))
    val set3 = set2.incl(new Tweet("b", "b body", 20))
    val c = new Tweet("c", "c body", 7)
    val d = new Tweet("d", "d body", 9)
    val set4c = set3.incl(c)
    val set4d = set3.incl(d)
    val set5 = set4c.incl(d)
  }

  def asSet(tweets: TweetSet): Set[Tweet] = {
    var res = Set[Tweet]()
    tweets.foreach(res += _)
    res
  }

  def size(set: TweetSet): Int = asSet(set).size

  test("filter: on empty set") {
    new TestSets {
      assert(size(set1.filter(tw => tw.user == "a")) === 0)
    }
  }

  test("filter: a on set5") {
    new TestSets {
      assert(size(set5.filter(tw => tw.user == "a")) === 1)
    }
  }

  test("filter: 20 on set5") {
    new TestSets {
      assert(size(set5.filter(tw => tw.retweets == 20)) === 2)
    }
  }
  
  test("filter: different structure"){
   val set1p = new Empty
    val set2p = set1p.incl(new Tweet("a", "c body", 10))
    val set3p = set2p.incl(new Tweet("b", "b body", 20))
    val cp = new Tweet("c", "a body", 30)
    val dp = new Tweet("d", "d body", 9)
    val set4cp = set3p.incl(cp)
    val set4dp = set3p.incl(dp)
    val set5p = set4cp.incl(dp)
    val set6 = set5p.incl(new Tweet("e", "aa body", 9))
    val set7 = set6.incl(new Tweet("f", "bb body", 9))
    val set8 = set7.incl(new Tweet("g", "cc body", 9))
    assert(size(set5p.filter(tw => tw.retweets == 10)) === 1)
    assert(size(set5p.filter(tw => tw.retweets == 30)) === 1)
    assert(size(set5p.filter(tw => tw.retweets == 30)) === 1)
    assert(size(set5p.filter(tw => tw.retweets == 9)) === 1)
    assert(size(set5p.filter(tw => tw.retweets == 3)) === 0)
    assert(size(set8.filter(tw => tw.retweets == 9)) === 4)
  }

  test("union: set4c and set4d") {
    new TestSets {
      assert(size(set4c.union(set4d)) === 4)
    }
  }

  test("union: set 5 and empty set1") {
    new TestSets {
      assert(size(set5.union(set1)) === 4)
    }
  }

  test("union: empty set1 and set5") {
    new TestSets {
      assert(size(set1.union(set5)) === 4)
    }
  }
  
  test("mostRetweeted: of set5 is a") {
    new TestSets {
      assert(set5.mostRetweeted.user === "a")
    }
    val set1p = new Empty
    val set2p = set1p.incl(new Tweet("a", "c body", 10))
    val set3p = set2p.incl(new Tweet("b", "b body", 20))
    val cp = new Tweet("c", "a body", 30)
    val dp = new Tweet("d", "d body", 9)
    val set4cp = set3p.incl(cp)
    val set4dp = set3p.incl(dp)
    val set5p = set4cp.incl(dp)
    assert(set5p.mostRetweeted.user === "c")

  }

  test("descending: empty set") {
    new TestSets {
      val trends = set1.descendingByRetweet
      assert(trends.isEmpty)
    }
  }

  test("descending: set5") {
    new TestSets {
      val trends = set5.descendingByRetweet
      assert(!trends.isEmpty)
      assert(trends.head.user == "a" || trends.head.user == "b")
    }
  }
}
