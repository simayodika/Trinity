package com.example.simayodika.inventaris.modals;

public class ListInvent {

    private String idInvent;
    private String namaInvent;
    private String kondisiId;
    private String ketId;
    private String jumlah;
    private String idJenis;
    private String tglRegis;
    private String idRuang;
    private String kodeInvent;
    private String namaJenis;
    private String namaRuang;

    public ListInvent(String idInvent, String namaInvent, String kondisiId, String ketId, String jumlah, String idJenis, String tglRegis, String idRuang, String kodeInvent, String namaJenis, String namaRuang) {
        this.idInvent = idInvent;
        this.namaInvent = namaInvent;
        this.kondisiId = kondisiId;
        this.ketId = ketId;
        this.jumlah = jumlah;
        this.idJenis = idJenis;
        this.tglRegis = tglRegis;
        this.idRuang = idRuang;
        this.kodeInvent = kodeInvent;
        this.namaJenis = namaJenis;
        this.namaRuang = namaRuang;
    }

    public String getIdInvent() {
        return idInvent;
    }

    public void setIdInvent(String idInvent) {
        this.idInvent = idInvent;
    }

    public String getNamaInvent() {
        return namaInvent;
    }

    public void setNamaInvent(String namaInvent) {
        this.namaInvent = namaInvent;
    }

    public String getKondisiId() {
        return kondisiId;
    }

    public void setKondisiId(String kondisiId) {
        this.kondisiId = kondisiId;
    }

    public String getKetId() {
        return ketId;
    }

    public void setKetId(String ketId) {
        this.ketId = ketId;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getIdJenis() {
        return idJenis;
    }

    public void setIdJenis(String idJenis) {
        this.idJenis = idJenis;
    }

    public String getTglRegis() {
        return tglRegis;
    }

    public void setTglRegis(String tglRegis) {
        this.tglRegis = tglRegis;
    }

    public String getIdRuang() {
        return idRuang;
    }

    public void setIdRuang(String idRuang) {
        this.idRuang = idRuang;
    }

    public String getKodeInvent() {
        return kodeInvent;
    }

    public void setKodeInvent(String kodeInvent) {
        this.kodeInvent = kodeInvent;
    }

    public String getNamaJenis() {
        return namaJenis;
    }

    public void setNamaJenis(String namaJenis) {
        this.namaJenis = namaJenis;
    }

    public String getNamaRuang() {
        return namaRuang;
    }

    public void setNamaRuang(String namaRuang) {
        this.namaRuang = namaRuang;
    }
}
