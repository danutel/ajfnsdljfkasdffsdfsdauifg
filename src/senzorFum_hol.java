import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;

public class senzorFum_hol extends Agent {


    private boolean fum[]=new boolean[6];

    @Override
    public void setup() {
        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                for(int i=0;i<6;i++) {
                    if (environment_hol.fum[i] > 20)
                        fum[i] = true;
                    else
                        fum[i] = false;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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

                String text="";
                for(int i=0;i<6;i++) {
                    text = text+ fum[i]+"~";
                }

                ACLMessage mesaj_fum = new ACLMessage(ACLMessage.INFORM);
                Iterator it = getAID().getAllAddresses();
                AID r = new AID("controller_hol@" + getAID().getName().split("@")[1], AID.ISGUID);
                r.addAddresses((String) it.next());
                mesaj_fum.setConversationId("fum[]");
                mesaj_fum.addReceiver(r);
                mesaj_fum.setContent(String.valueOf(text));
                myAgent.send(mesaj_fum);
                try {
                    Thread.sleep(300);
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

