package com.sds.study.socketclient;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    String TAG;
    ViewPager viewPager;
    MyPagerAdapter myPagerAdapter;
    Toolbar toolbar;
    MyOpenHelper myOpenHelper;
    ChatDAO chatDAO;
    Chat chat;

    Thread connectThread; // 접속용
    ClientThread clientThread; // 대화용
    MainActivity mainActivity;
    Handler handler; // UI반영
    ChatFragment chatFragment;

    Socket socket; // 자바 SE api를 쓴다!!
    String ip;

    int port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = this;
        TAG = this.getClass().getName();

        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager()); // 프레그먼트 매니저를 얻어와야한다!!
        viewPager.setAdapter(myPagerAdapter);

        /*toolbar얻자*/
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        viewPager.addOnPageChangeListener(this);

        /*앱바로 적용되는 시점*/
        setSupportActionBar(toolbar);
        init();


        chatFragment = (ChatFragment) myPagerAdapter.getItem(0);

        /*핸들러 재정의*/
        handler = new Handler() {
            public void handleMessage(Message message) {
                /*프레그먼트의 대화내역창에 메세지 출력*/
                String msg = message.getData().getString("msg");
                chatFragment.txt_receive.append(msg+"\n");
            }
        };
    }

    /*데이터베이스 초기화 및 SQLiteDatabase 객체 얻기*/
    public void init() {
        myOpenHelper = new MyOpenHelper(this);

        /*아래의 메서드가 호출될 때, onCreate나 onUpgrade 호출!!*/
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        chatDAO = new ChatDAO(db);

        chat = chatDAO.select(0);

        Log.d(TAG, chat.getIp());
        Log.d(TAG, chat.getPort());
        Log.d(TAG, chat.getNickname());

        /*접속관련 정보 셋팅*/
        ip = chat.getIp();
        port = Integer.parseInt(chat.getPort());

        /*두번째 프레그먼트 접근하여, 알맞는 값 대입하기!!*/
        ConfigFragment configFragment = (ConfigFragment) myPagerAdapter.getItem(1);
        //configFragment.setData(chat);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*회전 애니메이션 효과 주기!!*/
    public void setRotate(View view) {
        Animation ani = AnimationUtils.loadAnimation(this, R.anim.ani_config);
        view.startAnimation(ani);
    }

    /*메뉴를 선택하면 동작*/
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_connect:
                /*접속시도*/
                connectServer();
                break;
            case R.id.menu_chat:
                viewPager.setCurrentItem(0);
                break; // 페이지 전환!!

            case R.id.menu_config:
                viewPager.setCurrentItem(1);
                //setRotate(null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*페이지 관련 이벤트*/

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /*사용자가 해당 페이지를 확정지을 때(commit시)*/
    public void onPageSelected(int position) {
        if (position == 1) {// 설정화면 일 때만
            ConfigFragment configFragment = (ConfigFragment) myPagerAdapter.fragments[position];
            configFragment.setData(chat);
        }
    }

    public void onPageScrollStateChanged(int state) {
    }

    /*SQLite에 등록된 정보대로 소켓 서버에 접속하자!!*/
    public void connectServer() {
        connectThread = new Thread() {
            public void run() {
                try {
                    socket = new Socket(ip, port); // 소켓을 메모리에 올리는 것 자체가 접속시도이다!!
                    clientThread = new ClientThread(mainActivity, socket);
                    clientThread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        connectThread.start();
    }
}
