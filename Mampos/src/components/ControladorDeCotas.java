package components;

//@author diego

import com.jogamp.common.nio.Buffers;
import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_FLOAT;
import static com.jogamp.opengl.GL.GL_LEQUAL;
import static com.jogamp.opengl.GL.GL_LINES;
import static com.jogamp.opengl.GL.GL_STATIC_DRAW;
import static com.jogamp.opengl.GL.GL_TEXTURE0;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.math.Vec3f;
import static components.ControladorDeMuros.comprobarOrtogonalidad;
import static components.ControladorDeMuros.contadorDeClicks;
import static components.ControladorDeMuros.crearPorClicks;
import static components.ControladorDeMuros.crearPorDistanciaYClick;
import static components.ControladorDeMuros.eliminarTemporal;
import static components.ControladorDeMuros.setearVerticesDeMuroSegunForma;
import static components.ControladorDeMuros.temporal;
import static components.ControladorDeMuros.x1;
import static components.ControladorDeMuros.x2;
import static components.ControladorDeMuros.y1;
import static components.ControladorDeMuros.y2;
import static components.ControladorDeMuros.z1;
import static components.ControladorDeMuros.z2;
import java.nio.FloatBuffer;



public abstract class ControladorDeCotas {
    
    public static Cota cotas[] = new Cota[0];
    public static final Cota cotaTemporal = new Cota();
    
    public static void agregarCotaACotas(Cota nuevaCota){
        Cota cotasTemporales[] = new Cota[cotas.length + 1]; // Creo un array de muros temporales que van a hacer referencia a los muros actuales pero va a tener espacio para agregar el nuevo muro
        System.arraycopy(cotas, 0, cotasTemporales, 0, cotas.length); // Copio el array
        cotasTemporales[cotasTemporales.length-1] = nuevaCota; // Asigno el nuevo muro a la última posición del array
        cotas = cotasTemporales.clone();
    }
    
    public static void borrarCota(Cota cotaABorrar){
        int indice = 0;
        boolean coincidencia = false;
        for (Cota cota : cotas) {
            if (cotaABorrar.equals(cota)){
                 coincidencia = true;
                 break;
            }
            indice++;
        }
        if(coincidencia){
            Cota cotasTemporales[] = new Cota[cotas.length - 1]; // Creo un array de muros temporales que van a hacer referencia a los muros actuales pero va a tener espacio para agregar el nuevo muro
            System.arraycopy(cotas, 0, cotasTemporales, 0, indice); // Copio el array
            if(indice != cotasTemporales.length){
               System.arraycopy(cotas, indice + 1, cotasTemporales, indice, cotas.length - indice - 1); // Copio el array
            }
            cotas = cotasTemporales.clone();
        }
    }

    public static void dibujarCotas(GL4 gl, int[] vbo, int texture, int oLoc) {
        
        int cantidadDeCotas = cotas.length;
    	crearBufferDeCotas(gl, vbo);
        prepararBufferDeCotas(gl, texture, vbo);
        gl.glUniform1i(oLoc, 2);
        // adjust OpenGL settings and draw model
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LEQUAL);
        gl.glDrawArrays(GL_LINES, 0, 6 * 3 * (cantidadDeCotas+1));
        try{
            borrarCota(cotaTemporal);
        } catch (Exception e){
            
        }
    }

    private static void crearBufferDeCotas(GL4 gl, int[] vbo){ 
        
        if(cotas.length > 0) {
            float[] vertexPositions = new float[cotas.length * 6 * 3];
            int indice = 0;
            for (Cota cota : cotas) {
                if (cota != null) {
                    System.arraycopy(cota.vertexParallelLine, 0, vertexPositions, indice, cota.vertexParallelLine.length);
                    indice += cota.vertexParallelLine.length;
                    System.arraycopy(cota.vertexPerpendicularLine1, 0, vertexPositions, indice, cota.vertexPerpendicularLine1.length);
                    indice += cota.vertexPerpendicularLine1.length;
                    System.arraycopy(cota.vertexPerpendicularLine2, 0, vertexPositions, indice, cota.vertexPerpendicularLine2.length);
                    indice += cota.vertexPerpendicularLine2.length;
                }
            }
    
            gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[4]);
            FloatBuffer vertBuf = Buffers.newDirectFloatBuffer(vertexPositions);
            gl.glBufferData(GL_ARRAY_BUFFER, vertBuf.limit()*4, vertBuf, GL_STATIC_DRAW);
    	}
    }
    
    private static void prepararBufferDeCotas(GL4 gl, int texture, int[] vbo){
        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, texture);
      	// associate VBO with the corresponding vertex attribute in the vertex shader
      	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[4]);
      	gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
      	gl.glEnableVertexAttribArray(0);
    }
    
    public static void dibujarCotaDeMuroTemporal(Muro muroTemporal){
        cotaTemporal.setearVertices(muroTemporal);  
        agregarCotaACotas(cotaTemporal);
    }
  
    
}
