--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.6
-- Dumped by pg_dump version 9.5.6

-- Started on 2017-12-03 20:38:32

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2150 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 183 (class 1259 OID 25425)
-- Name: authors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE authors (
    id integer NOT NULL,
    fio character varying(75)
);


ALTER TABLE authors OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 25422)
-- Name: books; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE books (
    id integer NOT NULL,
    name character varying(25),
    idauthor integer
);


ALTER TABLE books OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 25474)
-- Name: country; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE country (
    id integer NOT NULL,
    name character varying(20)
);


ALTER TABLE country OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 25455)
-- Name: recomendations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE recomendations (
    iduser integer,
    idbook integer,
    id integer NOT NULL
);


ALTER TABLE recomendations OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 25407)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE users (
    id integer NOT NULL,
    login character varying(15),
    password character varying(8),
    firstname character varying(35),
    lastname character varying(35),
    age integer,
    mail character varying(35),
    idcountry integer
);


ALTER TABLE users OWNER TO postgres;

--
-- TOC entry 2151 (class 0 OID 0)
-- Dependencies: 181
-- Name: COLUMN users.password; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN users.password IS '
';


--
-- TOC entry 184 (class 1259 OID 25438)
-- Name: usertags; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE usertags (
    iduser integer,
    idbook integer,
    id integer NOT NULL
);


ALTER TABLE usertags OWNER TO postgres;

--
-- TOC entry 2006 (class 2606 OID 25429)
-- Name: primKeyAuth; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY authors
    ADD CONSTRAINT "primKeyAuth" PRIMARY KEY (id);


--
-- TOC entry 2004 (class 2606 OID 25431)
-- Name: primKeybook; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY books
    ADD CONSTRAINT "primKeybook" PRIMARY KEY (id);


--
-- TOC entry 2014 (class 2606 OID 25461)
-- Name: primRec; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recomendations
    ADD CONSTRAINT "primRec" PRIMARY KEY (id);


--
-- TOC entry 2001 (class 2606 OID 25448)
-- Name: primUser; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT "primUser" PRIMARY KEY (id);


--
-- TOC entry 2016 (class 2606 OID 25478)
-- Name: primcount; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY country
    ADD CONSTRAINT primcount PRIMARY KEY (id);


--
-- TOC entry 2010 (class 2606 OID 25459)
-- Name: primtags; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usertags
    ADD CONSTRAINT primtags PRIMARY KEY (id);


--
-- TOC entry 2011 (class 1259 OID 25473)
-- Name: fki_forBokkRec; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "fki_forBokkRec" ON recomendations USING btree (idbook);


--
-- TOC entry 2007 (class 1259 OID 25446)
-- Name: fki_forBook; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "fki_forBook" ON usertags USING btree (idbook);


--
-- TOC entry 1999 (class 1259 OID 25484)
-- Name: fki_forCount; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "fki_forCount" ON users USING btree (idcountry);


--
-- TOC entry 2002 (class 1259 OID 25437)
-- Name: fki_forKeyBook; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "fki_forKeyBook" ON books USING btree (idauthor);


--
-- TOC entry 2008 (class 1259 OID 25454)
-- Name: fki_forUser; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "fki_forUser" ON usertags USING btree (iduser);


--
-- TOC entry 2012 (class 1259 OID 25467)
-- Name: fki_forUserRec; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "fki_forUserRec" ON recomendations USING btree (iduser);


--
-- TOC entry 2022 (class 2606 OID 25468)
-- Name: forBokkRec; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recomendations
    ADD CONSTRAINT "forBokkRec" FOREIGN KEY (idbook) REFERENCES books(id);


--
-- TOC entry 2019 (class 2606 OID 25441)
-- Name: forBook; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usertags
    ADD CONSTRAINT "forBook" FOREIGN KEY (idbook) REFERENCES books(id);


--
-- TOC entry 2017 (class 2606 OID 25479)
-- Name: forCount; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT "forCount" FOREIGN KEY (idcountry) REFERENCES country(id);


--
-- TOC entry 2018 (class 2606 OID 25432)
-- Name: forKeyBook; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY books
    ADD CONSTRAINT "forKeyBook" FOREIGN KEY (idauthor) REFERENCES authors(id);


--
-- TOC entry 2020 (class 2606 OID 25449)
-- Name: forUser; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usertags
    ADD CONSTRAINT "forUser" FOREIGN KEY (iduser) REFERENCES users(id);


--
-- TOC entry 2021 (class 2606 OID 25462)
-- Name: forUserRec; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recomendations
    ADD CONSTRAINT "forUserRec" FOREIGN KEY (iduser) REFERENCES users(id);


--
-- TOC entry 2149 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2017-12-03 20:38:33

--
-- PostgreSQL database dump complete
--

