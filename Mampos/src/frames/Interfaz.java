
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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import rendering.Perfil;
import rendering.Planta;
import rendering.ProjectionSpace;

public class Interfaz implements ComponentListener{
    
    private GLCanvas myCanvas;
    private GLCanvas myCanvas2;
    private GLCanvas myCanvas3;
    private Planta floorSpace;
    
    public static JPanel verde = new JPanel();
    private JPanel rojo =  new JPanel();
    private JPanel azul =  new JPanel();
    
    public void desplegar(){
        
        floorSpace = new Planta();
        floorSpace.setDimension(-10f, 10f, 10f, -10f);
        ControladorDeEscena.setDimension(-10f, 10f, 10f, -10f);
        
        ProjectionSpace plainRedCube = new ProjectionSpace();	
        
        Perfil perfil = new Perfil();	
        
        myCanvas = floorSpace.myCanvas;
        myCanvas2 = plainRedCube.myCanvas2;
        myCanvas3 = perfil.myCanvas;
        
        Animator animtr = new Animator(myCanvas);
        animtr.start();
        Animator animtr2 = new Animator(myCanvas2);
        animtr2.start();
        Animator animtr3 = new Animator(myCanvas3);
        animtr3.start();
          
        components.Objects.currentStory = Objects.stories.get(0);
        
        //currentLevel.setText("Nivel: " + components.Objects.currentStory.name);
        
        JFrame frame = new JFrame();
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        CControl control = new CControl(frame);
        
        frame.setLayout(new GridLayout(1,1));
        frame.add(control.getContentArea());
        
        SingleCDockable red = create("Red", Color.RED, rojo, myCanvas2);
        SingleCDockable green = create("Green", Color.GREEN, verde, myCanvas);
        SingleCDockable blue = create("Blue", Color.BLUE, azul, myCanvas3);
        
        control.addDockable(red);
        green.setLocation( CLocation.base().normalEast(0.5));
        control.addDockable(green);
        green.setLocation( CLocation.base().normalEast(0.5));
        control.addDockable(blue);
         
        red.setVisible(true);
             
        green.setVisible(true);
        
        blue.setVisible(true);
        
        frame.setVisible(true);
        
        green.intern().getComponent().repaint();

    }  
    
    public void change(){
        floorSpace.changeSize();
    }
    public SingleCDockable create(String title, Color color, JPanel background, GLCanvas canvas){
        
        background.setOpaque(true);
        background.setBackground(color);
        background.add(canvas);
       
        return new DefaultSingleCDockable(title, title, background);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        
  }

    @Override
    public void componentMoved(ComponentEvent e) {
   }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
   }
    
   
    
    
}
