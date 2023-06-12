/*
 * Süleyman Tolga Acar
 * 2021400237
 * 03.05.2023
 * 
 * This is the class that contains the main method.
 * In this class, the input file is read and the grid is created.
 * Then, the user is asked to enter the coordinates to elevate.
 * After the user enters 10 coordinates, the program calculates the score.
 */

import java.io.File;
import java.util.Scanner;

public class suleyman_tolga_acar {
    public static void main(String[] args) throws Exception {
        File file = new File("input.txt");
        Grid grid = new Grid(file);
        grid.print();
        int i = 0; // counter for the number of modifications
        Scanner scanner = new Scanner(System.in);
        while (i < 10) {
            System.out.printf("Add stone %s / 10 to coordinate:\n", i + 1);
            String input = scanner.nextLine();
            String[] inputArray = input.split("");
            // Getting the x and y coordinates from the input and converting them to integers
            String xStr;
            String yStr;
            if (inputArray.length == 2) {
                xStr = inputArray[0];
                yStr = inputArray[1];
            } else if (inputArray.length == 4) {
                xStr = inputArray[0] + inputArray[1];
                yStr = inputArray[2] + inputArray[3];
            }
            else if (inputArray.length == 3) {
                if (Character.isDigit(inputArray[1].charAt(0))) {
                    xStr = inputArray[0];
                    yStr = inputArray[1] + inputArray[2];
                } else {
                    xStr = inputArray[0] + inputArray[1];
                    yStr = inputArray[2];
                }
            }
            else {
                System.out.println("Wrong input!");
                continue;
            }
            if (Character.isDigit(xStr.charAt(0)) || (xStr.length() == 2 && Character.isDigit(xStr.charAt(1)))) {
                System.out.println("Wrong input!");
                continue;
            }
            if (!Character.isDigit(yStr.charAt(0)) || (yStr.length() == 2 && !Character.isDigit(yStr.charAt(1)))) {
                System.out.println("Wrong input!");
                continue;
            }
            int x = Grid.getNum(xStr);
            int y = Integer.parseInt(yStr);
            // If the modification is successful, the counter is incremented and the grid is printed, otherwise the user is asked to enter another coordinate
            if (grid.elevate(x, y)) {
                i += 1;
                grid.print();
                System.out.println("---------------");
            }
        }
        scanner.close();

        // The grid is flooded and the lakes are found
        grid.floodAll(grid.getFlood());
        grid.findAllLakes();
        // The lake heights are calculated and the score is calculated and printed
        grid.calculateLakeHeights();
        grid.fixLakes();
        grid.print();
        System.out.printf("Final score: %.2f", grid.calculateScore());
    }
}
