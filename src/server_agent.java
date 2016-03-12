import jade.core.Agent;

public class server_agent extends Agent{
    @Override
    protected void setup() {
        addBehaviour(new serverBehaviour());
    }
}
