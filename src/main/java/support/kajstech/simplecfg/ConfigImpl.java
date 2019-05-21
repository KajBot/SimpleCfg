package support.kajstech.simplecfg;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigImpl implements SimpleCfg {

    protected final File path;
    protected final Format format;
    protected final File fallback;
    private Properties props = new Properties();

    public ConfigImpl(File path,
                      Format format,
                      File fallback) {
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
    public void set(String key, String value) {
        props.setProperty(key, value);
        save();
    }

    @Override
    public boolean contains(String key) {
        return props.containsKey(key) && !get(key).isEmpty();
    }

    @Override
    public void save() {
        try {
            if (format == Format.XML) props.storeToXML(new FileOutputStream(path + "." + format.toString()), null);
            if (format == Format.PROPERTIES)
                props.store(new OutputStreamWriter(new FileOutputStream(path + "." + format.toString()), StandardCharsets.UTF_8), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        try {
            if (format == Format.XML) props.loadFromXML(new FileInputStream(path + "." + format.toString()));
            if (format == Format.PROPERTIES)
                props.load(new BufferedReader(new InputStreamReader(new FileInputStream(path + "." + format.toString()), StandardCharsets.UTF_8)));
        } catch (FileNotFoundException e) {
            if (fallback != null && !path.exists()) {
                try {
                    Files.copy(ClassLoader.getSystemResourceAsStream(fallback.getName()), Paths.get(path.getAbsoluteFile().toPath() + ".properties"));
                } catch (IOException ignored) { }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
