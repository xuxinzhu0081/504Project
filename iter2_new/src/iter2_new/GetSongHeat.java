package iter2_new;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

public class GetSongHeat extends ParseAgain{
	//songHeatMap: ID||Heat,playListId 
	//public static HashMap<Integer,SongCell> songHeatMap=new HashMap<>();
	//public static HashMap<Integer,Vector<Integer>> playListMap= new HashMap<Integer,Vector<Integer>>();
	
	public static void getSongHeat(){
		//iterate each entry in playlistMap and add value 
		for(int i=0;i<playListMap.size()-1;i++){
			Vector<Integer> songList=playListMap.get(i);
			
			int playListId=i;
			//iterate from the second element
			int heat=songList.elementAt(0);

			for(int j=1;j<songList.size();j++){
				int songID=songList.elementAt(j);
				if(songHeatMap.containsKey(songID)){
					//update the most pop list
					
					SongCell song=songHeatMap.get(songID);
					int current_pop=song.popListID;
					if(heat>playListMap.get(current_pop).elementAt(0)){
						current_pop=playListId;
						song.popListID=current_pop;
					}
					
					//update heat
					song.setHeat(heat);
					
				}
				else{

					SongCell song=new SongCell(songID,playListId);
					song.setHeat(heat);
					songHeatMap.put(songID, song);	
					
				}
			}	
		}
			
	}
/* helper encapsulation function	*/
	public static int getHeat(int id){
		if(songHeatMap.containsKey(id))
			return songHeatMap.get(id).heat;
		else return -1;
	}
	//only maintains char array , exclude everything
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
	          //System.out.println(" SID "+SID+" heat "+song.heat);
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

