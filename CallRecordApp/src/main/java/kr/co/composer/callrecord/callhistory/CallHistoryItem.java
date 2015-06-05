package kr.co.composer.callrecord.callhistory;

import kr.co.composer.callrecord.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class CallHistoryItem extends LinearLayout {
//	private int offIcon = R.drawable.health_history_icon_donated_noon_off;
//	private int onIcon = R.drawable.health_history_icon_noon_on;
	
	public CallHistoryItem(Context context) {
		super(context);
		initLayout(context);
	}
	
	public CallHistoryItem(Context context, AttributeSet attr) {
		super(context, attr);
		initLayout(context);
	}

	private void initLayout(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.call_history_item, this, true);
        
//        final ImageView callHistoryItem = (ImageView)findViewById(R.id.btn_call_history_item_link); 
//        
//        ((ImageButton)this.findViewById(R.id.btn_call_history_item_link)).setOnTouchListener(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if (event.getAction() == MotionEvent.ACTION_DOWN) {
//					callHistoryItem.setBackgroundColor(Color.parseColor("#FFFFFF"));
//				}
//				
////				if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
////					callHistoryItem.setBackgroundColor(Color.parseColor("#000000"));
////				}
//				
//				return false;
//			}
//		});
//        
//        Typeface boldFont = CustomFontManager.getInstance().getTypeFace(CustomFont.NANUN_GOTHIC_BOLD);
//
//        TextView callerNameList = (TextView) this.findViewById(R.id.caller_name_list);
//        callerNameList.setTypeface(boldFont);
//        
//        TextView phoneNumberList = (TextView) this.findViewById(R.id.phone_number_list);
//        phoneNumberList.setTypeface(boldFont);
//        
//        TextView startTime = (TextView) this.findViewById(R.id.start_time);
//        startTime.setTypeface(boldFont);
        
//        ImageView caller_picture_list = (ImageView) this.findViewById(R.id.caller_picture_list);
        
//        ImageView sendReceiveImageList = (ImageView) this.findViewById(R.id.send_receive_image_list);
        
//        TextView callTimeList = (TextView) this.findViewById(R.id.call_time_list);
//        callTimeList.setTypeface(boldFont);
        
        
	}
	
//	public void setName(String name) {
//		((TextView)this.findViewById(R.id.caller_name_list)).setText(name);
//	}
//	
//	public void setNumber(String number) {
//		((TextView)this.findViewById(R.id.phone_number_list)).setText(number);
//	}
//	
//	public void setStartTime(String startTime) {
//		((TextView)this.findViewById(R.id.start_time))
//		.setText(startTime);
//	}
//	
//	public void setCallTime(String callTime) {
//		((TextView)this.findViewById(R.id.call_time_list))
////		.setText(DateFormat.format(CallFormatter.START_DATE_FORMAT,startTime).toString());
//		.setText(callTime);
//	}
//	
//	public void setSending(String sending){
//		if("true".equals(sending)){
//			sending = "발신";
//		}else{
//			sending = "수신";
//		}
//		((TextView)this.findViewById(R.id.sending))
//		.setText(sending);
//	}
	
	
}
