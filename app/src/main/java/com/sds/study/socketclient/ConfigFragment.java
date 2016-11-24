package com.sds.study.socketclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * 프레그먼트란 액티비티 내의 화면의 일부를 관리하는 객체!! 따라서 액티비티에 생명주기에 의존적이며
 * 화면을 관리한다는 점에 있어서는 액티비티와 상당히 유사하므로 자체적으로 생명주기 메서드가 있다!!
 */

public class ConfigFragment extends Fragment implements View.OnClickListener{
    String TAG;
    EditText txt_ip, txt_port, txt_nickname;
    ImageView img;
    Button bt_regist;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.configfragment_layout, null);

        txt_ip = (EditText) view.findViewById(R.id.txt_ip);
        txt_port = (EditText) view.findViewById(R.id.txt_port);
        txt_nickname = (EditText) view.findViewById(R.id.txt_nickname);
        img = (ImageView) view.findViewById(R.id.img);
        bt_regist=(Button)view.findViewById(R.id.bt_regist);

        bt_regist.setOnClickListener(this);

        return view;
    }

    /*각 뷰의 알맞는 데이터 넣기*/
    public void setData(Chat chat) {
        TAG = this.getClass().getName();

        txt_ip.setText(chat.getIp());
        txt_port.setText(chat.getPort());
        txt_nickname.setText(chat.getNickname());
    }

    public void onClick(View view) {
        MainActivity mainActivity=(MainActivity)getContext();
        Chat chat=new Chat();


        chat.setIp(txt_ip.getText().toString());
        chat.setPort(txt_port.getText().toString());
        chat.setNickname(txt_nickname.getText().toString());

        mainActivity.chat=chat;
        mainActivity.chatDAO.update(chat);
    }
}
