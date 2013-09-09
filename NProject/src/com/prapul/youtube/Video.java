package com.prapul.youtube;

public class Video {

	String id;
	String thubnail;
	String title;
	int position;
	int totalVideos;

	public void setId(String id) {
		this.id = id;
	}

	public void setThumbnail(String thumbnail) {
		this.thubnail = thumbnail;
	}

	public void setTitle(String tytle) {

		this.title = tytle;

	}

	public void setTotalVideos(int toatal) {
		totalVideos = toatal;
	}

	public int getToatalVideos() {
		return totalVideos;
	}

	public String getId() {
		return id;
	}

	public String getThumbnail() {

		return thubnail;
	}

	public String getTitle() {

		return title;
	}

	public void setPosition(int pos) {

		position = pos;
	}

	public int getPosition() {
		return position;
	}

}
