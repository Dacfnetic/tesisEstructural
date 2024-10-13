
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
import javafx.scene.layout.Background;
import javax.swing.JFrame;
import javax.swing.JPanel;
import rendering.Planta;


public class Interfaz implements ComponentListener{
    
    
    private GLCanvas myCanvas;
    private GLCanvas myCanvas2;
    private Planta floorSpace;
    
    public static JPanel verde = new JPanel();
    private JPanel rojo =  new JPanel();
    
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
        
        SingleCDockable red = create("Red", Color.RED, rojo);
        SingleCDockable green = create("Green", Color.GREEN, verde);
        
        
        control.addDockable(red);
        green.setLocation( CLocation.base().normalEast(0.5));
        control.addDockable(green);
        
        
        red.setVisible(true);
             
        green.setVisible(true);
        
        frame.setVisible(true);
        green.intern().getComponent().repaint();
        CLocation i = green.getBaseLocation();
        System.out.println(green.intern().getComponent().getX());
        System.out.println(green.intern().getComponent().getY());
        System.out.println(green.intern().getComponent().getWidth());
        System.out.println(control.getCDockable(0));
        System.out.println(control.getCDockableCount());
        
   
        System.out.println(verde.getX());
        System.out.println(verde.getY());
        System.out.println(verde.getWidth());
        System.out.println(verde.getHeight());
        
        
    }
    
    public void change(){
        floorSpace.changeSize();
    }
    public SingleCDockable create(String title, Color color, JPanel background){
        
        background.setOpaque(true);
        background.setBackground(color);
        background.add(myCanvas);
       
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
