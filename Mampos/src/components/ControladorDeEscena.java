package components;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import rendering.Controlador;
import rendering.Planta;

//@author diego
public abstract class ControladorDeEscena extends Controlador{
    
    public static float left, right, top, bottom;
    public static boolean drawSnap = false;
    public static float worldX, worldY, worldZ = 0;
    public static float xScreen, yScreen, zScreen = 0;
    
    public static void setDimension(float left, float right, float top, float bottom){
        ControladorDeEscena.left = left;
        ControladorDeEscena.right = right;
        ControladorDeEscena.top = top;
        ControladorDeEscena.bottom = bottom;
    }
    
    public static void moverMouseEnPlanta(Planta planta, MouseEvent e){     
        calcularCoordenadasGlobalesEnPlanta(planta, e);   
        ControladorDeMuros.comprobarAnclaje();
        ControladorDeMuros.dibujarMuroTemporal();
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

        float perUnitZ = (ControladorDeEscena.top - ControladorDeEscena.bottom)/2;
        float screenY = e.getY();
        float canvasY = planta.myCanvas.getHeight();
        float screenYCoordinates = -((2f * screenY/canvasY) - 1f); 
        worldZ = (screenYCoordinates * perUnitZ) + (planta.cameraCurrentY); 
    }
    
}