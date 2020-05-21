package muskan.myappcompany.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView textView;
    Boolean counterIsActive = false;
    Button button;
    CountDownTimer countDownTimer;
    public void buttonPressed(View view){

        if(counterIsActive){

            textView.setText("00:30");
            button.setText("GO!");
            seekBar.setEnabled(true);
            seekBar.setProgress(30);
            countDownTimer.cancel();
            counterIsActive = false;
        }else {

            counterIsActive = true;
            seekBar.setEnabled(false);
            button = (Button) findViewById(R.id.button);
            button.setText("STOP!");
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                    seekBar.setProgress((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarmsound);
                    mediaPlayer.start();
                    ImageView imageView = (ImageView) findViewById(R.id.imageView);
                    imageView.animate().rotation(720).setDuration(400);
                    textView.setText("00:30");
                    button.setText("GO!");
                    seekBar.setEnabled(true);
                    seekBar.setProgress(30);
                    countDownTimer.cancel();
                    counterIsActive = false;
                }
            }.start();
        }
    }

    public void updateTimer(int secondsLeft){
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft % 60;

        textView.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView);
        seekBar.setMax(600);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
