package org.example.payments.api;

public class PaymentRequest {
    private String clientId;
    private Integer amount;

    public PaymentRequest() {
    }

    public PaymentRequest(String clientId, Integer amount) {
        this.clientId = clientId;
        this.amount = amount;
    }

    public String getClientId() {
        return clientId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
