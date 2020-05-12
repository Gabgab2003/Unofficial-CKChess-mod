package net.downloadpizza.ckchess;

import net.downloadpizza.ckchess.board.ChessBoard;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class Ckchess implements ModInitializer {
    private static final String KEYBIND_CATEGORY = "Chess Keybindings";

    private ChangeBoardGui selectionGui;

    @Override
    public void onInitialize() {
        FabricKeyBinding selectionMenu = FabricKeyBinding.Builder.create(
                new Identifier("ckchess", "selectionbind"),
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                KEYBIND_CATEGORY
        ).build();

        FabricKeyBinding displayBoard = FabricKeyBinding.Builder.create(
                new Identifier("ckchess", "displaybind"),
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                KEYBIND_CATEGORY
        ).build();

        KeyBindingRegistry.INSTANCE.addCategory(KEYBIND_CATEGORY);

        KeyBindingRegistry.INSTANCE.register(selectionMenu);
        KeyBindingRegistry.INSTANCE.register(displayBoard);

        selectionGui = new ChangeBoardGui();

        ClientTickCallback.EVENT.register(e ->
        {
            if (selectionMenu.isPressed()) MinecraftClient.getInstance().openScreen(new PizzaScreen(selectionGui));
            if (displayBoard.isPressed()) {
                BlockPos block = selectionGui.getBlock();
                CornerDirection dir = selectionGui.getDirection();
                int spacing = selectionGui.getSpacing();

                if(spacing == -1) {
                    return;
                }

                boolean white = selectionGui.isWhiteSide();

                if (block != null && dir != null) {
                    ChessBoard cb = new ChessBoard(block, dir, spacing);
                    MinecraftClient.getInstance().openScreen(new PizzaScreen(new ShowBoardGui(cb, white)));
                }
            }
        });
    }
}
