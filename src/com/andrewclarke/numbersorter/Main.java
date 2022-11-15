/*
Potential future improvements:
- consider implementing constructors to initialise the NumberSorter and StringToIntArray classes
- research exceptions, and implement throughout. One area in particular is with mutator methods to check valid value.
- refactoring may be beneficial

Limitation: Previously, if a user provided a number greater than maximum integer 2147483647, it would
overflow the maximum integer amount and causes an error. To resolve this user inputs have been temporarily limited to
999,999,999. Need to research dealing with exceptions to increase this limit. Could also use long type, but same issue
would still occur, just at much larger numbers.

Limitation: Current number validity checking only allows positive integers to be specified. Need to change the way
number validity is checked. Researching dealing with exceptions may also help here.

Limitation: Printing to console limits amount of data that is visible, for larger lists information may be lost.
Could add importing from and exporting to files as a feature - requires further study.

Limitation: only works with integers currently. Could look at incorporating float/double values.

Limitation: currently no unit tests. Once implementation of unit tests has been studied, they should be implemented.
 */

package com.andrewclarke.numbersorter;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    // main method - calls getUserInput function to request user input
    public static void main(String[] args) {
        getUserInput();
    }

    // getUserInput method requests the user to select the sorting algorithm they wish to use
    private static void getUserInput(){
        // Request input from user
        System.out.println("Please select the sorting algorithm you wish to use by typing 1, 2, or 3.");
        System.out.println("1. Insert sort");
        System.out.println("2. Bubble sort");
        System.out.println("3. Both insert and bubble sort");

        // Use the Scanner class to get input from the user by creating a Scanner object and storing input
        Scanner scanner = new Scanner(System.in);
        String userInputSortType = scanner.nextLine();

        // check that a valid input of 1, 2 or 3 is provided
        switch (userInputSortType) {
            case "1", "2", "3": // valid selection - proceed to requesting numbers to sort
                requestNumbersToSort(Integer.parseInt(userInputSortType));
                break;
            default: //invalid selection - request input again
                System.out.println("Invalid input.");
                getUserInput();
                break;
        }
    }



    // requestNumbersToSort method requests the integers to be sorted
    private static void requestNumbersToSort(int sortType) {
        // get user input
        System.out.println("Please enter the list of positive integers (<= 999999999) you wish to sort, separated " +
                "by" + "spaces, or enter 'random' followed by the number of random integers you wish to sort. e.g. " +
                "random 200. Max # of integers to sort = 999999999.");

        Scanner scanner = new Scanner(System.in);
        String userNumberInputString = scanner.nextLine();

        // check if "random" and a valid integer are provided as the inputs
        String[] stringArrayUserInput = userNumberInputString.split("\\s+");
        if (stringArrayUserInput.length == 2 && stringArrayUserInput[0].equals("random") &&
                stringArrayUserInput[1].matches("^[0-9]*$")) {
            // if integer input is >999,999,999, return error to user and request another input
            if (stringArrayUserInput[1].length() > 9){
                System.out.println("Error: integer value " + stringArrayUserInput[1] + " exceeds 1000000000.");
                requestNumbersToSort(sortType);
            }
            // otherwise input is valid and the random input array is generated and passed to the sorting method
            else{
                NumberSorter sorter = new NumberSorter();
                sorter.generateRandom(Integer.parseInt(stringArrayUserInput[1]));
                System.out.println("Initial values:" + Arrays.toString(sorter.getUnsortedIntegerArray()));
                callSortMethod(sortType, sorter);
            }
        }

        // check if user input is a valid list of whitespace-separated integers
        else {
            StringToIntArray strToIntArray = new StringToIntArray();
            strToIntArray.setInputString(userNumberInputString);
            strToIntArray.checkStringValidity();
            if (strToIntArray.getStringValidity()){
                strToIntArray.convert();
                NumberSorter sorter = new NumberSorter();
                sorter.setUnsortedIntegerArray(strToIntArray.getIntArray());
                callSortMethod(sortType, sorter);
            }
            // if user input is not valid, request it again
            else {
                requestNumbersToSort(sortType);
            }
        }



    }




    // callSortMethod method calls the requested sorting method(s) and prints the results to the console
    private static void callSortMethod(int sortType, NumberSorter sorter) {
        // call requested method and print output to console
        switch (sortType) {
            case 1: // insert sort
                sorter.setSortType(1);
                sorter.sort();
                printOutput(sorter);
                break;
            case 2: // bubble sort
                sorter.setSortType(2);
                sorter.sort();
                printOutput(sorter);
                break;
            case 3: // insert and bubble sort
                sorter.setSortType(1);
                sorter.sort();
                printOutput(sorter);

                sorter.setSortType(2);
                sorter.sort();
                printOutput(sorter);
                break;
        }
    }

    // prints the sorted numbers and sorting time of a NumberSorter object to the console
    private static void printOutput(NumberSorter sorter){
        System.out.println(sorter.getSortTypeAsString() + ": " + Arrays.toString(sorter.getSortedIntegerArray()));
        System.out.println(sorter.getSortTypeAsString() + ": " + sorter.getTimeToSortAsString());
    }









}