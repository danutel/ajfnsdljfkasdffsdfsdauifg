import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;

public class senzorCO2 extends Agent{
    private float CO2;

    @Override
    public void setup(){

        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                CO2= (float) environment.CO2;
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
                ACLMessage mesaj_CO2 = new ACLMessage(ACLMessage.INFORM);
                Iterator it = getAID().getAllAddresses();
                AID r = new AID("controller@"+getAID().getName().split("@")[1], AID.ISGUID);
                r.addAddresses((String) it.next());
                mesaj_CO2.setConversationId("CO2");
                mesaj_CO2.addReceiver(r);
                mesaj_CO2.setContent(String.valueOf(CO2));
                myAgent.send(mesaj_CO2);
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
