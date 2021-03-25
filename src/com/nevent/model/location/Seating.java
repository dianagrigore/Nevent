package com.nevent.model.location;

import java.util.Map;
import java.util.Objects;

public class Seating {
        private Integer numberOfSeats;
        private Map<String, Integer> ticketsOfEachType;

        public Seating(Integer numberOfSeats, Map<String, Integer> ticketsOfEachType) {
                this.numberOfSeats = numberOfSeats;
                this.ticketsOfEachType = ticketsOfEachType;
        }

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

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Seating seating = (Seating) o;
                return Objects.equals(numberOfSeats, seating.numberOfSeats) && Objects.equals(ticketsOfEachType, seating.ticketsOfEachType);
        }

        @Override
        public int hashCode() {
                int hashCode = 31;
                int prime = 17;
                hashCode = numberOfSeats == null ? 0 : prime * numberOfSeats.hashCode();
                hashCode += ticketsOfEachType == null ? 0 : prime * ticketsOfEachType.hashCode();
                return hashCode;
        }

        @Override
        public String toString() {
                return "Seating{" +
                        "numberOfSeats=" + numberOfSeats +
                        ", ticketsOfEachType=" + ticketsOfEachType +
                        '}';
        }
}
