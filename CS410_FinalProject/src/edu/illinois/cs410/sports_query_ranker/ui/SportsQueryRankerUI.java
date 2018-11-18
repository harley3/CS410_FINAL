/**
 * 
 */
package edu.illinois.cs410.sports_query_ranker.ui;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import edu.illinois.cs410.sports_query_ranker.manager.SportsQueryManager;


/**
 * @author conradharley
 *
 */
public class SportsQueryRankerUI {
	/** Query Manager */
	public static SportsQueryManager queryManager = null;
	
	/** console input */
	public static Scanner console = null;
	
	/**
     * Starts the application.
     * 
     * @param args command line arguments.
	 * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
    	
    	// SportsQueryManager(articleFilePrefix, pathName)
  
    	String queryString = "";
    	
    	if (args.length == 1) {
    		queryString= args[0];
    	} else {
        	queryString = "score";
    	}
    	
    	queryManager = new SportsQueryManager(queryString);
    	System.exit(1);
     }
}
