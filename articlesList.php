<?php
   $con=mysqli_connect("localhost","root","root","infoportal");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }

   $result = mysqli_query($con,"SELECT * FROM Articles");
   $res = array();

   while($row = mysqli_fetch_array($result)){
     array_push($res,array('id'=>$row[0],'name'=>$row[1],'location'=>$row[2], 'author'=>$row[3]));
   }
 echo json_encode(array("result"=>$res));
 mysqli_close($con);
?>
