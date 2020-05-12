package net.downloadpizza.ckchess.board;

import net.minecraft.util.Identifier;

public class ChessPiece {
    private final Piece piece;
    private final Color color;

    public Identifier getPath() {
        return new Identifier("pieces", color.name().toLowerCase()+"/"+piece.name().toLowerCase()+".png");
    }

    public static ChessPiece pieceOrNull(String name) {
        try {
            return new ChessPiece(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public ChessPiece(String name) {
        String[] nameParts = name.split(" ");
        color = Color.valueOf(nameParts[0].toUpperCase());
        piece = Piece.valueOf(nameParts[1].toUpperCase());
    }

    public Piece getPiece() {
        return piece;
    }

    public Color getColor() {
        return color;
    }
}

enum Piece {
    PAWN,
    ROOK,
    KNIGHT,
    BISHOP,
    QUEEN,
    KING;
}

enum Color {
    BLACK,
    WHITE;
}