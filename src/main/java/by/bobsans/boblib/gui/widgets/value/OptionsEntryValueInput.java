package by.bobsans.boblib.gui.widgets.value;

import by.bobsans.boblib.gui.widgets.WatchedTextField;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.TextFieldWidget;

import java.util.function.Consumer;

public abstract class OptionsEntryValueInput<T> extends OptionsEntryValue<T> {
    final TextFieldWidget textField;

    OptionsEntryValueInput(String optionName, T value, Consumer<T> save) {
        super(optionName, value, save);

        textField = new WatchedTextField(this, Minecraft.getInstance().font, 0, 0, 138, 18);
        textField.setMaxLength(256);
        textField.setValue(String.valueOf(value));
    }

    @Override
    protected void drawValue(MatrixStack stack, int entryWidth, int entryHeight, int x, int y, int mouseX, int mouseY, boolean selected, float partialTicks) {
        textField.x = x + entryWidth - 160;
        textField.y = y + entryHeight / 6;
        textField.render(stack, mouseX, mouseY, partialTicks);
    }

    @Override
    public IGuiEventListener getListener() {
        return textField;
    }

    public abstract void setValue(String value);
}
