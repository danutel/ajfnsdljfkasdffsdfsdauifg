import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class serverBehaviour extends CyclicBehaviour
    {
        public List<String> lista_agenti = new ArrayList<String>();

        public void action()
        {
            ACLMessage msg = myAgent.receive();
            if(msg != null)
            {
                if(msg.getPerformative()== ACLMessage.REQUEST)
                {
                    String content = msg.getContent();
                    String nume = msg.getSender().getName();
                    String adresa = msg.getSender().toString().split("sequence ")[1].split(" ")[0];
                    if ((content.equals("register request") == true))
                    {
                        System.out.println("Message received from "+ nume +" "+ adresa +" : "+ content);
                        boolean alreadyRegistered = false;

                        Iterator iterator = lista_agenti.iterator();
                        while(iterator.hasNext()){
                            String element = (String) iterator.next();
                            if(element.equals(nume +" "+ adresa))
                            {
                                alreadyRegistered = true;
                            }
                        }

                        if(alreadyRegistered == false) {
                            lista_agenti.add(nume + " " + adresa);
                        }

                        ACLMessage mesaj_trimis = new ACLMessage(ACLMessage.CONFIRM);
                        AID receiver = new AID(nume,AID.ISGUID);
                        receiver.addAddresses(adresa);
                        mesaj_trimis.addReceiver(receiver);
                        mesaj_trimis.setContent("register acknowledge" );
                        myAgent.send(mesaj_trimis);
                        System.out.println("Message sent to"+ " "+nume+" "+adresa+" "+mesaj_trimis.getContent());
                    }
                    else if ((content.equals("agents list request") == true))
                    {
                        System.out.println("Message received from "+ nume +" "+ adresa +" : "+ content+"\n");
                        ACLMessage mesaj_trimis = new ACLMessage(ACLMessage.SUBSCRIBE);
                        AID receiver = new AID(nume,AID.ISGUID);
                        receiver.addAddresses(adresa);
                        mesaj_trimis.addReceiver(receiver);
                        mesaj_trimis.setContent(lista_agenti.toString());
                        myAgent.send(mesaj_trimis);
                    }
                    else
                    {
                        block();
                    }
                }
            }
            else
            {
                block();
            }
        }
    }
