public class AlphaBetaPruningDepthCutoff extends AlphaBetaPruning {
    // This will steer towards states with the most tokens
    // It doesnt care about the amount, only wether or not it has more than its opponent
    // Cuts of at some amount of tiles placed, which depends on the size of the board 

    private int depthCutoff = 10;
    private int cornerValue = 8;
    private int sideValue = 4;

    protected boolean Cutoff(GameState s, int depth) {
        if (s.isFinished()) return true;
        if (depth > depthCutoff) return true;        
        return false;
    }

    protected double Eval(GameState s) {
        if (s.isFinished()) return Utility(s);
        //var tokens = s.countTokens();
        var boardSize = this.boardSize(s);
        double ourControl;
        double opponentControl;

        if(s.getPlayerInTurn()==1){
            ourControl = this.countPlayerTokens(s)[0] / (double) boardSize;
            opponentControl = this.countPlayerTokens(s)[1] / (double) boardSize * -1;
        }else{
            ourControl = this.countPlayerTokens(s)[1] / (double) boardSize;
            opponentControl = this.countPlayerTokens(s)[0] / (double) boardSize * -1;
        }

        System.out.println(ourControl);
        System.out.println(opponentControl);
        return ourControl + opponentControl;
    }

    private int boardSize(GameState s) {

        int boardDimension = s.getBoard()[0].length;
        int innerBoardSize = (boardDimension - 2) * (boardDimension - 2);
        int corners = 4 * this.cornerValue;
        int sides = (boardDimension - 2) * 4 * this.sideValue; 
        return innerBoardSize + corners + sides;
    }

    private int[] countPlayerTokens(GameState s) {
        int tokens1 = 0;
    	int tokens2 = 0;
        int size = s.getBoard()[0].length;

    	for (int i = 0; i < size; i++){
    		for (int j = 0; j < size; j++){
                if(i == 0 || i == 8){
                    if(j==0 || j==8){
                        if (s.getBoard()[i][j] == 1 )
    				        tokens1 += this.cornerValue;
    			        else if (s.getBoard()[i][j] == 2 )
    				        tokens2 += this.cornerValue;
                    }else{
                        if (s.getBoard()[i][j] == 1 )
    				        tokens1 += this.sideValue;
    			        else if (s.getBoard()[i][j] == 2 )
    				        tokens2 += this.sideValue;
                    }
                }else{
                    if(j==0 || j==8){
                        if (s.getBoard()[i][j] == 1 )
    				        tokens1 += this.sideValue;
    			        else if (s.getBoard()[i][j] == 2 )
    				        tokens2 += this.sideValue;
                    }else{
                        if (s.getBoard()[i][j] == 1 )
    				        tokens1++;
    			        else if (s.getBoard()[i][j] == 2 )
    				        tokens2++;
                    }
                }
    		}
    	}
    	return new int[]{tokens1, tokens2};
    } 
}
