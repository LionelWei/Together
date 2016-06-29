package com.lionelwei.together.model.rest.core;

/*
 * FileName:	
 * Copyright:	炫彩互动网络科技有限公司
 * Author: 		weilai
 * Description:	封装retrofit
 * History:		2016/6/27 1.00 初始版本
 */

import java.io.IOException;
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
        if (headers != null) {
            OkHttpClient.Builder builder = mClient.newBuilder();
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Headers.Builder headerBuilder = new Headers.Builder();
                    for (Map.Entry<String, String> entry : headers.entrySet()) {
                        String value =  entry.getValue();
                        String key = entry.getKey();
                        headerBuilder.add(value, key);
                    }
                    Headers newHeaders = headerBuilder.build();

                    Request request = original.newBuilder()
                            .headers(newHeaders)
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });
            mClient = builder.build();
        }
    }

    public static class Builder {
        private OkHttpClient mOkHttpClient;
        private String mBaseUrl;
        private Map<String, String> mHeaders;

        public Builder httpClient(OkHttpClient okHttpClient) {
            this.mOkHttpClient = okHttpClient;
            return this;
        }

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
