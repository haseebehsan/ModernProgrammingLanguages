package me.haseebehsan.me.netflixexcessusagereminder;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service {
    // constant
    public static final long NOTIFY_INTERVAL = 4 * 1000; // 10 seconds

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy(){
        mTimer.cancel();
    }

    @Override
    public void onCreate() {
        // cancel if already existed
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);
    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    // display toast
                    ActivityManager am = (ActivityManager)getApplicationContext().getSystemService(Activity.ACTIVITY_SERVICE);
                    List<ActivityManager.RunningServiceInfo> rs = am.getRunningServices(50);
                    String message = null;

                    for (int i=0; i<rs.size(); i++) {
                        ActivityManager.RunningServiceInfo rsi = rs.get(i);
                        Log.i("Service", "Process " + rsi.process + " with component " + rsi.service.getClassName());

                        message =message+rsi.process +" " +rsi.activeSince+"\n";
                    }
                    

                   // getApplicationContext();
                    Intent done = new Intent();
                    done.setAction("back");
                    done.putExtra("msg", message);
                    sendBroadcast(done);

                    //Toast.makeText(getApplicationContext(), "-", Toast.LENGTH_SHORT).show();
                }

            });
        }

        private String getDateTime() {
            // get date time in custom format
            SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
            return sdf.format(new Date());
        }

    }
}