package sqlcomponentizer.tableobjects;

import sqlcomponentizer.dbserializer.DBColumn;
import sqlcomponentizer.dbserializer.DBSerializable;
import sqlcomponentizer.tableobjects.helpers.AuthTokenGenerator;

@DBSerializable(tableName = "User_AuthToken")
public class User_AuthToken {
    @DBColumn(name = "user_id")
    private final Long userID;

    @DBColumn(name = "auth_token")
    private final String authToken;

    private User_AuthToken(long userID, String authToken) {
        this.userID = userID;
        this.authToken = authToken;
    }

    /***
     * This creates a new User_AuthToken with the userID and a generated AuthToken
     *
     * @param userID
     * @return User_AuthToken with generated AuthToken
     */
    public static User_AuthToken create(long userID) {
        return create(userID, AuthTokenGenerator.generateAuthToken());
    }

    public static User_AuthToken create(long userID, String authToken) {
        return new User_AuthToken(userID, authToken);
    }

    public Long getUserID() {
        return userID;
    }


    public String getAuthToken() {
        return authToken;
    }

}
