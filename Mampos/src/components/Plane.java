package components;

import rendering.Objeto;

public class Plane extends Objeto{
    
    private final float y = 8.0f; // Plane height
    private final float x = 8.0f; // Plane right
    private final float z = 0.0f; // Plane top

    public void setupPlanePositions(){
        vertex.clear();
        
        float[] vertices = {
            -x,y,z, x,y,z, -x,-y,z, x,y,z, -x,-y,z, x,-y,z
        };
        for(int i = 0; i < vertices.length; i++){
            vertex.add(vertices[i]);
        }
    }
    
    public void setupCoordsPositions(){
        uv.clear();
        float[] vertices = {
            0.0f, 1.0f,     1.0f, 1.0f,     0.0f, 0.0f,   1.0f, 1.0f,  0.0f, 0.0f,   1.0f, 0.0f
        };
        for(int i = 0; i < vertices.length; i++){
            uv.add(vertices[i]);
        }
    }
}
