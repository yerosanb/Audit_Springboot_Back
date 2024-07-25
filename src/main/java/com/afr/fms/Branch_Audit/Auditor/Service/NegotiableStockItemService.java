package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Branch_Audit.Auditor.Mapper.NegotiableStockItemMapper;
import com.afr.fms.Branch_Audit.Entity.NegotiableStockItem;

@Service
public class NegotiableStockItemService {
    @Autowired
    private NegotiableStockItemMapper negotiableStockItemMapper;

    public void createNegotiableStockItem(NegotiableStockItem negotiableStockItem) {

        negotiableStockItemMapper.createNegotiableStockItem(negotiableStockItem);

    }

    public void updateNegotiableStockItemCategory(NegotiableStockItem negotiableStockItem) {
        negotiableStockItemMapper.updateNegotiableStockItem(negotiableStockItem);

    }

    public List<NegotiableStockItem> getNegotiableStockItem() {
        return negotiableStockItemMapper.getNegotiableStockItem();
    }

    public List<NegotiableStockItem> getNegotiableStockItemByCategory(String category) {
        return negotiableStockItemMapper.getNegotiableStockItemByCategory(category);
    }
    

    
    public void deleteNegotiableStockItem(NegotiableStockItem negotiableStockItem) {

        negotiableStockItemMapper.deleteNegotiableStockItem(negotiableStockItem.getId());

    }

}
