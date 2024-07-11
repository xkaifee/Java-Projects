import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


/**
 * WordGame
 *
 * @author Suraj
 * @version 1st Nov 2022
 */

public class WordGame {

    public static String welcome = "Ready to play?";
    public static String yesNo = "1.Yes\n2.No";
    public static String noPlay = "Maybe next time!";
    public static String currentRoundLabel = "Current Round: ";
    public static String enterGuess = "Please enter a guess!";
    public static String winner = "You got the answer!";
    public static String outOfGuesses = "You ran out of guesses!";
    public static String solutionLabel = "Solution: ";
    public static String incorrect = "That's not it!";
    public static String keepPlaying = "Would you like to make another guess?";
    public static String fileNameInput = "Please enter a filename";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(fileNameInput);
        String fileName = scanner.nextLine();

        boolean playAgainBool = true;
        int guesses = 1;
        int games = 1;
        boolean solved = false;

        String[] guessedWordsArray;
        int start = 1;


        WordLibrary wl = new WordLibrary(fileName);


        do {

            ArrayList<String> guessedWordsArrayList = new ArrayList<>();
            WordGuesser wg = new WordGuesser(wl.chooseWord());
            System.out.println(welcome);
            System.out.println(yesNo);
            start = scanner.nextInt();
            scanner.nextLine();

            if (start == 2) {
                System.out.println(noPlay);
                playAgainBool = false;
                break;
            } else if (start == 1) {
                playAgainBool = true;
            }


            while (wg.getRound() <= 5 && playAgainBool) {

                System.out.println(currentRoundLabel + wg.getRound());

                wg.printField();

                System.out.println(enterGuess);
                String userGuess = scanner.nextLine();
                guessedWordsArrayList.add(userGuess);

                boolean b;
                try {
                    b = wg.checkGuess(userGuess);
                    wg.setRound(wg.getRound() + 1);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                if (b) {

                    System.out.println(winner);

                    wg.printField();

                    solved = true;
                    playAgainBool = false;

                    guessedWordsArray = new String[guessedWordsArrayList.size()];

                    for (int i = 0; i < guessedWordsArrayList.size(); i++) {

                        guessedWordsArray[i] = guessedWordsArrayList.get(i);

                    }

                    try {
                        updateGameLog(wg.getSolution(), guessedWordsArray, solved);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    break;

                }

                if (wg.getRound() >= 6) {

                    solved = false;
                    System.out.println(outOfGuesses);
                    System.out.println(solutionLabel + wg.getSolution());
                    wg.printField();

                    guessedWordsArray = new String[guessedWordsArrayList.size()];

                    for (int i = 0; i < guessedWordsArrayList.size(); i++) {

                        guessedWordsArray[i] = guessedWordsArrayList.get(i);

                    }

                    try {
                        updateGameLog(wg.getSolution(), guessedWordsArray, solved);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    break;

                }
                if (!b) {
                    System.out.println(incorrect);

                }

                System.out.println(keepPlaying);
                System.out.println(yesNo);

                int playAgain = scanner.nextInt();
                scanner.nextLine();

                if (playAgain == 1) {
                    playAgainBool = true;

                } else if (playAgain == 2) {
                    playAgainBool = false;
                    guessedWordsArray = new String[guessedWordsArrayList.size()];
                    for (int i = 0; i < guessedWordsArrayList.size(); i++) {
                        guessedWordsArray[i] = guessedWordsArrayList.get(i);
                    }

                    try {
                        updateGameLog(wg.getSolution(), guessedWordsArray, solved);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                }


            }

        } while (start == 1);


    }

    public static void updateGameLog(String solution, String[] guesses, boolean solved) throws FileNotFoundException {

        int games = 1;

        try {

            if (!(new File("gamelog.txt")).exists()) {

                File f = new File("gamelog.txt");
                FileOutputStream fos = new FileOutputStream(f, false);
                PrintWriter pw = new PrintWriter(fos);

                pw.println(String.format("Games Completed: 1"));
                pw.println(String.format("Game 1"));
                pw.println(String.format("- Solution: %s", solution));
                pw.print(String.format("- Guesses: "));

                for (int i = 0; i < guesses.length; i++) {
                    if (i == guesses.length - 1) {
                        pw.print(String.format(guesses[i]));
                    } else
                        pw.print(String.format(guesses[i]) + ",");
                }

                if (solved == true) {

                    pw.print(String.format("\n- Solved: Yes"));
                } else {

                    pw.print(String.format("\n- Solved: No"));

                }
                pw.close();

            } else {

                File f = new File("gamelog.txt");
                FileReader fr = new FileReader(f);
                BufferedReader bfr = new BufferedReader(fr);
                ArrayList<String> guessesFile = new ArrayList<>();

                String line = bfr.readLine();


                while (line != null) {

                    if (line.contains("Games Completed: ")) {

                        //String[] lineVals = line.split(":");
                        games = Integer.parseInt(line.substring(17)) + 1;
                        //games += 1;

                    } else
                        guessesFile.add(line);
                    line = bfr.readLine();

                }

                FileOutputStream fos = new FileOutputStream(f, false);
                PrintWriter pw = new PrintWriter(fos);
                pw.println(String.format("Games Completed: %d", games));

                for (int i = 0; i < guessesFile.size(); i++) {

                    pw.println(String.format(guessesFile.get(i)));

                }
                pw.println(String.format("Game %d", games));
                pw.println(String.format("- Solution: %s", solution));
                pw.print(String.format("- Guesses: "));

                for (int i = 0; i < guesses.length; i++) {
                    if (i == guesses.length - 1) {
                        pw.print(String.format(guesses[i]));
                    } else
                        pw.print(String.format(guesses[i]) + ",");
                }

                if (solved == true) {

                    pw.println(String.format("\n- Solved: Yes"));
                } else {

                    pw.println(String.format("\n- Solved: No"));

                }

                bfr.close();
                pw.close();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
