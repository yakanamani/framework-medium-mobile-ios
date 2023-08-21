package FrameworkMediumIos.utils;

public class GlobalParams {
    private static ThreadLocal<String> platformName = new ThreadLocal<String>();
    private static ThreadLocal<String> udid = new ThreadLocal<String>();
    private static ThreadLocal<String> deviceName = new ThreadLocal<String>();
    private static ThreadLocal<String> virtualDevice = new ThreadLocal<String>();

    private static ThreadLocal<String> wdaLocalPort = new ThreadLocal<String>();
    private static ThreadLocal<String> webkitDebugProxyPort = new ThreadLocal<String>();

    public void setPlatformName(String platformName1){
        platformName.set(platformName1);
    }

    public String getPlatformName(){
        return platformName.get();
    }

    public String getUDID() {
        return udid.get();
    }

    public void setUDID(String udid2) {
        udid.set(udid2);
    }

    public String getDeviceName() {
        return deviceName.get();
    }

    public void setDeviceName(String deviceName2) {
        deviceName.set(deviceName2);
    }

    public String  getVirtualDevice() {return virtualDevice.get();}

    public void setVirtualDevice(String virtualDevice2) { virtualDevice.set(virtualDevice2);}

    public String getWdaLocalPort() {
        return wdaLocalPort.get();
    }

    public void setWdaLocalPort(String wdaLocalPort2) {
        wdaLocalPort.set(wdaLocalPort2);
    }

    public String getWebkitDebugProxyPort() {
        return webkitDebugProxyPort.get();
    }

    public void setWebkitDebugProxyPort(String webkitDebugProxyPort2) {
        webkitDebugProxyPort.set(webkitDebugProxyPort2);
    }

    public void initializeGlobalParams(){
        GlobalParams params = new GlobalParams();
        params.setPlatformName(System.getProperty("platformName", "iOS"));
        params.setUDID(System.getProperty("udid", "B6B097FE-864D-483D-861C-21726B44E5B1"));
        params.setDeviceName(System.getProperty("deviceName", "Nana"));
        params.setWdaLocalPort(System.getProperty("wdaLocalPort", "10001"));
        params.setWebkitDebugProxyPort(System.getProperty("webkitDebugProxyPort", "11001"));

    }
}
