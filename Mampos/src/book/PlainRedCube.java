/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package book;

/**
 *
 * @author Diego
 */


import org.joml.Matrix4f;


import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_FLOAT;
import static com.jogamp.opengl.GL.GL_LEQUAL;
import static com.jogamp.opengl.GL.GL_STATIC_DRAW;
import static com.jogamp.opengl.GL4.*;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.*;
import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_CCW;
import static com.jogamp.opengl.GL.GL_CULL_FACE;
import static com.jogamp.opengl.GL.GL_CW;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_FLOAT;
import static com.jogamp.opengl.GL.GL_LEQUAL;
import static com.jogamp.opengl.GL.GL_TEXTURE0;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.math.Vec3f;



import components.Muro;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.FloatBuffer;
import org.joml.Matrix4fStack;


public class PlainRedCube implements GLEventListener, KeyListener {
		
	public GLCanvas myCanvas;
	
	private int renderingProgram;
	private final int vao[] = new int[1]; // Vertexs? No
	private final int vbo[] = new int[3];
	
	private	float cameraX, cameraY, cameraZ;
	private	float cubeLocX, cubeLocY, cubeLocZ;
        private	float pyrLocX, pyrLocY, pyrLocZ;
        
        private float left, right, top, bottom;
        
        private double startTime;
        private double currentTime;
        private double tf;
        
        private int brickTexture;
	
	
	// allocate variables used in display() function, so that they won’t need to be allocated during rendering
	private final FloatBuffer vals = Buffers.newDirectFloatBuffer(16);  // utility buffer for transferring matrices
	private final Matrix4f pMat = new Matrix4f(); 
	private final Matrix4f vMat = new Matrix4f(); 
	private final Matrix4f mMat = new Matrix4f(); 
	private final Matrix4f mvMat = new Matrix4f(); 
        private final Matrix4fStack mvStack = new Matrix4fStack(5);
	private int mvLoc, pLoc, tfLoc;
	private float aspect;
	
	
    public PlainRedCube(){
    	
        myCanvas = new GLCanvas();
        myCanvas.addGLEventListener(this);
        myCanvas.addKeyListener(this);
        myCanvas.setBounds(0, 0, 600, 600);
      
    }
    
    @Override
    public void display(GLAutoDrawable drawable){
        GL4 gl = (GL4)GLContext.getCurrentGL();
        gl.glClear(GL_DEPTH_BUFFER_BIT);
        gl.glClear(GL_COLOR_BUFFER_BIT);
        gl.glEnable(GL_CULL_FACE);
        gl.glClearColor(0.0f,0.0f,0.0f,1.0f);
        gl.glUseProgram(renderingProgram);
        
        //gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        
        currentTime = System.currentTimeMillis() - startTime;
        tf = currentTime/1000.0;
        // use system time to generate slowly-increasing sequence of floating-point values
    
        // would all be declared of type double.
     
        // get references to the uniform variables for the MV and projection matrices
        mvLoc = gl.glGetUniformLocation(renderingProgram, "mv_matrix");
        pLoc = gl.glGetUniformLocation(renderingProgram, "p_matrix");
        
        // build perspective matrix. This one has fovy=60, aspect ratio matches the screen window.
        // Values for near and far clipping planes can vary as discussed in Section 4.
        
        
        // build view matrix, model matrix, and model-view matrix
      	mvStack.pushMatrix();
        mvStack.translate(-cameraX, -cameraY, -cameraZ);
        
        //Sun
        mvStack.pushMatrix();
        mvStack.translate(0.0f,0.0f,0.0f);
        mvStack.pushMatrix();
        mvStack.rotate((float)tf/1000,1.0f,0.0f,0.0f);
        gl.glUniformMatrix4fv(mvLoc, 1, false, mvStack.get(vals));
        // associate VBO with the corresponding vertex attribute in the vertex shader
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
        gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(0);
        gl.glEnable(GL_DEPTH_TEST);
        
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[2]);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0,0);
        gl.glEnableVertexAttribArray(1);
        
        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, brickTexture);
        
        gl.glDepthFunc(GL_LEQUAL);
        gl.glFrontFace(GL_CCW);
        gl.glDrawArrays(GL_TRIANGLES, 0, 18);
        mvStack.popMatrix();
        
        //Planet
        mvStack.pushMatrix();
        mvStack.translate((float)Math.sin(tf)*4.0f,0.0f,(float)Math.cos(tf)*4.0f);
        mvStack.pushMatrix();
        mvStack.rotate((float)tf,0.0f,1.0f,0.0f);
        gl.glUniformMatrix4fv(mvLoc, 1, false, mvStack.get(vals));
        // associate VBO with the corresponding vertex attribute in the vertex shader
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
        gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(0);
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LEQUAL);
        gl.glFrontFace(GL_CW);
        gl.glDrawArrays(GL_TRIANGLES, 0, 36);
        mvStack.popMatrix();


        //Moon
        mvStack.pushMatrix();
        mvStack.translate(0.0f,(float)Math.sin(tf)*2.0f,(float)Math.cos(tf)*2.0f);
        mvStack.rotate((float)tf,0.0f,0.0f,1.0f);
        mvStack.scale(0.25f,0.25f,0.25f);
        gl.glUniformMatrix4fv(mvLoc, 1, false, mvStack.get(vals));
        // associate VBO with the corresponding vertex attribute in the vertex shader
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
        gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(0);
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LEQUAL);
        gl.glFrontFace(GL_CW);
        gl.glDrawArrays(GL_TRIANGLES, 0, 36);
        mvStack.popMatrix();    
        mvStack.popMatrix();  
        mvStack.popMatrix();  
        mvStack.popMatrix();  



        // copy perspective and MV matrices to corresponding uniform variables
        gl.glUniformMatrix4fv(pLoc, 1, false, pMat.get(vals));

        

        // adjust OpenGL settings and draw model
        
        
        
     
             
        
    }
    
    @Override
    public void init(GLAutoDrawable drawable){
    	renderingProgram = mampos.Utils.createShaderProgram("shaders/book/plainRedCubeVertShader.glsl", "shaders/book/plainRedCubeFragShader.glsl");
    	
        brickTexture = mampos.Utils.loadTexture("textures/brick/Poliigon_BrickReclaimedRunning_7787_BaseColor.jpg");

        setupVertices();
        
        
        startTime = System.currentTimeMillis();
    	cameraX	= 0.0f;	cameraY	= 0.0f;	cameraZ	= 1.0f;
    	cubeLocX = 0.0f; cubeLocY = -2.0f; cubeLocZ = 0.0f;	// shifted down the Y-axis to reveal perspective 
        pyrLocX = 3.0f; pyrLocY = 2.0f; pyrLocZ = -2.0f;
        
        aspect	= (float) myCanvas.getWidth() / (float)	myCanvas.getHeight();
	pMat.setPerspective((float) Math.toRadians(60.0f), aspect, 0.1f, 2000.0f);
    }
    
    public void setupVertices() {
    	GL4 gl = (GL4)GLContext.getCurrentGL();
        float[] cubePositions = {
            -1.0f,  1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f, 1.0f,  1.0f, -1.0f, -1.0f,  1.0f, -1.0f,
            1.0f, -1.0f, -1.0f, 1.0f, -1.0f,  1.0f, 1.0f,  1.0f, -1.0f,
            1.0f, -1.0f,  1.0f, 1.0f,  1.0f,  1.0f, 1.0f,  1.0f, -1.0f,
            1.0f, -1.0f,  1.0f, -1.0f, -1.0f,  1.0f, 1.0f,  1.0f,  1.0f,
            -1.0f, -1.0f,  1.0f, -1.0f,  1.0f,  1.0f, 1.0f,  1.0f,  1.0f,
            -1.0f, -1.0f,  1.0f, -1.0f, -1.0f, -1.0f, -1.0f,  1.0f,  1.0f,
            -1.0f, -1.0f, -1.0f, -1.0f,  1.0f, -1.0f, -1.0f,  1.0f,  1.0f,
            -1.0f, -1.0f,  1.0f,  1.0f, -1.0f,  1.0f,  1.0f, -1.0f, -1.0f,
             1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f,  1.0f,
            -1.0f,  1.0f, -1.0f, 1.0f,  1.0f, -1.0f, 1.0f,  1.0f,  1.0f,
            1.0f,  1.0f,  1.0f, -1.0f,  1.0f,  1.0f, -1.0f,  1.0f, -1.0f
        };
        
        float[] pyramidPositions = {
            -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 
            1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 0.0f, 1.0f, 0.0f, 
            1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 0.0f, 1.0f, 0.0f, 
            -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 
            -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 
            1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f 
        };
        
        float[ ] pyrTextureCoordinates = {
            0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f, // top and right faces
            0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f, // back and left faces
            0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f };

    	gl.glGenVertexArrays(vao.length,  vao, 0);
    	gl.glBindVertexArray(vao[0]);
    	gl.glGenBuffers(vbo.length, vbo, 0);
        
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
    	FloatBuffer cubeBuf = Buffers.newDirectFloatBuffer(cubePositions);
    	gl.glBufferData(GL_ARRAY_BUFFER, cubeBuf.limit()*4, cubeBuf, GL_STATIC_DRAW);
    	
    	gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
    	FloatBuffer pyrBuf = Buffers.newDirectFloatBuffer(pyramidPositions);
    	gl.glBufferData(GL_ARRAY_BUFFER, pyrBuf.limit()*4, pyrBuf, GL_STATIC_DRAW); 	
    
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[2]);
        FloatBuffer pTexBuf = Buffers.newDirectFloatBuffer(pyrTextureCoordinates);
        gl.glBufferData(GL_ARRAY_BUFFER, pTexBuf.limit()*4, pTexBuf, GL_STATIC_DRAW);
    }


    
    
    
 
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height){
        GL4 gl = (GL4) GLContext.getCurrentGL();
        aspect = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        pMat.setPerspective((float)Math.toRadians(60.0f), aspect, 0.1f, 1000.0f);
    }
    public void dispose(GLAutoDrawable drawable){
    	
    }

    
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
                System.out.println("Dibuja tu muro prro");
		if(e.getKeyCode() == 77) {
			System.out.println("Dibuja tu muro prro");
		
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
                        vMat.rotate((float) (Math.PI/12),1.0f,0.0f,0.0f);
		}
                if(e.getKeyCode() == KeyEvent.VK_K) {
			System.out.println("moverse hacia atras en perspectiva");
                        vMat.rotate((float) -(Math.PI/12),1.0f,0.0f,0.0f);
		}
                if(e.getKeyCode() == KeyEvent.VK_J) {
			System.out.println("moverse hacia adelante en perspectiva");
                        vMat.rotate((float) (Math.PI/12),0.0f,0.0f,1.0f);
		}
                if(e.getKeyCode() == KeyEvent.VK_L) {
			System.out.println("moverse hacia atras en perspectiva");
                        vMat.rotate((float) -(Math.PI/12),0.0f,0.0f,1.0f);
		}
                if(e.getKeyCode() == KeyEvent.VK_N) {
			System.out.println("moverse hacia adelante en perspectiva");
                        vMat.rotate((float) (Math.PI/12),0.0f,1.0f,0.0f);
		}
                if(e.getKeyCode() == KeyEvent.VK_M) {
			System.out.println("moverse hacia atras en perspectiva");
                        vMat.rotate((float) -(Math.PI/12),0.0f,1.0f,0.0f);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
    
    
}