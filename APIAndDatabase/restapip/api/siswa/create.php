<?php
  // Headers
  header('Access-Control-Allow-Origin: *');
  header('Content-Type: application/json');
  header('Access-Control-Allow-Methods: POST');
  header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type, Access-Control-Allow-Methods, Authorization,X-Requested-With');

  include_once '../../config/Database.php';
  include_once '../../models/Siswa.php';
  // Instantiate DB & connect
  $database = new Database();
  $db = $database->connect();

  // Instantiate blog post object
  $siswa = new Siswa($db);

  // Get raw posted data
  $data = json_decode(file_get_contents("php://input"));

  $siswa->nama = $data->nama;
  $siswa->alamat = $data->alamat;
  $siswa->no_telpon = $data->no_telpon;


  // Create siswa
  if($siswa->create()) {
    echo json_encode(
      array('message' => 'Data Siswa di Inputkan')
    );
  } else {
    echo json_encode(
      array('message' => 'Data Siswa Gagal di Input')
    );
  }
