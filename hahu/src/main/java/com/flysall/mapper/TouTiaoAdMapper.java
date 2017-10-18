package com.flysall.mapper;

import com.flysall.model.TouTiaoAdClickRecord;

public interface TouTiaoAdMapper {

	int selectCountByIdfa(String idfa);
	
	int selectCountByImei(String imei);
	
	void insertClickRecord(TouTiaoAdClickRecord record);
}
