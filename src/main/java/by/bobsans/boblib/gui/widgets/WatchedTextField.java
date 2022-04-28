package by.bobsans.boblib.gui.widgets;

import by.bobsans.boblib.gui.widgets.value.OptionsEntryValueInput;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.TextComponent;

import javax.annotation.ParametersAreNonnullByDefault;

public class WatchedTextField extends EditBox {
    private final OptionsEntryValueInput<?> watcher;

    public WatchedTextField(OptionsEntryValueInput<?> watcher, Font font, int x, int y, int width, int height) {
        super(font, x, y, width, height, new TextComponent(""));
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
