import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class environment extends Agent{
    public static double temperatura_interior;
    public static double umiditate;
    public static float fum;
    public static float foc;
    public static float temperatura_exterior=5;
    public static float lumina;
    public static  boolean curent_electric;
    public static  boolean lumini_urgenta;
    public static  boolean sprinkler;
    public static boolean alarma_incendiu;
    public static double ventilatie; //0-oprit 1-reglare temperatura 2-trage aer din interior(pentru situatii de urgenta)
    public static double comanda_ventilatie;
    public static String adresa_server,nume_server;
    public static double CO2;

    private boolean sprinkler_activated=false;
    private boolean curent_electric_activated=true;
    private float foc_modificat,fum_modificat;
    private boolean lumini_urgenta_activated=false;
    private boolean alarma_incendiu_activated;
    public static float u;
    private double Kp,Tp,yk,ykanterior,uanterior;
    private int X,Y,Z;
    public static String locatie;
    public static boolean change_led=true;
    public static int mod_leduri=0;
    private leduri[] banda_leduri;
    private int index;

    @Override
    public void setup(){

        locatie = getAID().getName().split("@")[1];

        switch (locatie){
            case "server":{X=1017;Y=181;Z=-2056;index=1;break;}
            case "Camera 2":{X=1214;Y=181;Z=-1975;index=2;break;}
            case "Camera 3":{X=1129;Y=181;Z=-1835;index=3;break;}
            case "Camera 4":{X=1242;Y=181;Z=-1820;index=4;break;}
            case "Camera 5":{X=1214;Y=149;Z=-1975;index=5;break;}
            case "Camera 6":{X=1129;Y=181;Z=-1835;index=6;break;}
            case "Camera 7":{X=1242;Y=181;Z=-1820;index=7;break;}
            default: {X=0;Y=0;Z=0;}
        }

        requestHandler x = new requestHandler("load","Modele/Obiecte/fum.zip","fum.j3o","detector_fum",X-10,Y-1,Z-10,1,1,1,0,0,0,0);
        graphicEngine.request.add(x);
        requestHandler y = new requestHandler("load","Modele/Obiecte/bec.zip","bec.j3o","bec",X,Y-1,Z,1,1,1,0,0,0,0);
        graphicEngine.request.add(y);
        requestHandler z = new requestHandler("light",index,true,false,3f,200f,X,Y-10,Z);
        graphicEngine.request.add(z);
        requestHandler w  = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X-25,Y-1,Z-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w);
        requestHandler w2 = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X-25,Y-1,Z+25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w2);
        requestHandler w3 = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X+25,Y-1,Z-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w3);
        requestHandler w4 = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X+25,Y-1,Z+25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w4);
        requestHandler vent = new requestHandler("load","Modele/Obiecte/vent.zip","vent.j3o","vent",X,Y+2,Z-30,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(vent);



        Kp=0.3704;
        Tp=0.9753;
        u=1f;

        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                yk=(Kp*u)+(Kp*uanterior)+(Tp*ykanterior);
                ykanterior=yk;
                uanterior=u;
                temperatura_interior=yk+temperatura_exterior;
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

        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                if(foc>0)
                {
                    if(fum<35) {
                        fum = 5+fum + foc;
                        foc = foc + 1;
                    }
                }

                if(ventilatie==2)
                {
                    if(fum>=4)
                        fum=fum-4;
                    else
                        fum=0;
                }

                if(sprinkler==true)
                {
                    if(foc>=2)
                        foc=foc-2;
                    else
                        foc=0;
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
        });

        addBehaviour(new Behaviour() {
            @Override
            public void action() {

                if (sprinkler == true && sprinkler_activated == false) {
                    sprinkler_activated = true;
                    requestHandler sprink = new requestHandler("stropire", index, true, X - 25, (int)Y - 2, Z - 25);
                    graphicEngine.request.add(sprink);

                    requestHandler sprink2 = new requestHandler("stropire", index+1, true, X - 25, (int)Y - 2, Z + 25);
                    graphicEngine.request.add(sprink2);

                    requestHandler sprink3 = new requestHandler("stropire", index+2, true, X + 25, (int)Y - 2, Z - 25);
                    graphicEngine.request.add(sprink3);

                    requestHandler sprink4 = new requestHandler("stropire", index+3, true, X + 25, (int)Y - 2, Z + 25);
                    graphicEngine.request.add(sprink4);
                } else if (sprinkler == false && sprinkler_activated == true) {
                    sprinkler_activated = false;
                    requestHandler h = new requestHandler("stropire", index, false, X - 20, (int)Y - 2, Z - 28);
                    graphicEngine.request.add(h);

                    requestHandler h2 = new requestHandler("stropire", index+1, false, X - 20, (int)Y - 2, Z - 28);
                    graphicEngine.request.add(h2);

                    requestHandler h3 = new requestHandler("stropire", index+2, false, X - 20, (int)Y - 2, Z - 28);
                    graphicEngine.request.add(h3);

                    requestHandler h4 = new requestHandler("stropire", index+3, false, X - 20, (int)Y - 2, Z - 28);
                    graphicEngine.request.add(h4);
                }

                if(curent_electric==false && curent_electric_activated==true)
                {
                    curent_electric_activated=false;
                    requestHandler z = new requestHandler("light",index,false,false,1.5f,200f,X,Y-10,Z);
                    graphicEngine.request.add(z);
                }
                else if(curent_electric==true && curent_electric_activated==false)
                {
                    curent_electric_activated=true;
                    requestHandler z1 = new requestHandler("light",index,false,false,1.5f,200f,X,Y-10,Z);
                    graphicEngine.request.add(z1);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    requestHandler z = new requestHandler("light",index,true,false,1.5f,200f,X,Y-10,Z);
                    graphicEngine.request.add(z);
                }

                if(foc>0 && foc!=foc_modificat)
                {
                    foc_modificat=foc;
                    requestHandler hf = new requestHandler("foc_start",index,true,foc,X,Y-32,Z);
                    graphicEngine.request.add(hf);
                }
                else if(foc==0 && foc!=foc_modificat)
                {
                    foc_modificat=foc;
                    requestHandler hf = new requestHandler("foc_start",index,false,foc,X,Y-32,Z);
                    graphicEngine.request.add(hf);
                }

                if(fum>0 && fum!=fum_modificat)
                {
                    fum_modificat=fum;
                    requestHandler hf = new requestHandler("smoke",index,true,fum,X,Y-32,Z);
                    graphicEngine.request.add(hf);
                }
                else if(fum==0 && fum!=fum_modificat)
                {
                    fum_modificat=fum;
                    requestHandler hf2 = new requestHandler("smoke",index,false,fum,X,Y-32,Z);
                    graphicEngine.request.add(hf2);
                }

                if(lumini_urgenta==true && lumini_urgenta_activated==false)
                {
                    lumini_urgenta_activated=true;
                    requestHandler z1 = new requestHandler("light",index,false,false,1.5f,200f,X,Y-10,Z);
                    graphicEngine.request.add(z1);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    requestHandler z = new requestHandler("light",index,true,true,1.5f,200f,X,Y-10,Z);
                    graphicEngine.request.add(z);
                }
                else if (lumini_urgenta==false && lumini_urgenta_activated==true)
                {
                    lumini_urgenta_activated=false;
                    requestHandler z = new requestHandler("light",index,true,false,1.5f,200f,X,Y-10,Z);
                    graphicEngine.request.add(z);
                }

                if(alarma_incendiu==true && alarma_incendiu_activated==false)
                {
                    alarma_incendiu_activated=true;
                }
                else if(alarma_incendiu==false && alarma_incendiu_activated==true)
                {
                    alarma_incendiu_activated=false;
                }
            }

            @Override
            public boolean done() {
                return false;
            }
        });

    }
}
