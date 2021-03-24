package com.nevent.model.location;

import java.util.Map;

public class Seating {
        private Integer numberOfSeats;
        private Map<String, Integer> ticketsOfEachType;

        public Integer getNumberOfSeats() {
                return numberOfSeats;
        }

        public void setNumberOfSeats(Integer numberOfSeats) {
                this.numberOfSeats = numberOfSeats;
        }

        public Map<String, Integer> getTicketsOfEachType() {
                return ticketsOfEachType;
        }

        public void setTicketsOfEachType(Map<String, Integer> ticketsOfEachType) {
                this.ticketsOfEachType = ticketsOfEachType;
        }
}
