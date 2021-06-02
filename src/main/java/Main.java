import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    String path = "src/main/resources/map.txt";
    MapLoader mapLoader = new MapLoader();
    char[][] map = mapLoader.mapLoad(path);
    if (map != null) {
      RouteFinder routeFinder = new Algorithm();
//      mapLoader.print(map);
      mapLoader.print(routeFinder.findRoute(map));
    }
  }
}
