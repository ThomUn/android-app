package at.technikum.unger.android_app;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class OverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Button button = (Button) findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence string = "Toastitoast";

                Toast toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // https://github.com/PhilJay/MPAndroidChart/wiki/Getting-Started
        HorizontalBarChart mChart = (HorizontalBarChart) findViewById(R.id.chart);

        //SET BALANCE FROM USER
        float mBalance = 10.3f;

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

    }
}
