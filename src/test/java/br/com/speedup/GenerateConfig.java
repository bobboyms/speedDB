package br.com.speedup;

import br.com.speedup.config.Config;
import br.com.speedup.config.ConfigFactory;

public class GenerateConfig {

    public static Config getConfig() {

        if (System.getProperty("user.dir").equals("/")) {
            return ConfigFactory.create(new String[]{"CONTAINER"});
        } else {
            return ConfigFactory.create(new String[]{"LINUX"});
        }
    }

}
