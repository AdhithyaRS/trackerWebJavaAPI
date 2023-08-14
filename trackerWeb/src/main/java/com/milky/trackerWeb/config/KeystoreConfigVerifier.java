//package com.milky.trackerWeb.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import jakarta.annotation.PostConstruct;
//
//@Component
//public class KeystoreConfigVerifier {
//
//    @Value("${https.ssl.key-store}")
//    private String keystorePath;
//
//    @PostConstruct
//    public void verifyKeystoreConfig() {
//        System.out.println("Keystore path configured: " + keystorePath);
//    }
//}
