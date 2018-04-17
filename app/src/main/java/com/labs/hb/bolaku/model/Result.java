package com.labs.hb.bolaku.model;

/**
 * Created by BwX on 4/4/2018.
 */

public class Result {


        private String goalHomeTeam;
        private String gaolAwayTeam;
        public Result(){}
        public Result(String goalHomeTeam, String gaolAwayTeam) {
            this.goalHomeTeam = goalHomeTeam;
            this.gaolAwayTeam = gaolAwayTeam;
        }

        public String getGoalHomeTeam() {
            return goalHomeTeam;
        }

        public void setGoalHomeTeam(String goalHomeTeam) {
            this.goalHomeTeam = goalHomeTeam;
        }

        public String getGaolAwayTeam() {
            return gaolAwayTeam;
        }

        public void setGaolAwayTeam(String gaolAwayTeam) {
            this.gaolAwayTeam = gaolAwayTeam;
        }

}
