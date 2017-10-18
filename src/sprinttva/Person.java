package sprinttva;

import java.time.LocalDate;

public class Person  {
    private String name;
    private String personNumber;
    private LocalDate lastPayed;

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPersonNumber() { return personNumber; }
    public void setPersonNumber(String personNumber) { this.personNumber = personNumber; }

    public LocalDate getLastPayed() { return lastPayed; }
    public void setLastPayed(LocalDate lastPayed) { this.lastPayed = lastPayed; }
}
