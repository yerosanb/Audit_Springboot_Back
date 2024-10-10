package com.afr.fms.Branch_Audit.Auditor.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.afr.fms.Branch_Audit.Entity.NegotiableStockItem;

@Mapper
public interface NegotiableStockItemMapper {

	@Select("select * from  negotiable_stock_item")
	public List<NegotiableStockItem> getNegotiableStockItem();

	@Select("select * from  negotiable_stock_item where audit_type= #{category}")
	public List< NegotiableStockItem>   getNegotiableStockItemByCategory(String category);

	@Insert(" insert into negotiable_stock_item(stock_type, audit_type) values(#{stock_type},#{audit_type})")
	public void createNegotiableStockItem(NegotiableStockItem negotiableStockItem);

	@Update("update negotiable_stock_item set stock_type= #{stock_type}, audit_type = #{audit_type} where id=#{id} ")
	public void updateNegotiableStockItem(NegotiableStockItem negotiableStockItem);

	@Delete("delete from negotiable_stock_item where id=#{id} ")
	public void deleteNegotiableStockItem(Long id);
	@Select("select * from  negotiable_stock_item where id=#{negotiable_stock_item_d}")
	public NegotiableStockItem getNegotiableStockItemById(Long negotiable_stock_item_d);

}