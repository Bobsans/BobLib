package by.bobsans.boblib.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

@Deprecated
public abstract class ConfigBase {
    @Deprecated
    protected static void loadConfig(ForgeConfigSpec spec, Path path) {
        CommentedFileConfig configData = CommentedFileConfig
            .builder(path)
            .sync()
            .autosave()
            .autoreload()
            .writingMode(WritingMode.REPLACE)
            .build();

        configData.load();
        spec.setConfig(configData);
    }
}
