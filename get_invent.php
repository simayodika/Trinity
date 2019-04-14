<?php

require_once 'koneksi.php';

$sql = "SELECT tb_inventaris.IDInvent_, tb_inventaris.NamaInvent_, tb_inventaris.Kondisi_, tb_inventaris.Kondisi_, tb_inventaris.KetInvent_, tb_inventaris.KetInvent_, tb_inventaris.Jumlah_, tb_inventaris.IDJenis_, tb_inventaris.TglRegis_, tb_inventaris.IDRuang_, tb_inventaris.KodeInven_, tb_ruang.NamaRuang_, tb_ruang.KodeRuang_, tb_jenis.NamaJenis_, tb_jenis.KodeJenis_ 
FROM ((tb_inventaris
       INNER JOIN tb_jenis ON tb_inventaris.IDJenis_ = tb_jenis.IDJenis_)
      INNER JOIN tb_ruang ON tb_inventaris.IDRuang_ = tb_ruang.IDRuang_)";

$result = mysqli_query($con, $sql);

$data = array();

while($row = mysqli_fetch_assoc($result)){
	$data["data"][] = $row;
}

echo json_encode($data);
mysqli_close($con);

?>