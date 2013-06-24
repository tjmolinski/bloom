package com.tjmolinski.bloom;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.tjmolinski.bloom.util.Utils;

public class BloomPreview extends BaseActivity {

	AnimationDrawable animation;
	private Bloom mBloom;
	private final int DEFAULT_FRAME_DURATION = 600;
	private final int MAX_FRAME_DURATION = 1000;
	private ImageView bloomPreview;
	private SeekBar fpsBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bloom_preview);

		mBloom = getIntent().getExtras().getParcelable("BLOOM");

		animation = new AnimationDrawable();
		bloomPreview = (ImageView) findViewById(R.id.bloom_preview);
		fpsBar = (SeekBar) findViewById(R.id.fps_bar);
		fpsBar.setMax(MAX_FRAME_DURATION);
		fpsBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				startPreview();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				stopPreview();
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				animation = Utils.resampleAnimation(animation, MAX_FRAME_DURATION-progress);
			}
		});
		fpsBar.setProgress(MAX_FRAME_DURATION-DEFAULT_FRAME_DURATION);

		File[] root = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES + "/" + mBloom.getTitle())
				.listFiles();

		// Have to traverse backwards due to timestamping
		// making the newest picture the first picture to
		// be animated
		for (int i = root.length - 1; i >= 0; i--) {
			Bitmap bMap = BitmapFactory.decodeFile(root[i].getAbsolutePath());
			BitmapDrawable bmp = new BitmapDrawable(getResources(), bMap);
			bMap = Utils.flip(bmp).getBitmap();
			animation.addFrame(new BitmapDrawable(getResources(), bMap), DEFAULT_FRAME_DURATION);
		}

		animation.setOneShot(false);

		startPreview();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(BloomPreview.this, BloomCamera.class);
		intent.putExtra("BLOOM", mBloom);
		startActivity(intent);
	}
	
	@SuppressWarnings("deprecation")
	private void startPreview() {// run the start() method later on the UI thread
		bloomPreview.setBackgroundDrawable(animation);
		bloomPreview.post(new Runnable() {
			@Override
			public void run() {
				animation.start();
			}
		});
	}
	
	private void stopPreview() {// run the start() method later on the UI thread
		bloomPreview.post(new Runnable() {
			@Override
			public void run() {
				animation.stop();
			}
		});
	}
}
