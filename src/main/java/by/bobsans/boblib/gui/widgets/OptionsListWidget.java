package by.bobsans.boblib.gui.widgets;

import by.bobsans.boblib.gui.screens.ConfigScreenBase;
import by.bobsans.boblib.gui.widgets.value.OptionsEntryValue;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import org.jetbrains.annotations.NotNull;

public class OptionsListWidget extends AbstractSelectionList<OptionsListWidget.Entry> {
    private final ConfigScreenBase owner;
    private final Runnable diskWriter;

    private OptionsListWidget(ConfigScreenBase owner, Minecraft minecraft, int width, int height, int top, int bottom, int entryHeight, Runnable diskWriter) {
        super(minecraft, width, height, top, bottom, entryHeight);

        this.owner = owner;
        this.diskWriter = diskWriter;
        this.setRenderSelection(false);
        this.setRenderBackground(false);
        this.setRenderTopAndBottom(true);
    }

    public OptionsListWidget(ConfigScreenBase owner, Minecraft client, int width, int height, int top, int bottom, int entryHeight) {
        this(owner, client, width, height, top, bottom, entryHeight, null);
    }

    @Override
    public int getRowWidth() {
        return Math.min(minecraft.getWindow().getGuiScaledWidth(), 500);
    }

    @Override
    protected int getScrollbarPosition() {
        return this.width - 12;
    }

    @Override
    public void mouseMoved(double x, double y) {
        for (Entry child : this.children()) {
            if (child.isMouseOver(x, y)) {
                setSelected(child);
            }
        }
    }

    public void save() {
        children().stream().filter((e) -> e instanceof OptionsEntryValue).map((e) -> (OptionsEntryValue<?>) e).forEach(OptionsEntryValue::save);

        if (diskWriter != null) {
            diskWriter.run();
        }
    }

    public int addEntry(@NotNull Entry entry) {
        if (entry instanceof OptionsEntryValue) {
            AbstractWidget element = ((OptionsEntryValue<?>) entry).getListener();
            if (element != null) {
                owner.addListener(element);
            }
        }
        return super.addEntry(entry);
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput output) {}

    public abstract static class Entry extends AbstractSelectionList.Entry<Entry> {
        protected Entry() {}

        @Override
        public abstract void render(@NotNull PoseStack stack, int index, int rowTop, int rowLeft, int width, int height, int mouseX, int mouseY, boolean hovered, float deltaTime);
    }
}
