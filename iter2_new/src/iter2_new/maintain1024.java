package iter2_new;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

public class maintain1024 extends ParseAgain {

	 
		//first generate 1024 size maxheap, and build maxheap based on it
		public static MinHeap minheap=new MinHeap(1024);
		public static MaxHeap maxheap=new MaxHeap(1024);
		public static final int FRONT=1;

		//build size 1024 max_heap and min_heap
		
		public static class MaxHeap{
			
			public int size;
			public int[] maxheap;
			public int maxsize;
			public HashMap<Integer,Vector<Integer>> map;
			public static final int FRONT=1;
			public MaxHeap(int maxsize){
				this.map=playListMap;
				this.size=0;
				this.maxsize=maxsize;
				maxheap=new int[this.maxsize+1];
				maxheap[0]=Integer.MAX_VALUE;
			}
			
			public void setMap(HashMap<Integer,Vector<Integer>> map){
				this.map=map;
			}
			
			public int getMaxHeat(){
				return heat(maxheap[FRONT]);
			}
			public int parent(int i){
				return i/2;
			}
			public int leftChild(int i){
				return i*2;
			}
			public int rightChild(int i){
				return i*2+1;
			}
			public boolean isLeaf(int i){
				if(i>size/2&&i<=size)
					return true;
				else return false;
			}
			
			
			
			
			public void swap(int i, int j){
				int temp=maxheap[i];
				maxheap[i]=maxheap[j];
				maxheap[j]=temp;
			}
			
			public int heat(int index){
				if(index==Integer.MAX_VALUE) return Integer.MAX_VALUE;
				return map.get(index).elementAt(0);
			}
			
			
			
			public void maxHeapify(int root){
				if(!isLeaf(root)){
					if(heat(maxheap[root])<heat(maxheap[leftChild(root)])||heat(maxheap[root])<heat(maxheap[rightChild(root)])){
						if(heat(leftChild(root))>heat(rightChild(root))){
							swap(root,leftChild(root));
							maxHeapify(leftChild(root));
						}
						else{
							swap(root,rightChild(root));
							maxHeapify(rightChild(root));
						}
					}
				}
			}
			
			public void insert(int index){
				maxheap[++size]=index;
				int current=size;

				while(heat(maxheap[current])>heat(maxheap[parent(current)])){
					swap(current,parent(current));
					current=parent(current);
				}
			}
			
			public void print(){
				for(int i=1;i<size/2;i++){
					System.out.print("PARENT "+maxheap[i]+"Left child: "+maxheap[2*i]+"right child "+maxheap[2*i+1]);
					System.out.println();
				}
			}
			
			public void print2(){
				for(int i=1;i<=size;i++)
					System.out.print(maxheap[i]+" ");
				System.out.println();
			}
			
			
			
			public int pop(){
				int poped=maxheap[FRONT];
				maxheap[FRONT]=maxheap[size--];
				maxHeapify(FRONT);
				return poped;
			}
			
			public void delete(int index){
				for(int i=1;i<=maxsize;i++){
					if(index==maxheap[i]){
						maxheap[i]=maxheap[size--];
					}		
				}
			}
		}
		
		

		public static class MinHeap{
			public int size;
			public int[] minheap;
			public int maxsize;
			public static final int FRONT=1;
			public HashMap<Integer,Vector<Integer>> map;
			public MinHeap(int maxsize){
				this.size=0;
				this.maxsize=maxsize;
				this.map=playListMap;
				minheap=new int[this.maxsize+1];
				minheap[0]=Integer.MIN_VALUE;
			}
			public int getMinHeat(){
				return heat(minheap[FRONT]);
			}
			
			
			public void setMap(HashMap<Integer,Vector<Integer>> map){
				this.map=map;
			}
			
			public int parent(int i){
				return i/2;
			}
			public int leftChild(int i){
				return i*2;
			}
			public int rightChild(int i){
				return i*2+1;
			}
			public boolean isLeaf(int i){
				if(i>size/2&&i<=size)
					return true;
				else return false;
			}
			public void swap(int i, int j){
				int temp=minheap[i];
				minheap[i]=minheap[j];
				minheap[j]=temp;
			}
			
			public int heat(int index){
				if(index==Integer.MIN_VALUE) return Integer.MIN_VALUE;
				return map.get(index).elementAt(0);
			}
			
			public void minHeapify(int root){
				if(!isLeaf(root)){
					if(heat(minheap[root])>heat(minheap[leftChild(root)])||heat(minheap[root])>heat(minheap[leftChild(root)])){
						if(heat(minheap[leftChild(root)])<heat(minheap[rightChild(root)])){
							swap(root,leftChild(root));
							minHeapify(leftChild(root));
						}
						else{
							swap(root,rightChild(root));
							minHeapify(rightChild(root));
						}
						
					}
				}
			}
			public void insert(int index){
				
				minheap[++size]=index;
				int current=size;

				while(heat(minheap[current])<heat(minheap[parent(current)])){
					swap(current,parent(current));
					current=parent(current);
				}
				//not include 0 index, start from 1
				
			}
			
			public void print(){
				for(int i=1;i<size/2;i++){
					System.out.print("PARENT "+minheap[i]+"Left child: "+minheap[2*i]+"right child "+minheap[2*i+1]);
					System.out.println();
				}
			}
			public void print2(){
				for(int i=1;i<=size;i++)
					System.out.print(minheap[i]+" ");
				
					System.out.println();
			}
			
			
			public int pop(){
				
				int poped=minheap[FRONT];
				minheap[FRONT]=minheap[size--];
				minHeapify(FRONT);

				return poped;
			}
		}
		
		
		
		public static void InitMaxMinHeap(){
			Iterator<Entry<Integer,Vector<Integer>>> playListMapItr=playListMap.entrySet().iterator();
	          int count=0;
			  while(playListMapItr.hasNext()){
			      Map.Entry<Integer,Vector<Integer>> entry =(Map.Entry<Integer,Vector<Integer>>)playListMapItr.next();
				  if(minheap.size<minheap.maxsize&&maxheap.size<maxheap.maxsize){
					  int index=entry.getKey();
					  maxheap.insert(index);
					  minheap.insert(index);
				  }
				  
				  else{
					  
					  int sid=entry.getKey();
			    	  int heat=entry.getValue().elementAt(0);
			    	  if(heat>minheap.getMinHeat()){
			    		  int poped=minheap.pop();
			    		  minheap.insert(sid);
			    		  maxheap.delete(poped);
			    		  maxheap.insert(sid);
			    		 
			    	  }
			    	  

		      	}
			      count++;
			  }
			  System.out.println("you have map size"+count);
		}
		
	}



