<?php

$servername = "localhost";
$username = "root";
$password = "root";
$dbname = "illHack";

// Create connection
$conn = mysqli_connect($servername, $username, $password, $dbname);

// Check connection
if (!$conn) {
	die("Connection failed: " . mysqli_connect_error());
}


define('GOOGLE_API_KEY', 'AIzaSyCr');

?>
