import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class electricitate extends Agent {
    public boolean electricitate;
    @Override
    public void setup(){
        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage mesaj_receptionat = myAgent.receive();
                if(mesaj_receptionat!=null) {
                    myAgent.receive();
                    if (mesaj_receptionat.getConversationId() == "electricitate") {
                        electricitate = Boolean.parseBoolean(mesaj_receptionat.getContent());
                        environment.curent_electric=electricitate;
                    }

                    if (mesaj_receptionat.getConversationId() == "electricitate[]") {
                        String[] ceva = mesaj_receptionat.getContent().split("~");
                        for(int i = 0;i<6;i++)
                        {
                            environment_hol.curent_electric[i]= Boolean.parseBoolean(ceva[i]);
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
