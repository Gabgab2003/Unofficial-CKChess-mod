package net.downloadpizza.ckchess;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import net.downloadpizza.ckchess.board.ChessBoard;
import net.downloadpizza.ckchess.board.ChessPiece;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ShowBoardGui extends LightweightGuiDescription {
    private static final Identifier[] ROWS = {
            new Identifier("font","1.png"),
            new Identifier("font","2.png"),
            new Identifier("font","3.png"),
            new Identifier("font","4.png"),
            new Identifier("font","5.png"),
            new Identifier("font","6.png"),
            new Identifier("font","7.png"),
            new Identifier("font","8.png")
    };
    private static final Identifier[] COLUMNS = {
            new Identifier("font","a.png"),
            new Identifier("font","b.png"),
            new Identifier("font","c.png"),
            new Identifier("font","d.png"),
            new Identifier("font","e.png"),
            new Identifier("font","f.png"),
            new Identifier("font","g.png"),
            new Identifier("font","h.png")
    };

    public static final int pieceSize = 25;

    public ShowBoardGui(ChessBoard board, boolean whiteSide) {

        WGridPanel root = new WGridPanel(pieceSize);
        setRootPanel(root);

        root.setSize(pieceSize*10, pieceSize*10);

        for (int i = 0; i < 8; i++) {
            int index;
            if(!whiteSide) {
                index = i;
            } else {
                index = 7-i;
            }


            root.add(new WSprite(ROWS[index]), 0, i+1 , 1, 1);
            root.add(new WSprite(ROWS[index]), 9, i+1, 1, 1);

            root.add(new WSprite(COLUMNS[i]), i+1, 0, 1, 1);
            root.add(new WSprite(COLUMNS[i]), i+1, 9, 1, 1);
        }

        root.add(new ChessBoardRenderer(board), 1, 1, 8, 8);
    }
}

@Environment(EnvType.CLIENT)
class ChessBoardRenderer extends WGridPanel {
    private static final Identifier EMPTY_FIELD = new Identifier("pieces", "empty.png");
    //                                 aarrggbb
    private static final int BLACK = 0xee333333;
    private static final int WHITE = 0xeeffffff;

    private static final BackgroundPainter CHECKER_BOARD = (left, top, panel) -> {
        int size = ShowBoardGui.pieceSize;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                boolean whiteField = (x%2) == (y%2);
                ScreenDrawing.coloredRect(left+x*size, top+y*size, size, size, whiteField ? WHITE : BLACK);
            }
        }
    };

    ChessBoardRenderer(ChessBoard board) {
        super(ShowBoardGui.pieceSize);
        this.setSize(8*ShowBoardGui.pieceSize, 8*ShowBoardGui.pieceSize);
        this.setBackgroundPainter(CHECKER_BOARD);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                ChessPiece piece = board.getPiece(x, y);
                Identifier icon;
                if(piece == null) {
                    icon = EMPTY_FIELD;
                } else {
                    icon = piece.getPath();
                }

                this.add(new WSprite(icon), x, y);
            }
        }
    }
}