package iter2_new;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;




public class CachedTrie extends GetSongHeat{

	
	/* help function to build trie*/
	public static ArrayList<Integer> findInsertPosition(String s){
		initTable();
		s=s.toLowerCase();
		ArrayList<Integer> res=new ArrayList<>();
		char[] temp=s.toCharArray();

		for(int i=0;i<temp.length;i++){
			int index=lookupTable.get(temp[i]);
			res.add(index);
		}
		return res;
	}
	
	public static void initTable(){
		int start=(int) 'a';
		for(int i=0;i<26;i++){
			lookupTable.put((char)start,i);
			start++;
		}
	}
	
	
	
	/* 	songMap saves all song info
	  	heat=getSongHeat(id)
	 	String name = getSongName(id)
	 */
	
	public static void constructTree(){
			int count=0;
	
			System.out.println("size of song heat map is"+songHeatMap.size());
			//itertate in songHeatMap, get the heat from songHeatMap and name
			Iterator it=songMap.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<Integer,String> pair=(Map.Entry)it.next();
				int id=pair.getKey();
				//int heat=getHeat(id);
			
				String name=getSongName(id).toLowerCase();
				//System.out.println("name "+name+" count is"+count++);
				ArrayList<Integer> insertPos=findInsertPosition(name);	
				TrieNode runner=root;
				//iterate arraylist
				for(int i=0;i<insertPos.size();i++){
					int cur_index=insertPos.get(i);
					
					if(runner.children[cur_index]==null)
						runner.children[cur_index]=new TrieNode(name.charAt(i));
	
					//go to next node
					runner=runner.children[cur_index];
					resolveTop4(runner,id);
						
				}			
				
				runner.isEnd=true;
			}
	}
	
	public static void resolveTop4(TrieNode runner, int id){
		//print current runner 
		//System.out.println("im runner " +runner.word);
		int heat=getHeat(id);

		
		//if this song never appears in playlist, bypass it
		if(heat==-1) return;
		int min1=Math.min(getHeat(runner.topId[0]), getHeat(runner.topId[1]));
		int min2=Math.min(getHeat(runner.topId[2]), getHeat(runner.topId[3]));
		int min=Math.min(min1,min2);
		int min_id=-1;
		for(int i=0;i<4;i++){
			if(min==getHeat(runner.topId[i]))
				min_id=i;
		}
		
		if(heat<min)
			return;
		else{
			//remove the least minimum and insert new id into appropriate place
				runner.topId[min_id]=id;
				if(runner.topId.length==4)
					load.updateTop4(runner.topId, id);
		}
		
	}
	
		
	public static int[] orderResult(int[] res){
		Map<Integer,Integer> map=new HashMap<>();
		int [] heatArray=new int[4];
		
		for(int i=0;i<res.length;i++){
			map.put(getHeat(res[i]), res[i]);
			//System.out.println(res[i]+" "+getHeat(res[i]));
			heatArray[i]=getHeat(res[i]);
		}
		Arrays.sort(heatArray);
		//reverse order for descending order
		for(int i=0;i<2;i++){
			int temp=heatArray[i];
			heatArray[i]=heatArray[3-i];
			heatArray[3-i]=temp;
		}
		
		for(int i=0;i<heatArray.length;i++){
			res[i]=map.get(heatArray[i]);
		}
	      
	     /* System.out.println("now debug result array);");
	      for(int i=0;i<4;i++){
	    	  System.out.println(res[i]);
	      }
	      */
	      return res;
	}
	

	public static boolean findWord(String word){
		TrieNode currentNode=root;
		//fix word
		word=word.toLowerCase();
		word=word.replaceAll("[^a-zA-Z]", "");
		char [] characters=word.toCharArray();
		int size=characters.length;
		int index;
		
		for(index=0;index<size;index++){
			if(currentNode==null)
				return false;
			int pos=lookupTable.get(characters[index]);
			currentNode=currentNode.children[pos];
		}
		if(index==size&&currentNode==null) return false;
		if(currentNode!=null&&!currentNode.isEnd) return false;
		return true;
	}
	
	/* @param level			keep count of chars in the word
	 * @param substring 	store the substring "ck" that is going to be searched
	 */
	
	public static int[] findSubstring(String word){
		TrieNode currentNode=root;
		//fix word
		word=word.toLowerCase();
		word=word.replaceAll("[^a-zA-Z]", "");
		char [] characters=word.toCharArray();
		int size=characters.length;
		int index;
		
		for(index=0;index<size;index++){
			if(currentNode==null)
				break;
			int pos=lookupTable.get(characters[index]);

			currentNode=currentNode.children[pos];
			//System.out.println("current node ends at "+currentNode.word);
		}
		if(currentNode==null)
			return null;
		
		//print top 4 string related with current word
		
		int[] relatedSong=currentNode.topId;
		return currentNode.topId;
	}

	
	public static void printTrie(TrieNode root, int level,char[] collectLetters){
		if(root==null) return;
		for(int i=0;i<root.children.length;i++){
			collectLetters[level]=root.word;
			printTrie(root.children[i],level+1,collectLetters);
		}
		if(root.isEnd){
			//print the word
			for(int j=1;j<=level;j++)
				System.out.print(collectLetters[j]);
			System.out.println();
		}
	}
	
	
	
}
	
	
	



