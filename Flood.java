package codes;

import java.util.Scanner;

public class Flood {

	private static Node root;
	private int dimension = 14;
	static Scanner scan = new Scanner(System.in);


	private int rng() { // random number generator
		return (int) (Math.random() * (6 - 1) + 1); //returns random number between 6 and 1 
	}

	public Flood() {
		Node temp = new Node(rng()); // First Node
		root = temp; 
		Node Marker = root;
		for (int x = 0; x < dimension - 1; x++) { // First Row
			temp = new Node(rng()); // randomly fills in the node data 
			Marker.setRight(temp); // sets link right
			temp.setLeft(Marker);  // sets link left
			Marker = temp; 
		}
		// First node in second row
		Marker = root;
		Node rowMarker = root;
		for (int y = 0; y < dimension - 1; y++) {
			temp = new Node(rng());
			temp.setUp(rowMarker);
			rowMarker.setDown(temp);
			rowMarker = temp;
			Marker = rowMarker;

			for (int x = 0; x < dimension - 1; x++) { // every row after first
				temp = new Node(rng());
				Marker.setRight(temp);
				temp.setLeft(Marker);
				temp.setUp(temp.getLeft().getUp().getRight()); 
				temp.getLeft().getUp().getRight().setDown(temp);
				Marker = temp;
			}
		}
	}

	public void display() { //displays the linked grid
		Node temp = root;
		Node rowMarker = root;

		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				System.out.printf("%3d", temp.getData()); // %3d adds a set space in between each Node
				temp = temp.getRight();
			}
			System.out.println();
			temp = rowMarker.getDown();
			rowMarker = temp;
		}
		System.out.println();
	}
	
	
	public Node nodeAt(int x, int y) { // finds positions in the linked grid based on x and y coordinates 
		Node n = root;
		for (int i = 0; i < x; i++) { // goes right to find the x coordinate
			n = n.getRight();
		}
		for (int i = 0; i < y; i++) { // goes down to find the y coordinate
			n = n.getDown();
		}
		return n; // returns the node at x,y
	}
	
	private static boolean ifWin(){
		Node temp = root;
		Node rowmarker = temp;
		for(int i = 0; i < 14; i++){ // nested for loop to move through the entire board.
			for(int j = 0; j < 14; j++){
				if(temp.getData() != root.getData())
					return false; // returns false to break out of the method if it finds any number that isn't the same as the current root
				else
					temp = temp.getRight();
			}
			temp = rowmarker.getDown();
			rowmarker = temp;
		}
		return true; // if it goes through the entire board without finding a number that isn't the same as the rest returns true showing that the user flooded the board.
	}
	
	private void flood(Node pointer, int numchange) {
		int num = pointer.getData(); 
		pointer.setData(numchange); // changes the node.data so its the number hte user inputs

		if (pointer.getRight() != null && num == pointer.getRight().getData()) // checks that a node is to the right and that it is that same as the root node
			flood(pointer.getRight(),numchange); // recursion continues until there is either no node to the right or the number is not the same as the root
		
		if (pointer.getLeft() != null && num == pointer.getLeft().getData())// checks that a node is to the left and that it is that same as the root node
			flood(pointer.getLeft(),numchange);// recursion continues until there is either no node to the left or the number is not the same as the root
		
		if (pointer.getUp() != null && num == pointer.getUp().getData())// checks that there is a node above and that it is that same as the root node
			flood(pointer.getUp(),numchange);// recursion continues until there is either no node above or the number is not the same as the root
		
		if (pointer.getDown() != null && num == pointer.getDown().getData())// checks that there is a node below and that it is that same as the root node
			flood(pointer.getDown(),numchange); // recursion continues until there is either no node below or the number is not the same as the root
	}

	public static void main(String[] args) {
		Flood ll = new Flood();
		Node pointer;
		pointer = root;
		int floodNum;
		int turns = 55;
		
		while(turns >= 0){ // counts number of turns 
			if(ifWin() == true && turns >= 0){ // checks if the entire board is one number. If it it and the turns is greater than 0 ends teh program.
				System.out.println("You won!");
				break;
			}
			turns--;
			ll.display();
			System.out.println("Change number to flood");
			System.out.println("turns left: " + (turns+1));
			floodNum = scan.nextInt();
			ll.flood(pointer,floodNum); // begins the recursion program to flood the board
			
		}
		if(ifWin() == false) // if you run out of turns and all the nodes aren't one number
			System.out.println("Dang you lost! Better luck next time.");
	}
}