package components;

import static components.ControladorDeMuros.crearPorClicks;
import static components.ControladorDeMuros.crearPorDistanciaYClick;
import static components.ControladorDeMuros.distancia;
import static components.ControladorDeMuros.isChain;
import static components.ControladorDeMuros.isExterior;
import static components.ControladorDeMuros.isFlipped;
import static components.ControladorDeMuros.isOrtho;
import frames.MainFrame;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import org.joml.Matrix4f;
import rendering.Controlador;
import rendering.Planta;

//@author diego
public abstract class ControladorDeEscena extends Controlador{
    
    public static float left, right, top, bottom;
    public static boolean drawSnap = false;
    public static float worldX, worldY, worldZ = 0;
    public static float xScreen, yScreen, zScreen = 0;
   
    public static Herramienta usandoHerramientaMuro = new Herramienta();
    public static Herramienta usandoHerramientaLinea = new Herramienta();
    
    public static int contadorDeClicks = 0;
    
    public static void setContadorDeClicks(int cantidad){contadorDeClicks = cantidad;}
    
    public static void changeToolState(boolean tool, boolean newState){
        
    }
    
    public static void setDimension(float left, float right, float top, float bottom){
        ControladorDeEscena.left = left;
        ControladorDeEscena.right = right;
        ControladorDeEscena.top = top;
        ControladorDeEscena.bottom = bottom;
    }
    
    public static void moverMouseEnPlanta(Planta planta, MouseEvent e){     
        calcularCoordenadasGlobalesEnPlanta(planta, e);   
        ControladorDeMuros.comprobarAnclaje();
        if(usandoHerramientaMuro.isUsed()) ControladorDeMuros.dibujarMuroTemporal();
    }
    
    public static void scrollMouseEnPlanta(Planta planta, MouseWheelEvent e){     
        System.out.println(e);
        if(e.getWheelRotation() == 1) { // System.out.println("Zoom");
            ControladorDeEscena.left -= 0.1f;
            ControladorDeEscena.right += 0.1f;
            ControladorDeEscena.top += 0.1f;
            ControladorDeEscena.bottom -= 0.1f;
            ControladorDeEscena.setDimension(ControladorDeEscena.left, ControladorDeEscena.right, ControladorDeEscena.top, ControladorDeEscena.bottom);
            planta.setDimension(ControladorDeEscena.left, ControladorDeEscena.right, ControladorDeEscena.top, ControladorDeEscena.bottom);
        }
        if(e.getWheelRotation() == -1) { // System.out.println("Zoom");         
            ControladorDeEscena.left += 0.1f;
            ControladorDeEscena.right -= 0.1f;
            ControladorDeEscena.top -= 0.1f;
            ControladorDeEscena.bottom += 0.1f;
            ControladorDeEscena.setDimension(ControladorDeEscena.left, ControladorDeEscena.right, ControladorDeEscena.top, ControladorDeEscena.bottom);
            planta.setDimension(ControladorDeEscena.left, ControladorDeEscena.right, ControladorDeEscena.top, ControladorDeEscena.bottom);
        }
    }
    
    public static void calcularCoordenadasGlobalesEnPlanta(Planta planta, MouseEvent e){
        xScreen = e.getX();
        yScreen = e.getY();      
        float perUnitX = (ControladorDeEscena.right - ControladorDeEscena.left)/2;
        float screenX = e.getX();
        float canvasX = planta.myCanvas.getWidth();
        float screenXCoordinates = ((2f * screenX/canvasX) - 1f);
        worldX = (screenXCoordinates * perUnitX) - (planta.cameraCurrentX);

        float perUnitY = (ControladorDeEscena.top - ControladorDeEscena.bottom)/2;
        float screenY = e.getY();
        float canvasY = planta.myCanvas.getHeight();
        float screenYCoordinates = -((2f * screenY/canvasY) - 1f); 
        worldY = (screenYCoordinates * perUnitY) + (planta.cameraCurrentY); 
    }
    
    public static void subirNivel(){
        int indexOfCurrentStory = components.Objects.stories.indexOf(components.Objects.currentStory);
        if(indexOfCurrentStory == 0){
            components.Objects.currentStory = components.Objects.stories.get(components.Objects.stories.size() - 1);
        } else {
            components.Objects.currentStory = components.Objects.stories.get(indexOfCurrentStory - 1);
        }
    }
    public  static void bajarNivel(){
        int indexOfCurrentStory = components.Objects.stories.indexOf(components.Objects.currentStory);
        if(indexOfCurrentStory == components.Objects.stories.size() - 1){
            components.Objects.currentStory = components.Objects.stories.get(0);
        } else {
            components.Objects.currentStory = components.Objects.stories.get(indexOfCurrentStory + 1);
        }
    }
    
    public static void controlesEnPlanta(Planta planta, KeyEvent e){
        Matrix4f vMat = planta.vMat;
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            usandoHerramientaLinea.setEstado(false);
            usandoHerramientaMuro.setEstado(false);
        }
        if(e.getKeyCode() == KeyEvent.VK_O) isOrtho = !isOrtho;
        if(e.getKeyCode() == KeyEvent.VK_N) mampos.Command.openStoryEditor();
        if(e.getKeyCode() == KeyEvent.VK_UP) subirNivel();
        if(e.getKeyCode() == KeyEvent.VK_DOWN) bajarNivel();
        
        if(e.getKeyCode() == KeyEvent.VK_E) isExterior = !isExterior;
        if(e.getKeyCode() == KeyEvent.VK_F) isFlipped = !isFlipped;
        if(e.getKeyCode() == KeyEvent.VK_C) isChain = !isChain;
        if(e.getKeyCode() == KeyEvent.VK_M) usandoHerramientaMuro.setEstado(true);
        if(e.getKeyCode() == KeyEvent.VK_L) usandoHerramientaLinea.setEstado(true);
        if(e.getKeyCode() == KeyEvent.VK_W) vMat.translate(0.0f, 0.1f, 0.0f); // System.out.println("moverse hacia adelante en perspectiva");
        if(e.getKeyCode() == KeyEvent.VK_S) vMat.translate(0.0f, -0.1f, 0.0f); // System.out.println("moverse hacia atras en perspectiva");   
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
    
}