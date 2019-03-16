import java.io.*;

public class HighScoreInit {

    private final String scoreFilePath = ".\\scoresFile.txt";

    //This method allows me to read the scoreFile.txt and take the highest value
    // and set it to the highs core
    //if there is no value, return the string "0"
    public String getHighScoreString() {

        //format name: score = bob: 100
        FileReader readfile = null;
        BufferedReader br = null;

        try {
            readfile = new FileReader(scoreFilePath);
            br = new BufferedReader(readfile);
            return br.readLine();
        } catch (Exception el) {
            return "0";
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception el) {
                el.printStackTrace();
            }
        }
    }

    public String getScoresFileString() {

        FileReader readfile = null;
        BufferedReader br = null;

        try {
            readfile = new FileReader(scoreFilePath);
            br = new BufferedReader(readfile);
            String line;

            return br.readLine();
        } catch (Exception el) {
            return "0";
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception el) {
                el.printStackTrace();
            }
        }
    }

    //This method reads the whole file and returns it as a string with a new line
    //this is used to provide previous scores and top 10 scores
    public String getScoresFileRead() {

        FileReader readfile = null;
        BufferedReader br = null;
        StringBuffer stringBuffer = null;

        try {
            readfile = new FileReader(scoreFilePath);
            br = new BufferedReader(readfile);
            stringBuffer = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            readfile.close();
            return stringBuffer.toString();

        } catch (Exception el) {
            return "0";
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception el) {
                el.printStackTrace();
            }
        }
    }
}