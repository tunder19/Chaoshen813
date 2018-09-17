package com.example.administrator.chaoshen.bean;

import java.io.Serializable;

public class VersusHistoryBean  implements Serializable {



    private HostBean host;
    private GuestBean guest;
    private int matches;

    public HostBean getHost() {
        return host;
    }

    public void setHost(HostBean host) {
        this.host = host;
    }

    public GuestBean getGuest() {
        return guest;
    }

    public void setGuest(GuestBean guest) {
        this.guest = guest;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public static class HostBean  implements Serializable{


        private String even;
        private String lose;
        private AsHostBean asHost;
        private String win;

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

        public String getWin() {
            return win;
        }

        public void setWin(String win) {
            this.win = win;
        }

        public static class AsHostBean  implements Serializable{
            /**
             * even : 0
             * lose : 0
             * win : 0
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

    public static class GuestBean implements Serializable{
        /**
         * even : 1
         * lose : 1
         * win : 1
         * asGuest : {"even":0,"lose":0,"win":0}
         */

        private int even;
        private int lose;
        private int win;
        private AsGuestBean asGuest;

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

        public AsGuestBean getAsGuest() {
            return asGuest;
        }

        public void setAsGuest(AsGuestBean asGuest) {
            this.asGuest = asGuest;
        }

        public static class AsGuestBean implements Serializable{
            /**
             * even : 0
             * lose : 0
             * win : 0
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
