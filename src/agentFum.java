import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class agentFum extends Agent {
    private boolean senzor_fum;
    @Override
    public void setup()
    {

      /*  requestHandler h = new requestHandler("stropire","water1",true,-670,-88,-130);
        //graphicEngine.request.add(h);
        requestHandler hf = new requestHandler("foc_start","fire1",true,-670,-110,-130);
       // graphicEngine.request.add(hf);

        requestHandler fg = new requestHandler("load","Modele/Obiecte/vent.zip","vent.j3o","vent",-660,-84,-120,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(fg);*/

        addBehaviour(new Behaviour() {
            @Override
            public void action() {

                if(environment.fum>25)
                {
                    senzor_fum=true;
                }
                else
                {
                    senzor_fum=false;
                }

                if(senzor_fum) {
                    environment.alarma_incendiu = true;
                    environment.ventilatie = 2;
                    environment.curent_electric = false;
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    environment.lumini_urgenta = true;
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    environment.sprinkler = true;
                }
                else
                {
                    environment.alarma_incendiu = false;
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    environment.sprinkler = false;
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    environment.curent_electric = true;
                    environment.lumini_urgenta = false;
                }
                if(environment.fum <= 0)
                    environment.ventilatie = 1;


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
