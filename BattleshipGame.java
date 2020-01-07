import java.io.*;
import java.util.ArrayList;
import java.util.Collections;


public class BattleshipGame {
    private GameHelper helper = new GameHelper();
    private ArrayList<Battleship> battleshipList = new ArrayList<Battleship>();
    private int numOfGuesses = 0;

    private void setUpGame() {
        Battleship one = new Battleship("Alpha");
        Battleship two = new Battleship("Beta");
        Battleship three = new Battleship("Omega");
        Collections.addAll(battleshipList, one, two, three);

        System.out.println("Your goal is to sink three battleships.");
        System.out.println("They are located on an alphanumeric 7x7 grid (cell examples: A0, C5, G6)");
        System.out.println("These ships are named Alpha, Beta, and Omega.");
        System.out.println("Try to sink them all in the fewest number of guesses.");

        for (Battleship battleshipToSet : battleshipList) {
            ArrayList<String> newLocation = helper.placeBattleship(3);
            battleshipToSet.setLocationCells(newLocation);
        }
    }

    private void startPlaying() {
        while (!battleshipList.isEmpty()) {
            String userGuess = helper.getUserInput("Enter a guess:");
            checkUserGuess(userGuess);
        }
        finishGame();
    }

    private void checkUserGuess(String userGuess){
        numOfGuesses++;
        String result = "miss";

        for (Battleship battleshipToTest : battleshipList) {
            result = battleshipToTest.checkYourself(userGuess);
            if (result.equals("hit")) {
                break;
            }
            if (result.equals("kill")) {
                battleshipList.remove(battleshipToTest);
                break;
            }
        }
        System.out.println(result);
    }

    private void finishGame() {
        System.out.println("All battleships have been sunk! Your mission is complete.");
        if (numOfGuesses <= 18) {
            System.out.println("It only took you " + numOfGuesses + " guesses.");
            System.out.println("Your skill is beyond compare.");
        } else {
            System.out.println("Took you long enough. " + numOfGuesses + " guesses.");
            System.out.println("You'll have to do better than that next time.");
        }
    }

    public static void main(String[] args) {
        BattleshipGame game = new BattleshipGame();
        game.setUpGame();
        game.startPlaying();
    }
}

class Battleship {
    private ArrayList<String> locationCells;
    private String name;

    public Battleship(String name) {
        this.name = name;
    }

    public void setLocationCells(ArrayList<String> locations){
        locationCells = locations;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String checkYourself(String userInput){
        String result = "miss";
        int index = locationCells.indexOf(userInput);

        if (index >= 0) {
            locationCells.remove(index);

            if (locationCells.isEmpty()) {
                result = "kill";
                System.out.println("Well done! You sunk " + name + "!");
            } else {
                result = "hit";
            }
        }

        return result;
    }
}

class GameHelper {
    private static final String alphabet = "abcdefg";
    private int gridLength = 7;
    private int gridSize = 49;
    private int[] grid = new int[gridSize];
    private int shipCount = 0;

    public String getUserInput(String prompt) {
        String inputLine = null;
        System.out.print(prompt + "  ");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            inputLine = reader.readLine();
            if (inputLine.length() == 0) return null;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return inputLine.toLowerCase();
    }

    public ArrayList<String> placeBattleship(int shipSize) {
        ArrayList<String> alphabetCells = new ArrayList<String>();
        String temp = null;
        int[] coordinates = new int[shipSize];
        int attempts = 0;
        boolean success = false;
        int location = 0;

        shipCount++;
        int increment = 1;
        if ((shipCount % 2) == 1) {
            increment = gridLength;
        }

        while (!success & attempts++ < 200) {
            location = (int) (Math.random() * gridSize);
            int x = 0;
            success = true;
            while (success && x < shipSize) {
                if (grid[location] == 0) {
                    coordinates[x++] = location;
                    location += increment;
                    if (location >= gridSize){
                        success = false;
                    }
                    if (x > 0 && (location % gridLength == 0)) {
                        success = false;
                    }
                } else {
                    success = false;
                }
            }
        }

        int x = 0;
        int row = 0;
        int column = 0;
        while (x < shipSize) {
            grid[coordinates[x]] = 1;
            row = (int) (coordinates[x] / gridLength);
            column = coordinates[x] % gridLength;
            temp = String.valueOf(alphabet.charAt(column));
            alphabetCells.add(temp.concat(Integer.toString(row)));
            x++;
        }

        return alphabetCells;
    }
}
