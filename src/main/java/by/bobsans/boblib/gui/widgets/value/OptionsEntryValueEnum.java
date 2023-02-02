package by.bobsans.boblib.gui.widgets.value;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Locale;
import java.util.function.Consumer;

public class OptionsEntryValueEnum<T extends Enum<T>> extends OptionsEntryValue<T> {
    private final Button button;

    private OptionsEntryValueEnum(String optionName, T[] values, T selected, Consumer<T> save) {
        super(optionName, selected, save);

        this.button = Button
            .builder(Component.literal(selected.name().toUpperCase(Locale.ROOT)), (btn) -> value = values[(value.ordinal() + 1) % values.length])
            .bounds(0, 0, 140, 20)
            .build();
    }

    public OptionsEntryValueEnum(String langKeyPrefix, ForgeConfigSpec.EnumValue<T> spec, T[] values) {
        this(getLangKey(spec, langKeyPrefix), values, spec.get(), spec::set);
    }

    @Override
    protected void drawValue(PoseStack stack, int entryWidth, int entryHeight, int x, int y, int mouseX, int mouseY, boolean selected, float partialTicks) {
        this.button.setX(x + entryWidth - 160);
        this.button.setY(y + entryHeight / 6);
        this.button.setMessage(Component.literal(value.name().toUpperCase(Locale.ROOT)));
        this.button.render(stack, mouseX, mouseY, partialTicks);
    }

    @Override
    public AbstractWidget getListener() {
        return button;
    }
}
