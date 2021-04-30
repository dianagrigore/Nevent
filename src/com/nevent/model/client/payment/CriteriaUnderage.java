package com.nevent.model.client.payment;

import com.nevent.model.commons.Filterable;
import com.nevent.model.client.Client;

import java.util.HashSet;
import java.util.Set;

public class CriteriaUnderage implements Filterable<Client, Integer> {
    @Override
    public Set<Client> filter(Set<Client> clients, Integer age_restriction) {
        Set<Client> underageClients = new HashSet<>();
        for (Client client : clients){
            if(client.getAge() < age_restriction){
                underageClients.add(client);
            }
        }
        return underageClients;
    }
}
