/**
 * 
 */
package edu.illinois.cs410.query_ranker.manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
 
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.LMDirichletSimilarity;

/**
 * @author conradharley
 *
 */
public class QueryManager {
	/**
	 * Constructs a new QueryManager
	 * @throws Exception 
	 */
	public QueryManager(String queryString) throws Exception
	{
		
	// load SportsArticles using java NLP library of choice
		//Input folder
        String docsPath = "input";
         
        //Output folder
        String indexPath = "indexedFiles";
 
        //Input Path Variable
        final Path docDir = Paths.get(docsPath);
        
        String[][] outputRanking = new String[10][9];
        BufferedReader firstLineReader;
        String docTitle;
        try
        {
            //org.apache.lucene.store.Directory instance
            Directory dir = FSDirectory.open( Paths.get(indexPath) );
             
            //analyzer with the default stop words
            Analyzer analyzer = new StandardAnalyzer();
             
            //IndexWriter Configuration
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
             
            //IndexWriter writes new index files to the directory
            IndexWriter writer = new IndexWriter(dir, iwc);
             
            //Its recursive method to iterate all files and directories
            indexDocs(writer, docDir);
 
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        IndexSearcher searcher = createSearcher(indexPath);
       
        // BM25 Similarity
        searcher.setSimilarity(new BM25Similarity());
        
        TopDocs foundDocs = searchInContent(queryString, searcher);
        
        //Saves BM25Similarity to array 
        int i = 0;
        for (ScoreDoc sd : foundDocs.scoreDocs)
        {
        	
            Document d = searcher.doc(sd.doc);
            firstLineReader = new BufferedReader(new FileReader(d.get("path")));
            docTitle = firstLineReader.readLine();
            
            outputRanking[i][0] = d.get("path");
            outputRanking[i][1] = "" + sd.score + "";
            outputRanking[i][2] = docTitle;
            i++;
            //System.out.println(d.get("path"));
              
        }
     // LMDirichletSimilarity
        searcher.setSimilarity(new LMDirichletSimilarity());
        
        foundDocs = searchInContent(queryString, searcher);
        
        //Saves ClassicSimilarity to array 
        i = 0;
        for (ScoreDoc sd : foundDocs.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
            
            firstLineReader = new BufferedReader(new FileReader(d.get("path")));
            docTitle = firstLineReader.readLine();
            outputRanking[i][3] = d.get("path");
            outputRanking[i][4] = "" + sd.score + "";
            outputRanking[i][5] = docTitle;
            i++;
            //System.out.println(d.get("path"));   
        }
        
        // ClassicSimilarity
        searcher.setSimilarity(new ClassicSimilarity());
        
        foundDocs = searchInContent(queryString, searcher);
        
        //Saves ClassicSimilarity to array 
        i = 0;
        for (ScoreDoc sd : foundDocs.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
            
            firstLineReader = new BufferedReader(new FileReader(d.get("path")));
            docTitle = firstLineReader.readLine();
            outputRanking[i][6] = d.get("path");
            outputRanking[i][7] = "" + sd.score + "";
            outputRanking[i][8] = docTitle;
            i++;
            //System.out.println(d.get("path"));
              
        }
        
        
        for (int j = 0; j < outputRanking.length; j++)
        {
        	System.out.print(outputRanking[j][0] + " ___ " + outputRanking[j][1] + " ___ " + outputRanking[j][2] + " ___ ");
        	System.out.print(outputRanking[j][3] + " ___ " + outputRanking[j][4] + " ___ " + outputRanking[j][5] + " ___ ");
        	System.out.println(outputRanking[j][6] + " ___ " + outputRanking[j][7] + " ___ " + outputRanking[j][8]);
        
        }
        
	}
	
	public void loadDocuments() {
		
	}
	
	static void indexDocs(final IndexWriter writer, Path path) throws IOException
    {
        //Directory?
        if (Files.isDirectory(path))
        {
            //Iterate directory
            Files.walkFileTree(path, new SimpleFileVisitor<Path>()
            {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
                {
                    try
                    {
                        //Index this file
                        indexDoc(writer, file, attrs.lastModifiedTime().toMillis());
                    }
                    catch (IOException ioe)
                    {
                        ioe.printStackTrace();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        else
        {
            //Index this file
            indexDoc(writer, path, Files.getLastModifiedTime(path).toMillis());
        }
    }
 
    static void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException
    {
        try (InputStream stream = Files.newInputStream(file))
        {
            //Create lucene Document
            Document doc = new Document();
             
            doc.add(new StringField("path", file.toString(), Field.Store.YES));
            doc.add(new LongPoint("modified", lastModified));
            doc.add(new TextField("contents", new String(Files.readAllBytes(file)), Store.YES));
             
            //Updates a document by first deleting the document(s)
            //containing <code>term</code> and then adding the new
            //document.  The delete and then add are atomic as seen
            //by a reader on the same index
            writer.updateDocument(new Term("path", file.toString()), doc);
        }
    }
    private static TopDocs searchInContent(String textToFind, IndexSearcher searcher) throws Exception
    {
        //Create search query
        QueryParser qp = new QueryParser("contents", new StandardAnalyzer());
        Query query = qp.parse(textToFind);
         
        //search the index
        TopDocs hits = searcher.search(query, 10);
        return hits;
    }
 
    private static IndexSearcher createSearcher(String indexPath) throws IOException
    {
        Directory dir = FSDirectory.open(Paths.get(indexPath));
         
        //It is an interface for accessing a point-in-time view of a lucene index
        IndexReader reader = DirectoryReader.open(dir);
         
        //Index searcher
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }
}
