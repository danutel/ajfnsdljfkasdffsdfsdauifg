import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;
import net.sourceforge.jFuzzyLogic.FIS;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class controller extends Agent {
    public double temperatura;
    public boolean fum;
    public double umiditate;
    public double CO2,surplus_comanda_ventilator;
    public double ventilatie;
    public boolean stropitori;
    public boolean lumini_urgenta;
    public boolean electricitate;
    public double umidificator,ventilator,incalzire,racire;
    public static List<String> lista_celule = new ArrayList<>();
    public static boolean disabled = false;
    private double referinta_temperatura,referinta_umiditate,referinta_CO2;
    private FIS fis;

    @Override
    public void setup(){
        String fileName = "fuzzy.fcl";
        fis = FIS.load(fileName,true);

        // Error while loading?
        if( fis == null ) {
            System.err.println("Can't load file: '" + fileName + "'");
            return;
        }

        ThreadedBehaviourFactory tbf = new ThreadedBehaviourFactory();

        Behaviour citire_senzori = new Behaviour() {
            @Override
            public void action() {
                ACLMessage mesaj_receptionat = myAgent.receive();
                if(mesaj_receptionat!=null)
                {
                    if(mesaj_receptionat.getConversationId()=="temperatura")
                    {
                        temperatura = Double.parseDouble(mesaj_receptionat.getContent());
                    }

                    if(mesaj_receptionat.getConversationId()=="umiditate")
                    {
                        umiditate = Double.parseDouble(mesaj_receptionat.getContent());
                    }

                    if(mesaj_receptionat.getConversationId()=="fum")
                    {
                        fum = Boolean.parseBoolean(mesaj_receptionat.getContent());
                    }

                    if(mesaj_receptionat.getConversationId()=="CO2")
                    {
                        CO2 = Double.parseDouble(mesaj_receptionat.getContent());
                    }

                    if(mesaj_receptionat.getConversationId().equals("lista_celule"))
                    {
                        lista_celule.clear();
                        lista_celule.add(mesaj_receptionat.getContent());
                        System.out.println(lista_celule);
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
        };

        addBehaviour(tbf.wrap(citire_senzori));

        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                if(!disabled) {
                    Iterator it = getAID().getAllAddresses();
                    String adresa = (String) it.next();
                    String platforma = getAID().getName().split("@")[1];

                    ACLMessage mesaj_ventilatie = new ACLMessage(ACLMessage.REQUEST);
                    AID r = new AID("ventilatie@" + platforma, AID.ISGUID);
                    r.addAddresses(adresa);
                    mesaj_ventilatie.setConversationId("ventilatie");
                    mesaj_ventilatie.addReceiver(r);
                    mesaj_ventilatie.setContent(String.valueOf(ventilatie));
                    myAgent.send(mesaj_ventilatie);

                    ACLMessage mesaj_stropitori = new ACLMessage(ACLMessage.REQUEST);
                    AID r2 = new AID("stropitori@" + platforma, AID.ISGUID);
                    r2.addAddresses(adresa);
                    mesaj_stropitori.setConversationId("stropitori");
                    mesaj_stropitori.addReceiver(r2);
                    mesaj_stropitori.setContent(String.valueOf(stropitori));
                    myAgent.send(mesaj_stropitori);

                    ACLMessage mesaj_lumini_urgenta = new ACLMessage(ACLMessage.REQUEST);
                    AID r3 = new AID("lumini_urgenta@" + platforma, AID.ISGUID);
                    r3.addAddresses(adresa);
                    mesaj_lumini_urgenta.setConversationId("lumini_urgenta");
                    mesaj_lumini_urgenta.addReceiver(r3);
                    mesaj_lumini_urgenta.setContent(String.valueOf(lumini_urgenta));
                    myAgent.send(mesaj_lumini_urgenta);

                    ACLMessage mesaj_electricitate = new ACLMessage(ACLMessage.REQUEST);
                    AID r4 = new AID("electricitate@" + platforma, AID.ISGUID);
                    r4.addAddresses(adresa);
                    mesaj_electricitate.setConversationId("electricitate");
                    mesaj_electricitate.addReceiver(r4);
                    mesaj_electricitate.setContent(String.valueOf(electricitate));
                    myAgent.send(mesaj_electricitate);

                    ACLMessage mesaj_umidificator = new ACLMessage(ACLMessage.REQUEST);
                    AID r5 = new AID("Umidificator@" + platforma, AID.ISGUID);
                    r5.addAddresses(adresa);
                    mesaj_umidificator.setConversationId("umidificator");
                    mesaj_umidificator.addReceiver(r5);
                    mesaj_umidificator.setContent(String.valueOf(umidificator));
                    myAgent.send(mesaj_umidificator);

                    ACLMessage mesaj_racire = new ACLMessage(ACLMessage.REQUEST);
                    AID r6 = new AID("Racire@" + platforma, AID.ISGUID);
                    r6.addAddresses(adresa);
                    mesaj_racire.setConversationId("racire");
                    mesaj_racire.addReceiver(r6);
                    mesaj_racire.setContent(String.valueOf(racire));
                    myAgent.send(mesaj_racire);

                    ACLMessage mesaj_incalzire = new ACLMessage(ACLMessage.REQUEST);
                    AID r7 = new AID("Incalzire@" + platforma, AID.ISGUID);
                    r7.addAddresses(adresa);
                    mesaj_incalzire.setConversationId("incalzire");
                    mesaj_incalzire.addReceiver(r7);
                    mesaj_incalzire.setContent(String.valueOf(incalzire));
                    myAgent.send(mesaj_incalzire);

                    ACLMessage mesaj_ventilator = new ACLMessage(ACLMessage.REQUEST);
                    AID r8 = new AID("Ventilator@" + platforma, AID.ISGUID);
                    r8.addAddresses(adresa);
                    mesaj_ventilator.setConversationId("ventilator");
                    mesaj_ventilator.addReceiver(r8);
                    mesaj_ventilator.setContent(String.valueOf(ventilator));
                    myAgent.send(mesaj_ventilator);
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

        addBehaviour(new Behaviour() {                     //
            @Override
            public void action() {
                if(fum) {
                    environment.alarma_incendiu = true;
                    ventilatie = 2;
                    electricitate = false;
                    lumini_urgenta = true;
                    stropitori = true;
                }
                else
                {
                    environment.alarma_incendiu = false;
                    stropitori = false;
                    electricitate = true;
                    lumini_urgenta = false;
                }
                if(environment.fum <= 0)
                    ventilatie = 1;


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

        Behaviour fuzzy = new Behaviour() {
            @Override
            public void action() {
                if(!disabled) {
                    referinta_temperatura = environment.referinta_temperatura;
                    referinta_umiditate = environment.referinta_umiditate;
                    referinta_CO2 = environment.referinta_CO2;
                    double eroare_CO2 = referinta_CO2 - CO2;

                    if(eroare_CO2 < -100){
                        surplus_comanda_ventilator = 0.2;
                    }
                    else if(eroare_CO2 > 100){
                        surplus_comanda_ventilator = 0;
                    }

                    // Set inputs
                    fis.setVariable("eroare_temperatura", referinta_temperatura - temperatura);
                    fis.setVariable("eroare_umiditate", referinta_umiditate - umiditate);

                    // Evaluate
                    fis.evaluate();

                    racire = fis.getVariable("racire").getValue();
                    incalzire = fis.getVariable("incalzire").getValue();
                    umidificator = fis.getVariable("umidificator").getValue();
                    ventilator = fis.getVariable("ventilator").getValue()+surplus_comanda_ventilator;

                    if(racire<0.24 && racire>0)
                    {
                        racire=0.15;
                    }
                    if(incalzire<0.24 && incalzire>0)
                    {
                        incalzire=0.15;
                    }
                    if(racire>0.83)
                    {
                        racire=1;
                    }
                    if(incalzire>0.83)
                    {
                        incalzire=1;
                    }
                    System.out.println("Intrare eroare temp. "+fis.getVariable("eroare_temperatura").getValue()+ " eroare H. "+
                            fis.getVariable("eroare_umiditate").getValue()+" Iesire comanda incalzire "
                            +incalzire+" racire "+racire+" umidificator "+umidificator+" ventilator "+ventilator);

         /*           File log = new File("log.txt");

                    try{
                        if(!log.exists()){
                            System.out.println("We had to make a new file.");
                            log.createNewFile();
                        }

                        FileWriter fileWriter = new FileWriter(log, true);

                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(referinta_temperatura+" "+referinta_umiditate+" "+
                                referinta_CO2+" "+incalzire+" "+racire+" "+umidificator+" "+ventilator+
                                " "+temperatura+" "+umiditate+" "+CO2+"\n");
                        bufferedWriter.close();

                    } catch(IOException e) {
                        System.out.println("COULD NOT LOG!!");
                    }*/
                }
                else
                {
                    racire = graphicEngine.comanda_racire/100;
                    incalzire = graphicEngine.comanda_incalzire/100;
                    ventilatie = graphicEngine.comanda_ventilatie/100;
                    umidificator = graphicEngine.comanda_umidificator/100;
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
        addBehaviour(tbf.wrap(fuzzy));
    }
}

