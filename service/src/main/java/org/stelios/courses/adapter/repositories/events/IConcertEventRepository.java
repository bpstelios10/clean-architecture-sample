package org.stelios.courses.adapter.repositories.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IConcertEventRepository extends JpaRepository<ConcertEventJpaMapper, String> {
    List<ConcertEventJpaMapper> findAll();

    Optional<ConcertEventJpaMapper> findById(String id);
}
