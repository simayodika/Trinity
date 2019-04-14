package com.example.simayodika.inventaris.modals;

public class ListJenis {

    private String idJenis;
    private String namaJenis;
    private String kodeJenis;

    public ListJenis(String idJenis, String namaJenis, String kodeJenis) {
        this.idJenis = idJenis;
        this.namaJenis = namaJenis;
        this.kodeJenis = kodeJenis;
    }

    public String getIdJenis() {
        return idJenis;
    }

    public void setIdJenis(String idJenis) {
        this.idJenis = idJenis;
    }

    public String getNamaJenis() {
        return namaJenis;
    }

    public void setNamaJenis(String namaJenis) {
        this.namaJenis = namaJenis;
    }

    public String getKodeJenis() {
        return kodeJenis;
    }

    public void setKodeJenis(String kodeJenis) {
        this.kodeJenis = kodeJenis;
    }
}
