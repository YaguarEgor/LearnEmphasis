package com.example.learnemphasis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonTestPage;
    private Button buttonRulesPage;
    private Button buttonLatestResultsPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonTestPage = findViewById(R.id.buttonOpenTestPage); //кнопка теста
        buttonRulesPage = findViewById(R.id.buttonRules); //кнопка правил
        buttonLatestResultsPage = findViewById(R.id.buttonLatestReults); //кнопка последних результатов
        buttonLatestResultsPage.setOnClickListener(this);
        buttonRulesPage.setOnClickListener(this);
        buttonTestPage.setOnClickListener(this); //ставлю ожидание нажатия на кнопку
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonOpenTestPage:
                Intent openTestPage = new Intent(this, testPage.class);
                startActivity(openTestPage); //перехожу на новую страницу с тестом
                break;
            case R.id.buttonRules:
                Intent openRulesPage = new Intent(this, rulesPage.class);
                startActivity(openRulesPage); //перехожу на новую страницу с правилами
                break;
            case R.id.buttonLatestReults:
                Intent openLatestResultsPage = new Intent(this, latestResults.class);
                startActivity(openLatestResultsPage); //перехожу на новую страницу с последними результатами
                break;
            default:
                break;
        }
    }
}