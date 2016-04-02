import jade.core.Agent;

public class unAgent extends Agent {
    @Override
    protected void setup() {
        graphicEngine app=new graphicEngine();
       /* AppSettings settings = new AppSettings(true);
        settings.setResolution(1680, 1050);
        settings.setFullscreen(true);
        app.setShowSettings(false);
        app.setSettings(settings);*/
        app.start();
    }
}
