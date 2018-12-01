### Installation Instructions ####
Copy the following files to server w/ PHP(or local machine with latest command-line version of java):

queryForm.php
viewDoc.php
QueryManager.jar

Copy the following Document Collection folder to the same folder as the above files:

./input

RUN COMMAND-LINE TEST(query keyword: Presidential Election):

java -jar QueryManager.jar 'Presidential Election'

EXPECTED RESULTS(Ranking function should produce a similar result + or - slight ranking differences on each run):


<CENTER>
<TABLE border=1><TR><TD width="33%"> <B>BM25</B></TD><TD width="33%"> <B>LMDirichletSimilarity </B><TD width="33%"><B>ClassicSimilarity(TFIDFSimilarity)</B></TD></TR>

<TR><TD><A HREF=viewDoc.php?docPath=input/bbc/politics/267.txt>UKIP's secret weapon?</A>(13.428829)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/politics/267.txt>UKIP's secret weapon?</A>(7.7453737)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/politics/249.txt>No election TV debate, says Blair</A>(1.4331249)</TD></TR><TR><TD><A HREF=viewDoc.php?docPath=input/bbc/politics/249.txt>No election TV debate, says Blair</A>(13.308205)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/politics/249.txt>No election TV debate, says Blair</A>(6.9932833)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/politics/397.txt>Blunkett hints at election call</A>(0.81812656)</TD></TR><TR><TD><A HREF=viewDoc.php?docPath=input/bbc/tech/001.txt>Ink helps drive democracy in Asia</A>(13.206427)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/tech/001.txt>Ink helps drive democracy in Asia</A>(6.930863)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/politics/194.txt>Cherie accused of attacking Bush</A>(0.8134903)</TD></TR><TR><TD><A HREF=viewDoc.php?docPath=input/bbc/politics/194.txt>Cherie accused of attacking Bush</A>(12.33762)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/politics/189.txt>Blair congratulates Bush on win</A>(6.561554)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/business/007.txt>Jobs growth still slow in the US</A>(0.8134903)</TD></TR><TR><TD><A HREF=viewDoc.php?docPath=input/bbc/business/007.txt>Jobs growth still slow in the US</A>(12.33762)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/business/294.txt>Wall Street cheers Bush victory</A>(6.561554)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/business/253.txt>Jobs growth still slow in the US</A>(0.8134903)</TD></TR><TR><TD><A HREF=viewDoc.php?docPath=input/bbc/business/253.txt>Jobs growth still slow in the US</A>(12.33762)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/tech/151.txt>'Blog' picked as word of the year</A>(6.218217)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/politics/267.txt>UKIP's secret weapon?</A>(0.8049731)</TD></TR><TR><TD><A HREF=viewDoc.php?docPath=input/bbc/tech/157.txt>Online games play with politics</A>(12.254427)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/politics/194.txt>Cherie accused of attacking Bush</A>(6.081932)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/politics/207.txt>February poll claim 'speculation'</A>(0.79840976)</TD></TR><TR><TD><A HREF=viewDoc.php?docPath=input/bbc/politics/189.txt>Blair congratulates Bush on win</A>(12.128608)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/business/007.txt>Jobs growth still slow in the US</A>(6.081932)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/politics/077.txt>Brown names 16 March for Budget</A>(0.72692454)</TD></TR><TR><TD><A HREF=viewDoc.php?docPath=input/bbc/business/294.txt>Wall Street cheers Bush victory</A>(12.128608)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/business/253.txt>Jobs growth still slow in the US</A>(6.081932)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/tech/001.txt>Ink helps drive democracy in Asia</A>(0.72404957)</TD></TR><TR><TD><A HREF=viewDoc.php?docPath=input/bbc/politics/149.txt>Blair returns from peace mission</A>(11.871071)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/tech/157.txt>Online games play with politics</A>(6.0639243)</TD><TD><A HREF=viewDoc.php?docPath=input/bbc/politics/354.txt>Brown names 16 March for Budget</A>(0.7136341)</TD></TR>			
</TABLE></CENTER>



### Software Implementation ###
The provided software uses an Eclipse project which encompasses 2 java files: QueryManager.java and QueryRankerUI.java.
Together these two files load the Lucene Java Library, read in a command-line query term string, and returns documents 
relevant to the search query. These documents are displayed via the console as a comma separated list which is later 
parsed by the enclosed PHP file. This PHP file resides on a web server with access to running external files such as the provided QueryManager.jar.


An example of this PHP implementation with the QueryManager.jar can be found at: 
https://iamhumanoid.com/conradharley/CS410/

The search results, in the above example, for the specified query are displayed with 3 provided columns in a table that show side by side comparison scores between BM25, LM Dirichlet Similarity and ClassicSimilarity(TFIDFSimilarity). 

Furthermore, the viewDoc.php file simply creates HTML links to the relevant documents in the ./input folder by utilizing the comma separated list generated by the QueryManager.java which also indexes this same ./input folder.  