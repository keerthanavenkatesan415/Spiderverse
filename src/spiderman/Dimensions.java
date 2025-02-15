package spiderman;


public class Dimensions {

    private int dimensionNumber;
    private int NumCanonEvents;
    private int dimensionWeight;
    private Dimensions next;

    public Dimensions(int DimNum, int NCE, int DimWeight, Dimensions next){
       this.dimensionNumber = DimNum;
       this. NumCanonEvents = NCE;
       this.dimensionWeight = DimWeight;
       this.next = next;
    }

    public int getDimensionNumber(){
        return dimensionNumber;
    }
    public int getDimensionWeight(){
        return dimensionWeight;
    }
    public int getNumCanonEvents(){
        return NumCanonEvents;
    }
    public Dimensions getNext() {
        return next;
    }
   
    public void setDimensionNumber(int num){
        dimensionNumber = num;
    }
    public void setDimensionWeight(int num){
        dimensionWeight = num;
    }
    public void setNumCanonEvents(int num){
        NumCanonEvents = num;
    }
    public void setNext(Dimensions next) {
        this.next = next;
    }
   
   
    
}
