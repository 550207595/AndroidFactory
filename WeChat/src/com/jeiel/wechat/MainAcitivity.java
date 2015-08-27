package com.jeiel.wechatui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private List<Msg> msgList=new ArrayList<Msg>();
    private MsgAdapter adapter;
    private ListView msgListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMsgList();
        msgListView=(ListView)findViewById(R.id.msg_list_view);
        adapter=new MsgAdapter(this,R.layout.msg_item,msgList);
        msgListView.setAdapter(adapter);
        Button sendBtn=(Button)findViewById(R.id.send_btn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Msg msg = new Msg();
                EditText msgText=(EditText)findViewById(R.id.msg_text);
                msg.setContent(msgText.getText().toString());
                msg.setType(Msg.SEND);
                msgList.add(msg);
                adapter.notifyDataSetChanged();
                msgListView.setSelection(msgList.size());
                msgText.setText("");
            }
        });
    }

    public void initMsgList(){
        Msg msg1=new Msg();
        msg1.setContent("msg1");
        msg1.setType(Msg.RECEIVE);
        msgList.add(msg1);
        Msg msg2=new Msg();
        msg2.setContent("msg2");
        msg2.setType(Msg.SEND);
        msgList.add(msg2);
        Msg msg3=new Msg();
        msg3.setContent("msg3");
        msg3.setType(Msg.RECEIVE);
        msgList.add(msg3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
