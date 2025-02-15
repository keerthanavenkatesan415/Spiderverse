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
 * 
 * Step 2:
 * ClusterOutputFile name is passed in through the command line as args[1]
 * Output to ClusterOutputFile with the format:
 * 1. n lines, listing all of the dimension numbers connected to 
 *    that dimension in order (space separated)
 *    n is the size of the cluster table.
 * 
 * @author Seth Kelley
 */

public class Clusters {

    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.Clusters <dimension INput file> <collider OUTput file>");
                return;
        }
        
    
    
        String DimensionInputFile = args[0];
        String DimensionOutputFile = args[1];
        StdOut.setFile(DimensionOutputFile);

        Clusters cluster = new Clusters();

        Dimensions [] clusters = cluster.createCluster(DimensionInputFile);
        
        for(int k = 0; k < clusters.length; k++){
            Dimensions ptr4 = clusters[k];
            while(ptr4 != null){
                StdOut.print(ptr4.getDimensionNumber() + " ");
                ptr4 = ptr4.getNext();
            }
            StdOut.println();
        }
    }
    public Dimensions[] createCluster(String filename){
         // WRITE YOUR CODE HERE
        //setting the input file
        
        StdIn.setFile(filename); 
        
        // Reading the number of dimensions
        int a = StdIn.readInt();
        //reading Initial size of the clusters array
        int b = StdIn.readInt();
        // reading the threshold (load value before rehashing)
        double c = StdIn.readDouble();

        StdIn.readLine();

        //Creating a clusters array to store dimensions in each cluster
        Dimensions [] clusters = new Dimensions[b];
        //counts the total dimensions added so far as they are added
        int n = 0;

        //Reads the input file
        for(int count = 0; count < a; count++){

            //reads the dimension number of the dimension to be added
            int dimensionNumber = StdIn.readInt();
            //reads the number of cannon events this dimension has 
            int numCanonEvents = StdIn.readInt();
            //reads the dimension weight
            int dimensionWeight = StdIn.readInt();

            //moves to next line
            StdIn.readLine();

            //hash code to determine which cluster the dimension will be added to 
            int hash;
            // creates a new dimension based on the input values
            Dimensions dim = new Dimensions(dimensionNumber, numCanonEvents, dimensionWeight, null);

            //if the dimensions per cluster so far exceed the threshold
            if(n/b >= c){
                //double the size of cluster
                b*=2;
                //create a temporary array with the doubled size
                Dimensions [] temp = new Dimensions[b];

                //iterate through original cluster array
                for(int i = 0; i < clusters.length; i++){
                    // set a pointer to the current cluster
                    Dimensions ptr = clusters[i];
                    //iterate through every dimension in that cluster 
                    while(ptr != null){
                        //find the hashed element that each dimension belongs in with the new size
                       int hash2 = ptr.getDimensionNumber()%b;
                       Dimensions NewDim = new Dimensions(ptr.getDimensionNumber(), ptr.getNumCanonEvents(), ptr.getDimensionWeight(), null);
                       // set the current dimension into the hashed element of the temp
                       if(temp[hash2] == null){
                        temp[hash2] = NewDim;
                       } 
                       else{
                        NewDim.setNext(temp[hash2]);
                        temp[hash2] = NewDim;
                       }

                        //move to next dimension
                        ptr = ptr.getNext();
                    }
                }
                //set the rehashed temp array to the clusters to update
                clusters = temp;
                //generate a hash key for the new dimension to be added in the rehashed clusters
                hash = dim.getDimensionNumber()%b;

                //if the hash key refres to an element that is empty,
                if(clusters[hash] == null){
                    // add the new dimension into the empty element
                    clusters[hash] = dim;
                    //update the number of dimensions total
                    n++;
                }
                else{ // if hash key refres to an element where there is an existing list of dimensions
                    // add the new dimension to the front of the list
                    dim.setNext(clusters[hash]);
                    //set the new dimension holding the list to the cluster element
                    clusters[hash] = dim;
                    //update dimensions total
                    n++;
                }
            }
            else{ // if the dimension load does NOT exceed the threshold
                // get the hash key for the element to add new dimension
                hash = dim.getDimensionNumber()%b;
                //if the element referred by the hash key is empty
                if(clusters[hash] == null){
                    //add the new dimension into element
                    clusters[hash] = dim;
                    //update dimension total
                    n++;
                }
                else{//if the element referred to by hash key has an existing list of dimensions
                    //add the new dimension to the front of this list
                    dim.setNext(clusters[hash]);
                    //set the new front of the list into the element
                    clusters[hash] = dim;
                    //update n
                    n++;
                }
        }
        }
        
        Dimensions last = clusters[clusters.length-1];
        Dimensions lastWrap = new Dimensions(last.getDimensionNumber(), last.getNumCanonEvents(), last.getDimensionWeight(), null);
        Dimensions secLast = clusters[clusters.length-2];
        Dimensions seclastWrap = new Dimensions(secLast.getDimensionNumber(), secLast.getNumCanonEvents(), secLast.getDimensionWeight(), null);
        Dimensions ptr2;

        for(int i = 0; i < clusters.length; i++){
           
            if(i == 0){
                ptr2 = clusters[0];
                while(ptr2.getNext() != null){
                    ptr2 = ptr2.getNext();
                    
                }
        
                ptr2.setNext(lastWrap);
                ptr2 = ptr2.getNext();
                ptr2.setNext(seclastWrap);

            }
            else if(i == 1){
                ptr2 = clusters[1];

                while(ptr2.getNext() != null){
                    ptr2 = ptr2.getNext();
                }
                Dimensions Dim = clusters[0];
                Dimensions WrapDim = new Dimensions(Dim.getDimensionNumber(), Dim.getNumCanonEvents(), Dim.getDimensionWeight(), null);
                Dimensions last2 = clusters[clusters.length-1];
                Dimensions lastWrap2 = new Dimensions(last2.getDimensionNumber(), last2.getNumCanonEvents(), last2.getDimensionWeight(), null);
                ptr2.setNext(WrapDim);
                ptr2 = ptr2.getNext();
                ptr2.setNext(lastWrap2);
                
            }
            else{
                ptr2 = clusters[i];
                while(ptr2.getNext() != null){
                    ptr2 = ptr2.getNext();
                }
                Dimensions Dim2 = clusters[i-1];
                Dimensions WrapDim2 = new Dimensions(Dim2.getDimensionNumber(), Dim2.getNumCanonEvents(), Dim2.getDimensionWeight(), null);
                Dimensions Dim3 = clusters[i-2];
                Dimensions WrapDim3 = new Dimensions(Dim3.getDimensionNumber(), Dim3.getNumCanonEvents(), Dim3.getDimensionWeight(), null);
                ptr2.setNext(WrapDim2);
                ptr2 = ptr2.getNext();
                ptr2.setNext(WrapDim3);
            }

        }

        return clusters;
    }
       

        
       
    
}
