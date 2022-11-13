package com.andrewclarke.numbersorter;

import java.util.Arrays;
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
        // Use the Scanner class to get input from the user by creating a Scanner object
        Scanner scanner = new Scanner(System.in);
        // Read the input from the user and store it in a String variable, userInputSortType
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
        // request a list of space separated integers
        System.out.println("Please enter the list of integers you wish to sort, separated by spaces");

        // use scanner object to get user input
        Scanner scanner = new Scanner(System.in);
        // store user input in a String
        String userNumberInputString = scanner.nextLine();

        // check if user input is valid using the
        if (checkNumberValidity(userNumberInputString)){
            // convert string input from user into integer array
            int[] integersToSort = stringToIntegerArray(userNumberInputString);
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
        // if user input was not valid, request it again
        else {
            requestNumbersToSort(sortType);
        }
    }

    // checkNumberValidity method checks that the String userNumberInputString only contains numbers separated by
    // white space. Method returns true or false, and prints error message to the console notifying user of what the
    // issue is in the case of an invalid input.
    private static boolean checkNumberValidity(String userNumberInputString) {
        // remove all spaces from the string
        String inputStringNoSpaces = userNumberInputString.replace(" ", "");

        // check that without the spaces, the string contains only the digits 0-9, using a regex
        boolean onlyDigits = inputStringNoSpaces.matches("^[0-9]*$");

        // split the string into a string array, separating the elements by any number of whitespaces
        String[] splitArray = userNumberInputString.split("\\s+");

        // if there are at least two elements in the array, and all the elements are numbers return true
        if (splitArray.length >= 2 && onlyDigits){
            return true;
        }
        // else print to the console what the error is and return false
        else {
            String errorString = "Error:";
            if (!onlyDigits) {
                errorString += " Invalid characters detected.";
            }
            if (splitArray.length < 2) {
                errorString += " Fewer than 2 integers detected.";
            }
            System.out.println(errorString);
            return false;
        }
    }

    // stringToIntgerArray method converts an input string containing integers separated by spaces into an integer array
    private static int[] stringToIntegerArray(String userIntegerInputString) {
        String[] splitUserIntegerInputString = userIntegerInputString.split("\\s+"); // split by whitespaces
        int numberOfIntegers = splitUserIntegerInputString.length; // total # of elements in array
        int[] integerArray = new int[numberOfIntegers]; // setup empty integer array of the correct length

        // loop over array elements and parse integer values from string array and store as integers in integer array
        for (int element = 0; element < numberOfIntegers; element++) {
            integerArray[element] = Integer.parseInt(splitUserIntegerInputString[element]);
        }
        return integerArray; // return the converted integer array
    }


    // insertSort method sorts an input integer array into numerical order via the insert sort method and returns the
    // sorted list as a string
    public static String insertSort(int[] toSort){
        int length=toSort.length; // length of input array
        /*
        In insertion sort, we take the second element of the array and compare it to the first element. If the second
         element is greater than the first element, it is placed in front of the first element. At this point the
         first two elements of the array are in numerical order.

         The next stage is to get the first three elements of the array in order, then the first four and so on.

         The parameter iteration tracks the current element we are placing into the correct position, within the
         first 'iteration+1' number of elements.
            e.g. If iteration = 4, we are currently placing the 5th (due to zero based indexing) element into the
            correct position within the first 5 elements. Once this iteration of the for loop is complete, the first
            5 elements of the array will be in the correct order, and we move onto placing the 6th element into the
            correct place within the first 6 elements.

         The parameter numOfElementsToLeft initially stores the total number of elements to the left of the current
         element being sorted.

         The while loop states that as long as there are elements to the left of the current element, and we haven't
         compared it to all the elements to the left yet,
         AND
         The value of the current element is less than the next value to the left,
         THEN
         we shift the value which we just compared the current value to, to the right by one element
         and we update the number of elements to the left which we have yet to compare the current value to

         If there are no more elements to the left, or the current value being sorted is greater than the next
         element to the left, then the while loop no longer runs. In this case, we have shifted all the larger
         numbers to the right by one, and we insert the current value being sorted at the correct position within the
         first 'iteration + 1' values.

         Once we have sorted from the second element (iteration = 1) up to the final element
         i.e. until iteration == length
         all elements will be in numerical order.

         */
        for (int iteration = 1; iteration < length; iteration++){
            int current = toSort[iteration]; // value of current element being sorted
            int numOfElementsToLeft = iteration - 1; // current # of elements to left of current element being sorted

            // while there are elements to the left to compare to, and the current value is still less than the
            // elements on the left
            while (numOfElementsToLeft >= 0 && current < toSort[numOfElementsToLeft]){
                // shift element to the right by 1
                toSort[numOfElementsToLeft + 1] = toSort[numOfElementsToLeft];

                //number of elements to the left, which we are yet to compare the current value to
                numOfElementsToLeft--;
            }
            // place the current value into the correct position within the first 'iteration + 1' elements
            toSort[numOfElementsToLeft + 1] = current;
        }

        return Arrays.toString(toSort); // return sorted array as a String
    }


    // bubbleSort method sorts an input integer array into numerical order via the bubble sort method and returns the
    // sorted list as a string
    private static String bubbleSort(int[] toSort) {
        int length=toSort.length; //length of input array
        /*
        In the outermost loop, we are going through each interation of bubble sort. After the first outer loop the
        highest number is found and placed at the end of the array.
        Each loop that progresses will place the next highest number in the correct location at the end of the array.

        In the inner loop, we are looping over the individual elements of the array and comparing them one-by-one.
        As the highest numbers get placed into the correct position with each outer loop, we can make the inner loop
        shorter by 1, for each outer loop that we go through, hence the "i < length - loop - 1". i.e. We do not need to
        compare these numbers at the end of the loop as they are in the correct place.

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
        return Arrays.toString(toSort); // return sorted array as a string
    }

}