package by.bobsans.boblib.gui.widgets.value;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Consumer;

public class OptionsEntryValueBoolean extends OptionsEntryValue<Boolean> {
    private final Button button;

    private OptionsEntryValueBoolean(String optionName, boolean value, Consumer<Boolean> save) {
        super(optionName, value, save);

        this.button = Button
            .builder(Component.translatable("gui." + (value ? "yes" : "no")), (btn) -> this.value = !this.value)
            .bounds(0, 0, 140, 20)
            .build();
    }

    public OptionsEntryValueBoolean(String langKeyPrefix, ForgeConfigSpec.BooleanValue spec) {
        this(getLangKey(spec, langKeyPrefix), spec.get(), spec::set);
    }

    @Override
    protected void drawValue(PoseStack stack, int entryWidth, int entryHeight, int x, int y, int mouseX, int mouseY, boolean selected, float partialTicks) {
        this.button.setX(x + entryWidth - 160);
        this.button.setY(y + entryHeight / 6);
        this.button.setMessage(Component.translatable("gui." + (value ? "yes" : "no")));
        this.button.render(stack, mouseX, mouseY, partialTicks);
    }

    @Override
    public AbstractWidget getListener() {
        return button;
    }
}
