package com.prapul.youtube;

import android.content.Context;
import android.net.ConnectivityManager;

public class CheckNetConnection {

	public static boolean isNetAvilable(Context mContext) {
		ConnectivityManager connMgr = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()
				|| connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
						.isConnected()) {
			return true;
		}

		return false;
	}
	
	
	

}
