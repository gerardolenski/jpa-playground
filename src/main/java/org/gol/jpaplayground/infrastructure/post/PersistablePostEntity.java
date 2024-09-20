package org.gol.jpaplayground.infrastructure.post;

import org.gol.jpaplayground.infrastructure.entity.BasePersistableEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import static java.util.UUID.randomUUID;

@Entity
@Getter
@Setter
@Table(name = "post_persist")
@NoArgsConstructor
class PersistablePostEntity extends BasePersistableEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "author", length = 30)
    private String author;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "topic", length = 100)
    private String topic;

    @Column(name = "content", length = 10000)
    private String content;

    PersistablePostEntity(@NonNull String author, @NonNull String category, @NonNull String topic, @NonNull String content) {
        var now = LocalDateTime.now();
        this.id = randomUUID();
        this.creationDate = now.toLocalDate();
        this.creationTime = now;
        this.author = author;
        this.category = category;
        this.topic = topic;
        this.content = content;
    }
}
