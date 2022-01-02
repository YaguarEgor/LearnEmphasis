package com.example.learnemphasis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.*;
import android.view.*;
import android.widget.*;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class testPage extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> listOfWords; //список всех слов
    ArrayList<String> listOfWordsInTest; //список слов, которые участвуют в тесте
    Button buttonFinishTask; //кнопка Завершить тестирование
    int numberOfWordsInTest; //количество слов, которые задействованы в тесте
    TextView textViewResult;
    TextView textViewRulesToReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);
        listOfWords = getWordsIntoList(); //список всех слов с их вариантами ударений
        listOfWordsInTest = new ArrayList<String>(); //слова, которые используются в тесте
        chooseWordsForTestAndMakeIt(); //беру случайные слова из списка и ставлю их в тест
        buttonFinishTask = findViewById(R.id.buttonFinishTest);
        buttonFinishTask.setOnClickListener(this);
        numberOfWordsInTest = 5;
        textViewResult = findViewById(R.id.textViewResult);
        textViewResult.setVisibility(View.INVISIBLE);
        textViewRulesToReview = findViewById(R.id.textViewRulesToReview);
    }

    protected ArrayList<String> getWordsIntoList() {
        //вытащил все слова из xml файла
        // 0 - слово без ударения, 1 - правильное ударение, 2 - неправильное
        ArrayList<String> listOfWords = new ArrayList<String>();
        try {
            XmlPullParser wordsXML = getResources().getXml(R.xml.words);
            while (wordsXML.getEventType()!= XmlPullParser.END_DOCUMENT) {
                if (wordsXML.getEventType() == XmlPullParser.TEXT) {
                    listOfWords.add(wordsXML.getText());
                }
                wordsXML.next();
            }
        }
        catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return listOfWords;
    }

    protected void chooseWordsForTestAndMakeIt() {
        Random rand = new Random();
        RadioButton rd1; //первый ответ
        RadioButton rd2; //второй ответ
        TextView question; //само слово, в котором надо поставить ударение
        int chooseOfRadioButton; //выбор на какой радиокнопке будет правильный ответ
        for (int i = 0; i < 5; i++) {
            String word;
            int indOfWord;
            do {
                indOfWord = rand.nextInt(listOfWords.size()) / 4;
                indOfWord *= 4;
                word = listOfWords.get(indOfWord);
            } //вибарю рандомное слово, чтобы оно не было уже использовано
            while (listOfWordsInTest.contains(word));
            listOfWordsInTest.add(word);
            switch(i) {
                case 0:
                    question = findViewById(R.id.textViewQuestion1);
                    rd1 = findViewById(R.id.radioButtonQuestion1Variant1);
                    rd2 = findViewById(R.id.radioButtonQuestion1Variant2);
                    chooseOfRadioButton = rand.nextInt(2) + 1;
                    rd1.setText(listOfWords.get(indOfWord+chooseOfRadioButton));
                    rd2.setText(listOfWords.get(indOfWord+3-chooseOfRadioButton));
                    question.setText(word);
                    break;
                case 1:
                    question = findViewById(R.id.textViewQuestion2);
                    rd1 = findViewById(R.id.radioButtonQuestion2Variant1);
                    rd2 = findViewById(R.id.radioButtonQuestion2Variant2);
                    chooseOfRadioButton = rand.nextInt(2) + 1;
                    rd1.setText(listOfWords.get(indOfWord+chooseOfRadioButton));
                    rd2.setText(listOfWords.get(indOfWord+3-chooseOfRadioButton));
                    question.setText(word);
                    break;
                case 2:
                    question = findViewById(R.id.textViewQuestion3);
                    rd1 = findViewById(R.id.radioButtonQuestion3Variant1);
                    rd2 = findViewById(R.id.radioButtonQuestion3Variant2);
                    chooseOfRadioButton = rand.nextInt(2) + 1;
                    rd1.setText(listOfWords.get(indOfWord+chooseOfRadioButton));
                    rd2.setText(listOfWords.get(indOfWord+3-chooseOfRadioButton));
                    question.setText(word);
                    break;
                case 3:
                    question = findViewById(R.id.textViewQuestion4);
                    rd1 = findViewById(R.id.radioButtonQuestion4Variant1);
                    rd2 = findViewById(R.id.radioButtonQuestion4Variant2);
                    chooseOfRadioButton = rand.nextInt(2) + 1;
                    rd1.setText(listOfWords.get(indOfWord+chooseOfRadioButton));
                    rd2.setText(listOfWords.get(indOfWord+3-chooseOfRadioButton));
                    question.setText(word);
                    break;
                case 4:
                    question = findViewById(R.id.textViewQuestion5);
                    rd1 = findViewById(R.id.radioButtonQuestion5Variant1);
                    rd2 = findViewById(R.id.radioButtonQuestion5Variant2);
                    chooseOfRadioButton = rand.nextInt(2) + 1;
                    rd1.setText(listOfWords.get(indOfWord+chooseOfRadioButton));
                    rd2.setText(listOfWords.get(indOfWord+3-chooseOfRadioButton));
                    question.setText(word);
                    break;
            } //по индексу i выбирает нужную радиогруппу и туда ставит слово
        }
    }

    public void onClick(View v) {
        int result = 0;
        ArrayList<String> listOfResults = new ArrayList<>();
        ArrayList<String> listWithRulesToReview = new ArrayList<>();
        String rulesToReview = "";
        //region Description
        RadioGroup rg1 = findViewById(R.id.radioGroup1);
        RadioButton rd1 = findViewById(rg1.getCheckedRadioButtonId());
        RadioGroup rg2 = findViewById(R.id.radioGroup2);
        RadioButton rd2 = findViewById(rg2.getCheckedRadioButtonId());
        RadioGroup rg3 = findViewById(R.id.radioGroup3);
        RadioButton rd3 = findViewById(rg3.getCheckedRadioButtonId());
        RadioGroup rg4 = findViewById(R.id.radioGroup4);
        RadioButton rd4 = findViewById(rg4.getCheckedRadioButtonId());
        RadioGroup rg5 = findViewById(R.id.radioGroup5);
        RadioButton rd5 = findViewById(rg5.getCheckedRadioButtonId());
        //endregion
        //ищу все радиокнопки по id
        if (rg1.getCheckedRadioButtonId() == -1 || rg2.getCheckedRadioButtonId() == -1 || rg3.getCheckedRadioButtonId() == -1 || rg4.getCheckedRadioButtonId() == -1 || rg5.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Вы не ответили на все вопросы", Toast.LENGTH_LONG).show();
        }
        else {
            if (rd1.getText() == listOfWords.get(listOfWords.lastIndexOf(listOfWordsInTest.get(0)) + 1)) {
                result++;
                rd1.setTextColor(Color.parseColor("GREEN"));
            }
            else {
                rd1.setTextColor(Color.parseColor("RED"));
                listWithRulesToReview.add(listOfWords.get(listOfWords.lastIndexOf(listOfWordsInTest.get(0)) + 3));
            }

            if (rd2.getText() == listOfWords.get(listOfWords.lastIndexOf(listOfWordsInTest.get(1)) + 1)) {
                result++;
                rd2.setTextColor(Color.parseColor("GREEN"));
            }
            else {
                rd2.setTextColor(Color.parseColor("RED"));
                listWithRulesToReview.add(listOfWords.get(listOfWords.lastIndexOf(listOfWordsInTest.get(1)) + 3));
            }

            if (rd3.getText() == listOfWords.get(listOfWords.lastIndexOf(listOfWordsInTest.get(2)) + 1)) {
                result++;
                rd3.setTextColor(Color.parseColor("GREEN"));
            }
            else {
                rd3.setTextColor(Color.parseColor("RED"));
                listWithRulesToReview.add(listOfWords.get(listOfWords.lastIndexOf(listOfWordsInTest.get(2)) + 3));
            }

            if (rd4.getText() == listOfWords.get(listOfWords.lastIndexOf(listOfWordsInTest.get(3)) + 1)) {
                result++;
                rd4.setTextColor(Color.parseColor("GREEN"));
            }
            else {
                rd4.setTextColor(Color.parseColor("RED"));
                listWithRulesToReview.add(listOfWords.get(listOfWords.lastIndexOf(listOfWordsInTest.get(3)) + 3));
            }

            if (rd5.getText() == listOfWords.get(listOfWords.lastIndexOf(listOfWordsInTest.get(4)) + 1)) {
                result++;
                rd5.setTextColor(Color.parseColor("GREEN"));
            }
            else {
                rd5.setTextColor(Color.parseColor("RED"));
                listWithRulesToReview.add(listOfWords.get(listOfWords.lastIndexOf(listOfWordsInTest.get(4)) + 3));
            }
            textViewResult.setText(getText(R.string.result) + " " + getText(R.string.result2) + " " + Integer.toString(result) + getText(R.string.result3) + " " + Integer.toString(5-result));
            textViewResult.setVisibility(View.VISIBLE);
            if (!listWithRulesToReview.isEmpty()) {
                rulesToReview = "Повторите следующие правила: ";
                for (int i = 0; i < listWithRulesToReview.size(); i++) {
                    rulesToReview += listWithRulesToReview.get(i);
                    if (i != listWithRulesToReview.size()-1) rulesToReview += ", ";
                }
                textViewRulesToReview.setText(rulesToReview);
            }
            try {
                listOfResults = getLatestResultsFromXMLFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            if (listOfResults.size()<15) {
                addLatestResultToXMLFileNotFull(listOfResults, result, rulesToReview);
            }
        } //проверяю правильность ответов. Подсвечиваю правильные ответы зеленым, неправильные - красным

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

    public void addLatestResultToXMLFileNotFull(ArrayList<String> listOfResults, int result, String rulesToReview) {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse("file:C:\\Users\\Egor0\\AndroidStudioProjects\\LearnEmphasis\\app\\src\\main\\res\\xml\\latest_results.xml");
            NodeList results = doc.getElementsByTagName("result");
            NodeList resultFromXML = results.item(0).getChildNodes();
            Node node;
            Element el;
            node = resultFromXML.item(0);
            el = (Element) node;
            Date dateNow = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd HH:mm");
            el.setTextContent(formatForDateNow.format(dateNow));
            node = resultFromXML.item(1);
            el = (Element) node;
            el.setTextContent(Integer.toString(result)+"/5");
            node = resultFromXML.item(2);
            el = (Element) node;
            if (!rulesToReview.isEmpty()) {
                el.setTextContent(rulesToReview);
            }
            else {
                el.setTextContent("-");
            }


        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (SAXException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}