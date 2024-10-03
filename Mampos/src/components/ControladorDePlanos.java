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
import static com.jogamp.opengl.GL.GL_STATIC_DRAW;
import static com.jogamp.opengl.GL.GL_TEXTURE0;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_TRIANGLE_STRIP;
import com.jogamp.opengl.GL4;
import java.nio.FloatBuffer;

/**
 *
 * @author diego
 */
public abstract class ControladorDePlanos {
    
    public static void drawPlanes(GL4 gl, int[] vbo, int texture) {
      	// associate VBO with the corresponding vertex attribute in the vertex shader
        gl.glGenBuffers(vbo.length, vbo, 0);
        createPlanesBuffer(gl, vbo);
        preparePlanesBuffer(gl, vbo, texture);        
        // adjust OpenGL settings and draw model
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LEQUAL);
        gl.glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
   }
    
    private static void createPlanesBuffer(GL4 gl, int[] vbo) {
        Plane hoja = new Plane();
         
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
    	FloatBuffer vertBuf = Buffers.newDirectFloatBuffer(hoja.setupPlanePositions());
    	gl.glBufferData(GL_ARRAY_BUFFER, vertBuf.limit()*4, vertBuf, GL_STATIC_DRAW);
        
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[2]);
        FloatBuffer pTexBuf = Buffers.newDirectFloatBuffer(hoja.setupCoordsPositions());
        gl.glBufferData(GL_ARRAY_BUFFER, pTexBuf.limit()*4, pTexBuf, GL_STATIC_DRAW);
    }
    
    private static void preparePlanesBuffer(GL4 gl, int[] vbo, int checkerBoardTexture) {
      
        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, checkerBoardTexture);
        
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
      	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
      	gl.glEnableVertexAttribArray(0);
        
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[2]);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0,0);
        gl.glEnableVertexAttribArray(1);
   }
  
}
