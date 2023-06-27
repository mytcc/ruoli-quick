package ruoli.work.module.test.entity;

import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.databind.ObjectMapper;
import ruoli.work.core.entity.CommonException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EasyExcelUtils {

    ObjectMapper mapper=new ObjectMapper();

    public void exportToExcel(List<LinkedHashMap<String,String>> data, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=202210122.xlsx");
            EasyExcel.write(response.getOutputStream())
                    .head(head(data))
                    .autoCloseStream(true)
                    .useDefaultStyle(true)
                    .sheet("数据导出")
                    .doWrite(dataList(data));
        } catch (Exception e) {
            throw new CommonException(500,"执行数据导出出现错误",e);
        }
    }

    //设置表头
    private List<List<String>> head(List<LinkedHashMap<String, String>> dataList) {
        List<List<String>> list = new ArrayList<>();
        Map<String, String> map =dataList.get(0);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            List<String> headList = new ArrayList<>();
            headList.add(entry.getKey());
            list.add(headList);
        }
        return list;
    }

    //设置导出的数据内容
    private List<List<String>> dataList(List<LinkedHashMap<String, String>> dataList) {
        List<List<String>> list = new ArrayList<>();
        for (Map<String, String> map : dataList) {
            List<String> data = new ArrayList<>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                data.add(entry.getValue());
            }
            list.add(data);
        }
        return list;
    }


}