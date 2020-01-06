import java.io.*;
import java.util.Random;

class BattleshipTestDrive {
    public static void main(String[] args) {
        int numOfGuesses = 0;
        InputReader inputReader = new InputReader();
        Battleship ship = new Battleship();

        Random rand = new Random();
        int startLocation = 1 + rand.nextInt(5);
        int[] locations = {startLocation, startLocation + 1, startLocation + 2};
        ship.setLocationCells(locations);

        boolean isAlive = true;
        while (isAlive == true){
            String userGuess = inputReader.getUserInput("Enter a number between 0 and 7: ");
            String result = ship.checkYourself(userGuess);
            numOfGuesses++;
            if (result == "kill") {
                isAlive = false;
                System.out.println("Congratulations, you sunk the battleship in " + numOfGuesses + " guesses!");
            }
        }
    }
}

class Battleship {
    private int[] locationCells;
    private int numOfHits = 0;
    private final int hitValue = -1;

    public void setLocationCells(int[] locations){
        locationCells = locations;
    }

    public String checkYourself(String userGuess){
        int guess = Integer.parseInt(userGuess);
        String result = "miss";

        for (int i = 0; i < locationCells.length; i++) {
            if (guess == locationCells[i]) {
                result = "hit";
                locationCells[i] = hitValue;
                numOfHits++;
                break;
            }
        }

        if (numOfHits == locationCells.length) {
            result = "kill";
        }

        System.out.println(result);
        return result;
    }
}

class InputReader {
    public String getUserInput(String prompt) {
        String userGuess = null;
        System.out.print(prompt + "  ");

        try{
            String validValuePattern = "[0-7]";
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            userGuess = reader.readLine();
            while (userGuess.length() == 0 || !userGuess.matches(validValuePattern)) {
                System.out.print("Please enter a valid value:  ");
                userGuess = reader.readLine();
            }
        } catch(IOException e) {
            System.out.println("IOException: " + e);
        }
        return userGuess;
    }
}