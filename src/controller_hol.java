import com.jme3.math.ColorRGBA;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;

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
    private boolean mod1,mod2,mod3,mod4;
    private int[] incarcare_iesire1 = new int[6],incarcare_iesire2=new int[6];
    public static int etaj = 4;

    @Override
    public void setup(){

        sectoare[1] = new node(17,30);
        sectoare[2] = new node(27,40);
        sectoare[3] = new node(22,5);
        sectoare[4] = new node(0,27);
        sectoare[5] = new node(27,0);

        ThreadedBehaviourFactory tbf = new ThreadedBehaviourFactory();

        Behaviour primire = new Behaviour() {
            @Override
            public void action() {
                ACLMessage mesaj_receptionat = myAgent.receive();
                if(mesaj_receptionat!=null)
                {
                    if(mesaj_receptionat.getConversationId()=="fum[]")
                    {
                        fum[0] = Boolean.parseBoolean(mesaj_receptionat.getContent().split("~")[0]);
                        fum[1] = Boolean.parseBoolean(mesaj_receptionat.getContent().split("~")[1]);
                        fum[2] = Boolean.parseBoolean(mesaj_receptionat.getContent().split("~")[2]);
                        fum[3] = Boolean.parseBoolean(mesaj_receptionat.getContent().split("~")[3]);
                        fum[4] = Boolean.parseBoolean(mesaj_receptionat.getContent().split("~")[4]);
                        fum[5] = Boolean.parseBoolean(mesaj_receptionat.getContent().split("~")[5]);
                    }
                    else if (mesaj_receptionat.getConversationId()=="iesire1")
                    {
                        try {
                            incarcare_iesire1[0] = Integer.parseInt(mesaj_receptionat.getContent().split("~")[0]);
                            incarcare_iesire1[1] = Integer.parseInt(mesaj_receptionat.getContent().split("~")[1]);
                            incarcare_iesire1[2] = Integer.parseInt(mesaj_receptionat.getContent().split("~")[2]);
                            incarcare_iesire1[3] = Integer.parseInt(mesaj_receptionat.getContent().split("~")[3]);
                            incarcare_iesire1[4] = Integer.parseInt(mesaj_receptionat.getContent().split("~")[4]);
                            incarcare_iesire1[5] = Integer.parseInt(mesaj_receptionat.getContent().split("~")[5]);
                        }catch(Exception e)
                        {}
                    }
                    else if (mesaj_receptionat.getConversationId()=="iesire2")
                    {
                        incarcare_iesire2[0] = Integer.parseInt(mesaj_receptionat.getContent().split("~")[0]);
                        incarcare_iesire2[1] = Integer.parseInt(mesaj_receptionat.getContent().split("~")[1]);
                        incarcare_iesire2[2] = Integer.parseInt(mesaj_receptionat.getContent().split("~")[2]);
                        incarcare_iesire2[3] = Integer.parseInt(mesaj_receptionat.getContent().split("~")[3]);
                        incarcare_iesire2[4] = Integer.parseInt(mesaj_receptionat.getContent().split("~")[4]);
                        incarcare_iesire2[5] = Integer.parseInt(mesaj_receptionat.getContent().split("~")[5]);
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
        };
       addBehaviour(tbf.wrap(primire));

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
                int c= 0;
                for(int i=0; i<6;i++)
                {
                    if(fum[i]) {
                        ventilatie[i] = 2;
                        electricitate[i] = false;
                        lumini_urgenta [i]= true;
                        stropitori[i] = true;
                        c++;
                    }
                    else
                    {
                        stropitori[i] = false;
                        electricitate[i] = true;
                        lumini_urgenta[i]= false;
                    }
                    if(environment_hol.fum[i] <= 0)
                        ventilatie[i] = 1;
                }

                if(c!=0)
                    environment_hol.alarma_incendiu =true;

                //double raport  = incarcare_iesire2[etaj]/incarcare_iesire1[etaj];
                double raport = 15;

                if(environment_hol.alarma_incendiu) {

                    if(raport<=0.1 && !mod1 || fum[5])
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
                        mod1 = true;
                        environment_hol.nr_oameni_iesire2 = environment_hol.nr_oameni_iesire2+6;
                        graphicEngine.nr_oameni_setor_A=graphicEngine.nr_oameni_setor_A-2;
                        graphicEngine.nr_oameni_setor_B=graphicEngine.nr_oameni_setor_B-2;
                        graphicEngine.nr_oameni_setor_C=graphicEngine.nr_oameni_setor_C-2;
                    } else if (raport > 90 && !mod2 || fum[5]) {
                        //toti ies pe iesirea 1
                        directionare.culoareA_X = ColorRGBA.Red;
                        directionare.culoareB_X = ColorRGBA.Red;
                        directionare.culoareC_X = ColorRGBA.Red;
                        directionare.culoareX_Y = ColorRGBA.Black;
                        X_Y1_activated = true;
                        A_X_activated = true;
                        B_X_activated = true;
                        X_Y2_activated = true;
                        mod2 = true;
                        environment_hol.nr_oameni_iesire1 = environment_hol.nr_oameni_iesire1+6;
                        graphicEngine.nr_oameni_setor_A=graphicEngine.nr_oameni_setor_A-2;
                        graphicEngine.nr_oameni_setor_B=graphicEngine.nr_oameni_setor_B-2;
                        graphicEngine.nr_oameni_setor_C=graphicEngine.nr_oameni_setor_C-2;
                    } else if (raport >= 1 && raport <= 90 && !mod3) {
                        //A-1 B-1 C-2
                        directionare.culoareA_X = ColorRGBA.Blue;
                        directionare.culoareB_X = ColorRGBA.Blue;
                        directionare.culoareC_X = ColorRGBA.Red;
                        directionare.culoareX_Y = ColorRGBA.Blue;
                        X_Y1_activated = true;
                        A_X_activated = true;
                        B_X_activated = true;
                        Y2_X_activated = true;
                        mod3 = true;
                        environment_hol.nr_oameni_iesire2 = environment_hol.nr_oameni_iesire2+2;
                        environment_hol.nr_oameni_iesire1 = environment_hol.nr_oameni_iesire1+4;
                        graphicEngine.nr_oameni_setor_A=graphicEngine.nr_oameni_setor_A-2;
                        graphicEngine.nr_oameni_setor_B=graphicEngine.nr_oameni_setor_B-2;
                        graphicEngine.nr_oameni_setor_C=graphicEngine.nr_oameni_setor_C-2;
                    } else if(raport < 1 && raport>=0.1 && !mod4){
                        //A-1 B-2 C-2
                        directionare.culoareA_X = ColorRGBA.Blue;
                        directionare.culoareB_X = ColorRGBA.Red;
                        directionare.culoareC_X = ColorRGBA.Red;
                        directionare.culoareX_Y = ColorRGBA.Blue;
                        X_Y1_activated = true;
                        A_X_activated = true;
                        B_X_activated = true;
                        Y2_X_activated = true;
                        mod4 = true;
                        environment_hol.nr_oameni_iesire2 = environment_hol.nr_oameni_iesire2+4;
                        environment_hol.nr_oameni_iesire1 = environment_hol.nr_oameni_iesire1+2;
                        graphicEngine.nr_oameni_setor_A=graphicEngine.nr_oameni_setor_A-2;
                        graphicEngine.nr_oameni_setor_B=graphicEngine.nr_oameni_setor_B-2;
                        graphicEngine.nr_oameni_setor_C=graphicEngine.nr_oameni_setor_C-2;
                    }
                }
                else
                {
                    X_Y1_activated = false;
                    A_X_activated = false;
                    B_X_activated = false;
                    Y2_X_activated = false;
                    X_Y2_activated = false;
                    mod1=false;
                    mod2=false;
                    mod3=false;
                    mod4=false;
                }

                //System.out.println("Iesire 1: "+environment_hol.ocupare_scari1+" Iesire 2: "+environment_hol.ocupare_scari2);
                //System.out.println(mod1+" "+mod2+" "+mod3+" "+mod4);
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
