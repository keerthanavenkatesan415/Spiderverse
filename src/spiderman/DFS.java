package spiderman;

import java.util.ArrayList;
import java.util.HashMap;

public class DFS {
    private HashMap<Integer, Boolean> marked = new HashMap<Integer, Boolean>();
    private ArrayList<Integer> Path = new ArrayList<Integer>();
    private int destination;
    private int initial;

    public DFS(HashMap<Integer, Boolean> mark, ArrayList<Integer> path, int dest, int first){
        this.marked = mark;
        this.Path = path;
        this.destination = dest;
        this.initial = first;
    }
    public ArrayList<Integer> trackSpotDFS(HashMap<Integer, ArrayList<Dimensions>> AdjacencyList, int vertex){
       this.marked.put(vertex, true);
       
       if(vertex != destination){
            boolean added = false;
            boolean endAdded = false;
           
            for(int j = 0; j < Path.size(); j++){
                if(Path.get(j) == vertex){
                    added = true;
                }
                if(Path.get(j) == destination){
                    endAdded  = true;
                }
            }
            if(!endAdded){
                if(!added){
                    Path.add(vertex);
                }
            }
            else{
                return Path;
            }
        }

       ArrayList<Dimensions> AdjDim  = AdjacencyList.get(vertex);

       for(int i = 0; i < AdjDim.size(); i++){
        if(!this.marked.get(AdjDim.get(i).getDimensionNumber())){
            if(AdjDim.get(i).getDimensionNumber() == destination){
                boolean end = false;
                for(int j = 0; j < Path.size(); j++){
                    
                    if(Path.get(j) == destination){
                        end  = true;
                    }
                }
                if(end){
                    break;
                }
                else{
                    Path.add(AdjDim.get(i).getDimensionNumber());
                    break;
                }
                
            }
            else{
                trackSpotDFS(AdjacencyList, AdjDim.get(i).getDimensionNumber());
            }
        }
       }
       return Path;
    }
    public int getDestination() {
        return destination;
    }
    public HashMap<Integer, Boolean> getMarked() {
        return marked;
    }
    public ArrayList<Integer> getPath() {
        return Path;
    }
    public void setDestination(int destination) {
        this.destination = destination;
    }
    public void setMarked(HashMap<Integer, Boolean> marked) {
        this.marked = marked;
    }public void setPath(ArrayList<Integer> path) {
        Path = path;
    }
}
