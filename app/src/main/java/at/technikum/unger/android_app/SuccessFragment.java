package at.technikum.unger.android_app;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SuccessFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SuccessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuccessFragment extends Fragment {

    FragmentManager mFragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_success, null);

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3500);
                    mFragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new OverviewFragment()).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity.getStatus() == 1) {
            imageView.setImageResource(android.R.color.holo_green_dark);
            Toast.makeText(getActivity(), "It worked!", Toast.LENGTH_LONG).show();
            mainActivity.setStatus(0);
            thread.start();
        }
        if (mainActivity.getStatus() == -1) {
            imageView.setImageResource(android.R.color.holo_red_dark);
            Toast.makeText(getActivity(), "It failed.", Toast.LENGTH_LONG).show();
            mainActivity.setStatus(0);
            thread.start();
        }

        return view;
    }
}
