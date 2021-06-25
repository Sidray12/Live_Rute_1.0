package Modelo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface InterfaceAPI {

    @GET("positions")
    public Call<List<Positions>> getPositions(@Header("Authorization") String auth);

    @GET("devices")
    public Call<List<Devices>> getDevices(@Header("Authorization") String auth);

    @GET("drivers")
    public Call<List<Drivers>> getDrivers(@Header("Authorization") String auth);

}
