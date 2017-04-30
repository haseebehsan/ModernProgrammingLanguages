<?php
    $connect = mysqli_connect("localhost","root","password","WebTry");
    if(!$connect){
        echo "Error";
    }

    $abc = $_POST['username'];
    $bcd = $_POST['password'];

    echo $abc . " " . $cof

    $query = "INSERT INTO user(user_name, user_password) VALUES('$abc','$bcd')";

    $result = mysqli_query($connect, $query);
    
    
?>
