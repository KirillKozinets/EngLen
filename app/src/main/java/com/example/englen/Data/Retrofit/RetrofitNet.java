
//Класс для взаиможействия с результатом от яндекс api

package com.example.englen.Data.Retrofit;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        //Код ответа
        @SerializedName("code")
        @Expose
        private Integer code;
        //Язык ответа
        @SerializedName("lang")
        @Expose
        private String lang;
        // Текст ответа
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
        Context context;
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

        public RetrofitSend(Context context){
            this.context = context;
        }

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
                        int code = response.raw().code();
                        textView.setText("");
                        treatmentCode(code,textView);
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
                    textView.setText("Нет  доступа к интернету");
                    return;
                }
            });
            return;
        }

        public void setCode(String code) {
            this.code = code;
        }

        private void treatmentCode(int code,TextView textView) {
            String text = null;
            switch (code) {
                case 413://Превышен допустимый размер тектса
                    text = "Превышен допустимый размер текста";
                    break;
                case 422://Текст не может быть переведен
                    text = "Текст не может быть переведён";
                    break;
            }
            if(text != null)
            textView.setText(text);
        }
    }
}
