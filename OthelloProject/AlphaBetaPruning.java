import java.time.LocalTime;

public abstract class AlphaBetaPruning implements IOthelloAI {

    protected int playerNumber;

    public Position decideMove(GameState s) {
        var before=LocalTime.now();
        this.playerNumber = s.getPlayerInTurn();
        PositionUtility move = maxValue(s, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
        if (move.position == null) {
            return s.legalMoves().get(0);
        }
        System.out.println("Started: "+before+", ended "+LocalTime.now());
        return move.position;
    }

    private PositionUtility maxValue(GameState s, double alpha, double beta, int depth) {
        if(Cutoff(s, depth)){
            double util = Eval(s);
            return new PositionUtility(util, null);
        }
        double v = Double.NEGATIVE_INFINITY;
        Position move = s.legalMoves().size() != 0 ? s.legalMoves().get(0) : null;
        for (Position p : s.legalMoves()) {
            GameState newGameState = new GameState(s.getBoard(), s.getPlayerInTurn());
            newGameState.insertToken(p);
            PositionUtility u = minValue(newGameState, alpha, beta, depth + 1);
            //int i=0;
            if (u.utility > v) {
                //System.out.println(u.utility+" is bigger than "+v+" and this is iteration "+i );
                v = u.utility;
                move = p;
                alpha = Double.max(v, alpha);
            }
            //System.out.println("utility: "+v+", beta: "+beta+", alpha:"+alpha);
            if (v >= beta) return new PositionUtility(v, move);
        }
        return new PositionUtility(v, move);
    }

    private PositionUtility minValue(GameState s, double alpha, double beta, int depth){
        if(Cutoff(s, depth)){
            double util = Eval(s);
            return new PositionUtility(util, null);
        }
        double v = Double.POSITIVE_INFINITY;
        Position move = s.legalMoves().size() != 0 ? s.legalMoves().get(0) : null;
        for (Position p : s.legalMoves()) {
            GameState newGameState = new GameState(s.getBoard(), s.getPlayerInTurn());
            newGameState.insertToken(p);
            
            PositionUtility u = maxValue(newGameState, alpha, beta, depth + 1);
            if (u.utility < v) {
                v = u.utility;
                move = p;
                beta = Double.min(v, beta);
            }
            if (v <= alpha) return new PositionUtility(v, move);
        }
        return new PositionUtility(v, move);
    }

    protected double Utility(GameState s) {
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

    protected abstract double Eval(GameState s);

    protected abstract boolean Cutoff(GameState s, int depth);
}