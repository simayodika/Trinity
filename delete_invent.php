<?php

require_once 'koneksi.php';

$idinvent = $_GET['idinvent'];

$sql = "DELETE FROM tb_inventaris WHERE IDInvent_ = '$idinvent'";

$result = mysqli_query($con, $sql);

if ($sql) {
	echo "Berhasil";
} else {
	echo "Gagal";
}

mysqli_close($con);

?>