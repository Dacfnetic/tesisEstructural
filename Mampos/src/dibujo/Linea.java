package dibujo;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;
import rendering.Objeto;

public class Linea extends Objeto implements Cloneable{

    private Matrix punto1 = DenseMatrix.Factory.zeros(3, 1);
    private Matrix punto2 = DenseMatrix.Factory.zeros(3, 1);
    private Matrix vectorDirector = DenseMatrix.Factory.zeros(3, 1);
    public static List<Float> verticesDeLinea = new ArrayList<>();
    
    public Linea(){}
    
    public void setPunto1(double x, double z, double y){
        punto1.setAsDouble(x, 0, 0);
        punto1.setAsDouble(z, 1, 0);
        punto1.setAsDouble(y, 2, 0);
    }
    
    public void setPunto2(double x, double z, double y){
        punto2.setAsDouble(x, 0, 0);
        punto2.setAsDouble(z, 1, 0);
        punto2.setAsDouble(y, 2, 0);
    }
    
    public void setVectorDirector(){
        vectorDirector = punto2.minus(punto1);
    }
    
    public void setVertices(){
        vertex.clear();
        vertex.add(punto1.getAsFloat(0,0));
        vertex.add(punto1.getAsFloat(1,0));
        vertex.add(punto1.getAsFloat(2,0));
        vertex.add(punto2.getAsFloat(0,0));
        vertex.add(punto2.getAsFloat(1,0));
        vertex.add(punto2.getAsFloat(1,0));
    }
    
    public void imprimir(){
        System.out.println("Punto 1");
        System.out.println(punto1);
        System.out.println("Punto 2");
        System.out.println(punto2);
        System.out.println("Pendiente");
        System.out.println(vectorDirector);
        System.out.println("FloatValue");
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Linea nueva = new Linea();
        nueva.punto1 = this.punto1;
        nueva.punto2 = this.punto2;
        nueva.vectorDirector = this.vectorDirector;
        nueva.vertex = this.vertex;
        return nueva;
    }
    
}
