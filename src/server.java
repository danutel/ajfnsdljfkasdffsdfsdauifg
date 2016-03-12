import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;

import java.util.ArrayList;
import java.util.List;

public class server extends Agent{
    @Override
    public void setup(){
        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                int counter=0;
                List<String> lista_celule = new ArrayList<String>();

                while(counter<30) {
                    ACLMessage mesaj_receptionat = myAgent.receive();
                    if (mesaj_receptionat != null) {
                        if(mesaj_receptionat.getConversationId().equals("ospf"))
                        {
                            Iterator it = mesaj_receptionat.getSender().getAllAddresses();
                            String adresa = (String) it.next();
                            String client = mesaj_receptionat.getSender().getName() +"~"+ adresa;
                            if(lista_celule.contains(client)==false) {
                                lista_celule.add(client);
                            }

                        }
                    }
                    counter++;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for(int i=0;i<lista_celule.size();i++)
                {
                    ACLMessage reply_lista_clienti = new ACLMessage(ACLMessage.INFORM);
                    AID rec = new AID(lista_celule.get(i).split("~")[0], AID.ISGUID);
                    rec.addAddresses(lista_celule.get(i).split("~")[1]);
                    reply_lista_clienti.setConversationId("lista_celule");
                    reply_lista_clienti.addReceiver(rec);
                    reply_lista_clienti.setContent(lista_celule.toString());
                    myAgent.send(reply_lista_clienti);
                }

                //System.out.println(lista_celule);
            }

            @Override
            public boolean done() {
                return false;
            }
        });

    }
}
