/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.wk.libs.listview;

import java.util.Arrays;
import java.util.LinkedList;

import com.playcar.R;
import com.wk.libs.listview.PullToRefreshBase.Mode;
import com.wk.libs.listview.PullToRefreshBase.OnLastItemVisibleListener;
import com.wk.libs.listview.PullToRefreshBase.OnRefreshListener;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public final class PullToRefreshListActivity extends ListActivity {

	private LinkedList<String> mListItems;
	private PullToRefreshListView mPullRefreshListView;
	private ArrayAdapter<String> mAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wk_ptr_act_demo);

		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.wk_pull_refresh_list);

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = "";
						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						new GetDataTask().execute();
					}
				});

		// Add an end-of-list listener
		mPullRefreshListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						Toast.makeText(PullToRefreshListActivity.this,
								"End of List!", Toast.LENGTH_SHORT).show();
					}
				});

		ListView actualListView = mPullRefreshListView.getRefreshableView();

		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(actualListView);

		mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(mStrings));

		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mListItems);

		/**
		 * Add Sound Event Listener
		 */
		// SoundPullEventListener<ListView> soundListener = new
		// SoundPullEventListener<ListView>(
		// this);
		// soundListener.addSoundEvent(State.PULL_TO_REFRESH,
		// R.raw.wk_ptr_pull_event);
		// soundListener.addSoundEvent(State.RESET, R.raw.wk_ptr_reset_sound);
		// soundListener.addSoundEvent(State.REFRESHING,
		// R.raw.wk_ptr_refreshing_sound);
		// mPullRefreshListView.setOnPullEventListener(soundListener);

		// You can also just use setListAdapter(mAdapter) or
		// mPullRefreshListView.setAdapter(mAdapter)
		actualListView.setAdapter(mAdapter);

		mPullRefreshListView.setMode(Mode.BOTH);
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			mListItems.addFirst("Added after refresh...");
			mAdapter.notifyDataSetChanged();

			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}

	private String[] mStrings = { "Abbaye de Belloc",
			"Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu",
			"Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler",
			"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam",
			"Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis",
			"Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler" };
}
