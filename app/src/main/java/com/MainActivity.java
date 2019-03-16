package com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.widget.Toast;

//import
import com.example.xy.plan.R;
import com.plan.PlanActivity;
import com.tomatobell.Screen.setParam;


public class MainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button tomatobell=(Button) findViewById(R.id.tomatobell);
        tomatobell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,setParam.class);
                startActivity(intent);
            }
        });

        Button plan=(Button) findViewById(R.id.plan);
        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,PlanActivity.class);
                startActivity(intent);
            }
        });

        }

}
