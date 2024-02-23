public class AlphaBetaPruningDepthCutoff extends AlphaBetaPruning {
    // This will steer towards states with the most tokens
    // It doesnt care about the amount, only wether or not it has more than its opponent
    // Cuts of at some amount of tiles placed, which depends on the size of the board 

    private int depthCutoff = 14;

    protected boolean Cutoff(GameState s, int depth) {
        if (s.isFinished()) return true;
        if (depth > depthCutoff) return true;        
        return false;
    }

    protected double Eval(GameState s) {
        if (s.isFinished()) return Utility(s);
        return 0;
        // TBD
    }
}
