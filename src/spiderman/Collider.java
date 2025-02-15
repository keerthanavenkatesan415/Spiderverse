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
 * ColliderOutputFile name is passed in through the command line as args[2]
 * Output to ColliderOutputFile with the format:
 * 1. e lines, each with a different dimension number, then listing
 *       all of the dimension numbers connected to that dimension (space separated)
 * 
 * @author Seth Kelley
 */

public class Collider {

    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.Collider <dimension INput file> <spiderverse INput file> <collider OUTput file>");
                return;
        }

         // WRITE YOUR CODE HERE
        //setting the input file
        String DimensionInputFile = args[0];
        String SpiderverseInputFile = args[1];
        String outPutFile = args[2];

        Collider newCollider = new Collider();
        HashMap<Integer, ArrayList<Person>> spiderPpl = newCollider.addPerson(SpiderverseInputFile);

       /*  for(int key : spiderPpl.keySet()){
            ArrayList<Person> list = spiderPpl.get(key);
            StdOut.print(key);
            for(int j = 0; j < list.size(); j++){
                StdOut.print(  " " + list.get(j).getName());
            } 
            StdOut.println();
        }*/

        Clusters NewClusters = new Clusters();

        Dimensions [] clusters = NewClusters.createCluster(DimensionInputFile);
    
        HashMap<Integer, ArrayList<Dimensions>> adjList = newCollider.colliderList(clusters);
        StdOut.setFile(outPutFile);

        /*for(int key : adjList.keySet()){
            ArrayList<Integer> list = adjList.get(key);
            StdOut.print(key + " ");
            for(int j = 0; j < list.size(); j++){
                StdOut.print(list.get(j)+ " ");
            }
            StdOut.println();
        } */
        for(int key : adjList.keySet()){
            ArrayList<Dimensions> list = adjList.get(key);
            StdOut.print(key + " ");
            for(int j = 0; j < list.size(); j++){
                StdOut.print(list.get(j).getDimensionNumber()+ " ");
            } 
            StdOut.println();
        }

    }

    public HashMap<Integer, ArrayList<Dimensions>> colliderList(Dimensions [] clusters){
        HashMap<Integer, ArrayList<Dimensions>> adjList = new HashMap<Integer, ArrayList<Dimensions>>();

        for(int i = 0; i < clusters.length; i++){

            Dimensions ptr = clusters[i];
            ptr = ptr.getNext();
            Dimensions FirstPtr = clusters[i];
            ArrayList<Dimensions> DimList = new ArrayList<Dimensions>();

            while(ptr != null){
                int dNum = ptr.getDimensionNumber();
                int NumCanon = ptr.getNumCanonEvents();
                int weight = ptr.getDimensionWeight();

                Dimensions dimension = new Dimensions(dNum, NumCanon,weight,null);

                DimList.add(dimension);

                int FirstdNum = FirstPtr.getDimensionNumber();
                int FirstNumCanon = FirstPtr.getNumCanonEvents();
                int Firstweight = FirstPtr.getDimensionWeight();

                Dimensions FirstDimension = new Dimensions(FirstdNum, FirstNumCanon, Firstweight,null);

                if(adjList.containsKey(ptr.getDimensionNumber())){
                    adjList.get(ptr.getDimensionNumber()).add(FirstDimension);
                }
                else{
                    ArrayList<Dimensions> DimList2 = new ArrayList<Dimensions>(); 
                    DimList2.add(FirstDimension);
                    adjList.put(ptr.getDimensionNumber(), DimList2);
                }
                ptr = ptr.getNext();
            }
            if(!adjList.containsKey(FirstPtr.getDimensionNumber())){
                adjList.put(FirstPtr.getDimensionNumber(), DimList);

            }
            else{
                for(int j = 0; j < DimList.size(); j++){
                    adjList.get(FirstPtr.getDimensionNumber()).add(DimList.get(j));
                }
            }

             

                
        }
        /*HashMap<Integer, ArrayList<Integer>> adjList = new HashMap<Integer, ArrayList<Integer>>();

        for(int i = 0; i < clusters.length; i++){

            Dimensions ptr = clusters[i];
            ptr = ptr.getNext();
            Dimensions FirstPtr = clusters[i];
            ArrayList<Integer> DimList = new ArrayList<Integer>();

            while(ptr != null){
                DimList.add(ptr.getDimensionNumber());

                 ArrayList<Integer> DimList2 = new ArrayList<Integer>();

                if(adjList.containsKey(ptr.getDimensionNumber())){

                    adjList.get(ptr.getDimensionNumber()).add(FirstPtr.getDimensionNumber());
                }
                else{
                    DimList2.add(FirstPtr.getDimensionNumber());
                    adjList.put(ptr.getDimensionNumber(), DimList2);
                }
                ptr = ptr.getNext();
            }

            adjList.put(FirstPtr.getDimensionNumber(), DimList);

                
        } */

        return adjList;
    }

        
    public HashMap<Integer, ArrayList<Person>> addPerson (String filename){
        StdIn.setFile(filename);

        int NumPpl = StdIn.readInt();

        StdIn.readLine();
        HashMap<Integer, ArrayList<Person>> people = new HashMap<Integer, ArrayList<Person>>();

        for(int k = 0; k < NumPpl; k++){
            int Dimension = StdIn.readInt();
            String PersonName = StdIn.readString();
            int signature = StdIn.readInt();

            if(people.containsKey(Dimension)){
                Person person = new Person(Dimension, PersonName, signature);
                people.get(Dimension).add(person);
            }
            else{
                Person person = new Person(Dimension, PersonName, signature);
                ArrayList<Person> pplList = new ArrayList<Person>(); 
                pplList.add(person);
                people.put(Dimension, pplList);
            }

        } 
        return people;
    }
        

}