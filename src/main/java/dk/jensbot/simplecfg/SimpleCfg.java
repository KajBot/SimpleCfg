package dk.jensbot.simplecfg;

public interface SimpleCfg {

    String get(String key);

    void set(String key, String value);

    boolean contains(String key);

    void save();

    void load();

}
