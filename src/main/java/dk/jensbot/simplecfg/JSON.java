package dk.jensbot.simplecfg;

import org.json.JSONObject;

import java.util.Properties;

class JSON {
    static Properties toProps(JSONObject json) {
        Properties props = new Properties();
        props.putAll(json.toMap());
        return props;
    }

    static JSONObject toJson(Properties properties) {
        return new JSONObject(properties);
    }

}
