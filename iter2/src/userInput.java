import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class userInput extends CachedTrie{
	
	/*  1. load top 128 playlists along with popularity
	 *  2. load single playlist along with popularity through form page(within 1024)
	 *  3. list top 8 playlists
	 *  4. given a song suggest the most popular list
	 *  5. return top 4 related song when user type a song
	 */
	public static void main (String[] args) throws IOException{
		//initialize playListMap, songListMap, calculate songHeat
		
		CachedTrie();
		while(true){
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			String name=br.readLine();
			int[] res= findSubstring(name);

			if(name=="")
				break;
		}
		}
		
		
	}

