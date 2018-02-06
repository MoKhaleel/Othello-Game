package MyPack;

public class PrintBoard extends OthelloAlphaBeta{
	public void Print()	{
		for(int i=0; i < dimension ; i++)		{
    		for(int j=0 ; j < dimension ; j++){
    			if(board[i][j] == 'W')
    				System.out.print("_W_");
    			else if(board[i][j] == 'B')
    				System.out.print("_B_");
    			else
    				System.out.print("___");}
    		System.out.println(" ");}}	
	public void printBoard()	{
    	System.out.print("   ");    	
    	for(int i=1; i < board.length-1 ; i++)
    		System.out.print(" " + i + " ");    
    	System.out.println();
    	for(int i=1; i < board.length-1 ; i++){
    		System.out.print(" " + i + " ");
    		for(int j=1 ; j < board.length-1 ; j++)
    			if(board[i][j] == 'W')
    				System.out.print(" W ");
    			else if(board[i][j] == 'B')
    				System.out.print(" B ");
    			else
    				System.out.print(" . ");    		
    		System.out.println(" "+ i + " ");}
    	System.out.print("   ");    	
    	for(int i=1 ; i < board.length-1 ; i++)
    	    System.out.print(" " + i + " ");
    	System.out.println("\n");
    	System.out.println("The total number of diks on the board : " + filled);
    	Util utilObject = new Util();
    	utilObject.countStones();
    }
}
