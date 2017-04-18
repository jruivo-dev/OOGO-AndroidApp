package com.jruivodev.oogo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jojih on 17/04/2017.
 */

public class OrderState {
    private String mOrderId, mUserId;
    private static String mOrderState;


    public static Map<OrderState, String> orderStateMap = new HashMap<>();

    public String getOrderId() {
        return mOrderId;
    }

    public String getUserId() {
        return mUserId;
    }

    private OrderState(String mOrderId, String mUserId) {
        this.mOrderId = mOrderId;
        this.mUserId = mUserId;
    }

    public static String getOrderState(String orderId, String userId) {
        OrderState o = new OrderState(orderId, userId);
        if (orderStateMap.containsKey(o))
            return orderStateMap.get(o);
        return null;
    }

    public static void setOrderState(String orderId, String userId, String enumState) {
        OrderState o = new OrderState(orderId, userId);
        orderStateMap.put(o, enumState);
    }


    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof OrderState)) {
            return false;
        }
        OrderState order = (OrderState) o;

        return order.mOrderId.equals(mOrderId) &&
                order.mUserId.equals(mUserId);
    }

    //Idea from effective Java : Item 9
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + mOrderId.hashCode();
        result = 31 * result + mUserId.hashCode();
        return result;
    }


    /**
     * Enum
     */

    public enum State {
        PENDING("pending"),
        ACCEPTED("accepted"),
        REJECTED("rejected");

        private final String text;

        private State(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

}
