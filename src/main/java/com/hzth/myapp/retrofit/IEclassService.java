package com.hzth.myapp.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IEclassService {

	@GET("eclass/query")
	Call<List<Eclass>> query(@Query("gradeId") Integer gradeId, @Query("schoolTermId") Integer schoolTermId);

}
