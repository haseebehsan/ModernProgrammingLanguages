package me.haseebehsan.me.netflixexcessusagereminder;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class backgroundService extends Service {
    public backgroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public int onStartCommand(final Intent intent, int flags, int id) {

        ScheduledExecutorService execService
                =	Executors.newScheduledThreadPool(5);
        execService.scheduleAtFixedRate(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.d("tag1", "inside");
                                                Context context = getApplicationContext();
                                                Toast.makeText(context, "test", Toast.LENGTH_SHORT);
                                            }
                                        }
//                (){
//            //The repetitive task, say to update Database
//            System.out.println("Running repetitive task at: "+ new java.util.Date());
//        }

                , 1, 1, TimeUnit.SECONDS);
        Log.d("tag1", "outside");
        return START_STICKY;
    }
}
