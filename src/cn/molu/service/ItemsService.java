package cn.molu.service;

import java.util.List;

import cn.molu.pojo.Items;

public interface ItemsService{
	public List<Items> list() throws Exception;

	public Items findItemsById(Integer id)throws Exception;
	
	public void updateItems(Items items)throws Exception;
}
