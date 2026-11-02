package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:local.properties",
        "classpath:remote.properties"
})
public interface WebDriverConfig extends Config {

    @Key("browser.name")
    @DefaultValue("CHROME")
    Browser getBrowser();

    @Key("browser.version")
    @DefaultValue("latest")
    String getBrowserVersion();

    @Key("run.type")
    @DefaultValue("local")
    String getRunType();

    @Key("base.url")
    @DefaultValue("https://www.google.com")
    String getBaseUrl();

    @Key("remote.url")
    @DefaultValue("http://localhost:4444/wd/hub")
    String getRemoteUrl();
}