package FrameworkMediumIos.utils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;

public class ServerManager {


    private static final ThreadLocal<AppiumDriverLocalService> server = new ThreadLocal<>();
    TestUtils utils = new TestUtils();

    public static AppiumDriverLocalService getServer() {
        return server.get();
    }

    public void startServer() {
        utils.log().info("starting appium server");
        AppiumDriverLocalService server = GetAppiumService();
        server.clearOutPutStreams();
        server.start();
        if (server == null || !server.isRunning()) {
            utils.log().fatal("Appium server not started. ABORT!!!");
            throw new AppiumServerHasNotBeenStartedLocallyException("Appium server not started. ABORT!!!");
        }
        //server.clearOutPutStreams();
        ServerManager.server.set(server);
        utils.log().info("appium server started");

    }

    public AppiumDriverLocalService getAppiumServerDefault() {
        return AppiumDriverLocalService.buildDefaultService();
    }
    public AppiumDriverLocalService GetAppiumService() {
        GlobalParams params = new GlobalParams();
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withTimeout(Duration.ofSeconds(180))
                .withLogFile(new File("test-output" + File.separator + LocalDate.now() + File.separator
                        + params.getPlatformName() + "_" + params.getDeviceName() + File.separator + "logs"
                        + File.separator + "Server.log")));

    }

}
