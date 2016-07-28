package LibDM;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by weker on 7/22/2016.
 */
public class Restart {
    public void softReset (Context c){
        PowerManager pm = (PowerManager) c.getSystemService(Context.POWER_SERVICE);
        pm.reboot(null);
    }
}
