package Com.Selenium.Basepage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
public class Basepage 
{

private static WebDriver driver;
private static List<WebElement> elementCount;
public static HSSFWorkbook workbook;
private static String filepath="C:\\Users\\ADMIN\\Desktop\\Country.xls";
@Parameters("Browsername")
@BeforeClass
public void  get_Browser(String Browsername) throws InterruptedException, FileNotFoundException, IOException
{
//browser
if(Browsername.equals("firefox"))
{
System.out.println("____"+Browsername+"---");
System.setProperty("webdriver.gecko.driver", "F:\\Ragu123\\Com.Selenium.Demo\\src\\test\\resources\\Drivers\\geckodriver.exe");
driver=new FirefoxDriver();
//chrome

}	
else if(Browsername.equals("chrome"))
{
System.out.println("____"+Browsername+"---");
System.setProperty("webdriver.chrome.driver", "F:\\Ragu123\\Com.Selenium.Demo\\src\\test\\resources\\Drivers\\chromedriver.exe");
driver=new ChromeDriver();

}
else if(Browsername.equals("IE"))
{
System.out.println("____"+Browsername+"---");
System.setProperty("webdriver.ie.driver", "F:\\Ragu123\\Com.Selenium.Demo\\src\\test\\resources\\Drivers\\IEDriverServer.exe");
driver=new InternetExplorerDriver();

}
else
{
System.out.println("no driver found");
}
}
@Test
public void Browser_url()
{
	driver.manage().window().maximize();
	driver.get("http://demo.guru99.com/selenium/newtours/register.php");
	driver.manage().timeouts().implicitlyWait(3000,TimeUnit.SECONDS);
     select_droup_dwon_values(driver);
}

public void select_droup_dwon_values(WebDriver driver)
{
	Select drpCountry = new Select(driver.findElement(By.name("country")));
	elementCount = drpCountry.getOptions();
	System.out.println(elementCount.size());
	try {
		write_Excel(elementCount,filepath);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}



public static void write_Excel(List<WebElement> elementCount2,String excelFilePath) throws FileNotFoundException, IOException, InterruptedException
{
workbook = new HSSFWorkbook();
Sheet sheet = workbook.createSheet("country");
 int rowCount = 0;
 for(int j=rowCount;j<=5;j++)
 {
  for(int i=rowCount;i<j;i++)
	{
    Row row = sheet.createRow(i);
    String name= elementCount.get(i).getText();
	
	Cell cell = row.createCell(0);
	cell.setCellValue(name);
	row = sheet.createRow(++rowCount);
	            
	}
    try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) 
   {
   	workbook.write(outputStream);
     	
   }
        
     }
	
    read_excel_sheet();
	}
public static List<String> read_excel_sheet() throws IOException, InterruptedException
{
List<String> li=new ArrayList<String>();
FileInputStream inputStream = new FileInputStream(new File(filepath));
workbook = new HSSFWorkbook(inputStream);
Sheet firstSheet=workbook.getSheet("country");
	//Sheet firstSheet = workbook.getSheetAt(0);
 Iterator<Row> iterator = firstSheet.iterator();
 List<String> li1=new ArrayList<String>();
 while (iterator.hasNext())
  {
   Row nextRow = iterator.next();
   Iterator<Cell> cellIterator = nextRow.cellIterator();
   while (cellIterator.hasNext()) 
   {
   Cell nextCell = cellIterator.next();
   int column= nextCell.getColumnIndex();
   if(column==0)
   {
 	String cells=nextCell.getStringCellValue();
        	//System.out.println(nextCell.getStringCellValue());
 	li1.add(cells);
    }
    }
    }
    li.addAll(li1);
    System.out.println(li);
    select_each_droupdown(li);
	return li ;
    }
		
public static void select_each_droupdown(List<String> element) throws FileNotFoundException, InterruptedException, IOException
{
/*System.setProperty("webdriver.gecko.driver", "F:\\Ragu123\\Com.Selenium.Demo\\src\\test\\resources\\Drivers\\geckodriver.exe");
driver=new FirefoxDriver();
driver.manage().window().maximize();*/
/*driver.get("http://demo.guru99.com/selenium/newtours/register.php");
driver.manage().timeouts().implicitlyWait(3000,TimeUnit.SECONDS);*/
Select drpCountry = new Select(driver.findElement(By.name("country")));
System.out.println(element.size());
List<WebElement> value=drpCountry.getOptions();
System.out.println(value.size());
for(int i=0;i<=5;i++)
{
String sValue = value.get(i).getText();
String excelvalue=element.get(i);
if(sValue.equals(excelvalue))
{
System.out.println(sValue+"----match----"+excelvalue);
}
else
{
System.out.println(sValue+"----not match----"+excelvalue);
}
}
}
public static WebDriver getDriver()
{
return driver;
}
public static void setDriver(WebDriver driver) 
{
Basepage.driver = driver;
}
}
