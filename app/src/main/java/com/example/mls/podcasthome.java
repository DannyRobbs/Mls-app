package com.example.mls;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class podcasthome extends AppCompatActivity {
    ImageView image;ImageView playbtn2;ImageView playbtn3;ImageView playbtn4;ImageView playbtn5;
    ImageView image1;ImageView stopbtn2;ImageView stopbtn3;ImageView stopbtn4;ImageView stopbtn5;
    MediaPlayer mp;
    SeekBar seek;SeekBar seek2;SeekBar seek3;SeekBar seek4;SeekBar seek5;
    AudioManager.OnAudioFocusChangeListener focusChangeListner;
    private AudioManager am;
    TextView seekint;TextView seekint2;TextView seekint3;TextView seekint4;TextView seekint5;
    TextView seekintend;TextView seekintend2;TextView seekintend3;TextView seekintend4;TextView seekintend5;
    TextView audioname1;TextView audioname2;TextView audioname3;TextView audioname4;TextView audioname5;
    ProgressBar progressBar1;ProgressBar progressBar2;ProgressBar progressBar3;ProgressBar progressBar4;ProgressBar progressBar5;
    private ConnectivityManager cn;
    private NetworkInfo info;
    boolean stillplaying;
    String audioplaying;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#5e35b1"));
        }
        try {
            this.getSupportActionBar().hide();

        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_podcasthome);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5e35b1")));
        image = findViewById(R.id.covid1);playbtn2 = findViewById(R.id.playbtn2);playbtn3 = findViewById(R.id.playbtn3);playbtn4 = findViewById(R.id.playbtn4);playbtn5 = findViewById(R.id.playbtn5);
        image1 = findViewById(R.id.covid1st);stopbtn2 = findViewById(R.id.stopbtn2);stopbtn3 = findViewById(R.id.stopbtn3);stopbtn4 = findViewById(R.id.stopbtn4);stopbtn5 = findViewById(R.id.stopbtn5);
        image1.setVisibility(View.GONE);stopbtn2.setVisibility(View.GONE);stopbtn3.setVisibility(View.GONE);stopbtn4.setVisibility(View.GONE);stopbtn5.setVisibility(View.GONE);
        mp = null;
        cn = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        seek = findViewById(R.id.seek);seek2 = findViewById(R.id.seek2);seek3 = findViewById(R.id.seek3);seek4 = findViewById(R.id.seek4);seek5 = findViewById(R.id.seek5);
        seekint = findViewById(R.id.seekint);seekint2 = findViewById(R.id.seekint2);seekint3 = findViewById(R.id.seekint3);seekint4 = findViewById(R.id.seekint4);seekint5 = findViewById(R.id.seekint5);
        seekintend = findViewById(R.id.seekintend);seekintend2 = findViewById(R.id.seekintend2);seekintend3 = findViewById(R.id.seekintend3);seekintend4 = findViewById(R.id.seekintend4);seekintend5 = findViewById(R.id.seekintend5);
        audioname1 = findViewById(R.id.audiofile1);audioname2 = findViewById(R.id.audiofile2);audioname3 = findViewById(R.id.audiofile3);audioname4 = findViewById(R.id.audiofile4);audioname5 = findViewById(R.id.audiofile5);
        progressBar2 = findViewById(R.id.progresss2);progressBar1 = findViewById(R.id.progresss1);progressBar3 = findViewById(R.id.progresss3);progressBar4 = findViewById(R.id.progresss4);progressBar5 = findViewById(R.id.progresss5);
        progressBar2.setVisibility(View.GONE);progressBar1.setVisibility(View.GONE);progressBar3.setVisibility(View.GONE);progressBar4.setVisibility(View.GONE);progressBar5.setVisibility(View.GONE);
        am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

    }

    public void getSeekBarStatus(SeekBar  sek, TextView sekintend) {
        final SeekBar [] seek = {sek};
        final TextView []seekintend = {sekintend};

        new Thread(new Runnable() {
            @Override
            public void run() {
                // mp is your MediaPlayer
                // progress is your ProgressBar


                int currentPosition = 0;
                int total = mp.getDuration();
                seek[0].setMax(total);
                while (mp != null && currentPosition < total) {
                    try {

                        Thread.sleep(1000);
                        if (stillplaying) {
                            currentPosition = mp.getCurrentPosition();
                        }

                    } catch (InterruptedException e) {
                        //  Thread.interrupted();
                    }
                    seek[0].setProgress(currentPosition);

                }
            }
        }).start();
        seek[0].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(final SeekBar seekBar, int ProgressValue, boolean fromUser) {
                if (fromUser) {
                    mp.seekTo(ProgressValue);//if user drags the seekbar, it gets the position and updates in textView.
                }
                final long mMinutes = (ProgressValue / 1000) / 60;//converting into minutes
                final int mSeconds = ((ProgressValue / 1000) % 60);//converting into seconds
                seekintend[0].setText(mMinutes + ":" + mSeconds);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mp.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.start();
            }
        });
    }

    public void getDurationTimer(TextView seekint) {
        final long minutes = (mp.getDuration() / 1000) / 60;
        final int seconds = (int) ((mp.getDuration() / 1000) % 60);
        seekint.setText(minutes + ":" + seconds);


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void first(View v) {
        if (mp != null) {
            Toast.makeText(this,"Previous Play still in Session.",Toast.LENGTH_SHORT).show();
            return;
        }
        info = cn.getActiveNetworkInfo();
        if (info != null &&
                info.isAvailable() &&
                info.isConnected()) {
            audioplaying = "audio1";
            progressBar1.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
            seekint.setText("Buffering");
            seekintend.setText("Buffering");


            String url = "https://firebasestorage.googleapis.com/v0/b/mls-app-fa216.appspot.com/o/CovidChronicles1.mp3?alt=media&token=9b527b2c-4c1b-429f-b5f0-dbbd9b76f5bb";

            mp = new MediaPlayer();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressBar1.setVisibility(View.GONE);
                    stillplaying = true;
                    mp.start();
                    image1.setVisibility(View.VISIBLE);
                    getSeekBarStatus(seek,seekintend);
                    getDurationTimer(seekint);
                    sendNotification(audioname1);
                }
            });
            try {
                mp.setDataSource(url);
            } catch (IOException e) {
                Toast.makeText(this, "Problem dey o", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            int result = am.requestAudioFocus(focusChangeListner, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                focusChangeListner = new AudioManager.OnAudioFocusChangeListener() {
                    @Override
                    public void onAudioFocusChange(int i) {
                        switch (i) {
                            case (AudioManager.AUDIOFOCUS_GAIN):
                                mp.start();
                                break;

                            case (AudioManager.AUDIOFOCUS_LOSS):
//                                mp.release();
                                break;

                            case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT):
                                mp.release();
                                break;

                            case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK):
                                mp.pause();
                                break;
                        }

                    }
                };


                mp.prepareAsync();

            }


            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();

                }
            });


        } else {

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(getString(R.string.alert));
            dialog.setMessage(getString(R.string.network_warning));
            dialog.setNeutralButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog diag = dialog.create();
            diag.show();
        }
    }
    public void second(View v) {
        if (mp != null) {
            Toast.makeText(this,"Previous Play still in Session.",Toast.LENGTH_SHORT).show();
            return;
        }
        info = cn.getActiveNetworkInfo();
        if (info != null &&
                info.isAvailable() &&
                info.isConnected()) {
            audioplaying = "audio2";
            progressBar2.setVisibility(View.VISIBLE);
            playbtn2.setVisibility(View.GONE);
            seekint2.setText("Buffering");
            seekintend2.setText("Buffering");


            String url = "https://firebasestorage.googleapis.com/v0/b/mls-app-fa216.appspot.com/o/REBOOT_129_Depression_and_Suicide_with_Elisabeth_Poorman_MD.mp3?alt=media&token=37a4870c-8d5a-4295-9715-1d37f20e1113";

            mp = new MediaPlayer();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressBar2.setVisibility(View.GONE);
                    stillplaying = true;
                    mp.start();
                    stopbtn2.setVisibility(View.VISIBLE);
                    getSeekBarStatus(seek2,seekintend2);
                    getDurationTimer(seekint2);
                    sendNotification(audioname2);
                }
            });
            try {
                mp.setDataSource(url);
            } catch (IOException e) {
                Toast.makeText(this, "Problem dey o", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            int result = am.requestAudioFocus(focusChangeListner, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                focusChangeListner = new AudioManager.OnAudioFocusChangeListener() {
                    @Override
                    public void onAudioFocusChange(int i) {
                        switch (i) {
                            case (AudioManager.AUDIOFOCUS_GAIN):
                                mp.start();
                                break;

                            case (AudioManager.AUDIOFOCUS_LOSS):
//                                mp.release();
                                break;

                            case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT):
                                mp.release();
                                break;

                            case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK):
                                mp.pause();
                                break;
                        }

                    }
                };


                mp.prepareAsync();

            }


            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();

                }
            });


        } else {

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(getString(R.string.alert));
            dialog.setMessage(getString(R.string.network_warning));
            dialog.setNeutralButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog diag = dialog.create();
            diag.show();
        }
    }
    public void third(View v) {
        Toast.makeText(this,"Previous Play still in Session.",Toast.LENGTH_SHORT).show();
        if (mp != null) {
            return;
        }
        info = cn.getActiveNetworkInfo();
        if (info != null &&
                info.isAvailable() &&
                info.isConnected()) {
            audioplaying = "audio3";
            progressBar3.setVisibility(View.VISIBLE);
            playbtn3.setVisibility(View.GONE);
            seekint3.setText("Buffering");
            seekintend3.setText("Buffering");


            String url = "https://firebasestorage.googleapis.com/v0/b/mls-app-fa216.appspot.com/o/Rage2.mp3?alt=media&token=51bb93ba-9836-404a-a335-b4d1d60fd979";

            mp = new MediaPlayer();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressBar3.setVisibility(View.GONE);
                    stillplaying = true;
                    mp.start();
                    stopbtn3.setVisibility(View.VISIBLE);
                    getSeekBarStatus(seek3,seekintend3);
                    getDurationTimer(seekint3);
                    sendNotification(audioname3);
                }
            });
            try {
                mp.setDataSource(url);
            } catch (IOException e) {
                Toast.makeText(this, "Problem dey o", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            int result = am.requestAudioFocus(focusChangeListner, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                focusChangeListner = new AudioManager.OnAudioFocusChangeListener() {
                    @Override
                    public void onAudioFocusChange(int i) {
                        switch (i) {
                            case (AudioManager.AUDIOFOCUS_GAIN):
                                mp.start();
                                break;

                            case (AudioManager.AUDIOFOCUS_LOSS):
//                                mp.release();
                                break;

                            case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT):
                                mp.release();
                                break;

                            case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK):
                                mp.pause();
                                break;
                        }

                    }
                };


                mp.prepareAsync();

            }


            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();

                }
            });


        } else {

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(getString(R.string.alert));
            dialog.setMessage(getString(R.string.network_warning));
            dialog.setNeutralButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog diag = dialog.create();
            diag.show();
        }
    }
    public void fourth(View v) {
        if (mp != null) {
            Toast.makeText(this,"Previous Play still in Session.",Toast.LENGTH_SHORT).show();
            return;
        }
        info = cn.getActiveNetworkInfo();
        if (info != null &&
                info.isAvailable() &&
                info.isConnected()) {
            audioplaying = "audio4";
            progressBar4.setVisibility(View.VISIBLE);
            playbtn4.setVisibility(View.GONE);
            seekint4.setText("Buffering");
            seekintend4.setText("Buffering");


            String url = "https://firebasestorage.googleapis.com/v0/b/mls-app-fa216.appspot.com/o/CovidChroniclesEp2.mp3?alt=media&token=d96796f0-d836-4c7e-b08e-e1ccfac6af94";

            mp = new MediaPlayer();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressBar4.setVisibility(View.GONE);
                    stillplaying = true;
                    mp.start();
                    stopbtn4.setVisibility(View.VISIBLE);
                    getSeekBarStatus(seek4,seekintend4);
                    getDurationTimer(seekint4);
                    sendNotification(audioname4);
                }
            });
            try {
                mp.setDataSource(url);
            } catch (IOException e) {
                Toast.makeText(this, "Problem dey o", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            int result = am.requestAudioFocus(focusChangeListner, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                focusChangeListner = new AudioManager.OnAudioFocusChangeListener() {
                    @Override
                    public void onAudioFocusChange(int i) {
                        switch (i) {
                            case (AudioManager.AUDIOFOCUS_GAIN):
                                mp.start();
                                break;

                            case (AudioManager.AUDIOFOCUS_LOSS):
//                                mp.release();
                                break;

                            case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT):
                                mp.release();
                                break;

                            case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK):
                                mp.pause();
                                break;
                        }

                    }
                };


                mp.prepareAsync();

            }


            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();

                }
            });


        } else {

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(getString(R.string.alert));
            dialog.setMessage(getString(R.string.network_warning));
            dialog.setNeutralButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog diag = dialog.create();
            diag.show();
        }
    }
    public void fifth(View v) {
        Toast.makeText(this,"Previous Play still in Session.",Toast.LENGTH_SHORT).show();
        if (mp != null) {
            return;
        }
        info = cn.getActiveNetworkInfo();
        if (info != null &&
                info.isAvailable() &&
                info.isConnected()) {
            audioplaying = "audio5";
            progressBar5.setVisibility(View.VISIBLE);
            playbtn5.setVisibility(View.GONE);
            seekint5.setText("Buffering");
            seekintend5.setText("Buffering");


            String url = "https://firebasestorage.googleapis.com/v0/b/mls-app-fa216.appspot.com/o/201_COVID_Narratives_Stories_from_Frontline_Physicians.mp3?alt=media&token=1ec7ce9a-c73b-4400-b994-be97f39e7705";

            mp = new MediaPlayer();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressBar5.setVisibility(View.GONE);
                    stillplaying = true;
                    mp.start();
                    stopbtn5.setVisibility(View.VISIBLE);
                    getSeekBarStatus(seek5,seekintend5);
                    getDurationTimer(seekint5);
                    sendNotification(audioname5);
                }
            });
            try {
                mp.setDataSource(url);
            } catch (IOException e) {
                Toast.makeText(this, "Problem dey o", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            int result = am.requestAudioFocus(focusChangeListner, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                focusChangeListner = new AudioManager.OnAudioFocusChangeListener() {
                    @Override
                    public void onAudioFocusChange(int i) {
                        switch (i) {
                            case (AudioManager.AUDIOFOCUS_GAIN):
                                mp.start();
                                break;

                            case (AudioManager.AUDIOFOCUS_LOSS):
//                                mp.release();
                                break;

                            case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT):
                                mp.release();
                                break;

                            case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK):
                                mp.pause();
                                break;
                        }

                    }
                };


                mp.prepareAsync();

            }


            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();

                }
            });


        } else {

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(getString(R.string.alert));
            dialog.setMessage(getString(R.string.network_warning));
            dialog.setNeutralButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog diag = dialog.create();
            diag.show();
        }
    }



    public void stop(View v){
        NotificationManager nManager = ((NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE));
        if(mp==null){
            image1.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            stopbtn2.setVisibility(View.GONE);
            playbtn2.setVisibility(View.VISIBLE);
            stopbtn3.setVisibility(View.GONE);
            playbtn3.setVisibility(View.VISIBLE);
            stopbtn4.setVisibility(View.GONE);
            playbtn4.setVisibility(View.VISIBLE);
            stopbtn5.setVisibility(View.GONE);
            playbtn5.setVisibility(View.VISIBLE);
        }else {
            switch (audioplaying) {
                case "audio1":
                    image1.setVisibility(View.GONE);
                    image.setVisibility(View.VISIBLE);
                    progressBar1.setVisibility(View.GONE);
                    stillplaying = false;
                    nManager.cancelAll();
                    mp.stop();
                    mp.release();
                    mp = null;
                    seek.setProgress(0);
                    seekint.setText("0:0");
                    seekintend.setText("0:0");
                    break;
                case "audio2":
                    stopbtn2.setVisibility(View.GONE);
                    playbtn2.setVisibility(View.VISIBLE);
                    progressBar2.setVisibility(View.GONE);
                    stillplaying = false;
                    nManager.cancelAll();
                    mp.stop();
                    mp.release();
                    mp = null;
                    seek2.setProgress(0);
                    seekint2.setText("0:0");
                    seekintend2.setText("0:0");
                    break;
                case "audio3":
                    stopbtn3.setVisibility(View.GONE);
                    playbtn3.setVisibility(View.VISIBLE);
                    progressBar3.setVisibility(View.GONE);
                    stillplaying = false;
                    nManager.cancelAll();
                    mp.stop();
                    mp.release();
                    mp = null;
                    seek3.setProgress(0);
                    seekint3.setText("0:0");
                    seekintend3.setText("0:0");
                    break;
                case "audio4":
                    stopbtn4.setVisibility(View.GONE);
                    playbtn4.setVisibility(View.VISIBLE);
                    progressBar4.setVisibility(View.GONE);
                    stillplaying = false;
                    nManager.cancelAll();
                    mp.stop();
                    mp.release();
                    mp = null;
                    seek4.setProgress(0);
                    seekint4.setText("0:0");
                    seekintend4.setText("0:0");
                    break;
                case "audio5":
                    stopbtn5.setVisibility(View.GONE);
                    playbtn5.setVisibility(View.VISIBLE);
                    progressBar5.setVisibility(View.GONE);
                    stillplaying = false;
                    nManager.cancelAll();
                    mp.stop();
                    mp.release();
                    mp = null;
                    seek5.setProgress(0);
                    seekint5.setText("0:0");
                    seekintend5.setText("0:0");
                    break;
                default:
                    Toast.makeText(this, "An Error Occurred!", Toast.LENGTH_SHORT).show();

            }
        }

    }

        public void sendNotification(TextView view) {

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this);

            //Create the intent that’ll fire when the user taps the notification//

            Intent intent = new Intent(getApplicationContext(),podcasthome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            mBuilder.setContentIntent(pendingIntent);

            mBuilder.setSmallIcon(R.drawable.podcast);
            mBuilder.setContentTitle(view.getText().toString());
            mBuilder.setContentText("Now Playing");
            mBuilder.setPriority(2);
            mBuilder.setAutoCancel(true);

            NotificationManager mNotificationManager =

                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(0, mBuilder.build());

        }

    @Override
    protected void onDestroy() {

        if (mp != null) {
            stillplaying = false;
            NotificationManager nManager = ((NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE));
            nManager.cancelAll();
            mp.stop();
            mp.release();
            mp = null;
        }
        super.onDestroy();
    }


}

