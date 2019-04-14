<?php

require 'koneksi.php';

$sql = "SELECT
	(SELECT COUNT(*)
	FROM tb_inventaris
	) AS JumlahInvent_,
    (SELECT COUNT(*)
	FROM tb_petugas WHERE IDLevel_='2'
	) AS JumlahOperator_,
    (SELECT COUNT(*)
	FROM tb_pegawai
	) AS JumlahPegawai_,
	(SELECT COUNT(*)
	FROM tb_peminjaman WHERE StatusPinjaman_='1'
	) AS PinjamBarang_,
	(SELECT COUNT(*)
	FROM tb_peminjaman WHERE StatusPinjaman_='2'
	) AS PinjamKembali_,
    (SELECT COUNT(*)
	FROM tb_peminjaman WHERE StatusPinjaman_='3'
	) AS PinjamTerlambat_
	FROM DUAL";

$response = mysqli_query($con, $sql);
$result = array();
$result['total'] = array();

if (mysqli_num_rows($response) == 1) {
	$row = mysqli_fetch_assoc($response);

	$index['JumlahInvent_'] = $row['JumlahInvent_'];
	$index['JumlahOperator_'] = $row['JumlahOperator_'];
	$index['JumlahPegawai_'] = $row['JumlahPegawai_'];
	$index['PinjamBarang_'] = $row['PinjamBarang_'];
	$index['PinjamKembali_'] = $row['PinjamKembali_'];
	$index['PinjamTerlambat_'] = $row['PinjamTerlambat_'];

	array_push($result['total'], $index);

	$result['success'] = "1";
	$result['message'] = "success";

	echo json_encode($result);
}

mysqli_close($con);
?>