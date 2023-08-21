package FrameworkMediumIos.utils;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.util.Properties;

public class CapabilitiesManager {
    TestUtils utils = new TestUtils();

    public DesiredCapabilities getCaps() throws IOException {
        GlobalParams params = new GlobalParams();
        Properties props = new PropertyManager().getProps();
        try {
            utils.log().info("getting capabilities");
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME, params.getPlatformName());
            caps.setCapability(MobileCapabilityType.UDID, params.getUDID());
            caps.setCapability(MobileCapabilityType.DEVICE_NAME, params.getDeviceName());
            caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, props.getProperty("iOSAutomationName"));
            //String iOSAppUrl = getClass().getResource(props.getProperty("iOSAppLocation")).getFile();
            String iOSAppUrl = System.getProperty("user.dir")+ props.getProperty("iOSAppLocation");
            utils.log().info("appUrl is" + iOSAppUrl);
            caps.setCapability("bundleId", props.getProperty("iOSBundleId"));
            caps.setCapability("wdaLocalPort", params.getWdaLocalPort());
            caps.setCapability("webkitDebugProxyPort", params.getWebkitDebugProxyPort());
            caps.setCapability("app", iOSAppUrl);
            return caps;
        } catch (Exception e) {
            e.printStackTrace();
            utils.log().fatal("Failed to load capabilities. ABORT!!" + e.toString());
            throw e;        }
    }
}
