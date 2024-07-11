import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * WordLibrary
 *
 * @author Suraj
 * @version 1st Nov 2022
 */

public class WordLibrary {

    private String[] library;
    private int seed;
    private Random random;

    private String fileName;

    public WordLibrary(String fileName) {


        this.fileName = fileName;
        ArrayList<String> words = new ArrayList<>();

        try {

            File f = new File(fileName);
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);

            String line = bfr.readLine();


            while (line != null) {

                if (line.contains("Seed:")) {

                    String[] lineVals = line.split(":");

                    seed = Integer.parseInt(lineVals[1].trim());
                    //System.out.println(seed);
                    random = new Random(seed);

                } else {

                    words.add(line);

                }

                line = bfr.readLine();

            }

            bfr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        library = new String[words.size()];

        for (int i = 0; i < words.size(); i++) {

            library[i] = words.get(i);

        }

        try {
            processLibrary();
        } catch (InvalidWordException e) {
            e.printStackTrace();
        }

    }

    public void verifyWord(String word) throws InvalidWordException {

        if (word.length() != 5) {
            throw new InvalidWordException("Invalid word!");
        }

    }

    public void processLibrary() throws InvalidWordException {

        ArrayList<String> finalArrayList = new ArrayList<>();


        for (int i = 0; i < library.length; i++) {

            try {

                verifyWord(library[i]);
                finalArrayList.add(library[i]);


            } catch (InvalidWordException e) {

                System.out.println(e.getMessage());
            }
        }

        library = new String[finalArrayList.size()];
        for (int i = 0; i < finalArrayList.size(); i++) {

            library[i] = finalArrayList.get(i);

        }

    }

    public String chooseWord() {
        return library[random.nextInt(library.length)];
    }

    public String[] getLibrary() {
        return library;
    }

    public void setLibrary(String[] library) {
        this.library = library;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }
}
