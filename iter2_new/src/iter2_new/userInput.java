package iter2_new;
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
		//CachedTrie();
		runParsing();
		initTable();
		getSongHeat();
		constructTree();
		
		//printPlayListMap();

		while(true){
			System.out.println("type name");
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			String name=br.readLine();
			if(name=="123"){
				break;
			}
			int[] res= findSubstring(name);
			
			
			if(findSubstring(name)!=null){
				for(int i=0;i<res.length;i++)
					System.out.println(songMap.get(res[i]));
			}
			
			else continue;	
		}
		
		return;
	}		
}

