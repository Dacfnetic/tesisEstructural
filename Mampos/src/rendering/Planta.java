package rendering;

import org.joml.Matrix4f;
import static com.jogamp.opengl.GL4.*;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.*;
import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.GL2GL3.GL_LINE;
import com.jogamp.opengl.awt.GLCanvas;
import components.ControladorDeAnclaje;
import components.ControladorDeCotas;
import components.ControladorDeEscena;
import components.ControladorDeLineas;
import components.ControladorDeMuros;
import components.ControladorDePlanos;
import frames.Interfaz;
import frames.MainFrame;
import java.awt.event.*;
import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

//@author Diego
public class Planta implements GLEventListener, MouseListener, KeyListener, MouseMotionListener, MouseWheelListener {
		
    public GLCanvas myCanvas;

    public int renderingProgram;
    private final int vao[] = new int[1];
    private final int vbo[] = new int[5];

    private float cameraX, cameraY, cameraZ;
    public float cameraCurrentX, cameraCurrentY, cameraCurrentZ;

    public float left, right, top, bottom;

    // allocate variables used in display() function, so that they won’t need to be allocated during rendering
    public final FloatBuffer vals = Buffers.newDirectFloatBuffer(16);  // utility buffer for transferring matrices
    public final Matrix4f pMat = new Matrix4f(); 
    public Matrix4f vMat = new Matrix4f(); 
    public final Matrix4f mMat = new Matrix4f(); 
    public final Matrix4f mvMat = new Matrix4f(); 

    public int mvLoc, pLoc, oLoc;

    private int brickTexture;
    private int checkerBoardTexture;
        
    public Planta(){
        myCanvas = new GLCanvas();
        myCanvas.addGLEventListener(this);
        myCanvas.addMouseListener(this);
        myCanvas.addKeyListener(this);
        myCanvas.addMouseMotionListener(this);
        myCanvas.addMouseWheelListener(this);
        myCanvas.setBounds(0, 0, 600, 600); 
        
    }
    
    public void setDimension(float left, float right, float top, float bottom){
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }
    
    public void init(GLAutoDrawable drawable){
        GL4 gl = (GL4) GLContext.getCurrentGL();
        // 001. Se crea el programa en la GPU donde se procesará todo lo referente a este canvas.
        renderingProgram = mampos.Utils.createShaderProgram("shaders/projectionVertShader.glsl", "shaders/projectionFragShader.glsl");
        // 002. Se cargan las texturas que se utilizarán.
        //TODO: crear funcion para cargar texturas
    	brickTexture = mampos.Utils.loadTexture("textures/brick/Poliigon_BrickReclaimedRunning_7787_BaseColor.jpg");
        checkerBoardTexture = mampos.Utils.loadTexture("textures/ground/GroundWoodChips001_COL_2K.jpg");
        
        // 003. Se generan los array donde se almacenarán los buffers para enviar a la GPU.
        gl.glGenVertexArrays(vao.length,  vao, 0);
        // 004. Se activa el array a utilizar para este canvas.
    	gl.glBindVertexArray(vao[0]);
        // 005. Se generan los arrays donde se almacenarán las propiedades a renderizar.
        gl.glGenBuffers(vbo.length, vbo, 0);
        // 006. Se determina que programa se usará en este canvas.
        gl.glUseProgram(renderingProgram);
        
    	cameraX	= 0.0f;	cameraY	= 0.0f;	cameraZ	= 0.1f;
        vMat.translate(-cameraX, -cameraY, -cameraZ);
        vMat.rotate(6*(float) (Math.PI/12),1.0f,0.0f,0.0f);
        
        
        
    }
    
    public void changeSize(){
       // myCanvas.setBounds(0, 0, Interfaz.verde.getWidth(), Interfaz.verde.getHeight()); 
    }
    
    public void display(GLAutoDrawable drawable){
        
        GL4 gl = (GL4)GLContext.getCurrentGL();
        changeSize();
        gl.glPointSize(30f);
        //gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        // 006. Se limpia el canvas en cada frame.
        gl.glClear(GL_DEPTH_BUFFER_BIT);
        gl.glClear(GL_COLOR_BUFFER_BIT);
        gl.glClearColor(1.0f,1.0f,1.0f,1.0f);
        // 007
        mvLoc = gl.glGetUniformLocation(renderingProgram, "mv_matrix");
        // 008
        pLoc = gl.glGetUniformLocation(renderingProgram, "p_matrix");
        // 009
        oLoc = gl.glGetUniformLocation(renderingProgram, "osnap");
     
      	pMat.setOrtho(this.left, this.right, this.top, this.bottom, 0.1f, 5f);
        gl.glUniformMatrix4fv(pLoc, 1, false, pMat.get(vals));
        gl.glUniform1i(oLoc, 0);
        
        cameraCurrentX = vMat.get(3, 0);
        cameraCurrentY = vMat.get(3, 1);
        cameraCurrentZ = vMat.get(3, 2);
        
      	mvMat.identity();
      	mvMat.mul(vMat);
      	mvMat.mul(mMat);
        gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
        
        
        
        // 020. Se renderizan los objetos en la escena.
        Controlador.dibujar("v",gl, vbo, 1, checkerBoardTexture, ControladorDePlanos.planos, 6, GL_TRIANGLES);
        Controlador.dibujar("v",gl, vbo, 0, brickTexture, ControladorDeMuros.muros, 36, GL_TRIANGLES);
        Controlador.dibujar("v",gl, vbo, 3, brickTexture, ControladorDeLineas.lineas, 2, GL_LINES); 
        
        Controlador.dibujar("t",gl, vbo, 2, checkerBoardTexture, ControladorDePlanos.planos, 6, GL_TRIANGLES);
        ControladorDeCotas.dibujarCotas(gl, vbo, checkerBoardTexture, oLoc);
        if(ControladorDeEscena.drawSnap) ControladorDeAnclaje.drawOsnap(gl, vbo, ControladorDeEscena.worldX, ControladorDeEscena.worldZ, oLoc);
    }
         
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height){
        changeSize();
    }
    
    public void dispose(GLAutoDrawable drawable){}

    @Override
    public void mousePressed(MouseEvent e) {
        ControladorDeMuros.clickEnPlanta(this, e);
        try {
            ControladorDeLineas.clickEnPlanta(this, e, ControladorDeEscena.usandoHerramientaLinea, ControladorDeEscena.contadorDeClicks);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Planta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        ControladorDeEscena.moverMouseEnPlanta(this, e);
    }
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        ControladorDeEscena.scrollMouseEnPlanta(this, e);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        ControladorDeEscena.controlesEnPlanta(this, e);    
    }

    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    

}