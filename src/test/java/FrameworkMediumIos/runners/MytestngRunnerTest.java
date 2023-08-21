package FrameworkMediumIos.runners;


import FrameworkMediumIos.utils.PropertyManager;
import io.cucumber.testng.*;
import org.apache.logging.log4j.ThreadContext;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;
import FrameworkMediumIos.utils.DriverManager;
import FrameworkMediumIos.utils.GlobalParams;
import FrameworkMediumIos.utils.ServerManager;

import java.io.IOException;
import java.util.Properties;

@CucumberOptions(plugin = {
        "pretty",
        "html:test-output/reports/cucumber/report.html",
        "json:test-output/reports/cucumber/report.json",
        "junit:test-output/reports/cucumber/report.xml",
        "summary",
        //"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
},
        features = {"src/test/resources"},
        glue = "FrameworkMediumIos.stepdef",
        tags = "@test",
        monochrome = false
)
public class MytestngRunnerTest {
    private static final ThreadLocal<TestNGCucumberRunner> testNGCucumberRunner = new ThreadLocal<>();
    public static TestNGCucumberRunner getRunner(){
        return testNGCucumberRunner.get();
    }
    private static void setRunner(TestNGCucumberRunner testNGCucumberRunner1){
        testNGCucumberRunner.set(testNGCucumberRunner1);
    }
    @Parameters({"platformName", "udid", "deviceName", "virtualDevice","wdaLocalPort", "webkitDebugProxyPort"})
    @BeforeClass(alwaysRun = true)
    public void setUpClass(ITestContext context, String platformName, String udid, String deviceName, String virtualDevice,
                           @Optional("iOS")String wdaLocalPort,
                           @Optional("iOS")String webkitDebugProxyPort) throws IOException {
        ThreadContext.put("ROUTINGKEY", platformName + "_"
                + deviceName);
        GlobalParams params = new GlobalParams();
        Properties props = new PropertyManager().getProps();
        params.setPlatformName(platformName);
        params.setUDID(udid);
        params.setDeviceName(deviceName);
        params.setVirtualDevice(virtualDevice);
        params.setWdaLocalPort(wdaLocalPort);
        params.setWebkitDebugProxyPort(webkitDebugProxyPort);
        if(params.getVirtualDevice().equalsIgnoreCase("true")){
            Runtime.getRuntime().exec("xcrun simctl uninstall " + params.getUDID() + " " + props.getProperty("iOSBundleId"));

        }else if(params.getVirtualDevice().equalsIgnoreCase("false")){
            Runtime.getRuntime().exec("ideviceinstaller -U " + props.getProperty("iOSBundleId"));
        }
        new ServerManager().startServer();
        new DriverManager().initializeDriver();

        XmlTest currentXmlTest = context.getCurrentXmlTest();
        CucumberPropertiesProvider properties = currentXmlTest::getParameter;
        setRunner(new TestNGCucumberRunner(this.getClass(), properties));
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void scenario(PickleWrapper pickle, FeatureWrapper cucumberFeature) {
        getRunner().runScenario(pickle.getPickle());
    }

    @DataProvider
    public Object[][] scenarios() {
        return getRunner().provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws IOException {
        GlobalParams params = new GlobalParams();
        Properties props = new PropertyManager().getProps();
        DriverManager driverManager =new DriverManager();
        if(params.getVirtualDevice().equalsIgnoreCase("true")){
            Runtime.getRuntime().exec("xcrun simctl uninstall " + params.getUDID() + " " + props.getProperty("iOSBundleId"));

        }else if(params.getVirtualDevice().equalsIgnoreCase("false")){
            Runtime.getRuntime().exec("ideviceinstaller -U " + props.getProperty("iOSBundleId"));
        }
        if (driverManager.getDriver() != null) {
            driverManager.getDriver().quit();
            driverManager.setDriver(null);

        }
        ServerManager serverManager =new ServerManager();
        if (serverManager.getServer() != null) {
            serverManager.getServer().stop();

        }
        if (testNGCucumberRunner != null) {
            getRunner().finish();

        }
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        if (isWindows){
            Runtime.getRuntime().exec(String.format("cmd.exe /c allure generate --clean Test-output/reports/allure-results -o Test-output/reports/allure-report"));
        } else {
            Runtime.getRuntime().exec("allure generate --clean Test-output/reports/allure-results -o Test-output/reports/allure-report");
        }
    }
}
