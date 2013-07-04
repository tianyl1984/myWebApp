package com.hzth.myapp.core.model;

public class KV<K, V> {

	private K k;

	private V v;

	public KV(K k, V v) {
		this.k = k;
		this.v = v;
	}

	public K getK() {
		return k;
	}

	public void setK(K k) {
		this.k = k;
	}

	public V getV() {
		return v;
	}

	public void setV(V v) {
		this.v = v;
	}

}
