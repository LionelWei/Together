package com.lionelwei.together.model.rest.core;

/*
 * FileName:	
 * Copyright:	炫彩互动网络科技有限公司
 * Author: 		weilai
 * Description:	封装retrofit
 * History:		2016/6/27 1.00 初始版本
 */

import com.lionelwei.together.common.util.KeyUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

public class ServiceGenerator {
    private Retrofit.Builder mBuilder =
            new Retrofit.Builder()
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    private static OkHttpClient mClient = new OkHttpClient.Builder().build();
    private String mUrl;
    private Map<String, String> mCommonHeader;

    public <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = mBuilder
                            .baseUrl(mUrl)
                            .client(mClient)
                            .build();
        return retrofit.create(serviceClass);
    }

    private ServiceGenerator(String url, final Map<String, String> headers) {
        mUrl = url;
        initHttpClient(headers);
    }

    private void initHttpClient(final Map<String, String> headers) {
        OkHttpClient.Builder builder = mClient.newBuilder();
        builder = addHeader(builder, headers);
        mClient = builder.build();
    }

    private OkHttpClient.Builder addHeader(OkHttpClient.Builder builder,
                                           final Map<String, String> headers) {
        long curTime = System.currentTimeMillis();
        String nonce = KeyUtil.getNonce();
        String checkSum = KeyUtil.getCheckSum(nonce, curTime + "");
        mCommonHeader = new HashMap<>();
        mCommonHeader.put("AppKey", KeyUtil.getAppKey());
        mCommonHeader.put("Nonce", nonce);
        mCommonHeader.put("CurTime", curTime + "");
        mCommonHeader.put("CheckSum", checkSum);
        mCommonHeader.put("Content-Type", "application/x-www-form-urlencoded");

        return builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Headers.Builder headerBuilder = new Headers.Builder();
                for (Map.Entry<String, String> entry : mCommonHeader.entrySet()) {
                    String value =  entry.getValue();
                    String key = entry.getKey();
                    headerBuilder.set(key, value);
                }
                if (headers != null) {
                    for (Map.Entry<String, String> entry : headers.entrySet()) {
                        String value = entry.getValue();
                        String key = entry.getKey();
                        headerBuilder.set(key, value);
                    }
                }
                Headers newHeaders = headerBuilder.build();

                Request request = original.newBuilder()
                        .headers(newHeaders)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

    }

    public static class Builder {
        private String mBaseUrl;
        private Map<String, String> mHeaders;

        public Builder baseUrl(String url) {
            this.mBaseUrl = url;
            return this;
        }

        public Builder headers(Map<String, String> header) {
            mHeaders = header;
            return this;
        }

        public ServiceGenerator build() {
            return new ServiceGenerator(mBaseUrl, mHeaders);
        }
    }
}
