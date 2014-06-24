package com.rootls.test;

import com.rootls.crud.finance.Daytip;
import com.rootls.crud.finance.DaytipRepository;
import com.rootls.helper.ReadData;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-3-14
 * Time: 下午4:53
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-root.xml"})
public class ReadDaytip {

    @Autowired
    DaytipRepository daytipRepository;

    @Autowired
    ReadData readData;

    @Test
    public void testReadDaytip() throws Exception {

        List<Daytip> list = readData.readTipsFromFile();
        String tableName = "daytip";
        for (Daytip tip : list) {
            if (!daytipRepository.exsitDaytip(tip.getTipDateStr(), tableName)) {
                daytipRepository.addDaytip(tip, tableName);
            } else {
                daytipRepository.appendDaytipMoney(tip, tableName);
            }
        }

        Assert.assertTrue(daytipRepository.countDaytip(tableName) > 0);
    }

    @Test
    public void testUpdateTipDate() throws Exception {
        String tableName = "daytip";
        daytipRepository.updateTipDate(2014, tableName);
        Assert.assertTrue(daytipRepository.checkTipDate(tableName));
    }

    @Test
    public void testReloadDaytip() throws Exception {
        String tableName = "daytip";
        daytipRepository.delDaytip(tableName);
        Assert.assertTrue(daytipRepository.countDaytip(tableName) == 0);

        testReadDaytip();
        testUpdateTipDate();
    }

    @Test
    public void testReloadDaytipDetail() throws Exception {
        String tableName = "daytip_detail";
        daytipRepository.delDaytip(tableName);
        Assert.assertTrue(daytipRepository.countDaytip(tableName) == 0);

        List<Daytip> list = readData.readTipsFromFile();
        for (Daytip tip : list) {
            daytipRepository.addDaytip(tip, tableName);
        }
        Assert.assertTrue(daytipRepository.countDaytip(tableName) > 0);

        daytipRepository.updateTipDate(2014, tableName);
        Assert.assertTrue(daytipRepository.checkTipDate(tableName));
    }

}
