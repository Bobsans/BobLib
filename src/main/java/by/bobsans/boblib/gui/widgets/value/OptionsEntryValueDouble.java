package by.bobsans.boblib.gui.widgets.value;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Consumer;

public class OptionsEntryValueDouble extends OptionsEntryValueInput<Double> {
    private OptionsEntryValueDouble(String optionName, Double value, Consumer<Double> save) {
        super(optionName, value, save);

        textField.setFilter((s) -> s.matches("^[0-9.]*$"));
    }

    public OptionsEntryValueDouble(String langKeyPrefix, ForgeConfigSpec.DoubleValue spec) {
        this(getLangKey(spec, langKeyPrefix), spec.get(), spec::set);
    }

    public void setValue(String text) {
        value = Double.valueOf(text);
    }
}
