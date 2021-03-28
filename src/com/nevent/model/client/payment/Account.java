package com.nevent.model.client.payment;

import java.util.Objects;
import java.util.Set;

public class Account {
    private Double leftBalance;
    private Set<Voucher> vouchers;

    public Account(Double leftBalance, Set<Voucher> vouchers) {
        this.leftBalance = leftBalance;
        this.vouchers = vouchers;
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
