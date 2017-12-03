package com.bigdata.bookenizer.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "recomendations", schema = "public", catalog = "books")
public class RecomendationsEntity {
    private Long iduser;
    private Long idbook;

    @Id
    @Basic
    @Column(name = "iduser", nullable = true)
    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }

    @Basic
    @Column(name = "idbook", nullable = true)
    public Long getIdbook() {
        return idbook;
    }

    public void setIdbook(Long idbook) {
        this.idbook = idbook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecomendationsEntity that = (RecomendationsEntity) o;
        return Objects.equals(iduser, that.iduser) &&
                Objects.equals(idbook, that.idbook);
    }

    @Override
    public int hashCode() {

        return Objects.hash(iduser, idbook);
    }
}
