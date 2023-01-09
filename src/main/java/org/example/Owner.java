package org.example;

import java.util.Objects;

public class Owner {
    private String name;
    private String city;
    private int maxNumberOfPets;

    public Owner(String name, String city, int maxNumberOfPets) {
        this.name = name;
        this.city = city;
        this.maxNumberOfPets = maxNumberOfPets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getMaxNumberOfPets() {
        return maxNumberOfPets;
    }

    public void setMaxNumberOfPets(int maxNumberOfPets) {
        this.maxNumberOfPets = maxNumberOfPets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return maxNumberOfPets == owner.maxNumberOfPets && Objects.equals(name, owner.name) && Objects.equals(city, owner.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, maxNumberOfPets);
    }
}
