package at.technikum.unger.android_app;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OverviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverviewFragment extends Fragment {
    TextView welcomeText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
//        setContentView(R.layout.activity_overview);

//        Button button = (Button) view.findViewById(R.id.button3);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = getActivity().getApplicationContext();
//                CharSequence string = "Toastitoast";
//
//                Toast toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });


        welcomeText = (TextView) view.findViewById(R.id.welcomeText);

        String wellcome = "Willkommen " + getActivity().getIntent().getStringExtra("username") + "!";
        welcomeText.setText(wellcome);
        // https://github.com/PhilJay/MPAndroidChart/wiki/Getting-Started
        HorizontalBarChart mChart = (HorizontalBarChart) view.findViewById(R.id.chart);

        //SET BALANCE FROM USER
//        float mBalance = 10.3f;
        float mBalance = Float.parseFloat(String.valueOf(getActivity().getIntent().getDoubleExtra("balance", 0.1)));

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.getDescription().setEnabled(false);
        mChart.setDrawGridBackground(false);

        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);

        YAxis yl = mChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        yl.setAxisMaximum(mBalance+2.5f);

        YAxis yr = mChart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        yr.setAxisMaximum(mBalance+2.5f);

        mChart.setFitBars(true);
        mChart.animateY(2500);

        mChart.setExtraBottomOffset(15f);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(12f);
        l.setTextSize(12f);
        l.setXEntrySpace(100f);

        List<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(1, mBalance));

        BarDataSet dataSet = new BarDataSet(entries, "Guthaben");
        if(mBalance < 1) {
            dataSet.setColor(Color.RED);
        } else if (mBalance >= 1 && mBalance < 5) {
            dataSet.setColor(Color.YELLOW);
        } else if (mBalance >= 5) {
            dataSet.setColor(Color.GREEN);
        }
        dataSet.setValueTextColor(Color.RED); // styling, ..
        dataSet.setValueTextSize(15f);

        BarData barData = new BarData(dataSet);
        mChart.setData(barData);
        mChart.invalidate();

        return view;
    }
}
