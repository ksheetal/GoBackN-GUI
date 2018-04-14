package com.example.sheetal.gobackn;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeScreenActivity extends AppCompatActivity {

    Button BsendMessage;
    Button BsendAck;

    TextView Tmessage;

    DatabaseReference databaseReference, db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        BsendMessage = findViewById(R.id.send_msg_button);
        BsendAck = findViewById(R.id.send_acknowledgment);
        Tmessage = findViewById(R.id.msg);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        db = FirebaseDatabase.getInstance().getReference("Data");

        BsendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String mMessage = null;
                int mAck = 0;
                for (DataSnapshot doc : dataSnapshot.getChildren()) {
                    Data data1 = doc.getValue(Data.class);
                    mMessage = data1.getMessage();
                    mAck = data1.getAck();
                    DataReceive(mMessage, mAck);
                }
                //  Tmessage.setText(mMessage);
                //  Toast.makeText(HomeScreenActivity.this, "Ack : " + mAck, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void DataReceive(final String mMessage, final int ack) {
        int i = mMessage.length();
        int j = mMessage.length();
        int k =i/j;
      //  int k1 = Integer.parseInt(String.valueOf(k));
        //k=k+1;
      //  Toast.makeText(HomeScreenActivity.this, "hiihi "+k, Toast.LENGTH_SHORT).show();
//         String mdata = mMessage;
      //  for (int m = 0; m < mMessage.length(); m++) {
            String[] seprate = mMessage.split(" ");
            if (ack == -1) {
                Toast.makeText(HomeScreenActivity.this, "Sender is waiting for Acknowledgment. ", Toast.LENGTH_SHORT).show();
                Tmessage.setText("Sender is waiting for Acknowledgment..");
            } else if (ack == 0) {
                Toast.makeText(HomeScreenActivity.this, "Data :" + seprate[k - 1], Toast.LENGTH_SHORT).show();
                Tmessage.setText(seprate[k - 1].toString());
                //k = k + 1;
                // StartCounter();
            } else if (ack == 1) {
                Toast.makeText(HomeScreenActivity.this, "Data :" + seprate[k], Toast.LENGTH_SHORT).show();
                Tmessage.setText(seprate[k].toString());
            } else if (ack == 2) {
                Toast.makeText(HomeScreenActivity.this, "Data :" + seprate[k +1], Toast.LENGTH_SHORT).show();
                Tmessage.setText(seprate[k + 1].toString());
            }

            //final int finalK = k;
            BsendAck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                         int ack = 0;
                        for(DataSnapshot doc : dataSnapshot.getChildren()){
                        Data data2 = doc.getValue(Data.class);
                        ack = data2.getAck();
                        checkack(ack);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                    String[] seprate = mMessage.split(" ");
                    Data data = new Data();
                    if(ack == 1){
                        data.setAck(2);
                    }if(ack ==0){
                        data.setAck(1);
                    }if(ack==2){
                        data.setAck(3);
                    }

                    data.setMessage(mMessage);
              //      finalK = finalK+1;
                    db.setValue(data);
                    //  Toast.makeText(HomeScreenActivity.this,seprate[finalK],Toast.LENGTH_SHORT).show();
                }
            });

            if (mMessage.toString().equals(null)) {
                Toast.makeText(HomeScreenActivity.this, "No Data Received.", Toast.LENGTH_SHORT).show();
                Tmessage.setText("No Packet Received.");
            }
        }

    private void checkack(int ack) {
        if(ack ==1){
            Toast.makeText(HomeScreenActivity.this, "First Packet Received...", Toast.LENGTH_SHORT).show();
        }else if(ack == 2){
            Toast.makeText(HomeScreenActivity.this, "Second Packet Received...", Toast.LENGTH_SHORT).show();
        }else if(ack == 3){
            Toast.makeText(HomeScreenActivity.this, "Third Packet Received...", Toast.LENGTH_SHORT).show();
        }

    }
    //}
}
