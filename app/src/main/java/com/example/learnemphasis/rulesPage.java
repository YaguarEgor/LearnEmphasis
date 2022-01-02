package com.example.learnemphasis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class MyExpandableListView extends BaseExpandableListAdapter {
    private Context context;
    private int numberOfThemes; //количество тем (правил)

    public MyExpandableListView(Context context) {
        this.context = context;
        numberOfThemes = 4;
    }

    @Override
    public int getGroupCount() {
        return numberOfThemes;
    } //возвращает количество тем

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    } //возвращает количество "детей" у темы

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //задаю внешний вид описания темы (по сути, просто название темы)
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_groupview, null);
        }
        ArrayList<String> listOfThemes = null;
        try {
            listOfThemes = getRulesFromXMLFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //вытаскиваю название темы и устанавливаю ее по номеру группы + задаю размер побольше
        ((TextView) convertView.findViewById(R.id.question)).setText(listOfThemes.get(groupPosition*2));
        ((TextView) convertView.findViewById(R.id.question)).setTextSize(20);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)  {
        //задаю внешний вид внутренности expandable list view (то, что выпадет по нажатию на тему)
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview_groupview_childview, null);
        ArrayList<String> listOfThemes = new ArrayList<String>();
        try {
            listOfThemes = getRulesFromXMLFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //ставлю нужное описание темы исходя из номера группы (темы)
        ((TextView) convertView.findViewById(R.id.answer)).setText(listOfThemes.get(groupPosition*2+1));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public ArrayList<String> getRulesFromXMLFile() throws IOException {
        //вытаскиваю все правила из xml файла
        ArrayList<String> listOfThemes = new ArrayList<String>();
        try {
            XmlPullParser rulesXML = context.getResources().getXml(R.xml.rules);
            while (rulesXML.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (rulesXML.getEventType() == XmlPullParser.TEXT) {
                    listOfThemes.add(rulesXML.getText());
                }
                rulesXML.next();
            }
        }
        catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return listOfThemes;
    }
}


    public class rulesPage extends AppCompatActivity {
    ExpandableListView listView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_page);
        listView1 = findViewById(R.id.expandableList);
        listView1.setAdapter(new MyExpandableListView(this));
        MyExpandableListView myListView = new MyExpandableListView(this);
    }
}
