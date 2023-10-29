package com.devamatre.appsuite.spring.persistence.entity;

import com.devamatre.framework.core.ToString;
import com.devamatre.appsuite.spring.persistence.listener.BaseEntityListener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author Rohtash Lakra
 * @created 8/4/21 5:59 PM
 */
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(BaseEntityListener.class)
@MappedSuperclass
public abstract class AbstractEntity<ID extends Serializable> extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private ID id;

    /**
     * Returns the string representation of this object.
     *
     * @return
     */
    @Override
    public String toString() {
        return ToString.of(AbstractEntity.class)
            .add("id", getId())
            .add(super.toString())
            .toString();
    }
}
