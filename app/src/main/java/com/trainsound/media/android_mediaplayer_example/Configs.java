package com.trainsound.media.android_mediaplayer_example;

public class Configs {
    /*
    * video config
    */
    //localfileplay, network
    static final String default_videoplay_mode = "network"; //file play
    //rtsp://184.72.239.149/vod/mp4:BigBuckBunny_175k.mov
    //network play
    static final String video_url = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_175k.mov";
    //local file play
    static final int video_local_file = R.raw.park_boo_video;

    /*
     * audio config
     */
    //localfileplay, network
    static final String default_audioplay_mode = "localfileplay"; //file play
    static final String audio_url = "http://www.all-birds.com/Sound/western%20bluebird.wav";
    static final int audio_local_file = R.raw.park_boo;
}
