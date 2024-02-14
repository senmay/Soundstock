package com.soundstock.enums;

import java.util.HashMap;
import java.util.Map;

public class ApiEndpointsConstants {
    public static final String LAST_FM_GET_MOST_POPULAR_SONGS = "https://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&";
    public static final String COINGECKO_GET_COINLIST = "https://api.coingecko.com/api/v3//coins/list/";
    public static final String SPOTIFY_GET_TOP_TRACKS_FROM_2014 = "https://api.spotify.com/v1/playlists/37i9dQZF1DX0h0QnLkMBl4";
    public static final String SPOTIFY_GET_TOP_TRACKS_FROM_2015 ="https://api.spotify.com/v1/playlists/37i9dQZF1DX9ukdrXQLJGZ";
    public static final String SPOTIFY_GET_TOP_TRACKS_FROM_2016 ="https://api.spotify.com/v1/playlists/37i9dQZF1DX4dyzvuaRJ0n";
    public static final String SPOTIFY_GET_TOP_TRACKS_FROM_2017 ="https://api.spotify.com/v1/playlists/37i9dQZF1DX4dyzvuaRJ0n";
    public static final String SPOTIFY_GET_TOP_TRACKS_FROM_2018 ="https://api.spotify.com/v1/playlists/37i9dQZF1DX4dyzvuaRJ0n";
    public static final String SPOTIFY_GET_TOP_TRACKS_FROM_2019 ="https://api.spotify.com/v1/playlists/37i9dQZF1DX4dyzvuaRJ0n";
    public static final String SPOTIFY_GET_TOP_TRACKS_FROM_2020 ="https://api.spotify.com/v1/playlists/37i9dQZF1DX4dyzvuaRJ0n";
    public static final String SPOTIFY_GET_TOP_TRACKS_FROM_2021 ="https://api.spotify.com/v1/playlists/37i9dQZF1DX4dyzvuaRJ0n";
    public static final String SPOTIFY_GET_TOP_TRACKS_FROM_2022 ="https://api.spotify.com/v1/playlists/37i9dQZF1DX4dyzvuaRJ0n";
    public static final String SPOTIFY_GET_TOP_TRACKS_FROM_2023 ="https://api.spotify.com/v1/playlists/37i9dQZF1DX4dyzvuaRJ0n";
    public static final String SPOTIFY_GET_TOP_TRACKS_FROM_2024 ="https://api.spotify.com/v1/playlists/37i9dQZF1DX4dyzvuaRJ0n";
    public static final String SPOTIFY_GET_TOP_TRACKS_FROM_TODAY ="https://api.spotify.com/v1/playlists/37i9dQZF1DX4dyzvuaRJ0n/";
    public static final Map<Integer, String> YEAR_TO_SPOTIFY_URL = new HashMap<>();

    static {
        // Inicjalizacja mapy URL-i Spotify
        YEAR_TO_SPOTIFY_URL.put(2014, SPOTIFY_GET_TOP_TRACKS_FROM_2014);
        YEAR_TO_SPOTIFY_URL.put(2015, SPOTIFY_GET_TOP_TRACKS_FROM_2015);
        YEAR_TO_SPOTIFY_URL.put(2016, SPOTIFY_GET_TOP_TRACKS_FROM_2016);
        YEAR_TO_SPOTIFY_URL.put(2017, SPOTIFY_GET_TOP_TRACKS_FROM_2017);
        YEAR_TO_SPOTIFY_URL.put(2018, SPOTIFY_GET_TOP_TRACKS_FROM_2018);
        YEAR_TO_SPOTIFY_URL.put(2019, SPOTIFY_GET_TOP_TRACKS_FROM_2019);
        YEAR_TO_SPOTIFY_URL.put(2020, SPOTIFY_GET_TOP_TRACKS_FROM_2020);
        YEAR_TO_SPOTIFY_URL.put(2021, SPOTIFY_GET_TOP_TRACKS_FROM_2021);
        YEAR_TO_SPOTIFY_URL.put(2022, SPOTIFY_GET_TOP_TRACKS_FROM_2022);
        YEAR_TO_SPOTIFY_URL.put(2023, SPOTIFY_GET_TOP_TRACKS_FROM_2023);
        YEAR_TO_SPOTIFY_URL.put(2024, SPOTIFY_GET_TOP_TRACKS_FROM_2024);
        // Dla dzisiejszych utworów
        YEAR_TO_SPOTIFY_URL.put(0, SPOTIFY_GET_TOP_TRACKS_FROM_TODAY);
    }

    public static String getSpotifyUrlForYear(String year) {
        return YEAR_TO_SPOTIFY_URL.getOrDefault(year, null);
    }

}
