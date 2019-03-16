import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private GameView gv;


    //sets the region of the game for use in this class
    public void setFrame(GameView gameView) {
        this.gv = gameView;

    }

    //This is the method that allows the user to add input using the keyboard
    //and control the snake and the game
    //separate class as specified
    @Override
    public void keyPressed(KeyEvent e) {

        int keycode = e.getKeyCode();

        switch (keycode) {
            case KeyEvent.VK_UP:

                //If the game has not Ended and is Not Paused allow user input
                if (!gv.End && !gv.isPaused) {
                    GameView.SnakeOrientation previous = gv.dir.peekLast();
                    if (previous != GameView.SnakeOrientation.Down && previous
                            != GameView.SnakeOrientation.Up) {
                        gv.dir.addLast(GameView.SnakeOrientation.Up);
                    }
                }
                break;
            case KeyEvent.VK_DOWN:
                if (!gv.End && !gv.isPaused) {
                    GameView.SnakeOrientation previous = gv.dir.peekLast();
                    if (previous != GameView.SnakeOrientation.Up && previous
                            != GameView.SnakeOrientation.Down) {
                        gv.dir.addLast(GameView.SnakeOrientation.Down);
                    }
                }
                break;
            case KeyEvent.VK_LEFT:
                if (!gv.End && !gv.isPaused) {
                    GameView.SnakeOrientation previous = gv.dir.peekLast();
                    if (previous != GameView.SnakeOrientation.Right && previous
                            != GameView.SnakeOrientation.Left) {
                        gv.dir.addLast(GameView.SnakeOrientation.Left);
                    }
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (!gv.End && !gv.isPaused) {
                    GameView.SnakeOrientation previous = gv.dir.peekLast();
                    if (previous != GameView.SnakeOrientation.Left && previous
                            != GameView.SnakeOrientation.Right) {
                        gv.dir.addLast(GameView.SnakeOrientation.Right);
                    }
                }
                break;
            case KeyEvent.VK_W:
                if (!gv.End && !gv.isPaused) {
                    GameView.SnakeOrientation previous = gv.dir.peekLast();
                    if (previous != GameView.SnakeOrientation.Down && previous
                            != GameView.SnakeOrientation.Up) {
                        gv.dir.addLast(GameView.SnakeOrientation.Up);
                    }
                }
                break;
            case KeyEvent.VK_S:
                if (!gv.End && !gv.isPaused) {
                    GameView.SnakeOrientation previous = gv.dir.peekLast();
                    if (previous != GameView.SnakeOrientation.Up && previous
                            != GameView.SnakeOrientation.Down) {
                        gv.dir.addLast(GameView.SnakeOrientation.Down);
                    }
                }
                break;
            case KeyEvent.VK_A:
                if (!gv.End && !gv.isPaused) {
                    GameView.SnakeOrientation previous = gv.dir.peekLast();
                    if (previous != GameView.SnakeOrientation.Right && previous
                            != GameView.SnakeOrientation.Left) {
                        gv.dir.addLast(GameView.SnakeOrientation.Left);
                    }
                }
                break;
            case KeyEvent.VK_D:
                if (!gv.End && !gv.isPaused) {
                    GameView.SnakeOrientation previous = gv.dir.peekLast();
                    if (previous != GameView.SnakeOrientation.Left && previous
                            != GameView.SnakeOrientation.Right) {
                        gv.dir.addLast(GameView.SnakeOrientation.Right);
                    }
                }

                break;
            case KeyEvent.VK_ENTER:

                //If the game has Ended or just begun allow the Enter Key to Restart the game
                if (gv.End || gv.Started) {
                    gv.Restart();
                }
                break;

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public KeyHandler(GameView gv) {
        this.setFrame(gv);

    }
}
