package org.lenteam.colmen.repositories;

import org.lenteam.colmen.entities.PersonPageRankEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rinat
 */
@Repository
public interface PersonPageRankRepository extends CrudRepository<PersonPageRankEntity, Integer> {
}
