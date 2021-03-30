package com.nevent.model.client.payment;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
/* Class account, represents the user's credit account and stores the vouchers:
* User can add and retrieve monetary units from here
* User can view and use his vouchers */
public class Account {
    private String clientId;
    private Double leftBalance;
    private Set<Voucher> vouchers;

    public Account(String clientId) {
        this.leftBalance = 0.0;
        this.clientId = clientId;
        this.vouchers = new HashSet<>();
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

    public void addToThisAccount(Double amount){
        this.leftBalance += amount;
    }
    public void retrieveFromThisAccount(Double amount){
        if(amount < this.leftBalance) {
            this.leftBalance -= amount;
        }
    }
    public void seeMyVouchers(){
        Set<Voucher> vouchers = this.getVouchers();
        if(vouchers.isEmpty()){
            System.out.println("Voucher set is empty :(");
        } else {
            for (Voucher voucher : vouchers) {
                System.out.println("Voucher pentru " + voucher.getReason() + " in valoare de "
                        + voucher.getValue().toString());
            }
        }
    }

    public void addAVoucer(Voucher v){
        this.vouchers.add(v);
    }

    public Double getTheValueOfThisVoucher(String reason){
        for(Voucher voucher : getVouchers()){
            if (voucher.getReason().equals(reason))
                return voucher.getValue();
        }
        return 0.0;
    }

    public boolean useTheVoucher(String reason){
        if(reason != null) {
            for (Voucher voucher : this.vouchers) {
                if (voucher.getReason().equals(reason)) {
                    vouchers.remove(voucher);
                    return true;
                }
            }
        }
        return false;
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
