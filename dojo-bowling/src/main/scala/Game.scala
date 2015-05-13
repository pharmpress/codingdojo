package dojo.bowling

class Game {

  var total = 0

  var throws: Seq[Int] = Seq()

  def score() = {
    scoreRec(throws)
  }

  def scoreRec(throws:Seq[Int]): Int ={
    throws match {
      case 10 :: y :: z :: tail => 10 + y + z + scoreRec(y :: z :: tail)
      case x :: y :: z :: tail if x + y == 10 => 10 + z + scoreRec(z :: tail)
      case x :: y :: tail => x + y + scoreRec(tail)

      case _ => 0
    }
  }

  def roll(i: Int) = throws = throws.:+(i)

}
