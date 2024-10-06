package components;
import rendering.Objeto;

//@author Diego
public class Columna extends Objeto{
    
    protected float area;
    protected float altura;
    protected float k;
    protected float alturaEfectiva;
    protected float inerciaXX;
    protected float inerciaYY;
    protected float radioDeGiroX;
    protected float radioDeGiroY;
    protected float slenderness;
    
    public void imprimir(){
        System.out.println("Area: " + area);
        System.out.println("Altura: " + altura);
        System.out.println("K: " + k);
        System.out.println("Altura efectiva: " + alturaEfectiva);
        System.out.println("Inercia XX: " + inerciaXX);
        System.out.println("Inercia YY: " + inerciaYY);
        System.out.println("Radio de giro X: " + radioDeGiroX);
        System.out.println("Radio de giro Y: " + radioDeGiroY);
        System.out.println("Relación de esbeltez: " + slenderness);
    }
   
    public float getSlenderness() {return slenderness;}

    public void setParametros(){}
    public void setInerciaXX(){}
    public void setInerciaYY(){}
    public void setRadioDeGiroX(){radioDeGiroX = (float) Math.sqrt(inerciaXX/area);}
    public void setRadioDeGiroY(){radioDeGiroY = (float) Math.sqrt(inerciaYY/area);}
    public void setAltura(float l){altura = l;}
    public void setK(float k){this.k = k;}
    public void setAlturaEfectiva(){alturaEfectiva = altura * k;}
    public void setSlenderness() {slenderness = alturaEfectiva/Math.min(radioDeGiroX, radioDeGiroY);}
    
    
    
    public Columna(){}
    
  
    
}