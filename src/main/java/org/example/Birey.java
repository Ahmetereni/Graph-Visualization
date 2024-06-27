package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Birey {
    private String ad;

    private ArrayList<Birey> komsulari;

    @Override
    public String toString() {
        return ad;

    }

    public Birey(String value) {
        this.ad = value;
        this.komsulari = new ArrayList<>();
    }

    public void komsuEkle(Birey neighbor) {
        komsulari.add(neighbor);
    }


}