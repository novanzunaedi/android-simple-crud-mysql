<?php
  // Headers
  header('Access-Control-Allow-Origin: *');
  header('Content-Type: application/json');
  header('Access-Control-Allow-Methods: DELETE');
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

  // Set ID to UPDATE
  $siswa->id = $data->id;

  // Delete post
  if($siswa->delete()) {
    echo json_encode(
      array('message' => 'Data Siswa di Hapus')
    );
  } else {
    echo json_encode(
      array('message' => 'Data Siswa Gagal di Hapus')
    );
  }
