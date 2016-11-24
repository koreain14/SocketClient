package com.sds.study.socketclient;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

/**
 * CRUD를 수행하는 객체!!
 */

public class ChatDAO {
    String TAG;
    SQLiteDatabase db;

    public ChatDAO(SQLiteDatabase db) {
        this.db = db;
        TAG=this.getClass().getName();
    }

    public void insert(Chat chat) {
        String sql="insert into chat(ip,port, nickname, img)";
        sql=sql+" values(?,?,?,?);";

        try {
            db.execSQL(sql, new String[]{
                    chat.getIp(), chat.getPort(), chat.getNickname(), chat.getImg()
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Chat select(int chat_id) {
        Cursor rs=db.rawQuery("select * from chat",null);
        Chat chat=null;
        if(rs.moveToNext()){// 레코드가 있다면
            chat=new Chat();
            chat.setChat_id(rs.getInt(rs.getColumnIndex("chat_id")));
            chat.setIp(rs.getString(rs.getColumnIndex("ip")));
            chat.setPort(rs.getString(rs.getColumnIndex("port")));
            chat.setNickname(rs.getString(rs.getColumnIndex("nickname")));
            chat.setImg(rs.getString(rs.getColumnIndex("img")));

            Log.d(TAG,"DAO의 chat은"+chat);
        }
        return chat;
    }

    public void update(Chat chat) {
        String sql="update chat set ip=?, port=?, nickname=?";
        try {
            db.execSQL(sql, new String[]{
                chat.getIp(), chat.getPort(), chat.getNickname()
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int chat_id) {

    }
}
