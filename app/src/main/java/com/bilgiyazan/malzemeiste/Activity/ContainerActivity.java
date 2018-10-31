package com.bilgiyazan.malzemeiste.Activity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.bilgiyazan.malzemeiste.Fragment.BoyalarFragment;
import com.bilgiyazan.malzemeiste.Fragment.KampanyalarFragment;
import com.bilgiyazan.malzemeiste.Fragment.MakinalarFragment;
import com.bilgiyazan.malzemeiste.Fragment.MalzemeFragment;
import com.bilgiyazan.malzemeiste.Fragment.YazilimFragment;
import com.bilgiyazan.malzemeiste.Fragment.YedekParcaFragment;
import com.bilgiyazan.malzemeiste.Model.Yazilim;
import com.bilgiyazan.malzemeiste.R;
import com.bilgiyazan.malzemeiste.Utils.ConnectionDetector;
import com.bilgiyazan.malzemeiste.Utils.HttpHandler;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.Drawer;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;


public class ContainerActivity extends AppCompatActivity {
    public static Drawer result;
    Toolbar toolbar;
    boolean doubleBackToExitPressedOnce = false;
    Toast showToastMessage;
    DialogPlus SortDialog;
    CheckBox sort_by_code;
    FloatingSearchView mSearchView;
    AppBarLayout appBarLayout;
    RelativeLayout circularProgressBar;
    ConnectionDetector connectionDetector;
    Boolean isInternetPresent;
    SharedPreferences prefs;
    View view;
    private AccountHeader headerResult = null;
    private DatabaseReference mDatabase;
    ViewPager viewPager;
    public static final int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = this.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);

        showToastMessage = Toast.makeText(this, "Çıkmak için tekrar basın", Toast.LENGTH_SHORT);

        SortDialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.sort_dialog_layout))
                .setGravity(Gravity.BOTTOM)
                .setContentWidth(ViewGroup.LayoutParams.MATCH_PARENT)  // or any custom width ie: 300
                .setContentHeight(ViewGroup.LayoutParams.MATCH_PARENT)

                .setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogPlus dialog) {
                    }
                })
                .setOnBackPressListener(new OnBackPressListener() {
                    @Override
                    public void onBackPressed(DialogPlus dialogPlus) {
                        SortDialog.dismiss();
                    }
                })

                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {

                    }
                })
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {


                        if (view.getId() == R.id.close_sort) {
                            SortDialog.dismiss();
                        }


                    }
                })

                .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)

                .create();


        view = SortDialog.getHolderView();

        sort_by_code = (CheckBox) view.findViewById(R.id.sort_by_code);


        if (prefs.getString("SortByKod", "false").equals("true")) {

            sort_by_code.setChecked(true);


        }

        sort_by_code.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString("SortByKod", "true");


                    editor.apply();
                } else {
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString("SortByKod", "false");


                    editor.apply();

                }
            }
        });


        setContentView(R.layout.container_layout);
        circularProgressBar = (RelativeLayout) findViewById(R.id.circularProgressBar);
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_container);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(false);

        }
        mSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        // ViewCompat.setElevation(mSearchView, 5);
        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {


            }

            @Override
            public void onSearchAction(String currentQuery) {
                if (!currentQuery.equals("")) {
                    Intent intent = new Intent(ContainerActivity.this, ResultSearchActivity.class);
                    intent.putExtra("search", currentQuery);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, 911);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            mSearchView.clearQuery();
                        }
                    }, 1000);
                }
            }
        });

        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.action_sort) {


                    SortDialog.show();
                }

            }
        });


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("BASKI MAKİNALARI", MakinalarFragment.class) //Machines
                .add("Boyalar", BoyalarFragment.class) // Painting
                .add("Yazılımlar", YazilimFragment.class) //Software Programs
                .add("Yedek Parça", YedekParcaFragment.class) //Spare parts
                .add("Malzeme", MalzemeFragment.class) //Other equipments
                .add("KAMPANYALAR", KampanyalarFragment.class) //Promotions

                .create());

         viewPager = (ViewPager) findViewById(R.id.viewpager_container);

        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.tabs);

        viewPagerTab.setViewPager(viewPager);


     /*   mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category");
        mDatabase.keepSynced(true);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {*/
                appBarLayout.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                circularProgressBar.setVisibility(View.GONE);
/*

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.container_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_iletisim:
                Intent intent = new Intent(ContainerActivity.this, IletisimActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.app_bar_hakkimizda:
                Intent intent1 = new Intent(ContainerActivity.this, HakkimizdaActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            showToastMessage.cancel();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        showToastMessage.show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        view = SortDialog.getHolderView();

        sort_by_code = (CheckBox) view.findViewById(R.id.sort_by_code);

        if (requestCode == 911) {
            if (resultCode == Activity.RESULT_OK) {
                if (prefs.getString("SortByKod", "false").equals("true")) {

                    sort_by_code.setChecked(true);


                } else {

                    sort_by_code.setChecked(false);

                }
            }

        }


    }

  /*  private class GetEuroTate extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response

            String jsonStr = sh.makeServiceCall("http://free.currencyconverterapi.com/api/v3/convert?q=EUR_TRY");

            Log.e("", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                    JSONObject Resultobj = jsonObj.getJSONObject("results");
                    JSONObject Rateobj = Resultobj.getJSONObject("EUR_TRY");
                    String rateString = String.valueOf(Rateobj.get("val"));
                    Log.e("resuult euro", rateString + "");
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("Euro_Rate", rateString);
                    editor.apply();

                } catch (final JSONException e) {
                    Log.e("", "Json parsing error: " + e.getMessage());


                }
            } else {
                Log.e("", "Couldn't get json from server.");


            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }

    }

    private class GetDollarRate extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall("http://free.currencyconverterapi.com/api/v3/convert?q=USD_TRY");

            Log.e("", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject Resultobj = jsonObj.getJSONObject("results");
                    JSONObject Rateobj = Resultobj.getJSONObject("USD_TRY");
                    String rateString = String.valueOf(Rateobj.get("val"));
                    Log.e("resuult dollar", rateString + "");
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("Dollar_Rate", rateString);
                    editor.apply();

                } catch (final JSONException e) {
                    Log.e("", "Json parsing error: " + e.getMessage());


                }
            } else {
                Log.e("", "Couldn't get json from server.");


            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }

    }*/
}
