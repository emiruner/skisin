package skisin
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class RouteFinderTest {
  @Test
  def fastestRoute(): Unit = {
    val bestRoute = new RouteFinder(ElevationMap.fromStream(getClass.getResourceAsStream("/demomap.txt"))).findBestRoute()
    assertEquals(Seq(Coord(2, 1), Coord(1, 1), Coord(1, 2), Coord(2, 2), Coord(2, 3)), bestRoute)
  }
}
