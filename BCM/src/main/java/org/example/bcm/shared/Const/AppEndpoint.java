package org.example.bcm.shared.Const;

public class AppEndpoint {
    private static final String VERSION_1 = "/api/v1";
    private static final String VERSION_2 = "/api/v2";
    private static final String VERSION_3 = "/api/v3";

    private static final String VERSION = VERSION_1;

    public static final String AUTHENTICATION_ENDPOINT = VERSION + "/auth";
    public static final String CLUB_ENDPOINT = VERSION + "/club";
    public static final String SERVICE_ENDPOINT = VERSION + "/service";
}
