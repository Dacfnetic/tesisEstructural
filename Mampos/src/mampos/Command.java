/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mampos;

/**
 *
 * @author Diego
 */
import static com.jogamp.opengl.GL4.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import frames.MaterialsLibrary;

import frames.StoriesDefinitionFrame;
import frames.UnitsFrame;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public abstract class Command {

    public static void handleKey(KeyEvent e) {
    	if(e.getKeyCode() == KeyEvent.VK_N) {
    		System.out.println("Crear los niveles");
                openStoryEditor();
    	}
        if(e.getKeyCode() == KeyEvent.VK_M) {
         
    	}
    }
    
    public static void openStoryEditor(){
        new StoriesDefinitionFrame().setVisible(true);
    }
    public static void openUnitsEditor(){
        new UnitsFrame().setVisible(true);
    }
    
     
}

	