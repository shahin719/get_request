package abdolhossein.kolali.getrequest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityVakil extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterVakil adapter;
    List<Model> list = new ArrayList<>();
    String url = "http://caferejim.com/moshavereh/web_service/request.php";
    ProgressDialog formDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vakil);
        init();
        readData();
    }

    public void init() {
        final ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgBack.setBackgroundColor(getResources().getColor(R.color.colorBack));
                startActivity(new Intent(ActivityVakil.this, ActivityMain.class));
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new AdapterVakil(list);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void readData() {
        formDialog = new ProgressDialog(ActivityVakil.this);
        formDialog.setMessage("صبر کنید ...");
        formDialog.setIndeterminate(false);
        formDialog.setCancelable(true);
        formDialog.show();
        AndroidNetworking.post(url)
                .addBodyParameter("action", "getLawer")
                .setContentType("application/json")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Model model;
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                model = new Model(object.getString("title"),object.getInt("id"));
                                list.add(model);
                            }
                            adapter.notifyDataSetChanged();

                            formDialog.dismiss();

                            if (list.isEmpty()){
                                Snackbar mSnackBar = Snackbar.make(recyclerView, "موردی یافت نشد.", Snackbar.LENGTH_LONG);
                                TextView mainTextView = (TextView) (mSnackBar.getView()).findViewById(android.support.design.R.id.snackbar_text);
                                mainTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                                mSnackBar.show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        startActivity(new Intent(ActivityVakil.this, ActivityMain.class));
        finish();
    }
}
