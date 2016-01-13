package evil.ducks.monocle

import monocle.Lens
import monocle.std.map._
import monocle.function.At.at

import org.scalatest.{Matchers, FlatSpec}

class MapTest extends FlatSpec with Matchers {
  val medals = Map("first" → "gold", "second" → "silver", "third" → "bronze")

  "A simple map" should "be viewed through a lens" in {
    val _first: Lens[Map[String, String], Option[String]] = at("first")
    _first.get(medals) should be(Some("gold"))
  }
}
