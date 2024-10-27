package components;

import com.jogamp.opengl.math.Vec3f;
import java.util.ArrayList;
import java.util.List;
import rendering.Objeto;

//@author Diego
public class Muro extends Objeto{
    
    public Vec3f point1 = new Vec3f(0.0f, 0.0f, 0.0f);
    public Vec3f point2 = new Vec3f(0.0f, 0.0f, 0.0f);
 
    public float h = 3.0f;
    public float t = 0.20f;
 	
    public float largo;
	
    private float areaTotal;
    private float cantidadDeBlocks;
    private float pesoTotal;
    
    public static List<Cota> cotas = new ArrayList<>();

    public Muro(){}
    
    public void setPoints(Vec3f p1, Vec3f p2) {
        this.point1 = p1;
        this.point2 = p2;
    }

    public void setVertexlikeMidWall() {
        uv.clear();
        vertex.clear();

        float deltaX = this.point2.x() - this.point1.x();
        float deltaY = this.point2.y() - this.point1.y();

        float angle = (float) Math.atan(deltaY/deltaX);
        float sinAngle = (float) Math.sin(angle);
        float cosAngle = (float) Math.cos(angle);

        if(angle < 0){
            sinAngle = -sinAngle;
            cosAngle = -cosAngle;
        }

        largo = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        
        Vec3f t1v1 = new Vec3f(point1.x() + (t/2.0f) * sinAngle,  point1.y() - (t/2.0f) * cosAngle, point1.z());
        Vec3f t1v2 = new Vec3f(point2.x() + (t/2.0f) * sinAngle, point2.y() - (t/2.0f) * cosAngle, point2.z() + h);
        Vec3f t1v3 = new Vec3f(point1.x() + (t/2.0f) * sinAngle, point1.y() - (t/2.0f) * cosAngle, point1.z() + h);
        
        Vec3f t2v1 = t1v1;
        Vec3f t2v2 = new Vec3f(point2.x() + (t/2.0f) * sinAngle, point2.y() - (t/2.0f) * cosAngle,  point2.z());
        Vec3f t2v3 = t1v2;

        Vec3f t3v1 = new Vec3f(point1.x() - (t/2.0f) * sinAngle, point1.y() + (t/2.0f) * cosAngle, point1.z());
        Vec3f t3v2 = new Vec3f(point2.x() - (t/2.0f) * sinAngle, point2.y() + (t/2.0f) * cosAngle, point2.z()+h);
        Vec3f t3v3 = new Vec3f(point1.x() - (t/2.0f) * sinAngle, point1.y() + (t/2.0f) * cosAngle, point1.z()+h);

        Vec3f t4v1 = t3v1;
        Vec3f t4v2 = new Vec3f(point2.x() - (t/2.0f) * sinAngle, point2.y() + (t/2.0f) * cosAngle, point2.z());
        Vec3f t4v3 = t3v2;

        Vec3f t5v1 = t1v1;
        Vec3f t5v2 = t1v3;
        Vec3f t5v3 = t3v1;

        Vec3f t6v1 = t3v1;
        Vec3f t6v2 = t1v3;
        Vec3f t6v3 = t3v3;

        Vec3f t7v1 = t2v2;
        Vec3f t7v2 = t2v3;
        Vec3f t7v3 = t4v2;

        Vec3f t8v1 = t4v2;
        Vec3f t8v2 = t2v3;
        Vec3f t8v3 = t4v3;

        Vec3f t9v1 = t1v1;
        Vec3f t9v2 = t2v2;
        Vec3f t9v3 = t3v1;

        Vec3f t10v1 = t3v1;
        Vec3f t10v2 = t2v2;
        Vec3f t10v3 = t4v2;

        Vec3f t11v1 = t1v3;
        Vec3f t11v2 = t1v2;
        Vec3f t11v3 = t3v2;

        Vec3f t12v1 = t3v3;
        Vec3f t12v2 = t1v3;
        Vec3f t12v3 = t3v2;
        
        float[] vertices = {
            t1v1.x(), t1v1.y(), t1v1.z(),			t1v2.x(), t1v2.y(), t1v2.z(),	 		t1v3.x(), t1v3.y(), t1v3.z(),	
            t2v1.x(), t2v1.y(), t2v1.z(),			t2v2.x(), t2v2.y(), t2v2.z(),	 		t2v3.x(), t2v3.y(), t2v3.z(),
            t3v1.x(), t3v1.y(), t3v1.z(),			t3v2.x(), t3v2.y(), t3v2.z(),	 		t3v3.x(), t3v3.y(), t3v3.z(),	
            t4v1.x(), t4v1.y(), t4v1.z(),			t4v2.x(), t4v2.y(), t4v2.z(),	 		t4v3.x(), t4v3.y(), t4v3.z(),
            t5v1.x(), t5v1.y(), t5v1.z(),			t5v2.x(), t5v2.y(), t5v2.z(),	 		t5v3.x(), t5v3.y(), t5v3.z(),	
            t6v1.x(), t6v1.y(), t6v1.z(),			t6v2.x(), t6v2.y(), t6v2.z(),	 		t6v3.x(), t6v3.y(), t6v3.z(),
            t7v1.x(), t7v1.y(), t7v1.z(),			t7v2.x(), t7v2.y(), t7v2.z(),	 		t7v3.x(), t7v3.y(), t7v3.z(),	
            t8v1.x(), t8v1.y(), t8v1.z(),			t8v2.x(), t8v2.y(), t8v2.z(),	 		t8v3.x(), t8v3.y(), t8v3.z(),
            t9v1.x(), t9v1.y(), t9v1.z(),			t9v2.x(), t9v2.y(), t9v2.z(),	 		t9v3.x(), t9v3.y(), t9v3.z(),	
            t10v1.x(), t10v1.y(), t10v1.z(),			t10v2.x(), t10v2.y(), t10v2.z(),	 		t10v3.x(), t10v3.y(), t10v3.z(),
            t11v1.x(), t11v1.y(), t11v1.z(),			t11v2.x(), t11v2.y(), t11v2.z(),	 		t11v3.x(), t11v3.y(), t11v3.z(),	
            t12v1.x(), t12v1.y(), t12v1.z(),			t12v2.x(), t12v2.y(), t12v2.z(),	 		t12v3.x(), t12v3.y(), t12v3.z()
        };
        
        float[] uvs = {
            t1v1.x(), t1v1.y(), 			t1v2.x(), t1v2.y(), 	 		t1v3.x(), t1v3.y(), 	
            t2v1.x(), t2v1.y(), 			t2v2.x(), t2v2.y(),                     t2v3.x(), t2v3.y(), 
            t3v1.x(), t3v1.y(), 			t3v2.x(), t3v2.y(), 	 		t3v3.x(), t3v3.y(), 
            t4v1.x(), t4v1.y(), 			t4v2.x(), t4v2.y(), 	 		t4v3.x(), t4v3.y(), 
            t5v1.x(), t5v1.y(), 			t5v2.x(), t5v2.y(), 	 		t5v3.x(), t5v3.y(), 
            t6v1.x(), t6v1.y(), 			t6v2.x(), t6v2.y(), 	 		t6v3.x(), t6v3.y(), 
            t7v1.x(), t7v1.y(), 			t7v2.x(), t7v2.y(), 	 		t7v3.x(), t7v3.y(), 	
            t8v1.x(), t8v1.y(), 			t8v2.x(), t8v2.y(), 	 		t8v3.x(), t8v3.y(), 
            t9v1.x(), t9v1.y(), 			t9v2.x(), t9v2.y(), 	 		t9v3.x(), t9v3.y(), 	
            t10v1.x(), t10v1.y(),                       t10v2.x(), t10v2.y(),	 		t10v3.x(), t10v3.y(), 
            t11v1.x(), t11v1.y(), 			t11v2.x(), t11v2.y(), 	 		t11v3.x(), t11v3.y(), 	
            t12v1.x(), t12v1.y(), 			t12v2.x(), t12v2.y(), 	 		t12v3.x(), t12v3.y()
        };
        
        for(int i = 0; i < vertices.length; i++){
            vertex.add(vertices[i]);
        }
        
        for(int i = 0; i < uvs.length; i++){
            uv.add(uvs[i]);
        }
    }
    
    public void setVertexlikeExteriorWall() {
        vertex.clear();
        
        float deltaX = this.point2.x() - this.point1.x();
        float deltaZ = this.point2.z() - this.point1.z();

        float angle = (float) Math.atan(deltaZ/deltaX);
        float sinAngle = (float) Math.sin(angle);
        float cosAngle = (float) Math.cos(angle);

        if(angle < 0){
            sinAngle = -sinAngle;
            cosAngle = -cosAngle;
        }

        largo = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        Vec3f t1v1 = new Vec3f(point1.x(), -point1.y(),  point1.z());
        Vec3f t1v2 = new Vec3f(point2.x(), -point2.y() - h, point2.z());
        Vec3f t1v3 = new Vec3f(point1.x(), -point1.y() - h, point1.z());

        Vec3f t2v1 = t1v1;
        Vec3f t2v2 = new Vec3f(point2.x(),  -point2.y(), point2.z());
        Vec3f t2v3 = t1v2;

        Vec3f t3v1 = new Vec3f(point1.x() - (t) * sinAngle, -point1.y(),  point1.z() + (t) * cosAngle);
        Vec3f t3v2 = new Vec3f(point2.x() - (t) * sinAngle, -point2.y()-h, point2.z() + (t) * cosAngle);
        Vec3f t3v3 = new Vec3f(point1.x() - (t) * sinAngle, -point1.y()-h, point1.z() + (t) * cosAngle);

        Vec3f t4v1 = t3v1;
        Vec3f t4v2 = new Vec3f(point2.x() - (t) * sinAngle, -point2.y(), point2.z() + (t) * cosAngle);
        Vec3f t4v3 = t3v2;


        Vec3f t5v1 = t1v1;
        Vec3f t5v2 = t1v3;
        Vec3f t5v3 = t3v1;

        Vec3f t6v1 = t3v1;
        Vec3f t6v2 = t1v3;
        Vec3f t6v3 = t3v3;


        Vec3f t7v1 = t2v2;
        Vec3f t7v2 = t2v3;
        Vec3f t7v3 = t4v2;

        Vec3f t8v1 = t4v2;
        Vec3f t8v2 = t2v3;
        Vec3f t8v3 = t4v3;

        Vec3f t9v1 = t1v1;
        Vec3f t9v2 = t2v2;
        Vec3f t9v3 = t3v1;

        Vec3f t10v1 = t3v1;
        Vec3f t10v2 = t2v2;
        Vec3f t10v3 = t4v2;

        Vec3f t11v1 = t1v3;
        Vec3f t11v2 = t1v2;
        Vec3f t11v3 = t3v2;

        Vec3f t12v1 = t3v3;
        Vec3f t12v2 = t1v3;
        Vec3f t12v3 = t3v2;

        float[] vertices = {
                    t1v1.x(), t1v1.y(), t1v1.z(),			t1v2.x(), t1v2.y(), t1v2.z(),	 		t1v3.x(), t1v3.y(), t1v3.z(),	
                    t2v1.x(), t2v1.y(), t2v1.z(),			t2v2.x(), t2v2.y(), t2v2.z(),	 		t2v3.x(), t2v3.y(), t2v3.z(),
                    t3v1.x(), t3v1.y(), t3v1.z(),			t3v2.x(), t3v2.y(), t3v2.z(),	 		t3v3.x(), t3v3.y(), t3v3.z(),	
                    t4v1.x(), t4v1.y(), t4v1.z(),			t4v2.x(), t4v2.y(), t4v2.z(),	 		t4v3.x(), t4v3.y(), t4v3.z(),
                    t5v1.x(), t5v1.y(), t5v1.z(),			t5v2.x(), t5v2.y(), t5v2.z(),	 		t5v3.x(), t5v3.y(), t5v3.z(),	
                    t6v1.x(), t6v1.y(), t6v1.z(),			t6v2.x(), t6v2.y(), t6v2.z(),	 		t6v3.x(), t6v3.y(), t6v3.z(),
                    t7v1.x(), t7v1.y(), t7v1.z(),			t7v2.x(), t7v2.y(), t7v2.z(),	 		t7v3.x(), t7v3.y(), t7v3.z(),	
                    t8v1.x(), t8v1.y(), t8v1.z(),			t8v2.x(), t8v2.y(), t8v2.z(),	 		t8v3.x(), t8v3.y(), t8v3.z(),
                    t9v1.x(), t9v1.y(), t9v1.z(),			t9v2.x(), t9v2.y(), t9v2.z(),	 		t9v3.x(), t9v3.y(), t9v3.z(),	
                    t10v1.x(), t10v1.y(), t10v1.z(),			t10v2.x(), t10v2.y(), t10v2.z(),	 		t10v3.x(), t10v3.y(), t10v3.z(),
                    t11v1.x(), t11v1.y(), t11v1.z(),			t11v2.x(), t11v2.y(), t11v2.z(),	 		t11v3.x(), t11v3.y(), t11v3.z(),	
                    t12v1.x(), t12v1.y(), t12v1.z(),			t12v2.x(), t12v2.y(), t12v2.z(),	 		t12v3.x(), t12v3.y(), t12v3.z(),	
            };
        
        for(int i = 0; i < vertices.length; i++){
            vertex.add(vertices[i]);
        }
    }

    public void flipVertexlikeExteriorWall() {}

    public void setParametros(float pesoUnitarioBlock) {
        System.out.println("Altura: " + this.h + " m");
        System.out.println("Largo: " + this.largo + " m");
        System.out.println("Ancho: " + this.t + " m");
        this.areaTotal = this.h * this.largo;
        System.out.println("Área total: " + this.areaTotal + " m²");
        this.cantidadDeBlocks = this.areaTotal * 12.5f;	
        System.out.println("Cantidad de block: " + this.cantidadDeBlocks + " blocks");
        System.out.println("Peso por block: " + pesoUnitarioBlock + " kg");
        this.pesoTotal = this.cantidadDeBlocks * pesoUnitarioBlock;
        System.out.println("pesoTotal: " + pesoTotal + " kg");	
    }

}