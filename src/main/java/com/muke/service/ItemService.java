package com.muke.service;

import com.muke.error.BusinessException;
import com.muke.service.model.ItemModel;

import java.util.List;

/**
 * Created by   Intellif Idea 2019.09
 * Author:  Wang Yun
 * Date:    2019-09-01
 * Time:    19:27
 */
public interface ItemService {
//    创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

//    商品列表
    List<ItemModel> listItem();

//    商品详细浏览
    ItemModel getItemById(Integer id);
}
