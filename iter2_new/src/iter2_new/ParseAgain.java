package iter2_new;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.*;



public class ParseAgain{
	public static HashMap<Integer,Vector<Integer>> playListMap= new HashMap<Integer,Vector<Integer>>();
	public static HashMap<Integer,String> songMap=new HashMap<Integer,String>();
	public static HashMap<Integer,SongCell> songHeatMap=new HashMap<>();


	//public static int[] minheap=new int[1025];
	//public static int[] maxheap=new int[1025];
	
	public static int ROOT=1;
	
	
	public static TrieNode root=new TrieNode('#');
	public static HashMap<Character,Integer> lookupTable=new HashMap<>();
	public static class TrieNode{
		public char word;
		public boolean isEnd;
		//save id of top4 songlist
		public int [] topId;
		
		//char from A to Z 
		public TrieNode[] children;//look up by index is fast
		
		public TrieNode(char word){
			this.word=word;
			this.isEnd=false;
			this.children=new TrieNode[26];
			this.topId=new int[4];
			Arrays.fill(topId, -1);
		}
		
	}
	
	public static class SongCell{
		public int songID;
		public int popListID;
		public int heat;
		
		public SongCell(int songID, int popListID){
			this.songID=songID;
			this.popListID=popListID;
			this.heat=0;
		}
		//for debug only
		public void setHeat(int heat){
			this.heat=this.heat+heat;
		}
		public int getID(int heat){
			if(heat==this.heat)
				return songID;
			else return -1;
		}
		public void ListSongInfo(){
			System.out.println("<songID> is"+ songID);
			System.out.println(" Im most popular in songList "+ popListID);
			System.out.println("<heat> is"+ heat);
			System.out.println();
		}
	}
	
	
	public static void GeneratePlayListMap(){
		String fileName="all_playlists.txt";
    //structure for playlist ||PID:0__heat:23__song1,song2,song3,song4,.....,||
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
           // System.out.println("heat is "+Integer.parseInt(token[length-1]));
            for(int i=0;i<length-1;i++){  
              v.add(Integer.parseInt(token[i]));
            }
            playListMap.put(PID,v);
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
  

  public static void GenerateSongMap() throws IOException{
  String fileName="song_list.txt";
  String line=null;
  try {
        FileReader fileReader=new FileReader(fileName);
        BufferedReader bufferedReader=new BufferedReader(fileReader);
          while ((line=bufferedReader.readLine())!=null){
            String [] token=line.split("\\t");  
            int length=token.length;
            //System.out.println("token length is"+length);          
            int SID = Integer.parseInt(token[0]);
            String song=token[1];
            song=song.replaceAll("\\([^)]+\\)","");
            //System.out.println("sid is"+SID+"song is"+song);
            songMap.put(SID,song);
           
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

  
  public static void runParsing() throws IOException{
	  GeneratePlayListMap();
	  GenerateSongMap();
  }

  public static void printPlayListMap(HashMap<Integer,Vector<Integer>> map){
	  Iterator<Entry<Integer,Vector<Integer>>> playListMapItr=map.entrySet().iterator();
      while(playListMapItr.hasNext()){
          Map.Entry<Integer,Vector<Integer>> entry =(Map.Entry<Integer,Vector<Integer>>)playListMapItr.next();
          int PID=entry.getKey();
          int heat=entry.getValue().elementAt(0);
          System.out.println("PID "+PID+"heat "+heat);
      }
	  
  }
  public static void printSongHashMap(){
      Iterator<Entry<Integer,String>> songMapItr=songMap.entrySet().iterator();
      while(songMapItr.hasNext()){
          Map.Entry<Integer,String> entry =(Map.Entry<Integer,String>)songMapItr.next();
          int SID=entry.getKey();
          String song=entry.getValue();
          System.out.println("SID "+SID+"song "+song);
      }
  }
  //testonly
  }

