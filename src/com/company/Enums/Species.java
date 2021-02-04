package com.company.Enums;

public enum Species {
    NYKUR("Nykur", 30),
    GLOSON("Gloson", 40),
    KRAKEN("Kraken", 10),
    LINNR("Linnr", 20),
    TILBERI("Tilberi", 50);

    public String species;
    public int price;

    Species(String species, int price) {
        this.species = species;
        this.price = price;
    }
}
