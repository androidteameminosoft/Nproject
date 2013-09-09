package com.prapul.nproject;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

public class LiveTV_Class extends Activity {

	VideoView livetv_videoView;
	ProgressBar video_progress_dialog;
	MediaController mediaController = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_livetv_fragment);

		video_progress_dialog = (ProgressBar) findViewById(R.id.video_progress_dialog);
		livetv_videoView = (VideoView) findViewById(R.id.videoview);

		String videoURl = "rtsp://cdn.m.yuppcdn.net/liveorigin/ntv2";

		Uri video = Uri.parse(videoURl);
		mediaController = new MediaController(this);
		mediaController.setAnchorView(livetv_videoView);
		livetv_videoView.setMediaController(mediaController);
		livetv_videoView.setVideoURI(video);

		livetv_videoView.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				Toast.makeText(LiveTV_Class.this, "Error occured", 500).show();
				return false;
			}
		});

		livetv_videoView.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				video_progress_dialog.setVisibility(View.GONE);
				livetv_videoView.start();
			}
		});
	}

	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	//
	// ViewGroup viewgroup = (ViewGroup) inflater.inflate(
	// R.layout.ui_livetv_fragment, null);
	//
	// livetv_videoView = (VideoView) viewgroup.findViewById(R.id.videoview);
	// video_progress_dialog = (ProgressBar) viewgroup
	// .findViewById(R.id.video_progress_dialog);
	//
	// String videoURl = "rtsp://cdn.m.yuppcdn.net/liveorigin/ntv2";
	//
	// Uri video = Uri.parse(videoURl);
	// mediaController = new MediaController(getActivity());
	// mediaController.setAnchorView(livetv_videoView);
	// livetv_videoView.setMediaController(mediaController);
	// livetv_videoView.setVideoURI(video);
	//
	// livetv_videoView.setOnErrorListener(new OnErrorListener() {
	//
	// @Override
	// public boolean onError(MediaPlayer mp, int what, int extra) {
	// Toast.makeText(getActivity(), "Error occured", 500).show();
	// return false;
	// }
	// });

	// livetv_videoView.setOnPreparedListener(new OnPreparedListener() {
	//
	// @Override
	// public void onPrepared(MediaPlayer mp) {
	// video_progress_dialog.setVisibility(View.GONE);
	// livetv_videoView.start();
	// }
	// });
	//
	// return viewgroup;
	// }

	@Override
	public void onDestroy() {
		try {
			livetv_videoView.stopPlayback();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}
}
