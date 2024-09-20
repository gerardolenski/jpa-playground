package org.gol.jpaplayground.infrastructure.entity;

import org.springframework.data.domain.Persistable;

import java.util.UUID;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import lombok.Getter;

public abstract class BasePersistableEntity implements Persistable<UUID> {

    @Getter
    @Transient
    private boolean isNew = true;

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }
}
