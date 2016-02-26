package com.officehelper.service;

public class LoginService {

    private final String CLIENT_ID = "409139523626-a9dsapbdcl2275qoeih4r5q4em7a9573.apps.googleusercontent.com";
    private final String CLIENT_SECRET = "lcPTtpWTnhwuTce53Ft2Al4U";

    public void doIt(String authorizationCode) {

        /*
        try {
            // Upgrade the authorization code into an access and refresh token.
            GoogleTokenResponse tokenResponse;

            tokenResponse = new GoogleAuthorizationCodeTokenRequest(new NetHttpTransport(), new JacksonFactory(),
                    CLIENT_ID, CLIENT_SECRET, authorizationCode, "postmessage").execute();

            // You can read the Google user ID in the ID token.
            // This sample does not use the user ID.
            GoogleIdToken idToken = tokenResponse.parseIdToken();
            String gplusId = idToken.getPayload().getSubject();

        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        /*
        // Store the token in the session for later use.
        request.getSession().setAttribute("token", tokenResponse.toString());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(GSON.toJson("Successfully connected user."));
        */
    }
}
