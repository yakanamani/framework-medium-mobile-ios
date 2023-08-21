package FrameworkMediumIos.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import FrameworkMediumIos.utils.TestUtils;

public class MenuPage extends BasePage{
	TestUtils utils = new TestUtils();
	
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView\n" + 
			"") 
	@iOSXCUITFindBy (xpath="//XCUIElementTypeOther[@name=\"test-Menu\"]/XCUIElementTypeOther")
	private WebElement settingsBtn;
	
	public SettingsPage pressSettingsBtn() {
		click(settingsBtn, "press Settings button");
		return new SettingsPage();
	}

}
