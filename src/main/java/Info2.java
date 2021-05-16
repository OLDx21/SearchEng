import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;

public class Info2 {
    SXSSFWorkbook workbook = new SXSSFWorkbook();
    static int row=0;
    public  void clear(){
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        workbook.dispose();
        this.workbook=null;

    }

    public  SXSSFWorkbook getWorkbook() {
        return this.workbook;
    }

    public static int getRow() {
        return row;
    }

    public  void setWorkbook(SXSSFWorkbook workbook) {
        this.workbook =workbook;
    }

    public static void setRow(int row) {
        Info2.row = row;
    }
}
