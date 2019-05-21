package support.kajstech.simplecfg;

import java.io.File;

public class ConfigFactory {

    protected File cfgPath;
    protected Format format;
    protected File fallback;

    public ConfigFactory() {
        this(new File(System.getProperty("user.dir") + "/config.properties"));
        format(Format.PROPERTIES);
    }

    public ConfigFactory(File path) {
        this.cfgPath = path;
    }

    public ConfigFactory format(Format format) {
        this.format = format;
        return this;
    }

    public ConfigFactory fallback(File fallback) {
        this.fallback = fallback;
        return this;
    }

    public SimpleCfg build() {

        return new ConfigImpl(cfgPath, format, fallback);
    }

}
