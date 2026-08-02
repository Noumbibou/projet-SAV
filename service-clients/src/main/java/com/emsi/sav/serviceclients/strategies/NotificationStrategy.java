package com.emsi.sav.serviceclients.strategies;

public interface NotificationStrategy {
    void send(String destinataire, String sujet, String message);
}
