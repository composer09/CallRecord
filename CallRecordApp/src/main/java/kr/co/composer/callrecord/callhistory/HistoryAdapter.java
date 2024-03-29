package kr.co.composer.callrecord.callhistory;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import kr.co.composer.callrecord.R;
import kr.co.composer.callrecord.bo.Call;
import kr.co.composer.callrecord.dao.record.CallDAO;
import kr.co.composer.callrecord.recorder.AudioRecorder;
import kr.co.composer.callrecord.sharedpref.ConfigPreferenceManager;

public class HistoryAdapter extends BaseAdapter {
    Call call;
    CallDAO callDAO;
    LayoutInflater inflater;
    Context context;
    ArrayList<Call> arraySrc;
    AudioRecorder audioRecorder;
    int layout;
    File fileName;
    ConfigPreferenceManager preferenceManager;

    private SparseBooleanArray mSelectedItemsIds;

    public HistoryAdapter(Context context, int layout, ArrayList<Call> arraySrc) {
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
        this.arraySrc = arraySrc;
        preferenceManager = ConfigPreferenceManager.getInstance();

        callDAO = new CallDAO(context);
        audioRecorder = new AudioRecorder();
        mSelectedItemsIds = new SparseBooleanArray();

        // boldFont = CustomFontManager.getInstance().getTypeFace(
        // CustomFont.NANUN_GOTHIC_BOLD);
    }

    @Override
    public int getCount() {
        return arraySrc.size();
    }

    @Override
    public Call getItem(int position) {
        return arraySrc.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void toggleAll(ListView listView) {
        for (int i = 0; i < getCount(); i++) {
            listView.setItemChecked(i, true);
            mSelectedItemsIds.put(i, true);
        }
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    // 애니메이션 효과 refresh
    public void remove(Call call) {
        fileName = callDAO.selectFile(call.getRowId());
        callDAO.deleteCallV2(call.getRowId());
        if (fileName.exists()) {
            fileName.delete();
        }
//		((Activity)this.context).finish();
//		Intent refresh = new Intent(this.context,
//				MainActivity.class);
//		refresh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		((Activity) this.context).overridePendingTransition(
//				R.anim.fade_history, R.anim.hold_history);
//		this.context.startActivity(refresh);
    }


    public View getView(int position, View convertView, ViewGroup parentView) {
        CallViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(layout, parentView, false);
            holder = new CallViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.caller_image);
            holder.name = (TextView) convertView.findViewById(R.id.caller_name_list);
            holder.time = (TextView) convertView.findViewById(R.id.call_time_list);
            holder.sending = (TextView) convertView.findViewById(R.id.sending);
            holder.number = (TextView) convertView.findViewById(R.id.phone_number_list);
            holder.startTime = (TextView) convertView.findViewById(R.id.start_time);
            convertView.setTag(holder);
        } else {
            holder = (CallViewHolder)convertView.getTag();
        }


        if (arraySrc.get(position).getPhotoId() != 1) {
            holder.imageView.setImageBitmap(getThumbnail(arraySrc.get(position).getPhotoId()));
        } else if (arraySrc.get(position).getPhotoId() == 1) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.contact_image);
            holder.imageView.setImageBitmap(getCircleBitmap(bitmap));
        }

        holder.name.setText(arraySrc.get(position).getCallerName());
        holder.time.setText(arraySrc.get(position).getCallTime());

        String sendReceive = arraySrc.get(position).getsendReceive();
        if (sendReceive.equals("true")) {
            sendReceive = "발신";
        } else {
            sendReceive = "수신";
        }
        holder.sending.setText(sendReceive);
        holder.number.setText(arraySrc.get(position).getPhoneNumber());
        holder.startTime.setText(arraySrc.get(position).getStartTime());

        convertView.setId(arraySrc.get(position).getRowId());

        return convertView;
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int size = (bitmap.getWidth() / 2);
        canvas.drawCircle(size, size, size, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    private Bitmap getThumbnail(int thumbnailId) {
        Uri uri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, thumbnailId);
        InputStream input = null;
        try {
            input = context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (input == null) {
            return null;
        }
        return getCircleBitmap(BitmapFactory.decodeStream(input));
    }


}
