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
                        //System.out.println("iesire1:online ");
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
                        try {
                            //System.out.println("iesire2:online ");
                            incarcare_iesire2[0] = Integer.parseInt(mesaj_receptionat.getContent().split("~")[0]);
                            incarcare_iesire2[1] = Integer.parseInt(mesaj_receptionat.getContent().split("~")[1]);
                            incarcare_iesire2[2] = Integer.parseInt(mesaj_receptionat.getContent().split("~")[2]);
                            incarcare_iesire2[3] = Integer.parseInt(mesaj_receptionat.getContent().split("~")[3]);
                            incarcare_iesire2[4] = Integer.parseInt(mesaj_receptionat.getContent().split("~")[4]);
                            incarcare_iesire2[5] = Integer.parseInt(mesaj_receptionat.getContent().split("~")[5]);
                        }catch(Exception e){

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

                ACLMessage mesaj_iesire1 = new ACLMessage(ACLMessage.REQUEST);
                AID r5 = new AID("nucleu@"+platforma, AID.ISGUID);
                r4.addAddresses(adresa);
                mesaj_iesire1.setConversationId("oameni_iesire1");
                mesaj_iesire1.addReceiver(r5);
                mesaj_iesire1.setContent(etaj+"~"+String.valueOf(environment_hol.nr_oameni_iesire1));
                myAgent.send(mesaj_iesire1);

                ACLMessage mesaj_iesire2 = new ACLMessage(ACLMessage.REQUEST);
                AID r6 = new AID("nucleu@"+platforma, AID.ISGUID);
                r4.addAddresses(adresa);
                mesaj_iesire2.setConversationId("oameni_iesire2");
                mesaj_iesire2.addReceiver(r6);
                mesaj_iesire2.setContent(etaj+"~"+String.valueOf(environment_hol.nr_oameni_iesire2));
                myAgent.send(mesaj_iesire2);

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
                int c = 0;
                for (int i = 0; i < 6; i++) {
                    if (fum[i]) {
                        ventilatie[i] = 2;
                        electricitate[i] = false;
                        lumini_urgenta[i] = true;
                        stropitori[i] = true;
                        c++;
                    } else {
                        stropitori[i] = false;
                        electricitate[i] = true;
                        lumini_urgenta[i] = false;
                    }
                    if (environment_hol.fum[i] <= 0)
                        ventilatie[i] = 1;
                }

                if (c != 0)
                    environment_hol.alarma_incendiu = true;

                double raport = 1;
                try {
                    raport = incarcare_iesire2[etaj] / incarcare_iesire1[etaj];
                }catch (Exception e)
                {

                }

               /* File log = new File("log.txt");

                try{
                    if(!log.exists()){
                        System.out.println("We had to make a new file.");
                        log.createNewFile();
                    }

                    FileWriter fileWriter = new FileWriter(log, true);

                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write("Iesire 1: "+incarcare_iesire1[etaj]+"; Iesire 2: "+
                            incarcare_iesire2[etaj]+"; Mod 1: "+ mod1+"; Mod 2: "+mod2+"; Mod 3: "+mod3+"; Mod 4: "+mod4+"\n");
                    bufferedWriter.close();

                } catch(IOException e) {
                    System.out.println("COULD NOT LOG!!");
                }*/

                System.out.println("Iesire 1: "+incarcare_iesire1[etaj]+"; Iesire 2: "+
                        incarcare_iesire2[etaj]+"; Mod 1: "+ mod1+"; Mod 2: "+mod2+"; Mod 3: "+mod3+"; Mod 4: "+mod4);

                if (environment_hol.alarma_incendiu) {

                    if (raport <= 0.2 && !mod1 || incarcare_iesire1[etaj]>90) {
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
                        mod2 = false;
                        mod3 = false;
                        mod4 = false;
                    } else if (raport > 5 && !mod2 || incarcare_iesire2[etaj]>90) {
                        //toti ies pe iesirea 1
                        directionare.culoareA_X = ColorRGBA.Red;
                        directionare.culoareB_X = ColorRGBA.Red;
                        directionare.culoareC_X = ColorRGBA.Red;
                        directionare.culoareX_Y = ColorRGBA.Black;
                        X_Y1_activated = true;
                        A_X_activated = true;
                        B_X_activated = true;
                        X_Y2_activated = true;
                        mod1 = false;
                        mod2 = true;
                        mod3 = false;
                        mod4 = false;
                    } else if (raport >= 1 && raport <= 5 && !mod3) {
                        //A-1 B-1 C-2
                        directionare.culoareA_X = ColorRGBA.Blue;
                        directionare.culoareB_X = ColorRGBA.Blue;
                        directionare.culoareC_X = ColorRGBA.Red;
                        directionare.culoareX_Y = ColorRGBA.Blue;
                        X_Y1_activated = true;
                        A_X_activated = true;
                        B_X_activated = true;
                        X_Y2_activated = true;
                        mod1 = false;
                        mod2 = false;
                        mod3 = true;
                        mod4 = false;
                    } else if (raport < 1 && raport >= 0.2 && !mod4) {
                        //A-1 B-2 C-2
                        directionare.culoareA_X = ColorRGBA.Blue;
                        directionare.culoareB_X = ColorRGBA.Red;
                        directionare.culoareC_X = ColorRGBA.Red;
                        directionare.culoareX_Y = ColorRGBA.Blue;
                        X_Y1_activated = true;
                        A_X_activated = true;
                        B_X_activated = true;
                        Y2_X_activated = true;
                        mod1 = false;
                        mod2 = false;
                        mod3 = false;
                        mod4 = true;
                    }

                    if (mod1) {
                        environment_hol.nr_oameni_iesire2 = 0;
                        environment_hol.nr_oameni_iesire1 = 0;
                        if (graphicEngine.nr_oameni_setor_A > 4) {
                            graphicEngine.nr_oameni_setor_A = graphicEngine.nr_oameni_setor_A - 4;
                            environment_hol.nr_oameni_iesire2 += 4;
                        }
                        if (graphicEngine.nr_oameni_setor_B > 4) {
                            graphicEngine.nr_oameni_setor_B = graphicEngine.nr_oameni_setor_B - 4;
                            environment_hol.nr_oameni_iesire2 += 4;
                        }
                        if (graphicEngine.nr_oameni_setor_C > 4) {
                            graphicEngine.nr_oameni_setor_C = graphicEngine.nr_oameni_setor_C - 4;
                            environment_hol.nr_oameni_iesire2 += 4;
                        }
                    } else if (mod2) {
                        environment_hol.nr_oameni_iesire2 = 0;
                        environment_hol.nr_oameni_iesire1 = 0;
                        if (graphicEngine.nr_oameni_setor_A > 4) {
                            graphicEngine.nr_oameni_setor_A = graphicEngine.nr_oameni_setor_A - 4;
                            environment_hol.nr_oameni_iesire1 += 4;
                        }
                        if (graphicEngine.nr_oameni_setor_B > 4) {
                            graphicEngine.nr_oameni_setor_B = graphicEngine.nr_oameni_setor_B - 4;
                            environment_hol.nr_oameni_iesire1 += 4;
                        }
                        if (graphicEngine.nr_oameni_setor_C > 4) {
                            graphicEngine.nr_oameni_setor_C = graphicEngine.nr_oameni_setor_C - 4;
                            environment_hol.nr_oameni_iesire1 += 4;
                        }
                    } else if (mod3) {

                        environment_hol.nr_oameni_iesire2 = 0;
                        environment_hol.nr_oameni_iesire1 = 0;
                        if (graphicEngine.nr_oameni_setor_A >= 4) {
                            graphicEngine.nr_oameni_setor_A -= 4;
                            environment_hol.nr_oameni_iesire1 += 4;
                        }
                        if (graphicEngine.nr_oameni_setor_B >= 4) {
                            graphicEngine.nr_oameni_setor_B = graphicEngine.nr_oameni_setor_B - 4;
                            environment_hol.nr_oameni_iesire1 += 4;
                        }
                        if (graphicEngine.nr_oameni_setor_C >= 4) {
                            graphicEngine.nr_oameni_setor_C = graphicEngine.nr_oameni_setor_C - 4;
                            environment_hol.nr_oameni_iesire2 += 4;
                        }
                    } else if (mod4) {
                        environment_hol.nr_oameni_iesire2 = 0;
                        environment_hol.nr_oameni_iesire1 = 0;
                        if (graphicEngine.nr_oameni_setor_A >= 4) {
                            graphicEngine.nr_oameni_setor_A = graphicEngine.nr_oameni_setor_A - 4;
                            environment_hol.nr_oameni_iesire1 += 4;
                        }
                        if (graphicEngine.nr_oameni_setor_B >= 4) {
                            graphicEngine.nr_oameni_setor_B = graphicEngine.nr_oameni_setor_B - 4;
                            environment_hol.nr_oameni_iesire2 += 4;
                        }
                        if (graphicEngine.nr_oameni_setor_C >= 4) {
                            graphicEngine.nr_oameni_setor_C = graphicEngine.nr_oameni_setor_C - 4;
                            environment_hol.nr_oameni_iesire2 += 4;
                        }
                    }
                } else {
                    X_Y1_activated = false;
                    A_X_activated = false;
                    B_X_activated = false;
                    Y2_X_activated = false;
                    X_Y2_activated = false;
                    mod1 = false;
                    mod2 = false;
                    mod3 = false;
                    mod4 = false;
                }


                //System.out.println("Iesire 1: "+environment_hol.ocupare_scari1+" Iesire 2: "+environment_hol.ocupare_scari2);
                //System.out.println(mod1+" "+mod2+" "+mod3+" "+mod4);
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
        });
    }
}