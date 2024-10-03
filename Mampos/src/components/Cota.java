/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

//@author diego
public class Cota {
    
    //Que necesita la cota
    //La cota necesita vertice
    //La cota se va a renderizar como lineas
    //Cuantas lineas necesita una cota
    //La que es paralela al elemento (1)
    //Las que son perpendiculares al elemento (2)
    //Necesito renderizar el texto, la medida
    public float vertexParallelLine[] = {0f, 0f, 0f,    0f, 0f, 0f};
    public float vertexPerpendicularLine1[] = {0f, 0f, 0f,    0f, 0f, 0f};  
    public float vertexPerpendicularLine2[] = {0f, 0f, 0f,    0f, 0f, 0f};
    
    public Cota(){
        
    }
    
    public void setearVertices(Muro muro){
        //Tengo que arreglar la clase muro, está mul mal, necesito las matrices y el orden
        
        vertexParallelLine[0] = muro.point1.x();
        vertexParallelLine[1] = -1.1f;
        vertexParallelLine[2] = muro.point1.z();
        vertexParallelLine[3] = muro.point2.x();
        vertexParallelLine[4] = -1.1f;
        vertexParallelLine[5] = muro.point2.z();
        
    }
    
   
    
    
    
    
}
