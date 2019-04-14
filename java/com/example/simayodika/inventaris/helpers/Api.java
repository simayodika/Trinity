package com.example.simayodika.inventaris.helpers;

import android.net.Uri;

public class Api {

    private static String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
    private static String url = "http://192.168.8.101/ukk19/";

    private static String loginPengelola = url + "login_pengelola.php";
    private static String loginPegawai = url + "login_pegawai.php";
    public static String getInvent = url + "get_invent.php";
    public static String getJenis = url + "get_jenis.php";
    public static String getRuang = url + "get_ruang.php";
    public static String getPegawai = url + "get_pegawai.php";
    public static String getReport = url + "report.php";
    public static String getTotalReport = url + "count.php";
    private static String getIDPegawai = url + "get_idpegawai.php";
    private static String searchInvent = url + "search_invent.php";
    private static String addInvent = url + "add_invent.php";
    private static String updateInvent = url + "update_invent.php";
    private static String deleteInvent = url + "delete_invent.php";
    private static String addPeminjaman = url + "add_peminjaman.php";
    private static String cekKode = url + "cek_kode.php";
    private static String pengembalian = url + "pengembalian_peminjaman.php";
    private static String filterReport = url + "filter_report.php";

    public static String getLoginPengelola(String username, String password) {
        return Uri.encode(loginPengelola +
                "?username=" + username +
                "&password=" + password, ALLOWED_URI_CHARS);
    }

    public static String getLoginPegawai(String username, String password) {
        return Uri.encode(loginPegawai +
                "?username=" + username +
                "&password=" + password, ALLOWED_URI_CHARS);
    }

    public static String getSearchInvent(String nama) {
        return Uri.encode(searchInvent + "?nama=" + nama, ALLOWED_URI_CHARS);
    }

    public static String getAddInvent(String nama, String kondisi, String ket, String jumlah, String jenis, String ruang, String idpetugas) {
        return Uri.encode(addInvent +
                "?nama=" + nama +
                "&kondisi=" + kondisi +
                "&ket=" + ket +
                "&jumlah=" + jumlah +
                "&jenis=" + jenis +
                "&ruang=" + ruang +
                "&idpetugas=" + idpetugas, ALLOWED_URI_CHARS);
    }

    public static String getUpdateInvent(String idinvent, String nama, String kondisi, String ket, String jumlah, String jenis, String ruang) {
        return Uri.encode(updateInvent +
                "?idinvent=" + idinvent +
                "&nama=" + nama +
                "&kondisi=" + kondisi +
                "&ket=" + ket +
                "&jumlah=" + jumlah +
                "&jenis=" + jenis +
                "&ruang=" + ruang, ALLOWED_URI_CHARS);
    }

    public static String getDeleteInvent(String idinvent) {
        return Uri.encode(deleteInvent + "?idinvent=" + idinvent, ALLOWED_URI_CHARS);
    }

    public static String getIDPegawai(String nama) {
        return Uri.encode(getIDPegawai + "?nama=" + nama, ALLOWED_URI_CHARS);
    }

    public static String getAddPeminjaman(String idPegawai, String idInvent, String jumlah, String tglPinjam, String tglKembali) {
        return Uri.encode(addPeminjaman +
                "?idpegawai=" + idPegawai +
                "&idinvent=" + idInvent +
                "&jumlah=" + jumlah +
                "&tglpinjam=" + tglPinjam +
                "&tglkembali=" +tglKembali, ALLOWED_URI_CHARS);
    }

    public static String getCekKode(String kode, String tgl) {
        return Uri.encode(cekKode +
                "?kode=" + kode +
                "&tgl=" + tgl, ALLOWED_URI_CHARS);
    }

    public static String getPengembalian(String kode) {
        return Uri.encode(pengembalian + "?kode=" + kode, ALLOWED_URI_CHARS);
    }

    public static String getFilterReport(String tgl) {
        return Uri.encode(filterReport + "?tgl=" + tgl, ALLOWED_URI_CHARS);
    }
}
