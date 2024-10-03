package components;

import java.awt.event.MouseEvent;
import rendering.Planta;

//@author diego
public abstract class ControladorDeEscena {
    
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