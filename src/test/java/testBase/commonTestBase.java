package testBase;

import org.example.simulator.AppManager;
import org.example.utils.Config;
import org.junit.Before;
import org.junit.BeforeClass;

import java.net.URISyntaxException;

public class commonTestBase {
    static AppManager appManager;
    @Before
    public void prepare() {
        try {
            appManager = new AppManager("login", Config.getScheme(),Config.getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace(); // Handle and log the exception
        }
    }
    public static AppManager getAppManager(){
        return appManager;
    }
}
