package by.bobsans.boblib.gui.widgets.value;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Consumer;

public class OptionsEntryValueInteger extends OptionsEntryValueInput<Integer> {
    private OptionsEntryValueInteger(String optionName, Integer value, Consumer<Integer> save) {
        super(optionName, value, save);

        textField.setFilter((s) -> s.matches("^[0-9]*$"));
    }

    public OptionsEntryValueInteger(String langKeyPrefix, ForgeConfigSpec.IntValue spec) {
        this(getLangKey(spec, langKeyPrefix), spec.get(), spec::set);
    }

    public void setValue(String text) {
        value = Integer.valueOf(text);
    }
}
