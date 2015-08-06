package kr.co.composer.callrecord.page;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import kr.co.composer.callrecord.R;
import kr.co.composer.callrecord.bo.Call;
import kr.co.composer.callrecord.callhistory.HistoryAdapter;
import kr.co.composer.callrecord.dao.record.CallDAO;
import kr.co.composer.callrecord.recorder.AudioRecorder;
import kr.co.composer.callrecord.sharedpref.ConfigPreferenceManager;
import kr.co.composer.callrecord.test.MediaPlayer;

public class HistoryFragment extends Fragment {

    CallDAO callDAO;

    ConfigPreferenceManager configPreferenceManager;

    AudioRecorder audioRecorder;

    ListView listView;

    ArrayList<Call> callList;

    HistoryAdapter historyAdapter;

    SparseBooleanArray selected;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public HistoryFragment() {
    }

    public static HistoryFragment newInstance(int sectionNumber, Context context) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callDAO = new CallDAO(getActivity());
        configPreferenceManager = ConfigPreferenceManager.getInstance();
        audioRecorder = new AudioRecorder();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        init();
        View rootView = inflater.inflate(R.layout.activity_call_history, container, false);

//		액션바 타이틀변경
//            ActionBar ab = getActivity().getActionBar();
//            ab.setTitle("녹음목록");
//		액션바 백버튼 생성
//            ab.setDisplayHomeAsUpEnabled(true);

        listView = (ListView) rootView.findViewById(R.id.call_history_listview);

        Cursor cursor = callDAO.select();
        callList = callDAO.cursorCallList(cursor);
        historyAdapter = new HistoryAdapter(getActivity(), R.layout.call_history_item, callList);

        listView.setAdapter(historyAdapter);
        listView.setDivider(null);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long positionL) {
                File fileName = callDAO.selectFile(view.getId());


//                Intent audioStartIntent = new Intent();
//                audioStartIntent.setAction(Intent.ACTION_VIEW);
//                audioStartIntent.setDataAndType(Uri.fromFile(fileName), "audio/*");
//                getActivity().startActivity(audioStartIntent);

                Intent audioStartIntent = new Intent(getActivity(), MediaPlayer.class);
                audioStartIntent.putExtra("fileName",fileName.toString());
                startActivity(audioStartIntent);


            }

        });


        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                // Capture total checked items
                final int checkedCount = listView.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapter Class
//				Log.i("포지션확인",String.valueOf(position));
//				Log.i("아이템확인",String.valueOf(id));
                historyAdapter.toggleSelection(position);
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

//                                remove(selecteditem);
                                historyAdapter.remove(selecteditem);
                            }
                        }
                                getFragmentManager().beginTransaction().replace(R.id.container,new HistoryFragment()).commit();
                        // Close CAB
                        mode.finish();
                        return true;
                    case R.id.allAction:
                        historyAdapter.toggleAll(listView);
                        Toast.makeText(getActivity(), "전체선택", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }



            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.history_action, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
                historyAdapter.removeSelection();
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }
        });

        return rootView;
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        init();
    }

    public void remove(Call call) {
        File fileName = callDAO.selectFile(call.getRowId());
        callDAO.deleteCallV2(call.getRowId());
        if (fileName.exists()) {
            fileName.delete();
        }


        historyAdapter = new HistoryAdapter(getActivity(), R.layout.call_history_item, callList);
        historyAdapter.notifyDataSetChanged();
        listView.setAdapter(historyAdapter);

//        MainActivity main = new MainActivity();
//        main.refresh();
//        historyAdapter.notifyDataSetChanged();


    }




//    private void init() {
//        callDAO = new CallDAO(getActivity());
//        configPreferenceManager = ConfigPreferenceManager.getInstance();
//        audioRecorder = new AudioRecorder();
//
//        listView = (ListView) getActivity().findViewById(R.id.call_history_listview);
//
//        Cursor cursor = callDAO.select();
//        callList = callDAO.cursorCallList(cursor);
//        historyAdapter = new HistoryAdapter(getActivity(), R.layout.call_history_item, callList);
//
//        listView.setAdapter(historyAdapter);
//        listView.setDivider(null);
//        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
//        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
//
//            @Override
//            public void onItemCheckedStateChanged(ActionMode mode, int position,
//                                                  long id, boolean checked) {
//                // Capture total checked items
//                final int checkedCount = listView.getCheckedItemCount();
//                // Set the CAB title according to total checked items
//                mode.setTitle(checkedCount + " Selected");
//                // Calls toggleSelection method from ListViewAdapter Class
////				Log.i("포지션확인",String.valueOf(position));
////				Log.i("아이템확인",String.valueOf(id));
//                historyAdapter.toggleSelection(position);
//            }
//
//            @Override
//            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.deleteAction:
//                        // Calls getSelectedIds method from ListViewAdapter Class
//                        selected = historyAdapter
//                                .getSelectedIds();
//                        // Captures all selected ids with a loop
//                        for (int i = (selected.size() - 1); i >= 0; i--) {
//                            if (selected.valueAt(i)) {
//                                Call selecteditem = historyAdapter
//                                        .getItem(selected.keyAt(i));
//                                // Remove selected items following the ids
////                                historyAdapter.remove(selecteditem);
//                            }
//                        }
//                        // Close CAB
//                        mode.finish();
//                        return true;
//                    case R.id.allAction:
//                        historyAdapter.toggleAll(listView);
//                        Toast.makeText(getActivity(), "전체선택", Toast.LENGTH_SHORT).show();
//                        return true;
//                    default:
//                        return false;
//                }
//            }
//
//
//            @Override
//            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//                mode.getMenuInflater().inflate(R.menu.history_action, menu);
//                return true;
//            }
//
//            @Override
//            public void onDestroyActionMode(ActionMode mode) {
//                // TODO Auto-generated method stub
//                historyAdapter.removeSelection();
//            }
//
//            @Override
//            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//                // TODO Auto-generated method stub
//                return false;
//            }
//        });
//    }

}
