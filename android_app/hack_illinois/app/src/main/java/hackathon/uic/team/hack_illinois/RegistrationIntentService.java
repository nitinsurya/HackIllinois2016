package hackathon.uic.team.hack_illinois;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RegistrationIntentService extends IntentService {

    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String GCM_TOKEN = "gcmToken";
    // abbreviated tag name
    private static final String TAG = "RegIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Make a call to Instance API
        String token;
        SharedPreferences sharedPreferences;

        String senderId = getResources().getString(R.string.gcmsenderid);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        try{
            // request token that will be used by the server to send push notifications
            InstanceID instanceID = InstanceID.getInstance(this);
            token = (instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE,null));

            Log.i(TAG, "GCM Registration Token: " + token);



            // Fetch token here

                // save token
                sharedPreferences.edit().putString(GCM_TOKEN, token).apply();
                // pass along this data
                sendRegistrationToServer(token);
            } catch (IOException e) {
                Log.d(TAG, "Failed to complete token refresh", e);
                // If an exception happens while fetching the new token or updating our registration data
                // on a third-party server, this ensures that we'll attempt the update at a later time.
                sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, false).apply();



        }
    }

    private void sendRegistrationToServer(String token) {
        // send network request

        // if registration sent was successful, store a boolean that indicates whether the generated token has been sent to server
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, true).apply();
    }

}
