/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import com.jogamp.opengl.math.Vec3f;
import rendering.Objeto;

/**
 *
 * @author diego
 */
public class Plane extends Objeto{
    
    private float z = 0.0f; // Plane height
    private float l = -8.0f; // Plane left
    private float r = 8.0f; // Plane right
    private float b = -8.0f; // Plane bottom
    private float t = 8.0f; // Plane top
    private Vec3f topLeft = new Vec3f(l,z,t);
    private Vec3f topRight = new Vec3f(r,z,t);
    private Vec3f bottomLeft = new Vec3f(l,z,b);
    private Vec3f bottomRight = new Vec3f(r,z,b);
    private float[][] nodes = {{l,z,t},{r,z,t},{l,z,b},{r,z,b}};
    
    public Plane(){
       
    }
     
    public void setupPlanePositions(){
        vertex.clear();
        
        float[] vertices = {
            l,z,t, l,z,b,  r,z,t,  r,z,t, l,z,b, r,z,b
        };
        for(int i = 0; i < vertices.length; i++){
            vertex.add(vertices[i]);
        }
    }
    
    public void setupCoordsPositions(){
        uv.clear();
        float[] vertices = {
            0.0f, 1.0f,     0.0f, 0.0f,     1.0f, 1.0f,   1.0f, 1.0f,  0.0f, 0.0f,   1.0f, 0.0f
        };
        for(int i = 0; i < vertices.length; i++){
            uv.add(vertices[i]);
        }
    }
}
