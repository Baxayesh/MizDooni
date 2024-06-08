package ir.ut.ie.contracts;

import ir.ut.ie.utils.Token;

import java.util.Date;

public record TokenModel(
        String jwtToken,
        UserModel user,
        Date issueTime,
        Date expiration
) {
    public static TokenModel fromDomainObject(Token model) {

        return new TokenModel(
                "Bearer " + model.getJwtText(),
                UserModel.fromDomainObject(model.getOwner()),
                model.getIssueTime(),
                model.getExpiration()
        );
    }
}

