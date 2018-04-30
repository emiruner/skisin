package skisin
import java.io.InputStream

import scala.io.Source

case class Direction(dx: Int, dy: Int)
object North extends Direction(0, -1)
object South extends Direction(0, 1)
object East extends Direction(-1, 0)
object West extends Direction(1, 0)

case class Coord(x: Int, y: Int) {
  def goto(where: Direction): Coord = Coord(x + where.dx, y + where.dy)
}

class ElevationMap(val width: Int, val height: Int, initialValues: Seq[Int]) {
  if (initialValues.lengthCompare(width * height) != 0) {
    throw new IllegalArgumentException("width, height is not consistent with initial array size")
  }

  val values: Array[Int] = initialValues.toArray

  private def withinBounds(coord: Coord): Boolean =
    coord.x >= 0 && coord.x < width && coord.y >= 0 && coord.y < height

  def get(coord: Coord): Int = {
    if (!withinBounds(coord)) {
      throw new ArrayIndexOutOfBoundsException(s"$coord")
    }

    values(coord.y * width + coord.x)
  }

  def totalDrop(route: Seq[Coord]): Int = get(route.head) - get(route.last)

  def goto(from: Coord, direction: Direction): Option[Coord] =
    from.goto(direction) match {
      case to if withinBounds(to) && get(from) > get(to) => Some(to)
      case _ => None
    }
}

object ElevationMap {
  def fromStream(stream: InputStream): ElevationMap =
    try {
      val numbers = Source
        .fromInputStream(stream, "ISO-8859-1")
        .getLines()
        .mkString(" ")
        .split("\\s+")
        .filterNot(_.isEmpty)
        .filterNot(_ == "\n")
        .map(_.toInt)

      new ElevationMap(numbers(0), numbers(1), numbers.drop(2))
    } finally {
      stream.close()
    }
}
