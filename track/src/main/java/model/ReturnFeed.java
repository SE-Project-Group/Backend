package model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * Created by thor on 2017/6/28.
 */

public class ReturnFeed implements Serializable {
    private String feed_id;
    private int owner_id;
    private String portrait_url;
    private String owner_name;
    private String text;
    private String date;
    private boolean liked;
    private int like_cnt;
    private int share_cnt;
    private int comment_cnt;
    private int pic_cnt;
    private String position;
    private double latitude;
    private double longitude;
    private List<String> picUrls;


    public String getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(String feed_id) {
        this.feed_id = feed_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getPortrait_url() {
        return portrait_url;
    }

    public void setPortrait_url(String portrait_url) {
        this.portrait_url = portrait_url;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
    public int getLike_cnt() {
        return like_cnt;
    }

    public void setLike_cnt(int like_cnt) {
        this.like_cnt = like_cnt;
    }

    public int getShare_cnt() {
        return share_cnt;
    }

    public void setShare_cnt(int share_cnt) {
        this.share_cnt = share_cnt;
    }

    public int getComment_cnt() {
        return comment_cnt;
    }

    public void setComment_cnt(int comment_cnt) {
        this.comment_cnt = comment_cnt;
    }

    public int getPic_cnt() {
        return pic_cnt;
    }

    public void setPic_cnt(int pic_cnt) {
        this.pic_cnt = pic_cnt;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<String> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(List<String> picUrls) {
        this.picUrls = picUrls;
    }

}