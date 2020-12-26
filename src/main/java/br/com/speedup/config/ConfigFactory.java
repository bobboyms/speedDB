package br.com.speedup.config;

import br.com.speedup.exception.NotFoundArgsException;

import java.util.Objects;

public class ConfigFactory {

    private ConfigFactory(){}

    public static Config create(String[] args) {

        if (Objects.nonNull(args) && args.length > 0) {

            if (args[0].equals("CONTAINER")) {
                System.out.println("CREATE CONTAINER CONFIG");
                return new ConfigContainer();
            }

            if (args[0].equals("LINUX")) {
                return new ConfigLinux();
            }

            throw new NotFoundArgsException("Argument is not valid");
        }

        return new ConfigLinux();

    }

}
