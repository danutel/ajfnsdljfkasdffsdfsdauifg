import com.jme3.math.ColorRGBA;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class nucleu extends Agent{
    public static List<requestHandler> request_motor_grafic = new ArrayList<requestHandler>();
    public static List<requestHandler> request_date_environment = new ArrayList<requestHandler>();
    public String localaddress="";
    public List<String> online_cells = new ArrayList<>();
    private String locatie= "Camera 1";
    public static String iesire1;
    public static String iesire2;

    @Override
    public void setup() {

        ThreadedBehaviourFactory tbf = new ThreadedBehaviourFactory();

        Behaviour discovery = new Behaviour() {

            @Override
            public void action() {

                try {
                    localaddress = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

                String base = localaddress.split("\\.")[0] + "." + localaddress.split("\\.")[1] + "." + localaddress.split("\\.")[2];

                int timeout = 50;
                for (int i = 2; i < 250; i++) {
                    String host = base + "." + i;
                    try {
                        if (InetAddress.getByName(host).isReachable(timeout)) {
                            // System.out.println(host + " is reachable");
                            //if (i != Integer.parseInt(localaddress.split("\\.")[3]))
                            {
                                ACLMessage discovery = new ACLMessage(ACLMessage.REQUEST);
                                AID rec = new AID("nucleu@" + host + ":1099/JADE", AID.ISGUID);
                                rec.addAddresses("http://" + host + ":7778/acc");
                                discovery.setConversationId("ping");
                                discovery.addReceiver(rec);
                                discovery.setContent(myAgent.getAID().getName() + "~" + localaddress+ "~" +locatie);
                                myAgent.send(discovery);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public boolean done() {
                return false;
            }
        };



        Behaviour receive = new Behaviour() {
            @Override
            public void action() {
                ACLMessage mesaj_receptionat = myAgent.receive();
                if (mesaj_receptionat != null) {
                    if (mesaj_receptionat.getConversationId() == "comanda_motor_grafic") {
                        String[] banane = mesaj_receptionat.getContent().split("~");

                        switch (banane[0]) {
                            case "load":
                                graphicEngine.request.add(new requestHandler(banane[0], banane[1], banane[2], banane[3], Float.parseFloat(banane[4]),
                                        Float.parseFloat(banane[5]), Float.parseFloat(banane[6]), Float.parseFloat(banane[7]), Float.parseFloat(banane[8]), Float.parseFloat(banane[9]),
                                        Integer.parseInt(banane[10]), Float.parseFloat(banane[11]), Integer.parseInt(banane[12]), Float.parseFloat(banane[13])));
                                break;
                            case "foc_start":
                                graphicEngine.request.add(new requestHandler(banane[0], Integer.parseInt(banane[1]), Boolean.parseBoolean(banane[2]),
                                        Float.parseFloat(banane[3]), Integer.parseInt(banane[4]), Integer.parseInt(banane[5]), Integer.parseInt(banane[6])));
                                break;
                            case "stropire":
                                graphicEngine.request.add(new requestHandler(banane[0], Integer.parseInt(banane[1]), Boolean.parseBoolean(banane[2]),
                                        Integer.parseInt(banane[3]), Integer.parseInt(banane[4]), Integer.parseInt(banane[5])));
                                break;
                            case "light":
                                graphicEngine.request.add(new requestHandler(banane[0], Integer.parseInt(banane[1]), Boolean.parseBoolean(banane[2]), Boolean.parseBoolean(banane[3]),
                                        Float.parseFloat(banane[4]), Float.parseFloat(banane[5]), Integer.parseInt(banane[6]), Integer.parseInt(banane[7]), Integer.parseInt(banane[8])));
                                break;
                            case "smoke":
                                graphicEngine.request.add(new requestHandler(banane[0], Integer.parseInt(banane[1]), Boolean.parseBoolean(banane[2]),
                                        Float.parseFloat(banane[3]), Integer.parseInt(banane[4]), Integer.parseInt(banane[5]), Integer.parseInt(banane[6])));
                                break;
                            case "leduri":
                                graphicEngine.request.add(new requestHandler(banane[0], banane[1], Boolean.parseBoolean(banane[2]), Integer.parseInt(banane[3]),
                                        Float.parseFloat(banane[4]), Float.parseFloat(banane[5]), Integer.parseInt(banane[6]), Integer.parseInt(banane[7]), Integer.parseInt(banane[8]),
                                        ColorRGBA.randomColor()));
                                break;
                        }
                    }

                    if (mesaj_receptionat.getConversationId().equals("ping")) {
                        if (online_cells.contains(mesaj_receptionat.getContent()) == false) {
                            online_cells.add(mesaj_receptionat.getContent());
                        }
                        //System.out.println(mesaj_receptionat.getContent());
                    }

                    if (mesaj_receptionat.getConversationId().equals("request_informatii_environment")) {
                        if(mesaj_receptionat.getContent().equals("sup"))
                        {
                            ACLMessage reply = mesaj_receptionat.createReply();
                            reply.setPerformative( ACLMessage.INFORM );
                            reply.setContent(environment.temperatura+"~"+environment.umiditate+"~"+environment.fum+"~"+environment.foc+"~"+environment.temperatura_exterior
                                    +"~"+environment.curent_electric+"~"+environment.lumini_urgenta+"~"+environment.sprinkler+"~"+environment.alarma_incendiu+"~"+environment.ventilatie
                                    +"~"+environment.CO2);
                            myAgent.send(reply);
                        }
                        else if(mesaj_receptionat.getContent().contains("sup~")){
                            int index = Integer.parseInt(mesaj_receptionat.getContent().split("~")[1])-1;
                            ACLMessage reply = mesaj_receptionat.createReply();
                            reply.setPerformative( ACLMessage.INFORM );
                            reply.setContent(environment_hol.fum[index]+"~"+environment_hol.foc[index]+"~"+environment_hol.curent_electric[index]+"~"+environment_hol.lumini_urgenta[index]
                                    +"~"+environment_hol.sprinkler[index]+"~"+environment.alarma_incendiu+"~"+environment_hol.ventilatie[index]);
                            myAgent.send(reply);
                        }
                        else
                        {
                            //System.out.println(mesaj_receptionat.getContent());
                            if(!mesaj_receptionat.getContent().contains("FAILURE")&&!mesaj_receptionat.getContent().contains("internal-error")) {
                                String[] continut = mesaj_receptionat.getContent().split("~");
                                if (continut.length >= 10) {
                                    graphicEngine.temperatura_interior = Double.parseDouble(continut[0]);
                                    graphicEngine.umiditate = Double.parseDouble(continut[1]);
                                    graphicEngine.fum = Float.parseFloat(continut[2]);
                                    graphicEngine.foc = Float.parseFloat(continut[3]);
                                    graphicEngine.temperatura_exterior = Float.parseFloat(continut[4]);
                                    graphicEngine.curent_electric = Boolean.parseBoolean(continut[5]);
                                    graphicEngine.lumini_urgenta = Boolean.parseBoolean(continut[6]);
                                    graphicEngine.sprinkler = Boolean.parseBoolean(continut[7]);
                                    graphicEngine.alarma_incendiu = Boolean.parseBoolean(continut[8]);
                                    graphicEngine.ventilatie = Double.parseDouble(continut[9]);
                                    graphicEngine.CO2 = Double.parseDouble(continut[10]);
                                } else {
                                    graphicEngine.fum = Float.parseFloat(continut[0]);
                                    graphicEngine.foc = Float.parseFloat(continut[1]);
                                    graphicEngine.curent_electric = Boolean.parseBoolean(continut[2]);
                                    graphicEngine.lumini_urgenta = Boolean.parseBoolean(continut[3]);
                                    graphicEngine.sprinkler = Boolean.parseBoolean(continut[4]);
                                    graphicEngine.alarma_incendiu = Boolean.parseBoolean(continut[5]);
                                    graphicEngine.ventilatie = Double.parseDouble(continut[6]);
                                }
                            }
                        }
                    }

                    if (mesaj_receptionat.getConversationId().equals("informatii_scara")) {
                        if (mesaj_receptionat.getSender().toString().contains("iesire1")) {
                            iesire1 = mesaj_receptionat.getContent();
                        }
                        else if (mesaj_receptionat.getSender().toString().contains("iesire2")) {
                            iesire2 = mesaj_receptionat.getContent();
                        }
                        else if(mesaj_receptionat.getContent().contains("iesire1")){
                            ACLMessage mesaj_iesire = new ACLMessage(ACLMessage.INFORM);
                            Iterator it = getAID().getAllAddresses();
                            AID r = new AID("controller_hol@" + getAID().getName().split("@")[1], AID.ISGUID);
                            r.addAddresses((String) it.next());
                            mesaj_iesire.setConversationId("iesire1");
                            mesaj_iesire.addReceiver(r);
                            mesaj_iesire.setContent(mesaj_receptionat.getContent().split("@")[1]);
                            myAgent.send(mesaj_iesire);
                        }
                        else if(mesaj_receptionat.getContent().contains("iesire2"))
                        {
                            ACLMessage mesaj_iesire = new ACLMessage(ACLMessage.INFORM);
                            Iterator it = getAID().getAllAddresses();
                            AID r = new AID("controller_hol@" + getAID().getName().split("@")[1], AID.ISGUID);
                            r.addAddresses((String) it.next());
                            mesaj_iesire.setConversationId("iesire2");
                            mesaj_iesire.addReceiver(r);
                            mesaj_iesire.setContent(mesaj_receptionat.getContent().split("@")[1]);
                            myAgent.send(mesaj_iesire);
                        }
                    }

                    if (mesaj_receptionat.getConversationId().equals("oameni_iesire1")) {
                        //System.out.println(mesaj_receptionat.getContent());
                        for (int i = 0; i < online_cells.size(); i++) {
                            //System.out.println(online_cells.get(i));
                            if(online_cells.get(i).toString().contains("iesire1") && !online_cells.get(i).contains(localaddress))
                            {
                               // System.out.println(online_cells.get(i));
                                ACLMessage mesaj_informatii_scara = new ACLMessage(ACLMessage.REQUEST);
                                AID rec = new AID(online_cells.get(i).split("~")[0], AID.ISGUID);
                                rec.addAddresses("http://" + online_cells.get(i).split("~")[1] + ":7778/acc");
                                mesaj_informatii_scara.setConversationId("oameni_scara");
                                mesaj_informatii_scara.addReceiver(rec);
                                mesaj_informatii_scara.setContent(mesaj_receptionat.getContent());
                                myAgent.send(mesaj_informatii_scara);
                            }
                        }
                    }

                    if (mesaj_receptionat.getConversationId().equals("oameni_iesire2")) {
                        //System.out.println(mesaj_receptionat.getContent());
                        for (int i = 0; i < online_cells.size(); i++) {
                            //System.out.println(online_cells.get(i));
                            if(online_cells.get(i).toString().contains("iesire2") && !online_cells.get(i).contains(localaddress))
                            {
                                //System.out.println(online_cells.get(i));
                                ACLMessage mesaj_informatii_scara = new ACLMessage(ACLMessage.REQUEST);
                                AID rec = new AID(online_cells.get(i).split("~")[0], AID.ISGUID);
                                rec.addAddresses("http://" + online_cells.get(i).split("~")[1] + ":7778/acc");
                                mesaj_informatii_scara.setConversationId("oameni_scara");
                                mesaj_informatii_scara.addReceiver(rec);
                                mesaj_informatii_scara.setContent(mesaj_receptionat.getContent());
                                myAgent.send(mesaj_informatii_scara);
                            }
                        }
                    }

                    if (mesaj_receptionat.getConversationId().equals("oameni_scara")) {
                        //System.out.println(mesaj_receptionat.getContent());
                            ACLMessage mesaj_iesire = new ACLMessage(ACLMessage.REQUEST);
                            Iterator it = getAID().getAllAddresses();
                            AID r = new AID(locatie+ "@" + getAID().getName().split("@")[1], AID.ISGUID);
                            r.addAddresses((String) it.next());
                            mesaj_iesire.setConversationId("iesire1");
                            mesaj_iesire.addReceiver(r);
                            mesaj_iesire.setContent(mesaj_receptionat.getContent());
                            myAgent.send(mesaj_iesire);
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean done() {
                return false;
            }
        };




        Behaviour send = new Behaviour() {
            @Override
            public void action() {
                if (!request_motor_grafic.isEmpty()) {
                    String text = "";

                    switch (request_motor_grafic.get(0).type) {
                        case "load":
                            text = request_motor_grafic.get(0).type + "~" + request_motor_grafic.get(0).nume_arhiva + "~" + request_motor_grafic.get(0).nume_fisier
                                    + "~" + request_motor_grafic.get(0).nume_obiect + "~" + request_motor_grafic.get(0).translatie_x + "~" + request_motor_grafic.get(0).translatie_y
                                    + "~" + request_motor_grafic.get(0).translatie_z + "~" + request_motor_grafic.get(0).scalare_x + "~" + request_motor_grafic.get(0).scalare_y
                                    + "~" + request_motor_grafic.get(0).scalare_z + "~" + request_motor_grafic.get(0).rotatie_x + "~" + request_motor_grafic.get(0).rotatie_y
                                    + "~" + request_motor_grafic.get(0).rotatie_z + "~" + request_motor_grafic.get(0).masa;
                            break;
                        case "foc_start":
                            text = request_motor_grafic.get(0).type + "~" + request_motor_grafic.get(0).index + "~" + request_motor_grafic.get(0).pornit + "~" +
                                    request_motor_grafic.get(0).intensitate_foc + "~" + request_motor_grafic.get(0).translatie_x + "~" + request_motor_grafic.get(0).translatie_y
                                    + "~" + request_motor_grafic.get(0).translatie_z;
                            break;
                        case "stropire":
                            text = request_motor_grafic.get(0).type + "~" + request_motor_grafic.get(0).index + "~" + request_motor_grafic.get(0).pornit + "~" +
                                    request_motor_grafic.get(0).translatie_x + "~" + request_motor_grafic.get(0).translatie_y + "~" + request_motor_grafic.get(0).translatie_z;
                            break;
                        case "light":
                            text = request_motor_grafic.get(0).type + "~" + request_motor_grafic.get(0).index + "~" + request_motor_grafic.get(0).pornit + "~" +
                                    request_motor_grafic.get(0).alarma + "~" + request_motor_grafic.get(0).intensitate_lumina + "~" + request_motor_grafic.get(0).suprafata + "~" +
                                    request_motor_grafic.get(0).scalare_x + "~" + request_motor_grafic.get(0).scalare_y + "~" + request_motor_grafic.get(0).scalare_z;
                            break;
                        case "smoke":
                            text = request_motor_grafic.get(0).type + "~" + request_motor_grafic.get(0).index + "~" + request_motor_grafic.get(0).pornit + "~" +
                                    request_motor_grafic.get(0).intensitate_foc + "~" + request_motor_grafic.get(0).translatie_x + "~" + request_motor_grafic.get(0).translatie_y
                                    + "~" + request_motor_grafic.get(0).translatie_z;
                            break;
                        case "leduri":
                            text = request_motor_grafic.get(0).type + "~" + request_motor_grafic.get(0).nume_obiect + "~" + request_motor_grafic.get(0).pornit + "~" +
                                    request_motor_grafic.get(0).index + "~" + request_motor_grafic.get(0).intensitate_lumina + "~" + request_motor_grafic.get(0).suprafata
                                    + "~" + request_motor_grafic.get(0).translatie_x + "~" + request_motor_grafic.get(0).translatie_y + "~" + request_motor_grafic.get(0).translatie_z
                                    + "~" + request_motor_grafic.get(0).culoare;
                            break;
                    }
                    request_motor_grafic.remove(0);

                    for (int i = 0; i < online_cells.size(); i++) {
                        if(!online_cells.get(i).contains(localaddress)) {
                            ACLMessage mesaj_comanda_motor_grafic = new ACLMessage(ACLMessage.REQUEST);
                            AID rec = new AID(online_cells.get(i).split("~")[0], AID.ISGUID);
                            rec.addAddresses("http://" + online_cells.get(i).split("~")[1] + ":7778/acc");
                            mesaj_comanda_motor_grafic.setConversationId("comanda_motor_grafic");
                            mesaj_comanda_motor_grafic.addReceiver(rec);
                            mesaj_comanda_motor_grafic.setContent(text);
                            myAgent.send(mesaj_comanda_motor_grafic);
                        }
                    }
                }


                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public boolean done() {
                return false;
            }
        };



        Behaviour info = new Behaviour() {
            @Override
            public void action() {

                if(graphicEngine.locatie!="") {
                    for (int i = 0; i < online_cells.size(); i++) {
                        if (online_cells.get(i).contains(graphicEngine.locatie) && graphicEngine.locatie.contains("Camera")) {
                            ACLMessage mesaj_request_informatii_environment = new ACLMessage(ACLMessage.REQUEST);
                            AID rec = new AID(online_cells.get(i).split("~")[0], AID.ISGUID);
                            rec.addAddresses("http://" + online_cells.get(i).split("~")[1] + ":7778/acc");
                            mesaj_request_informatii_environment.setConversationId("request_informatii_environment");
                            mesaj_request_informatii_environment.addReceiver(rec);
                            mesaj_request_informatii_environment.setContent("sup");
                            myAgent.send(mesaj_request_informatii_environment);
                        }
                        else if (online_cells.get(i).contains(graphicEngine.locatie.split(" ")[0])) {
                            ACLMessage mesaj_request_informatii_environment = new ACLMessage(ACLMessage.REQUEST);
                            AID rec = new AID(online_cells.get(i).split("~")[0], AID.ISGUID);
                            rec.addAddresses("http://" + online_cells.get(i).split("~")[1] + ":7778/acc");
                            mesaj_request_informatii_environment.setConversationId("request_informatii_environment");
                            mesaj_request_informatii_environment.addReceiver(rec);
                            mesaj_request_informatii_environment.setContent("sup~" + graphicEngine.locatie.split(" ")[2]);
                            myAgent.send(mesaj_request_informatii_environment);
                        }
                    }
                }

                for (int i = 0; i < online_cells.size(); i++) {
                    //System.out.println(online_cells.get(i));
                    if(online_cells.get(i).toString().contains("Hol") && !online_cells.get(i).contains(localaddress))
                    {
                        ACLMessage mesaj_informatii_scara = new ACLMessage(ACLMessage.REQUEST);
                        AID rec = new AID(online_cells.get(i).split("~")[0], AID.ISGUID);
                        rec.addAddresses("http://" + online_cells.get(i).split("~")[1] + ":7778/acc");
                        mesaj_informatii_scara.setConversationId("informatii_scara");
                        mesaj_informatii_scara.addReceiver(rec);
                        if(locatie.contains("iesire1")) {
                            mesaj_informatii_scara.setContent(iesire1);
                        }
                        else
                        {
                            mesaj_informatii_scara.setContent(iesire2);
                        }
                        myAgent.send(mesaj_informatii_scara);
                    }
                }


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean done() {
                return false;
            }
        };

        addBehaviour(tbf.wrap(discovery));
        addBehaviour(tbf.wrap(receive));
        addBehaviour(tbf.wrap(send));
        addBehaviour(tbf.wrap(info));
    }

}