package components;

import com.jogamp.opengl.GL4;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL40;
import rendering.Controlador;

public class ControladorDeLosas extends Controlador{
    
    public static List<Losa> losas = new ArrayList<>();
    public static final Losa temporal = new Losa();
     
    public static Losa loadToVAO(float[] positions) {
	int vaoID = createVAO();
	storeDataInAttributeList(0, positions);
	GL40.glBindVertexArray(0);
        Losa losa = new Losa();
        losa.setVaoID(vaoID);
        losa.setVertexCount(positions.length/3);
	return losa;
    }
    
}