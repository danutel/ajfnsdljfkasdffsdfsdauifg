import com.jme3.scene.Spatial;
import jade.core.behaviours.Behaviour;

public class loader extends Behaviour{
    private Spatial femeie1,map,bloc,barbat1,femeie2,barbat3,femeie3;

    @Override
    public void action() {
       // graphicEngine app = new graphicEngine();
        //app.start();
        //app.load_object("Modele/Oameni/femeie1.zip", "femeie1.scene", femeie1, -500, -10, -10, 0.0022f, 0.0022f, 0.0022f, 0, 0, 0, 1f);
       // app.load_object("Modele/Oameni/barbat1.zip", "barbat1.j3o", barbat1, -400, 0, -20, 0.13f, 0.13f, 0.13f, 0, 0, 0, 0.5f);
        //app.load_object("Modele/Oameni/femeie2.zip", "femeie2.j3o", femeie2, -400, 0, 0, 0.15f, 0.15f, 0.15f, 0, 0, 0, 0.5f);
       // app.load_object("Modele/Oameni/barbat3.zip", "barbat3.j3o", barbat3, -420, 0, -20, 0.15f, 0.12f, 0.12f, 0, 0, 0, 0.5f);
        //app.load_object("Modele/Oameni/femeie3.zip", "femeie3.j3o", femeie3, -400, 0, -30, 0.12f, 0.12f, 0.12f, 0, 0, 0, 0.5f);

    }

    @Override
    public boolean done() {
        return false;
    }
}
