package org.gol.jpaplayground.infrastructure.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import javax.persistence.QueryHint;

import static org.hibernate.annotations.QueryHints.FETCH_SIZE;

public interface AutoIdPostRepository extends JpaRepository<AutoIdPostEntity, UUID> {

    @QueryHints(@QueryHint(name = FETCH_SIZE, value = "50"))
    @Query("SELECT p from AutoIdPostEntity p")
    Stream<AutoIdPostEntity> stream();

    @Query("SELECT p.id from AutoIdPostEntity p")
    Slice<UUID> findIds(Pageable pageable);

    @QueryHints(@QueryHint(name = FETCH_SIZE, value = "50"))
    Stream<AutoIdPostEntity> findAllBy();

    @QueryHints(@QueryHint(name = FETCH_SIZE, value = "50"))
    @Query(value = "SELECT p.id from AutoIdPostEntity p")
    Stream<UUID> streamIds();

    @Query("SELECT p.id from AutoIdPostEntity p")
    Set<UUID> getAllIds();
}
