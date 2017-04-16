package com.jruivodev.oogo.objects_and_adapters;

/**
 * Created by Jojih on 16/04/2017.
 */

public enum OrderState {
    PENDING("pending"),
    ACCEPTED("accepted"),
    REJECTED("rejected");

    private final String text;

    /**
     * @param text
     */
    private OrderState(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}