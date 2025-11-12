package com.rslakra.appsuite.spring.persistence.entity;

import com.rslakra.appsuite.core.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

/**
 * @author Rohtash Lakra
 * @created 8/20/21 7:14 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class NamedEntity<ID extends Serializable> extends AbstractEntity<ID> {

    @Column(name = "name")
    private String name;

    /**
     * Returns the string representation of this object.
     *
     * @return
     */
    @Override
    public String toString() {
        return ToString.of(NamedEntity.class)
            .add("id", getId())
            .add("name", getName())
            .toString();
    }
}
