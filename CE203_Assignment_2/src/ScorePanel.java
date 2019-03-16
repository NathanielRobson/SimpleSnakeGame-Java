import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ScorePanel extends JPanel {
    private HighScoreInit HS = new HighScoreInit();
    private GameView gv;
    JTextArea scoreArea = new JTextArea("Top Score of All time", 10, 35);
    int score = 0;

    //ScorePanel method creates the score panel to the right of the game which
    //consists of highscore, current scores, scorepanel jtext area
    // and 2 buttons
    ScorePanel(GameView gv) {
        //Score Panel
        this.gv = gv;

        //Size, Layout and Background colour of Score Panel
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.DARK_GRAY);

        //Button Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(400, 210));
        bottomPanel.setBackground(Color.DARK_GRAY);

        //Score Panel
        JPanel scoreBottomPanel = new JPanel();
        scoreBottomPanel.setPreferredSize(new Dimension(400, 170));
        scoreBottomPanel.setBackground(Color.LIGHT_GRAY);
        scoreArea.append("\n");


        scoreArea.setEditable(false);
        scoreBottomPanel.add(scoreArea);

        //Add button panel to ScorePanel
        bottomPanel.add(scoreBottomPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        //Buttons
        JButton restartBtn = new JButton("Restart");
        restartBtn.setFocusable(false);

        //View Score
        JButton viewScoreBtn = new JButton("View Scores");
        viewScoreBtn.setFocusable(false);

        //adding buttons to bottom panel
        bottomPanel.add(restartBtn);
        bottomPanel.add(viewScoreBtn);

        //Adding button actionListeners using ButtonHandler Class
        restartBtn.addActionListener(new ButtonHandler(this, 1));
        viewScoreBtn.addActionListener(new ButtonHandler(this, 2));

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Strings used in the side panel
        String currentScore = "Current Score: ";
        String totalApples = "Total Eaten: ";
        String appleValue = "Apple Value = ";
        String highScore = "Highest Score: ";

        //Scores Title
        g.setColor(Color.BLACK);
        g.setFont(new Font("Century Gothic", Font.PLAIN, 26));
        g.drawString("Scores", 200 - g.getFontMetrics().stringWidth("Scores") / 2, 30);

        //High Score
        g.setColor(Color.YELLOW);
        g.drawString(highScore + gv.playerName + " " + HS.getHighScoreString(), 110 - g.getFontMetrics().stringWidth(highScore) / 2, 70);

        //Current Player Scores
        g.setColor(Color.RED);
        g.drawString(currentScore + gv.getScore(), 200 - g.getFontMetrics().stringWidth(currentScore) / 2, 110);
        g.drawString(totalApples + gv.getTotalApple(), 200 - g.getFontMetrics().stringWidth(totalApples) / 2, 150);
        g.drawString(appleValue + gv.getAppleValue(), 200 - g.getFontMetrics().stringWidth(appleValue) / 2, 190);

    }

    //Button#handler class which implements ActionListener
    class ButtonHandler implements ActionListener {

        ScorePanel scorePanel;
        int action;

        //panel = panel
        //action = action
        public ButtonHandler(ScorePanel panel, int action) {
            this.scorePanel = panel;
            this.action = action;

        }

        @Override
        public void actionPerformed(ActionEvent e) {

            //if button 1 is pressed then perform this action
            if (action == 1) {
                System.out.println("Game Restarted with Restart Button");
                gv.Restart();

                //if button 2 is pressed show scores
            } else if (action == 2) {

                try {
                    Desktop.getDesktop().open(new File(".\\output.txt"));

                } catch (Exception el) {

                    System.out.println("File does not exist or is not found, continuing anyway.");


                }
            }
        }
    }
}