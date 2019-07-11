package dk.jensbot.simplecfg.formats;

import org.json.JSONObject;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;
import java.util.Properties;

public class YAML {
    public static Properties toProps(String yamlString) {
        Yaml yaml = new Yaml();
        Map<String, Object> map = yaml.load(yamlString);
        Properties props = new Properties();
        props.putAll(new JSONObject(map).toMap());
        return props;
    }

    public static String toYAML(Properties props) {
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        Map<String, Object> map = new Yaml().load(JSON.toJson(props).toString(2));
        return yaml.dump(map);
    }

}
