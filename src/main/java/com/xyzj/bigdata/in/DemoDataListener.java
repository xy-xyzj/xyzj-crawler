package com.xyzj.bigdata.in;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.xyzj.crawler.utils.savetomysql.SaveToMysql;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模板的读取类
 *
 * @author Jiaju Zhuang
 */
public class DemoDataListener extends AnalysisEventListener<DemoData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoDataListener.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5000;
    List<DemoData> list = new ArrayList<DemoData>();

    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        String sql = "INSERT INTO my_test " +
                "(name, birthday, age) VALUES (?, ?, ?)";
        List<Object[]> param = new ArrayList<>();

        for(int i=0;i<list.size();i++) {
            DemoData demoData = list.get(i);
            String[] arr =new String[3];
            arr[0] = demoData.getString();
            arr[1] = String.valueOf(demoData.getDate());
            arr[2] = String.valueOf(demoData.getDoubleData());
            param.add(arr);
        }
        //保存到数据库
        SaveToMysql saveToMysql = new SaveToMysql();
        saveToMysql.batchUpdate(sql,param);
        LOGGER.info("存储数据库成功！");
    }



}
