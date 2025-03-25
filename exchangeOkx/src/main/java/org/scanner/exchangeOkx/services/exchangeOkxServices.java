package org.scanner.exchangeOkx.services;

import okhttp3.*;
import org.scanner.exchangeOkx.component.OkxClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

@Service
public class exchangeOkxServices {

    @Autowired
    OkxClient okxClient;

    public void runOkx() {
//        var client = new OkHttpClient().newBuilder().build();
//        Request request = new Request.Builder()
//                .url("https://www.okx.com/api/v5/market/index-tickers?instId=BTC-USDT")
//                .build();
//        try {
//            Response response = client.newCall(request).execute();
//            System.out.println("==============response==============");
//            String answer = response.body().string();
//            System.out.println(answer);
////            log.error(answer);
////            log.warn(answer);
////            log.info(answer);
////            log.debug(answer);
////            log.trace(answer);
//            System.out.println("==============response==============");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void start() throws URISyntaxException {
        okxClient.market();
    }
}
