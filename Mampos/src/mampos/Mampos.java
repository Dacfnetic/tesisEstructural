/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mampos;

import components.ColumnaRectangular;
import components.ControladorDePlanos;
import components.Objects;
import components.Plane;
import frames.MainFrame;
import java.awt.EventQueue;
import rendering.Loader;
import rendering.Renderer;
import units.Longitud;
/**
 *
 * @author Diego
 */
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
                MainFrame cuadro = new frames.MainFrame();
                //cuadro.canvasCoords.setText("Hola");
                cuadro.setVisible(true);
                
                ColumnaRectangular C1 = new ColumnaRectangular();
                C1.setParametros(35f, 35f, 450f, 0.65f);
                C1.imprimir();
                
                System.out.println(Longitud.convertirAPulgadas(2.54f, "cm") + " in");
             
            }
        };	
        EventQueue.invokeLater(runner);
    }
	
}
