/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import com.jogamp.common.nio.Buffers;
import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_FLOAT;
import static com.jogamp.opengl.GL.GL_LEQUAL;
import static com.jogamp.opengl.GL.GL_POINTS;
import static com.jogamp.opengl.GL.GL_STATIC_DRAW;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import java.nio.FloatBuffer;


/**
 *
 * @author diego
 */
public abstract class ControladorDeAnclaje {
    
    public static void drawOsnap(GL4 gl, int[] vbo, float worldX, float worldZ, int oLoc) {
      	// associate VBO with the corresponding vertex attribute in the vertex shader
        gl.glGenBuffers(vbo.length, vbo, 0);
        gl.glUniform1i(oLoc, 1);
        
        createOsnapBuffer(worldX, worldZ, vbo);
        prepareOsnapBuffer(gl, vbo);
              
        // adjust OpenGL settings and draw model
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LEQUAL);
        gl.glDrawArrays(GL_POINTS, 0, 1);
   }
  
    private static void createOsnapBuffer(float worldX, float worldZ, int[] vbo) {
        GL4 gl = (GL4)GLContext.getCurrentGL();
        
        float[] position = {
            worldX, 0, worldZ
        };
        
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[3]);
    	FloatBuffer vertBuf = Buffers.newDirectFloatBuffer(position);
    	gl.glBufferData(GL_ARRAY_BUFFER, vertBuf.limit()*4, vertBuf, GL_STATIC_DRAW);
        
    }
    
    private static void prepareOsnapBuffer(GL4 gl, int[] vbo) {
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[3]);
      	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
      	gl.glEnableVertexAttribArray(0);
   }
   
}
