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
	
	public static void searchSong() throws IOException{
		
			System.out.println("type the song you want to search");
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			String name=br.readLine();
						int res[]=findSubstring(name);
			//resolve order
			//res=CachedTrie.orderResult(res);		
						
			if(findSubstring(name)!=null){
				for(int i=0;i<res.length;i++){
					System.out.println(songMap.get(res[i]));
				}
			}
	}
	
	
	public static void uploadFile()throws IOException{
		System.out.println("please type the file you want to upload (eg.day00.txt" );
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String fileName =br.readLine();
		load.updateSource(fileName);
		load.updateSongHeat();
		//printSongHeatMap();
		load.updateMinMaxHeap();
		load.updateTrieNode();

		
	}
	
	
	/*
	public static void main (String[] args) throws IOException{
		//init playListMap, songMap, songHeatMap
		ParseAgain.runParsing();
		GetSongHeat.getSongHeat();
		printSongHeatMap(5);

		
		ParseAgain.printPlayListMap(playListMap);
		ParseAgain.printSongHashMap();
		
		//get top 1024
		maintain1024.InitMaxMinHeap();
		CachedTrie.constructTree();
		while(true){
			searchSong();
			uploadFile();
		}
		//printSongHeatMap(5);

		
	}	
	*/	
}

