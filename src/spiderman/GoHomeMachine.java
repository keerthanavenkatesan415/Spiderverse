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
 * Read from the SpotInputFile with the format:
 * One integer
 *      i.    The dimensional number of the starting hub (int)
 * 
 * Step 4:
 * AnomaliesInputFile name is passed through the command line as args[3]
 * Read from the AnomaliesInputFile with the format:
 * 1. e (int): number of anomalies in the file
 * 2. e lines, each with:
 *      i.   The Name of the anomaly which will go from the hub dimension to their home dimension (String)
 *      ii.  The time allotted to return the anomaly home before a canon event is missed (int)
 * 
 * Step 5:
 * ReportOutputFile name is passed in through the command line as args[4]
 * Output to ReportOutputFile with the format:
 * 1. e Lines (one for each anomaly), listing on the same line:
 *      i.   The number of canon events at that anomalies home dimensionafter being returned
 *      ii.  Name of the anomaly being sent home
 *      iii. SUCCESS or FAILED in relation to whether that anomaly made it back in time
 *      iv.  The route the anomaly took to get home
 * 
 * @author Seth Kelley
 */

public class GoHomeMachine {
    
    public static void main(String[] args) {

        if ( args.length < 5 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.GoHomeMachine <dimension INput file> <spiderverse INput file> <hub INput file> <anomalies INput file> <report OUTput file>");
                return;
        }

        // WRITE YOUR CODE HERE
        String DimensionInputFile = args[0];
        String SpiderverseInputFile = args[1];
        String hubInPutFile = args[2];
        String AnomaliesInputFile = args[3];
        String outPutFile = args[4];

        StdIn.setFile(hubInPutFile);

        int hub = StdIn.readInt();

        StdIn.setFile(AnomaliesInputFile);   
        
        int NumAnomalies = StdIn.readInt();
        StdIn.readLine();
        HashMap< String, Integer>  AnomaliesFound = new HashMap<String, Integer> ();

        for(int j = 0; j < NumAnomalies; j++){
            String Name = StdIn.readString();
            int time = StdIn.readInt();
            StdIn.readLine();
            AnomaliesFound.put(Name, time);
        } 

        Clusters NewClusters = new Clusters();

        Dimensions [] clusters = NewClusters.createCluster(DimensionInputFile);
        HashMap<Integer, Integer> CanonEvents = new HashMap<Integer, Integer>();

        for(int d = 0; d < clusters.length; d++){
            for(Dimensions c = clusters[d]; c != null; c = c.getNext()){
                CanonEvents.put(c.getDimensionNumber(), c.getNumCanonEvents());
            }
        }

        Collider collider = new Collider();
        HashMap<Integer, ArrayList<Dimensions>> adjList = collider.colliderList(clusters);
       

        HashMap<Integer, ArrayList<Person>> people = collider.addPerson(SpiderverseInputFile); 

        //CollectAnomalies collectAnomalies  = new CollectAnomalies();

        //HashMap<Integer, Person> Spiders = collectAnomalies.findSpiders(people, hub);

        for(String key: AnomaliesFound.keySet()){
            for(int PPlkey : people.keySet()){
                ArrayList<Person> ppl = people.get(PPlkey);
                for(int i = 0; i < ppl.size(); i++){
                    if(ppl.get(i).getName().equals(key)){
                        ppl.get(i).setDim(hub);
                    }
    
                }
            }
        }

        StdOut.setFile(outPutFile);

        for(String key: AnomaliesFound.keySet()){
            for(int PPlkey : people.keySet()){
                ArrayList<Person> ppl = people.get(PPlkey);

                for(int i = 0; i < ppl.size(); i++){
                    if(ppl.get(i).getName().equals(key)){
                        int dimSig = ppl.get(i).getSignature();
                        int dimNum = ppl.get(i).getDim();

                        HashMap<Integer, Boolean> marked = new HashMap<Integer, Boolean>();
                        ArrayList<Integer> PathTo = new ArrayList<Integer>();
                    
                        for(int Listkey : adjList.keySet()){
                            marked.put(Listkey, false);
                        }

                        BFS ReturnAnomaly = new BFS(marked, hub);
                        HashMap<String, ArrayList<Integer>> Home = ReturnAnomaly.GoHomeBFS(adjList, PathTo, dimSig, hub, AnomaliesFound.get(key), clusters, key);

                        ppl.get(i).setSignature(ppl.get(i).getDim());

                        for(String HomeKey : Home.keySet()){
                            if(HomeKey.equals("FAILED")){
                                int canon = CanonEvents.get(dimSig) - 1;
                                StdOut.print(canon + " "+key +  " " + HomeKey + " ");

                                ArrayList<Integer> Path = Home.get(HomeKey);

                                for(int f = Path.size()-1; f >= 0; f--){
                                    StdOut.print(Path.get(f) + " ");
                                    
                                }
                                StdOut.println();

                            }
                            else if(HomeKey.equals("SUCCESS")){
                                int canon = CanonEvents.get(dimSig);
                                StdOut.print(canon + " "+ key   + " " + HomeKey + " ");

                                ArrayList<Integer> Path = Home.get(HomeKey);

                                for(int f = Home.get(HomeKey).size() -1; f >= 0; f--){
                                    StdOut.print(Path.get(f) + " ");
                                    
                                }
                                StdOut.println();
                            }
                        }

                    }
    
                }
            }
        }
       
        
    }
}
