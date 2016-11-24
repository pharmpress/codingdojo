package examples

import language.experimental.macros
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.language.postfixOps
import scala.reflect.macros.whitebox
//import reflect.macros.Context

object DebugMacros {
  def hello(): Unit = macro hello_impl

  def hello_impl(c: whitebox.Context)(): c.Expr[Unit] = {
    import c.universe._
    reify { println("Hello World!") }
  }

  def printparam(param: Any): Unit = macro printparam_impl

  def printparam_impl(c: whitebox.Context)(param: c.Expr[Any]): c.Expr[Unit] = {
    import c.universe._
    reify { println(param.splice) }
  }

  def debug1(param: Any): Unit = macro debug1_impl

  def debug1_impl(c: whitebox.Context)(param: c.Expr[Any]): c.Expr[Unit] = {
    import c.universe._
    val paramRepExpr = c.Expr[String](Literal(Constant(show(param.tree))))
    reify { println(paramRepExpr.splice + " = " + param.splice) }
  }

  def debug(params: Any*): Unit = macro debug_impl

  def debug_impl(c: whitebox.Context)(params: c.Expr[Any]*): c.Expr[Unit] = {
    import c.universe._

    val trees = params.map { param =>
      param.tree match {
        // Keeping constants as-is
        // The c.universe prefixes aren't necessary, but otherwise Idea keeps importing weird stuff ...
        case c.universe.Literal(c.universe.Constant(const)) =>
          reify { print(param.splice) } tree
        case _ =>
          val paramRepExpr = c.Expr[String](Literal(Constant(show(param.tree))))
          reify { print(paramRepExpr.splice + " = " + param.splice) } tree
      }
    }

    // Inserting ", " between trees, and a println at the end.
    val separators = (1 until trees.size).map(_ => reify {
      print(", ")
    }.tree) :+ reify {
      println()
    }.tree
    val treesWithSeparators = trees.zip(separators).flatMap(p => List(p._1, p._2))

    c.Expr[Unit](Block(treesWithSeparators.toList, Literal(Constant(()))))
  }

  def printf1(format: String, params: Any*): Unit = macro printf1_impl

  def printf1_impl(c: whitebox.Context)(format: c.Expr[String], params: c.Expr[Any]*): c.Expr[Unit] = {
    import c.universe._
    val Literal(Constant(s_format: String)) = format.tree
    val evals = ListBuffer[ValDef]()
    def preCompute(value: Tree, tpe: Type): Ident = {
      val freshName = TermName(c.freshName("eval$"))
      evals += ValDef(Modifiers(), freshName, TypeTree(tpe), value)
      Ident(freshName)
    }
    val paramsStack = mutable.Stack[Tree](params map (_.tree): _*)
    val refs = s_format.split("(?<=%[\\w%])|(?=%[\\w%])") map {
      case "%d" => preCompute(paramsStack.pop, typeOf[Int])
      case "%s" => preCompute(paramsStack.pop, typeOf[String])
      case "%%" => Literal(Constant("%"))
      case part => Literal(Constant(part))
    }
    val stats = evals ++ refs.map(ref => reify(print(c.Expr[Any](ref).splice)).tree)
    c.Expr[Unit](Block(stats.toList, Literal(Constant(()))))
  }

}