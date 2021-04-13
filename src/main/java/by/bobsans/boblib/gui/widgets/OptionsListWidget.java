package by.bobsans.boblib.gui.widgets;

import by.bobsans.boblib.gui.screens.ConfigScreenBase;
import by.bobsans.boblib.gui.widgets.value.OptionsEntryValue;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.list.AbstractList;

public class OptionsListWidget extends AbstractList<OptionsListWidget.Entry> {
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

    public void add(Entry entry) {
        if (entry instanceof OptionsEntryValue) {
            IGuiEventListener element = ((OptionsEntryValue<?>) entry).getListener();
            if (element != null) {
                owner.addListener(element);
            }
        }
        addEntry(entry);
    }

    public abstract static class Entry extends AbstractList.AbstractListEntry<Entry> {
        protected Entry() {}

        @Override
        public abstract void render(MatrixStack stack, int index, int rowTop, int rowLeft, int width, int height, int mouseX, int mouseY, boolean hovered, float deltaTime);
    }
}
