public class AlphaBetaPruningSimple extends AlphaBetaPruning {
    
    protected boolean Cutoff(GameState s, int depth) {
        return s.isFinished();
    }

    protected double Eval(GameState s) {
        int[] tokensCount = s.countTokens();
        if (tokensCount[0] > tokensCount[1]) {
            if (playerNumber == 1) {
                return 1;
            }
            return -1;
        } else if (tokensCount[1] > tokensCount[0]) {
            if (playerNumber == 1) {
                return -1;
            }
            return 1;
        } else {
            return 0;
        }
    }
}
