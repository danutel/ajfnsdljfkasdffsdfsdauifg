import com.jme3.math.ColorRGBA;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;

public class directionare extends Agent {
    public static leduri[] banda_leduri = new leduri[86];
    public static ColorRGBA culoareA_X = ColorRGBA.Blue;
    public static ColorRGBA culoareB_X = ColorRGBA.Blue;
    public static ColorRGBA culoareC_X = ColorRGBA.Blue;
    public static ColorRGBA culoareX_Y = ColorRGBA.Blue;
    public static boolean locked = false;

    @Override
    public void setup() {
        for (int i = 0; i < 86; i++) {
            banda_leduri[i] = new leduri(i);
            banda_leduri[i].intensitate = 50f;
        }

        ThreadedBehaviourFactory tbf = new ThreadedBehaviourFactory();

        Behaviour A_X = new Behaviour() {
            @Override
            public void action() {
                if(environment_hol.alarma_incendiu) {
                    if (controller_hol.A_X_activated) {
                        graphicEngine.A_X_activated = true;
                    }
                    else
                    {
                        graphicEngine.A_X_activated = false;
                    }
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean done() {
                return false;
            }
        };

        Behaviour B_X = new Behaviour() {
            @Override
            public void action() {
                if(environment_hol.alarma_incendiu) {
                    if (controller_hol.B_X_activated) {
                        graphicEngine.B_X_activated = true;
                    }
                    else
                    {
                        graphicEngine.B_X_activated = false;
                    }
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean done() {
                return false;
            }
        };

        Behaviour Y2_X = new Behaviour() {
            @Override
            public void action() {
                if(environment_hol.alarma_incendiu) {
                    if (controller_hol.Y2_X_activated) {
                        graphicEngine.Y2_X_activated = true;
                    }
                    else
                    {
                        graphicEngine.Y2_X_activated = false;
                    }
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean done() {
                return false;
            }
        };

        Behaviour X_Y2 = new Behaviour() {
            @Override
            public void action() {
                if(environment_hol.alarma_incendiu) {
                    if (controller_hol.X_Y2_activated) {
                        graphicEngine.X_Y2_activated = true;
                    }
                    else
                    {
                        graphicEngine.X_Y2_activated = false;
                    }
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean done() {
                return false;
            }
        };

        Behaviour X_Y1 = new Behaviour() {
            @Override
            public void action() {
                if (environment_hol.alarma_incendiu) {
                    if (controller_hol.X_Y1_activated) {
                        graphicEngine.X_Y1_activated = true;
                    }
                    else
                    {
                        graphicEngine.X_Y1_activated = false;
                    }
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean done() {
                return false;
            }
        };

        addBehaviour(tbf.wrap(A_X));
        addBehaviour(tbf.wrap(B_X));
        addBehaviour(tbf.wrap(Y2_X));
        addBehaviour(tbf.wrap(X_Y2));
        addBehaviour(tbf.wrap(X_Y1));
    }

}
