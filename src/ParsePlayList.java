import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

public class ParsePlayList(){
	//hard code for file location
	public HashMap<Integer,Vector<Integer>> GeneratePlayListMap() throws IOException {
		String fileName="all_playlists.txt";
		//structure for playlist ||PID:0__heat:23__song1,song2,song3,song4,.....,||
		HashMap<Integer,Vector<Integer>> playListMap= new HashMap<Integer,Vector<Integer>>();
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
        		for(int i=0;i<length-1;i++)
        			v.add(Integer.parseInt(token[i]));
      			playListMap.put(PID,v);
      			PID++;
      		}
      		bufferedReader.close();
      		//return playListMap;      
    	}
    	catch(FileNotFoundException ex){
    		System.out.println("unable to open file"+fileName);
    	}
    	catch(IOException ex){
    		ex.printStackTrace();
    	}
  }

  	public void WriteToFile(HashMap<Integer,Vector<Integer>> playListMap) throws IOException {
  		FileWriter fstream;
  		BufferedWriter out;
  		//records you want to read to file
  		int recordsToWrite=128;
  		
  		fstream=new FileWriter("all_output.txt");
  		out=new BufferedWriter(fstream);

  		int count=0;
  		//Iterator<Integer> playListItr=playListMap.keySet().iterator();
  		Iterator<Entry<Integer,Vector<Integer>>> playListItr=playListMap.entrySet().iterator();
  		//write format ||PID:12__Heat:23__Song1,Song2,Song3,Song4,......,||
  		while(playListItr.hasNext()&&count<recordsToWrite){
  			HashMap.Entry<Integer,Vector<Integer>> entry =(HashMap.Entry<Integer,Vector<Integer>>)playListItr.next();
  			
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
  		}
  	}
  	/*
  	public void Print() {  
        Enumeration RLKey = playListMap.keys();  
        while (RLKey.hasMoreElements()) {  
            String accRole = RLKey.nextElement().toString();  
            print(accRole + "=" + this.getRight(accRole));  
        }  
    } 
    */

    //testonly
    public void main(String[] args){
    	HashMap<Integer,Vector<Integer>> testMap=GeneratePlayListMap();
    	//Print();
    	WriteToFile(testMap);
    } 

}