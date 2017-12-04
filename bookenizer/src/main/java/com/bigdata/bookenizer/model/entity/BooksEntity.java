package com.bigdata.bookenizer.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "books", schema = "public", catalog = "books")
public class BooksEntity {
    private long id;
    private String name;
    private long idauthor;

    private String author;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "idauthor", nullable = false)
    public long getIdauthor() {
        return idauthor;
    }

    public void setIdauthor(long idauthor) {
        this.idauthor = idauthor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooksEntity that = (BooksEntity) o;
        return id == that.id &&
                idauthor == that.idauthor &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, idauthor);
    }

    @Transient
    public String getAuthor() {
        return author;
    }
    @Transient
    public void setAuthor(String author) {
        this.author = author;
    }


    public BooksEntity() {
    }


    public BooksEntity(long id, String name, long idauthor, String author) {
        this.id = id;
        this.name = name;
        this.idauthor = idauthor;
        this.author = author;
    }
}
