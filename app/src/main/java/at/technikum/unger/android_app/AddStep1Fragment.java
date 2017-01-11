package at.technikum.unger.android_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddStep1Fragment extends Fragment {
    Button addButton;
    EditText addValueText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addstep1,null);
        ((TabFragment)getParentFragment()).getViewPager().getAdapter().notifyDataSetChanged();

        addButton = (Button) view.findViewById(R.id.addButton);
        addButton.setEnabled(false);

        addValueText = (EditText) view.findViewById(R.id.addValue);
        addValueText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                if (!addValueText.getText().toString().equals("")){
                    addButton.setEnabled(true);
                } else {
                    addButton.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabFragment tabFragment = (TabFragment)getParentFragment();

                tabFragment.setValue(Double.parseDouble(addValueText.getText().toString()));

                ViewPager viewPager = tabFragment.getViewPager();
                viewPager.setCurrentItem(1);
                viewPager.getAdapter().notifyDataSetChanged();


//                // Create new fragment and transaction
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                Fragment fragment = new AddStep2Fragment();
//
//                // Replace whatever is in the fragment_container view with this fragment,
//                // and add the transaction to the back stack if needed
//                fragmentTransaction.replace(R.id.containerView, fragment);
//                fragmentTransaction.addToBackStack(null);
//
//                // Commit the transaction
//                fragmentTransaction.commit();
            }
        });
        return view;
    }
}