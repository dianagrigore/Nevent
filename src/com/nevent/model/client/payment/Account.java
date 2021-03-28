package com.nevent.model.client.payment;

import java.util.Objects;
import java.util.Set;

public class Account {
    private String clientId;
    private Double leftBalance;
    private Set<Voucher> vouchers;

    public Account(String clientId) {
        this.leftBalance = 0.0;
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public Double getLeftBalance() {
        return leftBalance;
    }

    public void setLeftBalance(Double leftBalance) {
        this.leftBalance = leftBalance;
    }

    public Set<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(Set<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public void seeMyVouchers(){
        Set<Voucher> vouchers = this.getVouchers();
        for (Voucher voucher : vouchers){
            System.out.println("Voucher pentru " + voucher.getReason() + " in valoare de "
                    + voucher.getValue().toString());
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "leftBalance=" + leftBalance +
                ", vouchers=" + vouchers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(leftBalance, account.leftBalance) && Objects.equals(vouchers, account.vouchers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftBalance, vouchers);
    }
}
