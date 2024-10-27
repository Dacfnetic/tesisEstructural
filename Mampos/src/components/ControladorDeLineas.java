package components;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.math.Vec3f;
import dibujo.Linea;
import frames.MainFrame;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import rendering.Controlador;
import rendering.Planta;

//@author diego
public class ControladorDeLineas extends Controlador {
    
    public static List<Linea> lineas = new ArrayList<>();
    public static final Linea lineaTemporal = new Linea();
    public static List<Float> verticesDeLineas = new ArrayList<>();
 
    public static boolean isChain = false;
    public static boolean isOrtho = false;
    public static boolean isOsnap = true;
    public static boolean crearPorClicks = true;
    public static boolean crearPorDistanciaYClick = false;
    
    public static float x1, y1, z1;
    public static float x2, y2, z2;
    public static float distancia = 0;
    
    //TODO: ABSTRAER ESTE MÉTODO
    public static void clickEnPlanta(Planta planta, MouseEvent e, Herramienta herramienta, int clicks) throws CloneNotSupportedException{
        // Primero se comprueba si la herramienta está activa
        if(herramienta.isUsed()){
            // Si se da click en el botón derecho del mouse se hace lo siguiente
            if(e.getButton() == 3){
                // Los clicks vuelven a ser cero
                ControladorDeEscena.setContadorDeClicks(0);
                // La herramienta se desactiva
                herramienta.setEstado(false);
                // Se elimina lo que la herramienta estaba haciendo
                eliminarTemporal();
                return;
            }
            // Si es el primer click luego de la activación de la herramienta
            if(clicks == 0) {     
                // Se setean las coordenadas
                x1 = ControladorDeEscena.worldX;
                y1 = ControladorDeEscena.worldY;
                z1 = components.Objects.currentStory.lowerStoryHeight;
                lineaTemporal.setPunto1(x1, y1, z1);
                ControladorDeEscena.setContadorDeClicks(clicks + 1);
            }
            // Si ya no es el primer click luego de la activación de la herramienta.
            if(clicks > 0) {
                // Se setean las coordenadas
                x2 = ControladorDeEscena.worldX;
                y2 = ControladorDeEscena.worldY; 
                comprobarOrtogonalidad();
                
                Linea nuevaLinea = new Linea();
                nuevaLinea.setPunto1(x1, y1, z1);
                nuevaLinea.setPunto2(x2, y2, z2);
                nuevaLinea.setVertices(); 
                // Se agrega la nueva linea
                lineas.add(nuevaLinea);
                // Si la opción de cadena está activada
                if(isChain){
                    x1 = x2;
                    y1 = y2;
                    z1 = z2; 
                    ControladorDeEscena.setContadorDeClicks(clicks + 1);
                } else {  
                    // Los clicks vuelven a ser cero
                    ControladorDeEscena.setContadorDeClicks(0);
                    // Se elimina lo que la herramienta estaba haciendo
                    eliminarTemporal();
                
                }
                components.Objects.rellenarVertices();
        }
        
        }
    }
    //TODO: ABSTRAER ESTE MÉTODO
    public static void eliminarTemporal(){
        try {lineas.remove(lineaTemporal);} 
        catch (Exception er) {System.out.println(er);}
    }
    
    public static void comprobarOrtogonalidad(){    
        float deltaX = Math.abs(x2 - x1);
        float deltaY = Math.abs(y2 - y1);
        if(isOrtho){
            if(deltaX >= deltaY){
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
   
    /*
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
    }*/
}