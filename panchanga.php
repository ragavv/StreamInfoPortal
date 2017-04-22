<?php
   $con=mysqli_connect("localhost","root","root","infoportal");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }

   $result = mysqli_query($con,"SELECT * FROM Panchanga");
   $res = array();

   while($row = mysqli_fetch_array($result)){
     array_push($res,array('day'=>$row[0],'text'=>$row[1]));
   }
 echo json_encode(array("result"=>$res));
 mysqli_close($con);
?>
