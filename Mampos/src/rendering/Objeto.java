package rendering;

import java.util.ArrayList;
import java.util.List;
import org.joml.Matrix4f;

public abstract class Objeto {
    
    public List<Float> vertex = new ArrayList<>();
    public List<Integer> indexes = new ArrayList<>();
    public List<Float> uv = new ArrayList<>();
    
    private int vaoID;
    private int vertexCount;
    
    public Matrix4f mMat = new Matrix4f();
    
    public int getVaoID() {
        return vaoID;
    }

    public void setVaoID(int vaoID) {
        this.vaoID = vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }
    
}