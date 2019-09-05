package com.muke.controller;

import com.muke.controller.viewobject.ItemVO;
import com.muke.error.BusinessException;
import com.muke.response.CommonReturnType;
import com.muke.service.ItemService;
import com.muke.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * Created by   Intellif Idea 2019.09
 * Author:  Wang Yun
 * Date:    2019-09-01
 * Time:    21:05
 */
@Controller
@RequestMapping("/item")
@CrossOrigin(allowedHeaders = "*",allowCredentials = "true")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    // 创建商品
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType createItem(@RequestParam(name = "title")String title,
                                       @RequestParam(name = "price")BigDecimal price,
                                       @RequestParam(name = "descript")String descript,
                                       @RequestParam(name = "stock") Integer stock,
                                       @RequestParam(name = "imgUrl")String imgUrl) throws BusinessException {
    //    封装service请求创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setPrice(price);
        itemModel.setDescript(descript);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);

        ItemModel itemModel1 = itemService.createItem(itemModel);
        // 将创建好的商品返回给前端
        ItemVO itemVO = convertItemVOFromItemModel(itemModel1);
        return CommonReturnType.create(itemVO);



    }

    private ItemVO convertItemVOFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO);
        return itemVO;

    }


}
