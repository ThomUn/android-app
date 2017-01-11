package at.technikum.unger.android_app;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.io.IOException;

import at.technikum.unger.android_app.rest.pojo.LogoutRequest;
import at.technikum.unger.android_app.rest.pojo.LogoutResponse;
import at.technikum.unger.android_app.rest.pojo.RegisterRequest;
import at.technikum.unger.android_app.rest.pojo.RegisterResponse;
import at.technikum.unger.android_app.rest.pojo.User;
import at.technikum.unger.android_app.rest.rf.RFUserInterface;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity implements
        NfcAdapter.OnNdefPushCompleteCallback
//        ,NfcAdapter.CreateNdefMessageCallback
{
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    private boolean alreadyChecked = false;
    NfcAdapter nfcAdapter;

    //1 = SUCCESS
    //-1 = FAIL
    private int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getIntent().putExtra("username", "hugo.himmel@technikum-wien.at");

        nfcAdapter = NfcAdapter.getDefaultAdapter(this.getBaseContext());
        nfcAdapter.setOnNdefPushCompleteCallback(this, this);

        /**
         *Setup the DrawerLayout and NavigationView
         */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        //TODO: shitstuff ausbessern
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new OverviewFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                if (menuItem.getItemId() == R.id.nav_item_overview) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, new OverviewFragment()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_item_send) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_item_receive) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new ReceiveFragment()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_item_logout) {
                    UserLogoutTask userLogoutTask = new UserLogoutTask(getIntent().getStringExtra("username"), getIntent().getStringExtra("sessionToken"));
                    userLogoutTask.execute();
                }
                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
    }

    public class UserLogoutTask extends AsyncTask<Void, Void, Boolean> {
        private String email;
        private String sessionToken;

        UserLogoutTask(String email, String sessionToken) {
            this.email = email;
            this.sessionToken = sessionToken;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Retrofit retrofit = null;
            if (Build.MODEL.contains("Android SDK built for x86")){
                // Emulator
                retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8080/")
                        .addConverterFactory(JacksonConverterFactory.create())
                        .build();
            } else {
                // Real device
                retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.0.10:8080/")
                        .addConverterFactory(JacksonConverterFactory.create())
                        .build();
            }
            RFUserInterface userService = retrofit.create(RFUserInterface.class);

            // TODO: reset user and passwort
            LogoutRequest request = new LogoutRequest(this.email, this.sessionToken);

            LogoutResponse response = null;
            Call<LogoutResponse> call = userService.logout(request);
            try {
                response = call.execute().body();
                if(response != null) {
                    if (response.isLoggedOut() == Boolean.TRUE){
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                        return true;
                    } else {
                        return false;
                    }
                }
            }catch (IOException e) {
                //DO NOTHING - return false - APP shows text
                Log.e("ERROR", "NO USER");
            }
            return false;
        }
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {
        //This is called when the system detects that our NdefMessage was successfully sent.
        //TODO: SET STATUS ACCORDING TO SERVER OUTPUT
        status = 1;

        FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
        xfragmentTransaction.replace(R.id.containerView,new SuccessFragment()).commit();
    }

    public boolean isAlreadyChecked() {
        return alreadyChecked;
    }

    public void setAlreadyChecked(boolean alreadyChecked) {
        this.alreadyChecked = alreadyChecked;
    }

    public NfcAdapter getNfcAdapter() {
        return nfcAdapter;
    }

    public void setNfcAdapter(NfcAdapter nfcAdapter) {
        this.nfcAdapter = nfcAdapter;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /*
    * nexus schickt app - s7 schickt ndefmessage - warum?!
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }
}

