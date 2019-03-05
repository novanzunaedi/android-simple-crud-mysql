<?php

  // Headers
  header('Access-Control-Allow-Origin: *');
  header('Content-Type: application/json');

  include_once '../../config/Database.php';
  include_once '../../models/Siswa.php';
  // Instantiate DB & connect
  $database = new Database();
  $db = $database->connect();
  // Instantiate blog siswa object
  $siswa = new Siswa($db);

  // Get ID
  $siswa->id = isset($_GET['id']) ? $_GET['id'] : die();

  // Get post
  $siswa->read_single();

  // Create array
  $siswa_arr = array(
    'id' => $siswa->id,
    'nama' => $siswa->nama,
    'alamat' => $siswa->alamat,
    'no_telpon' => $siswa->no_telpon
  );

  // Make JSON
  print_r(json_encode($siswa));
