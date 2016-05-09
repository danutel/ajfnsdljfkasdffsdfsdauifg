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
                    int offset = 25000;
                    for (int i = 13; i <= 20; i++) {
                        banda_leduri[i].culoare = culoareA_X;
                    }

                    for (int i = 41; i < 45; i++) {
                        banda_leduri[i].culoare = culoareA_X;
                        banda_leduri[i].onA_X();
                    }

                    while (controller_hol.A_X_activated) {
                        banda_leduri[(2 + offset) % 8 + 13].onA_X();
                        banda_leduri[(3 + offset) % 8 + 13].onA_X();
                        banda_leduri[(6 + offset) % 8 + 13].onA_X();
                        banda_leduri[(7 + offset) % 8 + 13].onA_X();

                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        banda_leduri[(2 + offset) % 8 + 13].offA_X();
                        banda_leduri[(3 + offset) % 8 + 13].offA_X();
                        banda_leduri[(6 + offset) % 8 + 13].offA_X();
                        banda_leduri[(7 + offset) % 8 + 13].offA_X();

                        offset--;
                        if (offset < 2)
                            offset = 25000;
                    }

                    for (int i = 41; i < 45; i++) {
                        banda_leduri[i].offA_X();
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
                    int offset = 25000;
                    for (int i = 54; i <= 65; i++) {
                        banda_leduri[i].culoare = culoareB_X;
                    }
                    while (controller_hol.B_X_activated) {
                        banda_leduri[(1 + offset) % 12 + 54].onB_X();
                        banda_leduri[(2 + offset) % 12 + 54].onB_X();
                        banda_leduri[(3 + offset) % 12 + 54].onB_X();
                        banda_leduri[(6 + offset) % 12 + 54].onB_X();
                        banda_leduri[(7 + offset) % 12 + 54].onB_X();
                        banda_leduri[(8 + offset) % 12 + 54].onB_X();

                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        banda_leduri[(1 + offset) % 12 + 54].offB_X();
                        banda_leduri[(2 + offset) % 12 + 54].offB_X();
                        banda_leduri[(3 + offset) % 12 + 54].offB_X();
                        banda_leduri[(6 + offset) % 12 + 54].offB_X();
                        banda_leduri[(7 + offset) % 12 + 54].offB_X();
                        banda_leduri[(8 + offset) % 12 + 54].offB_X();

                        offset--;
                        if (offset <= 1)
                            offset = 24999;
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
                    int offset = 25000;
                    for (int i = 1; i <= 12; i++) {
                        banda_leduri[i].culoare = culoareC_X;
                    }
                    while (controller_hol.Y2_X_activated) {
                        banda_leduri[(1 + offset) % 12 + 1].onY2_X();
                        banda_leduri[(2 + offset) % 12 + 1].onY2_X();
                        banda_leduri[(6 + offset) % 12 + 1].onY2_X();
                        banda_leduri[(7 + offset) % 12 + 1].onY2_X();
                        banda_leduri[(11 + offset) % 12 + 1].onY2_X();
                        banda_leduri[(12 + offset) % 12 + 1].onY2_X();

                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        banda_leduri[(1 + offset) % 12 + 1].offY2_X();
                        banda_leduri[(2 + offset) % 12 + 1].offY2_X();
                        banda_leduri[(6 + offset) % 12 + 1].offY2_X();
                        banda_leduri[(7 + offset) % 12 + 1].offY2_X();
                        banda_leduri[(11 + offset) % 12 + 1].offY2_X();
                        banda_leduri[(12 + offset) % 12 + 1].offY2_X();
                        offset--;
                        if (offset <= 2)
                            offset = 25000;
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
                    int offset = 0;
                    for (int i = 1; i <= 12; i++) {
                        banda_leduri[i].culoare = culoareC_X;
                    }
                    while (controller_hol.X_Y2_activated) {
                        banda_leduri[(1 + offset) % 12 + 1].onX_Y2();
                        banda_leduri[(2 + offset) % 12 + 1].onX_Y2();
                        banda_leduri[(6 + offset) % 12 + 1].onX_Y2();
                        banda_leduri[(7 + offset) % 12 + 1].onX_Y2();
                        banda_leduri[(11 + offset) % 12 + 1].onX_Y2();
                        banda_leduri[(12 + offset) % 12 + 1].onX_Y2();

                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        banda_leduri[(1 + offset) % 12 + 1].offY2_X();
                        banda_leduri[(2 + offset) % 12 + 1].offY2_X();
                        banda_leduri[(6 + offset) % 12 + 1].offY2_X();
                        banda_leduri[(7 + offset) % 12 + 1].offY2_X();
                        banda_leduri[(11 + offset) % 12 + 1].offY2_X();
                        banda_leduri[(12 + offset) % 12 + 1].offY2_X();

                        offset++;
                        if (offset > 25000)
                            offset = 0;
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
                    int offset = 25000;
                    for (int i = 49; i <= 53; i++) {
                        banda_leduri[i].culoare = culoareX_Y;
                    }
                    while (controller_hol.X_Y1_activated) {
                        banda_leduri[(1 + offset) % 5 + 49].onX_Y1();
                        banda_leduri[(2 + offset) % 5 + 49].onX_Y1();

                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        banda_leduri[(1 + offset) % 5 + 49].onX_Y1();
                        banda_leduri[(2 + offset) % 5 + 49].onX_Y1();

                        offset--;
                        if (offset <= 1)
                            offset = 24999;
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
