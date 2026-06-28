package com.catalog.framework.domain;

import com.catalog.framework.exception.BusinessException;

public interface EntrySortField {

    String getFieldName();

    /**
     * Frozen spot: valida se o campo informado é ordenável no domínio.
     * Hot spot: o enum do domínio define os campos válidos.
     */
    static <E extends Enum<E> & EntrySortField> E fromString(Class<E> enumClass, String value) {
        for (E constant : enumClass.getEnumConstants()) {
            if (constant.getFieldName().equalsIgnoreCase(value)) {
                return constant;
            }
        }
        throw new BusinessException("Invalid sort field: " + value);
    }
}
