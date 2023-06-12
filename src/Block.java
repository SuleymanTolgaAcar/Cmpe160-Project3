/*
 * Süleyman Tolga Acar
 * 2021400237
 * 03.05.2023
 * 
 * This is the class for the blocks.
 * Each block has a height, x and y coordinates, lake height, flooded status and considered status.
 * The label is used for the lakes.
 */


public class Block {
    private int height;
    private int x;
    private int y;
    private int lakeHeight = 0;
    private boolean isFlooded = false;
    private boolean isConsidered = false;
    private String label;

    public Block(int height, int x, int y) {
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void addHeight() {
        height += 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLakeHeight() {
        return lakeHeight;
    }

    public void setLakeHeight(int lakeHeight) {
        this.lakeHeight = lakeHeight;
    }

    public boolean isFlooded() {
        return isFlooded;
    }

    public void setFlooded(boolean isFlooded) {
        this.isFlooded = isFlooded;
    }

    public boolean isConsidered() {
        return isConsidered;
    }

    public void setConsidered(boolean isConsidered) {
        this.isConsidered = isConsidered;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
        if (label.length() == 1) {
            this.label = " " + label;
        }
    }
}
