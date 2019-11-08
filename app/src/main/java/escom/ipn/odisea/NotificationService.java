package escom.ipn.odisea;

import android.os.BatteryManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import Data.EnvioDato;

public class NotificationService extends FirebaseMessagingService
{
    //@Override
/*    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        if (remoteMessage.getNotification() != null)
        {
            String Title = remoteMessage.getNotification().getTitle();
            String Body = remoteMessage.getNotification().getBody();

            if (Title.contains("Servicios Odisea"))
            {
                //BatteryManager bm = (BatteryManager)getSystemService(BATTERY_SERVICE);
                //int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

                //EnvioDato envio = new EnvioDato("","",batLevel,"",true,true,false,false);

                //envio.enviar();
            }
        }
    }*/

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);
        String Body = remoteMessage.getNotification().getBody();
        String Title = remoteMessage.getNotification().getTitle();

        if (Title.contains("Servicios Odisea"))
        {
            BatteryManager bm = (BatteryManager)getSystemService(BATTERY_SERVICE);
            int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

            EnvioDato envio = new EnvioDato("","",batLevel,"",true,true,false,false);

            envio.enviar();
        }
    }
}
