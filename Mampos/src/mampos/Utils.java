/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mampos;

/**
 *
 * @author Diego
 */

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import static com.jogamp.opengl.GL4.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import components.Story;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

public abstract class Utils {
    
  

    
    public static int loadTexture(String textureFileName){ 
        
        com.jogamp.opengl.util.texture.Texture tex = null;
        
        try { 
            tex = com.jogamp.opengl.util.texture.TextureIO.newTexture(new File(textureFileName), false);
        } catch (GLException | IOException e){
            e.printStackTrace();
        }
        
        return tex.getTextureObject();
    }

    public static void printShaderLog(int shader) {
    	GL4 gl = (GL4)GLContext.getCurrentGL();
    	int[] len = new int[1];
    	int[] chWrittn = new int[1];
    	byte[] log = null;
    	
    	// determine the length of the shader compilation log
    	gl.glGetShaderiv(shader, GL_INFO_LOG_LENGTH, len, 0);
    	if(len[0] > 0) {
    		log = new byte[len[0]];
    		gl.glGetShaderInfoLog(shader, len[0], chWrittn, 0, log, 0);
    		System.out.println("Shader Info Log: ");
    		for(int i = 0; i < log.length; i++) {
    			System.out.print((char) log[i]);
    		}
    	}
    }
    
    public static void printProgramLog(int prog) {
    	GL4 gl = (GL4)GLContext.getCurrentGL();
    	int[] len = new int[1];
    	int[] chWrittn = new int[1];
    	byte[] log = null;
    	
    	// determine the length of the program linking log
    	gl.glGetProgramiv(prog , GL_INFO_LOG_LENGTH, len, 0);
    	if(len[0] > 0) {
    		log = new byte[len[0]];
    		gl.glGetProgramInfoLog(prog, len[0], chWrittn, 0, log, 0);
    		System.out.println("Program Info Log: ");
    		for(int i = 0; i < log.length; i++) {
    			System.out.print((char) log[i]);
    		}
    	}
    }
    
    public static boolean checkOpenGLError() {
    	GL4 gl = (GL4) GLContext.getCurrentGL();
    	boolean foundError = false;
    	GLU glu = new GLU();
    	int glErr = gl.glGetError();
    	while(glErr != GL_NO_ERROR) {
    		System.err.println("glError: " + glu.gluErrorString(glErr));
    		foundError = true;
    		glErr = gl.glGetError();
    	}
    	return foundError;
    }

    public static String[] readShaderSource(String filename) {
    	Vector<String> lines = new Vector<String>();
    	Scanner sc;
    	String[] program;
    	try {
    		sc = new Scanner(new File(filename));
    		while (sc.hasNext()) {
    			lines.addElement(sc.nextLine());
    		}
    		program = new String[lines.size()];
    		for(int i = 0; i < lines.size(); i++) {
    			program[i] = (String) lines.elementAt(i) + "\n";
    		}
    		sc.close();
    	} catch (IOException e) {
    		System.err.println("IOException reading file: " + e);
    		return null;
    	}
    	return program;
    }
    
    public static int createShaderProgram(String v, String f) {
    	GL4 gl = (GL4)GLContext.getCurrentGL();
    	
    	// arrays to collect GLSL compilation status values.
    	// note: one-element arrays are used because the associated JOGL calls require arrays.
    	int[] vertCompiled = new int[1];
    	int[] fragCompiled = new int[1];
    	int[] linked = new int[1];
    	
    	// catch errors while compiling shaders
    	
    	String vshaderSource[] = readShaderSource(v);
    	String fshaderSource[] = readShaderSource(f);
    	 	
    	int vShader = gl.glCreateShader(GL_VERTEX_SHADER); // Is created
    	gl.glShaderSource(vShader, vshaderSource.length, vshaderSource, null, 0); // Is configured, 3 is the count of lines of source code aaaaah
    	gl.glCompileShader(vShader); // Is compiled
    	checkOpenGLError();
    	gl.glGetShaderiv(vShader, GL_COMPILE_STATUS, vertCompiled, 0);
    	if(vertCompiled[0] != 1) {
    		System.out.println("vertex compilation failed.");
    		printShaderLog(vShader);
    	}
     	
    	
    	int fShader = gl.glCreateShader(GL_FRAGMENT_SHADER); // Is created
    	gl.glShaderSource(fShader, fshaderSource.length, fshaderSource, null, 0); // Is configured, 4 is the count of lines of source code
    	gl.glCompileShader(fShader);
    	checkOpenGLError();
    	gl.glGetShaderiv(fShader, GL_COMPILE_STATUS, fragCompiled, 0);
    	if(fragCompiled[0] != 1) {
    		System.out.println("fragment compilation failed.");
    		printShaderLog(fShader);
    	}
    	
    	if((vertCompiled[0]!=1) || (fragCompiled)[0]!=1) {
    		System.out.println("\nCompilation error; return-flags.");
    		System.out.println("vertCompiled = " + vertCompiled[0] + "; fragCompiled = " + fragCompiled[0]);
    	}
    	
    	int vfProgram = gl.glCreateProgram();
    	gl.glAttachShader(vfProgram, vShader);
    	gl.glAttachShader(vfProgram, fShader);
    	gl.glLinkProgram(vfProgram);
    	checkOpenGLError();
    	gl.glGetProgramiv(vfProgram, GL_LINK_STATUS, linked, 0);
    	if(linked[0]!=1) {
    		System.out.println("linking failed.");
    		printProgramLog(vfProgram);
    	}
    	
    	gl.glDeleteShader(vShader);
    	gl.glDeleteShader(fShader);
    	return vfProgram;
    }
     
    public static void setFrameInMiddle(JFrame frame, int height) {
    	Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
    	Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    	int taskBarHeight = scrnSize.height - winSize.height;
    	int width = (int) Math.round((((1f+Math.sqrt(5))/(2f))*(height)));
    	int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    	int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    	int x = (int) ((1f/2f)*(ancho-width));
    	int y = (int) ((1f/2f)*(alto-height)) -(taskBarHeight/2);
    	frame.setLocation(x, y);
    	frame.setSize(width, height);	
    }
    
    public static List<?> convertObjectToList(Object obj) {
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Object[])obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>)obj);
        }
        return list;
    }

    
    public static Object[][] convertVectorToObject(Vector vec) {
        
        
        Object elemento = vec.get(0);
        Object[] nuevo = convertObjectToList(elemento).toArray();
   
        
        Object[][] data = new Object[vec.size()][nuevo.length]; 
        
        for(int i = 0; i < vec.size(); i++){  
            elemento = vec.get(i);
            nuevo = convertObjectToList(elemento).toArray();
            data[i] = nuevo;
            /*for(int j = 0; j < nuevo.length; j++){
                data[i][j] = nuevo[j];
            }*/
        }   
     
        return data;
    }
    
    public static void saveToGson(){
        Gson gson = new Gson();
        String json = gson.toJson(components.Objects.stories);
        System.out.println(json);
        try {
            File myObj = new File("configurations/stories.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                try {
                    FileWriter myWriter = new FileWriter("configurations/stories.txt");
                    myWriter.write(json);
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                  System.out.println("An error occurred.");
                  e.printStackTrace();
                }
            } else {
                System.out.println("File already exists.");
                myObj.delete();
                myObj = new File("configurations/stories.txt");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                    try {
                        FileWriter myWriter = new FileWriter("configurations/stories.txt");
                        myWriter.write(json);
                        myWriter.close();
                        System.out.println("Successfully wrote to the file.");
                    } catch (IOException e) {
                      System.out.println("An error occurred.");
                      e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
      
    }
    
    public static void loadToGson() throws IOException{
        Gson gson = new Gson();
        
        // variable declaration 
        int ch; 
        String json = "";
        // check if File exists or not 
        FileReader fr=null; 
        try
        { 
            fr = new FileReader("configurations/stories.txt"); 
        } 
        catch (FileNotFoundException fe) 
        { 
            System.out.println("File not found"); 
        } 
  
        // read from FileReader till the end of file 
        while (-1!=(ch=fr.read())) 
            json = json + (char)ch;
            System.out.print((char)ch); 
  
        // close the file 
        fr.close();
        
        
        
        System.out.print(json); 
        List<Story> niveles = gson.fromJson(json, components.Objects.stories.getClass());
        components.Objects.setStories(niveles);
    }
    
    
    public static void abrirPDF(String archivo, JFrame jf){
        try{
            SwingController ctrl = new SwingController();
            SwingViewBuilder vb = new SwingViewBuilder(ctrl);
            JPanel s = vb.buildViewerPanel();
            ComponentKeyBinding.install(ctrl,s);
            ctrl.getDocumentViewController().setAnnotationCallback(
                new org.icepdf.ri.common.MyAnnotationCallback(ctrl.getDocumentViewController())
            );
            ctrl.openDocument(archivo);
            jf.add(s);
        }catch (Exception e){
            
        }
    }
    
    
}
