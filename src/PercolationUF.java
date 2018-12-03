public class PercolationUF implements IPercolate {

  protected boolean[][] myGrid;
  protected int myOpenCount;
  protected IUnionFind myFinder;
  private final int VTOP;
  private final int VBOTTOM;

  PercolationUF(int size, IUnionFind finder) {
    myGrid = new boolean[size][size];
    myOpenCount = 0;
    finder.initialize(size * size + 2);
    myFinder = finder;
    VTOP = size * size;
    VBOTTOM = size * size + 1;
  }

  @Override
  public void open(int row, int col) {
    if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
    if (myGrid[row][col] == true) {
      return;
    }
    myGrid[row][col] = true;

    if (inBounds(row + 1,col) && isOpen(row + 1,col)) {
      myFinder.union(rcToIndex(row,col), rcToIndex(row + 1,col));
    }
    if (inBounds(row - 1,col) && isOpen(row - 1,col)) {
      myFinder.union(rcToIndex(row,col), rcToIndex(row - 1,col));
    }
    if (inBounds(row,col + 1) && isOpen(row,col + 1)) {
      myFinder.union(rcToIndex(row,col), rcToIndex(row,col + 1));
    }
    if (inBounds(row,col - 1) && isOpen(row,col - 1)) {
      myFinder.union(rcToIndex(row,col), rcToIndex(row,col - 1));
    }

    if (row == 0) {
      myFinder.union(rcToIndex(row,col), VTOP);
    }
    if (row == myGrid.length - 1) {
      myFinder.union(rcToIndex(row,col), VBOTTOM);
    }
  }

  @Override
  public boolean isOpen(int row, int col) {
    if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
    return myGrid[row][col];
  }

  @Override
  public boolean isFull(int row, int col) {
    if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
    return myFinder.connected(rcToIndex(row,col), VTOP);
  }

  @Override
  public boolean percolates() {
    return myFinder.connected(VTOP, VBOTTOM);
  }

  @Override
  public int numberOfOpenSites() {
    return myOpenCount;
  }

  protected int rcToIndex(int row, int col) {
    if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
    }
    return row * myGrid.length + col;
  }

  protected boolean inBounds(int row, int col) {
		if (row < 0 || row >= myGrid.length) return false;
		if (col < 0 || col >= myGrid[0].length) return false;
		return true;
	}
}
