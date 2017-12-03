package com.bigdata.bookenizer.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "public", catalog = "books")
public class UsersEntity {
    private long id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;
    private String mail;
    private Integer age;
    private Long idcountry;

    public UsersEntity() {
    }

    public UsersEntity(String login, String password, String firstname, String lastname, String mail, Integer age, Long idcountry) {
        this.login = login;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.age = age;
        this.idcountry = idcountry;
    }

    public UsersEntity(long id, String login, String password, String firstname, String lastname, String mail, Integer age, Long idcountry) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.age = age;
        this.idcountry = idcountry;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "login", nullable = true, length = 10)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 8)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "firstname", nullable = true, length = 10)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "lastname", nullable = true, length = 15)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "mail", nullable = true, length = 30)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Basic
    @Column(name = "age", nullable = true)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }



    @Basic
    @Column(name = "idcountry", nullable = true)
    // @OneToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "idcountry", nullable = true)
    public Long getIdcountry() {
        return idcountry;
    }

    public void setIdcountry(Long idcountry) {
        this.idcountry = idcountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return
                Objects.equals(login, that.login) &&
                Objects.equals(password, that.password) &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(mail, that.mail) &&
                Objects.equals(age, that.age) &&
                Objects.equals(idcountry, that.idcountry);
    }

    @Override
    public int hashCode() {

        return Objects.hash(login, password, firstname, lastname, mail, age, idcountry);
    }


}
