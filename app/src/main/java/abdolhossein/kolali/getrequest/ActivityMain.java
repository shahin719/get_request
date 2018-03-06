package abdolhossein.kolali.getrequest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.brouding.simpledialog.SimpleDialog;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityMain extends AppCompatActivity {

    Button btnMoshavereh;
    Button btnVakil;
    Button btnArshiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMoshavereh = (Button) findViewById(R.id.btnMoshavereh);
        btnVakil = (Button) findViewById(R.id.btnVakil);
        btnArshiv = (Button) findViewById(R.id.btnArshiv);

        btnMoshavereh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMain.this,ActivityMoshavereh.class));
            }
        });

        btnVakil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMain.this,ActivityVakil.class));
            }
        });

        btnArshiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMain.this,ActivityArshiv.class));
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        new SimpleDialog.Builder(this)
                .setContent("آیا مایلید از برنامه خارج شوید؟", 3)
                .setBtnConfirmText("بله")
                .setBtnCancelText("خیر")
                .setCancelable(true)
                .onConfirm(new SimpleDialog.BtnCallback() {
                    @Override
                    public void onClick(@NonNull SimpleDialog dialog, @NonNull SimpleDialog.BtnAction which) {
                        dialog.dismiss();
                        moveTaskToBack(true);
                    }
                })
                .onCancel(new SimpleDialog.BtnCallback() {
                    @Override
                    public void onClick(@NonNull SimpleDialog dialog, @NonNull SimpleDialog.BtnAction which) {

                    }
                })
                .show();
    }
}
