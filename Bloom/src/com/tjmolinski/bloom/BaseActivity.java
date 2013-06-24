package com.tjmolinski.bloom;

import android.content.Intent;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class BaseActivity extends SherlockActivity{
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	       MenuInflater inflater = getSupportMenuInflater();
	       inflater.inflate(R.menu.main, menu);
	       return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_add_bloom:
			startActivity(new Intent(this, BloomCreation.class));
		break;
		}
		return super.onOptionsItemSelected(item);
	}
}
