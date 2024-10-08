package optimization;

import org.ujmp.core.*;

public class Optimizar {
    
    private double resultado;
    private double x1;
    private double x2;
    
    public void funciónObjetivo(){
        resultado = (9.82d * x1 * x2) + (2 * x1);
    }
    
    public Optimizar(){
        Object[] objetos = new Object[2];
        objetos[0] = 5;
        objetos[1] = 5;
        Matrix vectorDeDiseño = crearVectorDeDiseño(2,objetos);
        System.out.println(vectorDeDiseño);
    }
    
    public Matrix crearVectorDeDiseño(int cantidadDeVariablesDeDiseño, Object[] objetos){
        Matrix vectorDeDiseño = DenseMatrix.Factory.zeros(cantidadDeVariablesDeDiseño, 1);
        for(int i = 0; i < objetos.length; i++){
            vectorDeDiseño.setAsObject(objetos[i], i,0);
          
        }
        return vectorDeDiseño;
    }
    
    
}
