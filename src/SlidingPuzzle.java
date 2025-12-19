import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SlidingPuzzle {
	
	Scanner scan = new Scanner(System.in);
	int[][] puzzle3x3 = new int[3][3];
	int[][] puzzle4x4 = new int[4][4];
	int[][] puzzle5x5 = new int[5][5];
	
	public SlidingPuzzle() {
		
	}
	
	// prompts the user to choose a puzzle and returns a string of the puzzle name
	public int[][] puzzleSelector() {
		String puzzleChoice = "";
		System.out.println("Which puzzle do you want? (type in 3x3, 4x4, or 5x5)");
		
		// validates input as a puzzle that exists
		Boolean validString = false;
		while(!validString) {
			try {
				puzzleChoice = scan.next();
				if (puzzleChoice.equals("3x3") || puzzleChoice.equals("4x4") || puzzleChoice.equals("5x5")) {
					validString = true;
				}
				else {
					System.out.println("Invalid input, must be a valid puzzle");
				}
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid input, must be a valid puzzle");
				scan.next();
			}
		}
		
		// returns the correct array
		if (puzzleChoice.equals("3x3")) {
			return puzzle3x3;
		}
		else if (puzzleChoice.equals("4x4")) {
			return puzzle4x4;
		}
		else {
			return puzzle5x5;
		}
	}
	
	// reads from the chosen file and adds the data to the correct puzzle array
	public void fileReader(String puzzleChoice) {
		
		// creates a file and file reader based on what the user chose
		File puzzleFile = new File("." + File.separator + "data" + File.separator + puzzleChoice + ".txt");
		Scanner puzzleReader = null;
		try {
			puzzleReader = new Scanner(puzzleFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// creates and initializes size and targetPuzzle based on size
		int size = Integer.parseInt(puzzleChoice.substring(0, 1));
		int[][] targetPuzzle = new int[size][size];
		
		// gets data from file and puts it in an array
		String line = puzzleReader.nextLine();
		String[] numbers = line.split(",");
		for (int i = 0; i < numbers.length; i++) {
			System.out.println(numbers[i]);
		}
		
				
		// adds the data from the local array to targetPuzzle
		int temp = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == size - 1 && j == size - 1) {
				}
				else {
					targetPuzzle[i][j] = Integer.parseInt(numbers[temp]);
					temp++;
				}
			}
		}
		
		// puts data from targetPuzzle in the correct global 2d array
		if (puzzleChoice.equals("3x3")) {
			puzzle3x3 = targetPuzzle;
		}
		else if (puzzleChoice.equals("4x4")) {
			puzzle4x4 = targetPuzzle;
		}
		else {
			puzzle5x5 = targetPuzzle;
		}
		puzzleReader.close();
	}
	
	// prints current state of the puzzle
	public void printState(int[][] puzzle) {
		for (int i = 0; i < puzzle.length; i++) {
			// prints the horizontal lines between every row
			for (int j = 0; j < puzzle.length; j++) {
				System.out.print("+----");
			}
			System.out.println("+");
			// prints the numbers and the sides
			for (int j = 0; j < puzzle.length; j++) {
				// prints one digit numbers
				if (puzzle[i][j] > 9) {
					System.out.print("| " + puzzle[i][j] + " ");
				}
				// prints empty slot
				else if (puzzle[i][j] == 0) {
					System.out.print("|    ");
				}
				// prints two digit numbers
				else {
					System.out.print("|  " + puzzle[i][j] + " ");
				}
			}
			System.out.println("|");
		}
		for (int j = 0; j < puzzle.length; j++) {
			System.out.print("+----");
		}
		System.out.println("+");
	}
	
	// checks if puzzle is solved correctly
	public boolean checkPuzzle(int[][] puzzle) {
		int checker = 1;
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle.length; j++) {
				// checks if last index is equal to 0
				if (i == puzzle.length - 1 && j == puzzle.length - 1) {
					if (puzzle[i][j] != 0) {
						return false;
					}
				}
				
				// checks if the index is the correct number
				else {
					if (puzzle[i][j] != checker) {
						return false;
					}
				}
				// increments checker for the next index
				checker++;
			}
		}
		
		return true;
	}
	
	// allows user to solve a puzzle
	public void solvePuzzle(int[][] puzzle) {
		// initialized row and column and prints initial state of the puzzle
		int col = 0;
		int row = 0;
		printState(puzzle);
		
		// changes the puzzle while it isn't solved
		while(!checkPuzzle(puzzle)) {
			// gets the index of the empty spot
			for (int i = 0; i < puzzle.length; i++) {
				for (int j = 0; j < puzzle.length; j++) {
					if (puzzle[i][j] == 0) {
						col = j;
						row = i;
					}
				}
			}
			
			boolean checker = false;
			
			// gets value to move into empty spot and initializes its row and column
			System.out.println("Which piece do you want to slide into the empty slot?");
			int piece = scan.nextInt();
			int prow = 10;
			int pcol= 10;
			
			// checks if the piece value exists and updates it to the correct position
			for (int i = 0; i < puzzle.length; i++) {
				for (int j = 0; j < puzzle.length; j++) {
					if (puzzle[i][j] == piece) {
						pcol = j;
						prow = i;
					}
				}
			}
			
			// checks if the piece is above or below the empty spot
			if (prow == row && (col == pcol + 1 || col == pcol - 1)) {
				checker = true;
			}
			
			// checks if the piece is to the right or left of the empty spot
			if (pcol == col && (row == prow + 1 || row == prow - 1)) {
				checker = true;
			}
			
			// Either states the piece can't be moved there or updates the puzzle for the move
			if (!checker) {
				System.out.println("You can't move that piece");
			}
			else {
				puzzle[row][col] = piece;
				puzzle[prow][pcol] = 0;
			}
			
			// prints puzzle state after the piece was moved
			printState(puzzle);
		}
		
		System.out.println("The puzzle has been solved");
	}
	
	// prints out unsolved puzzles
	public void printUnsolvedPuzzles() {
		if (!checkPuzzle(puzzle3x3)) {
			printState(puzzle3x3);
		}
		if (!checkPuzzle(puzzle4x4)) {
			printState(puzzle4x4);
		}
		if (!checkPuzzle(puzzle5x5)) {
			printState(puzzle5x5);
		}
		if (checkPuzzle(puzzle3x3) && checkPuzzle(puzzle4x4) && checkPuzzle(puzzle5x5)) {
			System.out.println("All puzzles have been solved");
		}
	}
	
	// allows user to choose what they want to do
	public void mainMenu() {
		fileReader("3x3");
		fileReader("4x4");
		fileReader("5x5");
		
		int option = getOption();
		
		while (option != 1) {
			// prints unsolved puzzles and lets the user do something else
			if (option == 2) {
				printUnsolvedPuzzles();
				option = getOption();
			}
			// lets a user solve a puzzle and lets them do something else
			else if (option == 3) {
				solvePuzzle(puzzleSelector());
				option = getOption();
			}
			else {
				option = 1;
			}
		}
		
		scan.close();
	}
	
	// gets and validates the option for the main menu
	public int getOption() {
		// prints out main menu options
		System.out.println("Main menu:");
		System.out.println("Option 1: Exit");
		System.out.println("Option 2: Print unsolved puzzles");
		System.out.println("Option 3: Solve a puzzle");
		System.out.println("Choose an option (1, 2, or 3)");
		int option = 0;
		
		// makes sure input is an integer and is one of the options
		boolean valid = false;
		while(!valid) {
			try {
				option = scan.nextInt();
				if (option > 0 && option < 4) {
					valid = true;
				}
				else {
					System.out.println("Invalid input, must be a valid integer");
				}
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid input, must be a valid integer");
				scan.next();
			}
		}
		
		return option;
	}
	
}
