package br.com.speedup;

import br.com.speedup.attributes.Attribute;
import br.com.speedup.config.Config;
import br.com.speedup.config.ConfigFactory;
import br.com.speedup.terms.TermManager;
import br.com.speedup.threads.CreatedProcess;
import br.com.speedup.threads.LocalProcess;
import br.com.speedup.threads.ProcessFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

class Pthread extends LocalProcess {

    @Override
    public void onReceiveMessage(Object message) {
        System.out.println("Recebido: " + message);
    }

}

class Pthread2 extends LocalProcess {

    private final CreatedProcess createdProcess;

    public Pthread2(CreatedProcess createdProcess) {
        this.createdProcess = createdProcess;
    }

    @Override
    public void onReceiveMessage(Object message) {
        System.out.println("Recebido: " + message);
    }

    @Override
    public void run() {
        while (isRunning) {
            System.out.println("Escrevendo: " + getProcessId());
            createdProcess.sendMessage("Thread 2 escrevendo");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {

    public static void main(String[] args) throws IOException {


//        args = new String[]{"CONTAINER"};
//
//        Config config = ConfigFactory.create(args);
//
//        System.out.println("DB ROOT: " + config.getDbRoot());
//
//        if (!Files.isDirectory(Paths.get(config.getDbRoot()))) {
//            System.out.println("CRIANDO DIRETÓRIO: " + config.getDbRoot());
//            Files.createDirectories(Paths.get(config.getDbRoot()));
//        } else {
//            System.out.println("DIRETÓRIO JA EXITE");
//        }

//        System.out.println("---------------------------");
//        System.out.println(System.getProperty("user.dir"));
//
//        JSONObject obj = new JSONObject("{\"name\": \"John\"}");
//        obj.keys().forEachRemaining(s -> {
//            System.out.println("K: " + s + " : " + obj.get(s));
//        });

        Attribute attribute = new Attribute();
        attribute.setName("Teste");
        attribute.setTerms(Arrays.asList("x","a","c"));
        new ObjectMapper().writeValueAsString(attribute);
        System.out.println(new ObjectMapper().writeValueAsString(attribute));


    }

}
