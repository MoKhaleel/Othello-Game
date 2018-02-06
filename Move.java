package MyPack;

public class Move extends OthelloAlphaBeta{
	public void Initialize(){	
 		for(int i=0; i<moves.length; i++)
				moves[i] = i;
	}	
	public void initializeMoves(){
		int count = 0;
		for(int i=11; i<=88; i++){
			if(i%10 >=1 && i%10 <= 8)
				moves[count++] = i;
		}}}
