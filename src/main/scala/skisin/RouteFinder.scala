package skisin

class RouteFinder(val elevations: ElevationMap) {
  def width: Int = elevations.width
  def height: Int = elevations.height

  private def compareRoutes(r1: Seq[Coord], r2: Seq[Coord]) =
    if (r1.lengthCompare(r2.length) > 0) {
      r1
    } else if (r1.lengthCompare(r2.length) < 0) {
      r2
    } else {
      if (elevations.totalDrop(r1) >= elevations.totalDrop(r2)) r1 else r2
    }

  private def findRoutes(coord: Coord): Seq[Seq[Coord]] = {
    def routesForDirection(direction: Direction) =
      elevations.goto(coord, direction).map(findRoutes).getOrElse(Seq())

    Seq(North, South, East, West).flatMap(routesForDirection) match {
      case Seq() => Seq(Seq(coord))
      case combinedRoutes => combinedRoutes.map(Seq(coord) ++ _)
    }
  }

  def findBestRoute(): Seq[Coord] =
    (for {
      x <- 0 until width
      y <- 0 until height
    } yield Coord(x, y))
      .map(findRoutes(_).reduceLeft(compareRoutes))
      .reduceLeft(compareRoutes)
}
