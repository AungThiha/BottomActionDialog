package thiha.aung.bottomactiondialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openDialog(View view){
        new BottomActionDialog.Builder(this)
                .setCancelButtonTitle("Cancel")
                .setOtherButtonTitles("Say thank you", "Say welcome", "Say love you")
                .setOnOtherButtonClickedListener(new BottomActionDialog.OnOtherButtonClickedListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        String message;
                        switch (position){
                            case 0: message = "Thank you";
                                break;
                            case 1: message = "Welcome";
                                break;
                            case 2: message = "Love you";
                                break;
                            default:
                                throw new RuntimeException("Do not add more titles than the onclick listener handle");
                        }
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(true)
                .create().show();

    }
}
