package dojo.bowling

class Game {

  var total = 0

  var throws: Seq[Int] = Seq()

  def score() = {
    scoreRec(throws)
  }

  def scoreRec(throws:Seq[Int]): Int ={
      throws match {
        case 10 :: (tail @ y :: z :: rest) => 10 + y + z + (if (rest == Nil) 0 else scoreRec(tail))
        case x :: y :: (tail @ z :: rest) if x + y == 10 => 10 + z + (if (rest == Nil) 0 else scoreRec(tail))
        case x :: y :: tail => x + y + scoreRec(tail)
        case _ => 0
      }
  }

  def roll(i: Int) = throws = throws.:+(i)

}
