package com.example.administrator.chaoshen.bean;

import java.util.List;

public class M_FootBallBean {

    private List<SeasonsBean> seasons;
    private List<LeaguesBean> leagues;
    private List<PlayersBean> players;
    private List<MatchesBean> matches;
    private List<PointsBean> points;

    public List<SeasonsBean> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<SeasonsBean> seasons) {
        this.seasons = seasons;
    }

    public List<LeaguesBean> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<LeaguesBean> leagues) {
        this.leagues = leagues;
    }

    public List<PlayersBean> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayersBean> players) {
        this.players = players;
    }

    public List<MatchesBean> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchesBean> matches) {
        this.matches = matches;
    }

    public List<PointsBean> getPoints() {
        return points;
    }

    public void setPoints(List<PointsBean> points) {
        this.points = points;
    }


}
