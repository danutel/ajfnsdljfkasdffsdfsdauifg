import com.jme3.math.ColorRGBA;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class directionare extends Agent{
    public leduri[] banda_leduri=new leduri[61];
    public boolean[] first={true,true,true,true,true,false,false,false,false,false,false,false};

    @Override
    public void setup(){
        for(int i=1;i<61;i++) {
            banda_leduri[i] = new leduri(i);
            banda_leduri[i].intensitate = 50f;
            if(i>=1&&i<=12)
                banda_leduri[i].culoare = ColorRGBA.Red;
            if(i==54)
                banda_leduri[i].culoare = ColorRGBA.Red;
            if(i>=49&&i<=53)
                banda_leduri[i].culoare = ColorRGBA.Blue;
            if((i>=13&&i<=20) || (i>=41&&i<=44))
                banda_leduri[i].culoare = ColorRGBA.Blue;

        }
        banda_leduri[54].on();
        int [] banda_leduri_union={61,44,43,42,41,20,19,18,17,16,15,14,13,53,52,51,50,49};
        addBehaviour(new Behaviour() {
            @Override
            public void action() {

               /* boolean aux = first[11];
                for(int i=11;i>0;i--)
                {
                    first[i]=first[i-1];
                }
                first[0]=aux;

                for(int i=1;i<13;i++)
                {
                    if(first[i-1]==true)
                        banda_leduri[i].on();
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int i=1;i<13;i++)
                {
                    if(first[i-1]==true)
                        banda_leduri[i].off();
                }*/
                int index=7;
                banda_leduri[1].on();
                banda_leduri[2].on();
                banda_leduri[3].on();
                banda_leduri[4].on();
                banda_leduri[5].on();
                banda_leduri[6].on();

                int index2=8;
                banda_leduri[banda_leduri_union[1]].on();
                banda_leduri[banda_leduri_union[2]].on();
                banda_leduri[banda_leduri_union[3]].on();
                banda_leduri[banda_leduri_union[4]].on();
                banda_leduri[banda_leduri_union[5]].on();
                banda_leduri[banda_leduri_union[6]].on();
                banda_leduri[banda_leduri_union[7]].on();

                while(true)
                {
                    int aux;
                    banda_leduri[index].on();
                    if(index-6<=0)
                        aux=12+index-6;
                    else
                        aux=index-6;
                    banda_leduri[aux].off();
                    index++;
                    if(index==13)
                        index=1;

                    int aux2;
                    banda_leduri[banda_leduri_union[index2]].on();
                    if(index2-7<=0)
                        aux2=17+index2-7;
                    else
                        aux2=index2-7;
                    banda_leduri[banda_leduri_union[aux2]].off();
                    index2++;
                    if(index2==18)
                        index2=1;


                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


            }

            @Override
            public boolean done() {
                return false;
            }
        });

    }
}
