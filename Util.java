package MyPack;

import java.util.ArrayList;
import javax.swing.JOptionPane;
public class Util extends OthelloAlphaBeta{
    // Return the size of the playing board.
	public int getBoardSize(){
        return dimension - 2;}    
    // Return the position of each disk on the board.
    public char getPosition(int value){
        return board[value/8][value%8];}    
    // Counts the number of disks for a given player.
    public void countStones(){
        int countB = 0;
        int countW = 0;
		for (int i=0; i<dimension-1; i++)
			for (int j=0; j<dimension-1; j++){
				if (board[i][j]== 'B')
					countB++;
				if (board[i][j]== 'W')
					countW++;}
		System.out.println("The number of White disks is : " + countW);
		System.out.println("The number of Black disks is : " + countB);
    }    
	public void FinalScore(){
		// By now the game is ended. The program is going to count how many Black and White disks on the board and announce the winner.
		int countB=0, countW=0;
		for (int i=0; i<dimension-1; i++)
			for (int j=0; j<dimension-1; j++){
				if (board[i][j]== 'B')
					countB++;
				if (board[i][j]== 'W')
					countW++;}		
		if (countB<countW)		
			JOptionPane.showMessageDialog(null, "Game Over, YOU WIN\nThe final results is Agent: " + countB + " Human: " + countW);
		else if (countB>countW)
			JOptionPane.showMessageDialog(null, "Game Over, YOU LOSE\nThe final results is Agent: " + countB + " Human: " + countW);
		else
			JOptionPane.showMessageDialog(null, "Game Over, Draw\nThe final results is Agent: " + countB + " Human: " + countW);		
	}    
    public boolean gameOver(){
    	// Check if the board is full.
    	if (isFull())
    		return true;    		
    	// Check if there any legal move by one of the players.
    	else if (isMoveAvailable(board, player) || (isMoveAvailable(board, !player)))
    		return false;    		
    	// Return true if the board is full.
    	else return true;}    
    public boolean isFull(){
        return (filled == (dimension - 2) * (dimension - 2));}        
    public boolean isMoveAvailable(char[][] board, boolean player){
       	ArrayList<Integer> validList = new ArrayList<Integer>();
    	for(int i=0; i<moves.length; i++)
    		if(isValidMove(moves[i], board, player))
    			validList.add(moves[i]);
    	int[] l = new int[validList.size()];
    	for(int j=0; j<validList.size(); j++)
    		l[j] = validList.get(j);
    	if (validList.size()>0)
    		return true;
    	else
    		return false;}        
    public boolean isValidMove(int move, char[][] board, boolean player){
    	boolean flag = false;
    	if(board[move/10][move%10] == whoIsPlaying(player) || board[move/10][move%10] == whoIsPlaying(!player))
    		return false;    	
    	for(int k=0; k<locations.length; k++) {
    		boolean b = isNextSquareValid(move, locations[k], player, board);
    		if(b) flag = true;}
    	return flag;}    	
    public boolean isNextSquareValid(int move, int direction, boolean player, char[][] board){
    	int pos = move + direction;
    	if(isValidU(pos)){
    		if(board[pos/10][pos%10] == whoIsPlaying(player))
    			return false;
    		while(board[pos/10][pos%10] == whoIsPlaying(!player))
    			pos = pos + direction;
    		if(isValidU(pos) && board[pos/10][pos%10] == whoIsPlaying(player))
    			return true;}
    	return false;
    }    
	private static char whoIsPlaying(boolean player){
		if(player)
			return 'W';
		return 'B';}
    public boolean isValidU(int pos){
    	for(int i=0; i<moves.length; i++)
    		if(moves[i] == pos)
    			return true;
    	return false;}    
    public boolean noAvailableMove(boolean player){
    	// Check if the board is full.
    	if (isFull() || (!isMoveAvailable(board, player)))
    		return true;
    	return false;}
}