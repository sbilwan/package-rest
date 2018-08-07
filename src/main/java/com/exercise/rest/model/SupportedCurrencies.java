package com.exercise.rest.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class SupportedCurrencies {

    private static SupportedCurrencies supportedCurrencies = new SupportedCurrencies();

    private static final Object lock = new Object();

    private volatile Map<String, String> currencies;

    public static SupportedCurrencies getInstance() {
        return  supportedCurrencies;
    }

    public Map<String, String> getCurrencies() {
        return currencies;
    }

    public void loadCurrencies(Map<String, String> currenciesMap){
        synchronized (lock) {
            currencies.clear();
            currencies.putAll(currenciesMap);
        }
    }

    private SupportedCurrencies() {
        currencies = new ConcurrentHashMap<>();
    }

}
