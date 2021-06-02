import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapLoader {
  private final String start = "^[#.X]*@{1}[#.X]*$";
  private final String stop = "^[#.@]*X{1}[#.@]*$";

  public char[][] mapLoad(String path) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
    int x = 0;
    int y = 0;
    int sizeMap;
    String l;

    StringBuilder stringBuilder = new StringBuilder();
    List<String> list = new ArrayList<>();

    while ((l = bufferedReader.readLine()) != null && l.length() != 0) {
      x++;
      sizeMap = l.length();
      if (y != 0 && sizeMap > y) {
        return null;
      } else {
        y = sizeMap;
        list.add(l);
        stringBuilder.append(l);
      }
    }
    bufferedReader.close();

    if (!checkMap(stringBuilder.toString())) {
      return null;
    }
    return getMap(x, y, list);
  }

  public void print(char[][] map) {
    Arrays.stream(map).forEach(System.out::println);
  }

  private boolean checkMap(String map) {
    return map.matches(start) && map.matches(stop);
  }

  private char[][] getMap(int x, int y, List<String> strings) {
    char[][] map = new char[x][y];
    for (int i = 0; i < x; i++) {
      int q = 0;
      for (int j = 0; j < strings.get(i).length(); j++) {
        map[i][j] = strings.get(i).charAt(q++);
      }
    }
    return map;
  }
}
