package scenes;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javax.swing.JFrame;
import mampos.Utils;

/**
 * FXML Controller class
 *
 * @author diego
 */
public class C2_A_Elevation_RequirementsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void verEnLaNormaFHA(ActionEvent e){
        JFrame marco = new JFrame();
        marco.setExtendedState(JFrame.MAXIMIZED_BOTH);
        marco.setVisible(true);
        Utils.abrirPDF("pdfs/fha_alturas.pdf", marco);
    }
    
}
