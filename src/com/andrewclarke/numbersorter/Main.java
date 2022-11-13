/*
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

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    // main method - calls getUserInput function to request user input
    public static void main(String[] args) {
        getUserInput();
    }

    // getUserInput method requests the user to select the sorting algorithm they wish to use
    public static void getUserInput(){
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
                int[] integersToSort = generateRandomIntegerArray(Integer.parseInt(stringArrayUserInput[1]));
                System.out.println("Initial values:" + Arrays.toString(integersToSort));
                callSortMethod(sortType, integersToSort);
            }
        }

        // check if user input is a valid list of whitespace-separated integers
        else if (checkNumberValidity(userNumberInputString)) {
            // convert string input from user into integer array
            int[] integersToSort = stringToIntegerArray(userNumberInputString);
            callSortMethod(sortType, integersToSort);
        }

        // if user input is not valid, request it again
        else {
            requestNumbersToSort(sortType);
        }
    }

    // generateRandomIntegerArray method returns a random integer array of length 'length' using util.Random
    private static int[] generateRandomIntegerArray(int length) {
        int[] randomArray = new int[length];
        Random randomInt = new Random();

        // loop over integer array and set each value equal to a random integer
        for (int currentInt = 0; currentInt < length; currentInt++) {
            randomArray[currentInt] = randomInt.nextInt(0, 2147483647);
        }
        return randomArray; // return the randomised integer array
    }


    // callSortMethod method calls the requested sorting method(s) and prints the results to the console
    public static void callSortMethod(int sortType, int[] integersToSort) {
        // call requested method and print output to console
        switch (sortType) {
            case 1: // insert sort
                System.out.println("Insert: " + insertSort(integersToSort));
                break;
            case 2: // bubble sort
                System.out.println("Bubble: " + bubbleSort(integersToSort));
                break;
            case 3: // insert and bubble sort
                System.out.println("Insert: " + insertSort(integersToSort));
                System.out.println("Bubble: " + bubbleSort(integersToSort));
                break;
        }
    }

    // checkNumberValidity method checks that the string userNumberInputString only contains numbers separated by
    // white space. Method returns true or false, and prints error message to the console notifying user of the
    // issue is in the case of an invalid input.
    private static boolean checkNumberValidity(String userNumberInputString) {
        // remove all spaces from the string
        String inputStringNoSpaces = userNumberInputString.replace(" ", "");

        // check that without the spaces, the string contains only the digits 0-9, using a regex
        boolean onlyDigits = inputStringNoSpaces.matches("^[0-9]*$");

        // split the string into a string array, separating the elements by any number of whitespaces
        String[] splitArray = userNumberInputString.split("\\s+");

        // check all input numbers are less than 10 digits long i.e. <1,000,000,000
        boolean tooLong = false;
        for (String eachElement:splitArray){
            if (eachElement.length() > 9){
                tooLong = true;
                break;
            }
        }

        // if there are at least two elements in the array, and all the elements are numbers, method returns true
        if (splitArray.length >= 2 && onlyDigits && !tooLong){
            return true;
        }
        // else the error is printed to the console, and the method returns false
        else {
            String errorString = "Error:";
            if (!onlyDigits) {
                errorString += " Invalid characters detected.";
            }
            if (splitArray.length < 2) {
                errorString += " Fewer than 2 integers detected.";
            }
            if (tooLong) {
                errorString += " Integers exceeding 999999999 detected.";
            }
            System.out.println(errorString);
            return false;
        }
    }

    // stringToIntegerArray method converts an input string containing integers separated by spaces into an integer
    // array
    private static int[] stringToIntegerArray(String userIntegerInputString) {
        String[] splitUserIntegerInputString = userIntegerInputString.split("\\s+"); // split by whitespaces

        int numberOfIntegers = splitUserIntegerInputString.length;
        int[] integerArray = new int[numberOfIntegers];

        // loop over array elements, parse integer values from string array, and store as integers in integer array
        for (int element = 0; element < numberOfIntegers; element++) {
            integerArray[element] = Integer.parseInt(splitUserIntegerInputString[element]);
        }
        return integerArray; // return the converted integer array
    }


    // insertSort method sorts an input integer array into numerical order via the insert sort method and returns the
    // sorted list as a string
    public static String insertSort(int[] toSort){
        Instant startTime = Instant.now();
        int length=toSort.length;
        /*
        In insertion sort, the second element of the array is compared to the first element. If the second element is
        greater than the first element, it is placed in front of the first element. At this point the
        first two elements of the array are in numerical order.

        The next stage is to get the first three elements of the array in order, then the first four, and so on.

        The parameter iteration tracks the current element that is being placed into the correct position, within the
        first 'iteration+1' number of elements.
            e.g. If iteration = 4, the 5th (due to zero based indexing) element is currently being placed into the
            correct position within the first 5 elements. Once this iteration of the for loop is complete, the first
            5 elements of the array will be in the correct order. Then the code moves onto placing the 6th element into
            the  correct place within the first 6 elements.

        The parameter numOfElementsToLeft stores the total number of elements to the left of the current element
        being sorted, that have not been compared to the current element.

        The while loop states that as long as there are elements to the left of the current element, that have not
        been compared to the current element,
        AND
        The value of the current element is less than the next value to the left,
        THEN
        the value which was just compared the current value being sorted is shifted to the right by one element.
        Additionally, the number of elements to the left of the current value, which have not yet been compared to
        the current value, is updated.

        If there are no more elements to the left, or the current value being sorted is greater than the next
        element to the left, then the while loop no longer runs. In this case, all larger numbers have been shifted
        to the right by one, and the current value being sorted is inserted at the correct position within the
        first 'iteration + 1' values.

        Once the second element (iteration == 1) through to the final element have been sorted (iteration == length),
        then all elements will be in numerical order.
         */

        for (int iteration = 1; iteration < length; iteration++){
            int current = toSort[iteration]; // value of current element being sorted
            int numOfElementsToLeft = iteration - 1; // current # of elements to left of current element being sorted

            // while there are elements to the left to compare to, and the current value is still less than the
            // elements on the left
            while (numOfElementsToLeft >= 0 && current < toSort[numOfElementsToLeft]){
                // shift element to the right by 1
                toSort[numOfElementsToLeft + 1] = toSort[numOfElementsToLeft];

                // update number of elements to the left, which are yet to be compared to the current value
                numOfElementsToLeft--;
            }
            // place the current value into the correct position within the first 'iteration + 1' elements
            toSort[numOfElementsToLeft + 1] = current;
        }

        //time tracking
        Instant endTime = Instant.now();
        Duration totalTime = Duration.between(startTime, endTime);
        String timeString = totalTime.toSeconds() + "." + totalTime.toNanos() + " seconds: ";

        return timeString + Arrays.toString(toSort); // return sorted array as a String
    }


    // bubbleSort method sorts an input integer array into numerical order via the bubble sort method and returns the
    // sorted list as a string
    private static String bubbleSort(int[] toSort) {
        Instant startTime = Instant.now();
        int length=toSort.length;
        /*
        The outermost loop goes through each iteration of bubble sort. After the first outer loop is complete, the
        highest number will have been found and placed at the end of the array. Each subsequent loop will place the
        next highest number in the correct location at the end of the array.

        The inner loop loops over the individual elements of the array and compares them one-by-one. If the number to
        current number is larger than the next number they are swapped, and the loop moves onto the next element,
        gradually moving the next highest number into the correct position.

        As the next highest number get placed into the correct position with each outer loop, it is no longer
        necessary to make comparisons to these numbers. Therefore, the inner loop becomes shorter by 1 for each outer
        loop that is completed, hence the "i < length - loop - 1".
         */
        for (int loop = 0; loop < length - 1; loop++){
            for (int i = 0; i < length - loop - 1; i++) {
                if (toSort[i] > toSort[i + 1]) { // if current value is greater than the next, then swap them
                    int temp = toSort[i]; // temporary variable to allow rearranging of elements
                    toSort[i] = toSort[i + 1];
                    toSort[i + 1] = temp;
                }
            }
        }

        //time tracking
        Instant endTime = Instant.now();
        Duration totalTime = Duration.between(startTime, endTime);
        String timeString = totalTime.toSeconds() + "." + totalTime.toNanos() + " seconds: ";

        return timeString + Arrays.toString(toSort); // return sorted array as a string
    }

}