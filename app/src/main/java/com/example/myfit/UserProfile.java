package com.example.myfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity
{
    Button save_creds;
    RadioGroup ch_gender;
    EditText ename,eage,eheight,ewt,eemail;
    DatabaseReference insertData ;
    Profiles profile;
    long maxid = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        save_creds=(Button) findViewById(R.id.createText);
        ch_gender=(RadioGroup) findViewById(R.id.editGender);
        ename=(EditText)findViewById(R.id.name);
        eage=(EditText)findViewById(R.id.editAge);
        eheight=(EditText)findViewById(R.id.editHeight);
        ewt=(EditText)findViewById(R.id.editWeight);



        profile=new Profiles();

        insertData= FirebaseDatabase.getInstance().getReference().child("Profiles");
        insertData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    maxid=(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        save_creds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertProfile();
            }
        });
    }

    private void insertProfile()
    {
        final RadioGroup myRadioGroup = findViewById(R.id.editGender);

        String name = ename.getText().toString();
        int age = Integer.parseInt(eage.getText().toString().trim());
        float height = Float.parseFloat(eheight.getText().toString().trim());
        float weight = Float.parseFloat(ewt.getText().toString().trim());
        int gender = myRadioGroup.indexOfChild(findViewById(R.id.editGender));

        profile.setName(name);
        profile.setAge(age);
        profile.setHeight(height);
        profile.setWeight(weight);
        profile.setGender(gender);

        insertData.child(String.valueOf(maxid+1)).setValue(profile);


        Toast.makeText(this, "Profile Created!", Toast.LENGTH_SHORT).show();

        Intent intent2 = new Intent(this, HomePage2.class);
        startActivity(intent2);

    }
}