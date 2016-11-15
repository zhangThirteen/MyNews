package com.mobiletrain.my.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mobiletrain.my.R;
import com.mobiletrain.my.fragment.VideoFragment;
import com.mobiletrain.my.model.NewsVideoBean;
import com.mobiletrain.my.util.DateTimeUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/25 0025.
 */
public class VideoAdapter extends BaseAdapter {

    List<NewsVideoBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> data;
    Context context;
    private LayoutInflater inflater;
    public static MediaPlayer mediaPlayer;
    private Integer currentPlayingPosition = -1;
    private String TAG = "test";
    VideoFragment vf;


    public VideoAdapter(List data, Context context, VideoFragment videoFragment) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
        vf = videoFragment;

        mediaPlayer = new MediaPlayer();


    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_video, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = ((ViewHolder) convertView.getTag());
        }
        NewsVideoBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean bean = data.get(position);
        String text = bean.getText();
        String video_uri = bean.getVideo_uri();
        final String profile_image = bean.getProfile_image();


        holder.tvtext.setText(text.substring(2));
        holder.tvTitle.setText(text.substring(2));
        Glide.with(context).load(Uri.parse(profile_image))
                .override(20, 20)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivBlur);
        Glide.with(context).load(Uri.parse(profile_image)).into(holder.ivImg);

        Log.e(TAG, "getView:ssss " + position);

        holder.ivBlur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = holder.rlControl.getVisibility();
                if (visibility == View.GONE) {
                    holder.rlControl.setVisibility(View.VISIBLE);
                } else {
                    holder.rlControl.setVisibility(View.GONE);
                }
            }
        });

        holder.ivPlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    holder.ivPlayOrPause.setBackgroundResource(R.mipmap.play);
                } else {
                    if (currentPlayingPosition == -1) {
                        currentPlayingPosition = position;
                        notifyDataSetChanged();

                    }
                    mediaPlayer.start();
                    updateProgress(position, holder);
                    holder.ivPlayOrPause.setBackgroundResource(R.mipmap.pause);
                    int i = 0;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            holder.rlControl.setVisibility(View.GONE);


                        }
                    }, 3000);
                }
            }

        });

        TimeCont(position, holder);


        holder.sbPlay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int i = (int) ((float) mediaPlayer.getDuration() * seekBar.getProgress() / 100);
                mediaPlayer.seekTo(i);
//                Log.e(TAG, "onProgressChanged: "+progress+"///"+DateTimeUtil.durationMillisToString(i));
                holder.tvBeginTime.setText(DateTimeUtil.durationMillisToString(i));
            }
        });


        //播放谁就显示谁的surfaceView，否则显示封面
        if (position == currentPlayingPosition) {
            holder.ivImg.setVisibility(View.INVISIBLE);
            holder.svVideo.setVisibility(View.VISIBLE);//将surfaceView先隐藏后显示，能够清除surfaceView的图像残留
            playVideo(video_uri, holder, position);


            if (!mediaPlayer.isPlaying()) {
                holder.svVideo.setAlpha(0);
            }
        } else {
            holder.ivImg.setVisibility(View.VISIBLE);
            holder.svVideo.setVisibility(View.INVISIBLE);

        }

        //一旦播放当前播放条目离开屏幕，就停止播放，并将当前播放位置重新置为-1
        Integer formerPosition = (Integer) holder.svVideo.getTag();//前世
        if (formerPosition != null && position != currentPlayingPosition && formerPosition == currentPlayingPosition) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            currentPlayingPosition = -1;
        }
        holder.svVideo.setTag(position);

//        updateProgress(position,holder);

        vf.setFragmentStopListen(new VideoFragment.FragmentStopListen() {
            @Override
            public void fragmentStop(int i) {


            }
        });

        return convertView;
    }

    private void TimeCont(int position, ViewHolder holder) {
        if (currentPlayingPosition == position) {
            if (mediaPlayer.isPlaying()) {
                int duration = mediaPlayer.getDuration();
                int currentPosition = mediaPlayer.getCurrentPosition();
                holder.tvTime.setText(DateTimeUtil.durationMillisToString(duration));

                holder.tvBeginTime.setText(DateTimeUtil.durationMillisToString(mediaPlayer.getCurrentPosition()));

                int progress = (int) (currentPosition * 100 / (float) duration);
                holder.sbPlay.setProgress(progress);
                notifyDataSetChanged();
            }
        } else {
            holder.tvTime.setText("00:00");
            holder.tvBeginTime.setText("00:00");
        }
    }

    private void updateProgress(final int position, final ViewHolder holder) {
        if (currentPlayingPosition == position) {
            if (mediaPlayer.isPlaying()) {
                int duration = mediaPlayer.getDuration();
                int currentPosition = mediaPlayer.getCurrentPosition();
                holder.tvTime.setText(DateTimeUtil.durationMillisToString(duration));

                holder.tvBeginTime.setText(DateTimeUtil.durationMillisToString(mediaPlayer.getCurrentPosition()));

                int progress = (int) (currentPosition * 100 / (float) duration);
                holder.sbPlay.setProgress(progress);
            } else {
                holder.ivPlayOrPause.setBackgroundResource(R.mipmap.play);
            }
        } else {
            holder.tvTime.setText("00:00");
            holder.tvBeginTime.setText("00:00");
        }

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateProgress(position, holder);
                }
            }, 1000);
        }
    }


    private void playVideo(String videoUrl, final ViewHolder holder, final int position) {
        try {
            mediaPlayer.reset();//重置mediaPlayer
            mediaPlayer.setDataSource(context, Uri.parse(videoUrl));//设置数据源
            mediaPlayer.setDisplay(holder.svVideo.getHolder());//将画面输出到surfaceView
            mediaPlayer.prepareAsync();//准备播放
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();//异步回调：准备好之后开始播放视频
                    holder.svVideo.setAlpha(1);
                    updateProgress(position, holder);
//                    startProgressAutoRefresh(holder);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class ViewHolder {
        @BindView(R.id.ivBlur)
        ImageView ivBlur;
        @BindView(R.id.svVideo)
        SurfaceView svVideo;
        @BindView(R.id.ivImg)
        ImageView ivImg;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.ivPlayOrPause)
        ImageView ivPlayOrPause;
        @BindView(R.id.sbPlay)
        SeekBar sbPlay;
        @BindView(R.id.tvBeginTime)
        TextView tvBeginTime;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvFull)
        ImageView tvFull;
        @BindView(R.id.rlControl)
        RelativeLayout rlControl;
        @BindView(R.id.tvtext)
        TextView tvtext;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


    }


}
