package forcomp

object proves {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(76); 
  println("Welcome to the Scala worksheet");$skip(42); 
  
  println(List(10, 20), List(3, 4, 5));$skip(42); 
  val zp = List(10, 20) zip List(3, 4, 5);System.out.println("""zp  : List[(Int, Int)] = """ + $show(zp ));$skip(49); val res$0 = 
 (List(10, 20), List(3, 4, 5)).zipped.map(_ * _);System.out.println("""res0: <error> = """ + $show(res$0))}
  
  
}
