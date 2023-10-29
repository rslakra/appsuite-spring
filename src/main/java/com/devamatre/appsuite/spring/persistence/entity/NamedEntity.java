package com.devamatre.appsuite.spring.persistence.entity;

import com.devamatre.framework.core.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

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
