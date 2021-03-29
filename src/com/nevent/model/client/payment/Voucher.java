package com.nevent.model.client.payment;

import java.util.Objects;
/* Voucher, is a way to give users price cuts on tickets for various reasons*/
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
    public String toString() {
        return "Voucher{" +
                "reason='" + reason + '\'' +
                ", value=" + value +
                '}';
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
        return Objects.hash(reason, value);
    }
}
