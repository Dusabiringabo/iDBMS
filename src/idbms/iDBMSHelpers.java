/*
 This class contains helper functions for the iDBMS
 */
package idbms;
import java.util.*;

/**
 *
 * @author tdusabir
 */
public class iDBMSHelpers {
    
    // Tokeinizing a line with a space character. I assumed other delimiters are not allowed
    public String[] tokenizeLine(String line){
        String[] result = line.trim().split(" ");
    return result;
    }
    
    // Checking the data definition language (CREATE) syntax - case insensitive
    boolean isCreateddlOK(String createTable){
        String line[] = tokenizeLine(createTable);
        if(line[0].toUpperCase().compareTo("create".toUpperCase())!= 0 || 
                line[1].toUpperCase().compareTo("table".toUpperCase())!= 0 || 
                line[3].indexOf("(")==-1 || line[3].indexOf(")")==-1 ){
            return false;
        }
        return true;
    }
    
    // Checking the data manipulation language (INSERT) syntax - case insensitive
    boolean isInsertdmlOK(String createTable){
        String line[] = tokenizeLine(createTable);
        if(line[0].toUpperCase().compareTo("insert".toUpperCase())!= 0 || 
                line[1].toUpperCase().compareTo("into".toUpperCase())!= 0 || 
                line[3].indexOf("(")==-1 || line[3].indexOf(")")==-1 || 
                line[4].toUpperCase().compareTo("values".toUpperCase())!= 0 
                || line[5].toUpperCase().indexOf("(")==-1 || line[5].indexOf(")")==-1 ){
            return false;
        }
        return true;
    }
    
    //// Checking the data manipulation language (SELECT) syntax - case insensitive
    boolean isSelectdmlOK(String createTable){
        String line[] = tokenizeLine(createTable);
        if(line[0].toUpperCase().compareTo("select".toUpperCase())!= 0 || 
                line[2].toUpperCase().compareTo("from".toUpperCase())!= 0 || 
                line[4].toUpperCase().compareTo("Where".toUpperCase())!= 0 ){
            return false;
        }
        return true;
    }
    
    /*Getting rid of single quotations before proceeding
      The assumption is that only single quotations are allowed
    */
    public String[] tokenizeColsAndVals(String token){
        String newToken = token.replace("(", "");
        newToken = newToken.replace(")", "");
        String[] result = newToken.trim().split(",");
        for(int i=0; i< result.length; i++){
            result[i] = result[i].replaceAll("'", "");
        }
        return result;
    }
    
    /* Tokenizing where clause to be able to find key to be used in retrieving data that fullfill the
       condition
    */
    public String[] whereClause(String clause){
        String[] result = clause.trim().split("=");
        for(int i=0; i< result.length; i++){
            result[i] = result[i].replaceAll("'", "");
        }
        return result;
    }
}
