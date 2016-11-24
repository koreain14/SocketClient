package com.sds.study.socketclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 프레그먼트란 액티비티 내의 화면의 일부를 관리하는 객체!! 따라서 액티비티에 생명주기에 의존적이며
 * 화면을 관리한다는 점에 있어서는 액티비티와 상당히 유사하므로 자체적으로 생명주기 메서드가 있다!!
 */

public class ChatFragment extends Fragment implements View.OnClickListener{
    MainActivity mainActivity;

    TextView txt_receive;
    EditText txt_send;
    Button bt_send;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.chatfragment_layout,null);

        txt_receive=(TextView)view.findViewById(R.id.txt_receive);
        txt_send=(EditText)view.findViewById(R.id.txt_send);
        bt_send=(Button)view.findViewById(R.id.bt_send);

        bt_send.setOnClickListener(this);

        return view;
    }

    /*클라이언트 쪽 쓰레드에 send 호출하자!!*/
    public void onClick(View view) {
        /*프레그먼트는 자신을 관리한느 액티비티를 참조할 수 있다!*/

        mainActivity=(MainActivity) getContext();
        String msg=txt_send.getText().toString();

        mainActivity.clientThread.send(msg);
    }
}
