package com.example.agendas;

import android.app.Activity;

import androidx.appcompat.app.AlertDialog;

public class CxMsg {

    public static void Mensagem(String txt,  Activity activity){
        AlertDialog.Builder adb=new AlertDialog.Builder(activity);
        adb.setMessage(txt);
        adb.setNeutralButton("OK",null);
        adb.show();
    }

}
