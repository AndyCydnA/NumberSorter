package com.andrewclarke.numbersorter;

public class StringToIntArray {
    private String inputString;
    private int[] intArray;
    private boolean stringValidity;

    // converts inputString into an integer array, intArray
    public void convert() {
        checkStringValidity();
        if (stringValidity) {
            String[] splitArray = inputString.split("\\s+");
            int numberOfIntegers = splitArray.length;
            int[] integerArray = new int[numberOfIntegers];

            // loop over array elements, parse integer values from string array, and store as integers in integer array
            for (int element = 0; element < numberOfIntegers; element++) {
                integerArray[element] = Integer.parseInt(splitArray[element]);
            }
            intArray = integerArray; // return the converted integer array
        }
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    public int[] getIntArray() {
        return intArray;
    }

    public boolean getStringValidity() {
        return stringValidity;
    }

    // Checks that the inputString can be converted to an integer array, and updates stringValidity field.
    // In the case of an invalid string, the error is also printed to the console.
    public void checkStringValidity() {
        // remove all spaces from the string
        String inputStringNoSpaces = inputString.replace(" ", "");

        // check that without the spaces, the string contains only the digits 0-9, using a regex
        boolean onlyDigits = inputStringNoSpaces.matches("^[0-9]*$");

        // split the string into a string array, separating the elements by any number of whitespaces
        String[] splitArray = inputString.split("\\s+");

        // check all input numbers are less than 10 digits long i.e. <1,000,000,000
        boolean tooLong = false;
        for (String eachElement:splitArray){
            if (eachElement.length() > 9){
                tooLong = true;
                break;
            }
        }

        // if there are at least two elements in the array, and all the elements are numbers, the stringValidity field
        // is set to false
        if (splitArray.length >= 2 && onlyDigits && !tooLong){
            stringValidity = true;
        }
        // else the error is printed to the console, and the stringValidity field is set to false
        else {
            String stringValidityError = "Error:";
            if (!onlyDigits) {
                stringValidityError += " Invalid characters detected.";
            }
            if (splitArray.length < 2) {
                stringValidityError += " Fewer than 2 integers detected.";
            }
            if (tooLong) {
                stringValidityError += " Integers exceeding 999999999 detected.";
            }
            System.out.println(stringValidityError);
            stringValidity = false;
        }
    }
}
