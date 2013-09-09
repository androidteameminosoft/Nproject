package com.prapul.nproject;

/**
 * For setting the Braking news objects
 * 
 * @author prudhvi reddy
 * 
 */

public class BreakingNews {

	String title;
	String addedDate;
	int ID;
	String imagePath;
	String description;

	/**
	 * for setting album name
	 * 
	 * @param String
	 *            name
	 * @author prudhvi reddy
	 */
	public void setTitle(String newstitle) {
		title = newstitle;
	}

	public String getTitle() {
		return title;
	}

	public void setAddedDate(String addDate) {
		addedDate = addDate;
	}

	public String getAddedDate() {
		return addedDate;
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

	public void setDescription(String desc) {
		description = desc;
	}

	public String getDescription() {
		return description;
	}

}
