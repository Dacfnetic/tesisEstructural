
package frames;

import bibliothek.gui.dock.common.CControl;
import bibliothek.gui.dock.common.CLocation;
import bibliothek.gui.dock.common.DefaultSingleCDockable;
import bibliothek.gui.dock.common.SingleCDockable;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import components.ControladorDeEscena;
import components.Objects;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import rendering.Planta;


public class Interfaz {
    
    public SingleCDockable green;
    
    private GLCanvas myCanvas;
    private GLCanvas myCanvas2;
    private Planta floorSpace;
    
    public void desplegar(){
        
        floorSpace = new Planta();
        floorSpace.setDimension(-10f, 10f, 10f, -10f);
        ControladorDeEscena.setDimension(-10f, 10f, 10f, -10f);
        
        myCanvas = floorSpace.myCanvas;
        
        Animator animtr = new Animator(myCanvas);
        animtr.start();
        
        components.Objects.currentStory = Objects.stories.get(0);
        
        //currentLevel.setText("Nivel: " + components.Objects.currentStory.name);
        
        JFrame frame = new JFrame();
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        CControl control = new CControl(frame);
        
        frame.setLayout(new GridLayout(1,1));
        frame.add( control.getContentArea());
        
        SingleCDockable red = create("Red", Color.RED);
        green = create("Green", Color.GREEN);
        
        control.addDockable(red);
        green.setLocation( CLocation.base().normalEast(0.5));
        control.addDockable(green);
        
        red.setVisible(true);
        
        
        
        
        
        
        
        
        
        
       
        
     
        green.setVisible(true);
        
        frame.setVisible(true);
        
    }
    
    public SingleCDockable create(String title, Color color){
        JPanel background = new JPanel();
        background.setOpaque(true);
        background.setBackground(color);
        background.add(myCanvas);
        
        return new DefaultSingleCDockable(title, title, background);
    }
    
   
    
    
}
