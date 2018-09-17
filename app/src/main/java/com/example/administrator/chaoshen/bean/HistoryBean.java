package com.example.administrator.chaoshen.bean;

import java.io.Serializable;

public class HistoryBean implements Serializable {


    /**
     * recent : {"even":1,"lose":1,"asHost":{"even":0,"lose":1,"win":2},"matches":10,"win":8,"asGuest":{"even":0,"lose":1,"win":2}}
     */

    private RecentBean recent;

    public RecentBean getRecent() {
        return recent;
    }

    public void setRecent(RecentBean recent) {
        this.recent = recent;
    }

    public static class RecentBean implements Serializable {
        /**
         * even : 1
         * lose : 1
         * asHost : {"even":0,"lose":1,"win":2}
         * matches : 10
         * win : 8
         * asGuest : {"even":0,"lose":1,"win":2}
         */

        private String even;
        private String lose;
        private AsHostBean asHost;
        private int matches;
        private String win;
        private AsGuestBean asGuest;

        public String getEven() {
            return even;
        }

        public void setEven(String even) {
            this.even = even;
        }

        public String getLose() {
            return lose;
        }

        public void setLose(String lose) {
            this.lose = lose;
        }

        public AsHostBean getAsHost() {
            return asHost;
        }

        public void setAsHost(AsHostBean asHost) {
            this.asHost = asHost;
        }

        public int getMatches() {
            return matches;
        }

        public void setMatches(int matches) {
            this.matches = matches;
        }

        public String getWin() {
            return win;
        }

        public void setWin(String win) {
            this.win = win;
        }

        public AsGuestBean getAsGuest() {
            return asGuest;
        }

        public void setAsGuest(AsGuestBean asGuest) {
            this.asGuest = asGuest;
        }

        public static class AsHostBean  implements Serializable{
            /**
             * even : 0
             * lose : 1
             * win : 2
             */

            private int even;
            private int lose;
            private int win;

            public int getEven() {
                return even;
            }

            public void setEven(int even) {
                this.even = even;
            }

            public int getLose() {
                return lose;
            }

            public void setLose(int lose) {
                this.lose = lose;
            }

            public int getWin() {
                return win;
            }

            public void setWin(int win) {
                this.win = win;
            }
        }

        public static class AsGuestBean  implements Serializable{
            /**
             * even : 0
             * lose : 1
             * win : 2
             */

            private int even;
            private int lose;
            private int win;

            public int getEven() {
                return even;
            }

            public void setEven(int even) {
                this.even = even;
            }

            public int getLose() {
                return lose;
            }

            public void setLose(int lose) {
                this.lose = lose;
            }

            public int getWin() {
                return win;
            }

            public void setWin(int win) {
                this.win = win;
            }
        }
    }
}
