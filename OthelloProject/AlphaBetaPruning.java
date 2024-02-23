public abstract class AlphaBetaPruning implements IOthelloAI {

    protected int playerNumber;

    public Position decideMove(GameState s) {
        this.playerNumber = s.getPlayerInTurn();
        PositionUtility move = maxValue(s, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return move.position;
    }

    private PositionUtility maxValue(GameState s, int alpha, int beta) {
        if(Cutoff(s)){
            int util = Eval(s);
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
        if(Cutoff(s)){
            int util = Eval(s);
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

    protected abstract int Eval(GameState s);

    protected abstract boolean Cutoff(GameState s);
}


