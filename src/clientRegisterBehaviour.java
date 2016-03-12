import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class clientRegisterBehaviour extends Behaviour
{
    private boolean finished = false;
    public static String server_name;
    public static String server_address;
    @Override
    public void action() {

        if (server_name == null)
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Introduceti numele serverului @ platforma: ");
            try {
                server_name = (String) br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.print("\nIntroduceti adresa serverului: ");
            try {
                server_address = (String) br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ACLMessage mesaj_primit = myAgent.receive();

        if(mesaj_primit == null) {
            ACLMessage acl = new ACLMessage(ACLMessage.REQUEST);
            AID r = new AID(server_name, AID.ISGUID);
            r.addAddresses(server_address);
            acl.addReceiver(r);
            acl.setContent("register request");
            myAgent.send(acl);
            System.out.println(myAgent.getAID().getLocalName()+" sent to " + server_name+" "+server_address+" : "+acl.getContent());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if (mesaj_primit.getPerformative()== ACLMessage.CONFIRM)
        {
            String content = mesaj_primit.getContent();
            String nume = mesaj_primit.getSender().getName();
            String adresa = mesaj_primit.getSender().toString().split("sequence ")[1].split(" ")[0];

            if(content.equals("register acknowledge") || content.equals("already registered")) {
                System.out.println(myAgent.getLocalName()+" received from "+nume+" "+adresa+" : "+content);
                ACLMessage acl = new ACLMessage(ACLMessage.REQUEST);
                AID r = new AID(server_name, AID.ISGUID);
                r.addAddresses(server_address);
                acl.addReceiver(r);
                acl.setContent("agents list request");
                myAgent.send(acl);
                System.out.println(myAgent.getAID().getLocalName()+" sent to " + server_name+" "+server_address+" :"+acl.getContent());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
        else if (mesaj_primit.getPerformative()==ACLMessage.SUBSCRIBE)
        {
            String content = mesaj_primit.getContent();
            String nume = mesaj_primit.getSender().getName();
            String adresa = mesaj_primit.getSender().toString().split("sequence ")[1].split(" ")[0];

            System.out.println(myAgent.getLocalName()+" received from "+nume+" "+adresa+" : "+content+"\n");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else
        {
            block();
        }
    }

    @Override
    public boolean done() {
        return finished;
    }

}
