<?php
$querySTR = $_POST['query'];
exec("java -jar -Xmx512M QueryManager.jar '" . $querySTR . "' 2>&1", $output);
?>
<CENTER><H1>Search Results for: <?php echo $querySTR; ?></H1></CENTER>

<form method="post" action="queryForm.php" target=_new><label for="Name">Query Form:</label><input type="text" name="query" id="query" value="<?php echo $querySTR; ?>" /><input type="submit" name="submit" value="Submit" class="submit-button" /></form>
		
<HR>
<?php

$docRanking = null;
?>

<CENTER>
<TABLE border=1><TR><TD width="33%"> <B>BM25</B></TD><TD width="33%"> <B>LMDirichletSimilarity </B><TD width="33%"><B>ClassicSimilarity(TFIDFSimilarity)</B></TD></TR>

<?php
 for ($i = 0; $i < count($output); $i++) {
	 $str = '' . $output[$i] . '';
	 $arrayRank = split(' ___ ', $str);
	 
	 echo "<TR><TD><A HREF=viewDoc.php?docPath=". $arrayRank[0] . ">" . $arrayRank[2] . "</A>(" . $arrayRank[1] . ")</TD><TD><A HREF=viewDoc.php?docPath=". $arrayRank[3] . ">"  .  $arrayRank[5] . "</A>(" . $arrayRank[4] . ")</TD><TD><A HREF=viewDoc.php?docPath=". $arrayRank[6] . ">" . $arrayRank[8] . "</A>(" . $arrayRank[7] . ")</TD></TR>";
	 
}


?>			
</TABLE></CENTER>
