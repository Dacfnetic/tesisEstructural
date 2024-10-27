
package rendering;

import com.jogamp.common.nio.Buffers;
import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL2GL3.GL_LINE;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.awt.GLCanvas;
import components.ControladorDeAnclaje;
import components.ControladorDeCotas;
import components.ControladorDeEscena;
import components.ControladorDeMuros;
import components.ControladorDePlanos;
import java.nio.FloatBuffer;
import org.joml.Matrix4f;

public class Room {

    private GLCanvas myCanvas;

    private Renderer renderer;
    
    private RawModel model;

    public GLCanvas getMyCanvas() {return myCanvas;}

    public void setMyCanvas(GLCanvas myCanvas) {this.myCanvas = myCanvas;}
       
    public Room(){}
    
    public void init(GLAutoDrawable drawable){
     
        renderer = new Renderer();
        float[] vertices = {
        -0.5f, 0.5f, 0f,
        -0.5f,-0.5f, 0f,
         0.5f,-0.5f, 0f,
         
        0.5f,-0.5f, 0f,
        0.5f, 0.5f, 0f,
        -0.5f,0.5f, 0f
        };
    
    }
    
    public void display(GLAutoDrawable drawable){
        renderer.prepare();
        renderer.render(model);   
    }
    
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height){}
    public void dispose(GLAutoDrawable drawable){
       
    }

    
}
