import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;

public class iesire extends Agent{
    private double[] incarcare_etaj = new double[6];
    private double[] nr_oameni_etaj = new double[6];
    private double nr_oameni_plecati;
    private double[] nr_oameni_coada = new double[6];
    private String nume = "iesire1";
    @Override
    public void setup(){
        ThreadedBehaviourFactory tbf = new ThreadedBehaviourFactory();

        Behaviour simulare = new Behaviour() {
            @Override
            public void action() {
                for(int i =5;i>=0;i--)
                {
                    if(incarcare_etaj[i]<1)
                    {
                        incarcare_etaj[i]=1;
                    }
                    nr_oameni_coada[i] = nr_oameni_coada[i]+nr_oameni_etaj[i]+nr_oameni_plecati-(3/incarcare_etaj[i]);
                    nr_oameni_plecati = (0.1/incarcare_etaj[i]);
                    incarcare_etaj[i] = nr_oameni_coada[i]/50;
                    nr_oameni_etaj[i]=0;
                    if(incarcare_etaj[i]<1)
                    {
                        incarcare_etaj[i]=1;
                    }
                    if(nr_oameni_coada[i]<1)
                    {
                        nr_oameni_coada[i]=0;
                    }

                }
                System.out.println(nr_oameni_coada[4]);
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

        Behaviour receptionare = new Behaviour() {
            @Override
            public void action() {
                ACLMessage mesaj_receptionat = myAgent.receive();
                if(mesaj_receptionat!=null) {
                    String[] nr_oameni = mesaj_receptionat.getContent().toString().split("~");
                    nr_oameni_etaj[Integer.parseInt(nr_oameni[0])] = Double.parseDouble(nr_oameni[1]);
                    //System.out.println(nr_oameni_etaj[4]);
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
                String continut = "";

                for(int i=0; i<incarcare_etaj.length;i++)
                {
                    continut = continut+incarcare_etaj[i]+"~";
                }

                if(nume.equals("iesire1")) {
                    ACLMessage mesaj_iesire = new ACLMessage(ACLMessage.INFORM);
                    Iterator it = getAID().getAllAddresses();
                    AID r = new AID("controller_hol@" + getAID().getName().split("@")[1], AID.ISGUID);
                    r.addAddresses((String) it.next());
                    mesaj_iesire.setConversationId("iesire1");
                    mesaj_iesire.addReceiver(r);
                    mesaj_iesire.setContent(continut);
                    myAgent.send(mesaj_iesire);
                   // System.out.println(mesaj_iesire);
                }
                else {
                    ACLMessage mesaj_iesire = new ACLMessage(ACLMessage.INFORM);
                    Iterator it = getAID().getAllAddresses();
                    AID r = new AID("controller_hol@" + getAID().getName().split("@")[1], AID.ISGUID);
                    r.addAddresses((String) it.next());
                    mesaj_iesire.setConversationId("iesire2");
                    mesaj_iesire.addReceiver(r);
                    mesaj_iesire.setContent(continut);
                    myAgent.send(mesaj_iesire);
                }

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


        addBehaviour(tbf.wrap(simulare));
        addBehaviour(tbf.wrap(receptionare));
        addBehaviour(tbf.wrap(expediere));
    }
}
