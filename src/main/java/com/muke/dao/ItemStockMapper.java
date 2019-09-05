package com.muke.dao;

import com.muke.entity.ItemStock;

public interface ItemStockMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sun Sep 01 20:03:31 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sun Sep 01 20:03:31 CST 2019
     */
    int insert(ItemStock record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sun Sep 01 20:03:31 CST 2019
     */
    int insertSelective(ItemStock record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sun Sep 01 20:03:31 CST 2019
     */
    ItemStock selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sun Sep 01 20:03:31 CST 2019
     */
    int updateByPrimaryKeySelective(ItemStock record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sun Sep 01 20:03:31 CST 2019
     */
    int updateByPrimaryKey(ItemStock record);

    ItemStock selectByItemId(Integer itemId);
}