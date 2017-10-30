package com.example.administrator.myvideoplayer.View;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.VideoView;

/**
 * Created by Administrator on 2017/10/26 0026.
 */

public class MyVideoView extends VideoView{
    int defaulWidth=1920;
    int defaulHeight=1080;
    private int mVideoWidth=1920;
    private int mVideoHeight=1080;
    private MediaPlayer mMediaPlayer = null;
    private String mFile;

    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }





    @Override

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
//        getHolder().setFixedSize(1900, 800);

        int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
        int  height = getDefaultSize(mVideoHeight, heightMeasureSpec);
setMeasuredDimension(width,height);

//        File file = new File(Environment.getExternalStorageDirectory(), mFile);
//        MediaMetadataRetriever retr = new MediaMetadataRetriever();
//        retr.setDataSource(file.getPath());
//        String height1 = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT); // 视频高度
//        String width1= retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH); // 视频宽度
//
//        int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
//        int  height = getDefaultSize(mVideoHeight, heightMeasureSpec);
//
//        if(200<1080-mVideoHeight && 200<1920-mVideoWidth){
//         mVideoWidth=Integer.parseInt(width1)+500;
//         mVideoHeight=Integer.parseInt(height1)+500;
//             width = getDefaultSize(mVideoWidth, widthMeasureSpec);
//            height = getDefaultSize(mVideoHeight, heightMeasureSpec);
//
//         }else {
//            mVideoWidth=Integer.parseInt(width1);
//            mVideoHeight=Integer.parseInt(height1);
//            width = getDefaultSize(mVideoWidth, widthMeasureSpec);
//            height = getDefaultSize(mVideoHeight, heightMeasureSpec);
//        }
//
//        Log.e("GGG", String.valueOf(mVideoWidth)+" " +String.valueOf(mVideoHeight));
//
//
//
//        if (mVideoWidth > 0 && mVideoHeight > 0) {
//
//            int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
//            int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//            int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
//            int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
//
//            if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {
//                // the size is fixed
//               // width = widthSpecSize;
//               // height = heightSpecSize;
//                Log.e("III", "1" );
//
//                // for compatibility, we adjust size based on aspect ratio
//                if ( mVideoWidth * height  < width * mVideoHeight ) {
//                    //Log.i("@@@", "image too wide, correcting");
//                    width = (int) (height * mVideoWidth / mVideoHeight*1.5);
//                    Log.e("III", "2" );
//                } else if ( mVideoWidth * height  > width * mVideoHeight ) {
//                    //Log.i("@@@", "image too tall, correcting");
//                   // height = width * mVideoHeight / mVideoWidth;
//                    Log.e("III", "3" );
//                }
//            } else if (widthSpecMode == MeasureSpec.EXACTLY) {
//                // only the width is fixed, adjust the height to match aspect ratio if possible
//                width = widthSpecSize;
//                height = width * mVideoHeight / mVideoWidth;
//                Log.e("III", "4" );
//                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
//                    // couldn't match aspect ratio within the constraints
//                    height = heightSpecSize;
//                    Log.e("III", "5" );
//                }
//            } else if (heightSpecMode == MeasureSpec.EXACTLY) {
//                // only the height is fixed, adjust the width to match aspect ratio if possible
//                height = heightSpecSize;
//                width = height * mVideoWidth / mVideoHeight;
//                Log.e("III", "6" );
//                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
//                    // couldn't match aspect ratio within the constraints
//                    width = widthSpecSize;
//                    Log.e("III", "7" );
//                }
//            } else {
//                // neither the width nor the height are fixed, try to use actual video size
//                width = mVideoWidth;
//                height = mVideoHeight;
//                Log.e("III", "8" );
//                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
//                    // too tall, decrease both width and height
//                    height = heightSpecSize;
//                    width = height * mVideoWidth / mVideoHeight;
//                    Log.e("III", "9" );
//                }
//                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
//                    // too wide, decrease both width and height
//                    width = widthSpecSize;
//                    height = width * mVideoHeight / mVideoWidth;
//                    Log.e("III", "10" );
//                }
//            }
//        } else {
//            // no size yet, just adopt the given spec sizes
//        }
//        Log.e("CCC", "mdddddd" );
      //  setMeasuredDimension(width, height);












      //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//
//
//        File file = new File(Environment.getExternalStorageDirectory(), "ee.mp4");
//        MediaMetadataRetriever retr = new MediaMetadataRetriever();
//        retr.setDataSource(file.getPath());
//        String height1 = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT); // 视频高度
//        String width1= retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH); // 视频宽度
//        Log.e("CCC", width1+ height1);
//
//
//        int width=getDefaultSize(800,widthMeasureSpec);
//        int height=getDefaultSize(800,heightMeasureSpec);
//
//
//
//        setMeasuredDimension(width,height);

    }

    public void getVideoFile(String file) {
        mFile=file;

    }

    @Override
    public void setVideoPath(String path) {
        super.setVideoPath(path);
        getHolder().setFixedSize(1900, 800);

//        getHolder().setFixedSize(800, 800);
//        requestLayout();
        Log.e("EEE", "setVideoPath: " );
    }

    MediaPlayer.OnVideoSizeChangedListener mSizeChangedListener =
            new MediaPlayer.OnVideoSizeChangedListener() {
                public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                    mVideoWidth = mp.getVideoWidth();
                    mVideoHeight = mp.getVideoHeight();
                   // Log.e("DDD", String.valueOf(mVideoHeight)+String.valueOf(mVideoWidth));
                    if (mVideoWidth != 0 && mVideoHeight != 0) {
                        getHolder().setFixedSize(mVideoWidth, mVideoHeight);
                        requestLayout();
                    }
                }
            };

    MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
        public void onPrepared(MediaPlayer mp) {

            mVideoWidth = mp.getVideoWidth();
            mVideoHeight = mp.getVideoHeight();
            Log.e("QQQ", String.valueOf(22)+String.valueOf(22));
            if (mVideoWidth != 0 && mVideoHeight != 0) {
//                getHolder().setFixedSize(mVideoWidth, mVideoHeight);
//                requestLayout();
            }

        }
    };

}
