package dk.jensbot.simplecfg;

import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigImpl implements SimpleCfg {

    protected final File path;
    protected final Format format;
    protected final File fallback;
    private Properties props = new Properties();

    public ConfigImpl(File path, Format format, File fallback) {
        this.path = path;
        this.format = format;
        this.fallback = fallback;
    }

    @Override
    public String get(String key) {
        load();
        return props.getProperty(key) == null ? "Missing property" : props.getProperty(key);
    }

    @Override
    public Properties getProps() {
        return props;
    }

    @Override
    public void set(String key, String value) {
        props.setProperty(key, value);
        save();
    }

    @Override
    public void del(String key) {
        props.remove(key);
        save();
    }

    @Override
    public boolean contains(String key) {
        return props.containsKey(key) && !get(key).isEmpty();
    }

    @Override
    public void save() {
        try {
            if (format == Format.XML) props.storeToXML(new FileOutputStream(path), null);
            if (format == Format.PROPERTIES)
                props.store(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8), null);
            if (format == Format.JSON) {
                try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8)) {
                    writer.write(JSON.toJson(props).toString(2));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        if (fallback != null && !path.exists()) {
            try {
                Files.copy(ClassLoader.getSystemResourceAsStream(fallback.getName()), Paths.get(path.toString()));
            } catch (IOException ignored) {
            }
        }
        try {
            if (format == Format.XML) props.loadFromXML(new FileInputStream(path));
            if (format == Format.PROPERTIES)
                props.load(new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8)));
            if (format == Format.JSON) {
                String jsonString = new String(Files.readAllBytes(Paths.get(path.toURI())), StandardCharsets.UTF_8);
                props.putAll(JSON.toProps(new JSONObject(jsonString)));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
