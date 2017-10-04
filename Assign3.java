import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 * A simple program that populates a Binary Search Tree based on the 'Insert' or 'Delete' key specified by the record. The 
 * Binary Search tree is then printed to file using Depth-First inorder traversal and Breadth-First traversal. The input and 
 * ouput file names are specified by the user in the command line arguments.
 * @author James Bews
 * @version 1.0
 * @since March 12, 2017
 *
 */
public class Assign3 {
	/**
	 * Reads input file and populates array of strings with each line read
	 * @param inputfile
	 * @return elements: 
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
	 * Main class
	 * @param args
	 */
	public static void main(String[] args){
		if(args.length != 3){
			System.err.println("Error: Please specify an input file name and two output file names");
		}
		//Store commandline arguments
		String input, depth, breadth;
		input = args[0] + ".txt";
		depth = args[1] + ".txt";
		breadth = args[2] + ".txt";
		//Populate array with input data strings		
		String [] elements = readFile(input);
		//Create Binary Search Tree and add elemenst according to 'I'(insert) or 'D'(delete)
		BinaryTree tree = new BinaryTree();
		for(int i = 0; i < elements.length; i++){
			char[] test = elements[i].toCharArray();
			if(test[0] == 'I'){
				tree.insert(elements[i]);
			}
			if(test[0] == 'D'){
				tree.delete(elements[i]);
			}
		}
		//Print out tree to file using depth first and breadth first traversal
		tree.setFileName(depth, breadth);
		tree.depthFirst(tree.root);
		tree.breadthFirst(tree.root);
		

	}
}