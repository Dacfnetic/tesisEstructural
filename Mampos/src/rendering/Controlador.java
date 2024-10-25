package rendering;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_ELEMENT_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_FLOAT;
import static com.jogamp.opengl.GL.GL_LEQUAL;
import static com.jogamp.opengl.GL.GL_STATIC_DRAW;
import static com.jogamp.opengl.GL.GL_TEXTURE0;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_UNSIGNED_INT;
import com.jogamp.opengl.GL4;
import static components.ControladorDeMuros.vertices;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Controlador {
    
    public static List<Integer> vaos = new ArrayList<Integer>();
    public static List<Integer> vbos = new ArrayList<Integer>();
    
    private static Iterator<?> iterador;
    public static List<Float> vertices = new ArrayList<>();
    public static List<Integer> indices = new ArrayList<>();
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
    
    public static void crearBufferDeVerticesF(GL4 gl, int[] vbo, List lista, int vboIndex){ 
        iterador = lista.iterator();
        vertices.clear();
        indices.clear();
        while(iterador.hasNext()){
            Objeto objeto = (Objeto) iterador.next();
            vertices.addAll(objeto.vertex);
            indices.addAll(objeto.indexes);
        }
        double[] vertexPositions = vertices.stream().mapToDouble(f -> f).toArray();
        int[] indexesPositions = indices.stream().mapToInt(i -> i).toArray();
        rellenarBufferDeVerticesI(gl, vbo, indexesPositions, vboIndex);
        rellenarBufferDeVertices(gl, vbo, vertexPositions, vboIndex);
       
    }
    
    public static void rellenarBufferDeVerticesI(GL4 gl, int[] vbo, int[] data, int vboIndex){
        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo[vboIndex]);
        IntBuffer indexBuf = Buffers.newDirectIntBuffer(data);
        gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuf.limit()*4, indexBuf, GL_STATIC_DRAW);
    }
    
    public static void rellenarBufferDeVertices(GL4 gl, int[] vbo, double[] data, int vboIndex){
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[vboIndex]);
        float[] b = new float[data.length];
        for(int i = 0; i < data.length; i++){b[i] = (float) data[i];}
        FloatBuffer vertBuf = Buffers.newDirectFloatBuffer(b);
        gl.glBufferData(GL_ARRAY_BUFFER, vertBuf.limit()*4, vertBuf, GL_STATIC_DRAW);
    }
    
    public static void prepararBufferDeVertices(GL4 gl, int texture, int vertexQuantity, int objectsQuantity, int mode){
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
        gl.glDrawArrays(mode, 0, vertexQuantity * objectsQuantity);     
    }
    
    public static void prepararBufferDeVerticesF(GL4 gl, int texture, int vertexQuantity, int objectsQuantity, int mode){
     // 000. Se selecciona la textura activa.
        gl.glActiveTexture(GL_TEXTURE0);
        // 001. Se enlaza la textura activa.
        gl.glBindTexture(GL_TEXTURE_2D, texture);
      	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 6, 0);
       
        
        // 000. Se le dice la posicion que se debe seleccionar en el shader.
      	gl.glEnableVertexAttribArray(0);
        // adjust OpenGL settings and draw model
       
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LEQUAL);
        gl.glDrawElements(mode, vertexQuantity * objectsQuantity, GL_UNSIGNED_INT, 0);     
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
    
    public static void prepararBufferDeTexturas(GL4 gl, int texture, int vertexQuantity, int objectsQuantity, int mode){
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
        gl.glDrawArrays(mode, 0, vertexQuantity * objectsQuantity);     
    }
  


    public static void dibujar(String tipo, GL4 gl, int[] vbo, int vboIndex, int brickTexture, List lista, int verticesDeObjeto, int mode) {  
    	if(tipo  == "v"){
            crearBufferDeVertices(gl, vbo, lista, vboIndex);
            prepararBufferDeVertices(gl, brickTexture, verticesDeObjeto, lista.size(), mode);
        }
        if(tipo  == "t"){
            crearBufferDeTexturas(gl, vbo, lista, vboIndex);
            prepararBufferDeTexturas(gl, brickTexture, verticesDeObjeto, lista.size(), mode);
        }
        if(tipo  == "i"){
            crearBufferDeVerticesF(gl, vbo, lista, vboIndex);
            prepararBufferDeVerticesF(gl, brickTexture, verticesDeObjeto, lista.size(), mode);
        }
     
        
    }
    
    
    public static void dibujarObjeto(GL4 gl, Objeto objeto, int mode) {  
        gl.glBindVertexArray(objeto.getVaoID());
        gl.glEnableVertexAttribArray(0);
        gl.glDrawArrays(mode, 0, objeto.getVertexCount());
        gl.glDisableVertexAttribArray(0);
        gl.glBindVertexArray(0);
    }
    
}