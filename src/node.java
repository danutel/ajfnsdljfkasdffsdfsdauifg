
public class node {
    private int nume;
    private int distanta_y1;
    private int distanta_y2;
    public int numar_oameni;
    private int prima_iesire;

    public node(int distanta1, int distanta2)
    {
        this.distanta_y1 = distanta1;
        this.distanta_y2 = distanta2;
        setExit();
    }

    public node()
    {

    }

    private void setExit()
    {
        if(distanta_y1<distanta_y2)
            prima_iesire = 1;
        else
            prima_iesire = 2;
    }
}
