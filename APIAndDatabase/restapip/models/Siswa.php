<?php
  class Siswa {
    // DB Stuff
    private $conn;
    private $table = 'siswa';

    // Properties
    public $id;
    public $nama;
    public $alamat;
    public $no_telpon;

    // Constructor with DB
    public function __construct($db) {
      $this->conn = $db;
    }

    // Get categories
    public function read() {
      // Create query
      $query = 'SELECT
        id,
        nama,
        alamat,
        no_telpon
      FROM
        ' . $this->table . '
      ORDER BY
        nama DESC';

      // Prepare statement
      $stmt = $this->conn->prepare($query);

      // Execute query
      $stmt->execute();

      return $stmt;
    }


    // Get Single Category
  public function read_single(){
    // Create query
    $query = 'SELECT
           id,
           nama,
           alamat,
           no_telpon
        FROM
          ' . $this->table . '
      WHERE id = ?
      LIMIT 0,1';

      //Prepare statement
      $stmt = $this->conn->prepare($query);

      // Bind ID
      $stmt->bindParam(1, $this->id);

      // Execute query
      $stmt->execute();

      $row = $stmt->fetch(PDO::FETCH_ASSOC);

      // set properties
      $this->id = $row['id'];
      $this->nama = $row['nama'];
      $this->alamat = $row['alamat'];
      $this->no_telpon = $row['no_telpon'];
  }

  // Create Category
  public function create() {
    // Create Query

    // $query = 'INSERT INTO ' . $this->table . ' SET title = :title, body = :body, author = :author, category_id = :category_id';

    // $query = 'INSERT INTO ' .
    //   $this->table . '
    // SET
    //   nama = :nama';

    $query = 'INSERT INTO ' . $this->table . ' SET nama = :nama, alamat = :alamat, no_telpon =:no_telpon';

    // Prepare Statement
    $stmt = $this->conn->prepare($query);

    // Clean data
    $this->nama = htmlspecialchars(strip_tags($this->nama));
    $this->alamat = htmlspecialchars(strip_tags($this->alamat));
    $this->no_telpon = htmlspecialchars(strip_tags($this->no_telpon));

    // Bind data
    $stmt-> bindParam(':nama', $this->nama);
    $stmt-> bindParam(':alamat', $this->alamat);
    $stmt-> bindParam(':no_telpon', $this->no_telpon);

    // Execute query
    if($stmt->execute()) {
      return true;
    }

    // Print error if something goes wrong
    printf("Error: $s.\n", $stmt->error);

    return false;
  }

  // Update Category
  public function update() {
    // Create Query
    $query = 'UPDATE ' .
      $this->table . '
    SET
      nama = :nama,
      alamat = :alamat,
      no_telpon = :no_telpon
      WHERE
      id = :id';

  // Prepare Statement
  $stmt = $this->conn->prepare($query);

  // Clean data
  $this->id = htmlspecialchars(strip_tags($this->id));
  $this->nama = htmlspecialchars(strip_tags($this->nama));
  $this->alamat = htmlspecialchars(strip_tags($this->alamat));
  $this->no_telpon = htmlspecialchars(strip_tags($this->no_telpon));

  // Bind data
  $stmt-> bindParam(':nama', $this->nama);
  $stmt-> bindParam(':alamat', $this->alamat);
  $stmt-> bindParam(':no_telpon', $this->no_telpon);
  $stmt-> bindParam(':id', $this->id);

  // Execute query
  if($stmt->execute()) {
    return true;
  }

  // Print error if something goes wrong
  printf("Error: $s.\n", $stmt->error);

  return false;
  }

  // Delete Category
  public function delete() {
    // Create query
    $query = 'DELETE FROM ' . $this->table . ' WHERE id = :id';

    // Prepare Statement
    $stmt = $this->conn->prepare($query);

    // clean data
    $this->id = htmlspecialchars(strip_tags($this->id));

    // Bind Data
    $stmt-> bindParam(':id', $this->id);

    // Execute query
    if($stmt->execute()) {
      return true;
    }

    // Print error if something goes wrong
    printf("Error: $s.\n", $stmt->error);

    return false;
    }
  }
