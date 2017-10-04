import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
/**
 * 
 * @author James Bews
 * @version 1.0
 * @since April 8, 2017
 *
 */
public class Assign5 {
	/**
	 * Hash Table
	 */
	private static HashTable table;
	/**
	 * Takes input file of strings and inputs them into a hash table
	 * @param fileName: Name of file to read inputs from
	 */
	public static void readFileInput(String fileName){
		int count = 0;
		try{
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String current = br.readLine(); //Read current line in file
			
			while(true){
				if(current == null){
					break;
				}
				
				table.inputEntry(current); //Input each entry
				
				current = br.readLine(); //Read next line
				count++;
			}
			
			br.close();
			
			table.setRecordCount(count);
			
		}catch (IOException e){
			System.err.println("Problem reading from file");
			System.err.println(e.getMessage());
			System.err.println("Terminating program");
			System.exit(1);
			
		}
	}
	public static void searchTable(String inFileName, String outFileName){
		DecimalFormat format = new DecimalFormat("#0.00");
		DecimalFormat formatter = new DecimalFormat("#00.00");
		
		String loadFactor;
		String avgReads;
		String longestChain;
		String hashEff;
		
		try{
			FileReader fr = new FileReader(inFileName);
			BufferedReader br = new BufferedReader(fr);
			String current = br.readLine();
			
			File fout = new File(outFileName);
			if(!fout.exists()){
				fout.createNewFile();
			}
			FileWriter fw = new FileWriter(fout, true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write("=====Results of Search=====");
			bw.newLine();
			bw.newLine();
			
			loadFactor = "Load Factor: " + formatter.format(table.getLoadFactor()) + "%";
			
			bw.write(loadFactor);
			bw.newLine();
			
			while(true){
				
				if(current == null){
					break;
				}
				
			table.getEntry(current); //Search for entry
			
			current = br.readLine(); //read next line
			
			}
			
			longestChain = "Longest Chain: " + table.getLongestChain();
			bw.write(longestChain);
			bw.newLine();
			
			avgReads = "Average Reads: " + format.format(table.getAverageReads());
			bw.write(avgReads);
			bw.newLine();
			
			hashEff = "Hash Efficiency: " + formatter.format(table.getHashEfficiency() / 100.0 )+ "%";
			bw.write(hashEff);
			
			
			bw.close();
			br.close();

		}catch (IOException e){
			System.err.println(e.getMessage());
		}
	}
	/**
	 * Main class
	 * @param args: Input file name and output file name
	 */
	public static void main(String[] args){
		
		String input, output;
		input = args[0] + ".txt";
		output = args[1] + ".txt";
		
		table = new HashTable(); //initialize empty	hash table, values set to null
		 
		readFileInput(input); //send input file to method that will insert into hash table
		
		searchTable(input, output); // Use input has search key, print stats to file
		
	}
	
}