package at.technikum.unger.android_app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddStep2Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddStep2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddStep2Fragment extends Fragment {
    Button sendButton;
    EditText sendCodeText;
    private ProgressBar spinner;
    NfcAdapter nfcAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addstep2, null);

        sendCodeText = (EditText)view.findViewById(R.id.sendCode);
        sendCodeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                if (!sendCodeText.getText().toString().equals("")){
                    sendButton.setEnabled(true);
                } else {
                    sendButton.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        sendButton =(Button)view.findViewById(R.id.sendButton);
        spinner=(ProgressBar)view.findViewById(R.id.progressBar);
        spinner.setVisibility(View.INVISIBLE);

        MainActivity mainActivity = (MainActivity)this.getActivity();

        PackageManager pm = getActivity().getPackageManager();
        // Check whether NFC is available on device
        if (mainActivity.isAlreadyChecked() == false){
            if (!pm.hasSystemFeature(PackageManager.FEATURE_NFC)) {
                // NFC is not available on the device.
                Toast.makeText(getActivity(), "The device does not have NFC hardware.",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                // NFC and Android Beam file transfer is supported.
                Toast.makeText(getActivity(), "Android Beam is supported on your device.",
                        Toast.LENGTH_SHORT).show();
                mainActivity.setAlreadyChecked(true);
            }
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getVisibility() == View.INVISIBLE) {
                    spinner.setVisibility(View.VISIBLE);

                    //NFC CONNECTION!
                    nfcAdapter = ((MainActivity)getActivity()).getNfcAdapter();
                    // Check whether NFC is enabled on device
                    if(!nfcAdapter.isEnabled()){
                        // NFC is disabled, show the settings UI
                        // to enable NFC
                        Toast.makeText(getActivity(), "Please enable NFC.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
                    }
                    // Check whether Android Beam feature is enabled on device
                    else if(!nfcAdapter.isNdefPushEnabled()) {
                        // Android Beam is disabled, show the settings UI
                        // to enable Android Beam
                        Toast.makeText(getActivity(), "Please enable Android Beam.",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Settings.ACTION_NFCSHARING_SETTINGS));
                    } else {
//                        String text = ("Beam me up, Android!\n\n" + "Beam Time: " + System.currentTimeMillis());
                        String text = ("Sender der Überweisung: " + getActivity().getIntent().getStringExtra("username") + "\n" +
                                "Betrag: " + ((TabFragment)getParentFragment()).getValue() + "Euro\n" +
                                "Bestätigungscode: " + sendCodeText.getText().toString());

                        //
                        byte[] payload = new byte[1 + text.getBytes().length];
                        System.arraycopy(text.getBytes(), 0, payload, 1 , text.getBytes().length);

                        NdefRecord record = new NdefRecord(
                                NdefRecord.TNF_WELL_KNOWN,  //Our 3-bit Type name format
                                NdefRecord.RTD_TEXT,        //Description of our payload
                                new byte[0],                //The optional id for our Record
                                payload);

                        NdefMessage msg = new NdefMessage(record);

                        nfcAdapter.setNdefPushMessage(msg, getActivity());
                    }
                    return;
                }
//                if (spinner.getVisibility() == View.VISIBLE) {
//                    spinner.setVisibility(View.INVISIBLE);
//                    return;
//                }

            }
        });

        return view;
    }


}
