package com.adc.gads2020leaderboard.main.skill;

import java.util.Locale;

public class SkillIQLeadersModel {
   //"name":"Perry Oluwatobi","score":229,"country":"Nigeria",
   // "badgeUrl":"https://res.cloudinary.com/mikeattara/image/upload/v1596700835/skill-IQ-trimmed.png"
        private String mName;
        private int mScore;
        private String mCountry;
        private String mBadgeUrl;

        public SkillIQLeadersModel(String name, int score, String country, String badgeUrl)
        {
            mName = name;
            mScore = score;
            mCountry = country;
            mBadgeUrl = badgeUrl;
        }

        public String getName() {
            return mName;
        }

        public String getHoursCountry() {
            Locale locale = Locale.ENGLISH;
            return String.format(locale,"%d skill IQ score, %s", mScore, mCountry);
        }

        public String getBadgeUrl(){
            return mBadgeUrl;
        }
}
