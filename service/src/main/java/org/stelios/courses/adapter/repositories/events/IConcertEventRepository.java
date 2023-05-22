package org.stelios.courses.adapter.repositories.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConcertEventRepository extends JpaRepository<ConcertEventJpaMapper, String> {
}
