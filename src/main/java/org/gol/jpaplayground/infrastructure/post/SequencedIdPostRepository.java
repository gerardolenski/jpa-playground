package org.gol.jpaplayground.infrastructure.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SequencedIdPostRepository extends JpaRepository<SequencedIdPostEntity, Long> {
}
