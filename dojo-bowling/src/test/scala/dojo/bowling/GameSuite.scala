package dojo.bowling

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterEach, FunSuite}

@RunWith(classOf[JUnitRunner])
class GameSuite extends FunSuite with BeforeAndAfterEach {
  test("All of my balls are landing in the gutter"){
    val game = new Game
    for( i <- 0 until 20){
      game.roll(0)
    }
    assert(game.score() == 0)
  }

  test("Every throw scores 1") {
    val game = new Game
    for( i <- 0 until 20){
      game.roll(1)
    }
    assert(game.score() == 20)
  }

  test("Rolling a spare") {
      val game = new Game
      game.roll(5)
      game.roll(5)
      game.roll(7)
      for( i <- 3 until 20){
        game.roll(0)
      }
      assert(game.score() == 24)
    }

  test("Rolling a 6/4 spare") {
    val game = new Game
    game.roll(3)
    game.roll(6)
    game.roll(4)
    game.roll(1)
    for( i <- 4 until 20){
      game.roll(0)
    }
    assert(game.score() == 14)
  }

  test("Rolling a strike") {
    val game = new Game
    game.roll(10)
    game.roll(2)
    game.roll(4)
    for( i <- 3 until 20){
      game.roll(0)
    }
    assert(game.score() == 22)
  }

  test("Rolling a strike twice") {
    val game = new Game
    game.roll(10)
    game.roll(10)
    game.roll(2)
    game.roll(4)
    for( i <- 4 until 20){
      game.roll(0)
    }
    assert(game.score() == 44)
  }

  test("Perfect game") {
    val game = new Game
    for( i <- 0 until 12){
      game.roll(10)
    }
    assert(game.score() == 300)
  }
}
