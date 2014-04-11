package org.jan.advertisementviewgroup;

import org.jan.advertisementviewgroup.widget.AdGallery;
import org.jan.advertisementviewgroup.widget.AdGallery.OnAdItemClickListener;
import org.jan.advertisementviewgroup.widget.AdGalleryHelper;
import org.jan.advertisementviewgroup.widget.AdGalleryHelper.OnGallerySwitchListener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnAdItemClickListener{
	//广告栏控件
	private AdGallery adGallery;
	//广告数据
	private Advertisement[] data;
	
	//方法测试2中用来显示广告栏的容器
	private RelativeLayout adContainer;
	//针对AdGallery的封装
	private AdGalleryHelper adGalleryHelper;
	//是否自动播放广告图
	private boolean isAutoSwitch = true;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		makeInitData();
//		realizeFunc1();
		realizeFunc2();
	}
	/**
	 * 测试：单独使用AdGallery的布局作为控件显示
	 */
	public void realizeFunc1(){
		adGallery = (AdGallery) findViewById(R.id.adGallery);
		//进行初始化参数
		adGallery.init(data, 2000, new OnGallerySwitchListener() {
			@Override
			public void onGallerySwitch(int position) {
				System.out.println("onGallerySwitch position=>"+position);
			}
		},isAutoSwitch);
		//设置广告栏的点击事件
		adGallery.setAdOnItemClickListener(this);
	}
	
	/**
	 * 测试：使用AdGlleryHelper来实现对外提供自定义的布局视图
	 */
	public void realizeFunc2(){
		adContainer = (RelativeLayout) findViewById(R.id.ad_container);
		adGalleryHelper = new AdGalleryHelper(this, data, 2000,isAutoSwitch);
		//获取对外的AdGallery布局
		adContainer.addView(adGalleryHelper.getLayout());
		adGallery = adGalleryHelper.getAdGallery();
		adGallery.setAdOnItemClickListener(this);
	}
	
	/**
	 * 构建临时的模拟广告数据
	 */
	private void makeInitData(){
		Advertisement ad1 = new Advertisement(
				"http://i.mmcdn.cn/simba/img/T1TF7_Fu4bXXb1upjX.jpg",
				"http://click.mz.simba.taobao.com/ecpm?spm=1.6659421.915625793.3.0TBMP7&e=nYH%2BKFPd133YWY1C04TxDF3S%2F837JUxFOG6WduKKDT84tYVPvHNQKfbMyjgDBdBNaaKi0tn9u53uXrSgpU6oEfPkBgURfKOLUK7RDtIZydUyXElRAMLwo5FiZpwDPce9eExSU4v7XFq7oPqgjtktJTct7HIHKFBICVQjKtUM3UNYgWSKv80kDNUEZrNp6EEl&u=http%3A%2F%2Fad.doubleclick.net%2Fddm%2Fclk%2F281149676%3B107971445%3Br%3Fhttp%3A%2F%2Fchevrolet.tmall.com%2F&k=193",
				"汽车广告");
		Advertisement ad2 = new Advertisement(
				"http://i.mmcdn.cn/simba/img/T10jM6FCdbXXb1upjX.jpg",
				"http://click.mz.simba.taobao.com/ecpm?spm=1.6659421.915625793.14.0TBMP7&e=YGNFxW6nTsHYWY1C04TxDF3S%2F837JUxFOG6WduKKDT%2BSLxQ3q7KAxgEayugzqMrcaaKi0tn9u51x8pPsvOqsRJtwz2O4yMfOUK7RDtIZydUyXElRAMLwo4z9EHH1hc4AoRsD%2BWoXRtk4%2FiEyiIj3wP3HD9f4XfaprJENpCr4W%2FoYUpKT%2FBi2RyHpjADIzGYa&u=http%3A%2F%2Fdetail.ju.taobao.com%2Fhome.htm%3Fitem_id%3D37484036990%26id%3D10000002347249&k=193",
				"聚划算推广");
		Advertisement ad3 = new Advertisement(
				"http://i.mmcdn.cn/simba/img/T164oLFEteXXb1upjX.jpg",
				"http://click.mz.simba.taobao.com/ecpm?spm=1.6659421.915625793.13.0TBMP7&e=oapCTUxRLObYWY1C04TxDF3S%2F837JUxFOG6WduKKDT%2FD3tdr%2BPviU7sn04msoiNGaaKi0tn9u523Z2ZPdYtM8Nw9MFujznjQUK7RDtIZydUyXElRAMLwo1AEKK8jEe5%2BspPvpwDPwef33gR6yLkawj%2FOzSD1g4ZitGue3yWVkVwVa8xYNBw1ixbWvZPrzTlR&u=http%3A%2F%2Fdetail.ju.taobao.com%2Fhome.htm%3Fitem_id%3D36374857410%26id%3D10000002426125&k=193",
				"除螨神器");
		Advertisement ad4 = new Advertisement(
				"http://i.mmcdn.cn/simba/img/T1CR.HFBNXXXartXjX.gif",
				"http://click.mz.simba.taobao.com/ecpm?spm=1.6659421.915625793.13.0TBMP7&e=oapCTUxRLObYWY1C04TxDF3S%2F837JUxFOG6WduKKDT%2FD3tdr%2BPviU7sn04msoiNGaaKi0tn9u523Z2ZPdYtM8Nw9MFujznjQUK7RDtIZydUyXElRAMLwo1AEKK8jEe5%2BspPvpwDPwef33gR6yLkawj%2FOzSD1g4ZitGue3yWVkVwVa8xYNBw1ixbWvZPrzTlR&u=http%3A%2F%2Fdetail.ju.taobao.com%2Fhome.htm%3Fitem_id%3D36374857410%26id%3D10000002426125&k=193",
				"春季新款");
		Advertisement ad5 = new Advertisement(
				"http://gtms04.alicdn.com/tps/i4/T1D.I0FpxeXXazuKP7-520-280.jpg",
				"http://click.mz.simba.taobao.com/ecpm?spm=1.6659421.915625793.13.0TBMP7&e=oapCTUxRLObYWY1C04TxDF3S%2F837JUxFOG6WduKKDT%2FD3tdr%2BPviU7sn04msoiNGaaKi0tn9u523Z2ZPdYtM8Nw9MFujznjQUK7RDtIZydUyXElRAMLwo1AEKK8jEe5%2BspPvpwDPwef33gR6yLkawj%2FOzSD1g4ZitGue3yWVkVwVa8xYNBw1ixbWvZPrzTlR&u=http%3A%2F%2Fdetail.ju.taobao.com%2Fhome.htm%3Fitem_id%3D36374857410%26id%3D10000002426125&k=193",
				"特色中国美食");
		data = new Advertisement[]{ad1,ad2,ad3,ad4,ad5};
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * 实现AdGallery的OnAdItemOnClickListener
	 */
	@Override
	public void setItemClick(int position) {
		System.out.println("you had clicked position="+position);
		//调用系统浏览器访问对应广告图的链接
		Intent intent= new Intent();        
	    intent.setAction("android.intent.action.VIEW");    
	    Uri content_url = Uri.parse(data[position].getLinkUrl());   
	    intent.setData(content_url);  
	    startActivity(intent);
	}
	@Override
	protected void onDestroy() {
		if(adGalleryHelper!=null){
			adGalleryHelper.stopAutoSwitch();
		}
		super.onDestroy();
	}
}
