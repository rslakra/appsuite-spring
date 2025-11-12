package com.rslakra.appsuite.spring.payload.dto;

import com.rslakra.appsuite.core.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * @author Rohtash Lakra
 * @created 8/4/21 5:40 PM
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class AuditableDTO {

    private Long createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private String createdBy;

    private Long updatedOn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private String updatedBy;

    /**
     * ToString
     *
     * @return
     */
    @Override
    public String toString() {
        return ToString.of()
            .add("createdOn", getCreatedOn())
            .add("createdAt", getCreatedAt())
            .add("createdBy", getCreatedBy())
            .add("updatedOn", getUpdatedOn())
            .add("updatedAt", getUpdatedAt())
            .add("updatedBy", getUpdatedBy())
            .toString();
    }

}
