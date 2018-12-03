import java.util.*;

public class PercolationBFS extends PercolationDFSFast {

  public PercolationBFS(int n) {
    super(n);
  }

  @Override
  protected void dfs(int row, int col) {
    int[] rowDelta = {-1,1,0,0};
    int[] colDelta = {0,0,-1,1};

    Queue<Integer> queue = new LinkedList<>();
    myGrid[row][col] = FULL;
    queue.add(row * myGrid.length + col);

    while (queue.size() != 0) {
      int value = queue.remove();
      int r = value / myGrid.length;
      int c = value % myGrid.length;

        for (int k = 0; k < rowDelta.length; k += 1) {
          int nr = r + rowDelta[k];
          int nc = c + colDelta[k];

          if (inBounds(nr,nc) && isOpen(nr,nc) && !isFull(nr,nc)) {
            myGrid[nr][nc] = FULL;
            queue.add(nr * myGrid.length + nc);
          }
        }

    }

	}
}
