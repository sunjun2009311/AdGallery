package org.jan.advertisementviewgroup.widget;

import java.util.Timer;
import java.util.TimerTask;

import net.tsz.afinal.FinalBitmap;

import org.jan.advertisementviewgroup.Advertisement;
import org.jan.advertisementviewgroup.R;
import org.jan.advertisementviewgroup.widget.AdGalleryHelper.OnGallerySwitchListener;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * 无限滚动广告栏组件
 * 可以作为对外的布局引用
 * @author jan
 * @date 2014-04-10
 */
public class AdGallery extends Gallery implements
		android.widget.AdapterView.OnItemClickListener,
		android.widget.AdapterView.OnItemSelectedListener, OnTouchListener {

	public boolean isAutoSwitch = false; // 是否自动切换
	private Context mContext;
	private long mSwitchTime; // 图片切换时间

	private boolean runflag = false;
	private Timer mTimer; // 自动滚动的定时器

	private OnAdItemClickListener onItemClick;

	private OnGallerySwitchListener mGallerySwitchListener;

	private Advertisement[] mAds;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int position = getSelectedItemPosition();
			if (position >= (getCount() - 1)) {
				setSelection(getCount() / 2, true); // 跳转到第二张图片，在向左滑动一张就到了第一张图片
				onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
			} else {
				onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
			}
		}
	};

	public AdGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		mTimer = new Timer();
	}

	public AdGallery(Context context) {
		super(context);
		this.mContext = context;
		mTimer = new Timer();
	}

	public AdGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		mTimer = new Timer();
	}

	class ViewHolder {
		ImageView imageview;
	}

	/**
	 * 对控件的初始化参数
	 * @param ads 广告组
	 * @param switchTime	自动切换间隔时间
	 * @param gallerySwitchListener	切换事件的监听接口
	 * @param isAutoSwitch	是否需要自动切换
	 */
	public void init(Advertisement[] ads, long switchTime,
			OnGallerySwitchListener gallerySwitchListener, boolean isAutoSwitch) {
		this.mSwitchTime = switchTime;
		this.mGallerySwitchListener = gallerySwitchListener;
		this.isAutoSwitch = isAutoSwitch;
		this.mAds = ads;
		setAdapter(new AdAdapter());

		this.setOnItemClickListener(this);
		this.setOnTouchListener(this);
		this.setOnItemSelectedListener(this);
		this.setSoundEffectsEnabled(false);

		// setSpacing(10); //不能包含spacing，否则会导致onKeyDown()失效!!!
		setSelection(getCount() / 2); // 默认选中中间位置为起始位置
		setFocusableInTouchMode(true);
		setAutoSrcoll();
	}

	/**
	 * 设置点击事件的接口
	 * 
	 * @param onItemClick
	 */
	public void setAdOnItemClickListener(OnAdItemClickListener onItemClick) {
		this.onItemClick = onItemClick;
	}
	
	/**
	 * 初始化时判断是否需要设置自动切换广告图
	 */
	private void setAutoSrcoll() {
		if (isAutoSwitch) {
			setRunFlag(true);
			startAutoScroll();
		}
	}

	class AdAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mAds.length * (Integer.MAX_VALUE / mAds.length);
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.adgallery_image, null);
				Gallery.LayoutParams params = new Gallery.LayoutParams(
						Gallery.LayoutParams.FILL_PARENT,
						Gallery.LayoutParams.WRAP_CONTENT);
				convertView.setLayoutParams(params);

				viewHolder = new ViewHolder();
				viewHolder.imageview = (ImageView) convertView
						.findViewById(R.id.gallery_image);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			//使用FinalBitmap显示图片
			FinalBitmap.create(mContext).display(viewHolder.imageview,
					mAds[position % mAds.length].getImageUrl(),
					viewHolder.imageview.getWidth(),
					viewHolder.imageview.getHeight(), null, null);
			return convertView;
		}
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		int kEvent;
		if (isScrollingLeft(e1, e2)) { // 检查是否往左滑动
			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
		} else { // 检查是否往右滑动
			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
		}

		onKeyDown(kEvent, null);
		return true;

	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > (e1.getX() + 50);
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

	/**
	 * 开始自动滚动
	 */
	public void startAutoScroll() {
		mTimer.schedule(new TimerTask() {

			public void run() {
				if (runflag) {
					Message msg = new Message();
					if (getSelectedItemPosition() < (getCount() - 1)) {
						msg.what = getSelectedItemPosition() + 1;
					} else {
						msg.what = 0;
					}
					handler.sendMessage(msg);
				}
			}
		}, mSwitchTime, mSwitchTime);

	}

	public void setRunFlag(boolean flag) {
		runflag = flag;
	}

	public boolean isAutoScrolling() {
		if (mTimer == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (MotionEvent.ACTION_UP == event.getAction()
				|| MotionEvent.ACTION_CANCEL == event.getAction()) {
			// 重置自动滚动任务
			setRunFlag(true);
		} else {
			// 停止自动滚动任务
			setRunFlag(false);
		}
		return false;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		mGallerySwitchListener.onGallerySwitch(position % (mAds.length));
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		if (onItemClick != null) {
			onItemClick.setItemClick(position % mAds.length);
		} else {
			Log.v("AdGallery", "hasn't set up 'OnAdItemClickListener'");
		}
	}

	/**
	 * 　 * 当点击了AdGallery的图片时触发并回调接口中的方法 　 * @author jan
	 */
	public interface OnAdItemClickListener {
		public void setItemClick(int position);
	}
}
