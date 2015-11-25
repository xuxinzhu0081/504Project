package iter2_new;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

import iter2_new.ParseAgain.TrieNode;


public class load extends maintain1024{
	public static HashMap<Integer,Vector<Integer>> playListMap_day=new HashMap<>();
	
	public static void updateSource(String fileName) throws IOException{
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
	
	public static void updateSongHeat(){
		System.out.println("in updating song heat");		
		Iterator<Entry<Integer,Vector<Integer>>> MapItr=playListMap_day.entrySet().iterator();
		int count=0;
	      while(MapItr.hasNext()){
	          Map.Entry<Integer,Vector<Integer>> entry =(Map.Entry<Integer,Vector<Integer>>)MapItr.next();
	          int heat=entry.getValue().elementAt(0);
	          Vector<Integer> songList=entry.getValue();

	      		//iterate each entry in playlistMap and add value 
	      		
	      			for(int j=1;j<songList.size();j++){
	      				int songID=songList.elementAt(j);
      					//System.out.println("-----------");
	      				System.out.println("in updating song id "+ songID+" goig to increase heat "+ heat);
	      				
	      				if(songHeatMap.containsKey(songID)){
	      					//update the most pop list
	      					SongCell song=songHeatMap.get(songID);
	      					int prev_pid=song.popListID;
							System.out.println("pop list id is "+prev_pid);
	      					
	      				
							if(heat>playListMap.get(prev_pid).elementAt(0)){
								System.out.println("now pop list id changed , adding this one into playListMap with id "+ playListMap.size());
	      						playListMap.put(playListMap.size(), songList);
	      						int current_pop_pid=playListMap.size()-1;
	      						song.popListID=current_pop_pid;
								System.out.println("after change pop list id is "+ current_pop_pid);

	      					}
	      					
	      					
									
	      					//update heat
							song.setHeat(heat);
							String songName=songMap.get(song.songID);

								
							System.out.println("after update heat is "+song.heat);	      				
						}
	      				
	      					
	      				
	      				else{
	      					System.out.println("song id is"+songID);
	      					System.out.println("error");	
	      				}
	      			count++;
	      		}
	      }
	    }	
	      	
	
		public static void updateMinMaxHeap(){
			
			 
				Iterator<Entry<Integer,Vector<Integer>>> playListMapItr=playListMap_day.entrySet().iterator();
		          int count1=0;
		          int count2=0;
				  while(playListMapItr.hasNext()){
					  
				      Map.Entry<Integer,Vector<Integer>> entry =(Map.Entry<Integer,Vector<Integer>>)playListMapItr.next();
					  if(minheap.size<minheap.maxsize&&maxheap.size<maxheap.maxsize){
						  int index=entry.getKey();
						  maxheap.insert(index);
						  minheap.insert(index);
					  }
					  
					  else{
						  int pid=entry.getKey();
						  //System.out.println("pid is "+ pid);
						  //add it to main hashmap , generate new pid
						  
				    	  int heat=entry.getValue().elementAt(0);
				    	  //System.out.println("current playlist heat is "+heat);
				    	  //System.out.println("min heat is "+minheap.getMinHeat());

				    	  if(heat>minheap.getMinHeat()){
							  count1++;
							  int index=playListMap.size();
					    	  //System.out.println("current playlist index is "+index);

				    		  playListMap.put(index, entry.getValue());
				    		  
				    		  int poped=minheap.pop();
				    		  minheap.insert(index);
				    		  maxheap.delete(poped);
				    		  maxheap.insert(index);
				    	  }
				    	  

			      	}
				      count2++;
				  }
				  System.out.println("you have playlistmap_day size"+count2);
				  System.out.println("you inserted new playlist into playListMap "+count1);
			}

	
	
		//wrapper function for update source
		public static void updateTrieNode(){
			//iterate each songheatMap and update
			int count=0;
		    Iterator<Entry<Integer,SongCell>> songHeatMapItr=songHeatMap.entrySet().iterator();
		    while(songHeatMapItr.hasNext()){
		          Map.Entry<Integer,SongCell> entry =(Map.Entry<Integer,SongCell>)songHeatMapItr.next();
		          int SID=entry.getKey();
		          SongCell song=entry.getValue();
		          int heat=song.heat;
		          System.out.println("current song heat is "+ heat);
		          String name=GetSongHeat.getSongName(SID).toLowerCase();
		          TrieNode runner=root;
		          //now update each trieNode
		          ArrayList<Integer> insertPos=CachedTrie.findInsertPosition(name);	
		          for(int i=0;i<insertPos.size();i++){
		        	  int cur_index=insertPos.get(i);
		        	  runner=runner.children[cur_index];
		        	  System.out.println("im runner "+ runner.word);
		        	  //System.out.println("top 4 heat song of runner");
		        	  //find min of top4
		        	  //[-1,-1,-1,4]
		        	  runner.topId=updateTop4(runner.topId,SID);       	  
		     }
		          count++;
		}
		    System.out.println("you iterate "+count+"songs");
		}
		
		//no need to update if it's not full
		public static int[] updateTop4(int[] IdArr, int curId){
			for(int i=0;i<IdArr.length;i++){
				if(IdArr[i]==-1) return IdArr;
			}
			
			
			//System.out.println("current heat is "+songHeatMap.get(curId).heat);
			int[] heatArr=new int[5];
			HashMap<Integer,Integer> heatToId=new HashMap<>();
			
			for(int i=0;i<4;i++){
				//if current id already exists, no need to update
				if(curId==IdArr[i]) return IdArr;
				heatToId.put(songHeatMap.get(IdArr[i]).heat, IdArr[i]);	
				heatArr[i]=songHeatMap.get(IdArr[i]).heat;
			}
			
			heatToId.put(songHeatMap.get(curId).heat,curId);
			heatArr[4]=songHeatMap.get(curId).heat;
			
			Arrays.sort(heatArr);
			
			System.out.println("after sort current array is ");
			for(int i=0;i<5;i++){
				System.out.print(heatArr[i]+" ");
			}
			System.out.println();

			
			//if heat is larger than minimum, replace it with min_sid
			if(songHeatMap.get(curId).heat>heatArr[0]){
				int min_sid=heatToId.get(heatArr[0]);
				for(int i=0;i<IdArr.length;i++){
					if(IdArr[i]==min_sid)
						IdArr[i]=curId;
				}
				
			}
			
			return IdArr;
		}
		
   
}