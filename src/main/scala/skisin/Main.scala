package skisin

import java.io.FileInputStream

object Main {
  def main(args: Array[String]): Unit = {
    if (args.length < 1) {
      println("please give input file as argument")
    } else {
      println("loading map...")
      val elevations = ElevationMap.fromStream(new FileInputStream(args(0)))

      println("finding best route...")
      val best = new RouteFinder(elevations).findBestRoute()

      println(s"best route is: ${best.map(elevations.get).map(_.toString).mkString("-")}")
      println(s"route length is: ${best.length}")
      println(s"drop is: ${elevations.get(best.head) - elevations.get(best.last)}")
    }
  }
}
