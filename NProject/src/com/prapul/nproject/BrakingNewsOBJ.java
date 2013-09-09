package com.prapul.nproject;

import android.R.interpolator;

/**
 * For setting the Braking news objects
 * 
 * @author prudhvi reddy
 * 
 */

public class BrakingNewsOBJ {

	String albumName;
	int noOfImages;
	String addedDate;
	String category;
	String status;
	int ID;
	String imagePath;
	String name;
	String recordListingId;

	/**
	 * for setting album name
	 * 
	 * @param String
	 *            name
	 * @author prudhvi reddy
	 */
	public void setAlbumName(String name) {
		albumName = name;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setNoOfImages(int noImages) {
		noOfImages = noImages;
	}

	public int getNoOfImages() {
		return noOfImages;
	}

	public void setAddedDate(String addDate) {
		addedDate = addDate;
	}

	public String getAddedDate() {
		return addedDate;
	}

	public void setCategory(String addDate) {
		category = addDate;
	}

	public String getCategory() {
		return category;
	}

	public void setStatus(String newsstatus) {
		status = newsstatus;
	}

	public String getStatus() {
		return status;
	}

	public void setId(int newsId) {
		ID = newsId;
	}

	public int getId() {
		return ID;
	}

	public void setImagePath(String newsimagePath) {
		imagePath = newsimagePath;
	}

	public String getImagesPath() {
		return imagePath;
	}

	public void setName(String newsname) {
		name = newsname;
	}

	public String getName() {
		return name;
	}

	public void setrecordingListId(String nrecordingId) {
		recordListingId = nrecordingId;
	}

	public String getrecordingListId() {
		return recordListingId;
	}

}
