package by.bobsans.boblib.gui.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

import java.util.Objects;

public abstract class BaseScreen extends Screen {
    private final Screen parent;

    protected BaseScreen(Screen parent, ITextComponent title) {
        super(title);
        this.parent = parent;
    }

    @Override
    public void onClose() {
        Objects.requireNonNull(minecraft).displayGuiScreen(parent);
    }
}
