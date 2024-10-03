package rendering;

public class RawModel {
    
    private int vaoID;
    private int vertexCount;
    
    public int getVaoID() {return vaoID;}
    public int getVertexCount() {return vertexCount;}

    public void setVaoID(int vaoID) {this.vaoID = vaoID;}
    public void setVertexCount(int vertexCount) {this.vertexCount = vertexCount;}
    public void setParams(int vaoID, int vertexCount){
        setVaoID(vaoID);
        setVertexCount(vertexCount);
    }
    
    public RawModel(){}
    
}
