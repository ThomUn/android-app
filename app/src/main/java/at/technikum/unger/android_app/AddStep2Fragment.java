package at.technikum.unger.android_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddStep2Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddStep2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddStep2Fragment extends Fragment {
    Button b1;
    private ProgressBar spinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addstep2,null);

        b1=(Button)view.findViewById(R.id.sendButton);
        spinner=(ProgressBar)view.findViewById(R.id.progressBar);
        spinner.setVisibility(View.INVISIBLE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getVisibility() == View.INVISIBLE) {
                    spinner.setVisibility(View.VISIBLE);
                    return;
                }
                if (spinner.getVisibility() == View.VISIBLE) {
                    spinner.setVisibility(View.INVISIBLE);
                    return;
                }

            }
        });

        return view;
    }
}
