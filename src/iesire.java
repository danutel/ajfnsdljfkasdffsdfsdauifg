import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;

public class iesire extends Agent{
    private int[] incarcare_etaj;
    private String nume = "iesire1";
    @Override
    public void setup(){
        incarcare_etaj = new int[6];
        ThreadedBehaviourFactory tbf = new ThreadedBehaviourFactory();

        Behaviour simulare = new Behaviour() {
            @Override
            public void action() {

            }

            @Override
            public boolean done() {
                return false;
            }
        };

        Behaviour receptionare = new Behaviour() {
            @Override
            public void action() {
                ACLMessage mesaj_receptionat = myAgent.receive();
                if(mesaj_receptionat!=null) {

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
        };

        Behaviour expediere = new Behaviour() {
            @Override
            public void action() {
                Iterator it = getAID().getAllAddresses();
                String adresa = (String) it.next();
                String platforma = getAID().getName().split("@")[1];
                String continut = "";

                for(int i=0; i<incarcare_etaj.length;i++)
                {
                    continut = continut+incarcare_etaj[i]+"~";
                }

                ACLMessage mesaj = new ACLMessage(ACLMessage.REQUEST);
                AID r = new AID("controller_hol@"+platforma, AID.ISGUID);
                r.addAddresses(adresa);
                mesaj.setConversationId(nume);
                mesaj.addReceiver(r);
                mesaj.setContent(continut);
                myAgent.send(mesaj);

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

        tbf.wrap(simulare);
        tbf.wrap(receptionare);
        tbf.wrap(expediere);
    }
}
