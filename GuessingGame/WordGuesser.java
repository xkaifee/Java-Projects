/**
 * WordGuesser
 *
 * @author Suraj
 * @version 1st Nov 2022
 */
public class WordGuesser {

    private String[][] playingField;
    private int round;
    private String solution;

    public WordGuesser(String solution) {
        this.solution = solution;
        round = 1;
        playingField = new String[5][5];

        for (int i = 0; i < playingField.length; i++) {
            for (int j = 0; j < playingField.length; j++) {
                playingField[i][j] = "";
            }
        }
    }

    public String[][] getPlayingField() {
        return playingField;
    }

    public int getRound() {
        return round;
    }

    public String getSolution() {
        return solution;
    }

    public void setPlayingField(String[][] playingField) {
        this.playingField = playingField;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public boolean checkGuess(String guess) throws InvalidGuessException {

        if (guess.length() != 5) {
            throw new InvalidGuessException("Invalid Guess!");
        }

        String[] guessxsolution = new String[5];

        boolean guessed = false;

        if (guess.equals(solution)) {

            guessed = true;
            for (int i = 0; i < 5; i++) {

                guessxsolution[i] = "'" + guess.charAt(i) + "'";

            }

        } else {

            for (int i = 0; i < 5; i++) {

                for (int j = 0; j < 5; j++) {

                    if (guess.charAt(i) == solution.charAt(i)) {

                        guessxsolution[i] = "'" + guess.charAt(i) + "'";

                    } else if (solution.contains(String.valueOf(guess.charAt(i)))) {

                        guessxsolution[i] = "*" + guess.charAt(i) + "*";

                    } else {

                        guessxsolution[i] = "{" + guess.charAt(i) + "}";

                    }
                }
            }
        }

        for (int i = 0; i < this.getRound(); i++) {

            for (int j = 0; j < 5; j++) {

                playingField[this.getRound() - 1][j] = guessxsolution[j];
                this.setPlayingField(this.playingField);


            }

        }

        return guessed;
    }

    public void printField() {

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                if (j < 4) {
                    System.out.print(playingField[i][j] + " | ");
                } else {
                    System.out.print(playingField[i][j]);
                }

            }

            System.out.println();

        }

    }
}


