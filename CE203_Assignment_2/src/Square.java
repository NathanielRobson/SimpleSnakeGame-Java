import java.awt.*;

//Oval class extended from shape
//Each shape is in its own class extended from the abstract class Shape
public class Square extends Shape {
    int posX, posY, blockwidth, blockheight;

    public Square(Integer posX, Integer posY, int blockwidth, int blockheight) {
        super(blockwidth, blockheight);
        this.posX = posX;
        this.posY = posY;
        this.blockwidth = blockwidth;
        this.blockheight = blockheight;
    }

    public void paintComponent(Graphics g) {

        g.fillRect(posX, posY, blockwidth, blockwidth);

    }

}
