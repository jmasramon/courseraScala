package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
  trait TestTrees {
    val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
    val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
  }

  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }

  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }
 
  test("making code trees") {
	  val sampleTree = makeCodeTree(makeCodeTree(Leaf('x', 1), Leaf('e', 1)), Leaf('t', 2) )
	  assert(weight(sampleTree) === 4)
	  assert(chars(sampleTree) === List('x', 'e', 't'))
  }
  
  test("list of chars with occurence in a List[Char]") {
	  val chars = List('b','c','a','a','b','c','a','b','c','b','c','b','c')
	  assert(times(chars) === List(('b',5),('c',5),('a',3)))
  }
   
  test("making ordered leaf list from list of chars with occurence") {
	  val chars = List('b','c','a','a','b','c','a','b','c','b','c','b','c','c')

//	  println (makeOrderedLeafList(times(chars)))
	  assert(makeOrderedLeafList(times(chars)) === List(Leaf('a',3),Leaf('b',5),Leaf('c',6)))
  }
   
  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }
  
  test("makeOrderedLeafList of nil"){
    assert(makeOrderedLeafList(Nil) === List())    
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))
//    println(combine(leaflist))
    val leaflist2 = List(Leaf('e', 2), Leaf('t', 3), Leaf('x', 4))
    assert(combine(leaflist2) === List(Leaf('x',4), Fork(Leaf('e',2),Leaf('t',3),List('e', 't'),5) ))
//    println(combine(leaflist2))
  }
  
  test("combine a list of CodeTree until singleton") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(until(singleton, combine)(leaflist) === List(Fork(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3),Leaf('x',4),List('e', 't', 'x'),7)))
//    println(until(singleton, combine)(leaflist))
    val leaflist2 = List(Leaf('e', 2), Leaf('t', 3), Leaf('x', 4))
    assert(until(singleton, combine)(leaflist2) === List(Fork(Leaf('x',4),Fork(Leaf('e',2),Leaf('t',3),List('e', 't'),5),List('x', 'e', 't'),9)))
//    println(until(singleton, combine)(leaflist2))
  }
  
  test("create code tree from char list"){
    val initialCharList = List('b','c','a','a','b','c','a','b','c','b','c','b','c','c') // 6 cs, 5bs, 3 as
    assert(createCodeTree(initialCharList) === Fork(Leaf('c',6),Fork(Leaf('a',3),Leaf('b',5),List('a', 'b'),8),List('c', 'a', 'b'),14))
  }
  
  test("decode with custom tree"){
    val initialCharList = List('b','c','a','a','b','c','a','b','c','b','c','b','c','c') // 6 cs, 5bs, 3 as
    assert(decode(createCodeTree(initialCharList), List(1,0,0,0,1,0,1,1)) === List('a', 'c', 'c', 'a', 'b'))
  }
  
  
  test("decode secrect with french tree"){
    assert(decodedSecret === List('h', 'u', 'f', 'f', 'm', 'a', 'n', 'e', 's', 't', 'c', 'o', 'o', 'l'))
  }
 
  test("find letter code"){
    val initialCharList = List('b','c','a','a','b','c','a','b','c','b','c','b','c','c') // 6 cs, 5bs, 3 as
    assert(encodeLetter(createCodeTree(initialCharList), 'a', List()) === List(1,0))
    assert(encodeLetter(createCodeTree(initialCharList), 'b', List()) === List(1,1))
    assert(encodeLetter(createCodeTree(initialCharList), 'c', List()) === List(0))
  }
  
  test("encode text"){
    val initialCharList = List('b','c','a','a','b','c','a','b','c','b','c','b','c','c') // 6 cs, 5bs, 3 as
    val text =List('a','c','c','b')
    assert(encode(createCodeTree(initialCharList))(text) === List(1,0,0,0,1,1))
  }


  test("convertAcc a tree into a code table"){
    val tree = createCodeTree(List('b','c','a','a','b','c','a','b','c','b','c','b','c','c')) // 6 cs, 5bs, 3 as
    val unencodedChars =List('a','c','b')
    assert(convertAcc(tree, unencodedChars, List()) === List(('a',List(1, 0)), ('c',List(0)), ('b',List(1, 1))))
  }

  test("convert a tree into a code table"){
    val tree = createCodeTree(List('b','c','a','a','b','c','a','b','c','b','c','b','c','c')) // 6 cs, 5bs, 3 as
    assert(convert(tree) === List(('c',List(0)), ('a',List(1, 0)), ('b',List(1, 1))))
  }

  test("codeBits a char using a code table"){
    val tree = createCodeTree(List('b','c','a','a','b','c','a','b','c','b','c','b','c','c')) // 6 cs, 5bs, 3 as
    assert(codeBits(convert(tree))('a') === List(1, 0))
    assert(codeBits(convert(tree))('b') === List(1, 1))
    assert(codeBits(convert(tree))('c') === List(0))
  }
  
  test("quickEncode a very short text should be identity") {
    val tree = createCodeTree(List('b','c','a','a','b','c','a','b','c','b','c','b','c','c')) // 6 cs, 5bs, 3 as
    assert(quickEncode(tree)(List('a','c','c','b')) == List(1,0,0,0,1,1))
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }
}
