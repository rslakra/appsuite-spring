package com.rslakra.appsuite.spring.payload.model;

import com.rslakra.appsuite.core.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author Rohtash Lakra
 * @created 8/20/21 7:14 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class NamedModel<ID extends Serializable> extends AbstractModel<ID> {

    private String name;

    /**
     * Returns the string representation of this object.
     *
     * @return
     */
    @Override
    public String toString() {
        return ToString.of(NamedModel.class)
            .add("id", getId())
            .add("name", getName())
            .toString();
    }
}
