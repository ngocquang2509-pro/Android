package com.example.intent_service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {
    public MyService() {
    }
    MediaPlayer Mymedia;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Mymedia = MediaPlayer.create(this, R.raw.duchotanthe);
        Mymedia.setLooping(true);
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
       if(Mymedia.isPlaying())
           Mymedia.pause();
       else
           Mymedia.start();
       return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Mymedia.stop();
    }
}