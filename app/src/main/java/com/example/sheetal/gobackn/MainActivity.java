package com.example.sheetal.gobackn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText EPacket;
    TextView TPacket;
    Button  Bsend;

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bsend = findViewById(R.id.button);
        EPacket = findViewById(R.id.editText);
        TPacket = findViewById(R.id.textView);

        databaseReference = FirebaseDatabase.getInstance().getReference("Data");

        Bsend.setOnClickListener(new View.OnClickListener() {
                String i =null;
            @Override
            public void onClick(View v) {
                 i= EPacket.getText().toString();
                // String [] seprate = i.split(" ");
                 EPacket.setText("");
                 Data data1 = new Data();
                 data1.setAck(0);
                 data1.setMessage(i);
                 databaseReference.setValue(data1);
             /*    for(int j = 0 ; j< seprate.length;j++){
                     Toast.makeText(MainActivity.this,"Text : "+ seprate[j],Toast.LENGTH_SHORT).show();

                     databaseReference.setValue(seprate[j]);
                 }*/
                TPacket.setText(i);

            }
        });
    }
}
