
package mampos;

import components.ColumnaRectangular;
import components.ControladorDeLosas;
import components.ControladorDePlanos;
import components.Losa;
import components.Objects;
import components.Plane;
import dibujo.Linea;
import frames.Interfaz;
import frames.MainFrame;
import java.awt.EventQueue;
import optimization.Optimizar;
import rendering.Loader;
import rendering.Renderer;
import units.Longitud;

public class Mampos {
       
    public static void main(String args[]) {
        Runnable runner = new Runnable() {
            @Override
            public void run() {
                

                // Crear el primer story
                components.Story primerNivel = new components.Story();
                primerNivel.setParams("1", "Primer nivel", "Estructural", 0.0f, 3.0f);
                Objects.addStory(primerNivel);
                Plane plano = new Plane();
                plano.setupPlanePositions();
                plano.setupCoordsPositions();
                ControladorDePlanos.planos.add(plano);
                
                Losa losa = new Losa();
                losa.setupLosaPositions();
                ControladorDeLosas.losas.add(losa);
                
                //MainFrame cuadro = new frames.MainFrame();
                //cuadro.setVisible(true);
                //Optimizar o = new Optimizar();
                Interfaz cuadro = new Interfaz();
                cuadro.desplegar();
              
              
                ColumnaRectangular C1 = new ColumnaRectangular();
                C1.setParametros(35f, 35f, 450f, 0.65f);
                C1.imprimir();
                
                System.out.println(Longitud.convertirAPulgadas(2.54f, "cm") + " in");
             
                Linea prueba = new Linea();
                prueba.setPunto1(0, 0, 0);
                prueba.setPunto2(5, 2, 3);
                prueba.setVectorDirector();
                prueba.imprimir();  
            }
        };	
        EventQueue.invokeLater(runner);
    }	
}