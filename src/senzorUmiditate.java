import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;

public class senzorUmiditate extends Agent {
    private double umiditate = 0;

    @Override
    public void setup(){

        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                umiditate = environment.umiditate;
                try {
                    Thread.sleep(5000);
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
                ACLMessage mesaj_umiditate = new ACLMessage(ACLMessage.INFORM);
                Iterator it = getAID().getAllAddresses();
                AID r = new AID("controller@"+getAID().getName().split("@")[1], AID.ISGUID);
                r.addAddresses((String) it.next());
                mesaj_umiditate.setConversationId("umiditate");
                mesaj_umiditate.addReceiver(r);
                mesaj_umiditate.setContent(String.valueOf(umiditate));
                myAgent.send(mesaj_umiditate);
                try {
                    Thread.sleep(5000);
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
