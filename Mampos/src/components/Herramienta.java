/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

/**
 *
 * @author diego
 */
public class Herramienta {
    
    public String nombre;
    public boolean estado = false;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {this.nombre = nombre;}

    public boolean isUsed() {return estado;}

    public void setEstado(boolean estado) {this.estado = estado;}
    
    public Herramienta(){
       
    }
    
    
    
}
