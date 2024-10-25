package rendering;

import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import com.jogamp.opengl.awt.GLCanvas;
import components.ControladorDeLosas;
import components.Losa;

//@author Diego
public class Perfil implements GLEventListener {
		
    public GLCanvas myCanvas;

    public int renderingProgram;
    private final int vao[] = new int[1];
    private final int vbo[] = new int[2];
    
    private Losa losa = new Losa();

    public Perfil(){
        myCanvas = new GLCanvas();
        myCanvas.addGLEventListener(this);
        myCanvas.setBounds(0, 0, 600, 600);   
    }
 
    public void init(GLAutoDrawable drawable){
        GL4 gl = (GL4) GLContext.getCurrentGL();
         // 001. Se crea el programa en la GPU donde se procesará todo lo referente a este canvas.
        renderingProgram = mampos.Utils.createShaderProgram("shaders/projectionVertShader.glsl", "shaders/projectionFragShader.glsl");
       
        gl.glGenVertexArrays(vao.length,  vao, 0);
    	gl.glBindVertexArray(vao[0]);
        gl.glGenBuffers(vbo.length, vbo, 0);
         // 006. Se determina que programa se usará en este canvas.
        gl.glUseProgram(renderingProgram);
    }
    
    public void display(GLAutoDrawable drawable){
        GL4 gl = (GL4)GLContext.getCurrentGL();
        // 006. Se limpia el canvas en cada frame.
        gl.glClear(GL_DEPTH_BUFFER_BIT);
        gl.glClear(GL_COLOR_BUFFER_BIT);
        gl.glClearColor(1.0f,0.0f,0.0f,1.0f);
        // 020. Se renderizan los objetos en la escena.
       
        Controlador.dibujar("i",gl,vbo,0,0,ControladorDeLosas.losas,6,GL_TRIANGLES);
   }
         
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height){}
   
    public void dispose(GLAutoDrawable drawable){}
}