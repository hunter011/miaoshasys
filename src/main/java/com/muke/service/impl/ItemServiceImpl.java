package com.muke.service.impl;

import com.muke.dao.ItemMapper;
import com.muke.dao.ItemStockMapper;
import com.muke.entity.Item;
import com.muke.entity.ItemStock;
import com.muke.error.BusinessException;
import com.muke.error.EnBusinessError;
import com.muke.service.ItemService;
import com.muke.service.model.ItemModel;
import com.muke.validator.ValidatorImpl;
import com.muke.validator.ValidatorResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by   Intellif Idea 2019.09
 * Author:  Wang Yun
 * Date:    2019-09-01
 * Time:    19:31
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired(required = false)
    private ItemMapper itemMapper;

    @Autowired(required = false)
    private ItemStockMapper itemStockMapper;



    private Item convertItemFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        Item item = new Item();
        BeanUtils.copyProperties(itemModel,item);
        return item;
    }

    private ItemStock convertItemStockFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemStock itemStock = new ItemStock();
        itemStock.setItemId(itemModel.getId());
        itemStock.setStock(itemModel.getStock());
        return itemStock;


    }
    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        //校验入参
        ValidatorResult result = validator.validate(itemModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EnBusinessError.PARAMETER_VALIDATTION_ERROR,result.getErrMsg());
        }

        //转换ItemModel--》Item
        Item item = this.convertItemFromItemModel(itemModel);

        //写入数据库
        int i = itemMapper.insertSelective(item);
        itemModel.setId(item.getId());
        ItemStock itemStock = convertItemStockFromItemModel(itemModel);
        itemStockMapper.insertSelective(itemStock);
        //返回创建完成的对象

        return this.getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> listItem() {
        return null;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        Item item = itemMapper.selectByPrimaryKey(id);
        if (item == null) {
            return null;
        }
        // 操作获取库存数量
        ItemStock itemStock = itemStockMapper.selectByItemId(id);


        //将item转换为Model
        ItemModel itemModel = convertItemModelFromItem(item, itemStock);

        return itemModel;
    }

    private ItemModel convertItemModelFromItem(Item item,ItemStock itemStock) {
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(item,itemModel);
        itemModel.setStock(itemStock.getStock());
        return itemModel;

    }
}
