package com.example.servicemusic_nguyenthanhchinh_17044461;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtTitle, txtTimeSong, txtTimeTotal;
    SeekBar skSong;
    ImageView img;
    ImageButton btnBack, btnPlay, btnStop, btnNext;

    ArrayList<Song> songArrayList;
    int position = 0;
    MediaPlayer mediaPlayer;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        AddSong();
        animation = AnimationUtils.loadAnimation(this, R.anim.xoaydia);
        StartMediaPlayer();


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                }else{
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause);
                }
                SetTimeTotal();
                UpdateTimeSong();
                img.startAnimation(animation);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                btnPlay.setImageResource(R.drawable.play);
                StartMediaPlayer();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if(position > songArrayList.size() -1){
                    position = 0;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                StartMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if(position < 0){
                    position = songArrayList.size() - 1;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                StartMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });

        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skSong.getProgress());
            }
        });
    }

    private  void UpdateTimeSong(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
                txtTimeSong.setText(dateFormat.format(mediaPlayer.getCurrentPosition()));
                skSong.setProgress(mediaPlayer.getCurrentPosition());

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if(position > songArrayList.size() -1){
                            position = 0;
                        }
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        StartMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause);
                        SetTimeTotal();
                        UpdateTimeSong();
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    private  void SetTimeTotal(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        txtTimeTotal.setText(dateFormat.format(mediaPlayer.getDuration()));

        skSong.setMax(mediaPlayer.getDuration());
    }

    private  void StartMediaPlayer(){
        mediaPlayer = MediaPlayer.create(MainActivity.this,
                songArrayList.get(position).getFile());
        txtTitle.setText(songArrayList.get(position).getTitle());
    }

    private void AddSong() {
        songArrayList = new ArrayList<>();
        songArrayList.add(new Song("Giàu vì bạn, sang vì vợ", R.raw.giauvibansangvivo));
        songArrayList.add(new Song("Chú chó trên ô tô", R.raw.chu_cho_tren_o_to));
        songArrayList.add(new Song("Người ấy là ai", R.raw.nguoiaylaai));
        songArrayList.add(new Song("Nhiều tiền để làm gì", R.raw.nhieutiendelamgi));
        songArrayList.add(new Song("The Right Journey", R.raw.therightjourney));
        songArrayList.add(new Song("Va vào giai điệu này", R.raw.vavaogiadieunay));
    }

    private void AnhXa() {
        txtTimeSong  = (TextView) findViewById(R.id.tvTimeSong);
        txtTimeTotal = (TextView) findViewById(R.id.tvTimeTotal);
        txtTitle     = (TextView) findViewById(R.id.tvTitle);
        skSong       = (SeekBar) findViewById(R.id.skSong);
        btnBack      = (ImageButton) findViewById(R.id.ib_Back);
        btnPlay      = (ImageButton) findViewById(R.id.ib_Play);
        btnStop      = (ImageButton) findViewById(R.id.ib_Stop);
        btnNext      = (ImageButton) findViewById(R.id.ib_Next);
        img          = (ImageView) findViewById(R.id.imageView);
    }
}