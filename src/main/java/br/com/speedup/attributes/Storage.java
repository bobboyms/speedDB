package br.com.speedup.attributes;

import br.com.speedup.terms.Term;

import java.io.IOException;
import java.util.List;

public interface Storage {

    void persist(Attribute attribute) throws IOException;

}
