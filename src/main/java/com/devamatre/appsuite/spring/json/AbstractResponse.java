package com.devamatre.appsuite.spring.json;

import com.devamatre.framework.core.BeanUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Rohtash Lakra
 * @created 3/28/23 12:20 PM
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractResponse<DTO> {

    private DTO dataObject;
    private List<Error> errors;

    /**
     * @return
     */
    public final boolean hasErrors() {
        return BeanUtils.isNotEmpty(getErrors());
    }
}
