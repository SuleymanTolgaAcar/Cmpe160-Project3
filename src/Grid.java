/*
 * Süleyman Tolga Acar
 * 2021400237
 * 03.05.2023
 * 
 * In this class, I have methods to elevate the blocks, flood the lakes, find all the lakes, calculate the lake heights and calculate the score.
 * And quite a few helper methods such as getNum, getLabel, getNeighbours.
 * I flood the terrain by using floodAll method which calls flood method on each block.
 * Flood method itself is a recursive method that calls itself on the neighbours of the block.
 * I find all the lakes by using findAllLakes method which calls findLake method on each block.
 * FindLake method itself is a recursive method that calls itself on the neighbours of the block.
 * Finally, I calculate the score by calling first the calculateLakeHeights method and then the calculateScore method.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Grid {
    private Block[][] grid;
    private int width;
    private int height;
    private ArrayList<ArrayList<Block>> lakes = new ArrayList<ArrayList<Block>>();

    /**
     * This is the constructor of the Grid class.
     * It reads the file and creates the grid.
     * @param file The input file.
     */
    public Grid(File file) {
        try {
            Scanner scanner = new Scanner(file);
            int x = 0;
            int y = 0;
            width = scanner.nextInt();
            height = scanner.nextInt();
            grid = new Block[width][height];
            while (scanner.hasNextInt()) {
                int blockHeight = scanner.nextInt();
                Block block = new Block(blockHeight, x, y);
                setBlock(block);
                x++;
                if (x == width) {
                    x = 0;
                    y++;
                }
            }
            scanner.close();
        } catch (Exception e) {

        }
    }

    public void setBlock(Block block) {
        this.grid[block.getX()][block.getY()] = block;
    }
    /**
     * Saves the current flood state of the grid to a 2D boolean array. 
     */
    public boolean[][] getFlood() {
        boolean[][] flood = new boolean[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                flood[x][y] = grid[x][y].isFlooded();
            }
        }
        return flood;
    }

    /**
     * Takes two integers as parameters which are user inputs.
     * If the block is not null and there are no errors, increases the block's height by 1.
     * @param x The x coordinate of the block to be elevated.
     * @param y The y coordinate of the block to be elevated.
     */
    public boolean elevate(int x, int y) {
        try {
            Block block = this.grid[x][y];
            if (block == null) {
                System.out.println("Not a valid step!");
                return false;
            }
            block.addHeight();
            return true;
        } catch (Exception e) {
            System.out.println("Not a valid step!");
            return false;
        }
    }

    /**
     * Prints the grid to the console.
     * If the block is flooded, prints the label of the block.
     * If the block is not flooded, prints the height of the block.
     */
    public void print() {
        for (int y = 0; y < height; y++) {
            String line = y > 9 ? " " : "  ";
            line += y + "";
            for (int x = 0; x < width; x++) {
                String labelOrHeight = grid[x][y].isFlooded() ? grid[x][y].getLabel() : grid[x][y].getHeight() + "";
                line += (labelOrHeight.length() == 1 ? "  " : " ") + labelOrHeight;
            }
            System.out.println(line);
        }
        String line = "   ";
        for (int x = 0; x < width; x++) {
            String label = getLabel(x, "grid");
            line += (label.length() == 1 ? "  " : " ") + label;
        }
        System.out.println(line);
    }

    /**
     * Calls the flood method with each block.
     * If the flood state of the grid has changed, calls itself again.
     * @param lastFlood The last flood state of the grid.
     */
    public void floodAll(boolean[][] lastFlood) {
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                Block block = grid[x][y];
                flood(block, block);
            }
        }
        if (!Arrays.deepEquals(getFlood(), lastFlood)) {
            floodAll(getFlood());
        }
    }
    
    /**
     * Checks if the block can be flooded, then floods it.
     * If the block is on the edge of the grid, returns immediately.
     * If the block has no lower neighbour, floods the block.
     * If the block has a lower neighbour, calls the flood method with the neighbour.
     * @param block Current block.
     * @param lastBlock The block that was flooded before the current block. Equals to the current block at the first call.
     */
    public void flood(Block block, Block lastBlock) {
        if (block.getX() == 0 || block.getX() == width - 1 || block.getY() == 0 || block.getY() == height - 1) {
            block.setFlooded(false);
            return;
        }
        boolean isThereLower = false;
        boolean surroundedByFlood = true;
        for (Block neighbor : getNeighbors(block)) {
            if (neighbor.getHeight() <= block.getHeight() && neighbor != lastBlock && !neighbor.isFlooded()) {
                isThereLower = true;
                flood(neighbor, block);
                break;
            }
            if (!neighbor.isFlooded()) {
                surroundedByFlood = false;
            }
        }
        if (!isThereLower && !surroundedByFlood) {
            block.setFlooded(true);
        }
    }

    /**
     * Finds all lakes in the grid.
     * Calls the findLake method with each block which is flooded and not considered.
     * labelIndex is used to label the lakes with letters.
     */
    public void findAllLakes() {
        int labelIndex = 0;
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                Block block = grid[x][y];
                if (!block.isConsidered() && block.isFlooded()) {
                    ArrayList<Block> lake = new ArrayList<Block>();
                    lake.add(block);
                    block.setConsidered(true);
                    block.setLabel(getLabel(labelIndex++, "lake"));
                    lakes.add(lake);
                    findLake(block, lake);
                }
            }
        }
    }

    /**
     * Given a starting block finds the rest of the lake recursively.
     * If the neighbor is flooded and not considered, adds it to the lake and calls itself with the neighbor.
     * @param block Starting block.
     * @param lake The lake that the block is in.
     */
    public void findLake(Block block, ArrayList<Block> lake) {
        for (Block neighbor : getNeighbors(block)) {
            if (neighbor.isFlooded() && !lake.contains(neighbor)) {
                neighbor.setConsidered(true);
                neighbor.setLabel(block.getLabel());
                lake.add(neighbor);
                findLake(neighbor, lake);
            }
        }
    }

    /**
     * Calculates the height of each lake.
     * For each lake, finds the lowest height of the blocks that are not flooded.
     * Sets the lake height of each block in the lake to the lowest height.
     */
    public void calculateLakeHeights() {
        for (ArrayList<Block> lake : lakes) {
            int lakeHeight = Integer.MAX_VALUE;
            for (Block block : lake) {
                for (Block neighbor : getNeighbors(block)) {
                    if (!neighbor.isFlooded() && neighbor.getHeight() < lakeHeight) {
                        lakeHeight = neighbor.getHeight();
                    }
                }
            }
            for (Block block : lake) {
                block.setLakeHeight(lakeHeight);
            }
        }
    }

    /**
     * Calculates the score of the grid.
     * For each block, adds the difference between the lake height and the height of the block to the score.
     * @return The score of the final grid.
     */
    public double calculateScore() {
        double score = 0;
        for (ArrayList<Block> lake : lakes) {
            double lakeScore = 0;
            for (Block block : lake) {
                lakeScore += block.getLakeHeight() - block.getHeight();
            }
            score += Math.sqrt(lakeScore);
        }
        return score;
    }

    /**
     * Sets flooded state to false on blocks that are in a lake arraylist but have a water height of 0 or less.
     */
    public void fixLakes() {
        for (ArrayList<Block> lake : lakes) {
            for (Block block : lake) {
                if (block.getHeight() >= block.getLakeHeight()) {
                    block.setFlooded(false);
                }
            }
        }
    }

    /**
     * Returns the neighbors of a block.
     * @param block The block whose neighbors are to be found.
     * @return An arraylist of the neighbors of the block.
     */
    private ArrayList<Block> getNeighbors(Block block) {
        ArrayList<Block> neighbors = new ArrayList<Block>();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (x == 1 && y == 1) {
                    continue;
                }
                int neighborX = block.getX() + x - 1;
                int neighborY = block.getY() + y - 1;
                if (neighborX < 0 || neighborX >= width || neighborY < 0 || neighborY >= height) {
                    continue;
                }
                neighbors.add(grid[neighborX][neighborY]);
            }
        }
        return neighbors;
    }

    /**
     * Helper method to get a label for a block or the grid.
     * @param num The index of the label or the block.
     * @param opt "grid" for grid labels, "lake" for lake labels.
     * @return The label for the block or the grid.
     */
    public static String getLabel(int num, String opt) {
        String res = "";
        int mod = num % 26;
        int div = num / 26;
        int start;
        if (opt.equals("grid")) {
            start = 97;
        } else {
            start = 65;
        }
        char c = (char) (mod + start);
        if (div > 0) {
            res += (char) (div + start - 1);
        }
        res += c;
        return res;
    }
    
    /**
     * Helper method to get the index of a label.
     * @param label The label whose index is to be found.
     * @return The index of the label.
     */
    public static int getNum(String label) {
        int res = 0;
        if (label.length() == 2) {
            res += (label.charAt(0) - 97 + 1) * 26;
        }
        res += label.charAt(label.length() - 1) - 97 + 1;
        return res - 1;
    }
}

