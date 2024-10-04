package units;

public class Longitud {

    public static float convertirAPulgadas(float cantidad, String dimension){
        float convertida;
        if(dimension.equals("cm")){
            convertida = cantidad / 2.54f ;
        }else{
            convertida = cantidad;
        }
        return convertida;
    }
    
}
