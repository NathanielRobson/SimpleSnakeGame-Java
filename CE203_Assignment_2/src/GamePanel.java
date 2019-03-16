import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    //Grid width (number of columns)
    public static int gridw = 20;

    // Grid height (number of rows)
    public static int gridh = 20;

    //Size of blocks in the grid
    public static Integer blocksize = 20;

    //This is my Oval being created using the Abstract class Shape whet
    Oval myAppleOval = new Oval(getX(), getY(), blocksize, blocksize);
    Square myObstacles = new Square(getX(), getY(), blocksize, blocksize);

    //Creating a new instance of GameView as gv
    private GameView gv;

    //My array of the objects that will populate the grid
    private Shapes[] shapes;

    //This panel contains the game
    public GamePanel(GameView gv) {
        this.gv = gv;
        this.shapes = new Shapes[gridh * gridw];

        //Size and colour of my game
        setPreferredSize(new Dimension(gridh * gridw, gridh * blocksize));
        setBackground(Color.darkGray);

        setFocusable(true);

    }

    //This method draws the grid,
    //draws the Shapes by calling the shapes Collection and allows it to be drawn
    //The two shapes which i have decided to draw from the abstract class Shape
    //are Oval and Square, which are my obstacles on the board and the Green apples
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int x = 0; x < gridw; x++) {
            for (int y = 0; y < gridh; y++) {
                Shapes object = getShapes(x, y);
                if (object != null) {

                    drawSnake(x * 20, y * 20, object, g);

                    //calls the collection of shapes to draw myAppleOval which is an extended class of Shape
                    if (getShapes(x, y) != Shapes.SnakeBody && getShapes(x, y) != Shapes.SnakeHead && getShapes(x, y) != Shapes.Obstacle) {
                        g.setColor(Color.GREEN);
                        myAppleOval.posX = x * 20 + 2;
                        myAppleOval.posY = y * 20 + 2;
                        myAppleOval.blockwidth = 14;
                        myAppleOval.blockheight = 14;
                        myAppleOval.paintComponent(g);

                        //calls the collection of shapes to draw myObstacles which is an extended class of Shape
                    } else if (getShapes(x, y) != Shapes.SnakeBody && getShapes(x, y) != Shapes.SnakeHead && getShapes(x, y) != Shapes.Apple) {
                        g.setColor(Color.MAGENTA);
                        myObstacles.posX = x * 20;
                        myObstacles.posY = y * 20;
                        myObstacles.blockwidth = 20;
                        myObstacles.blockheight = 20;
                        myObstacles.paintComponent(g);
                    }
                }
            }
        }

        //draws the Grid Lines
        int x = 0;
        g.setColor(Color.WHITE);
        for (int i = x; i < gridw; i++) {
            for (int j = x; j < gridh; j++) {
                g.drawLine(i * blocksize, x, i * blocksize, getHeight());
                g.drawLine(x, j * blocksize, getWidth(), j * blocksize);

            }

        }

        //if game ends, game hasn't started
        //draw the string on screen describing which action to take next
        if (gv.End() || gv.Started()) {
            g.setColor(Color.WHITE);

            String GameString = "";
            if (gv.Started()) {
                GameString = "Welcome, Press Enter to begin!";
            } else if (gv.End()) {
                GameString = "You Died. Press Enter to Try Again!";
            }
            g.drawString(GameString, 200 - g.getFontMetrics().stringWidth(GameString) / 2, 180);
        }
    }

    //Draw Snake method which uses a switch case to allow me to either draw the head or the body
    //it also allows me to select where the snake is to be drawn
    public void drawSnake(int x, int y, Shapes object, Graphics g) {

        switch (object) {
            //Here we create the Snake Head
            case SnakeHead:

                g.setColor(new Color(150, 100, 200));
                g.fillRect(x + 2, y + 2, blocksize - 4, blocksize - 4);

                break;

            //Here we create the Snake Body
            case SnakeBody:

                g.setColor(Color.CYAN);
                g.fillRect(x + 2, y + 2, blocksize - 4, blocksize - 4);

                break;
        }
    }

    //redraw the game panel's shapes
    public void Redraw() {
        for (int k = 0; k < shapes.length; k++) {
            shapes[k] = null;
        }
    }

    //This method sets the position at which the object should spawn by getting the
    //position of one of the grid spaces and then drawing a new object
    public void setShapes(Point position, Shapes object) {
        setShapes(position.x, position.y, object);
    }

    //This method also sets the shapes x y position at which the object should spawn
    public void setShapes(int x, int y, Shapes object) {

        shapes[y * gridh + x] = object;
    }

    //Gets the shape position on the board
    public Shapes getShapes(int x, int y) {

        return shapes[y * gridh + x];
    }
}
