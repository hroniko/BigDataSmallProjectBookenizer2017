package com.bigdata.bookenizer.services;

import com.bigdata.bookenizer.model.entity.BooksEntity;
import org.springframework.data.repository.CrudRepository;

public interface BooksEntityDao extends CrudRepository<BooksEntity, Long>  {
}
