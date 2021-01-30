package com.example.mls;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class excofullpage extends AppCompatActivity {
    ImageView excoimg;
    TextView nametv;
    TextView dobtv;
    TextView lvltv;
    TextView posttv;
    TextView statetv;
    TextView relitv;
    TextView emailtv;
    TextView phonetv;
    TextView igtv;
    TextView nremtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.new_excofull);
        excoimg = findViewById(R.id.excoimgv);
        nametv = findViewById(R.id.exconametv);
        dobtv = findViewById(R.id.excodobtv);
        lvltv = findViewById(R.id.excolvltv);
        posttv = findViewById(R.id.excoposttv);
        statetv = findViewById(R.id.excostatetv);
        relitv = findViewById(R.id.excorelitv);
        emailtv = findViewById(R.id.excoemailtv);
        phonetv = findViewById(R.id.excophnetv);
        igtv = findViewById(R.id.excoigtv);
        nremtv = findViewById(R.id.excoremtv);
        Intent i = getIntent();
        filldata(sharedData.getMyexco());
    }

    public void filldata(excoitem s) {
        /*switch(s){
            case "vp":
                Bitmap bit = BitmapFactory.decodeResource(getResources(),R.drawable.vp_photo);
                excoimg.setImageBitmap(bit);
                nametv.setText(getString(R.string.vp_name));
                dobtv.setText(getString(R.string.vp_DOB));
                lvltv.setText(getString(R.string.vp_Level));
                posttv.setText(getString(R.string.vp_Post));
                statetv.setText(getString(R.string.vp_State));
                relitv.setText(getString(R.string.vp_Religion));
                emailtv.setText(getString(R.string.vp_email));
                phonetv.setText(getString(R.string.vp_phone));
                igtv.setText(getString(R.string.vp_ig));
                nremtv.setText(getString(R.string.vp_remark));
                break;
            case "president":

                excoimg.setImageBitmap( BitmapFactory.decodeResource(getResources(),R.drawable.pres));
                nametv.setText(getString(R.string.pre_name));
                dobtv.setText(getString(R.string.pre_DOB));
                lvltv.setText(getString(R.string.pre_Level));
                posttv.setText(getString(R.string.pre_Post));
                statetv.setText(getString(R.string.pre_State));
                relitv.setText(getString(R.string.pre_Religion));
                emailtv.setText(getString(R.string.pre_email));
                phonetv.setText(getString(R.string.pre_phone));
                igtv.setText(getString(R.string.pre_ig));
                nremtv.setText(getString(R.string.pre_remark));
                break;
            case "fin":

                excoimg.setImageBitmap( BitmapFactory.decodeResource(getResources(),R.drawable.fin_sec_photo));
                nametv.setText(getString(R.string.Finsec_name));
                dobtv.setText(getString(R.string.Finsec_DOB));
                lvltv.setText(getString(R.string.Finsec_Level));
                posttv.setText(getString(R.string.Finsec_Post));
                statetv.setText(getString(R.string.Finsec_State));
                relitv.setText(getString(R.string.Finsec_Religion));
                emailtv.setText(getString(R.string.Finsec_email));
                phonetv.setText(getString(R.string.Finsec_phone));
                igtv.setText(getString(R.string.Finsec_ig));
                nremtv.setText(getString(R.string.Finsec_remark));
                break;

            case "GS":

                excoimg.setImageBitmap( BitmapFactory.decodeResource(getResources(),R.drawable.gs));
                nametv.setText(getString(R.string.GS_name));
                dobtv.setText(getString(R.string.GS_DOB));
                lvltv.setText(getString(R.string.GS_Level));
                posttv.setText(getString(R.string.GS_Post));
                statetv.setText(getString(R.string.GS_State));
                relitv.setText(getString(R.string.GS_Religion));
                emailtv.setText(getString(R.string.GS_email));
                phonetv.setText(getString(R.string.GS_phone));
                igtv.setText(getString(R.string.GS_ig));
                nremtv.setText(getString(R.string.GS_remark));
                break;

            case "AGS":

                excoimg.setImageBitmap( BitmapFactory.decodeResource(getResources(),R.drawable.ags));
                nametv.setText(getString(R.string.AGS_name));
                dobtv.setText(getString(R.string.AGS_DOB));
                lvltv.setText(getString(R.string.AGS_Level));
                posttv.setText(getString(R.string.AGS_Post));
                statetv.setText(getString(R.string.AGS_State));
                relitv.setText(getString(R.string.AGS_Religion));
                emailtv.setText(getString(R.string.AGS_email));
                phonetv.setText(getString(R.string.AGS_phone));
                igtv.setText(getString(R.string.AGS_ig));
                nremtv.setText(getString(R.string.AGS_remark));
                break;

            case "Acad":

                excoimg.setImageBitmap( BitmapFactory.decodeResource(getResources(),R.drawable.acad_sec_photo));
                nametv.setText(getString(R.string.Acad_sec_name));
                dobtv.setText(getString(R.string.Acad_sec_DOB));
                lvltv.setText(getString(R.string.Acad_sec_Level));
                posttv.setText(getString(R.string.Acad_sec_Post));
                statetv.setText(getString(R.string.Acad_sec_State));
                relitv.setText(getString(R.string.Acad_sec_Religion));
                emailtv.setText(getString(R.string.Acad_sec_email));
                phonetv.setText(getString(R.string.Acad_sec_phone));
                igtv.setText(getString(R.string.Acad_sec_ig));
                nremtv.setText(getString(R.string.Acad_sec_remark));
                break;
            case "welfare":

                excoimg.setImageBitmap( BitmapFactory.decodeResource(getResources(),R.drawable.social));
                nametv.setText(getString(R.string.welfare_name));
                dobtv.setText(getString(R.string.welfare_DOB));
                lvltv.setText(getString(R.string.welfare_Level));
                posttv.setText(getString(R.string.welfare_Post));
                statetv.setText(getString(R.string.welfare_State));
                relitv.setText(getString(R.string.welfare_Religion));
                emailtv.setText(getString(R.string.welfare_email));
                phonetv.setText(getString(R.string.welfare_phone));
                igtv.setText(getString(R.string.welfare_ig));
                nremtv.setText(getString(R.string.welfare_remark));
                break;

            case "social":

                excoimg.setImageBitmap( BitmapFactory.decodeResource(getResources(),R.drawable.social_sec_photo));
                nametv.setText(getString(R.string.Social_name));
                dobtv.setText(getString(R.string.Social_DOB));
                lvltv.setText(getString(R.string.Social_Level));
                posttv.setText(getString(R.string.Social_Post));
                statetv.setText(getString(R.string.Social_State));
                relitv.setText(getString(R.string.Social_Religion));
                emailtv.setText(getString(R.string.Social_email));
                phonetv.setText(getString(R.string.Social_phone));
                igtv.setText(getString(R.string.Social_ig));
                nremtv.setText(getString(R.string.Social_remark));
                break;

            case "sport":

                excoimg.setImageBitmap( BitmapFactory.decodeResource(getResources(),R.drawable.sport_sec_photo));
                nametv.setText(getString(R.string.Sport_sec_name));
                dobtv.setText(getString(R.string.Sport_sec_DOB));
                lvltv.setText(getString(R.string.Sport_sec_Level));
                posttv.setText(getString(R.string.Sport_sec_Post));
                statetv.setText(getString(R.string.Sport_sec_State));
                relitv.setText(getString(R.string.Sport_sec_Religion));
                emailtv.setText(getString(R.string.Sport_sec_email));
                phonetv.setText(getString(R.string.Sport_sec_phone));
                igtv.setText(getString(R.string.Sport_sec_ig));
                nremtv.setText(getString(R.string.Sport_sec_remark));
                break;

            case "PRO":

                excoimg.setImageBitmap( BitmapFactory.decodeResource(getResources(),R.drawable.pro));
                nametv.setText(getString(R.string.Pro_name));
                dobtv.setText(getString(R.string.Pro_DOB));
                lvltv.setText(getString(R.string.Pro_Level));
                posttv.setText(getString(R.string.Pro_Post));
                statetv.setText(getString(R.string.Pro_State));
                relitv.setText(getString(R.string.Pro_Religion));
                emailtv.setText(getString(R.string.Pro_email));
                phonetv.setText(getString(R.string.Pro_phone));
                igtv.setText(getString(R.string.Pro_ig));
                nremtv.setText(getString(R.string.Pro_remark));
                break;

            default:
                break;

        }*/
        excoimg.setImageBitmap(s.getBtm());
        nametv.setText(s.getName());
        dobtv.setText(s.getDob());
        lvltv.setText(s.getLevel());
        posttv.setText(s.getPosition());
        statetv.setText(s.getState());
        relitv.setText(s.getReligion());
        emailtv.setText(s.getEmail());
        phonetv.setText(s.getPhone());
        igtv.setText(s.getIG());
        nremtv.setText(s.getRemark());

    }
}
