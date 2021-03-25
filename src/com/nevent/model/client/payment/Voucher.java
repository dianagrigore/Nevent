package com.nevent.model.client.payment;

import java.util.Objects;

public class Voucher {
    private String reason;
    private Double value;

    public Voucher(String reason, Double value) {
        this.reason = reason;
        this.value = value;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return Objects.equals(reason, voucher.reason) && Objects.equals(value, voucher.value);
    }

    @Override
    public int hashCode() {
        int hashCode = 31;
        int prime = 17;
        hashCode = reason == null ? 0 : prime * reason.hashCode();
        hashCode += value == null ? 0 : prime * value.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "reason='" + reason + '\'' +
                ", value=" + value +
                '}';
    }
}
