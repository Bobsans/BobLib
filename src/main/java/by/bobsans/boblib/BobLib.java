package by.bobsans.boblib;

import by.bobsans.boblib.network.NetworkingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(Reference.MODID)
public class BobLib {
    public BobLib() {
        MinecraftForge.EVENT_BUS.register(this);
        NetworkingManager.init();
    }
}
