import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;


import java.util.*;



public class ParsePlayList{
	public static HashMap<Integer,Vector<Integer>> playListMap= new HashMap<Integer,Vector<Integer>>();
	public static HashMap<Integer,String> songMap=new HashMap<Integer,String>();
	public static HashMap<Integer,SongCell> songHeatMap=new HashMap<>();


	public static ArrayList<Integer> minheap=new ArrayList<Integer>(Arrays.asList(0));
	public static ArrayList<Integer> maxheap=new ArrayList<Integer>(Arrays.asList(0));
	
	public static int root=1;
	
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
			System.out.println("songID is"+ songID);
			System.out.println(" Im most popular in songList "+ popListID);
			System.out.println("heat is"+ heat);
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
  public static void WriteTop8() throws IOException{
      try{
      FileWriter fstream;
      BufferedWriter out;
      //records you want to read to file
      //int recordsToWrite=128;
      
      fstream=new FileWriter("top8.xml");
      out=new BufferedWriter(fstream);
     

      int count=1;
      Iterator<Entry<Integer,Vector<Integer>>> playListItr=playListMap.entrySet().iterator();
      //write format ||PID:12__Heat:23__Song1,Song2,Song3,Song4,......,||
      while(count<=8){           
        int PID=maxheap.get(count);
        //write playListId to current line
        out.write("<note>");
        out.write("\n");

        //out.write("<PID>");
        //out.write(Integer.toString(PID));
        //out.write("</PID>");
        //out.write("\n");


        Vector<Integer> temp=playListMap.get(PID);
        int heat=temp.firstElement();
        List<Integer> songList=temp.subList(1,temp.size());
        //out.write("Heat:"+heat+"  ");
        //begin to write song
        out.write("<body>");
        for(int i=0;i<songList.size();i++)
          out.write(songMap.get(songList.get(i))+",");
        //starts from second element in vector   
        out.write("</body>");
        out.write("\n");
        count++;
        out.write("</note>");
        out.write("\n");

      }
      out.close();
      }
      catch(IOException ex){
        ex.printStackTrace();
      }
  }
  public static void WriteTop128() throws IOException{
      try{
      FileWriter fstream;
      BufferedWriter out;
      //records you want to read to file
      //int recordsToWrite=128;
      
      fstream=new FileWriter("top128.txt");
      out=new BufferedWriter(fstream);
     

      int count=1;
      Iterator<Entry<Integer,Vector<Integer>>> playListItr=playListMap.entrySet().iterator();
      //write format ||PID:12__Heat:23__Song1,Song2,Song3,Song4,......,||
      while(count<=128){           
        int PID=maxheap.get(count);
        //write playListId to current line
        out.write("PID:"+PID+"  ");

        Vector<Integer> temp=playListMap.get(PID);
        int heat=temp.firstElement();
        List<Integer> songList=temp.subList(1,temp.size());
        out.write("Heat:"+heat+"  ");
        //begin to write song
        out.write("Song:");
        for(int i=0;i<songList.size();i++)
          out.write(songMap.get(songList.get(i))+",");
        //starts from second element in vector   
        out.write("\n");
        count++;
      }
      out.close();
      }
      catch(IOException ex){
        ex.printStackTrace();
      }
  }

    public static void WriteToFile() throws IOException{
      try{
      FileWriter fstream;
      BufferedWriter out;
      //records you want to read to file
      //int recordsToWrite=128;
      
      fstream=new FileWriter("all_output.txt");
      out=new BufferedWriter(fstream);

      int count=0;
      //Iterator<Integer> playListItr=playListMap.keySet().iterator();
      Iterator<Entry<Integer,Vector<Integer>>> playListItr=playListMap.entrySet().iterator();
      //write format ||PID:12__Heat:23__Song1,Song2,Song3,Song4,......,||
      while(playListItr.hasNext()/*&&count<recordsToWrite*/){
        Map.Entry<Integer,Vector<Integer>> entry =(Map.Entry<Integer,Vector<Integer>>)playListItr.next();
        
        int PID=entry.getKey();
        //write playListId to current line
        out.write("PID:"+PID+"  ");
        Vector<Integer> vectorSongList=entry.getValue();
        Iterator<Integer> valueItr=vectorSongList.iterator();

        int heat=valueItr.next();
        out.write("Heat:"+heat+"  ");

        out.write("Song:");
      //starts from second element in vector
      while(valueItr.hasNext()){
        //write each one to output file in one line
        int temp=valueItr.next();
        out.write(temp+",");
      }
      out.write("\n");
      }
      out.close();
      }
      catch(IOException ex){
        ex.printStackTrace();
      }
    }
    
//find top 8
  public static void SearchTop8() throws IOException{
    try{
      Build_Max_Heap();
      WriteTop8();
    }
    catch(IOException ex){
      ex.printStackTrace();
    }
  }




    
  public static void Build_Max_Heap(){
      int i=0;
      //first initialize with 8 songlists
      for(Map.Entry<Integer,Vector<Integer>> entry:playListMap.entrySet()){
          maxheap.add(entry.getKey());
          BubbleUpMax(maxheap.size()-1);
          i++;
          if(i>9){
            if(getHeatMax(root)<entry.getValue().firstElement()){
                maxheap.add(entry.getKey());
                BubbleUpMax(maxheap.size()-1);
            }
          }     
      }
  }

  public static void Build_Min_Heap(){
    int i=0;
      for(Map.Entry<Integer,Vector<Integer>> entry:playListMap.entrySet()){
          minheap.add(entry.getKey());
          BubbleUpMin(minheap.size()-1);
          i++;
          if(i>9)
            break;
      }

  }
  public static void GenerateSongMap() throws IOException{
  String fileName="song_list.txt";
  String line=null;
  try {
        FileReader fileReader=new FileReader(fileName);
        BufferedReader bufferedReader=new BufferedReader(fileReader);
          while ((line=bufferedReader.readLine())!=null){
            //process each line in some way
        //put last digit as heat in the beginning of vector, and read each number into hashmap
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

  
public static int GetParent(int current){return current/2;}
public static int GetLeftChild(int parent){return 2*parent;}
public static int GetRightChild(int parent){return 2*parent+1;}
public static int getHeatMin(int current){
  int PID=minheap.get(current);
  int heat=playListMap.get(PID).firstElement();
  return heat;
}
public static int getHeatMax(int current){
  int PID=maxheap.get(current);
  int heat=playListMap.get(PID).firstElement();
  return heat;
}

public static void BubbleUpMin(int current){
    int parent=GetParent(current);
    if(getHeatMin(current)>=getHeatMin(parent)) return;
    else{
      Collections.swap(minheap,parent,current);
      BubbleUpMin(parent);
    }
    if(current==root) return;
  }



public static void BubbleUpMax(int current){
    int parent=GetParent(current);
    if(current==root) return;
    if(getHeatMax(current)<=getHeatMax(parent)) return;
    else{
      Collections.swap(maxheap,parent,current);
      BubbleUpMax(parent);
    }   
}


public static int ExtractTopMax(){
    int top=getHeatMax(root);
    maxheap.set(root,maxheap.get(maxheap.size()-1));
    maxheap.remove(maxheap.size()-1);
    HeapifyMax(root);
    return top;
  }

  public static int ExtractTopMin(){
    int top=getHeatMin(root);
    minheap.set(root,minheap.get(minheap.size()-1));
    minheap.remove(minheap.size()-1);
    HeapifyMin(root);

    return top;
  }
  
  public static void HeapifyMin(int current){
    int left=GetLeftChild(current);
    int right=GetRightChild(current);
    int smallest=current;
    if(left<=minheap.size()-1&&getHeatMin(left)<getHeatMin(smallest)){
      smallest=left;
    }
    if(right<=minheap.size()-1&&getHeatMin(right)<getHeatMin(smallest)){
      smallest=right;
    }
    if(smallest!=current){
      Collections.swap(minheap,smallest,current);
      HeapifyMin(smallest);
    }
    return;
  }
  public static void p(int i){
    System.out.println(i);
  }
  public static void mark(String i){
    System.out.println(i);
  }
  public static void HeapifyMax(int current){
    int left=GetLeftChild(current);
    p(left);
    int right=GetRightChild(current);
    p(right);
    int largest=current;
    p(largest);

    if(left<=maxheap.size()-1&&getHeatMax(left)>getHeatMax(largest)){
      largest=left;
    }
    if(right<=maxheap.size()-1&&getHeatMax(right)>getHeatMax(largest)){
      largest=right;
    }
    if(largest!=current){
      Collections.swap(maxheap,largest,current);

      HeapifyMax(largest);
    }
    return;
  }
  public static void printHeap(){

    int i=1;
    while(i<=9){
      i++;
    }
    System.out.println("");
   System.out.println("current maxheap size is(0 index included)"+maxheap.size());

    
    i=1;
      while(i<=minheap.size()-1){
            System.out.println("my index is"+i+"my id is"+minheap.get(i)+"my heat is"+ getHeatMin(i)+" ");

      i++;
    }
    System.out.println("current minheap size is(0 index included)"+minheap.size());
    System.out.println("");
  }

  public static void printHashMap(){
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