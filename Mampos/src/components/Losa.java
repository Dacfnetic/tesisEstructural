package components;

import rendering.Objeto;

public class Losa extends Objeto{
    
        
    public Losa(){}
    
    
    
    public void setupLosaPositions(){
        vertex.clear();
        
        float[] vertices = {
            -0.5f, 0.0f, 0.5f,
            -0.5f, 0.0f, -0.5f, 
            0.5f, -0.0f, 0.5f, 
            0.5f, -0.0f, -0.5f
        };
        
        int[] indices = {
            0, 1, 3,
            3, 1, 2     
        };
        
        for(int i = 0; i < vertices.length; i++){
            vertex.add(vertices[i]);
        }
        for(int i = 0; i < indices.length; i++){
            indexes.add(indices[i]);
        }
        
    }
}