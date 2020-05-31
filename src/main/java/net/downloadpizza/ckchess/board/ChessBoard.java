package net.downloadpizza.ckchess.board;

import net.downloadpizza.ckchess.CornerDirection;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

@Environment(EnvType.CLIENT)
public class ChessBoard {
    private final ChessPiece[][] board;

    public void load(BlockPos pos, CornerDirection cornerDirection, int spacing) {
        if(spacing < 0) return;
        World world = MinecraftClient.getInstance().world;
        assert world != null;
        for (int x = 0; x < 8; x++) {
            BlockPos current = pos;
            for (int i = 0; i < x*spacing; i++) {
                current = current.add(cornerDirection.getXBoardOffset().getVector());
            }
            for (int y = 0; y < 8; y++) {
                board[y][x] = null;
                BlockEntity blockEntity = world.getBlockEntity(current);
                if(blockEntity != null && blockEntity.getType() == BlockEntityType.BANNER) {
                    BannerBlockEntity bannerBlockEntity = (BannerBlockEntity) blockEntity;
                    Text name = bannerBlockEntity.getCustomName();
                    if(name != null)
                        board[y][x] = ChessPiece.pieceOrNull(name.getString());
                }
                for (int i = 0; i < spacing; i++) {
                    current = current.add(cornerDirection.getYBoardOffset().getVector());
                }
            }
        }
    }

    public ChessBoard() {
        this(new ChessPiece[8][8]);
    }

    public ChessBoard(ChessPiece[][] board) {
        this.board = board;
    }

    public ChessBoard copy() {
        ChessPiece[][] copy = board.clone();
        return new ChessBoard(copy);
    }

    public ChessPiece getPiece(int x, int y) {
        if(x < 0 || x > 7 || y < 0 || y > 7) throw new IllegalArgumentException("Coordinates [" + x + "|" + y + "] outside of [0;7]");
        return board[x][7-y];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Arrays.equals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "board=" + Arrays.deepToString(board) +
                '}';
    }
}
