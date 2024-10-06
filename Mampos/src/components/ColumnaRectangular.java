package components;


//@author Diego
public class ColumnaRectangular extends Columna{
    
    private float x; // Lado x local de la columna
    private float y; // Lado y local de la columna

    public float getX() {return x;}
    public float getY() {return y;}
    
    public void setX(float x) {this.x = x;}
    public void setY(float y) {this.y = y;}
    public void setArea(){area = x * y;}
    
    @Override
    public void setInerciaXX(){inerciaXX = (1f/12f) * x * y * y * y;}
    
    @Override
    public void setInerciaYY(){inerciaYY = (1f/12f) * y * x * x * x;}
    

    public void setParametros(float x, float y, float l, float k){
        setX(x);
        setY(y);
        setArea();
        setAltura(l);
        setK(k);
        setAlturaEfectiva();
        setInerciaXX();
        setInerciaYY();
        setRadioDeGiroX();
        setRadioDeGiroY();
        setSlenderness();
    }
    
    public ColumnaRectangular(){}
    
    
   
}