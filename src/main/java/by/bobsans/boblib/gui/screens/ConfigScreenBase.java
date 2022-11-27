package by.bobsans.boblib.gui.screens;

import by.bobsans.boblib.Reference;
import by.bobsans.boblib.gui.widgets.OptionsListWidget;
import by.bobsans.boblib.gui.widgets.value.OptionsEntryValue;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public abstract class ConfigScreenBase extends Screen {
    private final Runnable saver;
    private final Runnable canceller;

    private OptionsListWidget options;
    private OptionsListWidget.Entry lastSelected = null;
    private int tooltipCounter = 0;

    public ConfigScreenBase(String languageKeyPrefix, Runnable saver, Runnable canceller) {
        super(Component.translatable(languageKeyPrefix + ".config.title"));
        this.saver = saver;
        this.canceller = canceller;
    }

    public ConfigScreenBase(String languageKeyPrefix) {
        this(languageKeyPrefix, null, null);
    }

    public ConfigScreenBase() {
        this(Reference.MODID, null, null);
    }

    @Override
    protected void init() {
        options = fillOptions(new OptionsListWidget(this, minecraft, width, height, 32, height - 32, 28));

        addRenderableWidget(options);
        setFocused(options);

        addRenderableWidget(new Button(width / 2 - 100, height - 25, 100, 20, Component.translatable("gui.done"), (w) -> {
            options.save();
            if (saver != null) {
                saver.run();
            }
            onClose();
        }));

        addRenderableWidget(new Button(width / 2 + 5, height - 25, 100, 20, Component.translatable("gui.cancel"), (w) -> {
            if (canceller != null) {
                canceller.run();
            }
            onClose();
        }));
    }

    @Override
    public void render(@NotNull PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(stack);

        options.render(stack, mouseX, mouseY, partialTicks);

        drawCenteredString(stack, font, title, width / 2, 12, 16777215);

        super.render(stack, mouseX, mouseY, partialTicks);

        if (mouseY < 32 || mouseY > height - 32) {
            return;
        }

        OptionsListWidget.Entry entry = options.getSelected();
        if (entry instanceof OptionsEntryValue<?> value) {
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

            if (I18n.exists(value.getDescription())) {
                int valueX = value.getX() + 10;
                Component title = value.getTitle();
                if (mouseX < valueX || mouseX > valueX + font.width(title)) {
                    return;
                }

                if (minecraft != null) {
                    renderTooltip(stack, minecraft.font.split(Component.translatable(value.getDescription()), 200), mouseX, mouseY);
                }
            }
        }
    }

    @Override
    public void mouseMoved(double x, double y) {
        options.mouseMoved(x, y);
    }

    public void addListener(AbstractWidget listener) {
        addWidget(listener);
    }

    protected abstract OptionsListWidget fillOptions(OptionsListWidget widget);
}
