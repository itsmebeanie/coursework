import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.Iterator;

/**
 * @author Kaitlin Gu This program simulates a 2-Pass Linker
 */
public class Linker {

	/**
	 * Uses a file to produce the first and second pass of the linker.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String file = args[0];
		firstPass(file);
		secondPass(file);
	}

	// symbol table holds symbol pairs : symbol and absolute address
	static HashMap symbolTable = new HashMap();
	// symbol definition holds symbol and what module it's defined
	static HashMap symbolDef = new HashMap();
	// module holds module number and base address of module
	static HashMap module = new HashMap();
	// module size holds module and its size
	static HashMap moduleSize = new HashMap();
	// machine size
	static int machineSize = 300;

	/* First pass relocates relative addresses */

	public static void firstPass(String fileName) throws FileNotFoundException {
		// read in file
		HashMap errorMessage = new HashMap();
		BufferedReader f = new BufferedReader(new FileReader(fileName));
		Scanner scannerInput = new Scanner(f);

		// track relocation constant and module number starting at 1
		int relocationConstant = 0;
		int moduleNumber = 1;
		// Definition List
		while (scannerInput.hasNext()) {

			module.put(moduleNumber, relocationConstant);

			// Definition List: symbol location
			int line = scannerInput.nextInt();
			for (int i = 0; i < line; i++) {
				String symbol = scannerInput.next();
				// find absolute address of symbols
				int relativeAddress = scannerInput.nextInt();

				if (symbolTable.get(symbol) != null) {
					// multiple definition error
					if (errorMessage.get(symbol) != null) {
						String preexistingerror = (String) errorMessage
								.get(symbol);
						errorMessage
								.put(symbol,
										preexistingerror
												+ ";"
												+ "Error: This variable is multiply defined; last value used.");
					} else {
						errorMessage
								.put(symbol,
										"Error: This variable is multiply defined; last value used.");

					}
				}
				// add to symbol definition and symbol table
				symbolDef.put(symbol, moduleNumber);
				symbolTable.put(symbol, relativeAddress + relocationConstant);

			}

			// Don't care about use list for first pass
			int use = scannerInput.nextInt();
			for (int i = 0; i < use; i++) {
				scannerInput.next();
				int s;
				do {
					s = scannerInput.nextInt();
				} while (s != -1);

			}

			// Don't care about program text for first pass
			int programText = scannerInput.nextInt();
			for (int i = 0; i < programText; i++) {
				scannerInput.next();
			}

			// gives the module size for each module
			moduleSize.put(moduleNumber, programText);
			// relocation constant
			relocationConstant += programText;

			moduleNumber++;

		}

		Set setOfKeys2 = symbolDef.keySet();

		Iterator iterator2 = setOfKeys2.iterator();

		// error handling if a symbol exceeds the size of a module
		while (iterator2.hasNext()) {
			String key = (String) iterator2.next();
			Integer value = (Integer) symbolDef.get(key);
			int currentRelocationConstant = (int) module.get(value);
			int currentmoduleSize = (int) moduleSize.get(value);
			int relativeAddress = (int) symbolTable.get(key);
			
			if (relativeAddress - currentRelocationConstant >= currentmoduleSize) {
				if (errorMessage.get(key) != null) {
					String preexistingerror = (String) errorMessage.get(key);
					errorMessage
							.put(key,
									preexistingerror
											+ ";"
											+ "Error: Definition exceeds module size; last word in module used.");
				} else {
					errorMessage
							.put(key,
									"Error: Definition exceeds module size; last word in module used.");
				}
				symbolTable.put(key, currentRelocationConstant);
			}
		}

		// print out symbol table
		System.out.println("Symbol Table");

		Set setOfKeys = symbolTable.keySet();
		Iterator iterator = setOfKeys.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			Integer value = (Integer) symbolTable.get(key);
			System.out.print(key + " = " + value + " ");
			if (errorMessage.get(key) != null) {
				System.out.println(errorMessage.get(key));
			}
			System.out.println();
		}

	}

	/**
	 * Second pass produces memory map : Relocating relative addresses and
	 * resolving external references
	 */

	// list to keep track of uses
	public static HashMap useList = new HashMap();

	public static void secondPass(String fileName) throws FileNotFoundException {
		BufferedReader file = new BufferedReader(new FileReader(fileName));
		int memoryLine = 0;
		Scanner scannerInput = new Scanner(file);
		HashMap memoryMap = new HashMap();

		// keep track of error messages needed to be printed at the end of
		// program
		ArrayList<String> errorMessages = new ArrayList<String>();
		// keeps track of symbols used
		ArrayList<String> symbolsUsed = new ArrayList<String>();

		// counter for module number
		int moduleNumber = 1;

		// print out header for memory map
		System.out.println("\nMemory Map");

		while (scannerInput.hasNext()) {
			int relocationConstant = (int) module.get(moduleNumber);
			// Definition List: symbol location
			// already defined
			int line = scannerInput.nextInt();
			for (int i = 0; i < line; i++) {
				scannerInput.next();
				scannerInput.nextInt();
			}
			String errorMessage = "";
			int index = 0;
			// Use List
			int use = scannerInput.nextInt();
			for (int i = 0; i < use; i++) {
				String var = scannerInput.next();
				int s;
				do {
					s = scannerInput.nextInt();
					if (s != -1) {
						symbolsUsed.add(var);
						if (useList.get(s + relocationConstant) != null) {
							index = i;
						}
						// if use is grater than module size
						if (s > (int) moduleSize.get(moduleNumber)) {
							int mod = moduleNumber - 1;
							errorMessages.add("Error: Use of " + var
									+ " in module " + mod
									+ " exceeds module size; use ignored. ");

						}
						useList.put(s + relocationConstant, var);
					}
				} while (s != -1);

			}

			// program text passed into program helper
			int programText = scannerInput.nextInt();
			for (int i = 0; i < programText; i++) {
				int programLine = scannerInput.nextInt();
				programLineHelper(programLine, relocationConstant, memoryLine,
						programText, index);
				memoryLine++;
			}
			moduleNumber++;

		}

		Set symbolKeys = symbolDef.keySet();

		// if symbol definition is not used
		Iterator iterator = symbolKeys.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			Integer value = (Integer) symbolDef.get(key);
			if (!symbolsUsed.contains(key)) {
				value = value - 1;
				errorMessages.add("Warning: " + key + " was defined in module "
						+ value + " but never used.");
			}
		}

		// print out remaining error messages
		for (int i = 0; i < errorMessages.size(); i++) {
			System.out.println(errorMessages.get(i));
		}

	}

	/**
	 * Prints out the memory map using the given parameters
	 * 
	 * @param programLine
	 * @param relocationConstant
	 * @param memoryLine
	 * @param moduleSize
	 * @param error
	 */

	public static void programLineHelper(int programLine,
			int relocationConstant, int memoryLine, int moduleSize, int error) {
		int type = programLine % 10;
		String errorMessage = "";
		programLine = programLine / 10;
		if (type == 1) { // immediate
			// do nothing
			System.out.println(memoryLine + ":" + programLine + " "
					+ errorMessage);

		}

		if (type == 2) { // absolute
			int temp = programLine % 1000;
			// address greater than machine size
			if (temp > machineSize) {
				errorMessage = "Error: Absolute address exceeds machine size; largest address used.";
				programLine = programLine / 1000;
				programLine *= 1000;
				programLine += machineSize - 1;
			}

			System.out.println(memoryLine + ":" + programLine + " "
					+ errorMessage);

		}

		if (type == 3) { // relative
			int relativeAddress = programLine % 1000;

			// relative address is greater than module size
			if (relativeAddress > moduleSize) {
				programLine = programLine / 1000;
				programLine *= 1000;
				programLine += relocationConstant;

				errorMessage = "Error: Relative address exceeds module size; largest module address used.";
				System.out.println(memoryLine + ":" + programLine + " "
						+ errorMessage);

			} else {
				programLine += relocationConstant;
				System.out.println(memoryLine + ":" + programLine + " "
						+ errorMessage);
			}

		}
		if (type == 4) { // external
			int memoryAddress = 0;
			// external, look it up in the symbol table
			Object symbol = useList.get(memoryLine);
			// System.out.println("symbol is " + symbol);
			if (symbolTable.get(symbol) != null) {
				memoryAddress = (int) symbolTable.get(symbol);

			}
			// if there are multiple variables used
			if (error > 0 && error == memoryLine) {
				errorMessage = "Error: Multiple variables used in instruction; all but last ignored.";

			}

			else if (symbolTable.get(symbol) == null) {
				errorMessage = "Error: " + symbol + " is not defined; 111 used";
				memoryAddress = 111;
			}
			programLine = programLine / 1000;
			programLine *= 1000;
			programLine += memoryAddress;

			System.out.println(memoryLine + ":" + programLine + " "
					+ errorMessage);

		}

	}
}
