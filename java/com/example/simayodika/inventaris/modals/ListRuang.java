package com.example.simayodika.inventaris.modals;

public class ListRuang {

    private String idRuang;
    private String namaRuang;
    private String kodeRuang;

    public ListRuang(String idRuang, String namaRuang, String kodeRuang) {
        this.idRuang = idRuang;
        this.namaRuang = namaRuang;
        this.kodeRuang = kodeRuang;
    }

    public String getIdRuang() {
        return idRuang;
    }

    public void setIdRuang(String idRuang) {
        this.idRuang = idRuang;
    }

    public String getNamaRuang() {
        return namaRuang;
    }

    public void setNamaRuang(String namaRuang) {
        this.namaRuang = namaRuang;
    }

    public String getKodeRuang() {
        return kodeRuang;
    }

    public void setKodeRuang(String kodeRuang) {
        this.kodeRuang = kodeRuang;
    }
}
