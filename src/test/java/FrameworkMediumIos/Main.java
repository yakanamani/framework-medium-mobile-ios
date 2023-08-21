package FrameworkMediumIos;

import org.testng.TestNG;
import FrameworkMediumIos.utils.GlobalParams;
import FrameworkMediumIos.utils.PropertyManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class Main {
    public static void main(String[] args) throws IOException {
        GlobalParams params = new GlobalParams();
        Properties props = new PropertyManager().getProps();
        List<String> testFilesList = new ArrayList<>();
        testFilesList.add("testng.xml");
        TestNG testng = new TestNG();
        testng.setTestSuites(testFilesList);
        testng.run();

    }
}
