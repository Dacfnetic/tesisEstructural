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
import org.joml.Matrix4f;

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
    
    public static void rellenarBufferDeVertices(GL4 gl, int[] vbo, double[] data, int vboIndex){
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[vboIndex]);
        float[] b = new float[data.length];
        for(int i = 0; i < data.length; i++){b[i] = (float) data[i];}
        FloatBuffer vertBuf = Buffers.newDirectFloatBuffer(b);
        gl.glBufferData(GL_ARRAY_BUFFER, vertBuf.limit()*4, vertBuf, GL_STATIC_DRAW);
    }
    
    public static void prepararBufferDeVertices(GL4 gl, int texture, int vertexQuantity, int objectsQuantity, int mode){
        // 000. Se selecciona la textura activa.
     
        // 000. Se le dice que cuente cada 3 datos en el buffer x, y, z.
      	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
       
        // 000. Se le dice la posicion que se debe seleccionar en el shader.
      	gl.glEnableVertexAttribArray(0);
        // adjust OpenGL settings and draw model
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LEQUAL);
        gl.glDrawArrays(mode, 0, vertexQuantity * objectsQuantity);     
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
        FloatBuffer texBuf = Buffers.newDirectFloatBuffer(b);
        gl.glBufferData(GL_ARRAY_BUFFER, texBuf.limit()*4, texBuf, GL_STATIC_DRAW);
    }
    
    public static void prepararBufferDeTexturas(GL4 gl, int texture, int vertexQuantity, int objectsQuantity, int mode){
        
        // 000. Se le dice que cuente cada 3 datos en el buffer x, y, z.
      	gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        // 000. Se le dice la posicion que se debe seleccionar en el shader.
      	gl.glEnableVertexAttribArray(1);
        // adjust OpenGL settings and draw model
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LEQUAL);
        gl.glDrawArrays(mode, 0, vertexQuantity * objectsQuantity);     
    }
  


    public static void dibujar(GL4 gl, int[] vbo, int vboIndex, int vboIndexT, int texture, List lista, int verticesDeObjeto, int mode,
    Matrix4f viewMatrix, Matrix4f mvMat, int mvLoc, int pLoc, int oLoc, Matrix4f pMat, FloatBuffer vals) {  
    	if(!lista.isEmpty()){
            iterador = lista.iterator();
            while(iterador.hasNext()){
                
                vertices.clear();
                uv.clear();
                
                Objeto objeto = (Objeto) iterador.next();
                
                vertices.addAll(objeto.vertex);
                uv.addAll(objeto.uv);

                mvMat.identity();
                mvMat.mul(viewMatrix);
                mvMat.mul(objeto.mMat);
                gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
                gl.glUniformMatrix4fv(pLoc, 1, false, pMat.get(vals));
                gl.glUniform1i(oLoc, 0);
                
                double[] vertexPositions = vertices.stream().mapToDouble(f -> f).toArray();
                double[] uvPositions = uv.stream().mapToDouble(f -> f).toArray();
                rellenarBufferDeVertices(gl, vbo, vertexPositions, vboIndex);
                prepararBufferDeVertices(gl, texture, verticesDeObjeto, 1, mode);
                rellenarBufferDeTexturas(gl, vbo, uvPositions, vboIndexT);
                prepararBufferDeTexturas(gl, texture, 2* verticesDeObjeto/3, 1, mode);
            }
        }  
    }  
}