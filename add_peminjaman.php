<?php

require_once 'koneksi.php';

$idpegawai = $_GET['idpegawai'];
$idinvent = $_GET['idinvent'];
$jumlah = $_GET['jumlah'];
$tglpinjam = $_GET['tglpinjam'];
$tglkembali = $_GET['tglkembali'];

$string = substr(str_shuffle("1234567890"), 0, 6);
$kode = "KD".$string;

$sql = "INSERT INTO tb_peminjaman (IDPegawai_, IDInvent_, Jumlah_, TglPinjaman_, TglKembali_, StatusPinjaman_, KodePeminjaman_)
		VALUES ('$idpegawai', '$idinvent', '$jumlah', '$tglpinjam', '$tglkembali', '1', '$kode')";

$result = mysqli_query($con, $sql);

if ($result) {
	
	$lastid = mysqli_insert_id($con);
	$sql2 = "INSERT INTO tb_detail_pinjam (IDPeminjaman_, IDInvent_, Jumlah_, Tgl_) VALUES ('$lastid', '$idinvent', '$jumlah', '$tglpinjam')";
	$resul2 = mysqli_query($con, $sql2);
	if ($resul2) {
		echo "Berhasil";
	} else {
		echo mysqli_error($con);
	}

} else {
	echo mysqli_error($con);
}

mysqli_close($con);

?>