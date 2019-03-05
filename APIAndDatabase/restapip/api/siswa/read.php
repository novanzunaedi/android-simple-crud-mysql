<?php 
  // Headers
  header('Access-Control-Allow-Origin: *');
  header('Content-Type: application/json');

  include_once '../../config/Database.php';
  include_once '../../models/Siswa.php';

  // Instantiate DB & connect
  $database = new Database();
  $db = $database->connect();

  // Instantiate siswa object
  $siswa = new Siswa($db);

  // siswa read query
  $result = $siswa->read();
  
  // Get row count
  $num = $result->rowCount();

  // Check if any Siswa
  if($num > 0) {
        // Siswa array
        $sis_arr = array();
        $sis_arr['data'] = array();

        while($row = $result->fetch(PDO::FETCH_ASSOC)) {
          extract($row);

          $sis_item = array(
            'id' => $id,
            'nama' => $nama,
            'alamat' => $alamat,
            'no_telpon' => $no_telpon
          );

          // Push to "data"
          array_push($sis_arr['data'], $sis_item);
        }

        // Turn to JSON & output
        echo json_encode($sis_arr);

  } else {
        // No Siswa data
        echo json_encode(
          array('message' => 'No Siswa Found')
        );
  }
