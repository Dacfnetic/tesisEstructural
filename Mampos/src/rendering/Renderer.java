package rendering;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

public class Renderer {
    
    public void prepare(){
        GL4 gl = (GL4) GLContext.getCurrentGL();
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL4.GL_COLOR_BUFFER_BIT);
    }
    
    public void render(RawModel rawModel){
        GL4 gl = (GL4) GLContext.getCurrentGL();
        gl.glBindVertexArray(rawModel.getVaoID());
        gl.glEnableVertexAttribArray(0);
        gl.glDrawArrays(GL4.GL_TRIANGLES, 0, rawModel.getVertexCount());
        gl.glDisableVertexAttribArray(0);
        gl.glBindVertexArray(0);
    }
    
}
