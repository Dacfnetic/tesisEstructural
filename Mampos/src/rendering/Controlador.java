/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rendering;

import com.jogamp.common.nio.Buffers;
import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_FLOAT;
import static com.jogamp.opengl.GL.GL_LEQUAL;
import static com.jogamp.opengl.GL.GL_STATIC_DRAW;
import static com.jogamp.opengl.GL.GL_TEXTURE0;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import com.jogamp.opengl.GL4;
import java.nio.FloatBuffer;

/**
 *
 * @author diego
 */
public abstract class Controlador {
    
    public static void rellenarBuffer(GL4 gl, int[] vbo, float[] data, int vboIndex){
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[vboIndex]);
        FloatBuffer vertBuf = Buffers.newDirectFloatBuffer(data);
        gl.glBufferData(GL_ARRAY_BUFFER, vertBuf.limit()*4, vertBuf, GL_STATIC_DRAW);
    }
    
    
    public static void prepararBuffer(GL4 gl, int texture, int vertexQuantity, int objectsQuantity){
        // 000. Se selecciona la textura activa.
        gl.glActiveTexture(GL_TEXTURE0);
        // 001. Se enlaza la textura activa.
        gl.glBindTexture(GL_TEXTURE_2D, texture);
        // 000. Se le dice que cuente cada 3 datos en el buffer x, y, z.
      	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        // 000. Se le dice la posicion que se debe seleccionar en el shader.
      	gl.glEnableVertexAttribArray(0);
        // adjust OpenGL settings and draw model
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LEQUAL);
        gl.glDrawArrays(GL_TRIANGLES, 0, vertexQuantity * objectsQuantity);     
    }
    
    
}
