package io.leaderli.demo;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.YAMLConfiguration;
import org.apache.commons.configuration2.convert.DefaultConversionHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.interpol.ConfigurationInterpolator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestCommonsConfiguration {

    @Test
    public void test() {
//        Configurations configs = new Configurations();
//        try {
//            Configuration config = configs.properties("application.properties");
//            String property = config.getString("database.timeout", "110");
//            System.out.println(property);
//            property = config.getString("database.1timeout", "110");
//            System.out.println(property);
//            System.out.println(config.getString("a"));
//            System.out.println(config.getString("b"));
//            // access configuration properties
//        } catch (ConfigurationException cex) {
//            // Something went wrong
//        }


        YAMLConfiguration yaml = loads("test.yml","test_2.yml");
        yaml.setConversionHandler(new DefaultConversionHandler(){

            @Override
            public <T> T to(Object src, Class<T> targetCls, ConfigurationInterpolator ci) {
                System.out.println(src+"\t"+targetCls+"\t"+ci);
                return super.to(src, targetCls, ci);
            }
        });


        Configuration obj = yaml.subset("obj");
        System.out.println(obj.getString("o1"));


        System.out.println(yaml.get(Object.class,"obj"));
        System.out.println(Arrays.toString(yaml.get(int[].class, "arr")));

    }

    private YAMLConfiguration loads(String... paths) {
        YAMLConfiguration yamlConfiguration = new YAMLConfiguration();
        Map<String, Object> properties = new HashMap<>();

        for (String path : paths) {
            try {
                yamlConfiguration.read(ClassLoader.getSystemResource(path).openStream());
                yamlConfiguration.getKeys().forEachRemaining(k -> properties.put(k, yamlConfiguration.getProperty(k)));

            } catch (ConfigurationException | IOException e) {
                e.printStackTrace();
            }
        }

        properties.forEach(yamlConfiguration::setProperty);

        return yamlConfiguration;


    }
}
