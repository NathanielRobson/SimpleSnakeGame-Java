import javax.swing.*;

//Abstract class shape extends JPanel to allow extending from other shape classes
//to create Oval(Apple) and Square(Obstacles)
abstract class Shape extends JPanel {
    private Integer posX;
    private Integer posY;

    Shape(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;

    }

}
