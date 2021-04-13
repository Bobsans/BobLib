package by.bobsans.boblib.gui.widgets.value;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Consumer;

public class OptionsEntryValueBoolean extends OptionsEntryValue<Boolean> {
    private final Button button;

    private OptionsEntryValueBoolean(String optionName, boolean value, Consumer<Boolean> save) {
        super(optionName, value, save);

        this.button = new Button(0, 0, 140, 20, new TranslationTextComponent("gui." + (value ? "yes" : "no")), (btn) -> this.value = !this.value);
    }

    public OptionsEntryValueBoolean(String langKeyPrefix, ForgeConfigSpec.BooleanValue spec) {
        this(getLangKey(spec, langKeyPrefix), spec.get(), spec::set);
    }

    @Override
    protected void drawValue(MatrixStack stack, int entryWidth, int entryHeight, int x, int y, int mouseX, int mouseY, boolean selected, float partialTicks) {
        this.button.x = x + entryWidth - 160;
        this.button.y = y + entryHeight / 6;
        this.button.setMessage(new TranslationTextComponent("gui." + (value ? "yes" : "no")));
        this.button.render(stack, mouseX, mouseY, partialTicks);
    }

    @Override
    public IGuiEventListener getListener() {
        return button;
    }
}
