package org.jan.advertisementviewgroup;

/**
 * ���ʵ����
 * 
 * @author jan
 * 
 */
public class Advertisement {

	private String imageUrl; // ͼƬ��ַ
	private String linkUrl; // ���ӵ�ַ
	private String title; // ����
	
	public Advertisement(String imageUrl, String linkUrl, String title) {
		super();
		this.imageUrl = imageUrl;
		this.linkUrl = linkUrl;
		this.title = title;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}