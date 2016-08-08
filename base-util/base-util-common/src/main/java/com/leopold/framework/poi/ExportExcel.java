package com.leopold.framework.poi;

import com.leopold.framework.GenericsUtils;
import com.leopold.framework.poi.bean.ExcelColumnBean;
import com.leopold.framework.poi.util.PoiCommonUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;

import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Collection导出Excel类
 *
 * Created by IDEA
 * User:Leopold
 * Email:ylp_boy@126.com
 * Date:2015/12/21
 * Time:22:33
 */
public class ExportExcel<T> {
    private final static Logger logger=Logger.getLogger(ExportExcel.class);
    private Collection dataCollection;//数据集
    private List<ExcelColumnBean> columnList;//实体类列属性集合
    private String fileName;//导出文件名
    private String path;//导出路径
    private Class beanClass;//实体类
    public  ExportExcel(){}
    public  ExportExcel(Collection dataCollection,String fileName,String path){
        this.dataCollection=dataCollection;
        this.beanClass= GenericsUtils.getGenericType(this.getClass());
        this.columnList= PoiCommonUtils.getColumnBeanList(beanClass);
        this.fileName=fileName;
        this.path=path;
    }

    /**
     * 导出方法
     */
    public void export() throws Exception{
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个sheet页
        HSSFSheet sheet = workbook.createSheet("sheet1");
        sheet.autoSizeColumn(1);

        //创建标题style
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        setTitleFont(workbook,titleStyle);

        //创建非标题的style
        HSSFCellStyle noTitleStyle = workbook.createCellStyle();
        noTitleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        HSSFCell cell;
        HSSFRow row=sheet.createRow(0);
        for(int i=0;i<columnList.size();i++){
            cell=row.createCell(i);
            cell.setCellStyle(titleStyle);
            cell.setCellValue(columnList.get(i).getColumnName());
        }
        int rowNum=1;
        Iterator iterator=dataCollection.iterator();
        if(iterator.hasNext()){
            row=sheet.createRow(rowNum);
            for(int j=0;j<columnList.size();j++){
                cell=row.createCell(j);
                cell.setCellStyle(noTitleStyle);
                Object value=columnList.get(j).getGetterMethod()
                        .invoke(iterator.next(), null);
                if(value==null){
                    cell.setCellValue("");
                }else{
                    cell.setCellValue(value.toString());
                }
            }
        }
        FileOutputStream fout = new FileOutputStream(path+fileName+".xls");
        workbook.write(fout);
        fout.close();
    }
    private void setTitleFont(HSSFWorkbook wb,HSSFCellStyle style){
        HSSFFont font=wb.createFont();
        font.setFontHeightInPoints((short)12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
    }
}
