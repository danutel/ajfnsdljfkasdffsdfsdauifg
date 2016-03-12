import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;

public class senzorFum extends Agent {
   private boolean fum;

   @Override
   public void setup() {
      addBehaviour(new Behaviour() {
         @Override
         public void action() {
            if (environment.fum > 20)
               fum = true;
            else
               fum = false;
             try {
                 Thread.sleep(500);
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
            ACLMessage mesaj_fum = new ACLMessage(ACLMessage.INFORM);
            Iterator it = getAID().getAllAddresses();
            AID r = new AID("controller@"+getAID().getName().split("@")[1], AID.ISGUID);
            r.addAddresses((String) it.next());
            mesaj_fum.setConversationId("fum");
            mesaj_fum.addReceiver(r);
            mesaj_fum.setContent(String.valueOf(fum));myAgent.send(mesaj_fum);
             try {
                 Thread.sleep(500);
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
