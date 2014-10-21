package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 | r == 0 | c == r) 1 else 
      pascal(c-1,r-1) + pascal(c,r-1)
  }

	//if chars.isEmpty    
	def quantitize(char: Char): Int = {
	    (if (char == '(') 1 else 
	      if (char == ')') -1 else
	        0)
	}
	
	def numericBalance(chars: List[Char], tmpCount: Int): Int = {
	  try{
		  //TODO: should check the intermediate value and exit with -1 if it is ever negative
		  if (tmpCount < 0) -1 else
			  quantitize(chars.head) + numericBalance(chars.tail, tmpCount+quantitize(chars.head))
	  } catch {
	    case e: NoSuchElementException => {
	      if (e.toString() == "head of empty list") 0
	      else 0
	    }
	  }
	}

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    (numericBalance(chars, 0) == 0)
  }
  
  def smallestCoin(coins: List[Int], tmpMin: Int): Int = {
	  try{
	    if (tmpMin == 0) smallestCoin(coins.tail, coins.head) else
			if (coins.head < tmpMin) smallestCoin(coins.tail, coins.head) else
			  smallestCoin(coins.tail, tmpMin)
			  } catch {
			    case e: NoSuchElementException => {
			    	tmpMin
			    }
			  }
  }

  def biggestCoin(coins: List[Int], tmpMax: Int): Int = {
	  try{
	    if (tmpMax == 0) biggestCoin(coins.tail, coins.head) else
			if (coins.head > tmpMax) biggestCoin(coins.tail, coins.head) else
			  biggestCoin(coins.tail, tmpMax)
			  } catch {
			    case e: NoSuchElementException => {
			    	tmpMax
			    }
			  }
  }

  /**
   * Exercise 3
   * TODO: Remember that there is a clue about sorting being important
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    val sortedCoins = coins.sortWith((x, y) => x > y) //TODO: sort decreasing
    
	if (money == 0) 1 else	    
	  if (coins.isEmpty || money < 0 || (money%10 != 0 && money%10 < smallestCoin(coins, 0))) 0 else
	      if (sortedCoins.head > money) countChange(money, sortedCoins.tail) else
	        countChange(money, coins.tail) + countChange(money-coins.head, coins)
  }
  
}
