package HKMS.app;

import HKSM.data.SaveLoader;
import com.google.gson.JsonObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class LocalTest {

    private static final Logger log = LoggerFactory.getLogger(LocalTest.class);

    @Test
    public void testOnMyLocalInstall() throws Exception {
        String userName = System.getProperty("user.name");

        File saveFolder = new File("C:\\Users\\" + userName + "\\AppData\\LocalLow\\Team Cherry\\Hollow Knight");

        log.info("folder exists: " + saveFolder.exists());

        File saveFile = new File(saveFolder, "user1.dat");

        log.info("file exists: " + saveFile.exists());

        JsonObject saveData = SaveLoader.loadSave(saveFile);

        JsonObject playerData = saveData.get("playerData").getAsJsonObject();
        log.info("player data:\n{}", playerData);

//        SaveLoader.saveSave(saveFile, saveData);
    }

}
