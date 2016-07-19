package examples

import org.scalatest.FunSuite

class CaseClassPersonTest extends FunSuite {
  test("apply & equals") {
    val pA1 = PersonA("Alex1", 12)
    val pA2 = PersonA("Alex", 12)
    assert(pA1 !== pA2)
    pA1.name = "Alex"
    assert(pA1 === pA2)

    val pB1 = PersonB("Alex1", 12)
    val pB2 = PersonB("Alex", 12)

    assert(pB1 !== pB2)
    pB1.name = "Alex"
    assert(pB1 === pB2)
  }

  test("hashCode") {
    val p1 = PersonB("Alex", 12)
    val p2 = PersonB("Alex1", 12)
    val p3 = PersonB("Alex1", 12)
    val personSet = Set(p1, p2, p3)
    assert(personSet.size === 2)
    assert(PersonB("Alex1", 12).hashCode === PersonB("Alex1", 12).hashCode)
  }

  test("copy") {
    val pA1 = PersonA("Alex1", 12).copy(name = "Alex", age = 11)
    val pA2 = PersonA("Alex", 11)
    assert(pA1 === pA2)

    val pB1 = PersonB("Alex1", 12).copy(name = "Alex", age = 11)
    val pB2 = PersonB("Alex", 11)
    assert(pB1 === pB2)
  }

  test("toString") {
    val pA1 = PersonA("Alex1", 12)
    val pB1 = PersonB("Alex1", 12)
    assert(pB1.toString === pA1.toString)
    assert(PersonB.toString === "PersonA")
    assert(PersonA.toString === "PersonA")
  }

  test("unapply") {
    PersonA("Alex1", 12) match {
      case PersonA(name, age) =>
        assert(name === "Alex1")
        assert(age === 12)
    }
    PersonB("Alex1", 12) match {
      case PersonB(name, age) =>
        assert(name === "Alex1")
        assert(age === 12)
    }
    null match {
      case PersonB(name, age) =>
        fail("should fail")
      case _ =>
    }
  }

  test("product") {
    val pA1 = PersonA("Alex1", 12)
    val pB1 = PersonB("Alex1", 12)
    assert(pB1.productPrefix === pA1.productPrefix)
    assert(pB1.productArity === pA1.productArity)
    assert(pB1.productElement(0) === pA1.productElement(0))
    assert(pB1.productElement(1) === pA1.productElement(1))
    assert(pA1.productIterator.toSeq === pB1.productIterator.toSeq)
    intercept[IndexOutOfBoundsException] {
      pA1.productElement(2)
    }
    intercept[IndexOutOfBoundsException] {
      pB1.productElement(2)
    }
  }

  test("tupled") {
    val pA1 = PersonA("Alex1", 12)
    val pB1 = PersonB("Alex1", 12)
    assert(PersonA.tupled(("Alex1", 12)) === pA1)
    assert(PersonB.tupled(("Alex1", 12)) === pB1)
  }

  test("curried") {
    val pA1 = PersonA("Alex1", 12)
    val pB1 = PersonB("Alex1", 12)
    assert(PersonA.curried("Alex1")(12) === pA1)
    assert(PersonB.curried("Alex1")(12) === pB1)
  }

  test("match") {
    val mediumCaseClass = MediumCaseClass.apply()
    mediumCaseClass match {
      case MediumCaseClass(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v) =>
      case _ => fail("should match")
    }

    val bigCaseClass = BigCaseClass.apply()
    bigCaseClass match {
      case BigCaseClass(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, zb, zc, zd) =>
      // matches ?
      case _ =>
        fail("should match")
    }
  }
}
