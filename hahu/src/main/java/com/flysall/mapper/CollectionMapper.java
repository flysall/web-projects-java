package com.flysall.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.flysall.model.Collection;

public interface CollectionMapper {
	void insertCollection(Collection collection);

	List<Collection> listCreatingCollectionByUserId(@Param("userId") Integer userId);

	Collection selectCollectionByCollectionId(@Param("collectionId") Integer collectionId);

	Integer selectUserIdByCollectionId(@Param("collectionId") Integer collectionId);

	List<Collection> listCollectionByCollectionId(List<Integer> idList);
}