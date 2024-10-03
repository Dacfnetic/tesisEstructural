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
import static components.ControladorDeMuros.vertices;
import components.Muro;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author diego
 */
public abstract class Controlador {
    
    private static Iterator<?> iterador;
    public static List<Float> vertices = new ArrayList<>();
    public static List<Float> uv = new ArrayList<>();
    
    public static void crearBufferDeVertices(GL4 gl, int[] vbo, List lista, int vboIndex){ 
        iterador = lista.iterator();
        vertices.clear();
        while(iterador.hasNext()){
            Objeto objeto = (Objeto) iterador.next();
            vertices.addAll(objeto.vertex);
        }
        double[] vertexPositions = vertices.stream().mapToDouble(f -> f).toArray();
        rellenarBufferDeVertices(gl, vbo, vertexPositions, vboIndex);
    }
    
    public static void rellenarBufferDeVertices(GL4 gl, int[] vbo, double[] data, int vboIndex){
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[vboIndex]);
        float[] b = new float[data.length];
        for(int i = 0; i < data.length; i++){b[i] = (float) data[i];}
        FloatBuffer vertBuf = Buffers.newDirectFloatBuffer(b);
        gl.glBufferData(GL_ARRAY_BUFFER, vertBuf.limit()*4, vertBuf, GL_STATIC_DRAW);
    }
    
    public static void prepararBufferDeVertices(GL4 gl, int texture, int vertexQuantity, int objectsQuantity){
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
    
    public static void crearBufferDeTexturas(GL4 gl, int[] vbo, List lista, int vboIndex){ 
        iterador = lista.iterator();
        uv.clear();
        while(iterador.hasNext()){
            Objeto objeto = (Objeto) iterador.next();
            uv.addAll(objeto.uv);
        }
        double[] uvPositions = uv.stream().mapToDouble(f -> f).toArray();
        rellenarBufferDeTexturas(gl, vbo, uvPositions, vboIndex);
    }
    
    public static void rellenarBufferDeTexturas(GL4 gl, int[] vbo, double[] data, int vboIndex){
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[vboIndex]);
        float[] b = new float[data.length];
        for(int i = 0; i < data.length; i++){b[i] = (float) data[i];}
        FloatBuffer vertBuf = Buffers.newDirectFloatBuffer(b);
        gl.glBufferData(GL_ARRAY_BUFFER, vertBuf.limit()*4, vertBuf, GL_STATIC_DRAW);
    }
    
    public static void prepararBufferDeTexturas(GL4 gl, int texture, int vertexQuantity, int objectsQuantity){
        // 000. Se selecciona la textura activa.
        gl.glActiveTexture(GL_TEXTURE0);
        // 001. Se enlaza la textura activa.
        gl.glBindTexture(GL_TEXTURE_2D, texture);
        // 000. Se le dice que cuente cada 3 datos en el buffer x, y, z.
      	gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        // 000. Se le dice la posicion que se debe seleccionar en el shader.
      	gl.glEnableVertexAttribArray(1);
        // adjust OpenGL settings and draw model
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LEQUAL);
        gl.glDrawArrays(GL_TRIANGLES, 0, vertexQuantity * objectsQuantity);     
    }
     
    public static void dibujar(String tipo, GL4 gl, int[] vbo, int vboIndex, int brickTexture, List lista, int verticesDeObjeto) {  
    	if(tipo  == "v"){
            crearBufferDeVertices(gl, vbo, lista, vboIndex);
            prepararBufferDeVertices(gl, brickTexture, verticesDeObjeto, lista.size());
        }
        if(tipo  == "t"){
            crearBufferDeTexturas(gl, vbo, lista, vboIndex);
            prepararBufferDeTexturas(gl, brickTexture, verticesDeObjeto, lista.size());
        }
        
    }
    
}