import java.awt.*;

//Oval class extended from shape
//Each shape is in its own class extended from the abstract class Shape
public class Oval extends Shape {
    int posX, posY;
    int blockwidth, blockheight;

    public Oval(int posX, int posY, int blockwidth, int blockheight) {
        super(blockwidth, blockheight);
        this.posX = posX;
        this.posY = posY;
        this.blockwidth = blockwidth;
        this.blockheight = blockheight;
    }

    public void paintComponent(Graphics g) {

        g.fillOval(posX, posY, blockwidth, blockheight);

    }
}

