package br.com.speedup.terms;

import br.com.speedup.attributes.Attribute;
import br.com.speedup.config.Config;
import br.com.speedup.threads.CreatedProcess;
import br.com.speedup.threads.LocalProcess;
import br.com.speedup.threads.ProcessFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class TermManager {

    private static Long termId = 0l;
    protected static final Map<String, Term> TERMS = new HashMap<>();

    private final CreatedProcess createdProcess;
    private final Config config;

    public TermManager(Storage storage, Config config) throws IOException {
        this.config = config;
        onLoadTerms();
        this.createdProcess = ProcessFactory.
                createdProcess((LocalProcess) storage);
    }

//    private static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
//        ByteArrayInputStream in = new ByteArrayInputStream(data);
//        ObjectInputStream is = new ObjectInputStream(in);
//        return is.readObject();
//    }

    private void onLoadTerms() throws IOException {

        Files.list(Paths.get(config.getIndexRoot()))
                .forEach(path -> {
                    try {
//                        Term term = (Term)deserialize(Files.readAllBytes(path));
                        Term term = new ObjectMapper().readValue(new String(Files.readAllBytes(path)), Term.class);
                        TERMS.put((String) term.getValue(), term);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    public Term getTerm(final String name) {
        return TERMS.get(name);
    }

    public List<Term> indexTerms(final String document) {
        List<Term> terms = generateTerms(document);
        persistTerms(terms);
        return terms;
    }

    protected List<Term> generateTerms(final String document) {
        return Arrays.asList(document.split(" ")).
                parallelStream().
                map(term -> {
                    final var strLocalTerm = term.trim().toLowerCase();
                    final var objTerm = Optional.ofNullable(TERMS.get(strLocalTerm));

                    if (objTerm.isPresent()) {
                        var tempObjTerm = objTerm.get();
                        tempObjTerm.setaNew(false);
                        return tempObjTerm;
                    }

                    Term temObjTerm = new Term();
                    temObjTerm.setId(++termId);
                    temObjTerm.setValue(strLocalTerm);
                    temObjTerm.setaNew(true);
                    TERMS.put(strLocalTerm, temObjTerm);

                    return temObjTerm;
                }).
                collect(Collectors.toList());
    }

    protected void persistTerms(final List<Term> terms) {
        createdProcess.sendMessage(terms.stream().
                filter(Term::getaNew).
                collect(Collectors.toList()));
    }

}
