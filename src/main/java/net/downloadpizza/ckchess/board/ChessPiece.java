package net.downloadpizza.ckchess.board;

import net.minecraft.util.Identifier;

public class ChessPiece {
    private final Piece piece;
    private final Color color;
    private final Identifier path;

    public Identifier getPath() {
        return path;
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
        switch(nameParts[0].toLowerCase()) {
            case "white":
                color = Color.WHITE;
                break;
            case "black":
                color = Color.BLACK;
                break;
            default:
                throw new IllegalArgumentException("cant form color from " + nameParts[0]);
        }

        switch(nameParts[1].toLowerCase()) {
            case "pawn":
                piece = Piece.PAWN;
                break;
            case "rook":
                piece = Piece.ROOK;
                break;
            case "knight":
                piece = Piece.KNIGHT;
                break;
            case "bishop":
                piece = Piece.BISHOP;
                break;
            case "queen":
                piece = Piece.QUEEN;
                break;
            case "king":
                piece = Piece.KING;
                break;
            default:
                throw new IllegalArgumentException("cant form piece type from " + nameParts[1]);
        }

        path = new Identifier("pieces", color.path+"/"+piece.path+".png");
    }

    public Piece getPiece() {
        return piece;
    }

    public Color getColor() {
        return color;
    }
}

enum Piece {
    PAWN("pawn"),
    ROOK("rook"),
    KNIGHT("knight"),
    BISHOP("bishop"),
    QUEEN("queen"),
    KING("king");

    String path;

    Piece(String path) {
        this.path = path;
    }
}

enum Color {
    BLACK("black"),
    WHITE("white");

    String path;

    Color(String path) {
        this.path = path;
    }
}