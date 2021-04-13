package by.bobsans.boblib.gui.widgets.value;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Locale;
import java.util.function.Consumer;

public class OptionsEntryValueEnum<T extends Enum<T>> extends OptionsEntryValue<T> {
    private final Button button;

    private OptionsEntryValueEnum(String optionName, T[] values, T selected, Consumer<T> save) {
        super(optionName, selected, save);

        this.button = new Button(0, 0, 140, 20, new StringTextComponent(selected.name().toUpperCase(Locale.ROOT)), (btn) -> value = values[(value.ordinal() + 1) % values.length]);
    }

    public OptionsEntryValueEnum(String langKeyPrefix, ForgeConfigSpec.EnumValue<T> spec, T[] values) {
        this(getLangKey(spec, langKeyPrefix), values, spec.get(), spec::set);
    }

    @Override
    protected void drawValue(MatrixStack stack, int entryWidth, int entryHeight, int x, int y, int mouseX, int mouseY, boolean selected, float partialTicks) {
        this.button.x = x + entryWidth - 160;
        this.button.y = y + entryHeight / 6;
        this.button.setMessage(new StringTextComponent(value.name().toUpperCase(Locale.ROOT)));
        this.button.render(stack, mouseX, mouseY, partialTicks);
    }

    @Override
    public IGuiEventListener getListener() {
        return button;
    }
}
