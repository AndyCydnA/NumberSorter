package com.andrewclarke.numbersorter;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class NumberSorter {
    private int sortType;
    private Duration timeToSort;
    private int[] unsortedIntegerArray;
    private int[] sortedIntegerArray;

    public void setSortType(int sortType) {
        this.sortType = sortType; //could add check to ensure valid sortType and throw exception if not
    }

    public void setUnsortedIntegerArray(int[] unsortedIntegerArray) {
        this.unsortedIntegerArray = unsortedIntegerArray;
    }

    public int[] getUnsortedIntegerArray() {
        return unsortedIntegerArray;
    }

    public int[] getSortedIntegerArray(){
        return sortedIntegerArray;
    }

    public String getTimeToSortAsString(){
        return timeToSort.toSeconds() + "." + timeToSort.toNanos() + " seconds";
    }

    public String getSortTypeAsString() {
        switch (sortType) {
            case 1:
                return "Insert Sort";
            case 2:
                return "Bubble Sort";
            default:
                return "No valid selection";
        }
    }

    // generateRandom method updates unsortedIntegerArray with a random integer array of length 'length' using util
    // .Random
    public void generateRandom(int length) {
        int[] randomArray = new int[length];
        Random randomInt = new Random();

        // loop over integer array and set each value equal to a random integer
        for (int currentInt = 0; currentInt < length; currentInt++) {
            randomArray[currentInt] = randomInt.nextInt(0, 2147483647);
        }
        unsortedIntegerArray = randomArray; // set the randomised integer array
    }

    // calls requested sorting method based on sortType
    public void sort(){
        switch (sortType){
            case 1:
                insertSort();
                break;
            case 2:
                bubbleSort();
                break;
            default:
                break; //could throw exception here once researching how to do so
        }

    }

    // insertSort method sorts unsortedIntegerArray into numerical order via the insert sort method and updates the
    // timeToSort and sortedIntegerArray fields
    private void insertSort(){
        Instant startTime = Instant.now();
        int length=unsortedIntegerArray.length;
        int[] sortingArray = unsortedIntegerArray;
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
            int current = sortingArray[iteration]; // value of current element being sorted
            int numOfElementsToLeft = iteration - 1; // current # of elements to left of current element being sorted

            // while there are elements to the left to compare to, and the current value is still less than the
            // elements on the left
            while (numOfElementsToLeft >= 0 && current < sortingArray[numOfElementsToLeft]){
                // shift element to the right by 1
                sortingArray[numOfElementsToLeft + 1] = sortingArray[numOfElementsToLeft];

                // update number of elements to the left, which are yet to be compared to the current value
                numOfElementsToLeft--;
            }
            // place the current value into the correct position within the first 'iteration + 1' elements
            sortingArray[numOfElementsToLeft + 1] = current;
        }

        //time tracking
        Instant endTime = Instant.now();
        timeToSort = Duration.between(startTime, endTime);
        sortedIntegerArray = sortingArray; // return sorted array as a
        // String
    }

    // bubbleSort method sorts unsortedIntegerArray into numerical order via the bubble sort method and updates the
    // timeToSort and sortedIntegerArray fields
    private void bubbleSort() {
        Instant startTime = Instant.now();
        int length=unsortedIntegerArray.length;
        int[] sortingArray = unsortedIntegerArray;
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
                if (sortingArray[i] > sortingArray[i + 1]) { // if current value is greater than the next, then swap them
                    int temp = sortingArray[i]; // temporary variable to allow rearranging of elements
                    sortingArray[i] = sortingArray[i + 1];
                    sortingArray[i + 1] = temp;
                }
            }
        }

        //time tracking
        Instant endTime = Instant.now();
        timeToSort = Duration.between(startTime, endTime);


        sortedIntegerArray = sortingArray; // return sorted array as a string
    }
}
