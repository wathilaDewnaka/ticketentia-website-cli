public class RealTimeUpdate implements Runnable{
    private String sessionId;
    private HandleAPI handleAPI;

    public RealTimeUpdate(String sessionId) {
        this.sessionId = sessionId;
        this.handleAPI = new HandleAPI();
    }

    @Override
    public void run() {
        while (true) {
            try{
                String ticketPoolNew = handleAPI.ticketPoolData(sessionId);
                System.out.println(ticketPoolNew);
                Thread.sleep(5000);
            } catch (InterruptedException e){
                System.out.println("Interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }
}
