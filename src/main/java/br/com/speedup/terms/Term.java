package br.com.speedup.terms;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Objects;

public class Term implements Serializable {

    private Long id;
    private Object value;
    private Boolean aNew = false;

    public void setValue(Object value) {
        this.value = value;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setaNew(Boolean aNew) {
        this.aNew = aNew;
    }

    @Transient
    public Boolean getaNew() {
        return aNew;
    }

    public Long getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term term = (Term) o;
        return Objects.equals(id, term.id) &&
                Objects.equals(value, term.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }

    @Override
    public String toString() {
        return "Term{" +
                "id=" + id +
                ", value=" + value +
                ", aNew=" + aNew +
                '}';
    }
}
