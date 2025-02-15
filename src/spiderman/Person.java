package spiderman;
public class Person {

    private int dim;
    private String Name;
    private int signature;

    public Person( int dimension, String name, int sign){
        this.dim = dimension;
        this.Name = name;
        this.signature = sign;
    }

    public String getName() {
        return Name;
    }
    public int getSignature() {
        return signature;
    }
    public int getDim() {
        return dim;
    }
    public void setName(String name) {
        this.Name = name;
    }
    public void setSignature(int signature) {
        this.signature = signature;
    }
    public void setDim(int dim) {
        this.dim = dim;
    }
    
}
