import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;
import org.lwjgl.Sys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class controller extends Agent {
    public double temperatura;
    public boolean fum;
    public double umiditate;
    public double ventilatie;
    public boolean stropitori;
    public boolean lumini_urgenta;
    public boolean electricitate;
    public double umidificator;
    public static List<String> lista_celule = new ArrayList<>();

    @Override
    public void setup(){

        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage mesaj_receptionat = myAgent.receive();
                if(mesaj_receptionat!=null)
                {
                    if(mesaj_receptionat.getConversationId()=="temperatura")
                    {
                        temperatura = Double.parseDouble(mesaj_receptionat.getContent());
                    }

                    if(mesaj_receptionat.getConversationId()=="umiditate")
                    {
                        umiditate = Double.parseDouble(mesaj_receptionat.getContent());
                    }

                    if(mesaj_receptionat.getConversationId()=="fum")
                    {
                        fum = Boolean.parseBoolean(mesaj_receptionat.getContent());
                    }

                    if(mesaj_receptionat.getConversationId().equals("lista_celule"))
                    {
                        lista_celule.clear();
                        lista_celule.add(mesaj_receptionat.getContent());
                        System.out.println(lista_celule);
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
                Iterator it = getAID().getAllAddresses();
                String adresa = (String) it.next();
                String platforma = getAID().getName().split("@")[1];

                ACLMessage mesaj_ventilatie = new ACLMessage(ACLMessage.REQUEST);
                AID r = new AID("ventilatie@"+platforma, AID.ISGUID);
                r.addAddresses(adresa);
                mesaj_ventilatie.setConversationId("ventilatie");
                mesaj_ventilatie.addReceiver(r);
                mesaj_ventilatie.setContent(String.valueOf(ventilatie));
                myAgent.send(mesaj_ventilatie);

                ACLMessage mesaj_stropitori = new ACLMessage(ACLMessage.REQUEST);
                AID r2 = new AID("stropitori@"+platforma, AID.ISGUID);
                r2.addAddresses(adresa);
                mesaj_stropitori.setConversationId("stropitori");
                mesaj_stropitori.addReceiver(r2);
                mesaj_stropitori.setContent(String.valueOf(stropitori));
                myAgent.send(mesaj_stropitori);

                ACLMessage mesaj_lumini_urgenta = new ACLMessage(ACLMessage.REQUEST);
                AID r3 = new AID("lumini_urgenta@"+platforma, AID.ISGUID);
                r3.addAddresses(adresa);
                mesaj_lumini_urgenta.setConversationId("lumini_urgenta");
                mesaj_lumini_urgenta.addReceiver(r3);
                mesaj_lumini_urgenta.setContent(String.valueOf(lumini_urgenta));
                myAgent.send(mesaj_lumini_urgenta);

                ACLMessage mesaj_electricitate = new ACLMessage(ACLMessage.REQUEST);
                AID r4 = new AID("electricitate@"+platforma, AID.ISGUID);
                r4.addAddresses(adresa);
                mesaj_electricitate.setConversationId("electricitate");
                mesaj_electricitate.addReceiver(r4);
                mesaj_electricitate.setContent(String.valueOf(electricitate));
                myAgent.send(mesaj_electricitate);

                ACLMessage mesaj_umidificator = new ACLMessage(ACLMessage.REQUEST);
                AID r5 = new AID("umidificator@"+platforma, AID.ISGUID);
                r5.addAddresses(adresa);
                mesaj_umidificator.setConversationId("umidificator");
                mesaj_umidificator.addReceiver(r5);
                mesaj_umidificator.setContent(String.valueOf(umidificator));
                myAgent.send(mesaj_umidificator);

                ACLMessage mesaj_server = new ACLMessage(ACLMessage.REQUEST);
                AID rec = new AID("server@server", AID.ISGUID);
                rec.addAddresses(environment.adresa_server);
                mesaj_server.setConversationId("ospf");
                mesaj_server.addReceiver(rec);
                mesaj_server.setContent("test");
                myAgent.send(mesaj_server);

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

        addBehaviour(new Behaviour() {                     //
            @Override
            public void action() {
                if(fum) {
                    environment.alarma_incendiu = true;
                    ventilatie = 2;
                    electricitate = false;
                    lumini_urgenta = true;
                    stropitori = true;
                }
                else
                {
                    environment.alarma_incendiu = false;
                    stropitori = false;
                    electricitate = true;
                    lumini_urgenta = false;
                }
                if(environment.fum <= 0)
                    ventilatie = 1;


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

