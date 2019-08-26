package gr.blxbrgld.list.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Order Enumeration
 * @author blxbrgld
 */
public enum Order {

    ASC("ASC"),
    DESC("DESC");

    private final String order;

    /**
     * Constructor
     * @param order The Order
     */
    Order(final String order) {
        this.order = order;
    }

    /**
     * Get The Order
     * @return The Name
     */
    public String getOrder() {
        return order;
    }

    /**
     * Get Enum By A Given String Value
     * @param value The Value
     * @return Order Enum
     */
    public static Order get(final String value) {
        if(StringUtils.trimToNull(value) != null) {
            for (final Order order : Order.values()) {
                if (order.getOrder().equals(value)) {
                    return order;
                }
            }
        }
        return ASC; // The Default Ordering
    }
}
