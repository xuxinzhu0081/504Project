
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;




public class Trie{
    public static TrieNode trie = createTrie();           // Create a Trie with root '#'

	public static class TrieNode{

	    char letter;                            // stores the letter for each node
	    TrieNode[] children;                    // array of the children of a node
	    boolean endofWord;                      // true if the letter ends a word

	    // constructor for TrieNode class
	    TrieNode(char letter)
	    {
	        this.letter = letter;
	        children = new TrieNode[26];         // creates an array of 26 TrieNodes
	        this.endofWord = false;              // marks the end of word
	    }
	}
    
    /**
     * Creates a Trie with root node '#'
     *
     * @return root TrieNode
     */
    static TrieNode createTrie()
    {
        return(new TrieNode('#'));            // '#' denotes root of the Trie
    }
    
     /**
      * Receives a string as input and returns a new string which can be stored
      * in the trie, also accepts Uppercase input and converts it to lowercase
      *
      * @param word    String input
      * @return        Returns fixed String
      */

	static String fixString(String word) {
		int index = 0;              //starting index is 0

		// convert the string to lower case
		word=word.toLowerCase();

		// convert the String to an array of chars to easily
		// manipulate each char
		char[] oldChars = word.toCharArray();          // holds the old String
		char[] newChars = new char[word.length()];     // will make up the new String

		// loop until every char in oldChars is tested
		for( int x=0 ; x<oldChars.length ; x++ ) {
			// accept all alphabetic characters only
			if(oldChars[x]>='a' && oldChars[x]<='z') {
				newChars[index++]=oldChars[x];
			}
                        else{
                            System.out.println("Invalid input");
                            System.exit(0);
                        }

		}

		//return a String consisting of the characters in newChars
		return (new String(newChars));
	}

/**
     * Inserts words taken as input into the Trie.
     *
     * @param root     Root of the created Trie
     * @param word     Word to be inserted into the Trie
     */

    static void insertWord(TrieNode root, String word)
    {
        int offset = 97;                         // offset of be subtracted from character
        int size = word.length();                // stores the size of the word
        TrieNode currentNode = root;
        word=fixString(word);                    // converts string to valid string input
        char[] characters = word.toCharArray();  // convert string to character array
        

        // stores letters from the word in subsequent child nodes
        for (int i = 0; i < size; i++){

            if (currentNode.children[characters[i]-offset] == null)
                /* Create a new node */
                currentNode.children[characters[i]-offset] = new TrieNode(characters[i]);

                // go to next node
                currentNode = currentNode.children[characters[i]-offset];
        }
        //
        currentNode.endofWord = true;
    }

    /**
     * Searches for a given word in the created Trie.
     * Returns true if word is found, false if not.
     *
     * @param root    Root of the created Trie
     * @param word    Word to be searched in the Trie
     * @return        True if word is found, false if not
     */
    static boolean findWord(TrieNode root, String word)
    {
        int offset = 97;
        TrieNode currentNode = root;
        word= fixString(word);                            // fix input word
        char[] characters = word.toCharArray();
        int size = characters.length;
        int index;                                      // keep track of letters

        // loop until last character in the word
        for (index = 0; index < size; index++){

            if (currentNode == null)
                return false;
            // go to next node
            currentNode = currentNode.children[characters[index]-offset];
        }

        // branch is shorter than word
        if (index == size && currentNode == null)
            return false;

        // branch is longer than word
        if (currentNode != null && !currentNode.endofWord)
            return false;

        return true;
    }

    /**
     * Searches for  words prefix with "ck" substrings.
     * Prints all the words when the substring "ck" is found
     *
     * @param root             Root of the created Trie
     * @param level            Used to keep count of the characters in the word
     * @param collectLetters   Used to collect the characters in each branch of a Trie.
     * @param substring        Used to store the substring "ck" that is to be searched.
     */
    static void searchSubstring(TrieNode root, int level, char[] collectLetters, String substring)
    {
        boolean found=false;            // True if Substring "ck" is found in a word

        if (root == null)
            return;

        // loop over all the children of TrieNode
        for (int i = 0; i < root.children.length; i++){

            collectLetters[level] = root.letter;
            // recursive call to searchSubstring
            searchSubstring(root.children[i], level+1, collectLetters,substring);
        }

        // if last word is reached
        if (root.endofWord){

            // go through all letters collects from a branch of Trie
            // and search for "ck", set fouund = true if found.
            for (int j = 1; j <= level; j++){
             if(collectLetters[j]==substring.charAt(0) && collectLetters[j+1]==substring.charAt(1)){
                  found=true;
                  break;
                }
            }

            // if "ck is found" in a word, print it
            if(found){
                  // print word
                  for (int j = 1; j <= level; j++){
                       System.out.print(collectLetters[j]);
                  }
                System.out.println();
            }       
        
         }
    }

    /**
     * Prints all the words in the constructed Trie
     *
     * @param root               Root of the created Tire
     * @param level              Used to keep count of the characters in the word
     * @param collectLetters     Used to collect the characters in a branch of a Trie.
     */

static void printTrie(TrieNode root, int level, char[] collectLetters)
    {
        if (root == null)
            return;

        // loop over all the children of TrieNode
        for (int i = 0; i < root.children.length; i++)
        {
            collectLetters[level] = root.letter;
             // recursive call to searchSubstring
            printTrie(root.children[i], level+1, collectLetters);
        }

         // if last word is reached
        if (root.endofWord)
        {
            // print the word
            for (int j = 1; j <= level; j++)
                System.out.print(collectLetters[j]);
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException
    {
    		process();
            //int choice=0;                           // select operations for user
            String insertWord;                      // insert word into the Trie
            //String searchWord;                      // search a word in the Trie
            //String searchString="ck";               // substring to be searched
                      
            char[] collectLetters = new char[50];           // stores characters in a branch of Trie
           
            Iterator<Entry<Integer,String>> songMapItr=songMap.entrySet().iterator();
            while(songMapItr.hasNext()){
                Map.Entry<Integer,String> entry =(Map.Entry<Integer,String>)songMapItr.next();
                int SID=entry.getKey();
                //String song=entry.getValue();
                String song=getSongName(SID);
               // System.out.println(song);
                
                insertWord(trie,song);
                //System.out.println("SID "+SID+"song "+song);
            }
            System.out.println("Enter the word you want to find");
            String searchString="greenonions";
            searchSubstring(trie, 0, collectLetters,searchString);
            


            /*do{
                 // Menu of Operations
                 System.out.println("\nEnter the choice of operation: \n 1)Insert into Trie \n "
                    + "2)Find a word in Trie"+ "\n 3)Find words with 'ck'\n "
                    + "4)Print all words in Trie \n 5)Quit");

                 Scanner userinput=new Scanner(System.in);
                 choice = userinput.nextInt();
                
                switch(choice){

                    // Insert word into the Trie
                    case 1:
                        System.out.println("Enter the word to be inserted:");
                        insertWord=userinput.next();
                        insertWord(trie, insertWord);
                        System.out.println("Word inserted!!");
                        break;

                     // Search for a word in Trie
                    case 2:
                           System.out.println("Enter the word you want to find");
                        searchWord=userinput.next();
                        if (findWord(trie, searchWord))
                        {
                            System.out.println("The word was found");
                        }
                        else
                        {
                            System.out.println("The word was NOT found");
                        }
                        break;

                      // Search for substring "ck"
                     case 3:
                         searchSubstring(trie, 0, collectLetters,searchString);
                         break;

                      // Print all the words in the Trie
                     case 4:
                         System.out.println("Words in Trie:");
                         printTrie(trie, 0, collectLetters);
                         break;

                      // Exit
                     case 5:
                        System.exit(0);

                     default:
                        System.out.println("Enter correct choice");
                        break;

                }
              
            }while(true);
            */
    }
}
