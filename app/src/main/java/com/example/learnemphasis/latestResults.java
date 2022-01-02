package com.example.learnemphasis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class latestResults extends AppCompatActivity {

    private ArrayList<String> listOfResults;
    private ArrayList<TextView> listOfTextViewsWithResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_results);
        try {
            listOfResults = getLatestResultsFromXMLFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        listOfTextViewsWithResults = new ArrayList<>();
        listOfTextViewsWithResults.add(findViewById(R.id.textViewFirstResult));
        listOfTextViewsWithResults.add(findViewById(R.id.textViewSecondResult));
        listOfTextViewsWithResults.add(findViewById(R.id.textViewThirdResult));
        listOfTextViewsWithResults.add(findViewById(R.id.textViewForthResult));
        listOfTextViewsWithResults.add(findViewById(R.id.textViewFifthResult));
        fillTextViewsWithLatestResults();
    }

    public ArrayList<String> getLatestResultsFromXMLFile() throws IOException {
        //вытаскиваю все правила из xml файла
        ArrayList<String> listOfResults = new ArrayList<String>();
        try {
            XmlPullParser rulesXML = getResources().getXml(R.xml.latest_results);
            while (rulesXML.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (rulesXML.getEventType() == XmlPullParser.TEXT) {
                    listOfResults.add(rulesXML.getText());
                }
                rulesXML.next();
            }
        }
        catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return listOfResults;
    }

    public void fillTextViewsWithLatestResults() {
        Log.d("MyTag", Integer.toString(listOfResults.size()));
        for (int i = 0; i < listOfResults.size()/3; i++) {
            if (listOfResults.get(i*3+1).equals("5/5")) {
                listOfTextViewsWithResults.get(i).setText("Дата: " + listOfResults.get(i*3) +
                        "\nИтог тестирования: " + listOfResults.get(i*3+1));
            }
            else {
                listOfTextViewsWithResults.get(i).setText("Дата: " + listOfResults.get(i*3) +
                        "\nИтог тестирования: " + listOfResults.get(i*3+1) +
                        "\nПравила, которые стоит повторить: " + listOfResults.get(i*3+2));
            }

        }
    }
}