import com.jme3.math.ColorRGBA;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;

import java.util.ArrayList;
import java.util.List;

public class controller_hol extends Agent{
    public boolean[] fum=new boolean[6];
    public double[] ventilatie=new double[6];
    public boolean[] stropitori=new boolean[6];
    public boolean[] lumini_urgenta=new boolean[6];
    public boolean[] electricitate=new boolean[6];
    private node [] sectoare = new node[6];
    public static boolean A_X_activated = false;
    public static boolean X_Y2_activated = false;
    public static boolean X_Y1_activated = false;
    public static boolean B_X_activated = false;
    public static boolean Y2_X_activated = false;

    public static List<String> lista_celule = new ArrayList<>();
    @Override
    public void setup(){

        sectoare[1] = new node(17,30);
        sectoare[2] = new node(27,40);
        sectoare[3] = new node(22,5);
        sectoare[4] = new node(0,27);
        sectoare[5] = new node(27,0);

        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage mesaj_receptionat = myAgent.receive();
                if(mesaj_receptionat!=null)
                {
                    if(mesaj_receptionat.getConversationId()=="fum[]")
                    {
                        myAgent.receive();
                        fum[0] = Boolean.parseBoolean(mesaj_receptionat.getContent().split("~")[0]);
                        fum[1] = Boolean.parseBoolean(mesaj_receptionat.getContent().split("~")[1]);
                        fum[2] = Boolean.parseBoolean(mesaj_receptionat.getContent().split("~")[2]);
                        fum[3] = Boolean.parseBoolean(mesaj_receptionat.getContent().split("~")[3]);
                        fum[4] = Boolean.parseBoolean(mesaj_receptionat.getContent().split("~")[4]);
                        fum[5] = Boolean.parseBoolean(mesaj_receptionat.getContent().split("~")[5]);
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


        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                Iterator it = getAID().getAllAddresses();
                String adresa = (String) it.next();
                String platforma = getAID().getName().split("@")[1];

                String vv="";
                String vs="";
                String ve="";
                String vl="";
                for(int i=0;i<6;i++) {
                    vv = vv+ ventilatie[i]+"~";
                    vs = vs+ stropitori[i]+"~";
                    ve = ve+ electricitate[i]+"~";
                    vl = vl+ lumini_urgenta[i]+"~";
                }

                ACLMessage mesaj_ventilatie = new ACLMessage(ACLMessage.REQUEST);
                AID r = new AID("ventilatie@"+platforma, AID.ISGUID);
                r.addAddresses(adresa);
                mesaj_ventilatie.setConversationId("ventilatie[]");
                mesaj_ventilatie.addReceiver(r);
                mesaj_ventilatie.setContent(vv);
                myAgent.send(mesaj_ventilatie);

                ACLMessage mesaj_stropitori = new ACLMessage(ACLMessage.REQUEST);
                AID r2 = new AID("stropitori@"+platforma, AID.ISGUID);
                r2.addAddresses(adresa);
                mesaj_stropitori.setConversationId("stropitori[]");
                mesaj_stropitori.addReceiver(r2);
                mesaj_stropitori.setContent(vs);
                myAgent.send(mesaj_stropitori);

                ACLMessage mesaj_lumini_urgenta = new ACLMessage(ACLMessage.REQUEST);
                AID r3 = new AID("lumini_urgenta@"+platforma, AID.ISGUID);
                r3.addAddresses(adresa);
                mesaj_lumini_urgenta.setConversationId("lumini_urgenta[]");
                mesaj_lumini_urgenta.addReceiver(r3);
                mesaj_lumini_urgenta.setContent(vl);
                myAgent.send(mesaj_lumini_urgenta);

                ACLMessage mesaj_electricitate = new ACLMessage(ACLMessage.REQUEST);
                AID r4 = new AID("electricitate@"+platforma, AID.ISGUID);
                r4.addAddresses(adresa);
                mesaj_electricitate.setConversationId("electricitate[]");
                mesaj_electricitate.addReceiver(r4);
                mesaj_electricitate.setContent(ve);
                myAgent.send(mesaj_electricitate);

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

        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                for(int i=0; i<6;i++)
                {
                    if(fum[i]) {
                        environment.alarma_incendiu = true;
                        ventilatie[i] = 2;
                        electricitate[i] = false;
                        lumini_urgenta [i]= true;
                        stropitori[i] = true;
                    }
                    else
                    {
                        environment.alarma_incendiu = false;
                        stropitori[i] = false;
                        electricitate[i] = true;
                        lumini_urgenta[i]= false;
                    }
                    if(environment_hol.fum[i] <= 0)
                        ventilatie[i] = 1;
                }

                //if((environment_hol.ocupare_scari2/environment_hol.ocupare_scari1)<0.1)
                if (true && !X_Y1_activated)
                {
                    //toti ies pe iesirea 2
                    directionare.culoareA_X = ColorRGBA.Red;
                    directionare.culoareB_X = ColorRGBA.Red;
                    directionare.culoareC_X = ColorRGBA.Red;
                    directionare.culoareX_Y = ColorRGBA.Red;
                    X_Y1_activated = true;
                    A_X_activated = true;
                    B_X_activated = true;
                    Y2_X_activated = true;
                }

                else if((environment_hol.ocupare_scari2/environment_hol.ocupare_scari1)>90)
                {
                    //toti ies pe iesirea 1
                }

                else if((environment_hol.ocupare_scari2/environment_hol.ocupare_scari1)>=1 && (environment_hol.ocupare_scari2/environment_hol.ocupare_scari1)<=90)
                {
                    //A-1 B-1 C-2
                }

                else
                {
                    //A-1 B-2 C-2
                }

                try {
                    Thread.sleep(200);
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
