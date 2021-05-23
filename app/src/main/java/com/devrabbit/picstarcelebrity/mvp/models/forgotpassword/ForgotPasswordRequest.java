package com.devrabbit.picstarcelebrity.mvp.models.forgotpassword;

public class ForgotPasswordRequest {

@com.squareup.moshi.Json(name = "client_id")
private String clientId;
@com.squareup.moshi.Json(name = "email")
private String email;
@com.squareup.moshi.Json(name = "connection")
private String connection;

public String getClientId() {
return clientId;
}

public void setClientId(String clientId) {
this.clientId = clientId;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getConnection() {
return connection;
}

public void setConnection(String connection) {
this.connection = connection;
}

}