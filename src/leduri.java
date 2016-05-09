import com.jme3.math.ColorRGBA;

public class leduri {
    private int X,Y,Z;
    private int index;
    ColorRGBA culoare;
    float intensitate;

    public leduri(int i)
    {
        index=i;

        switch (i){
            case 12:            X=1288;Y=153;Z=-1907;                break;//x
            case 11:            X=1266;Y=153;Z=-1907;                break;
            case 10:            X=1244;Y=153;Z=-1907;                break;
            case 9:            X=1222;Y=153;Z=-1907;                break;
            case 8:            X=1200;Y=153;Z=-1907;                break;
            case 7:            X=1178;Y=153;Z=-1907;                break;
            case 6:            X=1156;Y=153;Z=-1907;                break;
            case 5:            X=1134;Y=153;Z=-1907;                break;
            case 4:            X=1112;Y=153;Z=-1907;                break;
            case 3:            X=1090;Y=153;Z=-1907;                break;
            case 2:            X=1068;Y=153;Z=-1907;                break;
            case 1:            X=1040;Y=153;Z=-1907;                break;//y2 et 4
            case 13:            X=1024;Y=153;Z=-1907;                break;
            case 14:            X=1002;Y=153;Z=-1907;                break;
            case 15:            X=980;Y=153;Z=-1907;                break;
            case 16:            X=958;Y=153;Z=-1907;                break;
            case 17:            X=936;Y=153;Z=-1907;                break;
            case 18:            X=914;Y=153;Z=-1907;                break;
            case 19:            X=892;Y=153;Z=-1907;                break;
            case 20:            X=892;Y=153;Z=-1907;                break;
            case 21:            X=1288;Y=121;Z=-1907;                break;
            case 22:            X=1266;Y=121;Z=-1907;                break;
            case 23:            X=1244;Y=121;Z=-1907;                break;
            case 24:            X=1222;Y=121;Z=-1907;                break;
            case 25:            X=1200;Y=121;Z=-1907;                break;
            case 26:            X=1178;Y=121;Z=-1907;                break;
            case 27:            X=1156;Y=121;Z=-1907;                break;
            case 28:            X=1134;Y=121;Z=-1907;                break;
            case 29:            X=1112;Y=121;Z=-1907;                break;
            case 30:            X=1090;Y=121;Z=-1907;                break;
            case 31:            X=1068;Y=121;Z=-1907;                break;
            case 32:            X=1046;Y=121;Z=-1907;                break;
            case 33:            X=1024;Y=121;Z=-1907;                break;
            case 34:            X=1002;Y=121;Z=-1907;                break;
            case 35:            X=980;Y=121;Z=-1907;                break;
            case 36:            X=958;Y=121;Z=-1907;                break;
            case 37:            X=936;Y=121;Z=-1907;                break;
            case 38:            X=914;Y=121;Z=-1907;                break;
            case 39:            X=892;Y=121;Z=-1907;                break;
            case 40:            X=880;Y=121;Z=-1907;                break;
            case 41:                X=867;Y=153;Z=-1912;                break;
            case 42:                X=867;Y=153;Z=-1934;                break;
            case 43:                X=867;Y=153;Z=-1956;                break;
            case 44:                X=867;Y=153;Z=-1960;                break;
            case 45:                X=867;Y=121;Z=-1912;                break;
            case 46:                X=867;Y=121;Z=-1934;                break;
            case 47:                X=867;Y=121;Z=-1956;                break;
            case 48:                X=867;Y=121;Z=-1960;                break;
            case 49:                X=1010;Y=153;Z=-1806;                break;//y1
            case 50:                X=1010;Y=153;Z=-1828;                break;
            case 51:                X=1010;Y=153;Z=-1850;                break;
            case 52:                X=1010;Y=153;Z=-1872;                break;
            case 53:                X=1010;Y=153;Z=-1894;                break;
            case 54:                X=1010;Y=153;Z=-1916;                break;
            case 55:                X=1010;Y=153;Z=-1938;                break;
            case 56:                X=1010;Y=153;Z=-1960;                break;
            case 57:                X=1010;Y=153;Z=-1982;                break;
            case 58:                X=1010;Y=153;Z=-2004;                break;
            case 59:                X=1010;Y=153;Z=-2026;                break;
            case 60:                X=1010;Y=153;Z=-2048;                break;
            case 61:                X=1010;Y=153;Z=-2070;                break;
            case 62:                X=1010;Y=153;Z=-2092;                break;
            case 63:                X=1010;Y=153;Z=-2114;                break;
            case 64:                X=1010;Y=153;Z=-2136;                break;
            case 65:                X=1010;Y=153;Z=-2158;                break;
            case 66:                X=1010;Y=121;Z=-1916;                break;
            case 67:                X=1010;Y=121;Z=-1806;                break;
            case 68:                X=1010;Y=121;Z=-1828;                break;
            case 69:                X=1010;Y=121;Z=-1850;                break;
            case 70:                X=1010;Y=121;Z=-1872;                break;
            case 71:                X=1010;Y=121;Z=-1894;                break;
            case 72:                X=1010;Y=121;Z=-1916;                break;
            case 73:                X=1010;Y=121;Z=-1938;                break;
            case 74:                X=1010;Y=121;Z=-1960;                break;
            case 75:                X=1010;Y=121;Z=-1982;                break;
            case 76:                X=1010;Y=121;Z=-2004;                break;
            case 78:                X=1010;Y=121;Z=-2026;                break;
            case 79:                X=1010;Y=121;Z=-2048;                break;
            case 80:                X=1010;Y=121;Z=-2070;                break;
            case 81:                X=1010;Y=121;Z=-2092;                break;
            case 82:                X=1010;Y=121;Z=-2114;                break;
            case 83:                X=1010;Y=121;Z=-2136;                break;
            case 84:                X=1010;Y=121;Z=-2158;                break;
            case 85:                X=1010;Y=121;Z=-1916;                break;
            default:X=0;Y=0;Z=0;                break;
        }
    }
    public void on()
    {
        requestHandler z = new requestHandler("light","led",true,index,intensitate,13f,X-1,Y,Z-1,culoare);
        graphicEngine.request.add(z);
    }

    public void off() {
        requestHandler z = new requestHandler("light", "led", false, index, intensitate, 13f, X - 1, Y, Z - 1, culoare);
        graphicEngine.request.add(z);
    }

    public void onA_X()
    {
        requestHandler z = new requestHandler("light","led",true,index,intensitate,13f,X-1,Y,Z-1,culoare);
        graphicEngine.requestA_X.add(z);
    }

    public void offA_X() {
        requestHandler z = new requestHandler("light", "led", false, index, intensitate, 13f, X - 1, Y, Z - 1, culoare);
        graphicEngine.requestA_X.add(z);
    }

    public void onB_X()
    {
        requestHandler z = new requestHandler("light","led",true,index,intensitate,13f,X-1,Y,Z-1,culoare);
        graphicEngine.requestB_X.add(z);
    }

    public void offB_X() {
        requestHandler z = new requestHandler("light", "led", false, index, intensitate, 13f, X - 1, Y, Z - 1, culoare);
        graphicEngine.requestB_X.add(z);
    }

    public void onY2_X()
    {
        requestHandler z = new requestHandler("light","led",true,index,intensitate,13f,X-1,Y,Z-1,culoare);
        graphicEngine.requestY2_X.add(z);
    }

    public void offY2_X() {
        requestHandler z = new requestHandler("light", "led", false, index, intensitate, 13f, X - 1, Y, Z - 1, culoare);
        graphicEngine.requestY2_X.add(z);
    }

    public void onX_Y2()
    {
        requestHandler z = new requestHandler("light","led",true,index,intensitate,13f,X-1,Y,Z-1,culoare);
        graphicEngine.requestX_Y2.add(z);
    }

    public void offX_Y2() {
        requestHandler z = new requestHandler("light", "led", false, index, intensitate, 13f, X - 1, Y, Z - 1, culoare);
        graphicEngine.requestX_Y2.add(z);
    }

    public void onX_Y1()
    {
        requestHandler z = new requestHandler("light","led",true,index,intensitate,13f,X-1,Y,Z-1,culoare);
        graphicEngine.requestX_Y1.add(z);
    }

    public void offX_Y1() {
        requestHandler z = new requestHandler("light", "led", false, index, intensitate, 13f, X - 1, Y, Z - 1, culoare);
        graphicEngine.requestX_Y1.add(z);
    }
}
