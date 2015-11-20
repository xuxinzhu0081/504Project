package iter2_new;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

import iter2_new.ParseAgain.SongCell;

public class load extends maintain1024{
	public static HashMap<Integer,Vector<Integer>> playListMap_day=new HashMap<>();
	public static void updateSource() throws IOException{
		System.out.println("please type file name");
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		//String file =br.readLine();
		String fileName="day02.txt";
		
		//update song list heat
		int PID=0;
		String line=null;
	     try {
	        FileReader fileReader=new FileReader(fileName);
	        BufferedReader bufferedReader=new BufferedReader(fileReader);
	         while ((line=bufferedReader.readLine())!=null){
	            //process each line in some way
	            //put last digit as heat in the beginning of vector, and read each number into hashmap
	            String [] token=line.split("\\s+");
	            Vector<Integer> v=new Vector<Integer>();
	            int length=token.length;
	            //pass heat as first element in token          
	            v.add(Integer.parseInt(token[length-1]));
	            for(int i=0;i<length-1;i++){  
	              v.add(Integer.parseInt(token[i]));
	            }
	            playListMap_day.put(PID,v);
	            PID++;
	          }
	          bufferedReader.close();
	      }
	      catch(FileNotFoundException ex){
	        System.out.println("unable to open file"+fileName);
	      }
	      catch(IOException ex){
	        ex.printStackTrace();
	      }
	}
	
	//need to do it
	public static void updateSongHeat(){
		Iterator<Entry<Integer,Vector<Integer>>> MapItr=playListMap_day.entrySet().iterator();
		int count=0;
	      while(MapItr.hasNext()){
	          Map.Entry<Integer,Vector<Integer>> entry =(Map.Entry<Integer,Vector<Integer>>)MapItr.next();
	          int heat=entry.getValue().elementAt(0);
	          //System.out.println("in day pid is "+entry.getKey()+" heat is "+ heat);
	          Vector<Integer> songList=entry.getValue();

	      		//iterate each entry in playlistMap and add value 
	      		
	      			for(int j=1;j<songList.size();j++){
	      				int songID=songList.elementAt(j);
      					System.out.println("-----------");

	      				//System.out.println("current sid "+songID);
	      				if(songHeatMap.containsKey(songID)){
	      					//update the most pop list
	      					SongCell song=songHeatMap.get(songID);
	      					int prev_pid=song.popListID;
	      					//System.out.println("previous Heat list is "+prev_pid+"top heat"+playListMap.get(prev_pid).elementAt(0));
	      					
	      					//if top songplaylist the song lies in change, add it into playListMap
							if(heat>playListMap.get(prev_pid).elementAt(0)){
	      						playListMap.put(playListMap.size(), songList);
	      						int current_pop_pid=playListMap.size();
	      						song.popListID=current_pop_pid;
	      					}
	      					//update heat
							//System.out.println("prev heat "+ song.heat);
	      					song.setHeat(heat);
	      					//System.out.println("sid is "+ songID+ " after update song heat is"+song.heat);
	      				}
	      				else{
	      					System.out.println("song id is"+songID);
	      					System.out.println("error");	
	      					
	      				}
	      			}
	      			count++;
	      		}		
	      	}     
	   }

