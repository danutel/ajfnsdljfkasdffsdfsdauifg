import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;

public class senzorTemperatura extends Agent{
    private float temperatura;

    @Override
    public void setup(){

        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                temperatura= (float) environment.temperatura;
                try {
                    Thread.sleep(2000);
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
                ACLMessage mesaj_temperatura = new ACLMessage(ACLMessage.INFORM);
                Iterator it = getAID().getAllAddresses();
                AID r = new AID("controller@"+getAID().getName().split("@")[1], AID.ISGUID);
                r.addAddresses((String) it.next());
                mesaj_temperatura.setConversationId("temperatura");
                mesaj_temperatura.addReceiver(r);
                mesaj_temperatura.setContent(String.valueOf(temperatura));
                myAgent.send(mesaj_temperatura);
                try {
                    Thread.sleep(2000);
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
