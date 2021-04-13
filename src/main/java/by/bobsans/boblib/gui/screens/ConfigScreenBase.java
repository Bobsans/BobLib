package by.bobsans.boblib.gui.screens;

import by.bobsans.boblib.Reference;
import by.bobsans.boblib.gui.widgets.OptionsListWidget;
import by.bobsans.boblib.gui.widgets.value.OptionsEntryValue;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.ParametersAreNonnullByDefault;

public abstract class ConfigScreenBase extends ScreenBase {
    private final Runnable saver;
    private final Runnable canceller;

    private OptionsListWidget options;
    private OptionsListWidget.Entry lastSelected = null;
    private int tooltipCounter = 0;

    public ConfigScreenBase(Screen parent, String languageKeyPrefix, Runnable saver, Runnable canceller) {
        super(parent, new TranslationTextComponent(languageKeyPrefix + ".config.title"));
        this.saver = saver;
        this.canceller = canceller;
    }

    public ConfigScreenBase(Screen parent, String languageKeyPrefix) {
        this(parent, languageKeyPrefix, null, null);
    }

    public ConfigScreenBase(Screen parent) {
        this(parent, Reference.MODID, null, null);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);

        options = fillOptions(new OptionsListWidget(this, minecraft, width, height, 32, height - 32, 28));

        children.add(options);
        setFocused(options);

        addButton(new Button(width / 2 - 100, height - 25, 100, 20, new TranslationTextComponent("gui.done"), (w) -> {
            options.save();
            if (saver != null) {
                saver.run();
            }
            onClose();
        }));

        addButton(new Button(width / 2 + 5, height - 25, 100, 20, new TranslationTextComponent("gui.cancel"), (w) -> {
            if (canceller != null) {
                canceller.run();
            }
            onClose();
        }));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(stack);

        options.render(stack, mouseX, mouseY, partialTicks);

        drawCenteredString(stack, font, title, width / 2, 12, 16777215);

        super.render(stack, mouseX, mouseY, partialTicks);

        if (mouseY < 32 || mouseY > height - 32) {
            return;
        }

        OptionsListWidget.Entry entry = options.getSelected();
        if (entry instanceof OptionsEntryValue) {
            if (lastSelected == entry) {
                if (tooltipCounter > 0) {
                    tooltipCounter--;
                    return;
                }
            } else {
                lastSelected = entry;
                tooltipCounter = 100;
                return;
            }

            OptionsEntryValue<?> value = (OptionsEntryValue<?>) entry;

            if (I18n.exists(value.getDescription())) {
                int valueX = value.getX() + 10;
                ITextComponent title = value.getTitle();
                if (mouseX < valueX || mouseX > valueX + font.width(title)) {
                    return;
                }

                if (minecraft != null) {
                    renderTooltip(stack, minecraft.font.split(new TranslationTextComponent(value.getDescription()), 200), mouseX, mouseY);
                }
            }
        }
    }

    @Override
    public void mouseMoved(double x, double y) {
        options.mouseMoved(x, y);
    }

    public void addListener(IGuiEventListener listener) {
        children.add(listener);
    }

    protected abstract OptionsListWidget fillOptions(OptionsListWidget widget);
}
