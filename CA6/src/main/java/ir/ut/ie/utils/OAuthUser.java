package ir.ut.ie.utils;

public record OAuthUser (
     String family_name,
     String name,
     String picture,
     String locale,
     String email,
     String given_name,
     String id,
     boolean verified_email
) {


}

