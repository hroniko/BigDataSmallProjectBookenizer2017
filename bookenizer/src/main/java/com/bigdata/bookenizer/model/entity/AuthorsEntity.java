package com.bigdata.bookenizer.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "authors", schema = "public", catalog = "books")
public class AuthorsEntity {
    private long id;
    private String fio;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "fio", nullable = true, length = 70)
    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorsEntity that = (AuthorsEntity) o;
        return id == that.id &&
                Objects.equals(fio, that.fio);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, fio);
    }
}
