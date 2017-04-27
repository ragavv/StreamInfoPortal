<?php
   $con=mysqli_connect("localhost","root","root","infoportal");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }

   $result = mysqli_query($con,"SELECT * FROM Events");
   $res = array();

   while($row = mysqli_fetch_array($result)){
     array_push($res,array('id'=>$row[0],'name'=>$row[1],'date'=>$row[2], 'time'=>$row[3], 'location'=>$row[4], 'details'=>$row[5]));
   }
 echo json_encode(array("result"=>$res));
 mysqli_close($con);
?>
