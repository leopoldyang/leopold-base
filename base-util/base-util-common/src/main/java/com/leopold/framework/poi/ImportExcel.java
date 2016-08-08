package com.leopold.framework.poi;

import com.common.leopold.poi.bean.ExcelColumnBean;
import com.common.leopold.poi.util.PoiCommonUtils;
import com.common.leopold.util.GenericsUtils;
import com.common.leopold.util.model.CommonResult;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel转换List类
 *
 * Created by IDEA
 * User:Leopold
 * Email:ylp_boy@126.com
 * Date:2016/3/6
 * Time:5:46
 */
public class ImportExcel<T> {
    private HSSFWorkbook workbook;
    private List<ExcelColumnBean> list;
    private Class beanClass;
    private static final String EXECUTE_RESULT_SUCCESS="success";
    public  ImportExcel(){}

    /**
     * 初始化方法
     * @param inputStream 文件流
     */
    public  ImportExcel(InputStream inputStream){
        try {
            this.workbook=new HSSFWorkbook(new POIFSFileSystem(inputStream));
            this.beanClass= GenericsUtils.getGenericType(this.getClass());
            this.list= PoiCommonUtils.getColumnBeanList(beanClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 转换list
     * @return
     * @throws Exception
     */
    public CommonResult<List<T>> excelToList() throws Exception{
        CommonResult<List<T>> result=new CommonResult<List<T>>();
        String checkResult=this.checkExcel();
        if(!ImportExcel.EXECUTE_RESULT_SUCCESS.equals(checkResult)){
            result.setMsg(checkResult);
            result.setSuccess(false);
            return result;
        }
        List reList = new ArrayList();
        HSSFSheet sheet = workbook.getSheetAt(0);
        int count = 1;
        boolean flag = sheet.getRow(count) == null ? false : true;
        while (flag) {
            Object obj = beanClass.newInstance();
            HSSFRow row = sheet.getRow(count);
            if (row != null) {
                for (int i = 0; i < list.size(); i++) {
                    ExcelColumnBean columnBean = list.get(i);
                    Object value = PoiCommonUtils.parseType(columnBean.getColumnField().getType(),
                            row.getCell(i).getStringCellValue());
                    columnBean.getSetterMethod().invoke(obj, value);
                }
                reList.add(obj);
                count++;
            } else {
                flag = false;
            }
        }
        result.setSuccess(true);
        result.setValue(reList);
        return result;
    }

    /**
     * 导入Excel校验
     * @return
     */
    private String checkExcel(){
        String result="success";
        if(workbook==null){
            return "上传文件为空!";
        }
        HSSFSheet sheet=workbook.getSheetAt(0);
        HSSFRow row=sheet.getRow(0);
        for(int i=0;i<list.size();i++){
            if(!row.getCell(i).getStringCellValue().equals(list.get(i).getColumnName())){
                return "sheet中列名与要求不匹配!";
            }
        }
        return  result;
    }
}
