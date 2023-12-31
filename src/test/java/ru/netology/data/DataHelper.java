package ru.netology.data;

import lombok.Value;

import java.util.Random;


public class DataHelper {

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class TransferInfo {
        private String from;
        private String to;
    }

    @Value
    public static class CardInfo{
        String cardNumber;
        String dataTestId;
    }

    public static  CardInfo getFirstCard(){
        CardInfo cardInfo = new CardInfo("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
        return cardInfo;
    }

    public static  CardInfo getSecondCard(){
        CardInfo cardInfo = new CardInfo("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
        return cardInfo;
    }

    public static TransferInfo getTransferInfo() {
        return new TransferInfo(getFirstCard().cardNumber, getSecondCard().cardNumber);
    }

    public static int generateValidAmount(int cardBalance) {
        return (int) (Math.random() * cardBalance);
    }

    public static int generateInvalidAmount(int cardBalance) {
        return (int) (Math.random() * 99_999 + cardBalance);
    }
}
