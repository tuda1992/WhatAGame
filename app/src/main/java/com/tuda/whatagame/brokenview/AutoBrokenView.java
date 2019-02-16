package com.tuda.whatagame.brokenview;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Region;
import android.util.Log;
import android.view.View;

public class AutoBrokenView {

    private BrokenAnimator brokenAnim;
    private BrokenView brokenView;
    private BrokenConfig config;

    private AutoBrokenView(Builder builder) {
        brokenView = builder.brokenView;
        config = builder.config;
        brokenAnim= builder.brokenAnim;
    }

    public static class Builder {
        private BrokenAnimator brokenAnim;
        private BrokenConfig config;
        private BrokenView brokenView;
        public Builder(BrokenView brokenView) {
            this.brokenView = brokenView;
            config = new BrokenConfig();
        }

        public Builder setComplexity(int complexity) {
            if(complexity < 6)
                complexity = 6;
            else if(complexity > 20)
                complexity = 20;
            config.complexity = complexity;
            return this;
        }
        public Builder setBreakDuration(int breakDuration) {
            if(breakDuration < 200)
                breakDuration = 200;
            config.breakDuration = breakDuration;
            return this;
        }
        public Builder setFallDuration(int fallDuration) {
            if(fallDuration < 200)
                fallDuration = 200;
            config.fallDuration = fallDuration;
            return this;
        }
        public Builder setCircleRiftsRadius(int radius) {
            if(radius < 20 && radius != 0)
                radius = 20;
            config.circleRiftsRadius = radius;
            return this;
        }
        /**
         * Be sure the childView in region doesn't intercept any touch event,
         * you can make onTouch-event return false and set clickable to false.
         *
         * @param region The region where can enable break-effect.
         *
         * @return the BrokenTouchListener Builder.
         */
        public Builder setEnableArea(Region region) {
            config.region = region;
            config.childView = null;
            return this;
        }
        /**
         * Be sure the childView doesn't intercept any touch event,
         * you can make onTouch-event return false and set clickable to false.
         *
         * @param childView The view can enable break-effect
         *
         * @return the BrokenTouchListener Builder.
         */
        public Builder setEnableArea(View childView) {
            config.childView = childView;
            config.region = null;
            return this;
        }
        public Builder setPaint(Paint paint) {
            config.paint = paint;
            return this;
        }

        public Builder setViewPoint(View v,Point point) {
            if(config.region == null || config.region.contains(point.x,point.y)) {
                brokenAnim = brokenView.getAnimator(v);
                if (brokenAnim == null)
                    brokenAnim = brokenView.createAnimator(v, point, config);

            }
            return this;
        }

        public AutoBrokenView build() {
            return new AutoBrokenView(this);
        }
    }

    public void setAutoBrokenView() {
        brokenAnim.start();
    }

}
