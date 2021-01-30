package com.example.mls;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class excoitem implements Parcelable {
    Bitmap btm;
    String Name, dob, level, position, state, religion, email, phone, IG, remark;

    public excoitem(Bitmap btm, String name, String dob, String level, String position, String state, String religion, String email, String phone, String IG, String remark) {
        this.btm = btm;
        Name = name;
        this.dob = dob;
        this.level = level;
        this.position = position;
        this.state = state;
        this.religion = religion;
        this.email = email;
        this.phone = phone;
        this.IG = IG;
        this.remark = remark;
    }

    protected excoitem(Parcel in) {
        btm = in.readParcelable(Bitmap.class.getClassLoader());
        Name = in.readString();
        dob = in.readString();
        level = in.readString();
        position = in.readString();
        state = in.readString();
        religion = in.readString();
        email = in.readString();
        phone = in.readString();
        IG = in.readString();
        remark = in.readString();
    }

    public static final Creator<excoitem> CREATOR = new Creator<excoitem>() {
        @Override
        public excoitem createFromParcel(Parcel in) {
            return new excoitem(in);
        }

        @Override
        public excoitem[] newArray(int size) {
            return new excoitem[size];
        }
    };

    public Bitmap getBtm() {
        return btm;
    }

    public String getName() {
        return Name;
    }

    public String getDob() {
        return dob;
    }

    public String getLevel() {
        return level;
    }

    public String getPosition() {
        return position;
    }

    public String getState() {
        return state;
    }

    public String getReligion() {
        return religion;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getIG() {
        return IG;
    }

    public String getRemark() {
        return remark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(btm, flags);
        dest.writeString(Name);
        dest.writeString(dob);
        dest.writeString(level);
        dest.writeString(position);
        dest.writeString(state);
        dest.writeString(religion);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(IG);
        dest.writeString(remark);
    }
}
