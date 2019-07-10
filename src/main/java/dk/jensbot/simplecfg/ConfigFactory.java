package dk.jensbot.simplecfg;

import java.io.File;

public class ConfigFactory {
    private File cfgPath;
    private Format format;
    private File fallback;

    public ConfigFactory() {
        this(new File("config"));
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
        SimpleCfg cfg = new ConfigImpl(new File(System.getProperty("user.dir") + "/" + cfgPath + "." + format.toString().toLowerCase()), format, fallback);
        cfg.load();
        return cfg;
    }

}
