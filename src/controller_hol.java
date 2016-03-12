import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.List;

public class controller_hol extends Agent{
    public boolean[] fum=new boolean[6];
    public double[] ventilatie=new double[6];
    public boolean[] stropitori=new boolean[6];
    public boolean[] lumini_urgenta=new boolean[6];
    public boolean[] electricitate=new boolean[6];

    public static List<String> lista_celule = new ArrayList<>();
    @Override
    public void setup(){
        for(int i=0;i<6;i++)
        {
            electricitate[i]=true;
        }
        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage mesaj_receptionat = myAgent.receive();
                if(mesaj_receptionat!=null)
                {
                    if(mesaj_receptionat.getConversationId()=="fum[]")
                    {
                        myAgent.receive();
                        fum[0] = Boolean.parseBoolean(mesaj_receptionat.getContent().split("~")[0]);
                        fum[1] = Boolean.parseBoolean(mesaj_receptionat.getContent().split("~")[1]);
                        fum[2] = Boolean.parseBoolean(mesaj_receptionat.getContent().split("~")[2]);
                        fum[3] = Boolean.parseBoolean(mesaj_receptionat.getContent().split("~")[3]);
                        fum[4] = Boolean.parseBoolean(mesaj_receptionat.getContent().split("~")[4]);
                        fum[5] = Boolean.parseBoolean(mesaj_receptionat.getContent().split("~")[5]);
                    }
                   /* if(mesaj_receptionat.getConversationId().equals("lista_celule"))
                    {
                        lista_celule.clear();
                        lista_celule.add(mesaj_receptionat.getContent());
                    }*/
                }
                try {
                    Thread.sleep(100);
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

                String vv="";
                String vs="";
                String ve="";
                String vl="";
                for(int i=0;i<6;i++) {
                    vv = vv+ ventilatie[i]+"~";
                    vs = vs+ stropitori[i]+"~";
                    ve = ve+ electricitate[i]+"~";
                    vl = vl+ lumini_urgenta[i]+"~";
                }

                ACLMessage mesaj_ventilatie = new ACLMessage(ACLMessage.REQUEST);
                AID r = new AID("ventilatie@"+platforma, AID.ISGUID);
                r.addAddresses(adresa);
                mesaj_ventilatie.setConversationId("ventilatie[]");
                mesaj_ventilatie.addReceiver(r);
                mesaj_ventilatie.setContent(vv);
                myAgent.send(mesaj_ventilatie);

                ACLMessage mesaj_stropitori = new ACLMessage(ACLMessage.REQUEST);
                AID r2 = new AID("stropitori@"+platforma, AID.ISGUID);
                r2.addAddresses(adresa);
                mesaj_stropitori.setConversationId("stropitori[]");
                mesaj_stropitori.addReceiver(r2);
                mesaj_stropitori.setContent(vs);
                myAgent.send(mesaj_stropitori);

                ACLMessage mesaj_lumini_urgenta = new ACLMessage(ACLMessage.REQUEST);
                AID r3 = new AID("lumini_urgenta@"+platforma, AID.ISGUID);
                r3.addAddresses(adresa);
                mesaj_lumini_urgenta.setConversationId("lumini_urgenta[]");
                mesaj_lumini_urgenta.addReceiver(r3);
                mesaj_lumini_urgenta.setContent(vl);
                myAgent.send(mesaj_lumini_urgenta);

                ACLMessage mesaj_electricitate = new ACLMessage(ACLMessage.REQUEST);
                AID r4 = new AID("electricitate@"+platforma, AID.ISGUID);
                r4.addAddresses(adresa);
                mesaj_electricitate.setConversationId("electricitate[]");
                mesaj_electricitate.addReceiver(r4);
                mesaj_electricitate.setContent(ve);
                myAgent.send(mesaj_electricitate);
              /*  ACLMessage mesaj_server = new ACLMessage(ACLMessage.REQUEST);
                AID rec = new AID("server@server", AID.ISGUID);
                rec.addAddresses(environment.adresa_server);
                mesaj_server.setConversationId("ospf");
                mesaj_server.addReceiver(rec);
                mesaj_server.setContent("test");
                myAgent.send(mesaj_server);*/

                try {
                    Thread.sleep(100);
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
                for(int i=0; i<6;i++)
                {
                    if(fum[i]) {
                        environment.alarma_incendiu = true;
                        ventilatie[i] = 2;
                        electricitate[i] = false;
                        lumini_urgenta [i]= true;
                        stropitori[i] = true;
                    }
                    else
                    {
                        environment.alarma_incendiu = false;
                        stropitori[i] = false;
                        electricitate[i] = true;
                        lumini_urgenta[i]= false;
                    }
                    if(environment_hol.fum[i] <= 0)
                        ventilatie[i] = 1;
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
