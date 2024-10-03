package components;

//@author diego
public class Punto {
    
    public float xEnMetros, yEnMetros, zEnMetros;
    public float xEnUnidadesEscogidas, yEnUnidadesEscogidas, zEnUnidadesEscogidas;

    public Punto(){}
   
    private void convertirAMetros(){
        xEnMetros = Unidad.convertirLongitud(xEnUnidadesEscogidas, "metros");
        yEnMetros = Unidad.convertirLongitud(yEnUnidadesEscogidas, "metros");
        zEnMetros = Unidad.convertirLongitud(zEnUnidadesEscogidas, "metros");
    }
    
    public void definirCoordenadas(float nuevaX, float nuevaY, float nuevaZ){
        this.xEnUnidadesEscogidas = nuevaX;
        this.yEnUnidadesEscogidas = nuevaY;
        this.zEnUnidadesEscogidas = nuevaZ;
        convertirAMetros();
    }
    
    public void moverPuntoPorDistanciasXYZ(float cambioX, float cambioY, float cambioZ){
        xEnUnidadesEscogidas += cambioX;
        yEnUnidadesEscogidas += cambioY;
        zEnUnidadesEscogidas += cambioZ;
        convertirAMetros();
    }
    
    public void moverPuntoPorDistanciaYAngulos(float distancia, float anguloX, float anguloY, float anguloZ){
        xEnUnidadesEscogidas += distancia * anguloX;
        yEnUnidadesEscogidas += distancia * anguloY;
        zEnUnidadesEscogidas += distancia * anguloZ;
        convertirAMetros();
    }
   
}