package com.hzth.myapp.retrofit;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit 封装http调用，结果解析
 * 
 * http://square.github.io/retrofit/
 * 
 * @author tianyl
 * 
 */
public class RetrofitDemo {

	public static void main(String[] args) throws Exception {
		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://127.0.0.1:8093/diagnosis-base/").addConverterFactory(GsonConverterFactory.create()).build();
		IEclassService eclassService = retrofit.create(IEclassService.class);
		List<Eclass> eclasses = eclassService.query(4, 1).execute().body();
		for (Eclass eclass : eclasses) {
			System.out.println(eclass.getEclassId() + ":" + eclass.getEclassName());
		}
	}
}
