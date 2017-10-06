/* I have chosen to use Java as it has built-in hash-tables, hash-maps and arrays in its core SDK
Jsut as the exam suggested

NOTE: Big O notation for complexity is inserted as comments in the functions.
Even though, the exam says 4 queries on second page, the first page shows 3 queries and these are
the ones I calculated the big O notation for
*/

package idbms;
import java.util.*;
/**
 *
 * @author tdusabir
 */
public class IDBMS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        iDBMSHelpers idh = new iDBMSHelpers();
        
        System.out.println("Enter your Query:");
        Scanner sc = new Scanner(System.in);
        // Let the user enter a command
        String input = sc.nextLine();
        String[] tokens = idh.tokenizeLine(input);
        //hashtable that contains main database
        Hashtable<String,Hashtable<String,LinkedList<String>>> db = new Hashtable<String,Hashtable<String,LinkedList<String>>>();
        
        /* Have used hash table as it stores a key-value pair, and retrive value from hash table 
           is very quick - O(1).
           Have also chosen to make both key and value string (Assumption)
           Used linked list for value as one key may contain many values, but a key is unique
           Unlike arrays,the other advantage of Linked lists is that they are of variable length 
        */
        Hashtable <String, LinkedList<String>> ht = new Hashtable <String, LinkedList<String>>();
        
        /*Have made keywords case insensitive by uppercasing all values 
          Here I check syantax errors: if no errors are found, codes are executed, otherwise
          the user will get an error message
          Note that compareTo() for string returns 0 if the strings are equal 
        */
        
        /*
            BIG OH NOTATION: The CREATE query will be O(n) as inserting values (keys) in hash tables is 0(1), and
            the for loop to loop for key and values will be O(n), that is, the program execution will slow down
            as the query lenght increases, though this is rare. Best case sceneario will be O(1)- very fast, 
            while the worst case will be O(n) due to the query length.
            */
        try{
        if(tokens[0].toUpperCase().compareTo("create".toUpperCase())== 0 && idh.isCreateddlOK(input)
                && !db.contains(tokens[2])){            
            
            String tableName = tokens[2];
            String[] cols = idh.tokenizeColsAndVals(tokens[3]);
            
            // Linked list to contain values, at the beginning all values are empty.
            LinkedList<String> list = new LinkedList<String>();
            for(int i =0; i<cols.length ; i++){
                // creating a table - with keys and null values
                ht.put(cols[i], list);
                //store tablename and its data
            db.put(tableName, ht);
            } 
        }}catch(Exception e){
            System.err.println("Invalid SQL command, or table already exists in the database!"); 
        }
        
        /*
            BIG OH NOTATION: The INSERT query will be O(n) as inserting values in hash tables is 0(1), and
            the for loop to loop for key and values will be O(n), that is, the program execution will slow down
            as the query lenght increases, though this is rare to have long queries that can notably slow down
            the system. Best case sceneario will be O(1)- very fast, while the worst case will be O(n) due to the 
            query length
            */
         
        try{
        if(tokens[0].toUpperCase().compareTo("insert".toUpperCase())== 0 && idh.isInsertdmlOK(input)
          && db.contains(tokens[2])){
            String[] columns = idh.tokenizeColsAndVals(tokens[3]);
            String[] values = idh.tokenizeColsAndVals(tokens[5]);
       
            LinkedList<String> list = new LinkedList<String>();
            for(int i =0; i<columns.length ; i++){
                // creating a linked list of values with the same key
                list.add(i, values[i]);
                // adding a list of values with the same key in the table
                ht.put(columns[i], list);
            }
        }}catch(Exception e){
            System.err.println("Invalid SQL command or table does not exist in the database!");
        }
        
        /*
        BIG OH NOTATION: The SELECT query will also have O(n) as worst case scenario and 0(1) for best 
        case scenario. the reasons are similar to ones given above: Hash table insertion and fetching of data
        is very fast - O(1), and the probable reason to slow the system down is the length of the queries, which
        are normaly not long to cause probllems.
        */
        try{
        if(tokens[0].toUpperCase().compareTo("select".toUpperCase())== 0 && idh.isSelectdmlOK(input)
          && db.contains(tokens[3])){
            String[] selectedVal = idh.tokenizeColsAndVals(tokens[1]);
                
                // fetching where clause
                String[] whereClauseVar = idh.whereClause(tokens[5]);
                // getting the key in the where clause
                String key = whereClauseVar[0];
                // getting the condition value in the where clause
                String value = whereClauseVar[1];
                //Only print out (show) values wchich fullfil the condition
                
            if(key.compareTo(value)==0){    
                for(int i = 0; i<selectedVal.length; i++){
                    System.out.println(ht.get(selectedVal[i]));
                }            
            }
        }}catch(Exception e){
            System.err.println("Invalid SQL command or table does not exist in the database!");
        }
    }    
}
