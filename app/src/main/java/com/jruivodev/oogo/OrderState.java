package com.jruivodev.oogo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jojih on 17/04/2017.
 */

public class OrderState {
    private String mOrderId, mUserId;
    private static String mOrderState;


    private static Map<OrderState, String> orderStateMap = new HashMap<>();


    private OrderState(String mOrderId, String mUserId) {
        this.mOrderId = mOrderId;
        this.mUserId = mUserId;
    }


    public static String getOrderState(String orderId, String userId) {
        return orderStateMap.get(new OrderState(orderId, userId));
    }

    public static void setOrderState(String orderId, String userId, String state) {
        orderStateMap.put(new OrderState(orderId, state), state);
    }


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
