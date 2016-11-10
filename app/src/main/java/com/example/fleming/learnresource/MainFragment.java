package com.example.fleming.learnresource;

import android.content.res.XmlResourceParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * MainFragment
 * Created by Fleming on 2016/11/10.
 */

public class MainFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    @BindView(R.id.bt_string)
    Button btString;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.bt_color)
    Button btColor;
    @BindView(R.id.bt_size)
    Button btSize;
    @BindView(R.id.bt_array)
    Button btArray;
    @BindView(R.id.bt_raw)
    Button btRaw;
    @BindView(R.id.bt_xml)
    Button btXml;
    @BindView(R.id.bt_image)
    Button btImage;
    @BindView(R.id.imageView)
    ImageView imageView;
    private MediaPlayer mPlayer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.bt_string, R.id.bt_color, R.id.bt_size, R.id.bt_array, R.id.bt_xml, R.id.bt_raw, R.id.bt_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_string:
                tvResult.setText(getResources().getString(R.string.text));
                break;
            case R.id.bt_color:
                tvResult.setTextColor(getResources().getColor(R.color.blue));
                break;
            case R.id.bt_size:
                tvResult.setTextSize(getResources().getDimension(R.dimen.text_size));
                break;
            case R.id.bt_array:
                String[] weeks = getResources().getStringArray(R.array.week);
                whatIsToday(weeks);
                break;
            case R.id.bt_xml:
                XmlResourceParser userXml = getResources().getXml(R.xml.user);
                parseXml(userXml);
                break;
            case R.id.bt_raw:
                InputStream inputStream = getResources().openRawResource(R.raw.ggh);
                mPlayer = MediaPlayer.create(getActivity(), R.raw.ggh);
                mPlayer.start();
                tvResult.setText(R.string.play_song);
                break;
            case R.id.bt_image:
                imageView.setImageDrawable(getResources().getDrawable(R.mipmap.miss));
                break;
        }
    }

    private void parseXml(XmlResourceParser xmlParser) {
        int event;
        try {
            event = xmlParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlParser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (nodeName.equals("name")) {
                            tvResult.setText("通过PULL解析器解析出了:" + xmlParser.nextText());
                        }
                        break;
                }
                event = xmlParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void whatIsToday(String[] weeks) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        tvResult.setText("Today is " + weeks[day - 1]);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.reset) {
            tvResult.setText("啊哦，被" + getResources().getString(R.string.reset) +"咯");
            tvResult.setTextColor(getResources().getColor(R.color.text_color_gray));
            tvResult.setTextSize(14);
            releaseMediaPlayer();
            imageView.setImageResource(R.mipmap.ic_launcher);
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
    }
}
