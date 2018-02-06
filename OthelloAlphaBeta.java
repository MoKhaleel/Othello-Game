package MyPack;

import java.util.ArrayList;
import javax.swing.JOptionPane;

class OthelloAlphaBeta 
{
    static int filled; // number of tokens on the board
	static boolean  input; // temp variable
	static int dimension = 10; // the diminsion of the board, the game will have 8*8 plus one row and column from each side for demonstration
	static char[][] board = new char[dimension][dimension]; // the dimension of the playing board, we are going to use only 8*8.
	static int[] moves = new int[64]; // the move array contains the valid 64 place that represents the valid places on the board array.
	static boolean player = true; // if it is true then the player is human, else it is an agent.
	static int Depth = 12; // the depth will be used by the agent to determine how deep the alphabeta will go into the tree, the deeper is the better agent. and since the search scope is small, we can use large values.
	static int ub, lb, x, y; // temp variables to hold some values like the dimensions x and y of the new add disk or the upper and lower beta values.
	static int max = Integer.MIN_VALUE;
	static int bestMove; // the best move possible by the agent.
	// This matrix contains the weights of all the moves on the board. I gave the weights on the corners very high value as it's very important
	// the most important position in the game beside the borders. some positions on the board is not good to be chosen, therefore, I assigned very
	// low weight to it.
	static int valueField[] =	{	0,   	0,		0,		0,		0,		0,		0,		0,		0,		0,
									0,		1000,	5,		500,	200,	200,	500,	5,		1000,	0,
									0,		5,		1,		50,		150,	150,	50,		1,		5,		0,
									0,		500,	50,		250,	100,	100,	250,	50,		500,	0,
									0,		200,	150,	100,	50,		50,		100,	150,	200,	0,
									0,		200,	150,	100,	50,		50,		100,	150,	200,	0,
									0,		500,	50,		250,	100,	100,	250,	50,		500,	0,
									0,		5,		1,		50,		150,	150,	50,		1,		5,		0,
									0,		1000,	5,		500,	200,	200,	500,	5,		1000,	0,
									0,   	0,   	0,		0,		0,		0,		0,		0,		0,		0};
	// The location function will determine the move in one dimensional array as follows:
	//   1	:	place the disk on the right side of the current disk
	//  -1	:	place the disk on the left side of the current disk
	//   9	:	place the disk on the upper right side of the current disk
	//  -9	:	place the disk on the lower right side of the current disk
	//  10	:	place the disk on the upper side of the current disk
	// -10	:	place the disk on the lower side of the current disk
	//  11	:	place the disk on the upper left side of the current disk
	// -11	:	place the disk on the lower left side of the current disk
	static int[] locations = {1, -1, 9, -9, 10, -10, 11, -11};	
	public static void main(String args[]){
		// Initialize the playing board.
		InitializeBoard Board = new InitializeBoard();
		Board.initializeBoard();
		// Initializing the Move list
		Move m = new Move();
		m.initializeMoves();		
		// The program will print the board on the screen with the two black and two white disks in the middle.
		JOptionPane.showMessageDialog(null, "Game Started\nPlease notice that all the value you should enter for this game should be Integer");
		PrintBoard Output = new PrintBoard();
		Output.printBoard();
		int[] legalMoves = getLegalMoves(board, player);
		if(legalMoves.length>0)
			printLegalMoves(legalMoves, player);		
		NextMove();
	}	
	@SuppressWarnings("resource")
	private static void NextMove(){
		// Asking the user to choose x and y value for the new disk need to be added to the board and add it to the board.
		// When entering new value, the program will check if this value is already taken by another disk or not.
		// Also the program going to check if the value in the range of the board or not.
		input = false;
		while (input==false){
			Util utilObject = new Util();
			if (utilObject.gameOver()){
				utilObject.FinalScore();				
				return;}		
			x = Integer.parseInt(JOptionPane.showInputDialog("Now is your turn to add new disk to the playing board\nPlease enter the x dimension of the disk you want to add from 1 to " + (dimension-2)));		
			y = Integer.parseInt(JOptionPane.showInputDialog("Please enter the y dimension of the disk you want to add from 1 to " + (dimension-2)));		
			if (x<1 || x>dimension-2 || y<1 || y>dimension-2)
				JOptionPane.showMessageDialog(null, "The disk you want to add is out of dimension of the board, please choose another place");
			else
				input=true;}
		// Call the function LetsPlayWithUser for the values x and y. If the function will be halted due to wrong input the catch will call the startingInput function again.
		try {
			userTurn(player, x*10 + y%10);}
		catch(Exception ex)	{
			JOptionPane.showMessageDialog(null,"Invalid input");
			NextMove();}}
	private static void userTurn(boolean player, int move) {
		Util utilObject = new Util();
		if (utilObject.noAvailableMove(player))	{
			if (utilObject.noAvailableMove(!player)) {
				JOptionPane.showMessageDialog(null,"There is No leagal move for both players");
				return;
			} else computerTurn(!player);}
		int[] legalMoves = getLegalMoves(board, player);
		boolean validMove = false;		
		for(int i=0; i<legalMoves.length; i++)
			if(legalMoves[i] == move)  {
				move = legalMoves[i]; 
				validMove = true;
				break;}		
		if(validMove) {
			board = makeMove(move, board, player);
	        filled++;	
			System.out.println("Player " + getPlayer(player) + " Move : ("+(move/10)+","+(move%10)+")");
			PrintBoard Output = new PrintBoard();
			Output.printBoard();			
			computerTurn(!player);} 
		else {
			System.out.println("There is not possible move by the Human");
			NextMove();}}	
	private static void computerTurn(boolean player){
		Util utilObject = new Util();
		if (utilObject.noAvailableMove(player)){
			if (utilObject.noAvailableMove(!player))
				return;
			else NextMove();}
		int[] legalMoves = getLegalMoves(board, player);		
		if(legalMoves.length > 0) {
			printLegalMoves(legalMoves,player);
			int bestMove = agent(board, player, Integer.MIN_VALUE, Integer.MAX_VALUE, Depth);
			board = makeMove(bestMove, board, player);
	        filled++;	
			System.out.println("The Player " + getPlayer(player) + " Move : ("+(bestMove/10)+","+(bestMove%10)+")");
			PrintBoard Output = new PrintBoard();
			Output.printBoard();			
			max =  Integer.MIN_VALUE; ub = 0; lb =0;
		} else {System.out.println("There is not possible move by the Agent");}
		legalMoves = getLegalMoves(board, !player);
		printLegalMoves(legalMoves, !player);
		NextMove();}	
	private static int agent(char[][] board, boolean player, int minValue, int maxValue, int depth){
		if(depth < 1){
			return evaluateFunction(board, player);}		
		int l[] = getLegalMoves(board, player);
		for(int i=0; i<l.length; i++){            
			if(Depth == depth)
				board = copyBoard();			
			char[][] board1 = makeMove(l[i], board, player);
			int score = getScore(player, board1);			
			if(score >= minValue && player) {
				minValue=score;
				ub = minValue;}
			if(score < maxValue && !player){
				maxValue=score;
				lb = maxValue;}		    
		    int val = -agent(board1, !player, ub, lb, depth-1);		    
		    if(Depth == depth){
		        val = (ub-lb) + (valueField[l[i]]);
			    if(val >= max){
			        max = val;
			        bestMove = l[i];
			    }}}return bestMove;}	
	private static int evaluateFunction(char[][] board, boolean player) 
	{return (getScore(player, board)) + (totalWeight(player,board));}	
	private static char[][] makeMove(int move, char[][] board, boolean player){
		board[move/10][move%10] = getPlayer(player);
        for(int i=0; i < locations.length; i++)
            board = makeFlips(move, locations[i], board, player);
        return board;}
	private static char[][] makeFlips(int move, int direction, char[][] board, boolean player) {
		int tempPlace = 0;
		int position = move + direction;
		if(isValid(position)){
			if(board[position/10][position%10] == getPlayer(player))
				tempPlace = 0;
			while(board[position/10][position%10] == getPlayer(!player))
				position = position + direction;
			if(board[position/10][position%10] == getPlayer(player))
				tempPlace = position;}
	    if(tempPlace == 0) 
	    	return board;
	    position = move + direction;
	    while(position != tempPlace) {
	        board[(position/10)][(position%10)] = getPlayer(player);
	        position = position + direction;}
	    return board;}
	private static int totalWeight(boolean player,char[][] board){ 
	   	int total=0;  	
	   	for(int i=0;i<moves.length;i++){
	   		if(board[moves[i]/10][moves[i]%10] == getPlayer(player)) 
	   			total = total + valueField[moves[i]];
	   		if(board[moves[i]/10][moves[i]%10] == getPlayer(!player)) 
	   			total = total - valueField[moves[i]];
	   	}return total;}	
	private static char[][] copyBoard() {
		char[][] cboard = new char[10][10];
		for(int i=1; i < cboard.length; i++)
			for(int j=1; j < cboard.length; j++)
				cboard[i][j] = board[i][j];
		return cboard;}
	private static void printLegalMoves(int[] p, boolean player) {
		System.out.print("Legal Moves for player " + getPlayer(player) + " : ");
		for(int i=0; i<p.length;i++)
			System.out.print("(" + p[i]/10 + "," + p[i]%10 + ")" + " ");
		System.out.println();}    
	private static int[] getLegalMoves(char[][] board, boolean player) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for(int i=0; i<moves.length; i++) {
			if(validMove(moves[i], board, player))
				arr.add(moves[i]);}
		int[] l = new int[arr.size()];
		for(int j=0; j<arr.size(); j++)
			l[j] = arr.get(j);
		return l;}
	private static boolean validMove(int move, char[][] board, boolean player) {
		boolean flag = false;
		if(board[move/10][move%10] == getPlayer(player) || board[move/10][move%10] == getPlayer(!player))
			return false;		
		for(int k=0; k<locations.length; k++) {
			boolean b = validBox(move, locations[k], player, board);
			if(b) flag = true;	
		}return flag;}	
	private static boolean validBox(int move, int direction, boolean player, char[][] board) 
	{	int position = move + direction;
		if(isValid(position)){
			if(board[position/10][position%10] == getPlayer(player))
				return false;
			while(board[position/10][position%10] == getPlayer(!player)) {
				position = position + direction;
			}
			if(isValid(position) && board[position/10][position%10] == getPlayer(player))
				return true;
		}		return false;	}
	private static int getScore(boolean player, char[][] board) {
		int firstPlay = 0;
		int secondplay = 0;
		for(int i = 11; i <= 88; i++){
			if(i%10 >=1 && i%10 <= 8) {
				if(board[i/10][i%10] == getPlayer(player)) 
					firstPlay++;
				if(board[i/10][i%10] == getPlayer(!player)) 
					secondplay++; 
			}}return firstPlay - secondplay;	}
	private static boolean isValid(int pos) {
		for(int i=0; i<moves.length; i++)
			if(moves[i] == pos)
				return true;
		return false;}
	private static char getPlayer(boolean player) {
		if(player)
			return 'W';
		return 'B';}
}