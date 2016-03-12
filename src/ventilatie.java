import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import org.lwjgl.Sys;

public class ventilatie extends Agent {
    public double ventilatie;
    @Override
    public void setup(){
        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage mesaj_receptionat = myAgent.receive();
                if(mesaj_receptionat!=null) {
                    if (mesaj_receptionat.getConversationId() == "ventilatie") {
                        ventilatie = Double.parseDouble(mesaj_receptionat.getContent());
                        environment.ventilatie = ventilatie;
                    }

                    if (mesaj_receptionat.getConversationId() == "ventilatie[]") {
                        String[] ceva = mesaj_receptionat.getContent().split("~");
                        for(int i = 0;i<6;i++)
                        {
                            environment_hol.ventilatie[i]= Double.parseDouble(ceva[i]);
                        }
                    }
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
    }
}
