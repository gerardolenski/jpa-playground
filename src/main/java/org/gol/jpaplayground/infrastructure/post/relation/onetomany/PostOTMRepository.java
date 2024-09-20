package org.gol.jpaplayground.infrastructure.post.relation.onetomany;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface PostOTMRepository extends JpaRepository<PostOTMEntity, UUID> {
}
