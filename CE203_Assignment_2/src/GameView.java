import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;


//main JFrame which holds the game and the score panel in
public class GameView extends JFrame {

    //Here is my Snake as a LinkedList
    private LinkedList<Point> mySnake;

    //This is a LinkedList of orientations available to the player
    public LinkedList<SnakeOrientation> dir;

    //enumeration of the snake orientation
    public enum SnakeOrientation {
        Up, Down, Left, Right
    }

    //Self explanatory booleans
    public boolean Started, End, isPaused;

    //Score board variables
    public int score, totalApple;
    private int appleValue = 50;
    //Game Speed Variable
    private int gamespeed = 10;

    //Score board String name addition
    public String playerName = ":";

    public LinkedList<Integer> scoreLinkedList = new LinkedList<>();
    private int lastElement = 0;
    private File scoreFile = new File(".\\scoresFile.txt");
    private File previousScoreFile = new File(".\\previousScoreFile.txt");
    public String previousscorestring = "";

    //New Timer instance and Random instances
    private Random rand;
    private Timer gameTimer;

    //Calling the classes containing both of my panels
    private GamePanel gamepanel;
    private ScorePanel scorepanel;

    private HighScoreInit HS = new HighScoreInit();
    public String highscorestring = "";

    //The main method of my game creates the format and layout of my game
    //it also calls the classes containing the mouse handler and the keyboard handler
    //for game input
    GameView() {

        //Display of Registration number
        //Initial setup specific to snake
        setTitle("Snake Game Submitted by: 1701837");
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        this.gamepanel = new GamePanel(this);
        this.scorepanel = new ScorePanel(this);

        //Adding both the game panel and the score panel
        add(gamepanel, BorderLayout.CENTER);
        add(scorepanel, BorderLayout.EAST);

        //Here I have implemented both my Mouse Listener Class
        //And my Key Listener Class
        addMouseListener(new MouseSnakeEvent(this));
        addKeyListener(new KeyHandler(this));

        pack();
    }

    //This initializes the game and the variables used in my game
    public void initialize() {

        this.rand = new Random();

        //mySnake is a collection to allow me to place objects in the specific position on my
        //grid
        this.mySnake = new LinkedList<>();

        //dir, is a collection of the different orientations the snake can take
        //up,down,left,right
        this.dir = new LinkedList<>();

        //Calls the timer class
        this.gameTimer = new Timer(gamespeed);
        this.Started = true;

        gameTimer.setPaused(true);

        //while true repaint the game panel, repaint the score panel and check collisions
        boolean init = true;
        while (init) {
            gameTimer.UpdateTimer();
            if (gameTimer.cycleComplete()) {
                checkCollisions();
            }
            gamepanel.repaint();
            scorepanel.repaint();
        }
    }

    //This method checks the collisions while in game
    private void checkCollisions() {
        Shapes collisions = moveSnake();

        //If snake collides with apple, score increment, total apples increment,
        //place another apple in game
        if (collisions == Shapes.Apple) {
            totalApple++;
            score += appleValue;
            placeApple();

            //If snake collides with body part game ends and timer pauses
        } else if (collisions == Shapes.SnakeBody || collisions == Shapes.Obstacle) {
            ScoreChecker();
            End = true;
            gameTimer.setPaused(true);

            //While game is running, decrement apple value until 1
        }
    }

    //Snake movement
    public Shapes moveSnake() {
        SnakeOrientation orientation = dir.peekFirst();

        Point snakeHeadPos = new Point(mySnake.peekFirst());

        //Code for snake movement.
        //Up = y-- decrement y axis
        //Down = y++ increment y axis
        //Left = x-- decrement x axis
        //Right = x++ increment x axis
        if (orientation == SnakeOrientation.Up) {
            snakeHeadPos.y--;

        } else if (orientation == SnakeOrientation.Down) {
            snakeHeadPos.y++;

        } else if (orientation == SnakeOrientation.Right) {
            snakeHeadPos.x++;

        } else if (orientation == SnakeOrientation.Left) {
            snakeHeadPos.x--;

            //If the snake head hits the border of my game, the game ends
            //I can however adjust this to make the game easier and allow the snake to move,
            //from one side of the grid to the other
        } else if (snakeHeadPos.x >= GamePanel.gridw || snakeHeadPos.y < 0 ||
                snakeHeadPos.y >= GamePanel.gridh) {
            //This simulates game over for us
            return Shapes.SnakeBody;

        }
        if (snakeHeadPos.x < 0 || snakeHeadPos.x >= GamePanel.gridw || snakeHeadPos.y < 0
                || snakeHeadPos.y >= GamePanel.gridh) {
            //Simulate game over for us
            return Shapes.SnakeBody;
        }

        Shapes lastBodyPart = gamepanel.getShapes(snakeHeadPos.x, snakeHeadPos.y);
        int snakeLength = 2;
        if (lastBodyPart != Shapes.Apple && mySnake.size() > snakeLength && lastBodyPart != Shapes.Obstacle) {

            Point lastElement = mySnake.removeLast();
            gamepanel.setShapes(lastElement, null);

            lastBodyPart = gamepanel.getShapes(snakeHeadPos.x, snakeHeadPos.y);

        }
        if (lastBodyPart != Shapes.SnakeBody) {

            gamepanel.setShapes(mySnake.peekFirst(), Shapes.SnakeBody);
            mySnake.push(snakeHeadPos);
            gamepanel.setShapes(snakeHeadPos, Shapes.SnakeHead);
            if (dir.size() > 1) {
                dir.poll();
            }
        }
        return lastBodyPart;
    }

    //This is the game restart method
    public void Restart() {

        //Resets all score variables and sets End boolean to false
        if (highscorestring.equals("")) {

            //init highscore

            highscorestring = HS.getHighScoreString();

        }

        //sets base variables to zero
        this.score = 0;
        this.totalApple = 0;
        this.Started = false;
        this.End = false;

        //Repositions the snake and then redraws the snake and the game panel
        Point startingPosition = new Point(GamePanel.gridw / 2, GamePanel.gridh / 2);

        //clears the snake linked list collection and positions it on the board
        mySnake.clear();
        mySnake.add(startingPosition);

        //redraws the game panel and positions the head of the snake
        gamepanel.Redraw();
        gamepanel.setShapes(startingPosition, Shapes.SnakeHead);

        //resets timer
        gameTimer.ResetTimer();

        //calls the method placeObstacle and places multiple obstacles on my board
        for (int i = 0; i < 20; i++) {
            placeObstacle();
        }

        //calls the method placeApple and places multiple apples on my board
        for (int i = 0; i < 4; i++) {
            placeApple();
        }

        //clears the direction of the snake so that it does not move when game is started
        dir.clear();
    }

    //This method allows me to place apples on my game board grid.
    private void placeApple() {
        boolean okay = false;

        while (!okay) {
            //Random position on board generated
            int x = rand.nextInt(20), y = rand.nextInt(20);

            Shapes Apple = Shapes.Apple;

            //If the random position x,y does not contain the snake then continue
            if (gamepanel.getShapes(x, y) == null ||
                    gamepanel.getShapes(x, y) == Apple) {

                //Set the position x,y to draw an apple.
                try {
                    for (int i = 0; i < 1; i++) {
                        gamepanel.setShapes(x, y, Apple);
                        okay = true;
                    }
                } catch (Exception el) {
                    System.out.println("Apple has spawned on head or body - Error Occurred");
                }
            }
        }
    }

    //This method allows me to place randomly generated obstacles on my
    //board to make the game harder
    private void placeObstacle() {
        boolean obs = false;

        while (!obs) {
            int x = rand.nextInt(20), y = rand.nextInt(20);

            Shapes Obstacle = Shapes.Obstacle;

            if (gamepanel.getShapes(x, y) == null ||
                    gamepanel.getShapes(x, y) == Obstacle) {

                try {
                    for (int i = 0; i < 1; i++) {
                        gamepanel.setShapes(x, y, Obstacle);

                    }
                    obs = true;
                } catch (Exception el) {
                    System.out.println("Apple has spawned on head or body - Error Occurred");
                    Restart();
                }
            }
        }
    }

    //Method to return the current state of the game
    boolean End() {
        return End;
    }

    //Method to return the current state of the game
    boolean Started() {
        return Started;
    }

    //getScore
    int getScore() {
        return score;
    }

    //Total apples eaten
    int getTotalApple() {
        return totalApple;
    }

    //Apple value
    int getAppleValue() {
        return appleValue;
    }

    //Checks the score and saves it to 3 separate files
    //if score is more than high score, set new highscore
    //if top 10 list is smaller than 10 then allow another score input to the file
    public void ScoreChecker() {

        if (highscorestring.equals("")) {
            return;
        }

        if (scoreLinkedList.size() == 0) {

            highscorestring = HS.getScoresFileString();
            previousscorestring = HS.getScoresFileRead();

            scoreLinkedList.add(Integer.parseInt(highscorestring));
            System.out.println("Previous High Scores Top 10:" + "\n" + previousscorestring);

        }

        lastElement = scoreLinkedList.getLast();

        if (score > 0) {
            highscorestring = HS.getScoresFileString();
            if (score > Integer.parseInt(highscorestring)) {

                //Text File Writer for top 10 Scores
                playerName = JOptionPane.showInputDialog("New highest Score, Enter Your Name");

                String scoreString = playerName + ":" + String.valueOf(score);

            }

            //This sorts the list
            if (scoreLinkedList.size() < 10) {
                scoreLinkedList.add(score);
                Collections.sort(scoreLinkedList, Collections.reverseOrder());

            } else {
                scoreLinkedList.removeLast();
                scoreLinkedList.add(score);
                Collections.sort(scoreLinkedList, Collections.reverseOrder());
            }

            //prints to the console the current top scores made by the player
            System.out.println(scoreLinkedList);
            highscorestring = HS.getScoresFileString();

            //adds to the textarea score information
            scorepanel.scoreArea.setText("Current Top 10 Scores: " + "\n" + scoreLinkedList.toString());
            scorepanel.scoreArea.append("\n");
            scorepanel.scoreArea.append("Previous Top 10 Scores:" + "\n" + previousscorestring);

            //if file doesnt exist create file
            if (!scoreFile.exists()) {
                try {
                    scoreFile.createNewFile();
                } catch (Exception el) {
                    System.out.println("Unable to create new high score file");
                }
            }
            //file writer for scoreFile.txt
            try {
                BufferedWriter outStream = new BufferedWriter(new FileWriter((scoreFile)));
                try {
                    for (int i = 0; i < scoreLinkedList.size(); i++) {
                        outStream.write(scoreLinkedList.get(i).toString() + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        outStream.close();
                    } catch (Exception ex) {
                        System.out.println("Unable to close writer");

                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("file not found");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //if file doesnt exist create file
            if (!previousScoreFile.exists()) {
                try {
                    previousScoreFile.createNewFile();
                } catch (Exception el) {
                    System.out.println("Unable to create new high score file");
                }
            }

            //new writer to write to previousscore string
            try {
                PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(previousScoreFile, true)));
                try {
                    printWriter.write(previousscorestring);
                } finally {
                    try {
                        printWriter.close();
                    } catch (Exception ex) {
                        System.out.println("Unable to close writer");

                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("previous score file not found");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //new reader and array list to write to previousscorefile.txt
            //rhis shows us the previous games scores even if the program has been
            //closed and reopened
            try {
                ArrayList<Integer> rows = new ArrayList<>();
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(previousScoreFile));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //while there is another line in the file, write to output.txt and sort them
                //by value
                String s;
                while ((s = reader.readLine()) != null)
                    rows.add(Integer.parseInt(s));

                Collections.sort(rows, Collections.reverseOrder());

                FileWriter writer = new FileWriter("output.txt");

                for (int i = 0; i < rows.size(); i++) {
                    Integer cur = rows.get(i);

                    writer.write(cur + "\n");
                }

                //close reader and close writer
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}




