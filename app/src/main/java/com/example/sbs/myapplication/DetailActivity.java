package com.example.sbs.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle("할일 상세보기");

        int todoId = getIntent().getIntExtra("todoId", 0);
        String todoTitle = getIntent().getStringExtra("todoTitle");

        StringBuilder sb = new StringBuilder();
        sb.append("번호 : " + todoId);
        sb.append("\n");
        sb.append(todoTitle);

        TextView textViewTodo = findViewById(R.id.activity_detail__textViewTodo);
        textViewTodo.setText(sb.toString());
        textViewTodo.setOnClickListener(view -> {
            finish();
        });
    }
}