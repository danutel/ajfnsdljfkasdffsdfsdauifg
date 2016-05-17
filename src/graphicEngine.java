import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.effect.shapes.EmitterBoxShape;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.PointLight;
import com.jme3.light.SpotLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.DepthOfFieldFilter;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;
import com.jme3.shadow.PointLightShadowFilter;
import com.jme3.shadow.PointLightShadowRenderer;
import com.jme3.shadow.SpotLightShadowFilter;
import com.jme3.shadow.SpotLightShadowRenderer;
import com.jme3.util.SkyFactory;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.EffectBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.*;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.checkbox.builder.CheckboxBuilder;
import de.lessvoid.nifty.controls.slider.builder.SliderBuilder;
import de.lessvoid.nifty.screen.DefaultScreenController;
import org.bushe.swing.event.EventTopicSubscriber;

import java.util.ArrayList;
import java.util.List;

public class graphicEngine extends SimpleApplication implements ActionListener{

    public static void main(String[] args) {

    }
    private BulletAppState bulletAppState;
    private CharacterControl player;
    private Spatial map,bloc,led;
    private Spatial bec;
    private Spatial detector_fum;
    private Spatial stropitoare;
    private Spatial vent;
    private Spatial barbat1;
    private Spatial femeie1;
    private PointLight [] light = new PointLight[21];
    private PointLightShadowRenderer [] dlsr = new PointLightShadowRenderer[21];
    private PointLightShadowFilter [] dlsf = new PointLightShadowFilter[21];
    private SpotLightShadowRenderer[] dlsr_led=new SpotLightShadowRenderer[86];
    private SpotLightShadowFilter[] dlsf_led=new  SpotLightShadowFilter[86];
    private ParticleEmitter [] fire = new ParticleEmitter[21];
    private ParticleEmitter [] water = new ParticleEmitter[61];
    private ParticleEmitter [] smoke = new ParticleEmitter[21];
    private BitmapText hudText,hudText2,hudText3,hudText23,hudText33,hudText4,hudText5,hudText6,hudText7,hudText8,hudText9,hudText12,hudText13,hudText14
            ,hudText15,hudText16,hudText17,hudText18,hudText19;
    private boolean left = false, right = false, up = false, down = false,camera=false,tp=false;
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    private Vector3f walkDirection = new Vector3f();
    public static String locatie = "";
    public static List<requestHandler> request = new ArrayList<requestHandler>();
    public static List<requestHandler> requestA_X = new ArrayList<requestHandler>();
    public static List<requestHandler> requestB_X = new ArrayList<requestHandler>();
    public static List<requestHandler> requestY2_X = new ArrayList<requestHandler>();
    public static List<requestHandler> requestX_Y2 = new ArrayList<requestHandler>();
    public static List<requestHandler> requestX_Y1 = new ArrayList<requestHandler>();
    private SpotLight[] lumina_leduri = new SpotLight[86];
    public static double temperatura_interior;
    public static double umiditate;
    public static float fum;
    public static float foc;
    public static float temperatura_exterior;
    public static float lumina;
    public static  boolean curent_electric;
    public static  boolean lumini_urgenta;
    public static  boolean sprinkler;
    public static boolean alarma_incendiu;
    public static double ventilatie;
    public static double CO2;
    private Nifty nifty;
    public static float referinta_temperatura,referinta_umiditate,referinta_CO2;
    public static float numar_oameni, temp_exterior, umiditate_exterior, comanda_racire, comanda_incalzire, comanda_ventilatie, comanda_umidificator;
    private boolean gui=false;
    private boolean electricitate_activated=true,sprinklers_activated=false,lumini_urgenta_activated=false;
    public static boolean loaded = false;
    public static int nr_oameni_setor_A;
    public static int nr_oameni_setor_B;
    public static int nr_oameni_setor_C;
    private int counter;



    @Override
    public void simpleInitApp() {

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        Spatial sky = SkyFactory.createSky(assetManager, "Textures/Sky/Bright/FullskiesBlueClear03.dds", false);
        sky.setLocalScale(1000);
        rootNode.attachChild(sky);
        rootNode.setShadowMode(RenderQueue.ShadowMode.Off);

        load_interfata();
        loadmap();
        load_hud();
        lightSetup();
        cameraSetup();
        load_player();
        setUpKeys();
        hud();
    }

    private void load_interfata() {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        guiViewPort.addProcessor(niftyDisplay);
        int h=settings.getHeight();
        int w=settings.getWidth();
        int panel1_w =(w/2)-300;

        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");

        nifty.addScreen("test", new ScreenBuilder("Hello Nifty Screen") {{
            controller(new DefaultScreenController()); // Screen properties

            // <layer>
            layer(new LayerBuilder("Layer_ID") {{
                childLayoutHorizontal(); // layer properties, add more...

                width(w+"px");
                height("300px");

                panel(new PanelBuilder("gol"){{
                    childLayoutVertical(); // panel properties, add more...
                    width("205px");
                    height("400px");
                    alignLeft();
                    valignBottom();
                    style("nifty-panel-no-shadow");

                    control(new ButtonBuilder("info","Informatii"){{
                        height("40px");
                        width("100%");
                        focusable(false);
                    }});
                }});

                panel(new PanelBuilder("lala") {
                    {
                        childLayoutHorizontal(); // panel properties, add more...
                        width(((w/2)-300)+"px");
                        style("nifty-panel-no-shadow");
                        height("200px");
                        alignCenter();
                        valignBottom();

                        panel(new PanelBuilder("manual") {
                            {
                                childLayoutVertical(); // panel properties, add more...
                                if(w>1366)
                                    width("200px");
                                else
                                    width("50%");
                                height("100%");
                                alignCenter();
                                valignBottom();

                                panel(new PanelBuilder("manual_check") {
                                    {
                                        childLayoutHorizontal();
                                        width("100%");
                                        height("14%");

                                        panel(new PanelBuilder("goll"){{
                                            width("5%");
                                        }});

                                        control(new ButtonBuilder("manual", "Comanda Manuala") {{

                                            alignLeft();
                                            valignCenter();
                                            height("80%");
                                            width("60%");
                                            this.onActiveEffect(new EffectBuilder("nimic"));
                                            this.focusable(false);
                                        }});

                                        panel(new PanelBuilder("goll"){{
                                            width("10%");
                                        }});

                                        control(new CheckboxBuilder("manual_activated") {{
                                            alignRight();
                                            valignCenter();
                                            this.focusable(false);
                                            width("20px");
                                            height("20px");
                                        }});

                                        panel(new PanelBuilder("goll"){{
                                            width("5%");
                                        }});
                                    }});

                                panel(new PanelBuilder("Electricitate") {
                                    {
                                        childLayoutHorizontal();
                                        width("100%");
                                        height("14%");

                                        panel(new PanelBuilder("goll"){{
                                            width("5%");
                                        }});

                                        control(new ButtonBuilder("manual1", "Electricitate") {{

                                            alignLeft();
                                            valignCenter();
                                            height("80%");
                                            width("60%");
                                            this.onActiveEffect(new EffectBuilder("nimic"));
                                            this.focusable(false);
                                        }});

                                        panel(new PanelBuilder("goll"){{
                                            width("10%");
                                        }});

                                        control(new CheckboxBuilder("electricitate_activated") {{
                                            alignRight();
                                            valignCenter();
                                            this.focusable(false);
                                            this.checked(true);
                                            width("20px");
                                            height("20px");
                                        }});

                                        panel(new PanelBuilder("goll"){{
                                            width("5%");
                                        }});
                                    }});

                                panel(new PanelBuilder("lumini_urgenta") {
                                    {
                                        childLayoutHorizontal();
                                        width("100%");
                                        height("14%");

                                        panel(new PanelBuilder("goll"){{
                                            width("5%");
                                        }});

                                        control(new ButtonBuilder("manual2", "Lumini urgenta") {{

                                            alignLeft();
                                            valignCenter();
                                            height("80%");
                                            width("60%");
                                            this.onActiveEffect(new EffectBuilder("nimic"));
                                            this.focusable(false);
                                        }});

                                        panel(new PanelBuilder("goll"){{
                                            width("10%");
                                        }});

                                        control(new CheckboxBuilder("lumini_urgenta_activated") {{
                                            alignRight();
                                            valignCenter();
                                            this.focusable(false);
                                            width("20px");
                                            height("20px");
                                        }});

                                        panel(new PanelBuilder("goll"){{
                                            width("5%");
                                        }});
                                    }});

                                panel(new PanelBuilder("sprinklers") {
                                    {
                                        childLayoutHorizontal();
                                        width("100%");
                                        height("14%");

                                        panel(new PanelBuilder("goll"){{
                                            width("5%");
                                        }});

                                        control(new ButtonBuilder("manual3", "Sprinklere") {{

                                            alignLeft();
                                            valignCenter();
                                            height("80%");
                                            width("60%");
                                            this.onActiveEffect(new EffectBuilder("nimic"));
                                            this.focusable(false);
                                        }});

                                        panel(new PanelBuilder("goll"){{
                                            width("10%");
                                        }});

                                        control(new CheckboxBuilder("sprinklers_activated") {{
                                            alignRight();
                                            valignCenter();
                                            this.focusable(false);
                                            width("20px");
                                            height("20px");
                                        }});

                                        panel(new PanelBuilder("goll"){{
                                            width("5%");
                                        }});
                                    }});

                                panel(new PanelBuilder("umiditate") {
                                    {
                                        childLayoutVertical();
                                        width("100%");
                                        height("25%");

                                        control(new ButtonBuilder("manual4", "Comanda umidificator") {{
                                            alignLeft();
                                            height("40%");
                                            width("90%");
                                            this.onActiveEffect(new EffectBuilder("nimic"));
                                            this.focusable(false);
                                        }});


                                        control(new SliderBuilder("comanda_umidificator", false) {{
                                            alignLeft();
                                            this.focusable(false);
                                            width("90%");
                                            height("30%");
                                            buttonStepSize(5f);
                                        }});
                                    }});

                                panel(new PanelBuilder("ventilatie") {
                                    {
                                        childLayoutVertical();
                                        width("100%");
                                        height("25%");

                                        control(new ButtonBuilder("manual5", "Comanda ventilatie") {{
                                            alignLeft();
                                            height("40%");
                                            width("90%");
                                            this.onActiveEffect(new EffectBuilder("nimic"));
                                            this.focusable(false);
                                        }});


                                        control(new SliderBuilder("comanda_ventilatie", false) {{
                                            alignLeft();
                                            this.focusable(false);
                                            width("90%");
                                            height("30%");
                                            buttonStepSize(5f);
                                        }});
                                    }});

                            }});

                        if(w>1366)
                        {
                            panel(new PanelBuilder("lumina")
                            {{
                                height("100%");
                                width(panel1_w-420+"px");
                                childLayoutVertical();

                                panel(new PanelBuilder("degeaba"){{
                                    height("5%");
                                }});

                                control(new ButtonBuilder("lumina","Lumina")
                                {{
                                    alignCenter();
                                    width("80%");
                                    focusable(false);
                                    height("15%");
                                }});

                                panel(new PanelBuilder("degeaba"){{
                                    height("5%");
                                }});

                                control(new SliderBuilder("luminozitate",true){{
                                    height("75%");
                                    stepSize(5f);
                                    focusable(false);
                                }});
                            }});
                        }

                        panel(new PanelBuilder("manual_2") {
                            {
                                childLayoutVertical(); // panel properties, add more...
                                if(w>1366)
                                    width("200px");
                                else
                                    width("50%");
                                height("100%");
                                alignCenter();
                                valignBottom();

                                panel(new PanelBuilder("goll"){{
                                    height("4%");
                                }});

                                panel(new PanelBuilder("incalzire") {
                                    {
                                        childLayoutVertical();
                                        width("100%");
                                        height("25%");

                                        control(new ButtonBuilder("manual6", "Comanda incalzire") {{
                                            alignRight();
                                            height("40%");
                                            width("90%");
                                            this.onActiveEffect(new EffectBuilder("nimic"));
                                            this.focusable(false);
                                        }});


                                        control(new SliderBuilder("comanda_incalzire", false) {{
                                            alignRight();
                                            this.focusable(false);
                                            width("90%");
                                            height("30%");
                                            buttonStepSize(5f);
                                        }});
                                    }});

                                panel(new PanelBuilder("racire") {
                                    {
                                        childLayoutVertical();
                                        width("100%");
                                        height("25%");

                                        control(new ButtonBuilder("manual7", "Comanda racire") {{
                                            alignRight();
                                            height("40%");
                                            width("90%");
                                            this.onActiveEffect(new EffectBuilder("nimic"));
                                            this.focusable(false);
                                        }});


                                        control(new SliderBuilder("comanda_racire", false) {{
                                            alignRight();
                                            this.focusable(false);
                                            width("90%");
                                            height("30%");
                                            buttonStepSize(5f);
                                        }});
                                    }});
                            }});
                    }});

                // <panel>
                panel(new PanelBuilder("Referinte") {{
                    childLayoutVertical(); // panel properties, add more...
                    width("200px");
                    style("nifty-panel-no-shadow");
                    height("250px");
                    alignCenter();
                    valignBottom();
                    x("400px");

                    // GUI elements
                    control(new ButtonBuilder("nimic","Referinte (Control Automat)") {{
                        alignCenter();
                        valignCenter();
                        height("15%");
                        width("100%");
                        this.onActiveEffect(new EffectBuilder("nimic"));
                    }});
                    panel(new PanelBuilder("degeaba"){{
                        height("20px");
                    }});

                    control(new ButtonBuilder("buton1","Temperatura: ") {{
                        alignCenter();
                        valignCenter();
                        height("12%");
                        width("100%");
                        this.focusable(false);
                    }});

                    control(new SliderBuilder("slider1", false) {{
                        alignCenter();
                        valignCenter();
                        height("15%");
                        width("100%");
                        this.buttonStepSize(0.5f);
                        this.min(10f);
                        this.max(30f);
                        this.focusable(false);
                    }});

                    control(new ButtonBuilder("buton2","Umiditate: ") {{
                        alignCenter();
                        valignBottom();
                        height("12%");
                        width("100%");
                        this.focusable(false);
                    }});

                    control(new SliderBuilder("slider2", false) {{
                        alignCenter();
                        valignBottom();
                        height("15%");
                        width("100%");
                        this.buttonStepSize(1f);
                        this.min(30f);
                        this.max(60f);
                        this.focusable(false);
                    }});

                    control(new ButtonBuilder("buton3","CO2: ") {{
                        alignCenter();
                        valignTop();
                        height("12%");
                        width("100%");
                        this.focusable(false);
                    }});

                    control(new SliderBuilder("slider3", false) {{
                        alignCenter();
                        valignTop();
                        height("15%");
                        width("100%");
                        this.buttonStepSize(25f);
                        this.min(300f);
                        this.max(1000f);
                        this.focusable(false);
                    }});
                    //.. add more GUI elements here


                }});
                // </panel>

                panel(new PanelBuilder("dadas") {
                    {
                        childLayoutHorizontal(); // panel properties, add more...
                        width(((w/2)-225)+"px");
                        style("nifty-panel-no-shadow");
                        height("200px");
                        alignLeft();
                        valignBottom();
                        panel(new PanelBuilder("setari") {
                            {
                                childLayoutVertical(); // panel properties, add more...
                                width("220px");
                                alignLeft();
                                valignBottom();

                                panel(new PanelBuilder("goll"){{
                                    height("4%");
                                }});

                                panel(new PanelBuilder("temperatura_ext") {
                                    {
                                        childLayoutVertical();
                                        width("90%");
                                        height("25%");

                                        control(new ButtonBuilder("manual8", "Temp. exterior") {{
                                            alignLeft();
                                            height("40%");
                                            width("90%");
                                            this.onActiveEffect(new EffectBuilder("nimic"));
                                            this.focusable(false);
                                        }});


                                        control(new SliderBuilder("temp_ext", false) {{
                                            alignLeft();
                                            this.focusable(false);
                                            width("90%");
                                            height("30%");
                                            buttonStepSize(0.5f);
                                            min(-20);max(40);
                                        }});
                                    }});

                                panel(new PanelBuilder("umiditate_ext") {
                                    {
                                        childLayoutVertical();
                                        width("90%");
                                        height("25%");

                                        control(new ButtonBuilder("manual18", "U.R. exterior") {{
                                            alignLeft();
                                            height("40%");
                                            width("90%");
                                            this.onActiveEffect(new EffectBuilder("nimic"));
                                            this.focusable(false);
                                        }});


                                        control(new SliderBuilder("umiditate_exterior", false) {{
                                            alignLeft();
                                            this.focusable(false);
                                            width("90%");
                                            height("30%");
                                            buttonStepSize(0.5f);
                                            min(10);max(90);
                                        }});
                                    }});

                                panel(new PanelBuilder("oameni") {
                                    {
                                        childLayoutVertical();
                                        width("90%");
                                        height("25%");

                                        control(new ButtonBuilder("manual9", "Numar oameni") {{
                                            alignLeft();
                                            height("40%");
                                            width("90%");
                                            this.onActiveEffect(new EffectBuilder("nimic"));
                                            this.focusable(false);
                                        }});


                                        control(new SliderBuilder("numar_oameni", false) {{
                                            alignLeft();
                                            this.focusable(false);
                                            width("90%");
                                            height("30%");
                                            buttonStepSize(1f);
                                            max(50);
                                        }});
                                    }});
                            }});



                        panel(new PanelBuilder("setari2") {
                            {
                                childLayoutVertical(); // panel properties, add more...
                                width("220px");
                                alignLeft();
                                valignBottom();

                                panel(new PanelBuilder("golll"){{
                                    height("4%");
                                }});



                                panel(new PanelBuilder("Iesire1") {
                                    {
                                        childLayoutVertical();
                                        width("90%");
                                        height("25%");

                                        control(new ButtonBuilder("manual180", "Nr. oameni sector A") {{
                                            alignLeft();
                                            height("40%");
                                            width("90%");
                                            this.onActiveEffect(new EffectBuilder("nimic"));
                                            this.focusable(false);
                                        }});


                                        control(new SliderBuilder("sectorA", false) {{
                                            alignLeft();
                                            this.focusable(false);
                                            width("90%");
                                            height("30%");
                                            buttonStepSize(0.5f);
                                            min(1);max(99);
                                        }});
                                    }});

                                panel(new PanelBuilder("Iesire2") {
                                    {
                                        childLayoutVertical();
                                        width("90%");
                                        height("25%");

                                        control(new ButtonBuilder("manual9", "Nr. oameni sector B") {{
                                            alignLeft();
                                            height("40%");
                                            width("90%");
                                            this.onActiveEffect(new EffectBuilder("nimic"));
                                            this.focusable(false);
                                        }});


                                        control(new SliderBuilder("sectorB", false) {{
                                            alignLeft();
                                            this.focusable(false);
                                            width("90%");
                                            height("30%");
                                            buttonStepSize(1f);
                                            min(1);max(99);
                                        }});
                                    }});

                                panel(new PanelBuilder("Iesire3") {
                                    {
                                        childLayoutVertical();
                                        width("90%");
                                        height("25%");

                                        control(new ButtonBuilder("manual9", "Nr. oameni sector C") {{
                                            alignLeft();
                                            height("40%");
                                            width("90%");
                                            this.onActiveEffect(new EffectBuilder("nimic"));
                                            this.focusable(false);
                                        }});


                                        control(new SliderBuilder("sectorC", false) {{
                                            alignLeft();
                                            this.focusable(false);
                                            width("90%");
                                            height("30%");
                                            buttonStepSize(1f);
                                            min(1);max(99);
                                        }});
                                    }});
                            }});
                    }});

                panel(new PanelBuilder("onlines") {
                    {
                        childLayoutVertical(); // panel properties, add more...
                        width("120px");
                        style("nifty-panel-no-shadow");
                        height("400px");
                        alignCenter();
                        valignBottom();
                        control(new ButtonBuilder("Online","Online"){{
                            width("100%");
                            height("40px");
                            focusable(false);
                        }});
                    }});
            }});
            // </layer>

        }}.build(nifty));

        nifty.subscribe(nifty.getCurrentScreen(), "slider1", SliderChangedEvent.class, eventHandler1);
        nifty.subscribe(nifty.getCurrentScreen(), "slider2", SliderChangedEvent.class, eventHandler2);
        nifty.subscribe(nifty.getCurrentScreen(), "slider3", SliderChangedEvent.class, eventHandler3);
        nifty.subscribe(nifty.getCurrentScreen(), "numar_oameni", SliderChangedEvent.class, eventHandler4);
        nifty.subscribe(nifty.getCurrentScreen(), "umiditate_exterior", SliderChangedEvent.class, eventHandler5);
        nifty.subscribe(nifty.getCurrentScreen(), "temp_ext", SliderChangedEvent.class, eventHandler6);
        nifty.subscribe(nifty.getCurrentScreen(), "comanda_racire", SliderChangedEvent.class, eventHandler7);
        nifty.subscribe(nifty.getCurrentScreen(), "comanda_incalzire", SliderChangedEvent.class, eventHandler8);
        nifty.subscribe(nifty.getCurrentScreen(), "comanda_ventilatie", SliderChangedEvent.class, eventHandler9);
        nifty.subscribe(nifty.getCurrentScreen(), "comanda_umidificator", SliderChangedEvent.class, eventHandler10);
        nifty.subscribe(nifty.getCurrentScreen(), "manual_activated",   CheckBoxStateChangedEvent.class, eventHandler11);
        nifty.subscribe(nifty.getCurrentScreen(), "electricitate_activated",   CheckBoxStateChangedEvent.class, eventHandler12);
        nifty.subscribe(nifty.getCurrentScreen(), "lumini_urgenta_activated",   CheckBoxStateChangedEvent.class, eventHandler13);
        nifty.subscribe(nifty.getCurrentScreen(), "sprinklers_activated",   CheckBoxStateChangedEvent.class, eventHandler14);
        nifty.subscribe(nifty.getCurrentScreen(), "sectorA", SliderChangedEvent.class, eventHandler15);
        nifty.subscribe(nifty.getCurrentScreen(), "sectorB", SliderChangedEvent.class, eventHandler16);
        nifty.subscribe(nifty.getCurrentScreen(), "sectorC", SliderChangedEvent.class, eventHandler17);

        nifty.gotoScreen("test");

    }


    EventTopicSubscriber<SliderChangedEvent> eventHandler15 = new EventTopicSubscriber<SliderChangedEvent>() {
        @Override
        public void onEvent(final String topic, final SliderChangedEvent event) {
            if(!environment_hol.alarma_incendiu)
                nr_oameni_setor_A = (int)nifty.getCurrentScreen().findNiftyControl("sectorA", Slider.class).getValue();
        }
    };

    EventTopicSubscriber<SliderChangedEvent> eventHandler16 = new EventTopicSubscriber<SliderChangedEvent>() {
        @Override
        public void onEvent(final String topic, final SliderChangedEvent event) {
            if(!environment_hol.alarma_incendiu)
                nr_oameni_setor_B = (int) nifty.getCurrentScreen().findNiftyControl("sectorB", Slider.class).getValue();
        }
    };

    EventTopicSubscriber<SliderChangedEvent> eventHandler17 = new EventTopicSubscriber<SliderChangedEvent>() {
        @Override
        public void onEvent(final String topic, final SliderChangedEvent event) {
            if(!environment_hol.alarma_incendiu)
                nr_oameni_setor_C = (int) nifty.getCurrentScreen().findNiftyControl("sectorC", Slider.class).getValue();
        }
    };

    EventTopicSubscriber<CheckBoxStateChangedEvent> eventHandler14 = new EventTopicSubscriber<CheckBoxStateChangedEvent>() {
        @Override
        public void onEvent(String s, CheckBoxStateChangedEvent checkBoxStateChangedEvent) {
            if(sprinklers_activated)
            {
                environment.sprinkler=false;
                sprinklers_activated=false;
            }
            else
            {
                environment.sprinkler=true;
                sprinklers_activated=true;
            }
        }
    };

    EventTopicSubscriber<CheckBoxStateChangedEvent> eventHandler13 = new EventTopicSubscriber<CheckBoxStateChangedEvent>() {
        @Override
        public void onEvent(String s, CheckBoxStateChangedEvent checkBoxStateChangedEvent) {
            if(lumini_urgenta_activated)
            {
                environment.lumini_urgenta=false;
                lumini_urgenta_activated=false;
            }
            else
            {
                environment.lumini_urgenta=true;
                lumini_urgenta_activated=true;
            }
        }
    };

    EventTopicSubscriber<CheckBoxStateChangedEvent> eventHandler12 = new EventTopicSubscriber<CheckBoxStateChangedEvent>() {
        @Override
        public void onEvent(String s, CheckBoxStateChangedEvent checkBoxStateChangedEvent) {
            if(electricitate_activated)
            {
                environment.curent_electric=true;
                electricitate_activated=false;
            }
            else
            {
                environment.curent_electric=false;
                electricitate_activated=true;
            }
        }
    };

    EventTopicSubscriber<CheckBoxStateChangedEvent> eventHandler11 = new EventTopicSubscriber<CheckBoxStateChangedEvent>() {
        @Override
        public void onEvent(String s, CheckBoxStateChangedEvent checkBoxStateChangedEvent) {
            if(!controller.disabled){
                controller.disabled = true;
                System.out.println("Controller disabled");
                nifty.getCurrentScreen().findNiftyControl("comanda_racire", Slider.class).enable();
                nifty.getCurrentScreen().findNiftyControl("comanda_incalzire", Slider.class).enable();
                nifty.getCurrentScreen().findNiftyControl("comanda_ventilatie", Slider.class).enable();
                nifty.getCurrentScreen().findNiftyControl("comanda_umidificator", Slider.class).enable();
                nifty.getCurrentScreen().findNiftyControl("electricitate_activated", CheckBox.class).enable();
                nifty.getCurrentScreen().findNiftyControl("lumini_urgenta_activated", CheckBox.class).enable();
                nifty.getCurrentScreen().findNiftyControl("sprinklers_activated", CheckBox.class).enable();
            }else
            {
                controller.disabled = false;
                nifty.getCurrentScreen().findNiftyControl("comanda_racire", Slider.class).disable();
                nifty.getCurrentScreen().findNiftyControl("comanda_incalzire", Slider.class).disable();
                nifty.getCurrentScreen().findNiftyControl("comanda_ventilatie", Slider.class).disable();
                nifty.getCurrentScreen().findNiftyControl("comanda_umidificator", Slider.class).disable();
                nifty.getCurrentScreen().findNiftyControl("electricitate_activated", CheckBox.class).disable();
                nifty.getCurrentScreen().findNiftyControl("lumini_urgenta_activated", CheckBox.class).disable();
                nifty.getCurrentScreen().findNiftyControl("sprinklers_activated", CheckBox.class).disable();
                System.out.println("Controller enabled");
            }
        }
    };

    EventTopicSubscriber<SliderChangedEvent> eventHandler1 = new EventTopicSubscriber<SliderChangedEvent>() {
        @Override
        public void onEvent(final String topic, final SliderChangedEvent event) {
            referinta_temperatura = nifty.getCurrentScreen().findNiftyControl("slider1", Slider.class).getValue();
            String value = String.valueOf(referinta_temperatura);
            nifty.getCurrentScreen().findNiftyControl("buton1", Button.class).setText("Temperatura: "+value+" °C");
        }
    };

    EventTopicSubscriber<SliderChangedEvent> eventHandler2 = new EventTopicSubscriber<SliderChangedEvent>() {
        @Override
        public void onEvent(final String topic, final SliderChangedEvent event) {
            referinta_umiditate = nifty.getCurrentScreen().findNiftyControl("slider2", Slider.class).getValue();
            String value = String.valueOf(referinta_umiditate);
            nifty.getCurrentScreen().findNiftyControl("buton2", Button.class).setText("Umiditate: "+value+"% UR");
        }
    };

    EventTopicSubscriber<SliderChangedEvent> eventHandler3 = new EventTopicSubscriber<SliderChangedEvent>() {
        @Override
        public void onEvent(final String topic, final SliderChangedEvent event) {
            referinta_CO2 = nifty.getCurrentScreen().findNiftyControl("slider3", Slider.class).getValue();
            String value = String.valueOf(referinta_CO2);
            nifty.getCurrentScreen().findNiftyControl("buton3", Button.class).setText("CO2: "+value+" PPM");
        }
    };

    EventTopicSubscriber<SliderChangedEvent> eventHandler4 = new EventTopicSubscriber<SliderChangedEvent>() {
        @Override
        public void onEvent(final String topic, final SliderChangedEvent event) {
            numar_oameni = nifty.getCurrentScreen().findNiftyControl("numar_oameni", Slider.class).getValue();
            String value = String.valueOf(numar_oameni);
            nifty.getCurrentScreen().findNiftyControl("manual9", Button.class).setText("Numar oameni: "+value);
        }
    };

    EventTopicSubscriber<SliderChangedEvent> eventHandler5 = new EventTopicSubscriber<SliderChangedEvent>() {
        @Override
        public void onEvent(final String topic, final SliderChangedEvent event) {
            umiditate_exterior = nifty.getCurrentScreen().findNiftyControl("umiditate_exterior", Slider.class).getValue();
            String value = String.valueOf(umiditate_exterior);
            nifty.getCurrentScreen().findNiftyControl("manual18", Button.class).setText("U.R. exterior: "+value);
        }
    };

    EventTopicSubscriber<SliderChangedEvent> eventHandler6 = new EventTopicSubscriber<SliderChangedEvent>() {
        @Override
        public void onEvent(final String topic, final SliderChangedEvent event) {
            temp_exterior = nifty.getCurrentScreen().findNiftyControl("temp_ext", Slider.class).getValue();
            String value = String.valueOf(temp_exterior);
            nifty.getCurrentScreen().findNiftyControl("manual8", Button.class).setText("Temp. exterior: "+value);
        }
    };

    EventTopicSubscriber<SliderChangedEvent> eventHandler7 = new EventTopicSubscriber<SliderChangedEvent>() {
        @Override
        public void onEvent(final String topic, final SliderChangedEvent event) {
            comanda_racire = nifty.getCurrentScreen().findNiftyControl("comanda_racire", Slider.class).getValue();
            String value = String.valueOf(comanda_racire/100);
            nifty.getCurrentScreen().findNiftyControl("manual7", Button.class).setText("Comanda racire: "+value);
        }
    };

    EventTopicSubscriber<SliderChangedEvent> eventHandler8 = new EventTopicSubscriber<SliderChangedEvent>() {
        @Override
        public void onEvent(final String topic, final SliderChangedEvent event) {
            comanda_incalzire = nifty.getCurrentScreen().findNiftyControl("comanda_incalzire", Slider.class).getValue();
            String value = String.valueOf(comanda_incalzire/100);
            nifty.getCurrentScreen().findNiftyControl("manual6", Button.class).setText("Comanda incalzire: "+value);
        }
    };

    EventTopicSubscriber<SliderChangedEvent> eventHandler9 = new EventTopicSubscriber<SliderChangedEvent>() {
        @Override
        public void onEvent(final String topic, final SliderChangedEvent event) {
            comanda_ventilatie = nifty.getCurrentScreen().findNiftyControl("comanda_ventilatie", Slider.class).getValue();
            String value = String.valueOf(comanda_ventilatie/100);
            nifty.getCurrentScreen().findNiftyControl("manual5", Button.class).setText("Comanda ventilatie: "+value);
        }
    };

    EventTopicSubscriber<SliderChangedEvent> eventHandler10 = new EventTopicSubscriber<SliderChangedEvent>() {
        @Override
        public void onEvent(final String topic, final SliderChangedEvent event) {
            comanda_umidificator = nifty.getCurrentScreen().findNiftyControl("comanda_umidificator", Slider.class).getValue();
            String value = String.valueOf(comanda_umidificator/100);
            nifty.getCurrentScreen().findNiftyControl("manual4", Button.class).setText("Comanda umidificator: "+value);
        }
    };

    public void loadmap()
    {
        //load_object(new requestHandler("load","Modele\\Harta\\fac.zip","6.mesh.j3o", "map",-420,0,-109,7f,7f,7f,0,0,0,0));//harta
        load_object(new requestHandler("load","Modele\\Harta\\fac.zip","1.mesh.j3o", "map",-144,14,-1637,7f,7f,7f,0,0,0,0));//facultate
        /*load_object(new requestHandler("load","Modele\\Harta\\9.zip","9.mesh.j3o", "bloc",81,-12,-2603,7f,7f,7f,0,0,0,0));//parcare+terenuri
        load_object(new requestHandler("load","Modele\\Harta\\7.zip","7.mesh.j3o", "bloc",2029,10,-415,7f,7f,7f,0,0,0,0));//cladiri cercetare
        load_object(new requestHandler("load","Modele\\Harta\\8.zip","8.mesh.j3o", "bloc",1750,10,-2600,7f,7f,7f,0,0,0,0));//cladiri cercetare
        load_object(new requestHandler("load","Modele\\Harta\\fac.zip","5.mesh.j3o", "bloc",-150,12,-280,7f,7f,7f,0,0,0,0));//parc+stadion
        load_object(new requestHandler("load","Modele\\Harta\\12.zip","12.mesh.j3o", "bloc",270,8,-489,7f,7f,7f,0,0,0,0));//harta
        load_object(new requestHandler("load","Modele\\Harta\\13.zip","13.mesh.j3o", "bloc",270,8,-489,7f,7f,7f,0,0,0,0));//harta
        load_object(new requestHandler("load","Modele\\Harta\\14.zip","14.mesh.j3o", "bloc",2000,10,-1755,7f,7f,7f,0,0,0,0));//cladiri cercetare
        load_object(new requestHandler("load","Modele\\Harta\\15.zip","15.mesh.j3o", "bloc",2000,10,-1755,7f,7f,7f,0,0,0,0));//cladiri cercetare
        load_object(new requestHandler("load","Modele\\Harta\\16.zip","16.mesh.j3o", "bloc",2000,5,-1755,7f,7f,7f,0,0,0,0));//cladiri cercetare
        load_object(new requestHandler("load","Modele\\Harta\\17.zip","173.mesh.j3o", "bloc",2440,13,-1555,7f,7f,7f,0,0,0,0));//cladiri cercetare
        load_object(new requestHandler("load","Modele\\Harta\\17.zip","173.mesh.j3o", "bloc",3170,13,-1555,7f,7f,7f,0,0,0,0));//cladiri cercetare
        load_object(new requestHandler("load","Modele\\Harta\\17.zip","173.mesh.j3o", "bloc",2440,13,-820,7f,7f,7f,0,0,0,0));//cladiri cercetare
        load_object(new requestHandler("load","Modele\\Harta\\17.zip","173.mesh.j3o", "bloc",3170,13,-820,7f,7f,7f,0,0,0,0));//cladiri cercetare*/

        for(int i = 1288;i>=869;i=i-22)
        {
            load_object(new requestHandler("load","Modele\\Harta\\spot.zip","bec2.j3o", "map",i,149.8f,-1907,1f,1f,1f,0,0,0,0));
            load_object(new requestHandler("load","Modele\\Harta\\spot.zip","bec2.j3o", "map",i,117.2f,-1907,1f,1f,1f,0,0,0,0));
        }

        for(int i = 1912;i<=1979;i=i+22)
        {
            load_object(new requestHandler("load","Modele\\Harta\\spot.zip","bec2.j3o", "map",867,149.8f,-i,1f,1f,1f,0,1.57f,0,0));
            load_object(new requestHandler("load","Modele\\Harta\\spot.zip","bec2.j3o", "map",867,117.2f,-i,1f,1f,1f,0,1.57f,0,0));
        }

        for(int i = 1806;i<=2160;i=i+22)
        {
            load_object(new requestHandler("load","Modele\\Harta\\spot.zip","bec2.j3o", "map",1010,149.8f,-i,1f,1f,1f,0,1.57f,0,0));
            load_object(new requestHandler("load","Modele\\Harta\\spot.zip","bec2.j3o", "map",1010,117.2f,-i,1f,1f,1f,0,1.57f,0,0));
        }

    }

    public void load_hud(){
        setDisplayStatView(false);
        int offset=330;

        BitmapFont myFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        hudText = new BitmapText(myFont, false);
        hudText.setSize(15);
        hudText.setColor(ColorRGBA.Green);           // the text
        hudText.setLocalTranslation(25, offset, 300); // position
        guiNode.attachChild(hudText);

        hudText2 = new BitmapText(myFont, false);
        hudText2.setSize(15);
        hudText2.setColor(ColorRGBA.Green);           // the text
        hudText2.setLocalTranslation(25, offset-20, 300); // position
        guiNode.attachChild(hudText2);

        hudText3 = new BitmapText(myFont, false);
        hudText3.setSize(15);
        hudText3.setColor(ColorRGBA.Green);           // the text
        hudText3.setLocalTranslation(25, offset-60, 300); // position
        guiNode.attachChild(hudText3);

        hudText23 = new BitmapText(myFont, false);
        hudText23.setSize(15);
        hudText23.setColor(ColorRGBA.Green);           // the text
        hudText23.setLocalTranslation(25, offset-80, 300); // position
        guiNode.attachChild(hudText23);

        hudText33 = new BitmapText(myFont, false);
        hudText33.setSize(15);
        hudText33.setColor(ColorRGBA.Green);           // the text
        hudText33.setLocalTranslation(25, offset-100, 300); // position
        guiNode.attachChild(hudText33);

        hudText4 = new BitmapText(myFont, false);
        hudText4.setSize(15);
        hudText4.setColor(ColorRGBA.Green);           // the text
        hudText4.setLocalTranslation(25, offset-140, 300); // position
        guiNode.attachChild(hudText4);

        hudText5 = new BitmapText(myFont, false);
        hudText5.setSize(15);
        hudText5.setColor(ColorRGBA.Green);           // the text
        hudText5.setLocalTranslation(25, offset-160, 300); // position
        guiNode.attachChild(hudText5);

        hudText6 = new BitmapText(myFont, false);
        hudText6.setSize(15);
        hudText6.setColor(ColorRGBA.Green);           // the text
        hudText6.setLocalTranslation(25, offset-180, 300); // position
        guiNode.attachChild(hudText6);

        hudText7 = new BitmapText(myFont, false);
        hudText7.setSize(15);
        hudText7.setColor(ColorRGBA.Green);           // the text
        hudText7.setLocalTranslation(25, offset-200, 300); // position
        guiNode.attachChild(hudText7);

        hudText8 = new BitmapText(myFont, false);
        hudText8.setSize(15);
        hudText8.setColor(ColorRGBA.Green);           // the text
        hudText8.setLocalTranslation(25, offset-220, 300); // position
        guiNode.attachChild(hudText8);

        hudText9 = new BitmapText(myFont, false);
        hudText9.setSize(15);
        hudText9.setColor(ColorRGBA.Green);           // the text
        hudText9.setLocalTranslation(25, offset-240, 300); // position
        guiNode.attachChild(hudText9);

        hudText12 = new BitmapText(myFont, false);
        hudText12.setSize(15);
        hudText12.setColor(ColorRGBA.Green);           // the text
        hudText12.setLocalTranslation(settings.getWidth()-90, offset, 300); // position
        hudText12.setText("Camera 1");
        guiNode.attachChild(hudText12);

        hudText13 = new BitmapText(myFont, false);
        hudText13.setSize(15);
        hudText13.setColor(ColorRGBA.Green);           // the text
        hudText13.setLocalTranslation(settings.getWidth()-90, offset-20, 300); // position
        hudText13.setText("Camera 2");
        guiNode.attachChild(hudText13);

        hudText14 = new BitmapText(myFont, false);
        hudText14.setSize(15);
        hudText14.setColor(ColorRGBA.Green);           // the text
        hudText14.setLocalTranslation(settings.getWidth()-90, offset-40, 300); // position
        hudText14.setText("Camera 3");
        guiNode.attachChild(hudText14);

        hudText15 = new BitmapText(myFont, false);
        hudText15.setSize(15);
        hudText15.setColor(ColorRGBA.Green);           // the text
        hudText15.setLocalTranslation(settings.getWidth()-90, offset-60, 300); // position
        hudText15.setText("Camera 4");
        guiNode.attachChild(hudText15);

        hudText16 = new BitmapText(myFont, false);
        hudText16.setSize(15);
        hudText16.setColor(ColorRGBA.Green);           // the text
        hudText16.setLocalTranslation(settings.getWidth()-90, offset-80, 300); // position
        hudText16.setText("Camera 5");
        guiNode.attachChild(hudText16);

        hudText17 = new BitmapText(myFont, false);
        hudText17.setSize(15);
        hudText17.setColor(ColorRGBA.Green);           // the text
        hudText17.setLocalTranslation(settings.getWidth()-90, offset-100, 300); // position
        hudText17.setText("Camera 6");
        guiNode.attachChild(hudText17);

        hudText18 = new BitmapText(myFont, false);
        hudText18.setSize(15);
        hudText18.setColor(ColorRGBA.Green);           // the text
        hudText18.setLocalTranslation(settings.getWidth()-90, offset-120, 300); // position
        hudText18.setText("Camera 7");
        guiNode.attachChild(hudText18);

        hudText19 = new BitmapText(myFont, false);
        hudText19.setSize(15);
        hudText19.setColor(ColorRGBA.Green);           // the text
        hudText19.setLocalTranslation(settings.getWidth()-90, offset-140, 300); // position
        hudText19.setText("Server");
        guiNode.attachChild(hudText19);
    }

    public  void load_player()
    {
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 12f, 1);
        player = new CharacterControl(capsuleShape, 2.5f);
        player.setJumpSpeed(20);
        player.setFallSpeed(50);
        player.setGravity(40);
        player.setPhysicsLocation(new Vector3f(0, 10, 0));
        player.setPhysicsLocation(new Vector3f(1028,160,-2078));
        bulletAppState.getPhysicsSpace().add(player);
    }
    public void cameraSetup()
    {
        cam.setFrustumFar(4000);
        cam.onFrameChange();
        flyCam.setMoveSpeed(300);
    }
    public void lightSetup()
    {
        PointLight lamp_light2 = new PointLight();
        lamp_light2.setColor(ColorRGBA.White.mult(.2f));
        lamp_light2.setRadius(100000);
        lamp_light2.setPosition(new Vector3f(-5000,5000,5000));
        rootNode.addLight(lamp_light2);

        PointLight lamp_light = new PointLight();
        lamp_light.setColor(ColorRGBA.White.mult(.7f));
        lamp_light.setRadius(100000);
        lamp_light.setPosition(new Vector3f(5000,5000,-5000));

        final int SHADOWMAP_SIZE=1024;
        PointLightShadowRenderer dlsr = new PointLightShadowRenderer(assetManager, SHADOWMAP_SIZE);
        dlsr.setLight(lamp_light);
        dlsr.setShadowIntensity(0.4f);
        rootNode.addLight(lamp_light);
        viewPort.addProcessor(dlsr);

        PointLightShadowFilter dlsf = new PointLightShadowFilter(assetManager, SHADOWMAP_SIZE);
        dlsf.setLight(lamp_light);
        dlsf.setEnabled(true);

      //  BloomFilter bloom=new BloomFilter();

        DepthOfFieldFilter dofFilter = new DepthOfFieldFilter();
        dofFilter.setFocusDistance(0);
        dofFilter.setFocusRange(1000);
        dofFilter.setBlurScale(1f);

        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        SSAOFilter ssaoFilter = new SSAOFilter(12.94f, 43.92f, 0.33f, 0.61f);
        fpp.addFilter(dlsf);
        fpp.addFilter(ssaoFilter);
        //fpp.addFilter(bloom);
        fpp.addFilter(dofFilter);
        viewPort.addProcessor(fpp);

    }

    private void setUpKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Change_camera", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addMapping("Teleport", new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE));
        inputManager.addMapping("fire1", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("fire2", new KeyTrigger(KeyInput.KEY_2));
        inputManager.addMapping("fire3", new KeyTrigger(KeyInput.KEY_3));
        inputManager.addMapping("fire4", new KeyTrigger(KeyInput.KEY_4));
        inputManager.addMapping("fire5", new KeyTrigger(KeyInput.KEY_5));
        inputManager.addMapping("fire6", new KeyTrigger(KeyInput.KEY_6));
        inputManager.addMapping("fire", new KeyTrigger(KeyInput.KEY_H));
        inputManager.addMapping("gui", new KeyTrigger(KeyInput.KEY_F1));
        inputManager.addMapping("reset", new KeyTrigger(KeyInput.KEY_TAB));
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Jump");
        inputManager.addListener(this, "Change_camera");
        inputManager.addListener(this, "Teleport");
        inputManager.addListener(this, "fire1");
        inputManager.addListener(this, "fire2");
        inputManager.addListener(this, "fire3");
        inputManager.addListener(this, "fire4");
        inputManager.addListener(this, "fire5");
        inputManager.addListener(this, "fire6");
        inputManager.addListener(this, "fire");
        inputManager.addListener(this, "gui");
        inputManager.addListener(this, "reset");
    }

    public void onAction(String binding, boolean isPressed, float tpf) {
        if (binding.equals("Left")) {
            left = isPressed;
        } else if (binding.equals("Right")) {
            right= isPressed;
        } else if (binding.equals("Up")) {
            up = isPressed;
        } else if (binding.equals("Down"))
        {
            down = isPressed;
        }
        else if (binding.equals("Change_camera"))
        {
            camera = !camera;
        }
        else if (binding.equals("Teleport"))
        {
            player.setPhysicsLocation(cam.getLocation());
        }
        else if (binding.equals("Jump")) {
            if (isPressed) {
                player.jump();
            }
        }
        else if (binding.equals("fire1")) {
            if (isPressed) {
                environment_hol.foc[0]=1;

            }
        }
        else if (binding.equals("fire2")) {
            if (isPressed) {
                environment_hol.foc[1]=1;
            }
        }
        else if (binding.equals("fire3")) {
            if (isPressed) {
                environment_hol.foc[2]=1;
            }
        }
        else if (binding.equals("fire4")) {
            if (isPressed) {
                environment_hol.foc[3]=1;
            }
        }
        else if (binding.equals("fire5")) {
            if (isPressed) {
                environment_hol.foc[4]=.5f;
            }
        }
        else if (binding.equals("fire6")) {
            if (isPressed) {
                environment_hol.foc[5]=.1f;
            }
        }
        else if (binding.equals("fire")) {
            if (isPressed) {
                environment.foc=1;
            }
        }
        else if (binding.equals("gui")) {
            if (isPressed) {
                gui=!gui;
            }
        }
        else if (binding.equals("reset")) {
            if (isPressed) {
                environment_hol.alarma_incendiu=false;
            }
        }
    }

    public void hud(){

    }

    public void load_leds()
    {
        load_object(new requestHandler("load","Modele\\Harta\\spot.zip","bec2.j3o", "map",1050,149.8f,-2078,1f,1f,1f,0,0,0,0));


    }

    public void stropire(requestHandler x)
    {
        ParticleEmitter numeObiect = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle,200000 );
        assetManager.registerLocator("assets/stropi.zip", ZipLocator.class);
        Material stropi = assetManager.loadMaterial("stropi.j3m");
        numeObiect.setMaterial(stropi);
        numeObiect.setLocalTranslation(x.translatie_x,x.translatie_y,x.translatie_z);
        numeObiect.setEndColor(  new ColorRGBA(0.8f, 0.8f, 1.0f, 0.5f));   // red
        numeObiect.setStartColor(new ColorRGBA(0.6f, 0.6f, 1.0f, 0.0f)); // yellow
        numeObiect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 14, 0));
        numeObiect.setStartSize(0.03f);
        numeObiect.setEndSize(0.08f);
        numeObiect.setGravity(0, 80, 0);
        numeObiect.setLowLife(0.5f);
        numeObiect.setHighLife(0.6f);
        numeObiect.setRandomAngle(true);
        numeObiect.setNumParticles(100000);
        numeObiect.setParticlesPerSec(4000);
        numeObiect.getParticleInfluencer().setVelocityVariation(2f);
        if(x.pornit) {
            water[x.index] = new ParticleEmitter();
            water[x.index]=numeObiect;
            rootNode.attachChild(water[x.index]);
        }
        else
        {
            rootNode.detachChild(water[x.index]);
        }
    }

    public void foc_start(requestHandler x){
        ParticleEmitter numeObiect = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 100*(int)x.intensitate_foc);
        Material mat_red = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat_red.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/flame.png"));
        numeObiect.setMaterial(mat_red);
        numeObiect.setImagesX(2);
        numeObiect.setImagesY(2); // 2x2 texture animation
        numeObiect.setEndColor(  new ColorRGBA(1f, 0f, 0f, 1f));   // red
        numeObiect.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
        numeObiect.setInitialVelocity(new Vector3f(0, 8, 0));
        numeObiect.setStartSize((8/(float)10)*6.6f);
        numeObiect.setEndSize((8/(float)10)*0.5f);
        numeObiect.setGravity(0, 0, 0);
        numeObiect.setLowLife((x.intensitate_foc/(float)10)*2.5f);
        numeObiect.setHighLife((x.intensitate_foc/(float)10)*3.5f);
        numeObiect.setVelocityVariation((x.intensitate_foc/(float)10)*0.35f);
       // numeObiect.setQueueBucket(RenderQueue.Bucket.Translucent);
        numeObiect.setLocalTranslation((x.translatie_x-(10f*x.intensitate_foc)/2),x.translatie_y,(x.translatie_z-(15f*x.intensitate_foc)/2));
        numeObiect.setShape(new EmitterBoxShape(new Vector3f(0, 0, 0), new Vector3f(10f*x.intensitate_foc, 0.8f*x.intensitate_foc, 15f*x.intensitate_foc)));
        numeObiect.setNumParticles(1000*(int)x.intensitate_foc);
        numeObiect.setParticlesPerSec(100*x.intensitate_foc);
        if(x.pornit) {
            if(fire[x.index]!=null)
            {
                rootNode.detachChild(fire[x.index]);
            }
            fire[x.index] = new ParticleEmitter();
            fire[x.index]=numeObiect;
            rootNode.attachChild(fire[x.index]);
        }
        else
        {
           rootNode.detachChild(fire[x.index]);
        }
    }

    public void smoke(requestHandler x)
    {
        ParticleEmitter numeObiect= new ParticleEmitter("SmokeTrail", ParticleMesh.Type.Triangle, 2200);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/smoketrail.png"));
        numeObiect.setMaterial(mat);
        numeObiect.setImagesX(1);
        numeObiect.setImagesY(3);
        numeObiect.setStartColor(new ColorRGBA(0.05f, 0.05f, 0.05f,0.5f));
        numeObiect.setEndColor(new ColorRGBA(0.3f,0.3f ,0.3f, 0.5f));
        numeObiect.setLocalTranslation((x.translatie_x-(3f*x.intensitate_foc)/2),x.translatie_y+5,(x.translatie_z-(4f*x.intensitate_foc)/2));
        numeObiect.setInitialVelocity(new Vector3f(0, 8, 0));
        numeObiect.setGravity(0, 0, 0);
        numeObiect.setStartSize((8/(float)10)*6.6f);
        numeObiect.setEndSize((8/(float)10)*0.5f);
        numeObiect.setLowLife((30/(float)10)*2.5f);
        numeObiect.setHighLife((30/(float)10)*3.5f);
        numeObiect.setShape(new EmitterBoxShape(new Vector3f(0, 0, 0), new Vector3f(3f*30, 0.6f*30, 4f*30)));
        numeObiect.setNumParticles(800*(int)x.intensitate_foc);
        //numeObiect.setQueueBucket(RenderQueue.Bucket.Translucent);
        numeObiect.setParticlesPerSec(100*x.intensitate_foc);

        if(x.pornit) {
            if(smoke[x.index]!=null)
            {
                rootNode.detachChild(smoke[x.index]);
            }
            smoke[x.index] = new ParticleEmitter();
            smoke[x.index]=numeObiect;
            rootNode.attachChild(smoke[x.index]);
        }
        else
        {
            rootNode.detachChild(smoke[x.index]);
        }
    }

    public void load_light(requestHandler x) {
        final int SHADOWMAP_SIZE = 1024;

        if(x.nume_obiect!=null)
        {
            if(x.pornit==false && lumina_leduri[x.index]!=null)
            {
                    rootNode.removeLight(lumina_leduri[x.index]);
                    viewPort.removeProcessor(dlsr_led[x.index]);
                    dlsf_led[x.index].setEnabled(false);
            }
            else
            {
                lumina_leduri[x.index] = new SpotLight();
                dlsr_led[x.index]=new SpotLightShadowRenderer(assetManager, SHADOWMAP_SIZE);
                dlsf_led[x.index]=new SpotLightShadowFilter(assetManager, SHADOWMAP_SIZE);


                lumina_leduri[x.index].setColor(x.culoare.mult(x.intensitate_lumina));

                lumina_leduri[x.index].setSpotRange(x.suprafata);
                lumina_leduri[x.index].setPosition(new Vector3f(x.translatie_x, x.translatie_y, x.translatie_z));
                lumina_leduri[x.index].setDirection(new Vector3f(0, -1, 0));
                lumina_leduri[x.index].setSpotInnerAngle(10f * FastMath.DEG_TO_RAD);
                lumina_leduri[x.index].setSpotOuterAngle(85f * FastMath.DEG_TO_RAD);
                dlsr_led[x.index].setLight(lumina_leduri[x.index]);
                dlsr_led[x.index].setShadowIntensity(0.8f);
                dlsf_led[x.index].setLight(lumina_leduri[x.index]);

                rootNode.addLight(lumina_leduri[x.index]);
                viewPort.addProcessor(dlsr_led[x.index]);
                dlsf_led[x.index].setEnabled(true);
            }
        }
        else
        {
            if(x.pornit==false && light[x.index]!=null)
            {
                rootNode.removeLight(light[x.index]);
                viewPort.removeProcessor(dlsr[x.index]);
                dlsf[x.index].setEnabled(false);
                light[x.index]=null;
                dlsr[x.index]=null;
                dlsf[x.index]=null;
            }
            else if (x.pornit==true)
            {
                light[x.index] = new PointLight();
                dlsr[x.index]=new PointLightShadowRenderer(assetManager, SHADOWMAP_SIZE);
                dlsf[x.index]=new PointLightShadowFilter(assetManager, SHADOWMAP_SIZE);

                if(x.alarma==true) {
                    light[x.index].setColor(ColorRGBA.Red.mult(x.intensitate_lumina));
                }
                else
                {
                    light[x.index].setColor(ColorRGBA.White.mult(x.intensitate_lumina));
                }
                light[x.index].setPosition(new Vector3f(x.translatie_x, x.translatie_y, x.translatie_z));
                light[x.index].setRadius(x.suprafata);

                dlsr[x.index].setLight(light[x.index]);
                dlsr[x.index].setShadowIntensity(0.8f);
                dlsr[x.index].setLight(light[x.index]);

                rootNode.addLight(light[x.index]);
                viewPort.addProcessor(dlsr[x.index]);
                dlsf[x.index].setEnabled(true);
            }
        }
    }

    public void load_object(requestHandler x)
    {
        Spatial numeObiect;
        assetManager.registerLocator(x.nume_arhiva, ZipLocator.class);
        numeObiect = assetManager.loadModel(x.nume_fisier);
        numeObiect.setLocalTranslation(x.translatie_x,x.translatie_y,x.translatie_z);
        numeObiect.scale(x.scalare_x,x.scalare_y,x.scalare_z);
        numeObiect.rotate(x.rotatie_x,x.rotatie_y,x.rotatie_z);

        if(x.nume_obiect.equals("bloc")) {
            numeObiect.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        }
        else if (x.nume_obiect.equals("map"))
        {
            numeObiect.setShadowMode(RenderQueue.ShadowMode.Receive);
        }
        else
        {
            numeObiect.setShadowMode(RenderQueue.ShadowMode.Cast);
        }
        RigidBodyControl numeObiect_PhysX = new RigidBodyControl(x.masa);
        numeObiect.addControl(numeObiect_PhysX);
        bulletAppState.getPhysicsSpace().add(numeObiect_PhysX);
        switch (x.nume_obiect)
        {
            case "map": map=numeObiect;
                rootNode.attachChild(map);
                break;
            case "bloc": bloc=numeObiect;
                rootNode.attachChild(bloc);
                break;
            case "detector_fum":
                detector_fum = numeObiect;
                rootNode.attachChild(detector_fum);
                break;
            case "bec":
                bec = numeObiect;
                rootNode.attachChild(bec);
                break;
            case "stropitoare":
                stropitoare = numeObiect;
                rootNode.attachChild(stropitoare);
                break;
            case "vent":
                vent = numeObiect;
                rootNode.attachChild(vent);
                break;
            case "barbat1":
                barbat1 = numeObiect;
                rootNode.attachChild(barbat1);
                break;
        }
    }



    @Override
    public void simpleUpdate(float tpf) {
        if(counter>50000)
            counter = 0;
        else
            counter++;

         if (request.isEmpty()==false)
        {
            try {
                requestHandler x = request.get(0);
                    nucleu.request_motor_grafic.add(x);

                    switch (x.type) {
                        case "load":
                            load_object(x);
                            break;
                        case "light":
                            load_light(x);
                            break;
                        case "foc_start":
                            foc_start(x);
                            break;
                        case "stropire":
                            stropire(x);
                            break;
                        case "smoke":
                            smoke(x);
                            break;
                        case "leduri":
                            load_light(x);
                    }


                    request.remove(0);

            }catch (NullPointerException e){
                System.out.println("eroare ");
                request.remove(0);
               request.toString();
            }
        }

        if(Integer.parseInt(this.fpsText.getText().split(": ")[1])>0) {
            if (requestA_X.isEmpty() == false) {
                requestHandler x = requestA_X.get(0);
                //nucleu.request_motor_grafic.add(x);
                switch (x.type) {
                    case "load":
                        load_object(x);
                        break;
                    case "light":
                        load_light(x);
                        break;
                    case "foc_start":
                        foc_start(x);
                        break;
                    case "stropire":
                        stropire(x);
                        break;
                    case "smoke":
                        smoke(x);
                        break;
                    case "leduri":
                        load_light(x);
                }
                requestA_X.remove(0);
                if (requestA_X.size() > 50)
                    requestA_X.clear();
            }

            if (requestB_X.isEmpty() == false) {
                requestHandler x = requestB_X.get(0);
                //nucleu.request_motor_grafic.add(x);
                switch (x.type) {
                    case "load":
                        load_object(x);
                        break;
                    case "light":
                        load_light(x);
                        break;
                    case "foc_start":
                        foc_start(x);
                        break;
                    case "stropire":
                        stropire(x);
                        break;
                    case "smoke":
                        smoke(x);
                        break;
                    case "leduri":
                        load_light(x);
                }
                requestB_X.remove(0);
                if (requestB_X.size() > 50)
                    requestB_X.clear();
            }

            if (requestY2_X.isEmpty() == false) {
                requestHandler x = requestY2_X.get(0);
                //nucleu.request_motor_grafic.add(x);
                switch (x.type) {
                    case "load":
                        load_object(x);
                        break;
                    case "light":
                        load_light(x);
                        break;
                    case "foc_start":
                        foc_start(x);
                        break;
                    case "stropire":
                        stropire(x);
                        break;
                    case "smoke":
                        smoke(x);
                        break;
                    case "leduri":
                        load_light(x);
                }
                requestY2_X.remove(0);
                if (requestY2_X.size() > 50)
                    requestY2_X.clear();
            }

            if (requestX_Y2.isEmpty() == false) {
                requestHandler x = requestX_Y2.get(0);
                //nucleu.request_motor_grafic.add(x);
                switch (x.type) {
                    case "load":
                        load_object(x);
                        break;
                    case "light":
                        load_light(x);
                        break;
                    case "foc_start":
                        foc_start(x);
                        break;
                    case "stropire":
                        stropire(x);
                        break;
                    case "smoke":
                        smoke(x);
                        break;
                    case "leduri":
                        load_light(x);
                }
                requestX_Y2.remove(0);
                if (requestX_Y2.size() > 50)
                    requestX_Y2.clear();
            }

            if (requestX_Y1.isEmpty() == false) {
                requestHandler x = requestX_Y1.get(0);
                //nucleu.request_motor_grafic.add(x);
                switch (x.type) {
                    case "load":
                        load_object(x);
                        break;
                    case "light":
                        load_light(x);
                        break;
                    case "foc_start":
                        foc_start(x);
                        break;
                    case "stropire":
                        stropire(x);
                        break;
                    case "smoke":
                        smoke(x);
                        break;
                    case "leduri":
                        load_light(x);
                }
                requestX_Y1.remove(0);
                if (requestX_Y1.size() > 50)
                    requestX_Y1.clear();
            }
        }

        if(!camera) {
            camDir.set(cam.getDirection()).multLocal(0.6f);
            camLeft.set(cam.getLeft()).multLocal(0.4f);
            walkDirection.set(0, 0, 0);
            if (left) {
                walkDirection.addLocal(camLeft);
            }
            if (right) {
                walkDirection.addLocal(camLeft.negate());
            }
            if (up) {
                walkDirection.addLocal(camDir);
            }
            if (down) {
                walkDirection.addLocal(camDir.negate());
            }
            player.setWalkDirection(walkDirection);
            cam.setLocation(new Vector3f(player.getPhysicsLocation().getX(),player.getPhysicsLocation().getY()+3,player.getPhysicsLocation().getZ()));

        }

        float x = cam.getLocation().getX();
        float y = cam.getLocation().getY();
        float z = cam.getLocation().getZ();

        if((x<1100 && x>935) && (y<180&&y>150) && (z<-1948&&z>-2164))
            locatie = "Camera 1";
        else if((x<1267 && x>1162) && (y<180&&y>150) && (z>-2015&&z<-1935))
            locatie = "Camera 2";
        else if((x<1182 && x>1077) && (y<180&&y>150) && (z>-1875&&z<-1795))
            locatie = "Camera 3";
        else if((x<1296 && x>1189) && (y<180&&y>150) && (z>-1860&&z<-1780))
            locatie = "Camera 4";
        else if((x<1267 && x>1162) && (y<148&&y>120) && (z>-2015&&z<-1935))
            locatie = "Camera 5";
        else if((x<1182 && x>1077) && (y<148&&y>120) && (z>-1875&&z<-1795))
            locatie = "Camera 6";
        else if((x<1296 && x>1189) && (y<148&&y>120) && (z>-1860&&z<-1780))
            locatie = "Camera 7";
        else if((x<871 && x>811) && (y<180&&y>150) && (z>-1989&&z<-1905))
            locatie = "et.4 Hol 1";
        else if((x<981 && x>870) && (y<180&&y>150) && (z>-1941&&z<-1878))
            locatie = "et.4 Hol 2";
        else if((x<1098 && x>981) && (y<180&&y>150) && (z>-1941&&z<-1878))
            locatie = "et.4 Hol 3";
        else if((x<1179 && x>1098) && (y<180&&y>150) && (z>-1941&&z<-1873))
            locatie = "et.4 Hol 4";
        else if((x<1292 && x>1190) && (y<180&&y>150) && (z>-1921&&z<-1859))
            locatie = "et.4 Hol 5";
        else if((x<1020 && x>987) && (y<180&&y>150) && (z>-1874&&z<-1788))
            locatie = "et.4 Hol 6";
        else if((x<871 && x>811) && (y<148&&y>120) && (z>-1989&&z<-1905))
            locatie = "et.3 Hol 1";
        else if((x<981 && x>870) && (y<148&&y>120) && (z>-1941&&z<-1878))
            locatie = "et.3 Hol 2";
        else if((x<1098 && x>981) && (y<148&&y>120) && (z>-1941&&z<-1878))
            locatie = "et.3 Hol 3";
        else if((x<1179 && x>1098) && (y<148&&y>120) && (z>-1941&&z<-1873))
            locatie = "et.3 Hol 4";
        else if((x<1292 && x>1190) && (y<148&&y>120) && (z>-1921&&z<-1859))
            locatie = "et.3 Hol 5";
        else if((x<1020 && x>987) && (y<148&&y>120) && (z>-1874&&z<-1788))
            locatie = "et.3 Hol 6";
        else locatie = "Neacoperita";


        hudText.setText("GPS: " + (int)cam.getLocation().getX() +"x"+ " "+(int)cam.getLocation().getY()+"y"+" "+(int)cam.getLocation().getZ()+"z");
        hudText2.setText("Locatie: "+locatie);
        if(locatie.contains("Camera")) {
            hudText3.setText("Temperatura: " + String.format("%.3f", temperatura_interior) + " °C");
            hudText23.setText("Umiditate: " + String.format("%.3f", umiditate) + " U.R.");
            hudText33.setText("CO2: " + String.format("%.0f", CO2) + " PPM");
        }
        else
        {
            hudText3.setText("Nr. oameni sector A: "+nr_oameni_setor_A);
            hudText23.setText("Nr. oameni sector B: " + nr_oameni_setor_B);
            hudText33.setText("Nr. oameni sector C: " + nr_oameni_setor_C);
        }

        hudText4.setText("Foc: "+ foc);
        hudText5.setText("Fum: "+ fum);
        String blabla = null;
        if(ventilatie==2)
            blabla = "Trage aer";
        if(environment.ventilatie>=-1&&ventilatie<=1)
            blabla = "Auto";
        if(ventilatie==3)
            blabla = "Manual";
        hudText6.setText("Ventilatie: "+ blabla);
        hudText7.setText("Sprinkler: "+ sprinkler);
        hudText8.setText("Electricitate: "+ curent_electric);
        hudText9.setText("L. urgenta: "+ lumini_urgenta);

        /*
        if(controller.lista_celule.get(0).contains("Camera1"))
            hudText12.setColor(ColorRGBA.Green);
        else
            hudText12.setColor(ColorRGBA.Gray);
        if(controller.lista_celule.get(0).contains("Camera2"))
            hudText13.setColor(ColorRGBA.Green);
        else
            hudText13.setColor(ColorRGBA.Gray);
        if(controller.lista_celule.get(0).contains("Camera3"))
            hudText14.setColor(ColorRGBA.Green);
        else
            hudText14.setColor(ColorRGBA.Gray);
        if(controller.lista_celule.get(0).contains("Camera4"))
            hudText15.setColor(ColorRGBA.Green);
        else
            hudText15.setColor(ColorRGBA.Gray);
        if(controller.lista_celule.get(0).contains("Camera5"))
            hudText16.setColor(ColorRGBA.Green);
        else
            hudText16.setColor(ColorRGBA.Gray);
        if(controller.lista_celule.get(0).contains("Camera6"))
            hudText17.setColor(ColorRGBA.Green);
        else
            hudText17.setColor(ColorRGBA.Gray);
        if(controller.lista_celule.get(0).contains("Camera7"))
            hudText18.setColor(ColorRGBA.Green);
        else
            hudText18.setColor(ColorRGBA.Gray);
        if(controller.lista_celule.get(0).contains("server"))
            hudText19.setColor(ColorRGBA.Green);
        else
            hudText19.setColor(ColorRGBA.Gray);*/

        if(gui)
        {
            flyCam.setDragToRotate(true);
        }
        else
        {
            flyCam.setDragToRotate(false);
        }
        loaded = true;
    }
}