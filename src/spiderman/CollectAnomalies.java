package spiderman;
import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * DimensionInputFile name is passed through the command line as args[0]
 * Read from the DimensionsInputFile with the format:
 * 1. The first line with three numbers:
 *      i.    a (int): number of dimensions in the graph
 *      ii.   b (int): the initial size of the cluster table prior to rehashing
 *      iii.  c (double): the capacity(threshold) used to rehash the cluster table 
 * 2. a lines, each with:
 *      i.    The dimension number (int)
 *      ii.   The number of canon events for the dimension (int)
 *      iii.  The dimension weight (int)
 * 
 * Step 2:
 * SpiderverseInputFile name is passed through the command line as args[1]
 * Read from the SpiderverseInputFile with the format:
 * 1. d (int): number of people in the file
 * 2. d lines, each with:
 *      i.    The dimension they are currently at (int)
 *      ii.   The name of the person (String)
 *      iii.  The dimensional signature of the person (int)
 * 
 * Step 3:
 * HubInputFile name is passed through the command line as args[2]
 * Read from the HubInputFile with the format:
 * One integer
 *      i.    The dimensional number of the starting hub (int)
 * 
 * Step 4:
 * CollectedOutputFile name is passed in through the command line as args[3]
 * Output to CollectedOutputFile with the format:
 * 1. e Lines, listing the Name of the anomaly collected with the Spider who
 *    is at the same Dimension (if one exists, space separated) followed by 
 *    the Dimension number for each Dimension in the route (space separated)
 * 
 * @author Seth Kelley
 */

public class CollectAnomalies {
    
    public static void main(String[] args) {

        if ( args.length < 4 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.CollectAnomalies <dimension INput file> <spiderverse INput file> <hub INput file> <collected OUTput file>");
                return;
        }

        // WRITE YOUR CODE HERE
        String DimensionInputFile = args[0];
        String SpiderverseInputFile = args[1];
        String hubInPutFile = args[2];
        String outPutFile = args[3];

        StdIn.setFile(hubInPutFile);

        int hub = StdIn.readInt();

        Clusters NewClusters = new Clusters();

        Dimensions [] clusters = NewClusters.createCluster(DimensionInputFile);
        Collider collider = new Collider();
        HashMap<Integer, ArrayList<Dimensions>> adjList = collider.colliderList(clusters);
       

        HashMap<Integer, ArrayList<Person>> people = collider.addPerson(SpiderverseInputFile); 

        HashMap<Integer, Person> Spiders = findSpiders(people, hub);

        StdOut.setFile(outPutFile);

        printAnomalies(people, adjList, Spiders, hub);
       

   


        
    }
    
    public static void printAnomalies(HashMap<Integer, ArrayList<Person>> people,HashMap<Integer, ArrayList<Dimensions>> adjList,  HashMap<Integer, Person> Spiders, int hub){
        for(int key : people.keySet()){
            ArrayList<Person> anomaly = people.get(key);

            for(int i = 0; i < anomaly.size(); i++){
                int dimNum = anomaly.get(i).getDim();
                int dimSig = anomaly.get(i).getSignature();
                String Name = anomaly.get(i).getName();

                if(dimNum != hub){
                    if(dimNum != dimSig){
                        HashMap<Integer, Boolean> marked = new HashMap<Integer, Boolean>();
                        ArrayList<Integer> PathTo = new ArrayList<Integer>();
                    
                        for(int Listkey : adjList.keySet()){
                            marked.put(Listkey, false);
                        }
                        BFS collectAnomaly = new BFS(marked, hub);
                        ArrayList<Integer> Path = collectAnomaly.CollectAnomaliesBFS(adjList, PathTo, dimNum, hub);

                        StdOut.print(Name + " ");

                        if(Spiders.get(dimNum) != null){
                            StdOut.print(Spiders.get(dimNum).getName() + " ");
                            for(int j = 0; j < Path.size()-1; j++ ){
                                StdOut.print(Path.get(j) + " ");
                            }
                            StdOut.println();
                        }
                        else{
                            for(int j = Path.size()-2; j > 0; j-- ){
                                StdOut.print(Path.get(j) + " ");
                            }
                            for(int j = 0; j < Path.size()-1; j++ ){
                                StdOut.print(Path.get(j) + " ");
                            }
                            
                            StdOut.println();

                        }

                    }
                    
                }

            }

        }
    }
    public static HashMap<Integer, Person> findSpiders(HashMap<Integer, ArrayList<Person>> people, int hub){
        HashMap<Integer, Person> Spiders = new HashMap<Integer, Person>();

        for(int key : people.keySet()){
            ArrayList<Person> ppl = people.get(key);

            for(int i = 0; i < ppl.size(); i++){
                int dimNum = ppl.get(i).getDim();
                int dimSig = ppl.get(i).getSignature();

                if(dimNum != hub){
                    if((dimNum == dimSig) && (Spiders.get(key) == null)){
                       Spiders.put(key, ppl.get(i));
                    }  
                }

            }
        }
        return Spiders;
    }
}
