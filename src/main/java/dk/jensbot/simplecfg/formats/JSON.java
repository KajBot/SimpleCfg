package dk.jensbot.simplecfg.formats;

import org.json.JSONObject;

import java.util.Properties;

public class JSON {
    public static Properties toProps(JSONObject json) {
        Properties props = new Properties();
        props.putAll(json.toMap());
        return props;
    }

    public static JSONObject toJson(Properties properties) {
        return new JSONObject(properties);
    }

}
