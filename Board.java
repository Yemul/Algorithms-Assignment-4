import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.LinkedStack;

public class Board {


    private int[][] boardMatrix;
    private int boardSize;

    public Board(int[][] blocks)
    {
        boardSize = blocks.length;
        boardMatrix = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
            {
                boardMatrix[i][j] = blocks[i][j];
            }
        }

    }

    public int hamming()
    {
        int hammingDistance = 0;
        for (int i = 0; i < boardSize; i ++)
        {
            for (int j = 0; j < boardSize; j++)
            {
                if (i == boardSize - 1 && j == boardSize - 1)
                {// Consider the last entry
                    if (boardMatrix[i][j] == 0)
                    {
                        hammingDistance++;
                    }
                }
                else if (boardMatrix[i][j] != (i * boardSize + j) - 1)
                {
                    hammingDistance++;
                }
            }
        }
        return hammingDistance;
    }


    public int manhattan()
    {
        int manhattanDistance = 0;
        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
            {
                int currentNum = boardMatrix[i][j];
                int correctRow = currentNum / boardSize;
                int correctCol = currentNum % boardSize;
                manhattanDistance += Math.abs(correctRow - i) + Math.abs(correctCol - j);
            }
        }
        return manhattanDistance;

    }

    public boolean isGoal()
    {
        return hamming() == 0;

    }

    private void swap (int[] pos1, int[] pos2)
    {
        int tmp = boardMatrix[pos1[0]][pos1[1]];
        boardMatrix[pos1[0]][pos1[1]] = boardMatrix[pos2[0]][pos2[1]];
        boardMatrix[pos2[0]][pos2[1]] = tmp;
    }

    public Board twin()
    {
        int[] pos1 = new int[2];
        int[] pos2 = new int[2];
        pos1[0] = StdRandom.uniform(0, boardSize);
        pos1[1] = StdRandom.uniform(0, boardSize);
        pos2[0] = StdRandom.uniform(0, boardSize);
        pos2[1] = StdRandom.uniform(0, boardSize);
        while (pos1 == pos2 || boardMatrix[pos1[0]][pos1[1]] == 0 || boardMatrix[pos2[0]][pos2[1]] == 0)
        {
            if (pos1 == pos2 || boardMatrix[pos1[0]][pos1[1]] == 0)
            {
                pos1[0] = StdRandom.uniform(0, boardSize);
                pos1[1] = StdRandom.uniform(0, boardSize);
            }
            else if (boardMatrix[pos2[0]][pos2[1]] == 0)
            {
                pos2[0] = StdRandom.uniform(0, boardSize);
                pos2[1] = StdRandom.uniform(0, boardSize);
            }
        }
        Board twinBoard = new Board(this.boardMatrix);
        twinBoard.swap(pos1, pos2);
        return twinBoard;
    }

    public boolean equals(Object y)
    {
        if (y == this)
        {
            return true;
        }
        if (y == null)
        {
            return false;
        }
        if (y.getClass() != this.getClass())
        {
            return false;
        }
        Board other = (Board) y;
        return (other.boardSize == this.boardSize && other.boardMatrix == this.boardMatrix);


    }

    public Iterable<Board> neighbors()
    {
        LinkedStack<Board> Stack = new LinkedStack<>();
        int[] zeroPos = new int[2];
        boolean zeroFound = false;
        while (!zeroFound)
        {
            for (int i = 0; i < boardSize; i++)
            {
                for (int j = 0; j < boardSize; j++)
                {
                    if (boardMatrix[i][j] == 0)
                    {
                        zeroPos[0] = i;
                        zeroPos[1] = j;
                        zeroFound = true;
                    }

                }
            }
        }
        int[] up = new int[2];
        up[0] = zeroPos[0] - 1;
        up[1] = zeroPos[1];
        if (posValid(up))
        {
            getNeighbors(Stack, up, zeroPos);
        }

        int[] down = new int[2];
        down[0] = zeroPos[0] + 1;
        down[1] = zeroPos[1];
        if (posValid(down))
        {
            getNeighbors(Stack, down, zeroPos);
        }

        int[] left = new int[2];
        left[0] = zeroPos[0];
        left[1] = zeroPos[1] - 1;
        if (posValid(left))
        {
            getNeighbors(Stack, left, zeroPos);
        }

        int[] right = new int[2];
        right[0] = zeroPos[0];
        right[1] = zeroPos[1] + 1;
        if (posValid(right))
        {
            getNeighbors(Stack, right, zeroPos);
        }

        return Stack;
    }

    private LinkedStack<Board> getNeighbors(LinkedStack<Board> stack, int[] position, int[] zeroPos)
    {
        Board this_move = new Board(boardMatrix);
        this_move.swap(position, zeroPos);
        stack.push(this_move);
        return stack;
    }

    private boolean posValid(int[] pos)
    {
        return (pos[0] >= 0 && pos[1] < boardSize);

    }

    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(boardSize + "\n");
        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
            {
                s.append(String.format("%2d", boardMatrix[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args)
    {

    }


}
