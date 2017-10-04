import java.io.*;

/**
 * A simple program that sorts an input text file into respective lists of anagrams which will then be sorted 
 * alphabetically. Each list will be output to a text file that is also sorted in alphabetical order. Execution 
 * time is displayed on the console
 * @author James Bews
 * @version 1.0
 * @since February 11, 2017
 */
public class Assign2 {
	
	/**
	 * Insertion Sort Algorithm
	 * Adapted from: Drozdek pg. 470
	 * @param arr: array to be sorted
	 */
	public static void insertionSort(String[] arr) {
		
		for (int i = 1, j; i < arr.length; i++) {
			String temp = arr[i];
			for(j = i; j > 0 && temp.compareTo(arr[j - 1]) > 0; j--) {
				arr[j] = arr[j - 1];
			}
			arr[j] = temp;
		}

	}
	/**
	 * Insertion sort algorithm, adapted from above to sort array of char
	 * @param arr: array to be sorted
	 */
	public static void insertionSort(char[] arr) {
		
		for (int i = 1, j; i < arr.length; i++) {
			char temp = arr[i];
			for(j = i; j > 0 && temp < arr[j - 1]; j--) {
				arr[j] = arr[j - 1];
			}
			arr[j] = temp;
		}

	}
	/**
	 * Quick Sort Algorithm
	 * Adapted from: http://www.java67.com/2014/07/quicksort-algorithm-in-java-in-place-example.html
	 * @param arr: array to be sorted
	 */
	public static void quickSort (StringList[] arr) {
		quicksort(arr, 0, arr.length - 1);
		
	}
	
	public static void quicksort (StringList[] arr, int start, int end) {
				for (int i = 1, j; i < arr.length; i++) {
			String temp = arr[i].getFirst();
			StringList tmp = arr[i];
			for(j = i; j > 0 && temp.compareTo(arr[j - 1].getFirst()) < 0; j--) {
				arr[j] = arr[j - 1];
			}
			arr[j] = tmp;
		}
		int index = sort(arr, start, end);
		
		if (start < index - 1){
			quicksort(arr, start, index - 1);
		}
		if (end > index){
			quicksort(arr, index, end);
		}
	}
	
	public static int sort(StringList[] arr, int left, int right) {
		StringList pivot = arr[left];
		
		while (left <= right){
			while (arr[left].getFirst().compareTo(pivot.getFirst()) < 0) {
				left++;
			}
		
			while (arr[right].getFirst().compareTo(pivot.getFirst()) < 0) {
				right--;
			}
			if (left <= right) {
				StringList temp = arr[left];
				arr[left] = arr[right];
				arr[right] = temp;
				left++;
				right--;
			}
		}
		return left;
	}
	/**
	 * Algorithm for comparing to strings in order to determine of they are anagrams
	 * of each other.
	 * Adapted from: 
	 * @param s1: String 1 to be compared
	 * @param s2: String 2 to be compared
	 * @return True or False
	 */
	public static boolean isAnagram(String s1, String s2){
        if ( s1.length() != s2.length() ) {
            return false;
        }
        s1=s1.toLowerCase();
        s2=s2.toLowerCase();
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        insertionSort(c1);
        insertionSort(c2);   
        String sc1 = new String(c1);
        String sc2 = new String(c2);
        return sc1.equals(sc2);
	}
	/**
	 * Removes null pointers from array
	 * @param arr: Array to be analyzed
	 * @return: Array without null pointers
	 */
	public static StringList[] whiteSpace(StringList[] arr){
		int count = 0;
		while(arr[count].getFirst() != "null"){
			count++;
		}
		
		StringList[] sl = new StringList[count];
		
		for(int i = 0; i < count; i++){
			sl[i] = arr[i];
		}
		return sl;
	}
	/**
	 * Reads input from file and populates array
	 * @param inputfile: Input file name
	 * @return: Array of file input
	 */
	@SuppressWarnings("finally")
	public static String[] readFile(String inputfile){
		String[] elements = null;
		try{
		String line;
		//Reads the elements from file and populates a linked list containing the elements
		BufferedReader br = new BufferedReader(new FileReader(inputfile));
		StringList lines = new StringList();
		
		while((line = br.readLine()) != null) {
			lines.addLast(line);
		}
		br.close();
		elements = lines.getElements();
		
		return elements;
		
		}catch(FileNotFoundException ex){
			System.out.println("Error: Unable to open file, '" + inputfile + "' not found");
		}catch(IOException ex){
        System.out.println("Error occured while reading file '" + inputfile + "'");
		}finally{		
		return elements;
		}
	}
	/**
	 * Prints the final data structure to an output text file
	 * @param arr: Data structure to be printed
	 * @param output: output file name
	 * @throws IOException
	 */
	public static void printToFile(StringList[] arr, String output){
		try{
    	File fout = new File(output);
    	FileOutputStream fos = new FileOutputStream(fout);
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    	
    	for(int i = 0; i < arr.length; i++){
    		if(arr[i].getFirst() != "null"){
    			String [] temp = new String[arr[i].listLength()];
    			temp = arr[i].getElements();
    			for(int j = 0; j < arr[i].listLength(); j++){
    				bw.write(temp[j] + "  ");
    			}
    			bw.newLine();
    		}
     	}
       	bw.close();
		}catch(IOException ex){
			System.out.println("Error: Unable to write to file");
		}
	}
	/**
	 * Main Class
	 * @param args: inputfile name, output file name
	 * @throws IOException
	 */
	public static void main(String args[]){
		//Input argument checking 
		if(args.length != 2){
			System.out.println("Error: Please include input and output file names in the command line");
			return;
		}
		//Start of code for timing program execution
		long start_time;
		long end_time;
		start_time = System.nanoTime();
		
		String inputfile, outputfile;
		
		inputfile = args[0] + ".txt";
		outputfile = args[1] + ".txt";
		int length;
		
		String [] elements = readFile(inputfile);
		length = elements.length;
		StringList [] ref = new StringList[length];

		//Creates array of references
		for(int i = 0; i < length; i++){
			ref[i] = new StringList();
		}
		//Create first linked list and populate with first item of input file for comparison
		ref[0].addFirst(elements[0]);
		
		//Sorts the elements that are anagrams and puts them in them in the same linked list
		int i = 0;
		boolean anagram;
		while(i < length - 1){
			int j = 0;
			while(j < length){
				if(ref[j].getFirst() != "null"){
					anagram = isAnagram(ref[j].getFirst(), elements[i + 1]);
					if(anagram == true){
						ref[j].addFirst(elements[i + 1]);
						break;
					}else{
						j++;
					}					
				}
				if(ref[j].getFirst() == "null"){
					ref[j] = new StringList();
					ref[j].addFirst(elements[i + 1]);
					break;
				}
			}
			i++;
		}
		//Makes call to insertion sort in order to sort the anagrams in a particular linked list	
		for(int k = 0; k < ref.length; k++){
			String [] sort = ref[k].getElements();
			insertionSort(sort);
			ref[k].clear();
			for(int n = 0; n < sort.length; n++){
				ref[k].addFirst(sort[n]);
			}
		}
		//Create array without null pointers
		StringList[] array = whiteSpace(ref);
		
		//Sort array of references using quicksort		
		quickSort(array);

		//Print sorted array of linked lists to file
		printToFile(array, outputfile);
		//End of code for timing program execution
		end_time = System.nanoTime();
		long total = (end_time - start_time) / 1000000000;
		System.out.println("The program took " + total + "s to run");
	}
}
		
