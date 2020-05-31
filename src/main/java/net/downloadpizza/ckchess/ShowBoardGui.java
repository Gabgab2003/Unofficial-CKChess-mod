package net.downloadpizza.ckchess;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import net.downloadpizza.ckchess.board.ChessBoard;
import net.downloadpizza.ckchess.board.ChessPiece;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

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

    private static final Identifier EMPTY_FIELD = new Identifier("pieces", "empty.png");

    public static final int pieceSize = 25;
    private final ChessBoard board = new ChessBoard();
    private final ChessBoardRenderer boardRenderer = new ChessBoardRenderer(board);
    private final ChangeBoardGui cbg;

    private final WSprite[] leftLabels = newSpriteArray();
    private final WSprite[] rightLabels = newSpriteArray();
    private final WSprite[] topLabels = newSpriteArray();
    private final WSprite[] bottomLabels = newSpriteArray();

    private static WSprite[] newSpriteArray() {
        WSprite[] sprites = new WSprite[8];
        for (int i = 0; i < 8; i++) {
            sprites[i] = new WSprite(EMPTY_FIELD);
        }
        return sprites;
    }

    public ShowBoardGui(ChangeBoardGui cbg) {
        this.cbg = cbg;

        WGridPanel root = new WGridPanel(pieceSize);
        setRootPanel(root);

        root.setSize(pieceSize*10, pieceSize*10);

        for (int i = 0; i < 8; i++) {
            root.add(leftLabels[i], 0, i+1 , 1, 1);
            root.add(rightLabels[i], 9, i+1, 1, 1);

            root.add(topLabels[i], i+1, 0, 1, 1);
            root.add(bottomLabels[i], i+1, 9, 1, 1);
        }

        root.add(boardRenderer, 1, 1, 8, 8);
    }

    public void redraw() {
        for (int i = 0; i < 8; i++) {
            int rowIndex = cbg.isWhiteSide() ? 7 - i : i;
            int columnIndex = cbg.isWhiteSide() ? i : 7 - i;
            leftLabels[i].setImage(ROWS[rowIndex]);
            rightLabels[i].setImage(ROWS[rowIndex]);

            topLabels[i].setImage(COLUMNS[columnIndex]);
            bottomLabels[i].setImage(COLUMNS[columnIndex]);
        }
        board.load(cbg.getBlock(), cbg.getDirection(), cbg.getSpacing());
        boardRenderer.redraw();
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

    private final WSprite[][] sprites = new WSprite[8][8];
    private final ChessBoard board;

    ChessBoardRenderer(ChessBoard board) {
        super(ShowBoardGui.pieceSize);
        this.board = board;
        this.setSize(8*ShowBoardGui.pieceSize, 8*ShowBoardGui.pieceSize);
        this.setBackgroundPainter(CHECKER_BOARD);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                WSprite field = new WSprite(EMPTY_FIELD);
                sprites[x][y] = field;
                this.add(field, x, y);
            }
        }
    }

    public void redraw() {
        if(board == null) {
            return;
        }
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                ChessPiece piece = board.getPiece(x, y);
                Identifier icon;
                if(piece == null) {
                    icon = EMPTY_FIELD;
                } else {
                    icon = piece.getPath();
                }

                sprites[x][y].setImage(icon);
            }
        }
    }
}