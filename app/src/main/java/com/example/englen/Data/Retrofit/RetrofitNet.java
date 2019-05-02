package com.example.englen.Data.Retrofit;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.security.cert.Extension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public class RetrofitNet {

    static class ResulrGSON {

        @SerializedName("code")
        @Expose
        private Integer code;
        @SerializedName("lang")
        @Expose
        private String lang;
        @SerializedName("text")
        @Expose
        private List<String> text = null;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public List<String> getText() {
            return text;
        }
    }

    interface Request {
        @FormUrlEncoded
        @POST("/api/v1.5/tr.json/translate?")
        Call<ResulrGSON> performPostCall(@FieldMap HashMap<String, String> postDataParams);
    }

    public static class RetrofitSend {
        private String answerHTTP;
        private String code = "ru-en";
        private final String server = "https://translate.yandex.net/";
        public boolean empty = false;

        private Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        private Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(server)
                .build();

        private Request req = retrofit.create(Request.class);


        public void send(final TextView textView, String text) {
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("key", "trnsl.1.1.20190501T045529Z.76074845cdf35218.7d2f204664624daf93665f7805fab6e521faafe8");
            postDataParams.put("text", text);
            postDataParams.put("lang", code);
            postDataParams.put("format", "plain");

            Call<ResulrGSON> call = req.performPostCall(postDataParams);

            call.enqueue(new Callback<ResulrGSON>() {
                @Override
                public void onResponse(Call<ResulrGSON> call, Response<ResulrGSON> response) {
                    if (response.errorBody() != null) {
                        textView.setText("");
                        empty = true;
                        return;
                    } else {
                        if (empty == false) {
                            ResulrGSON resulrGSON = response.body();
                            answerHTTP = resulrGSON.getText().get(0);
                            textView.setText(answerHTTP);
                        }
                        return;
                    }
                }

                @Override
                public void onFailure(Call<ResulrGSON> call, Throwable t) {
                    textView.setText("");
                    return;
                }
            });
            return;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
