package br.com.speedup.attributes;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Attribute {

    private String name;
    private List<String> terms;

    public void setName(String name) {
        this.name = name;
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
    }

    public String getName() {
        return name;
    }

    public List<String> getTerms() {
        return terms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attribute = (Attribute) o;
        return Objects.equals(name, attribute.name) &&
                Objects.equals(terms, attribute.terms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, terms);
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "name='" + name + '\'' +
                ", terms=" + terms +
                '}';
    }
}
