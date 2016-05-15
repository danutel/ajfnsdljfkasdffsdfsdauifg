import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class environment_hol extends Agent{
    private String locatie;
    private float Y;
    public static float[] fum = new float[6];
    public static float[] foc = new float[6];
    public static  boolean[] curent_electric=new boolean[6];
    public static  boolean[] lumini_urgenta=new boolean[6];
    public static  boolean []sprinkler=new boolean[6];
    public static boolean alarma_incendiu;
    public static double ventilatie[]=new double[6]; //0-oprit 1-reglare temperatura 2-trage aer din interior(pentru situatii de urgenta)
    public static String adresa_server,nume_server;
    private boolean []sprinkler_activated=new boolean[6];
    private boolean[] curent_electric_activated=new boolean[6];
    private float[] foc_modificat=new float[6],fum_modificat=new float[6];
    private boolean []lumini_urgenta_activated=new boolean[6];
    private int index;
    private int [] X = {840,950,1050,1150,1250,1005};
    private int [] Z = {-1936,-1885,-1885,-1885,-1885,-1810};
    public static int nr_oameni_iesire1;
    public static int nr_oameni_iesire2;

    @Override
    public void setup()
    {
        locatie = getAID().getName().split("@")[1];

        locatie="hol4";

        switch (locatie){
            case "hol4":{Y=181.5f;index=9;break;}
            case "hol3":{Y=149;index=14;break;}
            default: {Y=0;}
        }

        for(int i=0;i<6;i++)
        {
            curent_electric[i]=true;
            curent_electric_activated[i]=true;
        }

        requestHandler x = new requestHandler("load","Modele/Obiecte/fum.zip","fum.j3o","detector_fum",X[0]-10,Y-1,Z[0]-10,1,1,1,0,0,0,0);
        graphicEngine.request.add(x);
        requestHandler y = new requestHandler("load","Modele/Obiecte/bec.zip","bec.j3o","bec",X[0],Y-1,Z[0],1,1,1,0,0,0,0);
        graphicEngine.request.add(y);
        requestHandler z = new requestHandler("light",index,true,false,1f,150f,X[0], (int) (Y-8),Z[0]);
        graphicEngine.request.add(z);
        requestHandler w  = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[0]-25,Y-1,Z[0]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w);
        requestHandler w2 = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[0]-25,Y-1,Z[0]+25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w2);
        requestHandler w3 = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[0]+25,Y-1,Z[0]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w3);
        requestHandler w4 = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[0]+25,Y-1,Z[0]+25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w4);
        requestHandler vent = new requestHandler("load","Modele/Obiecte/vent.zip","vent.j3o","vent",X[0],Y+2,Z[0]-30,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(vent);

        requestHandler x2 = new requestHandler("load","Modele/Obiecte/fum.zip","fum.j3o","detector_fum",X[1],Y-1,Z[1]-15,1,1,1,0,0,0,0);
        graphicEngine.request.add(x2);
        requestHandler y2 = new requestHandler("load","Modele/Obiecte/bec.zip","bec.j3o","bec",X[1],Y-1,Z[1]-25,1,1,1,0,0,0,0);
        graphicEngine.request.add(y2);
        requestHandler z2 = new requestHandler("light",index+1,true,false,1f,150f,X[1],(int)Y-5,Z[1]-25);
        graphicEngine.request.add(z2);
        requestHandler w21  = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[1]-25,Y-1,Z[1]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w21);
        requestHandler w32 = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[1]+25,Y-1,Z[1]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w32);
        requestHandler vent2 = new requestHandler("load","Modele/Obiecte/vent.zip","vent.j3o","vent",X[1]-50,Y+2,Z[1]-30,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(vent2);

        requestHandler x22 = new requestHandler("load","Modele/Obiecte/fum.zip","fum.j3o","detector_fum",X[2],Y-1,Z[2]-15,1,1,1,0,0,0,0);
        graphicEngine.request.add(x22);
        requestHandler y22 = new requestHandler("load","Modele/Obiecte/bec.zip","bec.j3o","bec",X[2],Y-1,Z[2]-25,1,1,1,0,0,0,0);
        graphicEngine.request.add(y22);
        requestHandler z22 = new requestHandler("light",index+2,true,false,1f,150f,X[2],(int)Y-5,Z[2]-25);
        graphicEngine.request.add(z22);
        requestHandler w212  = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[2]-25,Y-1,Z[2]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w212);
        requestHandler w322 = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[2]+25,Y-1,Z[2]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w322);
        requestHandler vent22 = new requestHandler("load","Modele/Obiecte/vent.zip","vent.j3o","vent",X[2]-50,Y+2,Z[2]-30,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(vent22);

        requestHandler x222 = new requestHandler("load","Modele/Obiecte/fum.zip","fum.j3o","detector_fum",X[3],Y-1,Z[3]-15,1,1,1,0,0,0,0);
        graphicEngine.request.add(x222);
        requestHandler y222 = new requestHandler("load","Modele/Obiecte/bec.zip","bec.j3o","bec",X[3],Y-1,Z[3]-25,1,1,1,0,0,0,0);
        graphicEngine.request.add(y222);
        requestHandler z222 = new requestHandler("light",index+3,true,false,1f,150f,X[3],(int)Y-5,Z[3]-25);
        graphicEngine.request.add(z222);
        requestHandler w2122  = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[3]-25,Y-1,Z[3]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w2122);
        requestHandler w3222 = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[3]+25,Y-1,Z[3]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w3222);
        requestHandler vent222= new requestHandler("load","Modele/Obiecte/vent.zip","vent.j3o","vent",X[3]-50,Y+2,Z[3]-30,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(vent222);

        requestHandler x2222 = new requestHandler("load","Modele/Obiecte/fum.zip","fum.j3o","detector_fum",X[4],Y-1,Z[4]-15,1,1,1,0,0,0,0);
        graphicEngine.request.add(x2222);
        requestHandler y2222 = new requestHandler("load","Modele/Obiecte/bec.zip","bec.j3o","bec",X[4],Y-1,Z[4]-25,1,1,1,0,0,0,0);
        graphicEngine.request.add(y2222);
        requestHandler z2222 = new requestHandler("light",index+4,true,false,1f,150f,X[4],(int)Y-5,Z[4]-25);
        graphicEngine.request.add(z2222);
        requestHandler w21222  = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[4]-25,Y-1,Z[4]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w21222);
        requestHandler w32222 = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[4]+25,Y-1,Z[4]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w32222);
        requestHandler vent2222 = new requestHandler("load","Modele/Obiecte/vent.zip","vent.j3o","vent",X[4]-50,Y+2,Z[4]-30,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(vent2222);

        requestHandler y22222 = new requestHandler("load","Modele/Obiecte/bec.zip","bec.j3o","bec",X[5],Y-1,Z[5]-25,1,1,1,0,0,0,0);
        graphicEngine.request.add(y22222);
        requestHandler z22222 = new requestHandler("light",index+5,true,false,1f,150f,X[5],(int)Y-5,Z[5]-25);
        graphicEngine.request.add(z22222);
        requestHandler vent22222 = new requestHandler("load","Modele/Obiecte/vent.zip","vent.j3o","vent",X[5],Y+2,Z[5]-50,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(vent22222);
        requestHandler w21223  = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[5],Y-1,Z[4]+75,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w21223);
        requestHandler w32224 = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[5],Y-1,Z[4]+15,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w32224);
        requestHandler x2225 = new requestHandler("load","Modele/Obiecte/fum.zip","fum.j3o","detector_fum",X[5],Y-1,Z[5]-50,1,1,1,0,0,0,0);
        graphicEngine.request.add(x2225);



        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                for(int i=0;i<6;i++)
                {
                    if(foc[i]>0)
                    {
                        if(fum[i]<35) {
                            fum[i] = fum[i] + foc[i]+3;
                            foc[i]= foc[i] + 0.6f;
                        }
                    }
                }

                for(int i=0;i<6;i++)
                {
                    if(ventilatie[i]==2)
                    {
                        if(sprinkler[i]==true)
                        {
                            fum[i]=fum[i]-7;
                        }else {
                            if(fum[i]>=5)
                                fum[i]=fum[i]-2;
                            else
                                fum[i]=0;
                        }
                    }
                }

                for(int i=0;i<6;i++)
                {
                    if(sprinkler[i]==true)
                    {
                        if(foc[i]>=2)
                            foc[i]=foc[i]-1.2f;
                        else
                            foc[i]=0;
                    }
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

                for (int i = 0; i < 6; i++) {
                    if (sprinkler[i] == true && sprinkler_activated[i] == false) {

                        sprinkler_activated[i] = true;
                        if(i==5)
                        {
                            requestHandler sprink = new requestHandler("stropire", index + (i * 4), true, X[i], (int) Y - 2, Z[i]-60);
                            graphicEngine.request.add(sprink);
                            requestHandler sprink3 = new requestHandler("stropire", index + (i * 4) + 2, true, X[i], (int) Y - 2, Z[i]);
                            graphicEngine.request.add(sprink3);
                        }
                        else {
                            requestHandler sprink = new requestHandler("stropire", index + (i * 4), true, X[i] - 25, (int) Y - 2, Z[i] - 25);
                            graphicEngine.request.add(sprink);
                            requestHandler sprink3 = new requestHandler("stropire", index + (i * 4) + 2, true, X[i] + 25, (int) Y - 2, Z[i] - 25);
                            graphicEngine.request.add(sprink3);
                        }

                        if(i==0)
                        {
                            requestHandler sprink2 = new requestHandler("stropire", index+(i*4)+1, true, X[i] - 25, (int)Y - 2, Z[i] + 25);
                            graphicEngine.request.add(sprink2);
                            requestHandler sprink4 = new requestHandler("stropire", index+(i*4)+3, true, X[i] + 25, (int)Y - 2, Z[i] + 25);
                            graphicEngine.request.add(sprink4);
                        }
                    } else if (sprinkler[i] == false && sprinkler_activated[i] == true) {
                        sprinkler_activated[i] = false;
                        requestHandler h = new requestHandler("stropire", index+(i*4), false, X[i] - 20, (int)Y - 2, Z[i] - 28);
                        graphicEngine.request.add(h);
                        requestHandler h3 = new requestHandler("stropire", index+(i*4)+2, false, X[i] - 20, (int)Y - 2, Z[i] - 28);
                        graphicEngine.request.add(h3);

                        if(i==0)
                        {
                            requestHandler sprink2 = new requestHandler("stropire", index+(i*4)+1, false, X[i] - 25, (int)Y - 2, Z[i] + 25);
                            graphicEngine.request.add(sprink2);
                            requestHandler sprink4 = new requestHandler("stropire", index+(i*4)+3, false, X[i] + 25, (int)Y - 2, Z[i] + 25);
                            graphicEngine.request.add(sprink4);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                for (int i = 0; i < 6; i++) {
                    if (curent_electric[i] == false && curent_electric_activated[i] == true) {
                        curent_electric_activated[i] = false;
                        requestHandler z = new requestHandler("light", index + i, false, false,1f,150f, X[i], (int) Y - 5, Z[i]-25);
                        graphicEngine.request.add(z);
                    } else if (curent_electric[i] == true && curent_electric_activated[i] == false) {
                        curent_electric_activated[i]=true;

                        requestHandler z1 = new requestHandler("light",index+i,false,false,1f,150f,X[i],(int)Y - 5, Z[i]-25);
                        graphicEngine.request.add(z1);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        requestHandler g = new requestHandler("light",index+i,true,false,1f,150f,X[i],(int)Y - 5, Z[i]-25);
                        graphicEngine.request.add(g);
                    }
                }
                for (int i = 0; i < 6; i++)
                {
                    if(foc[i]>0 && foc[i]!=foc_modificat[i])
                    {
                        foc_modificat[i]=foc[i];
                        requestHandler hf = new requestHandler("foc_start",index+i,true,foc[i],X[i],(int)Y-32,Z[i]-18);
                        graphicEngine.request.add(hf);
                    }
                    else if(foc[i]==0 && foc[i]!=foc_modificat[i])
                    {
                        foc_modificat[i]=foc[i];
                        requestHandler hf = new requestHandler("foc_start",index+i,false,foc[i],X[i],(int)Y-35,Z[i]);
                        graphicEngine.request.add(hf);
                    }
                }

                for (int i = 0; i < 6; i++) {
                    if (fum[i] > 0 && fum[i] != fum_modificat[i]) {
                        fum_modificat[i] = fum[i];
                        requestHandler hf = new requestHandler("smoke", index+i, true, fum[i]*1.5f, X[i], (int)Y - 35, Z[i]-18);
                        graphicEngine.request.add(hf);
                    } else if (fum[i] <=0 && fum[i] != fum_modificat[i]) {
                        fum_modificat[i] = fum[i];
                        requestHandler hf2 = new requestHandler("smoke", index+i, false, 0, X[i], (int)Y - 35, Z[i]);
                        graphicEngine.request.add(hf2);
                    }
                }



                for(int i =0;i<6;i++)
                {
                    if(lumini_urgenta[i]==true && lumini_urgenta_activated[i]==false)
                    {
                        lumini_urgenta_activated[i]=true;
                        requestHandler z1 = new requestHandler("light",index+i,false,false,1.5f,150f,X[i],(int)Y - 5, Z[i]-25);
                        graphicEngine.request.add(z1);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        requestHandler z = new requestHandler("light",index+i,true,true,1.5f,150f,X[i],(int)Y - 5, Z[i]-25);
                        graphicEngine.request.add(z);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (lumini_urgenta[i]==false && lumini_urgenta_activated[i]==true)
                    {
                        lumini_urgenta_activated[i]=false;

                    }
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
    }
}
