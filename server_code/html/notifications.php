<?php

include("db.php");

//Generic php function to send GCM push notification
function sendMessageThroughGCM($registatoin_ids, $message) {
	//Google cloud messaging GCM-API url
	$url = 'https://android.googleapis.com/gcm/send';
	$fields = array(
		'registration_ids' => $registatoin_ids,
		'data' => $message,
	);
	$headers = array(
		'Authorization: key=' . GOOGLE_API_KEY,
		'Content-Type: application/json'
	);
	$ch = curl_init();
	curl_setopt($ch, CURLOPT_URL, $url);
	curl_setopt($ch, CURLOPT_POST, true);
	curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);   
	curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
	curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
	$result = curl_exec($ch);               
	if ($result === FALSE) {
		die('Curl failed: ' . curl_error($ch));
	}
	curl_close($ch);
	echo $result;
}

/*function sendPushNotification($registration_ids, $message) {
    $url = 'https://android.googleapis.com/gcm/send';
    $fields = array(
	'registration_ids' => $registration_ids,
	'data' => $message,
    );

    $headers = array(
	'Authorization:key=' . GOOGLE_API_KEY,
	'Content-Type: application/json'
    );
    echo json_encode($fields);
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));

    $result = curl_exec($ch);
    if($result === false)
	die('Curl failed ' . curl_error());

    curl_close($ch);
    return $result;

}

function sendPushNotification2() {
	$apiKey = "AIzaSyAG_Aix3jEOt2ubqZfsPcyCKz1RGrwzPgY";
	$apiKey = "AIzaSyCrw01hnE_NaD4p5w_4e5sO84EYpqzTMr4";
	$msg = array("message" => "PersonA needs care..");

	$fields = array(
		'registration_ids' 	=> array("exfN2LrF7"),
		'data'			=> $msg
	);

	$headers = array(
		'Authorization: key=' . $apiKey,
		'Content-Type: application/json'
	);

	$ch = curl_init();
	curl_setopt( $ch,CURLOPT_URL, 'https://android.googleapis.com/gcm/send' );
	//curl_setopt( $ch,CURLOPT_URL, 'https://gcm-http.googleapis.com/gcm/send' );
	curl_setopt( $ch,CURLOPT_POST, true );
	curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
	curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
	curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
	curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
	$result = curl_exec($ch );
	curl_close( $ch );
	var_dump($result);
	//exit();
}*/

# getting the top data_str from ecg
$sql = "select deviceId, GROUP_CONCAT(data_str SEPARATOR ';') as final_str
	from (select data_str, deviceId
	from ecg_data
	order by id DESC)
	as tmp_table
	group by deviceId;";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
	$output = array();
	// output data of each row
	while($row = $result->fetch_assoc()) {
		$req_arr = explode(";", $row["final_str"]);
		$output[$row["deviceId"]] = array();
		foreach($req_arr as $item) {
			$output[$row["deviceId"]] = array_merge(json_decode($item, true), $output[$row["deviceId"]]);
		}
	}
}

$last_peak = -1;
$count = 0;
$avg_diff = 0;
function checkIfUserOK($device_data) {
	foreach($device_data as $val) {
		if($val[1] == 2.5 && $last_peak == -1) {
			$last_peak = $val[0];
			$count = 1;
			continue;
		}

		if($val[1] != 2.5)
			continue;

		if(($val[0] - $last_peak) > 0.7) {
			$avg_diff = (($avg_diff * $count) + ($val[0] - $last_peak))/($count + 1);
			$last_peak = $val[0];
			$count += 1;
		}

		if($avg_diff <= 0)
			$count = 1;

		if($count >= 3 && $avg_diff > 0.15) {
			return False;
		}
	}

	return True;
}

foreach($output as $deviceId => $device_output) {
	$isOK = checkIfUserOK($device_output);

	if(!$isOK) {
		echo "Sending notification ....\n\n";
		sendMessageThroughGCM(array('exfN2LrF7To:APA91bEmlZ71oMd224yB0MWfeiE5c1-oQ2QP6eH2BryPUYGUnTZCbN3jpkh_gbWGcIky97E6Z0MF5-IKd5BHAa7YSpY5pLPI02KUnE23Rh-K-iWGNBc6GSQvY-fHJrLe6ZT3oJjx0Vdg'),
			array("message" => "Oogway needs care.. Dragon warrior must fight!!", "deviceId" => 1, "avg_diff" => $avg_diff));
	} else {
		echo "Not sending notification ....\n\n";
	}
}


?>
