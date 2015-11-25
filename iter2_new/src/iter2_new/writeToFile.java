package iter2_new;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Vector;
import java.io.FileReader;
import java.io.FileWriter;

public class writeToFile extends CachedTrie {
	public static void main(String[] args) throws IOException{
		ParseAgain.runParsing();
		//initialization
		System.out.println("you have playListMap size"+playListMap.size());
		System.out.println("you have songMap size"+songMap.size());
		//some song doesn't exist in songList
		GetSongHeat.getSongHeat();
		System.out.println("you generated songHeat map with size"+ songHeatMap.size());
		//get top 1024 playList
		maintain1024.InitMaxMinHeap();

		//includes unused index 0
		System.out.println("max heap size is"+maintain1024.maxheap.size);
		System.out.println("min heap size is"+maintain1024.minheap.size);
		
		System.out.println("***************");
		System.out.println("begin to write top 1024 playList");
		writeTop1024();
		
		System.out.println("now get top 8");
		maintain1024.getTop8();
		System.out.println("now get top 128");

		maintain1024.getTop128();
		//writeTop128();
		System.out.println("successfully initialize top 1024 playList to file top1024.xml");
		System.out.println("***************");
		
		CachedTrie.constructTree();
		System.out.println("successfully initialize trie");
		System.out.println("***************");
		
		
		System.out.println("search song");
		int i=0;
		while(i<5){
		userInput.searchSong();
		i++;
		}
			
		
		//test load more data
		
		userInput.uploadFile();
		writeTop1024();
		System.out.println("1024.xml has been updated");
		System.out.println("");
		for(int count = 0; count<3; count++){
			userInput.searchSong();
		}
		
	
	}
	
	
	/*
	public static void writeTop8(){
		int[] top8= maintain1024.maxheap.getTop8();
		String fileName="top8.xml";
		try{
			//print maxheap to file 
			FileWriter writer=new FileWriter(fileName);
			BufferedWriter bufferedWriter=new BufferedWriter(writer);
			
			bufferedWriter.write("<CATALOG>");
			bufferedWriter.newLine();
			for(int i=0;i<top8.length;i++){
				bufferedWriter.write("    <list>");
				bufferedWriter.newLine();
				int playListId=top8[i];
				bufferedWriter.write("id : "+playListId);

				//System.out.println("current playlist id is"+playListId);
				bufferedWriter.write("      <order>");
				bufferedWriter.write(Integer.toString(i));
				bufferedWriter.write(" </order>");
				bufferedWriter.newLine();


				

				Vector<Integer> songList=playListMap.get(playListId);
				//System.out.println("heat is"+songList.get(0));
				bufferedWriter.write("      <song>");
				
				for(int j=1;j<songList.size();j++){
					String songName=songMap.get(songList.get(j)). replace("&"," and ");     

					bufferedWriter.write(songName+",");
					
				}
				bufferedWriter.newLine();
				bufferedWriter.write("      </song>");
				bufferedWriter.newLine();
				bufferedWriter.write("      <heat>");
				bufferedWriter.write(Integer.toString(songList.get(0)));
				bufferedWriter.write(" </heat>");
				bufferedWriter.newLine();
				bufferedWriter.write("    </list>");
				bufferedWriter.newLine();
			}
			bufferedWriter.write("</CATALOG>");
			bufferedWriter.close();
			}
			catch(IOException ex){
				System.out.println("error");
			}	
		}
		
	*/
	
	
	
	public static void writeTop1024() throws IOException{
		String fileName="top1024.xml";

		try{
		//print maxheap to file 
		FileWriter writer=new FileWriter(fileName);
		BufferedWriter bufferedWriter=new BufferedWriter(writer);
		
		bufferedWriter.write("<CATALOG>");
		bufferedWriter.newLine();
		for(int i=1;i<=maintain1024.maxheap.size;i++){
			bufferedWriter.write("    <list>");
			bufferedWriter.newLine();
			int playListId=maintain1024.maxheap.maxheap[i];
			bufferedWriter.write("id : "+playListId);

			//System.out.println("current playlist id is"+playListId);
			bufferedWriter.write("      <order>");
			bufferedWriter.write(Integer.toString(i));
			bufferedWriter.write(" </order>");
			bufferedWriter.newLine();


			

			Vector<Integer> songList=playListMap.get(playListId);
			//System.out.println("heat is"+songList.get(0));
			bufferedWriter.write("      <song>");
			
			for(int j=1;j<songList.size();j++){
				String songName=songMap.get(songList.get(j)). replace("&"," and ");     

				bufferedWriter.write(songName+",");
				
			}
			bufferedWriter.newLine();
			bufferedWriter.write("      </song>");
			bufferedWriter.newLine();
			bufferedWriter.write("      <heat>");
			bufferedWriter.write(Integer.toString(songList.get(0)));
			bufferedWriter.write(" </heat>");
			bufferedWriter.newLine();
			bufferedWriter.write("    </list>");
			bufferedWriter.newLine();
		}
		bufferedWriter.write("</CATALOG>");
		bufferedWriter.close();
		}
		catch(IOException ex){
			System.out.println("error");
		}	
	}
	
	/*
	public static void writeTop128(){
		int[] top128= maintain1024.maxheap.getTop128();
		String fileName="top128.xml";
		try{
			//print maxheap to file 
			FileWriter writer=new FileWriter(fileName);
			BufferedWriter bufferedWriter=new BufferedWriter(writer);
			
			bufferedWriter.write("<CATALOG>");
			bufferedWriter.newLine();
			for(int i=0;i<top128.length;i++){
				bufferedWriter.write("    <list>");
				bufferedWriter.newLine();
				int playListId=top128[i];
				bufferedWriter.write("id : "+playListId);

				//System.out.println("current playlist id is"+playListId);
				bufferedWriter.write("      <order>");
				bufferedWriter.write(Integer.toString(i));
				bufferedWriter.write(" </order>");
				bufferedWriter.newLine();


				

				Vector<Integer> songList=playListMap.get(playListId);
				//System.out.println("heat is"+songList.get(0));
				bufferedWriter.write("      <song>");
				
				for(int j=1;j<songList.size();j++){
					String songName=songMap.get(songList.get(j)). replace("&"," and ");     

					bufferedWriter.write(songName+",");
					
				}
				bufferedWriter.newLine();
				bufferedWriter.write("      </song>");
				bufferedWriter.newLine();
				bufferedWriter.write("      <heat>");
				bufferedWriter.write(Integer.toString(songList.get(0)));
				bufferedWriter.write(" </heat>");
				bufferedWriter.newLine();
				bufferedWriter.write("    </list>");
				bufferedWriter.newLine();
			}
			bufferedWriter.write("</CATALOG>");
			bufferedWriter.close();
			}
			catch(IOException ex){
				System.out.println("error");
			}	
		}
		*/
		

}
