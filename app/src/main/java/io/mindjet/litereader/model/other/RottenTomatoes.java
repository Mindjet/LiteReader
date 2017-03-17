package io.mindjet.litereader.model.other;

/**
 * Created by Jet on 3/17/17.
 */

public class RottenTomatoes {

    private String tomatoMeter;     //烂番茄新鲜度
    private String audienceScore;   //爆米花指数

    public String getTomatoMeter() {
        return tomatoMeter;
    }

    public void setTomatoMeter(String tomatoMeter) {
        this.tomatoMeter = tomatoMeter;
    }

    public String getAudienceScore() {
        return audienceScore;
    }

    public void setAudienceScore(String audienceScore) {
        this.audienceScore = audienceScore;
    }
}
