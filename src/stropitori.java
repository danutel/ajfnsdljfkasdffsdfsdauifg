import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class stropitori extends Agent {
    public boolean stropitori;
    @Override
    public void setup(){
        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage mesaj_receptionat = myAgent.receive();
                if(mesaj_receptionat!=null) {
                    myAgent.receive();
                    myAgent.receive();
                    if (mesaj_receptionat.getConversationId() == "stropitori") {
                        stropitori = Boolean.parseBoolean(mesaj_receptionat.getContent());
                        environment.sprinkler=stropitori;
                    }

                    if (mesaj_receptionat.getConversationId() == "stropitori[]") {
                        String[] ceva = mesaj_receptionat.getContent().split("~");
                        for(int i = 0;i<6;i++)
                        {
                            environment_hol.sprinkler[i]= Boolean.parseBoolean(ceva[i]);
                        }
                    }
                }
                try {
                    Thread.sleep(50);
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
