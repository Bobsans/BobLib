package by.bobsans.boblib.gui.widgets;

import by.bobsans.boblib.gui.widgets.value.OptionsEntryValueInput;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.ParametersAreNonnullByDefault;

public class WatchedTextField extends TextFieldWidget {
    private final OptionsEntryValueInput<?> watcher;

    public WatchedTextField(OptionsEntryValueInput<?> watcher, FontRenderer fontRenderer, int x, int y, int width, int height) {
        super(fontRenderer, x, y, width, height, new StringTextComponent(""));
        this.watcher = watcher;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void insertText(String string) {
        super.insertText(string);
        watcher.setValue(getValue());
    }

    @Override
    @ParametersAreNonnullByDefault
    public void setValue(String value) {
        super.setValue(value);
        watcher.setValue(getValue());
    }

    @Override
    public void deleteWords(int count) {
        super.deleteWords(count);
        watcher.setValue(getValue());
    }
}
