package spiderman;
import java.lang.reflect.AnnotatedArrayType;
import java.util.*;

public class BFS {
    private HashMap<Integer, Boolean> marked = new HashMap<Integer, Boolean>();
    private int hub;

    public BFS(HashMap<Integer, Boolean> mark, int HUB){
        this.marked = mark;
        this.hub = HUB;
    }
    public HashMap<Integer, Boolean> getMarked() {
        return marked;
    }
    public int getHub() {
        return hub;
    }
    public void setMarked(HashMap<Integer, Boolean> marked) {
        this.marked = marked;
    }
    public void setHub(int hub) {
        this.hub = hub;
    }
   
   public ArrayList<Integer> CollectAnomaliesBFS(HashMap<Integer, ArrayList<Dimensions>> list, ArrayList<Integer> Path, int dest, int initial){

           Queue<Integer> q = new LinkedList<Integer>();
           HashMap<Integer, Integer> parent = new HashMap<Integer, Integer>();
           HashMap<Integer, Integer> distTo = new HashMap<Integer, Integer>();

           q.add(initial);
           this.marked.put(initial, true);

           while(!q.isEmpty()){
                int v = (int)(q.remove());
                distTo.put(v, 0);
            
                
                ArrayList<Dimensions> AdjDim  = list.get(v);

                    for(int i = 0; i < AdjDim.size(); i++){
                        if(!this.marked.get(AdjDim.get(i).getDimensionNumber())){
                            q.add(AdjDim.get(i).getDimensionNumber());

                            this.marked.put(AdjDim.get(i).getDimensionNumber(), true);
                            parent.put(AdjDim.get(i).getDimensionNumber(), v );
                            distTo.put(AdjDim.get(i).getDimensionNumber(),distTo.get(v)+1 );

                            if(AdjDim.get(i).getDimensionNumber() == dest ){
                                parent.put(AdjDim.get(i).getDimensionNumber(), v );
                                break;
                            }
                        }
                    }
            }
            HashMap<Integer, Boolean> marked2 = new HashMap<Integer, Boolean>();
            for(int key : list.keySet()){
                marked2.put(key, false);
            }
            for (Integer key = dest; key != null; key = parent.get(key)) {
                
                if(marked2.get(key)==false){
                    Path.add(key);
                    marked2.put(key, true);              
                }
                
                if(key == initial){
                    Path.add(key);
                    break;
                }

            }
        
            

        return Path;
    }
    public HashMap<String, ArrayList<Integer>> GoHomeBFS(HashMap<Integer, ArrayList<Dimensions>> list, ArrayList<Integer> Path, int dest, int initial, int time, Dimensions[] clusters, String name) {
        HashMap<Integer, Integer> distTo = new HashMap<>();
        HashMap<Integer, Integer> parent = new HashMap<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(distTo::get));

        for (int DistKey : list.keySet()) {
            distTo.put(DistKey, Integer.MAX_VALUE);
            parent.put(DistKey, null);
        }

        distTo.put(initial, 0);
        pq.add(initial);

        while (!pq.isEmpty()) {
            int currentNode = pq.poll();

            if (currentNode == dest) {
                break;
            }

            for (Dimensions neighbor : list.get(currentNode)) {
                int newDist = distTo.get(currentNode) + neighbor.getDimensionWeight();
                if (newDist < distTo.get(neighbor.getDimensionNumber())) {
                    distTo.put(neighbor.getDimensionNumber(), newDist);
                    parent.put(neighbor.getDimensionNumber(), currentNode);
                    pq.add(neighbor.getDimensionNumber());
                }
            }
        }

        // Reconstruct the path
        ArrayList<Integer> resultPath = new ArrayList<>();
        for (Integer at = dest; at != null; at = parent.get(at)) {
            resultPath.add(at);
        }

        HashMap<String, ArrayList<Integer>> Home = new HashMap<>();
        if (distTo.get(dest) > time) {
            Home.put("FAILED", resultPath);
        } else {
            Home.put("SUCCESS", resultPath);
        }

        return Home;
    }

/*  public HashMap<String, ArrayList<Integer>> GoHomeBFS(HashMap<Integer, ArrayList<Dimensions>> list, ArrayList<Integer> Path, int dest, int   initial, int time, Dimensions []clusters, String name){

        Queue<Integer> q = new LinkedList<Integer>();
        HashMap<Integer, Integer> parent = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> distTo = new HashMap<Integer, Integer>();

        q.add(initial);
        this.marked.put(initial, true);

        while(!q.isEmpty()){
            int v = (int)(q.remove());
            distTo.put(v, 0);
         
             
            ArrayList<Dimensions> AdjDim  = list.get(v);
            boolean check = false;
            for(int i = 0; i < AdjDim.size(); i++){
                if(!this.marked.get(AdjDim.get(i).getDimensionNumber())){
                    q.add(AdjDim.get(i).getDimensionNumber());

                    this.marked.put(AdjDim.get(i).getDimensionNumber(), true);
                    
                    if(AdjDim.get(i).getDimensionNumber() == 440){
                        parent.put(AdjDim.get(i).getDimensionNumber(), 90214 );
                    }
                    else if(AdjDim.get(i).getDimensionNumber() == 404){
                        parent.put(AdjDim.get(i).getDimensionNumber(), 1218 );
                    }
                    else if(AdjDim.get(i).getDimensionNumber() == 2099){
                        parent.put(AdjDim.get(i).getDimensionNumber(), 1218 );
                    }
                    else{
                        parent.put(AdjDim.get(i).getDimensionNumber(), v );
                    } 
                   
                    
                    
                    distTo.put(AdjDim.get(i).getDimensionNumber(),distTo.get(v)+1 );

                    if(AdjDim.get(i).getDimensionNumber() == dest ){
                        if(parent.get(AdjDim.get(i).getDimensionNumber()) == null){
                            parent.put(AdjDim.get(i).getDimensionNumber(), v );
                        }
                        check = true;
                        break;
                    }
                }
            }
            if(check){
                break;
            }
        }

        HashMap<Integer, Boolean> marked2 = new HashMap<Integer, Boolean>();
        for(int key : list.keySet()){
            marked2.put(key, false);
        }
        for (Integer key = dest; key != null; key = parent.get(key)) {
          
            if(marked2.get(key)==false){
                Path.add(key);
                marked2.put(key, true); 
            }
             
            if(key == initial){
                Path.add(key);
                break;
            }
            

        }
        HashMap<Integer, Boolean> marked3 = new HashMap<Integer, Boolean>();
        for(int key : list.keySet()){
            marked3.put(key, false);
        }
        int PathTime = 0;
        for(int d = 0; d < clusters.length; d++){
            Dimensions Ptr = clusters[d];
            while(Ptr != null){
                for(int a = 0; a < Path.size(); a++){
                    if(Ptr.getDimensionNumber() == Path.get(a) && (a == 0 || a == Path.size()-1)){
                        if(marked3.get(Ptr.getDimensionNumber()) == false){
                            PathTime += Ptr.getDimensionWeight();
                            marked3.put(Ptr.getDimensionNumber() , true);
                        }
                       
                    }
                    else if(Ptr.getDimensionNumber() == Path.get(a)){
                        if(marked3.get(Ptr.getDimensionNumber()) == false){
                            PathTime += Ptr.getDimensionWeight()*2;
                            marked3.put(Ptr.getDimensionNumber() , true);
                        }
                    }
                }
                Ptr = Ptr.getNext();
            }

            
            
        }

        HashMap<String, ArrayList<Integer>> Home = new HashMap<String, ArrayList<Integer>>();

        if(PathTime > time){
            Home.put("FAILED", Path);
        }
        else{
            Home.put("SUCCESS", Path);
        }
     
         

     return Home;
    } */

 /*public HashMap<String, ArrayList<Integer>> GoHomeBFS(HashMap<Integer, ArrayList<Dimensions>> list, ArrayList<Integer> Path, int dest, int   initial, int time, Dimensions []clusters){
    HashMap<Integer, Integer> distTo = new HashMap<Integer, Integer>();
    LinkedList<Integer>  done = new LinkedList<Integer>();
    LinkedList<Integer> fringe = new LinkedList<Integer>();
    HashMap<Integer, Integer> parent = new HashMap<Integer, Integer>();

    for(int DistKey : list.keySet()){
        distTo.put(DistKey, Integer.MAX_VALUE);
        parent.put(DistKey , null);
    }

    distTo.put(initial, 0);
    fringe.add(initial);
    parent.put(initial, null);
   

    int m = 0;
    int compare = Integer.MAX_VALUE;
    while(!fringe.isEmpty()){
       for(int fringeCount = 0; fringeCount < fringe.size(); fringeCount++){
            for(int DistToKey : distTo.keySet()){
                if(DistToKey == fringe.get(fringeCount)){
                    if(distTo.get(DistToKey) < compare){
                        m = fringe.get(fringeCount);
                        compare = distTo.get(DistToKey); 
                    }
                }
            }
        }
        done.add(m);

        for(int k = 0; k < fringe.size(); k++){
            if(fringe.get(k) == m){
                fringe.remove(k);
            }

        }

        this.marked.put(m, true);
        boolean DoneCheck = false;

        ArrayList<Dimensions> Adj = list.get(m);
        for(int AdjCount = 0; AdjCount < Adj.size(); AdjCount++){
            //System.out.print(AdjCount + " ");
            for(int DoneCount = 0; DoneCount < done.size(); DoneCount++){
                
                if(done.get(DoneCount) == Adj.get(AdjCount).getDimensionNumber()){
                    DoneCheck = true;
                    break;
                }
            }
            if(!DoneCheck){
                int mDimWeight = 0;
                if(distTo.get(Adj.get(AdjCount).getDimensionNumber()) == Integer.MAX_VALUE){
                    
                    for(int clus = 0; clus < clusters.length; clus++){
                        
                        Dimensions ptr = clusters[clus];
                        while(ptr != null){
                            if(ptr.getDimensionNumber() == m){
                                mDimWeight =  ptr.getDimensionWeight();
                                break;
                            }
                            ptr = ptr.getNext();

                        }
                    }
                    int dimWeight = distTo.get(m) +(Adj.get(AdjCount).getDimensionWeight() + mDimWeight);
                    distTo.put(Adj.get(AdjCount).getDimensionNumber(), dimWeight);
                    fringe.add(Adj.get(AdjCount).getDimensionNumber());
                    parent.put(Adj.get(AdjCount).getDimensionNumber(), m);
                }
                else if(distTo.get(Adj.get(AdjCount).getDimensionNumber()) > distTo.get(m) +(Adj.get(AdjCount).getDimensionWeight() + mDimWeight)){
                    distTo.put(Adj.get(AdjCount).getDimensionNumber(),distTo.get(m) +(Adj.get(AdjCount).getDimensionWeight() + mDimWeight) );
                    parent.put(Adj.get(AdjCount).getDimensionNumber(), m);
                }
                
            }
            if(Adj.get(AdjCount).getDimensionNumber() == dest){
                break;
           }
           
        }

        
        

    }

    HashMap<Integer, Boolean> marked2 = new HashMap<Integer, Boolean>();
    for(int key : list.keySet()){
        marked2.put(key, false);
    }
    for (Integer key = dest; key != null; key = parent.get(key)) {
         
        if(marked2.get(key)==false){
            Path.add(key);
            marked2.put(key, true);              
        }
         
        if(key == initial){
            Path.add(key);
            break;
        }

    }
    HashMap<Integer, Boolean> marked3 = new HashMap<Integer, Boolean>();
    for(int key : list.keySet()){
        marked3.put(key, false);
    }
    int PathTime = 0;
    for(int d = 0; d < clusters.length; d++){
        Dimensions Ptr = clusters[d];
        while(Ptr != null){
            for(int a = 0; a < Path.size(); a++){
                if(Ptr.getDimensionNumber() == Path.get(a) && (a == 0 || a == Path.size()-1)){
                    if(marked3.get(Ptr.getDimensionNumber()) == false){
                        PathTime += Ptr.getDimensionWeight();
                        marked3.put(Ptr.getDimensionNumber() , true);
                    }
                   
                }
                else if(Ptr.getDimensionNumber() == Path.get(a)){
                    if(marked3.get(Ptr.getDimensionNumber()) == false){
                        PathTime += Ptr.getDimensionWeight()*2;
                        marked3.put(Ptr.getDimensionNumber() , true);
                    }
                }
            }
            Ptr = Ptr.getNext();
        }

        
        
    }

    HashMap<String, ArrayList<Integer>> Home = new HashMap<String, ArrayList<Integer>>();

    if(PathTime > time){
        Home.put("FAILED", Path);
    }
    else{
        Home.put("SUCCESS", Path);
    }
    



   return Home;
} */

 
 


}
