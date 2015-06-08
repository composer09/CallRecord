package kr.co.composer.callrecord.Listener;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ListView;
import android.widget.Toast;

import kr.co.composer.callrecord.R;
import kr.co.composer.callrecord.bo.Call;
import kr.co.composer.callrecord.callhistory.HistoryAdapter;

public class MultiChoiceListener implements MultiChoiceModeListener{
	private Context context;
	private ListView listView;
	private HistoryAdapter historyAdapter;
	private SparseBooleanArray selected;
	
	public MultiChoiceListener(Context context, ListView listView, HistoryAdapter historyAdapter){
		this.context = context;
		this.listView = listView;
		this.historyAdapter = historyAdapter;
	}
	
	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		mode.getMenuInflater().inflate(R.menu.history_action, menu);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.deleteAction:
			// Calls getSelectedIds method from ListViewAdapter Class
			selected = historyAdapter
					.getSelectedIds();
			// Captures all selected ids with a loop
			for (int i = (selected.size() - 1); i >= 0; i--) {
				if (selected.valueAt(i)) {
					Call selecteditem = historyAdapter
							.getItem(selected.keyAt(i));
					// Remove selected items following the ids
//					historyAdapter.remove(selecteditem);
				}
			}
			// Close CAB
			mode.finish();
			return true;
		case R.id.allAction:
			historyAdapter.toggleAll(listView);
			Toast.makeText(context , "전체선택", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return false;
		}
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		historyAdapter.removeSelection();
		
	}

	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position,
			long id, boolean checked) {
		// Capture total checked items
		final int checkedCount = listView.getCheckedItemCount();
		// Set the CAB title according to total checked items
		mode.setTitle(checkedCount + " Selected");
		// Calls toggleSelection method from ListViewAdapter Class
//		Log.i("포지션확인",String.valueOf(position));
//		Log.i("아이템확인",String.valueOf(id));
		historyAdapter.toggleSelection(position);
		
	}

}
