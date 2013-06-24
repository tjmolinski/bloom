package com.tjmolinski.bloom;

import java.io.File;

import android.os.Parcel;
import android.os.Parcelable;

import com.tjmolinski.bloom.util.Utils;

public class Bloom implements Parcelable {
	
	private String mTitle;
	private String mDescription;
	private File mFolder;
	private int mReminderDays;
	
	public Bloom(String newTitle, String newDescription) {
		mTitle = newTitle;
		mDescription = newDescription;
		
		mFolder = Utils.createDirectory(mTitle);
	}
	
	public Bloom(String title, File directory) {
		mTitle = title;
		mFolder = directory;
	}
	
	public Bloom(Parcel p) {
		mTitle = p.readString();
		mDescription = p.readString();
		mFolder = (File) p.readSerializable();
		mReminderDays = p.readInt();
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public String getDescription() {
		return mDescription;
	}
	
	public File getFolderDestination() {
		return mFolder;
	}
	
	public int getImageCount() {
		return mFolder.list().length;
	}
	
	public File getPicture(int idx) {
		return new File(mFolder.getPath()+"/"+mFolder.list()[idx]);
	}
	
	public void setReminderDays(int days) {
		mReminderDays = days;
	}
	
	public int getReminderDays() {
		return mReminderDays;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mTitle);
		dest.writeString(mDescription);
		dest.writeSerializable(mFolder);
		dest.writeInt(mReminderDays);
	} 
	
	public static Creator<Bloom> CREATOR = new Creator<Bloom>() {
        public Bloom createFromParcel(Parcel parcel) {
            return new Bloom(parcel);
        }

        public Bloom[] newArray(int size) {
            return new Bloom[size];
        }
    };
}
