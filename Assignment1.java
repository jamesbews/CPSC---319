/**
 * A simple program that creates array and sorts it to users specification
 * and outputs the sorted array to a text file.
 * 
 * @author James Bews
 * @version 1.0
 * @since January 26, 2017
 */
import java.util.*;
import java.io.*;

public class Assignment1 {
	/**
	 * Bubble Sort Algorithm
	 * Drozdek pg. 475
	 * @param arr: array to be sorted
	 */
	public static void bubbleSort(int[] arr){
		int temp;
		for (int i = 0; i < arr.length - 1; i++){
			for (int j = 1; j < arr.length; j++){
				if (arr[j - 1] > arr[j]) {
					temp = arr[j - 1];
					arr[j - 1] = arr[j];
					arr[j] = temp;
				}
			}
		}
		
	}
/**
 * Insertion Sort Algorithm
 * Drozdek pg. 470
 * @param arr: array to be sorted
 */
	public static void insertionSort(int[] arr) {
		
		for (int i = 1, j; i < arr.length; i++) {
			int temp = arr[i];
			for(j = i; j > 0 && temp < arr[j - 1]; j--) {
				arr[j] = arr[j - 1];
			}
			arr[j] = temp;
		}
	}
	/**
	 * Merge Sort Algorithm
	 * http://www.softwareandfinance.com/Java/MergeSort_Recursive.html
	 * @param arr: array to be sorted
	 */
	
	public static void mergeSort (int[] arr) {
		mergesort(arr, 0, arr.length - 1);
	}
	
	public static void mergesort(int[] arr, int first, int last) {
		if (first < last){
			int mid = (first + last) / 2;
			mergesort(arr, first, mid);
			mergesort(arr, (mid + 1), last);
			merge(arr, first, (mid + 1), last);
		}
	}
	/**
	 * Merges subarrays after sorting
	 * Drozdek pg. 495
	 * @param arr: input array
	 * @param left: left bound
	 * @param mid: middle bound
	 * @param right: right bound
	 */
	public static void merge(int[] arr, int left, int mid, int right){
        int [] temp = new int[arr.length];
        int i, left_end, num, pos;
    
        left_end = (mid - 1);
        pos = left;
        num = (right - left + 1);
    
        while ((left <= left_end) && (mid <= right))
        {
            if (arr[left] <= arr[mid])
                temp[pos++] = arr[left++];
            else
                temp[pos++] = arr[mid++];
        }
    
        while (left <= left_end)
            temp[pos++] = arr[left++];
 
        while (mid <= right)
            temp[pos++] = arr[mid++];
 
        for (i = 0; i < num; i++)
        {
            arr[right] = temp[right];
            right--;
        }
    }
	
	/**
	 * Quick Sort Algorithm
	 * http://www.java67.com/2014/07/quicksort-algorithm-in-java-in-place-example.html
	 * @param arr: array to be sorted
	 */
	public static void quickSort (int[] arr) {
		quicksort(arr, 0, arr.length - 1);
		
	}
	
	public static void quicksort (int[] arr, int start, int end) {
		
		int index = sort(arr, start, end);
		
		if (start < index - 1){
			quicksort(arr, start, index - 1);
		}
		if (end > index){
			quicksort(arr, index, end);
		}
	}
	
	public static int sort(int[] arr, int left, int right) {
		int pivot = arr[left];
		
		while (left <= right){
			while (arr[left] < pivot) {
				left++;
			}
		
			while (arr[right] > pivot) {
				right--;
			}
			if (left <= right) {
				int temp = arr[left];
				arr[left] = arr[right];
				arr[right] = temp;
				left++;
				right--;
			}
		}
		return left;
	}
	/**
	 * Prints sorted array to txt file
	 * @param filename: name of output file
	 * @param arr: sorted array to be printed
	 */
	public static void writeToFile(String filename, int[] arr)throws IOException{
		File file = new File(filename);
		
		if(!file.exists())
			file.createNewFile();
		
		FileWriter fw = new FileWriter(filename + ".txt");
		BufferedWriter bw = new BufferedWriter(fw);
		
		for(int i = 0; i < arr.length; i++) {
			int temp = arr[i];
			bw.write(temp);
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
	
	/**
	 * Main Class
	 * @param args: order size algorithm output file
	 */
	public static void main(String[] args) throws Exception {
		
		String order, algorithm, output;
		int size;
		order = args[0];
		size = Integer.parseInt(args[1]);
		algorithm = args[2];
		output = args[3];
		long startTime = 0;
		long endTime = 0;
		
		/**
		 * Check for valid size
		 */
		if(size < 0){
			System.out.println("Error: Invalid size. Size must be greater than 0.");
			return;
		}
		/**
		 * Output user inputs
		 */
		System.out.println("User has specified the following:");
		System.out.println("Order: " + order.toUpperCase());
		System.out.println("Size: " + size);
		System.out.println("Algorithm: " + algorithm.toUpperCase());
		System.out.println("Output File: " + output.toUpperCase());
		System.out.println();
		System.out.println("Generated Array:");
		
		/**
		 * Generate array to specified size
		 */
		int[] array = new int[size];
		
		/**
		 * Populate array in either random, descending, ascending order
		 */
		if (order.toUpperCase().equals("RANDOM") || order.toUpperCase().equals("ASCENDING")
				|| order.toUpperCase().equals("DESCENDING")){
		
			if (order.toUpperCase().equals("RANDOM")) {
			Random r = new Random();
				for(int i = 0; i < array.length; i++) {
					array[i] = r.nextInt(size + 1);
					/**
					 * Code segment for printing to terminal
					 */
					//System.out.print(array[i] + " ");
				}
			}
			if (order.toUpperCase().equals("ASCENDING")){
				int start = 0;
				for (int i = 0; i < array.length; i++) {
					array[i] = start;
					start++;
					/**
					 * Code segment for printing to terminal
					 */
					//System.out.print(array[i] + " ");
					
				}
			}
			if (order.toUpperCase().equals("DESCENDING")){
				int end = size;
				for (int i = 0; i < array.length; i++) {
					array[i] = end;
					end--;
					/**
					 * Code segment for printing to terminal
					 */
					//System.out.print(array[i] + " ");
				}
			}
		}
		else {
			System.out.println("Error: Invalid order specified. Order must be either random, ascending or descending");
		}
		
		
		System.out.println();
		System.out.println("The array after sorting is:");
		/**
		 * Implement algorithm based on user specification
		 */
		if (algorithm.toUpperCase().equals("BUBBLE") || algorithm.toUpperCase().equals("INSERTION")
				|| algorithm.toUpperCase().equals("MERGE") || algorithm.toUpperCase().contentEquals("QUICK")){
		
			if (algorithm.toUpperCase().equals("BUBBLE")) {
				startTime = System.nanoTime();
				bubbleSort(array);
				endTime = System.nanoTime();
			}
			if (algorithm.toUpperCase().equals("INSERTION")) {
				startTime = System.nanoTime();
				insertionSort(array);
				endTime = System.nanoTime();
			}
			if (algorithm.toUpperCase().equals("MERGE")) {
				startTime = System.nanoTime();
				mergeSort(array);
				endTime = System.nanoTime();
			}
			if (algorithm.toUpperCase().equals("QUICK")) {
				startTime = System.nanoTime();
				quickSort(array);
				endTime = System.nanoTime();
			}		
			/**
			 * Code segment for displaying sorted array to terminal
			 */
			/*for(int i = 0; i < array.length; i++) {
				System.out.print(array[i] + " ");
			}*/
			/**
			 * Display total time taken for algorithm to run
			 */
			System.out.println();
			long total = (endTime - startTime);
			System.out.println("The algorithm took " + total + "ns to run");
		}
		else {
			System.out.println("Error: Invalid algorithm specified. Algorithm must be either bubble, insertion, merge, or quick.");
		}
		/**
		 * Print sorted array to text file
		 */
		try{			
			writeToFile(output, array);
		}
		catch (IOException e) {
			System.err.println("An IO exception was caught:" + e.getMessage());
		}
		
	} /* End Main*/

} /*End Assignment 1*/
