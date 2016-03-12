import com.jme3.math.ColorRGBA;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

public class nucleu extends Agent{
    public static List<requestHandler> request_motor_grafic = new ArrayList<requestHandler>();
    @Override
    public void setup(){
        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage mesaj_receptionat = myAgent.receive();
                if(mesaj_receptionat!=null) {
                    if (mesaj_receptionat.getConversationId() == "comanda_motor_grafic") {
                        String[] banane = mesaj_receptionat.getContent().split("~");

                        switch (banane[0]) {
                            case "load":graphicEngine.request.add(new requestHandler(banane[0],banane[1],banane[2],banane[3],Float.parseFloat(banane[4]),
                                    Float.parseFloat(banane[5]),Float.parseFloat(banane[6]),Float.parseFloat(banane[7]),Float.parseFloat(banane[8]),Float.parseFloat(banane[9]),
                                    Integer.parseInt(banane[10]),Float.parseFloat(banane[11]),Integer.parseInt(banane[12]),Float.parseFloat(banane[13])));break;
                            case "foc_start":graphicEngine.request.add(new requestHandler(banane[0],Integer.parseInt(banane[1]),Boolean.parseBoolean(banane[2]),
                                    Float.parseFloat(banane[3]),Integer.parseInt(banane[4]),Integer.parseInt(banane[5]),Integer.parseInt(banane[6])));break;
                            case "stropire":graphicEngine.request.add(new requestHandler(banane[0],Integer.parseInt(banane[1]),Boolean.parseBoolean(banane[2]),
                                    Integer.parseInt(banane[3]),Integer.parseInt(banane[4]),Integer.parseInt(banane[5])));break;
                            case "light":graphicEngine.request.add(new requestHandler(banane[0],Integer.parseInt(banane[1]),Boolean.parseBoolean(banane[2]),Boolean.parseBoolean(banane[3]),
                                    Float.parseFloat(banane[4]),Float.parseFloat(banane[5]),Integer.parseInt(banane[6]),Integer.parseInt(banane[7]),Integer.parseInt(banane[8])));break;
                            case "smoke":graphicEngine.request.add(new requestHandler(banane[0],Integer.parseInt(banane[1]),Boolean.parseBoolean(banane[2]),
                                    Float.parseFloat(banane[3]),Integer.parseInt(banane[4]),Integer.parseInt(banane[5]),Integer.parseInt(banane[6])));break;
                            case "leduri":graphicEngine.request.add(new requestHandler(banane[0],banane[1],Boolean.parseBoolean(banane[2]),Integer.parseInt(banane[3]),
                                    Float.parseFloat(banane[4]),Float.parseFloat(banane[5]),Integer.parseInt(banane[6]),Integer.parseInt(banane[7]),Integer.parseInt(banane[8]),
                                    ColorRGBA.randomColor()));break;
                        }
                    }
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean done() {
                return false;
            }
        });


        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                if(!request_motor_grafic.isEmpty()) {
                    String text="";

                    switch (request_motor_grafic.get(0).type) {
                        case "load":text = request_motor_grafic.get(0).type + "~" + request_motor_grafic.get(0).nume_arhiva + "~" + request_motor_grafic.get(0).nume_fisier
                                + "~" + request_motor_grafic.get(0).nume_obiect + "~" + request_motor_grafic.get(0).translatie_x + "~" + request_motor_grafic.get(0).translatie_y
                                + "~" + request_motor_grafic.get(0).translatie_z + "~" + request_motor_grafic.get(0).scalare_x + "~" + request_motor_grafic.get(0).scalare_y
                                + "~" + request_motor_grafic.get(0).scalare_z + "~" + request_motor_grafic.get(0).rotatie_x + "~" + request_motor_grafic.get(0).rotatie_y
                                + "~" + request_motor_grafic.get(0).rotatie_z + "~" + request_motor_grafic.get(0).masa;break;
                        case "foc_start":text = request_motor_grafic.get(0).type + "~" + request_motor_grafic.get(0).index + "~" + request_motor_grafic.get(0).pornit + "~" +
                                request_motor_grafic.get(0).intensitate_foc + "~" + request_motor_grafic.get(0).translatie_x + "~" + request_motor_grafic.get(0).translatie_y
                                + "~" + request_motor_grafic.get(0).translatie_z;break;
                        case "stropire":text= request_motor_grafic.get(0).type + "~" +request_motor_grafic.get(0).index + "~" + request_motor_grafic.get(0).pornit + "~" +
                                request_motor_grafic.get(0).translatie_x + "~" + request_motor_grafic.get(0).translatie_y + "~" + request_motor_grafic.get(0).translatie_z;break;
                        case "light":text = request_motor_grafic.get(0).type + "~" +request_motor_grafic.get(0).index + "~" +request_motor_grafic.get(0).pornit + "~" +
                                request_motor_grafic.get(0).alarma + "~" +request_motor_grafic.get(0).intensitate_lumina + "~" +request_motor_grafic.get(0).suprafata + "~" +
                                request_motor_grafic.get(0).scalare_x + "~" +request_motor_grafic.get(0).scalare_y + "~" +request_motor_grafic.get(0).scalare_z;break;
                        case "smoke":text = request_motor_grafic.get(0).type + "~" + request_motor_grafic.get(0).index + "~" + request_motor_grafic.get(0).pornit + "~" +
                                request_motor_grafic.get(0).intensitate_foc + "~" + request_motor_grafic.get(0).translatie_x + "~" + request_motor_grafic.get(0).translatie_y
                                + "~" + request_motor_grafic.get(0).translatie_z;break;
                        case "leduri":text = request_motor_grafic.get(0).type + "~" + request_motor_grafic.get(0).nume_obiect + "~" + request_motor_grafic.get(0).pornit + "~" +
                                request_motor_grafic.get(0).index + "~" + request_motor_grafic.get(0).intensitate_lumina + "~" + request_motor_grafic.get(0).suprafata
                                + "~" + request_motor_grafic.get(0).translatie_x+ "~" + request_motor_grafic.get(0).translatie_y+ "~" + request_motor_grafic.get(0).translatie_z
                                + "~" + request_motor_grafic.get(0).culoare;break;
                    }
                    request_motor_grafic.remove(0);

                    ACLMessage request_motor_grafic = new ACLMessage(ACLMessage.REQUEST);
                    AID rec = new AID("nucleu@server", AID.ISGUID);
                    rec.addAddresses("http://DESKTOP-7ILLSRK:7778/acc");
                    request_motor_grafic.setConversationId("comanda_motor_grafic");
                    request_motor_grafic.addReceiver(rec);
                    request_motor_grafic.setContent(text);
                    myAgent.send(request_motor_grafic);
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean done() {
                return false;
            }
        });
    }

}
