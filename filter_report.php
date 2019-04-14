<?php

require_once 'koneksi.php';

$tgl = $_GET['tgl'];

$sql = "SELECT tb_detail_pinjam.IDDetailPinjam_, tb_detail_pinjam.IDInvent_, tb_detail_pinjam.IDPeminjaman_, tb_detail_pinjam.Jumlah_, tb_peminjaman.TglPinjaman_, tb_peminjaman.TglKembali_, tb_peminjaman.StatusPinjaman_, tb_inventaris.NamaInvent_, tb_inventaris.KetInvent_ FROM ((tb_detail_pinjam
	INNER JOIN tb_peminjaman ON tb_detail_pinjam.IDPeminjaman_ = tb_peminjaman.IDPeminjaman_)
    INNER JOIN tb_inventaris ON tb_detail_pinjam.IDInvent_ = tb_inventaris.IDInvent_) WHERE tb_peminjaman.TglPinjaman_='$tgl'";

$result = mysqli_query($con, $sql);

$data = array();

while($row = mysqli_fetch_assoc($result)) {
	$data["data"][] = $row;
}

echo json_encode($data);
mysqli_close($con);

?>