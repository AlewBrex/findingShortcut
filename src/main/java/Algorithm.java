import java.util.PriorityQueue;

public class Algorithm implements RouteFinder {

  public class Cell {
    public int i;
    public int j;
    public int costX = 0;
    public int costY = 0;
    Cell parent;

    public Cell(int i, int j) {
      this.i = i;
      this.j = j;
    }
  }

  private static Cell[][] grid;
  private static int startI;
  private static int startJ;
  private static int stopI;
  private static int stopJ;
  private static PriorityQueue<Cell> unchecked;
  private static boolean[][] checked;

  public static void setStart(int i, int j) {
    startI = i;
    startJ = j;
  }

  public static void setStop(int i, int j) {
    stopI = i;
    stopJ = j;
  }

  private void setBlock(int i, int j) {
    grid[i][j] = null;
  }

  @Override
  public char[][] findRoute(char[][] map) {
    initCell(map);
    startAlgorithm();
    return drawRoute(map);
  }

  private void initCell(char[][] map) {
    int x = map.length;
    int y = map[0].length;
    int countBlock = 0;
    int[][] b = null;

    grid = new Cell[x][y];
    checked = new boolean[x][y];
    unchecked =
        new PriorityQueue<>(
            (Object o1, Object o2) -> {
              Cell c1 = (Cell) o1;
              Cell c2 = (Cell) o2;
              return (Integer.compare(c1.costY, c2.costY));
            });

    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        if (map[i][j] == '@') {
          setStart(i, j);
        }
        if (map[i][j] == 'X') {
          setStop(i, j);
        }
        if (map[i][j] == '#') {
          countBlock++;
        }
        grid[i][j] = new Cell(i, j);
        grid[i][j].costX = Math.abs(i - stopI) + Math.abs(j - stopJ);
      }
    }
    b = getCell(map, b, countBlock);
    grid[startI][startJ].costY = 0;
    if (b != null) {
      for (int[] ints : b) {
        setBlock(ints[0], ints[1]);
      }
    }
  }

  private char[][] drawRoute(char[][] map) {
    if (checked[stopI][stopJ]) {
      Cell current = grid[stopI][stopJ];
      while (current.parent != null) {
        current = current.parent;
        if (map[current.i][current.j] == '.') {
          map[current.i][current.j] = '+';
        }
      }
    } else {
      return new char[][] {{}};
    }
    return map;
  }

  private void startAlgorithm() {
    unchecked.add(grid[startI][startJ]);

    Cell first;
    while (true) {
      first = unchecked.poll();
      if (first == null) {
        break;
      }
      checked[first.i][first.j] = true;
      if (first.equals(grid[stopI][stopJ])) {
        return;
      }

      Cell second;
      if (first.i - 1 >= 0) {
        second = grid[first.i - 1][first.j];
        check(first, second, first.costY + 1);
      }
      if (first.j - 1 >= 0) {
        second = grid[first.i][first.j - 1];
        check(first, second, first.costY + 1);
      }
      if (first.j + 1 < grid[0].length) {
        second = grid[first.i][first.j + 1];
        check(first, second, first.costY + 1);
      }
      if (first.i + 1 < grid[0].length) {
        second = grid[first.i + 1][first.j];
        check(first, second, first.costY + 1);
      }
    }
  }

  private int[][] getCell(char[][] map, int[][] block, int countBlock) {
    if (countBlock != 0) {
      block = new int[countBlock][2];
      int b = 0;
      while (b < countBlock) {
        for (int i = 0; i < map.length; i++) {
          for (int j = 0; j < map[0].length; j++) {
            if (map[i][j] == '#') {
              block[b][0] = i;
              block[b][1] = j;
              b++;
            }
          }
        }
      }
    }
    return block;
  }

  private void check(Cell first, Cell second, int cost) {
    if (second == null || checked[second.i][second.j]) {
      return;
    }

    int finalCost = second.costX + cost;
    boolean in = !unchecked.contains(second);
    if (in || finalCost < second.costY) {
      second.costY = finalCost;
      second.parent = first;
      if (in) {
        unchecked.add(second);
      }
    }
  }
}
