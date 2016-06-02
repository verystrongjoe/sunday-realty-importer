package snippet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class SundayRealtyImporter {
	
//	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
//	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/skeleton";
//	private static final String DB_USER = "root";
//	private static final String DB_PASSWORD = "admin";

	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://birming.asuscomm.com:6033/REALTY?autoReconnect=true&useSSL=false";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "root";

	
	public static void main(String[] argv) throws FileNotFoundException, IOException {
		try {
			
			String directory = "C:/Users/JY/git/sunday-realty-importer/sunday-realty-importer";
	        File dir = new File(directory);

	        File[] matchingFiles = dir.listFiles(new FilenameFilter() {
	               public boolean accept(File dir, String fileName) {
	            	   if(fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
	            		   return true;
	            	   }
	            	   else {
	            		   return false;
	            	   }
	               }
	               
	        });

	        for(File xls : matchingFiles) {
	        	POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("test.xls"));
			    HSSFWorkbook wb = new HSSFWorkbook(fs);
			    
			    for(int s = 0; s < wb.getNumberOfSheets(); s++) {
				    HSSFSheet sheet = wb.getSheetAt(s);
				    HSSFRow row;
				    HSSFCell cell;
		
				    int rows; // No of rows
				    rows = sheet.getPhysicalNumberOfRows();
		
				    int cols = 0; // No of columns
				    int tmp = 0;
		
				    // This trick ensures that we get the data properly even if it doesn't start from first few rows
				    for(int i = 0; i < 10 || i < rows; i++) {
				        row = sheet.getRow(i);
				        if(row != null) {
				            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
				            if(tmp > cols) cols = tmp;
				        }
				    }
		
		//		    for(int r = 0; r < rows; r++) {
				    for(int r = 1; r < rows; r++) {
				        row = sheet.getRow(r);
				        if(row != null) {
				            
				        	RealtyVo vo = new RealtyVo();
				        	vo.setSigungu( row.getCell((short)0).getStringCellValue() );
				        	vo.setMainNo(      new Double( row.getCell((short)1).getNumericCellValue() ).intValue() );
				        	vo.setSubNo(   new Double( row.getCell((short)2).getNumericCellValue() ).intValue()    );
				        	vo.setDanjiName(     row.getCell((short)3).getStringCellValue()      );
				        	vo.setRealSize(   new Double( row.getCell((short)4).getNumericCellValue() ).intValue()    );
		
				        	//5번째 계약일은 range value이기때문에 우선 생략
				        	
				        	//vo.setContractMoney(  new Double( row.getCell((short)6).getNumericCellValue() ).intValue()    );
				        	vo.setContractMoney(  Integer.valueOf( row.getCell((short)6).getStringCellValue().replace(",","")  ).intValue()  );
				        	
		//		        	vo.setStory(   new Double( row.getCell((short)7).getNumericCellValue() ).intValue()   );
				        	vo.setStory(   new Double( row.getCell((short)7).getStringCellValue() ).intValue()   );
				        	
				        	vo.setConstructYear(    new Double( row.getCell((short)8).getNumericCellValue() ).intValue()   );
				            
				        	vo.setAddress(   row.getCell((short)9).getStringCellValue()    );
				            
				        	insertRecordIntoTable(vo);
				        }
				    }
			    
			    }
	        }
		    
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void insertRecordIntoTable(RealtyVo vo) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO realty"
				+ "(Sigungu,MainNo,SubNo,DanjiName,RealSize,ContractDate,ContractMoney,Story,ConstructYear,Address) VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?)";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			preparedStatement.setString(1, vo.getSigungu());
			preparedStatement.setInt(2, vo.getMainNo());
			preparedStatement.setInt(3, vo.getSubNo());
			preparedStatement.setString(4, vo.getDanjiName());
			preparedStatement.setInt(5,  vo.getRealSize());
			preparedStatement.setDate(6, new Date(System.currentTimeMillis())); // 우선 현재날짜로..
			preparedStatement.setInt(7, vo.getContractMoney());
			preparedStatement.setInt(8, vo.getStory());
			preparedStatement.setInt(9, vo.getConstructYear());
			preparedStatement.setString(10, vo.getAddress());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}
		}

	}

	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());
		}

		try {
			dbConnection = DriverManager.getConnection(
                            DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

	private static java.sql.Timestamp getCurrentTimeStamp() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
	}
	
}

