<?php
	$uploadPath = 'upload/';		// 저장위치
	$fileName = $_GET['filename'];	// 파일명

	// 이미지 데이터 받고, 헤더 제거
	$filteredData=substr($GLOBALS['HTTP_RAW_POST_DATA'], strpos($GLOBALS['HTTP_RAW_POST_DATA'], ",")+1);

	// 데이터 디코딩
	$decodedData=base64_decode($filteredData);

	// 파일입력
	$fp = fopen( $uploadPath.$fileName.'.png', 'wb' );
	fwrite( $fp, $decodedData);
	fclose( $fp );
?>