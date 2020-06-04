package com.example.mobile_hw11;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class hw11_2 extends AppCompatActivity {
    Button btn_prev,btn_play,btn_next;
    SeekBar seekbar;
    TextView current_time,finish_time;
    ListView listview;
    MediaPlayer mPlayer;
    int state=0; //0:first start 1: playing 2: pause
    int array_index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw11_2);
        btn_prev=(Button)findViewById(R.id.btn_prev);
        btn_play=(Button)findViewById(R.id.btn_play);
        btn_next=(Button)findViewById(R.id.btn_next);
        seekbar=(SeekBar)findViewById(R.id.seekbar);
        current_time = (TextView)findViewById(R.id.current_time);
        finish_time = (TextView)findViewById(R.id.finish_time);
        listview = (ListView)findViewById(R.id.listview);
        final SimpleDateFormat timeFormat2 = new SimpleDateFormat("mm:ss");

        final String[] music_name = {"Astronomia","On_My_Way","Titanium"};
        final int[] music_file ={R.raw.astronomia,R.raw.on_my_way,R.raw.titanium};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,music_name);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("adsfasdf",""+position);
                array_index=position;
                if(state==0) {
                    mPlayer = MediaPlayer.create(getApplicationContext(), music_file[array_index]);
                    finish_time.setText(String.format(timeFormat2.format(mPlayer.getDuration())));
                }
                else if(state==1 || state==2) {
                    mPlayer.stop();
                    mPlayer.reset();
                     mPlayer = MediaPlayer.create(getApplicationContext(), music_file[array_index]);
                     finish_time.setText(String.format(timeFormat2.format(mPlayer.getDuration())));
                    btn_play.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                    state = 0;
                }

            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    mPlayer.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                array_index++;
                if(array_index==3){
                    array_index=0;
                }
                mPlayer.stop();
                mPlayer.reset();
                listview.setItemChecked(array_index,true);
                mPlayer = MediaPlayer.create(getApplicationContext(), music_file[array_index]);
                finish_time.setText(String.format(timeFormat2.format(mPlayer.getDuration())));
                current_time.setText(String.format(timeFormat2.format(0)));
                mPlayer.start();
                btn_play.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                makeThread();
                state=1;
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(state==0) {
                try{
                    mPlayer.start();//노래시작
                    btn_play.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                    btn_play.setClickable(true);
                    state=1;
                    makeThread();
                }catch(NullPointerException e){
                    Toast.makeText(getApplicationContext(),"노래를 선택하세요",Toast.LENGTH_SHORT).show();
                    btn_play.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                    btn_play.setClickable(true);
                    state=0;
                }
              }//첫번째 재생
              else if(state==1){
                  mPlayer.pause();
                  btn_play.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                  state=2;
                  makeThread();
              }//일시정지
              else if(state==2){
                  mPlayer.start();
                  btn_play.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                  state=1;
                  makeThread();
              }//일시정지 상태에서 재생
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                array_index--;
                if(array_index==-1){
                    array_index=2;
                }
                mPlayer.stop();
                mPlayer.reset();
                listview.setItemChecked(array_index,true);
                mPlayer = MediaPlayer.create(getApplicationContext(), music_file[array_index]);
                finish_time.setText(String.format(timeFormat2.format(mPlayer.getDuration())));
                current_time.setText(String.format(timeFormat2.format(0)));
                mPlayer.start();
                btn_play.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                makeThread();
                state=1;
            }
        });

    }
    void makeThread(){
        new Thread(){
            public void run() {
                final SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
                if(mPlayer==null) return;
                seekbar.setMax(mPlayer.getDuration());
                while(mPlayer.isPlaying()){
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            seekbar.setProgress(mPlayer.getCurrentPosition());
                            current_time.setText(String.format(timeFormat.format(mPlayer.getCurrentPosition())));
                        }
                    });
                    SystemClock.sleep(100);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        seekbar.setProgress(mPlayer.getCurrentPosition());
                        current_time.setText(String.format(timeFormat.format(mPlayer.getCurrentPosition())));
                    }

                });
            }
        }.start();

    }
}
