package com.adc.gads2020leaderboard.main.learning;

import java.util.Locale;

public class LearningLeadersModel {
    //{"name":"Kojo Yeboah","hours":96,"country":"Ghana",
    // "badgeUrl":"https://res.cloudinary.com/mikeattara/image/upload/v1596700848/Top-learner.png"
        private String mName;
        private int mHours;
        private String mCountry;
        private String mBadgeUrl;

        public LearningLeadersModel(String name, int hours, String country, String badgeUrl)
        {
            mName = name;
            mHours = hours;
            mCountry = country;
            mBadgeUrl = badgeUrl;
        }

        public String getName() {
            return mName;
        }

        public String getHoursCountry() {
            Locale locale = Locale.ENGLISH;
            return String.format(locale,"%d learning hours, %s.", mHours, mCountry);
        }

        public String getBadgeUrl(){
            return mBadgeUrl;
        }
}
