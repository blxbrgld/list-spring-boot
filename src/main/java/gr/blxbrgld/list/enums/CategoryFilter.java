package gr.blxbrgld.list.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * CategoryFilter enumeration defining the desired usage of a list request
 * @author blxbrgld
 */
public enum CategoryFilter {

    NONE, MENU, DROPDOWN;

    /**
     * Get enum by string value null safe
     * @param value The value
     * @return {@link CategoryFilter}
     */
    public static CategoryFilter get(final String value) {
        if(StringUtils.trimToNull(value) != null) {
            for (final CategoryFilter filter : CategoryFilter.values()) {
                if (filter.name().equalsIgnoreCase(value)) {
                    return filter;
                }
            }
        }
        return NONE;
    }
}
