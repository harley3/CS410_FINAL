<form method="post" action="test1.php" target=_new><label for="Name">Query Form:</label><input type="text" name="query" id="query" /><input type="submit" name="submit" value="Submit" class="submit-button" /></form>
		
<HR>
	
<?php
$docPATH = $_GET['docPath'];
$fh = fopen($docPATH, 'r');

$pageText = fread($fh, 25000);

 echo nl2br($pageText);

echo file_get_contents($docPATH);
fclose($fh);

?>