public class OurAI1 implements IOthelloAI {

    public Position decideMove(GameState s) {
        System.out.println("Deciding move");
        System.out.println(s.legalMoves().size());
        PositionUtility move = maxValue(s, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return move.position;
    }

    private PositionUtility maxValue(GameState s, int alpha, int beta) {
        //System.out.println("maxValue");
        if(s.isFinished()){
            int util = Utility(s);
            return new PositionUtility(util, null);
        }
        int v = Integer.MIN_VALUE;
        Position move = s.legalMoves().size() != 0 ? s.legalMoves().get(0) : null;
        for (Position p : s.legalMoves()) {
            GameState newGameState = new GameState(s.getBoard(), s.getPlayerInTurn());
            newGameState.insertToken(p);
            
            PositionUtility u = minValue(newGameState, alpha, beta);
            if (u.utility > v) {
                v = u.utility;
                move = p;
                beta = Integer.max(v, alpha);
            }
            if (v >= beta) return new PositionUtility(v, move);
        }
        return new PositionUtility(v, move);
    }

    private PositionUtility minValue(GameState s, int alpha, int beta){
        //System.out.println("minValue");
        if(s.isFinished()){
            int util = Utility(s);
            return new PositionUtility(util, null);
        }
        int v = Integer.MAX_VALUE;
        Position move = s.legalMoves().size() != 0 ? s.legalMoves().get(0) : null;
        for (Position p : s.legalMoves()) {
            GameState newGameState = new GameState(s.getBoard(), s.getPlayerInTurn());
            newGameState.insertToken(p);
            
            PositionUtility u = maxValue(newGameState, alpha, beta);
            if (u.utility < v) {
                v = u.utility;
                move = p;
                beta = Integer.min(v, beta);
            }
            if (v <= alpha) return new PositionUtility(v, move);
        }
        return new PositionUtility(v, move);
    }

    private int Utility(GameState s) {
        //System.out.println("Deciding utility");
        int[] tokensCount = s.countTokens();
        if (tokensCount[0] > tokensCount[1]) {
            return 1;
        } else if (tokensCount[1] > tokensCount[0]) {
            return -1;
        } else {
            return 0;
        }
    }
}


