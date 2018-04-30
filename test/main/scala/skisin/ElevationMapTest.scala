package skisin
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ElevationMapTest {
  @Test
  def readMap(): Unit = {
    val map = ElevationMap.fromStream(getClass.getResourceAsStream("/doubledigitmap.txt"))

    assertEquals(4, map.width)
    assertEquals(4, map.height)

    assertEquals(Seq(4, 8, 7, 3, 2, 5, 19, 3, 6, 13, 2, 5, 4, 4, 1, 6), map.values.toSeq)
  }
}
