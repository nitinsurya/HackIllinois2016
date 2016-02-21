<?php

include("db.php");

# deleting the old data
#$sql = "delete from ";


if(!empty($_GET["deviceId"])) {
	# getting the top data_str from ecg
	$sql = "select deviceId, GROUP_CONCAT(data_str SEPARATOR ';') as final_str
		from (select data_str, deviceId
			from ecg_data
			where deviceId=" . $_GET["deviceId"] . "
			order by id DESC)
		as tmp_table
		group by deviceId;";

} else {
	# getting the top data_str from ecg
	$sql = "select deviceId, GROUP_CONCAT(data_str SEPARATOR ';') as final_str
		from (select data_str, deviceId
			from ecg_data
			order by id DESC)
		as tmp_table
		group by deviceId;";
}

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
	echo json_encode($output);
} else {
	echo json_encode(array());
}

?>
