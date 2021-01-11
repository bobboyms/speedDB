package br.com.speedup.attributes;

import br.com.speedup.config.Config;
import br.com.speedup.threads.LocalProcess;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import netscape.javascript.JSObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StorageAttribute extends LocalProcess implements Storage {

    private final Config config;
    private final String collectionName;
    private final ObjectMapper objectMapper;

    public StorageAttribute(Config config, String collectionName) {
        this.config = config;
        this.collectionName = collectionName;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void onReceiveMessage(Object message) throws IOException {
        persist((Attribute) message);
    }

    @Override
    public void persist(Attribute attribute) {
        try {
            final var json = objectMapper.writeValueAsString(attribute);
            Files.write(Paths.get(config.getDbRoot()+collectionName+"/"+attribute.getName()), json.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    private byte[] serialize(Object obj) throws IOException {
////        ByteArrayOutputStream out = new ByteArrayOutputStream();
////        ObjectOutputStream os = new ObjectOutputStream(out);
////        os.writeObject(obj);
////        return out.toByteArray();
//        return objectMapper.writeValueAsString(obj).getBytes();
//
//    }

}
