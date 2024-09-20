package org.gol.jpaplayground.infrastructure.post.relation.manytoone;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface PostMTORepository extends JpaRepository<PostMTOEntity, UUID> {
}
