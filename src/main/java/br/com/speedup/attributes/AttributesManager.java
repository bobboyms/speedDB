package br.com.speedup.attributes;

import br.com.speedup.config.Config;
import br.com.speedup.terms.Term;
import br.com.speedup.terms.TermManager;
import br.com.speedup.threads.CreatedProcess;
import br.com.speedup.threads.LocalProcess;
import br.com.speedup.threads.ProcessFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AttributesManager {

    private final Config config;
    private final String collectionName;
    private final TermManager termManager;
    private final CreatedProcess createdProcess;
    private final ObjectMapper objectMapper;
    private final Map<String, List<Term>> MAP_ATTIBUTES = new HashMap<>();

    protected Map<String, List<Term>> getMAP_ATTIBUTES() {
        return MAP_ATTIBUTES;
    }

    public AttributesManager(TermManager termManager, String collectionName, Config config, Storage storage) throws IOException {
        this.termManager = termManager;
        this.collectionName = collectionName;
        this.config = config;
        this.objectMapper = new ObjectMapper();
        this.createdProcess = ProcessFactory.
                createdProcess((LocalProcess) storage);
        onLoadAttributes();
    }

    private Object deserialize(byte[] data) throws IOException {
        return objectMapper.readValue(new String(data), Attribute.class);
//        ByteArrayInputStream in = new ByteArrayInputStream(data);
//        ObjectInputStream is = new ObjectInputStream(in);
//        return is.readObject();
    }

    private void onLoadAttributes() throws IOException {
        System.out.println("OnLoad");
        Files.list(Paths.get(config.getDbRoot()+collectionName))
                .forEach(path -> {
                    try {
                        Attribute attribute = (Attribute) deserialize(Files.readAllBytes(path));
                        System.out.println("Encontrou?");
                        System.out.println(attribute);
                        var terms =attribute.getTerms().
                                stream().map(name -> termManager.getTerm(name)).
                                collect(Collectors.toList());
                        MAP_ATTIBUTES.put(attribute.getName(), terms);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void indexAttributes(String jsonDoc) {
        JSONObject jsonObject = new JSONObject(jsonDoc);
        jsonObject.keys().forEachRemaining(key -> {
            try {
                indexAttributes(key, (String) jsonObject.get(key));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void indexAttributes(String key, String value) throws IOException {

        final List<Term> terms = termManager.indexTerms(value);

        if (terms.size() == 0) {
            return;
        }

        var attObject = Optional.ofNullable(MAP_ATTIBUTES.get(key));

        if (attObject.isPresent()) {

            final var tempList = attObject.get();
            final var newList = terms.stream().
                    filter(term -> !tempList.contains(term)).
                    collect(Collectors.toList());

            tempList.addAll(newList);

            var listIdTerms = tempList.stream().
                    map(term -> term.getValue().toString()).
                    collect(Collectors.toList());
            Attribute attribute = new Attribute();
            attribute.setName(key);
            attribute.setTerms(listIdTerms);
            createdProcess.sendMessage(attribute);
        } else {
            MAP_ATTIBUTES.put(key, terms);
            var listIdTerms = terms.stream().
                    map(term -> term.getValue().toString()).
                    collect(Collectors.toList());
            Attribute attribute = new Attribute();
            attribute.setName(key);
            attribute.setTerms(listIdTerms);
            createdProcess.sendMessage(attribute);
        }

    }

}
