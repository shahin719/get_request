package abdolhossein.kolali.getrequest;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityTextMoshavereh extends AppCompatActivity {

    int id;
    TextView name;
    TextView personPhone;
    TextView title;
    TextView description;
    Button btnCall;
    Button btnText;
    Button btnDone;
    String url = "http://caferejim.com/moshavereh/web_service/request.php";
    String call_type;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_moshavereh);
        init();
        readData();
    }

    public void init() {
        final ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgBack.setBackgroundColor(getResources().getColor(R.color.colorBack));
                startActivity(new Intent(ActivityTextMoshavereh.this, ActivityMoshavereh.class));
                finish();
            }
        });

        name = (TextView) findViewById(R.id.name);
        personPhone = (TextView) findViewById(R.id.phone);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        btnCall = (Button) findViewById(R.id.btnCall);
        btnText = (Button) findViewById(R.id.btnText);
        btnDone = (Button) findViewById(R.id.btnDone);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
                Snackbar mSnackBar = Snackbar.make(view, "با موفقیت به اتمام رسید.", Snackbar.LENGTH_LONG);
                TextView mainTextView = (TextView) (mSnackBar.getView()).findViewById(android.support.design.R.id.snackbar_text);
                mainTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                mSnackBar.show();
                return;
            }
        });
    }

    public void readData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("ID");
        }
        AndroidNetworking.post(url)
                .addBodyParameter("action", "readData")
                .addBodyParameter("id", String.valueOf(id))
                .setContentType("application/json")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject object = response.getJSONObject(0);
                            name.setText("نام : " + object.getString("name") + " " + object.getString("family"));
                            title.setText("موضوع : " + object.getString("title"));
                            description.setText("توضیحات : " + object.getString("question"));
                            call_type = object.getString("kind");
                            phone = object.getString("phone");
                            personPhone.setText("تلفن : " + phone);
                            Log.i("LOG", call_type);
                            if (call_type.equals("text")){
                                btnText.setVisibility(View.VISIBLE);
                            }
                            else if (call_type.equals("call")){
                                btnCall.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("LOG", phone);
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);
            }
        });

        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uri = Uri.parse("smsto:" + phone);
                    // No permisison needed
                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
                    // Set the message to be sent
                    smsIntent.putExtra("sms_body", "سلام. ");
                    startActivity(smsIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void update() {
        AndroidNetworking.post(url)
                .addBodyParameter("action", "update")
                .addBodyParameter("id", String.valueOf(id))
                .setContentType("application/json")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActivityTextMoshavereh.this, ActivityMoshavereh.class));
        finish();
    }
}
