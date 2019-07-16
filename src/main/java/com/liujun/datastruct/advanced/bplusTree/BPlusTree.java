package com.liujun.datastruct.advanced.bplusTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

class BPlusTree {

    private static Node tree;
    private static int degree;
    private static boolean debug;
    
    private BPlusTree(int x) {
      // a B+ Tree must have an initial degree
    	degree = x;
    	
    	// The initial type of MyNode for a B+Tree is a leaf
    	tree = new LeafNode(degree);
    	
    	debug = false;
    }
    
    private static void executeCommand(Command c, BufferedWriter output) throws InvalidCommandException, IOException {
    	// execute command, does as it says, calls the appropriate procedure to accomplish the command
    	// There are also some debug options to help the user see what's going on
        switch( (int) c.getCommand() ) {
        case 'd':
        	if( c.getXValue() == 1 && !debug) {
        		debug = true;
                System.out.println("ENTERING DEBUG MODE");
        	}
        	else if ( c.getXValue() == 0 && debug) {
        		debug = false;
                System.out.println("EXITTING DEBUG MODE");
        	}
        	else if (c.getXValue() != 0 || c.getXValue() != 1){
        		throw new InvalidCommandException("Invalid Operand with command d. Must be 0 or 1.");
        	}
        case 'p':
        	if(debug) {
                System.out.println("PRINTING TREE");
        	}
        	printTree(output);
            break;
        case 's':
        	if(debug) {
                System.out.println("SEARCHING TREE FOR x = " + c.getXValue());
        	}
        	searchTree(c.getXValue(), output);
        	break;
        case 'i':
        	if(debug) {
                System.out.println("INSERTING x = " + c.getXValue() + " INTO THE TREE");
        	}
        	insertIntoTree(new DataNode(c.getXValue()));
            break;
        }
        if(debug && (int)c.getCommand() != 'p') {
        	printTree(new BufferedWriter(new PrintWriter(System.out)));
        	System.out.println("--->OPERATION COMPLETE");
        }
    }
    
    private static void insertIntoTree(DataNode dnode) {
    	tree = tree.insert(dnode);
	}

	private static void searchTree(int x, BufferedWriter output) throws IOException {
		
		// search the tree starting from the top
        if( tree.search(new DataNode(x)) ) {
            output.write("FOUND" + System.getProperty("line.separator"));    
        }
        else {
            output.write("NOT FOUND" + System.getProperty("line.separator"));
        }
	}

	@SuppressWarnings("unchecked")
    private static void printTree(BufferedWriter output) throws IOException {
        // create a vector to store all the nodes from each level as we 
        Vector<Node> nodeList = new Vector();
        
        // put the root of the tree onto the stack to start the process
        nodeList.add(tree);

        boolean done = false;
        while(! done) {
        	// this vector will hold all the children of the nodes in the current level
            Vector<Node> nextLevelList = new Vector();
            String toprint = "";
            
            // for each node in the list convert it to a string and add any children to the nextlevel stack
            for(int i=0; i < nodeList.size(); i++) {
            	
            	// get the node at position i
                Node node = (Node)nodeList.elementAt(i);
                
                // convert the node into a string
                toprint += node.toString() + " ";
                
                // if this is a leaf node we need only print the contents
                if(node.isLeafNode()) {
                    done = true;
                }
                // if this is a tree node print the contents and populate
                // the temp vector with nodes that node i points to
                else
                {
                    for(int j=0; j < node.size()+1 ; j++) {
                        nextLevelList.add( ((TreeNode)node).getPointerAt(j) );
                    }
                }
            }
            
            // print the level
            output.write(toprint + System.getProperty("line.separator"));
            
            // go to the next level and print it
            nodeList = nextLevelList;
        }
	}

	private static void readDegree(BufferedReader in) {
        // get the tree's degree from input
		try {
        	int x = Integer.parseInt(in.readLine().trim());
        	
        	// set the degree of the tree
        	new BPlusTree(x);
        	
        } catch (Exception e1) {
            System.err.println("degree could not be read... defaulting to order 3");
            new BPlusTree(3);
        }
    }
    
    public static void main(String[] args) throws IOException {
        // if there are too many arguments error
        if(args.length > 1) {
            System.err.println("Syntax error in call sequence, use:\n\tjava BplusTree");
        }
        else {
            
        	// declare a reader on Standard in, incase the file reader fails
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            
            // create a new file to store output
            BufferedWriter output = new BufferedWriter( new FileWriter(new File("bplustree.out")) );            
            try {
                in = new BufferedReader(new InputStreamReader(new FileInputStream("bplustree.inp")));
            } catch (FileNotFoundException e) {
                System.err.println("Error: specified file not found (defaulting to standard input)");
            }
            
            // get the degree of the B+Tree
            readDegree(in);
        	
            // declare a new command object
            Command c = new Command(); 
            
            // continue executing commands until quit is reached
            while(c.getCommand() != 'q') {
                try {
                	// read a command from input
                    c.readCommand(in);
                    
					// execute the command
                    executeCommand(c, output);
                } 
                catch (IOException e) {
                    e.printStackTrace();
                }
                catch (InvalidCommandException e) {
                    System.err.println(e.getMessage());
                    System.out.println("Valid Query-Modes:\n\ti x - insert x into tree\n\ts x - find x in tree\n\tp   - print tree\n\tq   - quit");
                }
                catch (NumberFormatException e) {
                	System.err.println("This type of command requires a integer operand");
                    System.out.println("Valid Query-Modes:\n\ti x - insert x into tree\n\ts x - find x in tree\n\tp   - print tree\n\tq   - quit");
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
            
            // close input and output
            output.close();
            in.close();
            
            // output.write("Exitting");
            System.exit(0);
        }
    }
}

class Command {
	int xvalue;
	char command;
	
	Command() {
		command = 0;
		xvalue = 0;
	}
	
	public String toString() {
		return "command = " + command + " x = " + xvalue;
	}

	public void readCommand(BufferedReader in) throws InvalidCommandException, IOException, NumberFormatException {
        
		boolean readcommand = false;
		
		// until a command has been read (valid or invalid) get something from input
		while(!readcommand && in.ready()) {
			command = (char)in.read();

			// if the line is a comment output the rest of the line
        	if(command == '#') {
        		// print the comment to the screen
                System.out.println( in.readLine() );
        	}
        	// if a valid command was read get any necessary arguments
        	else if(this.validCommand()) {
        		if(this.commandWithArgument()) {
        			xvalue = Integer.parseInt(in.readLine().trim());
        		}
        		else {
        			xvalue = 0;
        			in.readLine();
        		}
        		readcommand = true;
        	}
        	else {
        		// clean up the rest of the garbage on the input line
        		in.readLine();
        		throw new InvalidCommandException(command);
        	}
		}
	}
	
	public char getCommand() {
		return command;
	}
	
	public int getXValue() {
		return xvalue;
	}
	
	private boolean validCommand() {
		return commandWithArgument() || commandWithoutArgument();
	}
	// list of commands that have an argument
	private boolean commandWithArgument() {
		return this.command == 'i' || this.command == 's' || this.command == 'd';
	}
	// list of commands that don't have arguments
	private boolean commandWithoutArgument() {
		return this.command == 'p' || this.command == 'q';
	}
}

// Class to handle occurances of invalid commands
class InvalidCommandException extends Exception {
	private static final long serialVersionUID = -2169157330841961180L;

	InvalidCommandException(char command) {
        super("Error: invalid query-mode \"" + command + "\" entered");
    }
	InvalidCommandException(String s) {
		super(s);
	}
}

// The node class is an abstract class
// its subclasses are LeafNode and TreeNode

abstract class Node {
	// all nodes need data, parent, and a capacity
	protected Vector<DataNode> data;
	protected Node parent;
	protected int maxsize;

	public boolean isLeafNode() {
		// determine if a node is a leafnode
	    return this.getClass().getName().trim().equals("LeafNode");
	}

	// both types of node need to insert and search
	abstract Node insert(DataNode dnode);
	abstract boolean search(DataNode x);

	protected boolean isFull() {
		return data.size() == maxsize-1;
	}
	
	public DataNode getDataAt(int index) {
		return (DataNode) data.elementAt(index);
	}
	
	protected void propagate(DataNode dnode, Node right) {
		// propogate takes a piece of data and two pointers left(this) and right and pushes the data up the tree

		// if there was no parent
		if(parent == null) {
			
			// create a new parent
			TreeNode newparent = new TreeNode(maxsize);
			
			// add the necessary data and pointers
			newparent.data.add(dnode);
			newparent.pointer.add(this);
			newparent.pointer.add(right);
			
			// update the parent information for right and left
			this.setParent(newparent);
			right.setParent(newparent);
		}
		else {
			// if the parent is not full
			if( ! parent.isFull() ) {
				// add the necessary data and pointers to existing parent
				boolean dnodeinserted = false;
				for(int i = 0; !dnodeinserted && i < parent.data.size(); i++) {
					if( ((DataNode)parent.data.elementAt(i)).inOrder(dnode) ) {
						parent.data.add(i,dnode);
						((TreeNode)parent).pointer.add(i+1, right);
						dnodeinserted = true;
					}
				}
				if(!dnodeinserted) {
					parent.data.add(dnode);
					((TreeNode)parent).pointer.add(right);
				}
				
				// set the necessary parent on the right node, left.parent is already set
				right.setParent(this.parent);
			}
			// the parent is full
			else {
                // split will take car of setting the parent of both nodes, because 
				// there are 3 different ways the parents need to be set
                ((TreeNode)parent).split(dnode, this, right);

			}
		}
	}
	
	public int size() {
		return data.size();
	}

	@SuppressWarnings("unchecked") Node(int degree) {
		// initially the parent node is null
	    parent = null;
	    
	    data = new Vector();
	    maxsize = degree;
	}
	
	// Convert a node to a string
	public String toString() {
		String s = "";
		for(int i=0; i < data.size(); i++) {
			s += ((DataNode)data.elementAt(i)).toString() + " ";
		}
		return s + "#";
	}

	// this operation traverses the tree using the parent nodes until the parent is null and returns the node
	protected Node findRoot() {
		Node node = this;
		
		while(node.parent != null) {
			node = node.parent;
		}
		
		return node;
	}

	protected void setParent(Node newparent) {
		this.parent = newparent;
	}
} 

class LeafNode extends Node {
	private LeafNode nextNode;
	
	LeafNode(int degree) {
		super(degree);
		
		// initially the nextnode is null
		nextNode = null;
	}
	
	private void setNextNode(LeafNode next) {
		nextNode = next;
	}
	
	protected LeafNode getNextNode() {
		return nextNode;
	}

	public boolean search(DataNode x) {
		// search through the data sequentially until x is found, or there are no more entries
		for(int i=0; i < data.size(); i++) {
			if( ((DataNode)data.elementAt(i)).getData() == x.getData() ) {
				return true;
			}
		}
		return false;
	}

	protected void split(DataNode dnode) {
		// insert dnode into the vector (it will now be overpacked)
		boolean dnodeinserted = false;
		for(int i=0; !dnodeinserted && i < data.size(); i++) {
			if( ((DataNode)data.elementAt(i)).inOrder(dnode) ) {
				data.add(i,dnode);
				dnodeinserted = true;
			}
		}
		if(!dnodeinserted) {
			data.add(data.size(), dnode);
		}
		
		// calculate the split point; ceiling(maxsize/2)
		int splitlocation;
		if(maxsize%2 == 0) {
			splitlocation = maxsize/2;
		}
		else {
			splitlocation = (maxsize+1)/2;
		}
				
		// create new LeafNode
		LeafNode right = new LeafNode(maxsize);
		
		for(int i = data.size()-splitlocation; i > 0; i--) {
			right.data.add(data.remove(splitlocation));
		}
		
		// link the two together
		right.setNextNode(this.getNextNode());
		this.setNextNode(right);
		
		// get the middle item's data
		DataNode mid =  (DataNode) data.elementAt(data.size()-1);

		// propagate the data and pointers into the parent node
		this.propagate(mid, right);
	}

	public Node insert(DataNode dnode) {
		// if the leaf isn't full insert it at the proper place
		if(data.size() < maxsize-1) {
			boolean dnodeinserted = false;
			int i = 0;
			while(!dnodeinserted && i < data.size()) {
				if( ((DataNode)data.elementAt(i)).inOrder(dnode) ) {
					data.add(i,dnode);
					dnodeinserted = true;
				}
				i++;
			}
			if(!dnodeinserted) {
				data.add(data.size(), dnode);
			}
		}
		
		// if the leaf is full split
		else {
			this.split(dnode);
		}
		
		// return the root of the tree
		return this.findRoot();
	}
}

class TreeNode extends Node {
	protected Vector<Node> pointer;
	
	// constructor for TreeNode
	// x-1 is the maximum # of DataNodes a single node can store 
	@SuppressWarnings("unchecked") TreeNode(int x) {
		super(x);
		pointer = new Vector();
	}

	// this will find the correct pointer to the next lowest level of the tree where x should be found
	public Node getPointerTo(DataNode x) {
		// find the index i where x would be located
		int i = 0;
		boolean xptrfound = false;
		while(!xptrfound && i < data.size()) {
			if( ((DataNode)data.elementAt(i)).inOrder(x ) ) {
				xptrfound = true;
			}
			else {
				i++;				
			}

		}
		
		
		// return the MyNode in pointer(i)
		return (Node) pointer.elementAt(i);
		
	}

	// returns the pointer at a specific index in the pointer stack
	public Node getPointerAt(int index) {
		return (Node) pointer.elementAt(index);
	}

	boolean search(DataNode dnode) {
		// get a pointer to where dnode.data should be found
		Node next = this.getPointerTo(dnode);
	
		// recursive call to find dnode.data if it is present
		return next.search(dnode);
	}

	protected void split(DataNode dnode, Node left, Node right) {
		// calculate the split point ( floor(maxsize/2)
		int splitlocation, insertlocation = 0; 
		if(maxsize%2 == 0) {
			splitlocation = maxsize/2;
		}
		else {
			splitlocation = (maxsize+1)/2 -1;
		}
		
		// insert dnode into the vector (it will now be overpacked)
		boolean dnodeinserted = false;
		for(int i=0; !dnodeinserted && i < data.size(); i++) {
			if( ((DataNode)data.elementAt(i)).inOrder(dnode) ) {
				data.add(i,dnode);
				((TreeNode)this).pointer.remove(i);
				((TreeNode)this).pointer.add(i, left);
				((TreeNode)this).pointer.add(i+1, right);
				dnodeinserted = true;
                
                // set the location of the insert this will be used to set the parent
				insertlocation = i;
			}
		}
		if(!dnodeinserted) {
            // set the location of the insert this will be used to set the parent
            insertlocation = data.size();
			data.add(dnode);
			((TreeNode)this).pointer.remove(((TreeNode)this).pointer.size()-1);
			((TreeNode)this).pointer.add(left);
			((TreeNode)this).pointer.add(right);
            
		}
		
		// get the middle dataNode
		DataNode mid = (DataNode) data.remove(splitlocation);
		
		// create a new tree node to accomodate the split 
		TreeNode newright = new TreeNode(maxsize);
		
		// populate the data and pointers of the new right node
		for(int i=data.size()-splitlocation; i > 0; i--) {
			newright.data.add(this.data.remove(splitlocation));
			newright.pointer.add(this.pointer.remove(splitlocation+1));
		}
		newright.pointer.add(this.pointer.remove(splitlocation+1));		

        // set the parents of right and left
		// if the item was inserted before the split point both nodes are children of left
        if(insertlocation < splitlocation) {
            left.setParent(this);
            right.setParent(this);
        }
        // if the item was inserted at the splitpoint the nodes have different parents this and right
        else if(insertlocation == splitlocation) {
            left.setParent(this);
            right.setParent(newright);
        }
        // if the item was was inserted past the splitpoint the nodes are children of right
        else {
            left.setParent(newright);
            right.setParent(newright);
        }
        
		// propogate the node up
		this.propagate(mid, newright);
	}

	Node insert(DataNode dnode) {
		Node next = this.getPointerTo(dnode);
		
		return next.insert(dnode);
	}
}

class DataNode {
    // I chose Integer because it allows a null value, unlike int
    private Integer data;
    
    DataNode() {
        data = null;
    }   
    public String toString() {
		return data.toString();
	}
	public DataNode(int x) {
        data = x;
    }
    public int getData() {
        return data.intValue();
    }   
    public boolean inOrder(DataNode dnode) {
        return (dnode.getData() <= this.data.intValue());
    }
}
