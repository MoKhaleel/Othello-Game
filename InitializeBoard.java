package MyPack;

public class InitializeBoard extends OthelloAlphaBeta{
	public static void Initialize()	{
		for(int i=0; i<dimension; i++)
			for(int j=0; j<dimension; j++)
				board[i][j] = ' ';
		board[3][3] = 'B';
		board[4][4] = 'B';
		board[3][4] = 'W';
		board[4][3] = 'W';
        filled = 4;}	
	public void initializeBoard() {
		for(int j=1; j<board.length; j++)
			board[0][j] = (char)j;		
		for(int i=1; i<board.length; i++)
			board[i][0] = (char)i;		
		for(int i=1; i<board.length; i++)
			for(int j=1; j<board.length; j++)
				board[i][j] = '.';			
		board[4][4] = 'B';
		board[5][5] = 'B';
		board[4][5] = 'W';
		board[5][4] = 'W';
        filled = 4;}
}
