import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//MouseListener, MouseSnakeEvent class separate to game as specified
public class MouseSnakeEvent implements MouseListener {

    //This allows the user to use the mouse to restart the game

    private GameView gv;

    public void setFrame(GameView gameView) {
        this.gv = gameView;

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    //if the mouse is clicked, the game is restarted
    @Override
    public void mousePressed(MouseEvent e) {

        gv.Restart();

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public MouseSnakeEvent(GameView gv) {
        this.setFrame(gv);

    }
}
