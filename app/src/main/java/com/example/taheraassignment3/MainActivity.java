package com.example.taheraassignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.LongSparseArrayKt;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.taheraassignment3.databinding.ActivityMainBinding;
import com.example.taheraassignment3.model.CurrentWeather;
import com.example.taheraassignment3.model.Weather;
import com.example.taheraassignment3.network.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getCanonicalName();

    private ActivityMainBinding binding;
    private ArrayAdapter<String> adapter;

    private  Weather weather;

    private final String API_KEY = "6ad2b8d97c574d2aaaf170710220311";

    private ArrayList<Weather>  weatherList;



    private ImageView weatherImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());


        this.weatherImg = findViewById(R.id.weather_img);
        this.weatherImg.setImageResource(R.drawable.cloudy);

        this.binding.fetchWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Objects.nonNull(binding.editCityName) && StringUtils.isNotEmpty(binding.editCityName.getText().toString())) {
                    showLoadingDialog();
                    getWeatherInfo(binding.editCityName.getText().toString());
                    //dismissLoadingDialog();

                }
                else {
                    binding.editCityName.setError("This field can not be blank");
                }

            }
        });
    }

    private Weather getWeatherInfo(String city){
        Log.d(TAG, "getWeatherInfo: Getting weather info");

        //get the instance  of Call<CategoryContainer>
        Call<Weather> weatherCall = RetrofitClient.getInstance().getApi().retrieveWeatherInfo(API_KEY,city);

        //execute the Call
        try{
            weatherCall.enqueue(new Callback<Weather>() {
                @Override
                public void onResponse(Call<Weather> call, Response<Weather> response) {

//                    if (response.isSuccessful()){
                    if (response.code() == 200){

                        Weather apiResponse = response.body();


                        if (Objects.nonNull(apiResponse.getCurrent())) {
                            Log.e(TAG, "onResponse: Weather Data Received " + apiResponse.toString() + " objects received.");
                            weather = apiResponse;
                            CurrentWeather current = apiResponse.getCurrent();
                            binding.tempCValue.setText(current.getTemp_c()+"");
                            binding.feelslikeCValue.setText(current.getFeelslike_c()+"");
                            binding.windSpeedValue.setText(current.getWind_kph()+"");
                            binding.gustSpeedValue.setText(current.getGust_kph()+"");
                            binding.humidityValue.setText(current.getHumidity()+"");
                            binding.uvValue.setText(current.getUv()+"");
                            binding.visValue.setText(current.getVis_km()+"");
                            binding.conditionValue.setText(current.getCondition().text);
                            Picasso.get().load("https:"+current.getCondition().icon).into(weatherImg);
                            dismissLoadingDialog();
                            //adapter.notifyDataSetChanged(); //refresh the UI of spinner/RecyclerView once the data has changed
                        }else{
                            Log.e(TAG, "onResponse: Empty response received from server,Please check the structure of the data");
                        }
                    }else{
                        Log.e(TAG, "onResponse: Server responded with code " + response.code() );
                    }
                    call.cancel();
                }

                @Override
                public void onFailure(Call<Weather> call, Throwable t) {
                    call.cancel();
                    Log.e(TAG, "onFailure: Failed to get the data from API" + t.getLocalizedMessage() );
                }
            });

        }catch(Exception ex){
            Log.e(TAG, "getCategoryList: Unable to complete the categoryCall" + ex.getLocalizedMessage() );
        }
        return null;
    }
    ProgressDialog progress;
    public void showLoadingDialog() {

        if (progress == null) {
            progress = new ProgressDialog(this);
            progress.setTitle("Loading Weather Data");
            progress.setMessage("Please wait..data is loading");
        }
        progress.show();
    }

    public void dismissLoadingDialog() {

        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }}
