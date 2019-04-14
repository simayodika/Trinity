package com.example.simayodika.inventaris.modals;

public class ListReport {

    private String idInvent;
    private String idPeminjaman;
    private String jumlah;
    private String tglPinjaman;
    private String tglKembali;
    private String statusPinjaman;
    private String namaInvent;
    private String ketInvent;

    public ListReport(String  idInvent, String idPeminjaman, String jumlah, String tglPinjaman, String tglKembali, String statusPinjaman, String namaInvent, String ketInvent) {
        this.idInvent = idInvent;
        this.idPeminjaman = idPeminjaman;
        this.jumlah = jumlah;
        this.tglPinjaman = tglPinjaman;
        this.tglKembali = tglKembali;
        this.statusPinjaman = statusPinjaman;
        this.namaInvent = namaInvent;
        this.ketInvent = ketInvent;
    }

    public String getIdInvent() {
        return idInvent;
    }

    public void setIdInvent(String idInvent) {
        this.idInvent = idInvent;
    }

    public String getIdPeminjaman() {
        return idPeminjaman;
    }

    public void setIdPeminjaman(String idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTglPinjaman() {
        return tglPinjaman;
    }

    public void setTglPinjaman(String tglPinjaman) {
        this.tglPinjaman = tglPinjaman;
    }

    public String getTglKembali() {
        return tglKembali;
    }

    public void setTglKembali(String tglKembali) {
        this.tglKembali = tglKembali;
    }

    public String getStatusPinjaman() {
        return statusPinjaman;
    }

    public void setStatusPinjaman(String statusPinjaman) {
        this.statusPinjaman = statusPinjaman;
    }

    public String getNamaInvent() {
        return namaInvent;
    }

    public void setNamaInvent(String namaInvent) {
        this.namaInvent = namaInvent;
    }

    public String getKetInvent() {
        return ketInvent;
    }

    public void setKetInvent(String ketInvent) {
        this.ketInvent = ketInvent;
    }
}
