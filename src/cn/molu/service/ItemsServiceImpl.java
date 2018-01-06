package cn.molu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.molu.dao.ItemsMapper;
import cn.molu.pojo.Items;
import cn.molu.pojo.ItemsExample;
@Service
public class ItemsServiceImpl implements ItemsService {
	@Autowired
	private ItemsMapper itemsMapper;

	public List<Items> list(){
		// 查询条件，如果不需要任何查询条件，直接将example  new出来即可。
		ItemsExample example = new ItemsExample();
		List<Items> list = itemsMapper.selectByExampleWithBLOBs(example);
		return list;
	}

	public Items findItemsById(Integer id) throws Exception {
		Items items = itemsMapper.selectByPrimaryKey(id);
		return items;
	}

	public void updateItems(Items items) throws Exception {
		// TODO Auto-generated method stub
		itemsMapper.updateByPrimaryKeyWithBLOBs(items);
	}
}
