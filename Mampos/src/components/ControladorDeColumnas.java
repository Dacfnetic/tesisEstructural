package components;

import com.jogamp.opengl.math.Vec3f;
import frames.MainFrame;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import org.joml.Matrix4f;
import rendering.Controlador;
import rendering.Planta;

//@author diego
public class ControladorDeColumnas extends Controlador {
    
    public static List<Muro> muros = new ArrayList<>();
    public static final Muro temporal = new Muro();

    public static int contadorDeClicks = 0;
    public static boolean isChain = false;
    public static boolean isOrtho = false;
    public static boolean isOsnap = true;
    public static boolean isExterior = false;
    public static boolean isFlipped = false;
    public static boolean crearPorClicks = true;
    public static boolean crearPorDistanciaYClick = false;
    
    public static float x1, y1, z1;
    public static float x2, y2, z2;
    public static float distancia = 0;
      
    public static void clickEnPlanta(Planta planta, MouseEvent e){
        if(e.getButton() == 3){
            contadorDeClicks = 0;
            eliminarTemporal();
            return;
        }
        if(contadorDeClicks == 0) {           
            x1 = ControladorDeEscena.worldX;
            y1 = ControladorDeEscena.worldZ; 
            z1 = components.Objects.currentStory.lowerStoryHeight;           
        }
        
        if(contadorDeClicks > 0) {
            x2 = ControladorDeEscena.worldX;
            y2 = ControladorDeEscena.worldZ; 
            comprobarOrtogonalidad();
            Muro nuevoMuro = new Muro();
            nuevoMuro.setPoints(new Vec3f(x1, z1, y1), new Vec3f(x2, z2, y2));
            setearVerticesDeMuroSegunForma(nuevoMuro);
            muros.add(nuevoMuro);
            
            //Cota nuevaCota = new Cota();
            //nuevaCota.setearVertices(nuevoMuro);  
            //ControladorDeCotas.agregarCotaACotas(nuevaCota);
            if(isChain){
                x1 = nuevoMuro.point2.x();
                y1 = nuevoMuro.point2.z(); 
                z1 = components.Objects.currentStory.lowerStoryHeight;
                components.Objects.rellenarVertices();
            } else {
                components.Objects.rellenarVertices();
                contadorDeClicks = -1;
                eliminarTemporal();
            }
        }
        contadorDeClicks++;
    }
    
    public static void controlesEnPlanta(Planta planta, KeyEvent e){
        Matrix4f vMat = planta.vMat;
        if(e.getKeyCode() == KeyEvent.VK_O) isOrtho = !isOrtho;
        if(e.getKeyCode() == KeyEvent.VK_E) isExterior = !isExterior;
        if(e.getKeyCode() == KeyEvent.VK_F) isFlipped = !isFlipped;
        if(e.getKeyCode() == KeyEvent.VK_C) isChain = !isChain;
        if(e.getKeyCode() == KeyEvent.VK_M) MainFrame.popup();
        if(e.getKeyCode() == KeyEvent.VK_W) vMat.translate(0.0f, 0.0f, -0.1f); // System.out.println("moverse hacia adelante en perspectiva");
        if(e.getKeyCode() == KeyEvent.VK_S) vMat.translate(0.0f, 0.0f, 0.1f); // System.out.println("moverse hacia atras en perspectiva");   
        if(e.getKeyCode() == KeyEvent.VK_D) vMat.translate(-0.1f, 0.0f, 0.0f);  //System.out.println("moverse a la derecha en perspectiva");  
        if(e.getKeyCode() == KeyEvent.VK_A) vMat.translate(0.1f, 0.0f, 0.0f); //System.out.println("moverse a la izquierda en perspectiva");        
        if(e.getKeyCode() == KeyEvent.VK_Z) { // System.out.println("Zoom");
            ControladorDeEscena.left -= 0.1f;
            ControladorDeEscena.right += 0.1f;
            ControladorDeEscena.top += 0.1f;
            ControladorDeEscena.bottom -= 0.1f;
            ControladorDeEscena.setDimension(ControladorDeEscena.left, ControladorDeEscena.right, ControladorDeEscena.top, ControladorDeEscena.bottom);
            planta.setDimension(ControladorDeEscena.left, ControladorDeEscena.right, ControladorDeEscena.top, ControladorDeEscena.bottom);
        }
        if(e.getKeyCode() == KeyEvent.VK_X) { // System.out.println("Zoom");         
            ControladorDeEscena.left += 0.1f;
            ControladorDeEscena.right -= 0.1f;
            ControladorDeEscena.top -= 0.1f;
            ControladorDeEscena.bottom += 0.1f;
            ControladorDeEscena.setDimension(ControladorDeEscena.left, ControladorDeEscena.right, ControladorDeEscena.top, ControladorDeEscena.bottom);
            planta.setDimension(ControladorDeEscena.left, ControladorDeEscena.right, ControladorDeEscena.top, ControladorDeEscena.bottom);
        }
        char tecla = e.getKeyChar();
        if(Character.isDigit(tecla)){
            crearPorClicks = false;
            crearPorDistanciaYClick = true;
            distancia = Float.parseFloat(String.valueOf(tecla));
        }
    }
    
    public static void moverMuro(){}
     
    public static void eliminarTemporal(){
        try {muros.remove(temporal);} 
        catch (Exception er) {System.out.println(er);}
    }
    
    public static void comprobarOrtogonalidad(){    
        float deltaX = Math.abs(x2 - x1);
        float deltaZ = Math.abs(y2 - y1);
        if(isOrtho){
            if(deltaX >= deltaZ){
                y2 = y1;
            } else {
                x2 = x1;
            }  
        }
        z2 = components.Objects.currentStory.lowerStoryHeight;
    }

    public static void comprobarAnclaje(){
        if(isOsnap){
            //Necesito saber si estoy cerca de un vertice por el momento con 5 centímetros lo veo bien
            int contador = -1;
            float x = 0.0f;
            float z;
            for(int i = 0; i < vertices.size(); i++){
              
                contador++;
                if(contador > 2){
                    contador = 0;
                }
                if(contador == 0){
                    x = (float) vertices.get(i);  
                }
                if(contador == 2){
                    z = (float) vertices.get(i);
                    if(Math.abs(ControladorDeEscena.worldX-x)< 0.02 && Math.abs(ControladorDeEscena.worldZ-z)< 0.02){
                        ControladorDeEscena.worldX = x;
                        ControladorDeEscena.worldZ = z;
                        ControladorDeEscena.drawSnap = true;
                        return;
                    }else{
                        ControladorDeEscena.drawSnap = false;
                    }
                }              
            }            
        } 
    }
   
    public static void dibujarMuroTemporal(){
        if(contadorDeClicks > 0) {         
            eliminarTemporal();
            if(crearPorClicks){
                crearMuroPorClick();  
            }
            if(crearPorDistanciaYClick){
                crearMuroPorDistanciaYClick();
            }
            comprobarOrtogonalidad();
            temporal.setPoints(new Vec3f(x1, z1, y1), new Vec3f(x2, z2, y2));        
            setearVerticesDeMuroSegunForma(temporal);
            //ControladorDeCotas.dibujarCotaDeMuroTemporal(temporal);
            muros.add(temporal);
        }
    }
    
    private static void crearMuroPorClick() { 
        x2 = ControladorDeEscena.worldX;
        y2 = ControladorDeEscena.worldZ; 
    }
    
    private static void crearMuroPorDistanciaYClick() {
        float deltaX = ControladorDeEscena.worldX - x1;
        float deltaZ = ControladorDeEscena.worldZ - y1;
        float angle = (float) Math.abs(Math.atan(deltaZ/deltaX));
        float sinAngle = (float) Math.sin(angle);
        float cosAngle = (float) Math.cos(angle);

        if(deltaX >= 0 && deltaZ >= 0){
            x2 = x1 + distancia * cosAngle;
            y2 = y1 + distancia * sinAngle;
        }if(deltaX < 0 && deltaZ >= 0){
            x2 = x1 - distancia * cosAngle;
            y2 = y1 + distancia * sinAngle; 
        }if(deltaX < 0 && deltaZ < 0){
            x2 = x1 - distancia * cosAngle;
            y2 = y1 - distancia * sinAngle; 
        }if(deltaX >= 0 && deltaZ < 0){
            x2 = x1 + distancia * cosAngle;
            y2 = y1 - distancia * sinAngle; 
        } 
    }
    
    private static void crearMuroPorDistanciaYAngulo() {}
    
    public static void setearVerticesDeMuroSegunForma(Muro muro){
        if(isExterior){
            muro.setVertexlikeExteriorWall();
            if(isFlipped){
                muro.flipVertexlikeExteriorWall();
            }
        } else {
            muro.setVertexlikeMidWall();
        }
    }

}