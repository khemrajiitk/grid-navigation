import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

class QItemComparator implements Comparator<QItem> {
    public int compare(QItem qItem1, QItem qItem2) {
        return qItem1.dist.compareTo(qItem2.dist);
    }
}

public class App {
    private static void findShortestPath(char[][] grid) {
        QItem source = new QItem(0, 0, 0.0);

        outerLoop: for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {

                if (grid[i][j] == 'S') {
                    source.row = i;
                    source.col = j;
                    break outerLoop;
                }
            }
        }

        PriorityQueue<QItem> queue = new PriorityQueue<QItem>(new QItemComparator());
        queue.add(new QItem(source.row, source.col, 0.0));

        boolean[][] visited = new boolean[grid.length][grid[0].length];
        visited[source.row][source.col] = true;

        Parent[][] parentGrid = new Parent[grid.length][grid[0].length];
        parentGrid[source.row][source.col] = null;

        while (queue.isEmpty() == false) {
            QItem p = queue.remove();

            // Destination found;
            if (grid[p.row][p.col] == 'E') {
                Parent currentParent = parentGrid[p.row][p.col];
                while (grid[currentParent.row][currentParent.col] != 'S') {
                    grid[currentParent.row][currentParent.col] = '+';
                    currentParent = parentGrid[currentParent.row][currentParent.col];
                }

                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        System.out.print(grid[i][j]);
                    }
                    System.out.print("\n");
                }
                return;
            }

            Parent parent = new Parent(p.row, p.col);

            // moving up
            if (isValid(p.row - 1, p.col, grid, visited)) {
                queue.add(new QItem(p.row - 1, p.col,
                        p.dist + 1));

                visited[p.row - 1][p.col] = true;
                parentGrid[p.row - 1][p.col] = parent;
            }

            // moving down
            if (isValid(p.row + 1, p.col, grid, visited)) {
                queue.add(new QItem(p.row + 1, p.col,
                        p.dist + 1));
                visited[p.row + 1][p.col] = true;
                parentGrid[p.row + 1][p.col] = parent;
            }

            // moving left
            if (isValid(p.row, p.col - 1, grid, visited)) {
                queue.add(new QItem(p.row, p.col - 1,
                        p.dist + 1));
                visited[p.row][p.col - 1] = true;
                parentGrid[p.row][p.col - 1] = parent;
            }

            // moving right
            if (isValid(p.row, p.col + 1, grid,
                    visited)) {
                queue.add(new QItem(p.row, p.col + 1,
                        p.dist + 1));
                visited[p.row][p.col + 1] = true;
                parentGrid[p.row][p.col + 1] = parent;
            }

            // moving right up
            if (isValid(p.row - 1, p.col + 1, grid,
                    visited)) {
                queue.add(new QItem(p.row - 1, p.col + 1,
                        p.dist + 1.44));
                visited[p.row - 1][p.col + 1] = true;
                parentGrid[p.row - 1][p.col + 1] = parent;
            }

            // moving left up
            if (isValid(p.row - 1, p.col - 1, grid,
                    visited)) {
                queue.add(new QItem(p.row - 1, p.col - 1,
                        p.dist + 1.44));
                visited[p.row - 1][p.col - 1] = true;
                parentGrid[p.row - 1][p.col - 1] = parent;
            }

            // moving left down
            if (isValid(p.row + 1, p.col - 1, grid,
                    visited)) {
                queue.add(new QItem(p.row + 1, p.col - 1,
                        p.dist + 1.44));
                visited[p.row + 1][p.col - 1] = true;
                parentGrid[p.row + 1][p.col - 1] = parent;
            }

            // moving right down
            if (isValid(p.row + 1, p.col + 1, grid,
                    visited)) {
                queue.add(new QItem(p.row + 1, p.col + 1,
                        p.dist + 1.44));
                visited[p.row + 1][p.col + 1] = true;
                parentGrid[p.row + 1][p.col + 1] = parent;
            }

        }

        //Path not found
        return;
    }

    // checking valid or not
    private static boolean isValid(int x, int y,
            char[][] grid,
            boolean[][] visited) {
        if (x >= 0 && y >= 0 && x < grid.length
                && y < grid[0].length && grid[x][y] != '#'
                && visited[x][y] == false) {
            return true;
        }
        return false;
    }

    //init function
    public static void main(String[] args) {

        ArrayList<String> lines = new ArrayList<String>();

        String filePath = new File("").getAbsolutePath();

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filePath + "/src/input.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int colSize = lines.get(0).length();
        int rowSize = lines.size();

        char[][] grid = new char[rowSize][colSize];

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                char c = lines.get(i).charAt(j);
                grid[i][j] = c;
            }
        }

        findShortestPath(grid);
    }
}
