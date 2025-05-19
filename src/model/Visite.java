package model;

import java.time.LocalDate;

public class Visite {
    private LocalDate date;
    private String motif;
    private String veterinaire;

    public Visite(LocalDate date, String motif, String veterinaire) {
        this.date = date;
        this.motif = motif;
        this.veterinaire = veterinaire;
    }

    public LocalDate getDate() { return date; }
    public String getMotif() { return motif; }
    public String getVeterinaire() { return veterinaire; }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public void setVeterinaire(String veterinaire) {
        this.veterinaire = veterinaire;
    }
}