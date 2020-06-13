package by.bobsans.boblib.gui.screens;

import by.bobsans.boblib.Reference;
import by.bobsans.boblib.gui.widgets.OptionsListWidget;
import by.bobsans.boblib.gui.widgets.value.OptionsEntryValue;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

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

        addButton(new Button(width / 2 - 100, height - 25, 100, 20, I18n.format("gui.done"), w -> {
            options.save();
            if (saver != null) {
                saver.run();
            }
            onClose();
        }));

        addButton(new Button(width / 2 + 5, height - 25, 100, 20, I18n.format("gui.cancel"), w -> {
            if (canceller != null) {
                canceller.run();
            }
            onClose();
        }));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        renderBackground();

        options.render(mouseX, mouseY, partialTicks);

        drawCenteredString(font, title.getFormattedText(), width / 2, 12, 16777215);

        super.render(mouseX, mouseY, partialTicks);

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

            if (I18n.hasKey(value.getDescription())) {
                int valueX = value.getX() + 10;
                String title = value.getTitle().getFormattedText();
                if (mouseX < valueX || mouseX > valueX + font.getStringWidth(title)) {
                    return;
                }

                List<String> tooltip = Lists.newArrayList(title);
                tooltip.addAll(font.listFormattedStringToWidth(I18n.format(value.getDescription()), 200));
                renderTooltip(tooltip, mouseX, mouseY);
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
