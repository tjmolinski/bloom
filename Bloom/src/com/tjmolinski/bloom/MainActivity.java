package com.tjmolinski.bloom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tjmolinski.bloom.util.Utils;

public class MainActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Utils.getLocalBlooms();
	}
	
	public void onNewBloomClick(View v) {
		startActivity(new Intent(MainActivity.this, BloomCreation.class));
	}
	
	public void onPreviewBloomClick(View v) {
		startActivity(new Intent(MainActivity.this, BloomList.class));
	}
	
	public void onSettingsClick(View v) {
		Toast.makeText(this, "No settings yet", Toast.LENGTH_LONG).show();
	}
}
