package io.leaderli.demo.yml;

import io.leaderli.demo.bean.Lock;
import org.apache.commons.configuration2.YAMLConfiguration;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class YamlTest {
    @Test
    public void test() {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("application.yml");
        Yaml yaml = new Yaml();
        Map load = yaml.load(inputStream);

        Map lock = (Map) load.get("lock");
        System.out.println(lock);
        yaml = new Yaml();
        String dump = yaml.dump(lock);
        System.out.println("dump = " + dump);

        Lock lockBean = yaml.loadAs(dump, Lock.class);

        System.out.println("lockBean = " + lockBean);
        YAMLConfiguration yamlConfiguration = new YAMLConfiguration();

    }

    @Test
    public void test2() {
        Yaml yaml = new Yaml();
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("use", "fuck");
        System.out.println(yaml.dumpAsMap(map));


    }

    @Test
    public void test3() {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("application.yml");
        Yaml yaml = new Yaml();
        Object load = yaml.load(inputStream);
        System.out.println(load);
    }


}
