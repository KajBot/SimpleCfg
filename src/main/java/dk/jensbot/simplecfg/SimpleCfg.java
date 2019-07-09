package dk.jensbot.simplecfg;

import java.util.Properties;

public interface SimpleCfg {

    String get(String key);

    Properties getProps();

    void set(String key, String value);

    void del(String key);

    boolean contains(String key);

    void save();

    void load();
}
