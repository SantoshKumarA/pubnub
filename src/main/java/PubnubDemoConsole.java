import com.pubnub.api.*;
import static java.lang.System.out;

public class PubnubDemoConsole {

    Pubnub pubnub;
    public static final String PARKING_CHANNEL = "parking_channel";
    String publish_key = "pub-c-b11b8c65-22c5-4bc7-84d2-dd41949dbc58";
    String subscribe_key = "sub-c-313d4a34-2ef7-11e6-b700-0619f8945a4f";

    public PubnubDemoConsole(String publish_key, String subscribe_key) {
        this.publish_key = publish_key;
        this.subscribe_key = subscribe_key;
    }

    private void notifyUser(Object message) {
        out.println(message.toString());
    }

     private void subscribe(final String channel) {

        try {
            pubnub.subscribe(channel, new Callback() {

                @Override
                public void connectCallback(String channel, Object message) {
                    notifyUser("SUBSCRIBE : CONNECT on channel:" + channel + " : " + message.getClass() + " : "
                            + message.toString());
                }

                @Override
                public void disconnectCallback(String channel, Object message) {
                    notifyUser("SUBSCRIBE : DISCONNECT on channel:" + channel + " : " + message.getClass() + " : "
                            + message.toString());
                }

                public void reconnectCallback(String channel, Object message) {
                    notifyUser("SUBSCRIBE : RECONNECT on channel:" + channel + " : " + message.getClass() + " : "
                            + message.toString());
                }

                @Override
                public void successCallback(String channel, Object message) {
                    notifyUser("SUBSCRIBE : " + channel + " : " + message.getClass() + " : " + message.toString());

                }

                @Override
                public void successCallback(String channel, Object message, String timetoken) {
                    notifyUser("SUBSCRIBE : [TT - " + timetoken + "] " + channel + " : " + message.getClass() + " : "
                            + message.toString());

                }

                @Override
                public void errorCallback(String channel, PubnubError error) {

                    /*
                     *
                     * # Switch on error code, see PubnubError.java
                     *
                     * if (error.errorCode == 112) { # Bad Auth Key!
                     * unsubscribe, get a new auth key, subscribe, etc... } else
                     * if (error.errorCode == 113) { # Need to set Auth Key !
                     * unsubscribe, set auth, resubscribe }
                     */

                    notifyUser("SUBSCRIBE : ERROR on channel " + channel + " : " + error.toString());
                    if (error.errorCode == PubnubError.PNERR_TIMEOUT)
                        pubnub.disconnectAndResubscribe();
                }
            });

        } catch (Exception e) {
        }
    }
    public void startDemo(){

        pubnub = new Pubnub(this.publish_key, this.subscribe_key);
        pubnub.setCacheBusting(false);
        System.out.println(Thread.currentThread().getName());
        subscribe(PARKING_CHANNEL);
    }

    /**
     * @param args
     */
    public static void main(String[] args)throws Exception{
        new Thread(new Runnable() {
            @Override
            public void run() {
                PubnubDemoConsole  pdc = new PubnubDemoConsole("pub-c-b11b8c65-22c5-4bc7-84d2-dd41949dbc58", "sub-c-313d4a34-2ef7-11e6-b700-0619f8945a4f");
                pdc.startDemo();
            }
        },"subscriber-thread").start();
        Thread.sleep(100);
    }

}
