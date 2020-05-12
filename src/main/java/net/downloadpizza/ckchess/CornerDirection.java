package net.downloadpizza.ckchess;

import net.minecraft.util.math.Direction;

// x = +east -west
// z = +south -north

public enum CornerDirection {
    NORTH_EAST(Direction.EAST, Direction.NORTH),
    SOUTH_EAST(Direction.SOUTH, Direction.EAST),
    SOUTH_WEST(Direction.WEST, Direction.SOUTH),
    NORTH_WEST(Direction.NORTH, Direction.WEST);

    private final Direction yBoardOffset;
    private final Direction xBoardOffset;

    CornerDirection(Direction yBoardOffset, Direction xBoardOffset) {
        this.yBoardOffset = yBoardOffset;
        this.xBoardOffset = xBoardOffset;
    }

    public Direction getYBoardOffset() {
        return yBoardOffset;
    }

    public Direction getXBoardOffset() {
        return xBoardOffset;
    }
}
