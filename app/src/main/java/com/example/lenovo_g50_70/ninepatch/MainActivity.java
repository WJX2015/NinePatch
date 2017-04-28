package com.example.lenovo_g50_70.ninepatch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Msg> msgList =new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //百度云推送初始化
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,"OTkZcEh6GaAftccdkGDKZuPW");
        initMsgs();
        inputText = (EditText) findViewById(R.id.input_text);
        msgRecyclerView= (RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter =new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content =inputText.getText().toString();
                if(!"".equals(content)){
                    Msg msg=new Msg(content,Msg.TYPE_SENT);
                    msgList.add(msg);
                    //当有新消息时，刷新RecyclerView中的显示
                    adapter.notifyItemInserted(msgList.size()-1);
                    //将RecyclerView定位到最后一行
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    inputText.setText("");
                }
            }
        });
    }

    private void initMsgs() {
        Msg msg1=new Msg("Hello guy.",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2=new Msg("Hello. Who is that?",Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3=new Msg("This is Tom.Nice to talking to you",Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }
}
