<?php
$querySTR = $_POST['query'];
exec("java -jar SportsQueryManager.jar " . $querySTR . " 2>&1", $output);
?>
<CENTER><H1>Search Results for: <?php echo $querySTR; ?></H1></CENTER>

<form method="post" action="queryForm.php" target=_new><label for="Name">Query Form:</label><input type="text" name="query" id="query" /><input type="submit" name="submit" value="Submit" class="submit-button" /></form>
		
<HR>
<?php

$docRanking = null;



 for ($i = 0; $i < count($output); $i++) {
	///print_r($output[$i]);
	 echo "<A HREF=viewDoc.php?docPath=". $output[$i] . "> Text Document: " .  $output[$i] . "</A><BR>";
	echo "<BR>";
}

?>			

