package br.com.speedup.terms;

import br.com.speedup.config.ConfigFactory;
import br.com.speedup.threads.CreatedProcess;
import br.com.speedup.threads.LocalProcess;
import br.com.speedup.threads.ProcessFactory;

import java.util.*;
import java.util.stream.Collectors;

public class TermManager {

    private static Long termId = 0l;
    protected static final Map<String, Term> TERMS = new HashMap<>();

    private final CreatedProcess createdProcess;

    public TermManager(Storage storage) {
        this.createdProcess = ProcessFactory.
                createdProcess((LocalProcess) storage);
    }

    public List<Term> generateTerms(final String document) {
        return Arrays.asList(document.split(" ")).
                parallelStream().
                map(term -> {
                    final var strLocalTerm = term.trim().toLowerCase();
                    final var objTerm = Optional.ofNullable(TERMS.get(strLocalTerm));

                    if (objTerm.isPresent()) {
                        var tempObjterm = objTerm.get();
                        tempObjterm.setaNew(false);
                        return tempObjterm;
                    }

                    final var temObjTerm = new Term(++termId, strLocalTerm);
                    temObjTerm.setaNew(true);
                    TERMS.put(strLocalTerm, temObjTerm);

                    return temObjTerm;
                }).
                collect(Collectors.toList());
    }

    public void persistTerms(final List<Term> terms) {
        createdProcess.sendMessage(terms.stream().
                filter(Term::getaNew).
                collect(Collectors.toList()));
    }

    public static void main(String[] args) {

        List<String> docs = Arrays.asList(
                "Tatiane foi almoçar com Isabella",
                "Thiago e Tatiane são pais de Isabella",
                "Isabella foi tomar açai com Thiago seu pai");


        TermManager termManager = new TermManager(new StorageTerm(ConfigFactory.create(new String[]{""})));

        docs.forEach(doc ->{
            var list = termManager.generateTerms(doc);
            termManager.persistTerms(list);
        });


        TERMS.forEach((s, term) -> {
            System.out.println(term);
        });

        System.out.println(TERMS.size());

    }

}
