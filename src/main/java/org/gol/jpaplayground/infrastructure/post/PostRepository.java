package org.gol.jpaplayground.infrastructure.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface PostRepository extends JpaRepository<PostEntity, UUID> {


}
