import java.io.CharArrayWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Enter number of players: ");
        int playersAmount = scanner.nextInt();

        String[] word = addWords();
        String[] wordDescription = addWordDescriptions();

        int wordIndex = random.nextInt(10);

        String wordToGuess = word[wordIndex];
        ArrayList<String> guessedLetters = new ArrayList<String>();

        String enteredLetterOrWord = "";
        String message = "";

        ArrayList<String> players = addPlayers(scanner, playersAmount);
        ArrayList<Integer> playersScores = addScores(players);

        int playersNumber = 0;
        int oneLettersPoint = 1000 / wordToGuess.length();

        System.out.println("                      " + wordDescription[wordIndex]);
        System.out.println();

        outer:
        while (guessedLetters.size() != wordToGuess.length()) {
            int amountOfLetter = 0;
            System.out.print("Enter the data: ");
            enteredLetterOrWord = scanner.next();

            if (enteredLetterOrWord.length() == 1) {
                if (wordToGuess.toLowerCase().contains(enteredLetterOrWord.toLowerCase())) {

                    for (int i = 0; i < wordToGuess.length(); i++) {
                        if (wordToGuess.toLowerCase().charAt(i) == enteredLetterOrWord.toLowerCase().charAt(0)) {
                            amountOfLetter++;
                            guessedLetters.add(enteredLetterOrWord);
                        }
                    }

                    enteredLetterOrWord = enteredLetterOrWord.toUpperCase();

                    playersScores.set(playersNumber,
                            playersScores.get(playersNumber) + oneLettersPoint * amountOfLetter);
                    message = "guessed";

                } else {
                    message = "no letter";
                    playersNumber++;
                    if (playersNumber >= playersAmount) {
                        playersNumber = 0;
                    }
                }
                if (playersScores.get(playersNumber) > 500) {
                    clearScreen(message);

                    playersNumber++;
                    if (playersNumber >= playersAmount) {
                        playersNumber = 0;
                    }
                    for (int i = 0; i < playersAmount - 1; i++) {
                        message = "other players try to guess word";
                        System.out.println("                      " + wordDescription[wordIndex]);
                        System.out.println();
                        showPlayersAndScores(players, playersScores, playersAmount, message, playersNumber);
                        if (message != "player won") {
                            System.out.print(
                                    "                       「 " + players.get(playersNumber) + " 」,your turn ➢  ");
                        }
                        continue outer;
                    }
                }
            }
        }
    }

    public static ArrayList<String> addPlayers(Scanner scanner, int playersAmount) {
        ArrayList<String> players = new ArrayList<String>();
        while (playersAmount != 0) {
            System.out.print("Enter your name: ");
            String player = scanner.next();
            players.add(player);
            playersAmount--;
            System.out.println("\033[H\033[J");
        }
        System.out.println("\033[H\033[J");
        Collections.shuffle(players);
        return players;
    }

    public static ArrayList<Integer> addScores(ArrayList<String> players) {
        ArrayList<Integer> playersScores = new ArrayList<Integer>();
        for (int i = 0; i < players.size(); i++) {
            playersScores.add(0);
        }
        return playersScores;
    }

    public static String[] addWords() {
        String[] word = new String[10];
        word[0] = "clip";
        word[1] = "turtle";
        word[2] = "colibri";
        word[3] = "duckbill";
        word[4] = "parrot";
        word[5] = "Vatican";
        word[6] = "Italy";
        word[7] = "Nauru";
        word[8] = "scorpion";
        word[9] = "octopus";
        return word;
    }

    public static String[] addWordDescriptions() {
        String[] wordDescription = new String[10];
        wordDescription[0] = "A piece of bent wire or plastic used for holding several sheets of paper together.";
        wordDescription[1] = "A reptile that has a shell covering its body";
        wordDescription[2] = "It's the smallest migrating bird.";
        wordDescription[3] = "Mammal that lays eggs.";
        wordDescription[4] = "brightly colored tropical bird.";
        wordDescription[5] = "The smallest country in the world.";
        wordDescription[6] = "Country that looks like a boot.";
        wordDescription[7] = "Country that doesn't have a capital.";
        wordDescription[8] = "Which animal can hold breath for 6 days?";
        wordDescription[9] = "Which animal has blue blood?";

        return wordDescription;
    }

    public static void showMessage(String message, ArrayList<String> players, int playersNumber,
                                   ArrayList<Integer> playersScores) {
        if (message == "guessed") {
            System.out.println("                        ⧼ You guessed the letter! ⧽");
        } else if (message == "no letter") {
            System.out.println("              ⧼ This letter doesn't exist! ⧽");
        } else if (message == "already called") {
            System.out.println("                        ⧼ This letter was already called! ⧽");
        } else if (message == "other players try to guess word") {
            System.out.println("          【 All players are given one attempt to guess the word! 】");
        } else if (message == "player won") {
            System.out.println("\033[H\033[J");
            System.out.println(
                    "                🎉  Congratulations, " + players.get(playersNumber) + "! You won! Your score is "
                            + playersScores.get(playersNumber) + "  🎉");
        }
    }

    public static void clearScreen(String message) {
        if (message != "player is out") {
            System.out.println("\033[H\033[J");
        }
    }

    public static void showPlayersAndScores(ArrayList<String> players, ArrayList<Integer> playersScores,
                                            int playersAmount, String message, int playersNumber) {
        for (int i = 0; i < players.size() - 1; i++) {
            System.out.print(players.get(i) + " ➱  ");
            System.out.print(playersScores.get(i));
            System.out.println();
        }
        System.out.print(players.get(playersAmount - 1) + " ➱  ");
        System.out.print(playersScores.get(playersAmount - 1));
        showMessage(message, players, playersNumber, playersScores);
        System.out.println();
    }
}