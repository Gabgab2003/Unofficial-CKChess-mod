package net.downloadpizza.ckchess;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class Ckchess implements ModInitializer {
    private static final String KEYBIND_CATEGORY = "Chess Keybindings";

    private final ChangeBoardGui selectionGui = new ChangeBoardGui();
    private final ShowBoardGui showBoardGui = new ShowBoardGui(selectionGui);

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

        ClientTickCallback.EVENT.register(e ->
        {
            if (selectionMenu.isPressed())
                MinecraftClient.getInstance().openScreen(new PizzaScreen(selectionGui));

            if (displayBoard.isPressed())
                MinecraftClient.getInstance().openScreen(new PizzaScreen(showBoardGui));

            if (MinecraftClient.getInstance().currentScreen instanceof PizzaScreen && ((PizzaScreen) MinecraftClient.getInstance().currentScreen).getDescription() instanceof ShowBoardGui) {
                showBoardGui.redraw();
            }
        });
    }
}
