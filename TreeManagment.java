import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TreeManagment {

	public static Scanner scan = new Scanner(System.in);
	public static ThreadedBinaryTree tbst = new ThreadedBinaryTree();

	public static void main(String[] args) {

		
		boolean flag = true;

		do
		{
			menu();
			int choice = scan.nextInt();
			scan.nextLine();
			
			switch(choice)
			{
			case 1 :
				insert();
		    	System.out.print("\n\n\n\n");

				break;
			case 2 : 
				delete();
		    	System.out.print("\n\n\n\n");

				break;
				
			case 3 :
				search();
		    	System.out.print("\n\n\n\n");

				break;
				
			case 4 : 
				System.out.println("Please enter key for find his successor");
				System.out.println(tbst.findSuccessor(tbst.search(scan.nextInt())));
		    	System.out.print("\n\n\n\n");

				break;
				
			case 5:
				System.out.println("Please enter key for find his predecessor");
				System.out.println(tbst.findPredecessor(tbst.search(scan.nextInt())));
		    	System.out.print("\n\n\n\n");

				break;
				   
			case 6:
				System.out.println(tbst.getMax());
		    	System.out.print("\n\n\n\n");

				break;
			
			case 7:
				System.out.println(tbst.getMin());
		    	System.out.print("\n\n\n\n");
		    	
		    	break;

				
			case 8:
				System.out.println(tbst.getMedian());
		    	System.out.print("\n\n\n\n");

				break;
						
			case 9:
				tbst.inOrderTreeWalk();
		    	System.out.print("\n\n\n\n");

				break;
			
			case 10:
				tbst.preOrderTreeWalk();
		    	System.out.print("\n\n\n\n");

				break;
				
			case 11:
				tbst.postOrederTreeWalk();
		    	System.out.print("\n\n\n\n");

				break;
			case 12:
				readFromFile();
				
				break;
				
			case 13 :
				flag = false;
				
				break;
				
				
				
		    default : 
		    	System.out.println("wrong input,try again");
		    	System.out.print("\n\n\n\n");
		    	
		    	break;
	
			}
			
			
			
		} while(flag);
	
	}

	// case 1.
	private static void insert() {
		int key;
		String value;
		System.out.println("Plese enter you name");
		value = scan.nextLine();
		System.out.println("Please enter your id");
		key = scan.nextInt();
		ThreadedNode node = new ThreadedNode(key, value);
		tbst.insert(node);
		System.out.println("The node inserted.");
	}

	// case 2.
	private static void delete() {
		System.out.println("Please enter key for delete");
		ThreadedNode nodeForDelete = tbst.delete(tbst.search(scan.nextInt()));
		if (nodeForDelete == null) {
			System.out.println("The node does not exist.");
		} else {
			System.out.println("The node deleted.");
		}
	}

	// case 3
	private static void search() {
		System.out.println("Please enter key for search");
		ThreadedNode nodeForSearch = tbst.search(scan.nextInt());
		if (nodeForSearch == null) {
			System.out.println("The node does not exist");
		} else if (nodeForSearch != null) {
			System.out.println("node found : " + nodeForSearch);
		}
	}
	// case 13

	private static void readFromFile() {
		System.out.println("File format should be : \n (int) (string)");
		System.out.println("Please enter the path");
		String path = scan.nextLine();

		try {
			Scanner fileScanner = new Scanner(new FileInputStream(path));

			while (fileScanner.hasNext()) {
				ThreadedNode nodeFromFile = new ThreadedNode(fileScanner.nextInt(), fileScanner.nextLine());
				tbst.insert(nodeFromFile);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist");
		}
		System.out.println("File loaded into tree");

	}

	private static void menu() {

		System.out.println("Welcome,please enter your choice.");
		System.out.println("1 - insert.");
		System.out.println("2 - delete.");
		System.out.println("3 - search.");
		System.out.println("4 - get successor.");
		System.out.println("5 - get precedessor.");
		System.out.println("6 - get max");
		System.out.println("7 - get min");
		System.out.println("8 - get median");
		System.out.println("9 - inorder tree walk.");
		System.out.println("10 - pre order tree walk.");
		System.out.println("11 - post order tree walk.");
		System.out.println("12 - read the node from file");
		System.out.println("13 - exit.");

	}

}
