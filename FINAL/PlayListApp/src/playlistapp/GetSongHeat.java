package playlistapp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

public class GetSongHeat extends ParseAgain{
	//songHeatMap: ID||Heat,playListId 
	
	public static void getSongHeat(){
		System.out.println("you are generating songHeat map based on playList map (sourcesize is" +playListMap.size()); 
		Iterator it=playListMap.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<Integer, Vector> pair=(Map.Entry)it.next();
			Vector<Integer> songList=pair.getValue();
			int playListId=pair.getKey();
			int heat=songList.elementAt(0);
			
			//System.out.println("playListis is "+ playListId);
			//System.out.println("its heat is "+ heat);
			//System.out.println("*******************");

			for(int j=1;j<songList.size();j++){
				
				int songID=songList.elementAt(j);
				//System.out.println("song is "+ songID);

				if(songHeatMap.containsKey(songID)){
					SongCell song=songHeatMap.get(songID);
					int current_pop=song.popListID;
					if(heat>playListMap.get(current_pop).elementAt(0)){
						current_pop=playListId;
						song.popListID=current_pop;
					}
					
					song.setHeat(heat);
					
				}
				else{

					SongCell song=new SongCell(songID,playListId);
					song.setHeat(heat);
					songHeatMap.put(songID, song);	
					
				}
			}
			//System.out.println("*******************");
		}
			
	}
	
/* helper encapsulation function	*/
	public static int getHeat(int id){
		if(songHeatMap.containsKey(id))
			return songHeatMap.get(id).heat;
		else return -1;
	}
	public static String getSongName(int id){	
		String name=songMap.get(id);
		name=name.replaceAll("[^a-zA-Z]", "");
		return name;
	}
	
	
	
	
/* help function to debug*/
	
	public static void printSongHeatMap(int count){
		int i=0;
	      Iterator<Entry<Integer,SongCell>> songHeatMapItr=songHeatMap.entrySet().iterator();
	      while(songHeatMapItr.hasNext()&&i<count){
	          Map.Entry<Integer,SongCell> entry =(Map.Entry<Integer,SongCell>)songHeatMapItr.next();
	          int SID=entry.getKey();
	          SongCell song=entry.getValue();
	          System.out.println(" SID "+SID+" heat "+song.heat);
	          song.ListSongInfo();
	          i++;
	      }
	}
	
	public static void debugSongHeatMap(){
		  SongCell song=songHeatMap.get(0);
	      int pid=song.popListID;
	      int heat=playListMap.get(pid).elementAt(0);
	      System.out.println(heat);
	      song.ListSongInfo();
	      
	}
	
}

