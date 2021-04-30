package com.nevent.model.commons;

import java.util.Set;

public interface Filterable <C, T>{
   Set<C> filter(Set<C> items, T value);
}
