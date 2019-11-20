package escom.ipn.odisea;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.support.v4.app.ActivityCompat;

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

        SharedPreferences pref = this.getApplicationContext().getSharedPreferences("OdiseaPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String mail = pref.getString("mail", "");

        if (Title.contains("Servicios Odisea"))
        {
            BatteryManager bm = (BatteryManager)getSystemService(BATTERY_SERVICE);
            int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

            EnvioDato envio = new EnvioDato(batLevel,"",true,true,false,false);
            envio.setUsuario(mail);

            envio.setLongitud(getLocation(1));
            envio.setLatitud(getLocation(2));
            envio.enviar();
        }
    }

    public String getLocation(int t)
    {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try
        {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            }

            Location location   = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            double longitude    = location.getLongitude();
            double latitude     = location.getLatitude();

            if (t == 1)
            {
                return String.valueOf(longitude);
            }
            else
            {
                return String.valueOf(latitude);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return "0";
        }
    }
}
