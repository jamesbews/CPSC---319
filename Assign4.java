import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
/**
 * A simple program that reads an adjacency matrix and a query from file. The adjacancy matrix defines whether or not 2
 * nodes are connected via an edge. The query file gives a source and destination node, the program then determines if
 * a path exists between the 2 nodes by doing a Breadth First and Depth First traversal. The result is the printed to
 * a output file.
 * @author James
 * @version 1.0
 * @since March 24, 2017 *
 */
public class Assign4 {
	/**
	 * Reads adjacency matrix from file and populates a 2D array
	 * @param filename
	 * @return
	 */
	@SuppressWarnings("finally")
	public static int[][] readFromFile(String filename) {
		int[][] matrix = {{1}, {2}}; // Initialize array
		
		File inFile = new File(filename);
		try{
		Scanner in = new Scanner(inFile);
		BufferedReader br = new BufferedReader(new FileReader(filename));
		
		int row = 0;
		while(br.readLine() != null) //Count rows
			row++;
		br.close();
		
		int col = 0;
		String[] length = in.nextLine().trim().split("\\s+"); //Count columns
		  for (int i = 0; i < length.length; i++) {
		    col++;
		  }

		in.close();

		matrix = new int[row][col]; //Initialize matrix
		in = new Scanner(inFile);

		int lineCount = 0;
		while (in.hasNextLine()) {
		  String[] currentLine = in.nextLine().trim().split("\\s+"); //Read data from file and populate 2D array
		     for (int i = 0; i < currentLine.length; i++) {			 // with integer values
		        matrix[lineCount][i] = Integer.parseInt(currentLine[i]);    
		            }
		  lineCount++;
		 }  
		in.close();
		 return matrix;
		}catch (IOException e){
			System.out.println(e.getMessage());
		}finally{
			return matrix; //Return array
		}
	}	
	
	/**
	 * Prints result of traversal to text file by appending the result of each traversal to the file
	 * @param filename
	 * @param src
	 * @param dst
	 * @param path
	 */
	public static void printStackToFile(String filename, int src, int dst, Stack found){
		
		int[] path = new int[found.size()]; //Convert stack to array
		for(int i = found.size() - 1; i >= 0; i--){
			path[i] = found.pop();
		}
		String dest = Integer.toString(dst);

		try{
			
			File fout = new File(filename); //Check if file exists
			if(!fout.exists()){
				fout.createNewFile();
			}
			FileWriter fw = new FileWriter(fout, true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(src + ", ");	//Print path 
			int i = 0;
			while(i < path.length){
				bw.write(path[i] + ", ");
				i++;
			}
			bw.write(dest);
			bw.newLine();
			bw.close();
			
		}catch (IOException e){
			System.err.println("Error writing to file" + e.getMessage());
		}
	}
	
	/**
	 * Prints result of breadth first traversal to file by appending each path to the existing file
	 * @param filename
	 * @param src
	 * @param dst
	 * @param found
	 */
	public static void printQueueToFile(String filename, int src, int dst, Queue<Integer> found){
		
		int[] path = new int[found.size()];
		for(int i = 0; i <= found.size() - 1; i++){
			path[i] = found.dequeue();
		}
		String dest = Integer.toString(dst);

		try{
			
			File fout = new File(filename); //Check if file exists
			if(!fout.exists()){
				fout.createNewFile();
			}
			FileWriter fw = new FileWriter(fout, true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(src + ", ");
			int i = 0;
			while(i < path.length){
				bw.write(path[i] + ", ");
				i++;
			}
			bw.write(dest);
			bw.newLine();
			bw.close();
			
		}catch (IOException e){
			System.err.println("Error writing to file" + e.getMessage());
		}
	}
	
	/**
	 * Determines if a path exists between 2 nodes using DEPTH FIRST traversal. Returns true or false
	 * @param matrix: Adjacancy Matrix
	 * @param src: Source node
	 * @param dst: Destination node
	 * @return The path in a stack
	 */
	public static Stack depthFirst(int[][] matrix, int src, int dst){
		int nodes = matrix.length; //Number of nodes
		Stack stack = new Stack();
		int [] isVisited = new int[nodes]; //Keeps track of if nodes are visited or not
		
		int current;
		
		stack.push(src); //Insert first node
		
		while(!stack.isEmpty()){
			current = stack.pop();
			if(current == dst){ 
				return stack; //If destination node is found
			}
			if(isVisited[current] == 0){
				isVisited[current] = 1; //Mark as visited
			}
			for(int i = 0; i < nodes; i++){
				if(matrix[current][i] == 1 && isVisited[i] == 0){ //Traverse to each node, checking if it has been visited
					stack.push(i); //Add node stack
					isVisited[i] = 1; //Mark as visited
					current = i;
					i = 0;
					
				}
			}
			
		}
		Stack fail = new Stack(); //If path is not found, return -1 in stack
		fail.push(-1);
		return fail;
	}
	/**
	 * Determines if there is a path between 2 nodes using BREADTH FIRST search
	 * @param matrix: Adjacency Matrix
	 * @param src: Source Node
	 * @param dst: Destination Node
	 * @return The path in a queue
	 */
	public static Queue<Integer> breadthFirst(int[][] matrix, int src, int dst){
		int nodes = matrix.length; //Number of nodes
		Queue<Integer> queue = new Queue<Integer>();
		Queue<Integer> result = new Queue<Integer>();
		int [] isVisited = new int[nodes]; //Keeps track of if nodes are visited or not
		int [] prev = new int[nodes]; //Records previous node visited from a visited node
		for (int i = 0; i < prev.length; i++)// Populate array with -1
			prev[i] = -1;
		
		int current;
		
		queue.enqueue(src); //Put root node in queue
		isVisited[src] = 1; //Mark as visited
		
		while(!queue.isEmpty()){
			current = queue.dequeue();
			if(current == dst){ //If destination node is found, find path my backtracking through previous array
				int i = current; 
				while(prev[i] != src){
					result.enqueue(prev[i]); //Put path in a queue
					i = prev[i];
				}
				return result; //Returns path 
			}
			for(int i = 0; i < nodes; i++){
				if(matrix[current][i] == 1 && isVisited[i] == 0){ //Traverse nodes, checking if each has been visited
					prev[i] = current; //Record path
					queue.enqueue(i); //Add to queue
					isVisited[i] = 1; //Mark as visited
					i = 0;
					
				}
			}
			
		}
		Queue<Integer> fail = new Queue<Integer>();
		fail.enqueue(-1);
		return fail; //If no path, return -1
	}
	/**
	 * Main Class
	 * @param args
	 */
	public static void main(String[] args){
		
		//Handles command line arguments
		String input, query, dfsOut, bfsOut;
		
		input = args[0] + ".txt";
		query = args[1] + ".txt";
		dfsOut = args[2] + ".txt";
		bfsOut = args[3] + ".txt";
		
	
			int [][] inputArray = readFromFile(input); //Puts adjacency matrix from file into a 2D array
			int [][] queryArray = readFromFile(query); //Puts query file into a 2D array
			
			
			Stack dfsResult;
			for(int i = 0; i < queryArray.length; i++){
				dfsResult = depthFirst(inputArray, queryArray[i][0], queryArray[i][1]); //For every query, do a depth first traversal
				printStackToFile(dfsOut, queryArray[i][0], queryArray[i][1], dfsResult); //Print path to file

			}
			
			Queue<Integer> bfsResult;
			for(int i = 0; i < queryArray.length; i++){
				bfsResult = breadthFirst(inputArray, queryArray[i][0], queryArray[i][1]); //For every query, do a breadth first traversal
				printQueueToFile(bfsOut, queryArray[i][0], queryArray[i][1], bfsResult);  //Print path to file

			}
	}

}
