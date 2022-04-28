package by.bobsans.boblib.gui.widgets.value;

import by.bobsans.boblib.gui.widgets.OptionsListWidget;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public abstract class OptionsEntryValue<T> extends OptionsListWidget.Entry {
    private final Consumer<T> save;
    private final BaseComponent title;
    private final String description;
    T value;
    private int x;

    OptionsEntryValue(String optionName, T value, Consumer<T> save) {
        this.title = new TranslatableComponent(optionName);
        this.description = optionName + ".desc";
        this.value = value;
        this.save = save;
    }

    static <T> String getLangKey(ForgeConfigSpec.ConfigValue<T> spec, String langLeyPrefix) {
        return langLeyPrefix + ".config." + String.join(".", spec.getPath());
    }

    @Override
    public final void render(@NotNull PoseStack stack, int index, int rowTop, int rowLeft, int width, int height, int mouseX, int mouseY, boolean hovered, float deltaTime) {
        Font fontRenderer = Minecraft.getInstance().font;
        fontRenderer.draw(stack, title, rowLeft + 10, rowTop + (height / 4.0f) + (fontRenderer.lineHeight / 2.0f), 16777215);
        drawValue(stack, width, height, rowLeft, rowTop, mouseX, mouseY, hovered, deltaTime);
        this.x = rowLeft;
    }

    public void save() {
        save.accept(value);
    }

    public AbstractWidget getListener() {
        return null;
    }

    public BaseComponent getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getX() {
        return x;
    }

    protected abstract void drawValue(PoseStack stack, int entryWidth, int entryHeight, int x, int y, int mouseX, int mouseY, boolean selected, float partialTicks);
}
