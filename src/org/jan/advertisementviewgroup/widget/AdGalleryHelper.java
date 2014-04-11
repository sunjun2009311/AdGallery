package org.jan.advertisementviewgroup.widget;

import org.jan.advertisementviewgroup.Advertisement;
import org.jan.advertisementviewgroup.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

/**
 * ���Զ������AdGallery������һ�η�װ ������ͼƬ����͵�ǰλ��ָʾ��(RadioGroup)�Ĳ���
 */
public class AdGalleryHelper {

	private AdGallery mAdGallery; // ���޹���Gallery
	private TextView mPicTitle; // ���ͼƬ����
	private RadioGroup mRadioGroup; // ����������
	
	private Context mContext;
	private LayoutInflater mInflater;

	private RelativeLayout galleryLayout;
	
	public AdGalleryHelper(Context context, final Advertisement[] ads,
			long switchTime,boolean isAutoSwitch) {

		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		galleryLayout = (RelativeLayout) mInflater.inflate(
				R.layout.adgallery_hellper, null);
		mRadioGroup = (RadioGroup) galleryLayout
				.findViewById(R.id.home_pop_gallery_mark);

		// ����RadioButton
		Bitmap b = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.point_1);
		LayoutParams params = new LayoutParams(dpToPx(mContext, b.getWidth()),
				dpToPx(mContext, b.getHeight()));
		for (int i = 0; i < ads.length; i++) {
			RadioButton _rb = new RadioButton(mContext);
			_rb.setId(0x1234 + i);
			_rb.setButtonDrawable(mContext.getResources().getDrawable(
					R.drawable.gallery_selector));
			mRadioGroup.addView(_rb, params);
		}

		mPicTitle = (TextView) galleryLayout
				.findViewById(R.id.news_gallery_text);
		mAdGallery = (AdGallery) galleryLayout.findViewById(R.id.gallerypop);
		mAdGallery.isAutoSwitch=isAutoSwitch;
		mAdGallery.init(ads, switchTime, new OnGallerySwitchListener() {

			@Override
			public void onGallerySwitch(int position) {
				if (mRadioGroup != null) {
					mRadioGroup.check(mRadioGroup.getChildAt(position).getId());
				}
				if (mPicTitle != null) {
					mPicTitle.setText(ads[position].getTitle());
				}
			}
		}, mAdGallery.isAutoSwitch);
	}

	/**
	 * ���⿪�Ų��ֶ���ʹ�ÿ��Խ����ֶ������ӵ��ⲿ�Ĳ�����ȥ
	 * 
	 * @return
	 */
	public RelativeLayout getLayout() {
		return galleryLayout;
	}
	/**
	 * ��ȡ��ǰʹ�õ�AdGllery����
	 * @return
	 */
	public AdGallery getAdGallery(){
		return mAdGallery;
	}
	
	/**
	 * ��ʼ�Զ�ѭ���л�
	 */
	public void startAutoSwitch() {
		mAdGallery.setRunFlag(true);
		mAdGallery.startAutoScroll();
	}
	
	/**
	 * ֹͣ�Զ�ѭ���л�
	 */
	public void stopAutoSwitch() {
		mAdGallery.setRunFlag(true);
	}

	/**
	 * ͼƬ�л��ص��ӿ�
	 * 
	 */
	public interface OnGallerySwitchListener {
		public void onGallerySwitch(int position);
	}

	private int dpToPx(Context context, int dp) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	private int pxToDp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}