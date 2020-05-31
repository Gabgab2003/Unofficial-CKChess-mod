package net.downloadpizza.ckchess;

import net.downloadpizza.ckchess.board.ChessBoard;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
public class ChessGameRecording {
    private final ChangeBoardGui cbg;
    private final ArrayList<ChessBoard> boards = new ArrayList<>();
    private final ChessBoard rootBoard;
    private ChessBoard lastBoard;

    public ChessGameRecording(ChangeBoardGui cbg) {
        this.cbg = cbg;
        this.rootBoard = new ChessBoard();
        lastBoard = rootBoard.copy();
        boards.add(lastBoard);
    }

    public void refresh() {
        rootBoard.load(cbg.getBlock(), cbg.getDirection(), cbg.getSpacing());
        if(!rootBoard.equals(lastBoard)) {
            lastBoard = rootBoard.copy();
            boards.add(lastBoard);
        }
    }

    @Override
    public String toString() {
        return boards.stream().map(ChessBoard::toString).collect(Collectors.joining("\n"));
    }
}
