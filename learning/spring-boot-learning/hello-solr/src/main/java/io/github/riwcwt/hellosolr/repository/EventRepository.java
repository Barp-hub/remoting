package io.github.riwcwt.hellosolr.repository;

import io.github.riwcwt.hellosolr.document.Events;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface EventRepository extends SolrCrudRepository<Events, String> {

    @Query(value = "_text_:?0")
    List<Events> search(String content, Pageable page);

}
