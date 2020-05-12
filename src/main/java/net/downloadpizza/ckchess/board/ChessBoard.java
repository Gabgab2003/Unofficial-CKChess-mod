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

@Environment(EnvType.CLIENT)
public class ChessBoard {
    private final ChessPiece[][] board = new ChessPiece[8][8];

    public ChessBoard(BlockPos pos, CornerDirection cornerDirection, int spacing) {
        World world = MinecraftClient.getInstance().world;
        assert world != null;
        for (int x = 0; x < 8; x++) {
            BlockPos current = pos;
            for (int i = 0; i < x*spacing; i++) {
                current = current.add(cornerDirection.getXBoardOffset().getVector());
            }
            for (int y = 0; y < 8; y++) {
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

    public ChessPiece getPiece(int x, int y) {
        if(x < 0 || x > 7 || y < 0 || y > 7) throw new IllegalArgumentException("Coordinates [" + x + "|" + y + "] outside of [0;7]");
        return board[x][7-y];
    }
}
