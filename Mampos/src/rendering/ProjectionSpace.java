/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rendering;

/**
 *
 * @author Diego
 */
import javax.swing.*;

import org.joml.Matrix4f;

import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_FLOAT;
import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL.GL_LEQUAL;
import static com.jogamp.opengl.GL.GL_STATIC_DRAW;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.GL2GL3.GL_LINE;
import static com.jogamp.opengl.GL4.*;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.*;
import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_FLOAT;
import static com.jogamp.opengl.GL.GL_LEQUAL;
import static com.jogamp.opengl.GL.GL_TEXTURE0;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_TRIANGLE_STRIP;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.math.Vec3f;
import com.jogamp.opengl.util.*;
import components.ControladorDeCotas;
import components.ControladorDeMuros;
import components.ControladorDePlanos;
import components.Plane;


import components.Muro;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.FloatBuffer;


public class ProjectionSpace implements GLEventListener, MouseListener, KeyListener {
		
	public GLCanvas myCanvas2;

        private int n = 360;
        
	private int renderingProgram;
	private int vao[] = new int[1];
	private int vbo[] = new int[5];
	
	
	private	float cameraX, cameraY, cameraZ;
	private	float cubeLocX, cubeLocY, cubeLocZ;
	
	
	// allocate variables used in display() function, so that they won’t need to be allocated during rendering
	private FloatBuffer vals = Buffers.newDirectFloatBuffer(16);  // utility buffer for transferring matrices
	private Matrix4f pMat = new Matrix4f(); 
	private Matrix4f vMat = new Matrix4f(); 
	private Matrix4f mMat = new Matrix4f(); 
	private Matrix4f mvMat = new Matrix4f(); 
	private int mvLoc, pLoc, oLoc;
	private	float aspect;
    
	private int counter = 0;
	
	private float x1 = 0;
	private float x2 = 0;
	
	private float y1 = 0;
	private float y2 = 0;

        
        private int brickTexture;
        private int checkerBoardTexture;
	
    public ProjectionSpace(){
    
        myCanvas2 = new GLCanvas();
        myCanvas2.addGLEventListener(this);
        myCanvas2.addMouseListener(this);
        myCanvas2.addKeyListener(this);
        myCanvas2.setBounds(0, 0, 600, 600);
           
    }
    
    
    public void display(GLAutoDrawable drawable){
        GL4 gl = (GL4)GLContext.getCurrentGL();
        gl.glClear(GL_DEPTH_BUFFER_BIT);
        gl.glClear(GL_COLOR_BUFFER_BIT);
        gl.glUseProgram(renderingProgram);
        //gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        
        mvLoc = gl.glGetUniformLocation(renderingProgram, "mv_matrix");
        pLoc = gl.glGetUniformLocation(renderingProgram, "p_matrix");
        oLoc = gl.glGetUniformLocation(renderingProgram, "osnap");
        
       	aspect	= (float) myCanvas2.getWidth()/(float)myCanvas2.getHeight();
       	pMat.setPerspective((float) Math.toRadians(60.0f), aspect, 0.1f, 1000.0f);
        gl.glUniformMatrix4fv(pLoc, 1, false, pMat.get(vals));
     
      	mvMat.identity();
      	mvMat.mul(vMat);
      	mvMat.mul(mMat);
        gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
        gl.glUniform1i(oLoc, 0);
        
      	ControladorDePlanos.drawPlanes(gl, vbo, checkerBoardTexture);
        
        ControladorDeMuros.dibujarMuros(gl, vbo, brickTexture);
        ControladorDeCotas.dibujarCotas(gl, vbo, checkerBoardTexture, oLoc);
    }
    
    public void init(GLAutoDrawable drawable){
        GL4 gl = (GL4) GLContext.getCurrentGL();
    	renderingProgram = mampos.Utils.createShaderProgram("shaders/projectionVertShader.glsl", "shaders/projectionFragShader.glsl");
        brickTexture = mampos.Utils.loadTexture("textures/brick/Poliigon_BrickReclaimedRunning_7787_BaseColor.jpg");
        checkerBoardTexture = mampos.Utils.loadTexture("textures/ground/GroundWoodChips001_COL_2K.jpg");
        
        gl.glGenVertexArrays(vao.length,  vao, 0);
    	gl.glBindVertexArray(vao[0]);
        gl.glGenBuffers(vbo.length, vbo, 0);
        
    	cameraX	= 0.0f;	cameraY	= 0.0f;	cameraZ	= 0.0f;
    	
        vMat.translation(-cameraX, -cameraY, -cameraZ);
        Vec3f eye = new Vec3f(0.0f,-20.0f,0.0f);
        Vec3f center = new Vec3f(1.0f,0.0f,0.0f);
        Vec3f up = new Vec3f(0.0f,0.0f,1.0f);
        vMat.lookAt(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z());
    }
 
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height){

    }
    public void dispose(GLAutoDrawable drawable){
    	
    }

    @Override
    public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_R) {     
            float largo = 0.0f;
            for(int i = 0; i < components.Objects.walls.size(); i++){
                largo = largo + components.Objects.walls.get(i).largo;
            }
            System.out.println("Tienes " + largo + " metros de muro.");
        }
        if(e.getKeyCode() == KeyEvent.VK_D) {
            System.out.println("moverse a la derecha en perspectiva");
            vMat.translateLocal(-1.0f, 0.0f, 0.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_A) {
            System.out.println("moverse a la izquierda en perspectiva");
            vMat.translateLocal(1.0f, 0.0f, 0.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_V) {
            System.out.println("moverse hacia arriba en perspectiva");
            vMat.translate(0.0f, 1.0f, 0.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_B) {
            System.out.println("moverse hacia abajo en perspectiva");
            vMat.translate(0.0f, -1.0f, 0.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_W) {
            System.out.println("moverse hacia adelante en perspectiva");
            vMat.translate(0.0f, 0.0f, -1.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_S) {
            System.out.println("moverse hacia atras en perspectiva");
            vMat.translate(0.0f, 0.0f, 1.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_I) {
            System.out.println("moverse hacia adelante en perspectiva");
            vMat.rotate((float) (Math.PI/n),1.0f,0.0f,0.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_K) {
            System.out.println("moverse hacia atras en perspectiva");
            vMat.rotate((float) -(Math.PI/n),1.0f,0.0f,0.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_J) {
            System.out.println("moverse hacia adelante en perspectiva");
            vMat.rotate((float) (Math.PI/n),0.0f,0.0f,1.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_L) {
            System.out.println("moverse hacia atras en perspectiva");
            vMat.rotate((float) -(Math.PI/n),0.0f,0.0f,1.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_N) {
            System.out.println("moverse hacia adelante en perspectiva");
            vMat.rotate((float) (Math.PI/n),0.0f,1.0f,0.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_M) {
            System.out.println("moverse hacia atras en perspectiva");
            vMat.rotate((float) -(Math.PI/n),0.0f,1.0f,0.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_D) {
            System.out.println("moverse a la derecha en perspectiva");
            vMat.translate(-1.0f, 0.0f, 0.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_A) {
            System.out.println("moverse a la izquierda en perspectiva");
            vMat.translate(1.0f, 0.0f, 0.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_V) {
            System.out.println("moverse hacia arriba en perspectiva");
            vMat.translate(0.0f, 1.0f, 0.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_B) {
            System.out.println("moverse hacia abajo en perspectiva");
            vMat.translate(0.0f, -1.0f, 0.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_W) {
            System.out.println("moverse hacia adelante en perspectiva");
            vMat.translate(0.0f, 0.0f, -1.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_S) {
            System.out.println("moverse hacia atras en perspectiva");
            vMat.translate(0.0f, 0.0f, 1.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_I) {
            System.out.println("moverse hacia adelante en perspectiva");
            vMat.rotate((float) (Math.PI/n),1.0f,0.0f,0.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_K) {
            System.out.println("moverse hacia atras en perspectiva");
            vMat.rotate((float) -(Math.PI/n),1.0f,0.0f,0.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_J) {
            System.out.println("moverse hacia adelante en perspectiva");
            vMat.rotate((float) (Math.PI/n),0.0f,0.0f,1.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_L) {
            System.out.println("moverse hacia atras en perspectiva");
            vMat.rotate((float) -(Math.PI/n),0.0f,0.0f,1.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_N) {
            System.out.println("moverse hacia adelante en perspectiva");
            vMat.rotate((float) (Math.PI/n),0.0f,1.0f,0.0f);
        }
        if(e.getKeyCode() == KeyEvent.VK_M) {
            System.out.println("moverse hacia atras en perspectiva");
            vMat.rotate((float) -(Math.PI/n),0.0f,1.0f,0.0f);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub

    }

    

    
    
}