/**
 * 
 */
package edu.illinois.cs410.query_ranker.ui;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import edu.illinois.cs410.query_ranker.manager.QueryManager;


/**
 * @author conradharley
 *
 */
public class QueryRankerUI {
	/** Query Manager */
	public static QueryManager queryManager = null;
	
	/** console input */
	public static Scanner console = null;
	
	/**
     * Starts the application.
     * 
     * @param args command line arguments.
	 * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
    	
    	String queryString = "";
    	
    	if (args.length == 1) {
    		queryString= args[0];
    	} else {
        	queryString = "score";
    	}
    	
    	queryManager = new QueryManager(queryString);
    	System.exit(1);
     }
}
