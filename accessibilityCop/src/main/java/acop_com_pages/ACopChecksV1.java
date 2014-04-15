package acop_com_pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



	/**
	 * Accessibility common modules to test key check points on any webpage
	 * 	ACOP 1 ALT TEXT - IMAGE <IMG ...>
	 *	 	- Image elements should have alt attributes
	 * 		- Image elements should not have alt attributes empty
	 * 
	 *  ACOP 2 ALT TEXT - INPUT IMAGE <INPUT TYPE = 'image' ...>
	 * 		- Image input elements should have alt attributes
	 * 		- Image input elements should not have alt attributes empty
	 * 
	 *  ACOP 3 ALT TEXT - ANCHOR IMAGE <A><IMG ...>
	 * 		- Image elements inside anchor tags should have empty alt attributes
	 * 
	 *  ACOP 4 ALT TEXT - AREA ALT
	 * 		- <area shape='rect' coords='0,0,82,126' href='sun.htm' alt='Sun'>
	 * 		- Area elements should have an alt attribute
	 * 		- Area element alt attribute cannot be empty
	 * 
	 *  ACOP 5 TITLE - <TITLE> </TITLE>
	 * 		- Page title element should not be empty or missing
	 * 		- There should not be more than one page title
	 * 
	 *  ACOP 6 FRAMES <FRAMESET><FRAME title=""... >
	 * 		- Frame elements should have title attributes
	 * 		- Frame elements should not have title attributes empty
	 * 
	 *  ACOP 7 iFRAMES - <iFRAME title=""... >
	 * 		- iFrame elements should have title attributes
	 * 		- iFrame elements should not have title attributes empty
	 * 
	 *  ACOP 8 DOCTYPE - <!DOCTYPE Resource SYSTEM 'foo.dtd'>
	 * 		- Doctype should be specified if frame or iframe elements exist on the page
	 * 
	 *  ACOP 9 VISUAL FORMATTING <b></b>, <i></i>, <center></center>, <font></font>, <u></u>
	 * 		- HTML visual formatting elements should be avoided
	 * 		- CSS should be used for formatting
	 * 
	 *  ACOP 10 HTML LANG <HTML LANG='en'></HTML>
	 * 		- Primary language of a page should be declared within html lang attribute
	 * 
	 *  ACOP 11 HYPERLINKS <body><a href="www.intuit.com">Go to Intuit</a></body>
	 * 		- Hyperlinks should always have text associated with them
	 * 		- There should not be duplicate text for hyperlinks on the same page
	 *  
	 *  ACOP 12 FLASHING CONTENT <blink> <marquee>
	 * 		- blink and marquee elements must not be used
	 * 		- There should not be duplicate text for hyperlinks on the same page
	 * 
	 *  ACOP 13 TABLE <table summary="summary"><th>Table Heading</th><tr><td>Data 1</td></tr></table>
	 * 		- Table should have a table header
	 * 		- Table should have a non empty summary attribute
	 * 
	 *  ACOP 14 TABLE <table summary="summary"><th role="columnheader" ...>
	 * 		- Table headers should have a non empty scope attribute specifying whether it is for a row or column
	 * 		- Use SCOPE, ROLE, HEADER, ID for screen reader
	 * 
	 * @author asuryanarayana
	 *
	 */

public class ACopChecksV1  {
	
	protected static WebDriver driver =  null;

	Map<String, String> content = new HashMap<String, String>();
	private static Log logger = getLog(ACopChecksV1.class);

	private final String altAttribute = "alt";
	private final String srcAttribute = "src";
	private final String hrefAttribute = "href";
	private final String titleAttribute = "title";
	private final String langAttribute = "lang";
	private final String summaryAttribute = "summary";
	private final String roleAttribute = "role";
	private final String scopeAttribute = "scope";
	private final String headerAttribute = "header";
	

	private final String inputXpath = "//input[@type='image']";
	private final String imageXpath = "//img";
	private final String areaXpath = "//area";
	private final String titleXpath = "//title";
	private final String iframeXpath = "//iframe";
	private final String frameSetXpath = "//frameset/frame";
	private final String htmlXpath = "//html";
	private final String hyperLinkXpath = "//a";
	private final String tableXpath = "//table";
	private final String tableHeaderXpath = "//table//th";

	
	private int WAIT_TO_CHECK = 500;
	public static int MaxWaitSeconds = 2000;

	public static List <String> acopErrorList = new ArrayList<String>();
	
	public ACopChecksV1(WebDriver driver) {
		this.driver = driver;
	}
	
	
	public List<String> runAcopChecks () {
		checkAltTextImageType();
		checkAltTextInputType();
		checkAreaAltText();
		checkTitleText();
		checkFrameSetText();
		checkiFrameTitleText();
		checkDocType();
		checkVisualFormatting();
		checkLanguageAttribute();
		checkHyperLinksText();
		checkFlashingContent();
		checkTableAccessibility();
		checkTableHeaders();
		
		return acopErrorList;		
	}
	
	/** 1
	 * ALT TEXT - IMAGE <IMG ...>
	 * 	- Image elements should have alt attributes
	 * 	- Image elements should not have alt attributes empty
	 * 
	 */
	public void checkAltTextImageType(){
		logger.info(" Accessbility Rule 1 - ALT TEXT for Images "); 
		if(isElementPresentWithXPath(driver, imageXpath)){
			List<WebElement> subNavLinks = driver.findElements(By.xpath(imageXpath));
			for (WebElement subNavLink : subNavLinks) {
				String altAttributeText = subNavLink.getAttribute(altAttribute);
				String srcAttributeText = subNavLink.getAttribute(srcAttribute);
				logger.info(" IMAGE Source - " +srcAttributeText  + "  Alternate Text - " + altAttributeText);
				if(altAttributeText.isEmpty()){
					acopErrorList.add("FAILURE Accessbility Rule 1 - Alternate Text Missing " +
							"for Image - " + srcAttributeText);
					logger.info(" FAILURE Accessbility Rule 1 - Alternate Text Missing " +
							"for Image - " + srcAttributeText);
				}
			}
		}else {
			logger.info(" Accessbility Rule 1 - ALT TEXT for Images - does not apply to this webpage "); 
		}
	}
	
	
	/** 2
	 * ALT TEXT - INPUT IMAGE <INPUT TYPE = 'image' ...>
	 * 	- Image input elements should have alt attributes
	 * 	- Image input elements should not have alt attributes empty
	 * 
	 */
	public void checkAltTextInputType(){
		logger.info(" Accessbility Rule 2 - ALT TEXT for Input Images "); 
		if(isElementPresentWithXPath(driver, inputXpath)){
			List<WebElement> subNavLinks = driver.findElements(By.xpath(inputXpath));
			for (WebElement subNavLink : subNavLinks) {
				String altAttributeText = subNavLink.getAttribute(altAttribute);
				String srcAttributeText = subNavLink.getAttribute(srcAttribute);
				logger.info(" IMAGE Source - " +srcAttributeText  + "  Alternate Text - " + altAttributeText);
				if(altAttributeText.isEmpty()){
					acopErrorList.add(" FAILURE Accessbility Rule 2 - Alternate Text Missing " +
						"for Image - " + srcAttributeText);
					logger.info(" FAILURE Accessbility Rule 2 - Alternate Text Missing " +
							"for Image - " + srcAttributeText);
				}
			}
		}else {
			logger.info(" Accessbility Rule 2 - ALT TEXT for Input Images - does not apply to this webpage "); 
		}
	}

	
	/**
	 * 3
	 * ALT TEXT - ANCHROR TAGS
	 * 	- Will be added in next version 0.1.2
	 * 
	 */
	
	
	/** 4
	 * ALT TEXT - AREA ALT
	 * <area shape='rect' coords='0,0,82,126' href='sun.htm' alt='Sun'>
	 * 	- Area elements should have an alt attribute
	 * 	- Area element alt attribute cannot be empty
	 * 
	 */
	public void checkAreaAltText(){
		logger.info(" Accessbility Rule 4 - ALT TEXT for Area tags "); 
		if(isElementPresentWithXPath(driver, areaXpath)){
			List<WebElement> subNavLinks = driver.findElements(By.xpath(areaXpath));
			for (WebElement subNavLink : subNavLinks) {
				String altAttributeText = subNavLink.getAttribute(altAttribute);
				String hrefAttributeText = subNavLink.getAttribute(hrefAttribute);
				logger.info("  IMAGE Source - " +hrefAttributeText  + "  Alternate Text - " + altAttributeText);
				if(altAttributeText.isEmpty()){
					acopErrorList.add(" FAILURE Accessbility Rule 4 - Alternate Text Missing " +
						"for Image - " + hrefAttributeText);
					logger.info(" FAILURE Accessbility Rule 4 - Alternate Text Missing " +
							"for Image - " + hrefAttributeText);
				}
			}
		}else {
			logger.info(" Accessbility Rule 4 - ALT TEXT for Area tags - does not apply to this webpage "); 
		}
	}	

	
	/** 5
	 * TITLE - <TITLE> </TITLE>
	 * 	- Page title element should not be empty or missing
	 * 	- There should not be more than one page title
	 */
	public void checkTitleText(){
		logger.info(" Accessbility Rule 5 - Page Title Should not be empty "); 
		if(isElementPresentWithXPath(driver, titleXpath)){
			int count = getCountOfElementsWithSameXpath(driver, titleXpath);
			if(count > 1){
				acopErrorList.add(" FAILURE Accessbility Rule 5 - More than one Page Title found - ");
				logger.info(" FAILURE Accessbility Rule 5 - More than one Page Title found - ");
			}
			String titleText =  driver.getTitle();
			logger.info(" Page Title Text - " + titleText);
			if(titleText.isEmpty()){
				acopErrorList.add(" FAILURE Accessbility Rule 5 - Page Title is Missing for Webpage - ");
				logger.info(" FAILURE Accessbility Rule 5 - Page Title is Missing for Webpage - ");
			}
		}else {
			logger.info(" Accessbility Rule 5 - Page Title Should not be empty - does not apply to this webpage "); 
		}
	}	
	

	
	/** 6
	 * FRAMES <FRAMESET><FRAME title=""... >
	 * 	- Frame elements should have title attributes
	 * 	- Frame elements should not have title attributes empty
	 */
	public void checkFrameSetText(){
		logger.info(" Accessbility Rule 6 - Frame elements should have title attributes "); 
		if(isElementPresentWithXPath(driver, frameSetXpath)){
			List<WebElement> subNavLinks = driver.findElements(By.xpath(frameSetXpath));
			for (WebElement subNavLink : subNavLinks) {
				String altAttributeText = subNavLink.getAttribute(titleAttribute);
				String hrefAttributeText = subNavLink.getAttribute(hrefAttribute);
				logger.info(" FrameSet Source - " +hrefAttributeText  + "  Title Text - " + altAttributeText);
				if(altAttributeText.isEmpty()){
					acopErrorList.add(" FAILURE Accessbility Rule 6 - Frame element Title " +
						"text is missing - " + hrefAttributeText);
					logger.info(" FAILURE Accessbility Rule 6 - Frame element Title " +
							"text is missing - " + hrefAttributeText);
				}
			}
		}else {
			logger.info(" Accessbility Rule 6 - Frame elements should have title attributes - does not apply to this webpage "); 
		}
	}	

	
	
	/** 7
	 * iFRAMES - <iFRAME title=""... >
	 * 	- iFrame elements should have title attributes
	 * 	- iFrame elements should not have title attributes empty
	 * 
	 */
	public void checkiFrameTitleText(){
		logger.info(" Accessbility Rule 7 - iFrame elements should have title attributes "); 
		if(isElementPresentWithXPath(driver, iframeXpath)){
			List<WebElement> subNavLinks = driver.findElements(By.xpath(iframeXpath));
			for (WebElement subNavLink : subNavLinks) {
				String altAttributeText = subNavLink.getAttribute(titleAttribute);
				String srcAttributeText = subNavLink.getAttribute(srcAttribute);
				logger.info("  iFrame Source - " +srcAttributeText  + "  Title Text - " + altAttributeText);
				if(altAttributeText.isEmpty()){
					acopErrorList.add(" FAILURE Accessbility Rule 7 - iFrame element Title " +
						"text is missing - " + srcAttributeText);
					logger.info(" FAILURE Accessbility Rule 7 - iFrame element Title " +
							"text is missing - " + srcAttributeText);
				}
			}
		}else {
			logger.info(" Accessbility Rule 7 - iFrame elements should have title attributes - does not apply to this webpage ");  
		}
	}	
	

	
	/** 8
	 * DOCTYPE - <!DOCTYPE Resource SYSTEM 'foo.dtd'>
	 * 	- Doctype should be specified if frame or iframe elements exist on the page
	 * 
	 */
	public void checkDocType(){
		logger.info(" Accessbility Rule 8 -  DOCTYPE should be specified if frames exist on page "); 
		if(driver.getPageSource().contains("iframe") || driver.getPageSource().contains("frame")){
			if(! (driver.getPageSource().contains("DOCTYPE"))){
				acopErrorList.add(" FAILURE Accessbility Rule 8 - " +
						"DOCTYPE is missing for this webpage");
				logger.info(" FAILURE Accessbility Rule 8 - " +
						"DOCTYPE is missing for this webpage");
				}
		}else {
			logger.info(" Accessbility Rule 8 -  DOCTYPE should be specified if frames exist on page - does not apply to " +
					"this webpage ");  
		}
	}	
	
	
	
	/** 9
	 * VISUAL FORMATTING <b></b>, <i></i>, <center></center>, <font></font>, <u></u>
	 * 	- HTML visual formatting elements should be avoided
	 * 	- CSS should be used for formatting
	 * 
	 */
	public void checkVisualFormatting(){
		logger.info(" Accessbility Rule 9 -  HTML visual formatting elements should be avoided on page, use CSS instead "); 
		if (driver.getPageSource().contains("<b>") || 
				driver.getPageSource().contains("<center>")	|| 
					driver.getPageSource().contains("<font>") || 
						driver.getPageSource().contains("<u>")){
			acopErrorList.add(" FAILURE Accessbility Rule 9 -  HTML visual formatting elements <b></b>, <i></i>, <center></center>, " +
					"<font></font>, <u></u> should be avoided on page - Use CSS instead");
			logger.info(" FAILURE Accessbility Rule 9 -  HTML visual formatting elements <b></b>, <i></i>, <center></center>, " +
					"<font></font>, <u></u> should be avoided on page - Use CSS instead");
		}else {
			logger.info(" Accessbility Rule 9 -  HTML visual formatting elements should be avoided on page, use CSS instead - " +
					"does not apply to this webpage ");  
		}
	}	
	
	
	
	/** 10
	 * HTML LANG <HTML LANG='en'></HTML>
	 * 	- Primary language of a page should be declared within html lang attribute
	 * 
	 */
	public void checkLanguageAttribute(){
		logger.info(" Accessbility Rule 10 - Primary language of a page should be declared within html lang attribute "); 
		if(isElementPresentWithXPath(driver, htmlXpath)){
			WebElement element = driver.findElement(By.xpath(htmlXpath));
			String titleText =  element.getAttribute(langAttribute);
			logger.info(" Language Attribute for the Webpage - " + titleText);
			if(titleText.isEmpty()){
				acopErrorList.add(" FAILURE Accessbility Rule 10 - Primary language of a page " +
					"should be declared within html lang attribute ");
				logger.info(" FAILURE Accessbility Rule 10 - Primary language of a page " +
						"should be declared within html lang attribute ");
			}
		}else {
			logger.info(" Accessbility Rule 10 - Primary language of a page should be declared within html lang attribute" +
					" - does not apply to this webpage "); 
		}
	}	
		
	
	/** 11
	 * HYPERLINKS <body><a href="www.intuit.com">Go to Intuit</a></body>
	 * 	- Hyperlinks should always have text associated with them
	 * 	- There should not be duplicate text for hyperlinks on the same page
	 * 
	 */
	public void checkHyperLinksText(){
		logger.info(" Accessbility Rule 11 - Hyperlinks should always have text associated with them "); 
		if(isElementPresentWithXPath(driver, hyperLinkXpath)){
			int count = getCountOfElementsWithSameXpath(driver, hyperLinkXpath);
			logger.info(" Total number of HyperLinks on this webpage " +count);
			List<WebElement> subNavLinks = driver.findElements(By.xpath(hyperLinkXpath));
			for (WebElement subNavLink : subNavLinks) {
				String altAttributeText = subNavLink.getText();
				String hrefAttributeText = subNavLink.getAttribute(hrefAttribute);
				logger.info(" HyperLink Source - " +hrefAttributeText  + " HyperLink Text - " + altAttributeText);
				if(altAttributeText.isEmpty()){
					acopErrorList.add("FAILURE Accessbility Rule 11 - Hyperlinks should always have text associated " +
							"with them for - " + hrefAttributeText);
					logger.info("FAILURE Accessbility Rule 11 - Hyperlinks should always have text associated " +
							"with them for - " + hrefAttributeText);
				}
			}
		}else {
			logger.info(" Accessbility Rule 11 - Hyperlinks should always have text associated with them - " +
					"does not apply to this webpage "); 
		}
	}	
	
	
	/** 12
	 * FLASHING CONTENT <blink> <marquee>
	 * 	- blink and marquee elements must not be used
	 * 	- There should not be duplicate text for hyperlinks on the same page
	 * 
	 */
	public void checkFlashingContent(){
		logger.info(" Accessbility Rule 12 - FLASHING CONTENT blink and marquee elements must not be used "); 
		if(driver.getPageSource().contains("<blink>") || 
				driver.getPageSource().contains("<marquee>")){
			acopErrorList.add(" FAILURE Accessbility Rule 12 -  FLASHING CONTENT blink and marquee elements must not be used ");
			logger.info(" FAILURE Accessbility Rule 12 -  FLASHING CONTENT blink and marquee elements must not be used ");
		}else {
		logger.info(" Accessbility Rule 12 -  FLASHING CONTENT blink and marquee elements must not be used  - " +
				"does not apply to this webpage ");
		}
	}	
	
	
	/** 13
	 * TABLE <table summary="summary"><th>Table Heading</th><tr><td>Data 1</td></tr></table>
	 * 	- Table should have a table header
	 * 	- Table should have a non empty summary attribute
	 * 
	 */
	public void checkTableAccessibility(){
		logger.info(" Accessbility Rule 13 - Table should have a non empty summary attribute "); 
		if(isElementPresentWithXPath(driver, tableXpath)){
			List<WebElement> subNavLinks = driver.findElements(By.xpath(tableXpath));
			for (WebElement subNavLink : subNavLinks) {
				String summaryAttributeText = subNavLink.getAttribute(summaryAttribute);
				logger.info(" Table Summary Text - " + summaryAttributeText);
				if(summaryAttributeText.isEmpty()){
					acopErrorList.add(" FAILURE Accessbility Rule 13 - " +
						"Table should have a non empty summary attribute  ");
					logger.info(" FAILURE Accessbility Rule 13 - " +
							"Table should have a non empty summary attribute  ");
				}
			}
		}else {
			logger.info(" Accessbility Rule 13 - Table should have a non empty summary attribute - " +
					"does not apply to this webpage ");  
		}
	}	
	
	
	/** 14
	 * TABLE <table summary="summary"><th role="columnheader" ...>
	 * 	- Table headers should have a non empty scope attribute specifying whether it is for a row or column
	 * 	- Use SCOPE, ROLE, HEADER, ID for screen reader
	 * 
	 */
	public void checkTableHeaders(){
		logger.info(" Accessbility Rule 14 - Table Headers should have attributes to specify row and column headers "); 
		if(isElementPresentWithXPath(driver, tableHeaderXpath)){
			List<WebElement> subNavLinks = driver.findElements(By.xpath(tableHeaderXpath));
			for (WebElement subNavLink : subNavLinks) {
				String headerAttributeText = subNavLink.getAttribute(headerAttribute);
				String roleAttributeText = subNavLink.getAttribute(roleAttribute);
				String scopeAttributeText = subNavLink.getAttribute(scopeAttribute);
				logger.info(" >>Header - " +headerAttributeText +" >> Role - " +roleAttributeText +" >>Scope - " +scopeAttributeText);
				if( (headerAttributeText == null) && (roleAttributeText == null) && (scopeAttributeText == null)) {
					acopErrorList.add("FAILURE Accessbility Rule 14 - Table Headers should have attributes to specify row and " +
							"column headers ");
					logger.info("FAILURE Accessbility Rule 14 - Table Headers should have attributes to specify row and " +
							"column headers ");
				}
			}
		}else {
			logger.info(" Accessbility Rule 14 - Table Headers should have attributes to specify row and column headers - " +
					"does not apply to this webpage ");  
		}
	}	
		

	/**
	 * Common Selenium Webdriver utility modules- non Accessibility section
	 * 
	 */
	
	/**
	 * Checks whether an element is present on the page. It uses an elements
	 * xpath expression to find it on the page
	 * 
	 * @param driver
	 *            webdriver instance used in the test
	 * @param xPathExpression
	 *            Xpath expression for a given element on the page
	 * @return true, if element is present; false if not found
	 */
	public boolean isElementPresentWithXPath(WebDriver driver, String xPathExpression) {
		boolean isElementPresent = false;
		WebElement targetElement = null;

		try {
			targetElement = waitOnXPath(xPathExpression);
			if (targetElement != null)
				isElementPresent = true;
			else
				isElementPresent = false;
		} catch (org.openqa.selenium.ElementNotVisibleException ex) {
			isElementPresent = false;
			logger.error("ElementNotVisibleException  for element with xpath "
					+ xPathExpression + " Error is " + ex.getMessage());
		} catch (org.openqa.selenium.NoSuchElementException ex) {
			logger.error("NoSuchElementException  for element with xpath "
					+ xPathExpression + " Error is " + ex.getMessage());
			isElementPresent = false;
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			logger.error("Stale Element Reference for element with xpath "
					+ xPathExpression + " Error is " + ex.getMessage());
			isElementPresent = false;
		}

		return isElementPresent;
	}
	
	/**
	 * Gets the count of elements having same xpath
	 * 
	 * @param driver
	 * @param subNavLinkXPath
	 * @return
	 */
	public int getCountOfElementsWithSameXpath(WebDriver driver, String xpath) {
		int count = 0;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<WebElement> elements = driver.findElements(By.xpath(xpath));
		if (elements != null & !elements.isEmpty())
			count = elements.size();
		return count;
	}
	
	/**
	 * Waits until webdriver can find the element specified by xpath.
	 *
	 * @param path
	 * @param maxMilliseconds
	 * @param frame
	 * @return true if WebDriver is able to find an element with the given id
	 */
	public WebElement waitOnXPath(String path) {
		return waitOnElement(By.xpath(path), MaxWaitSeconds, null);
	}

	
	/**
	 * Waits until webdriver can find the element specified by xpath.
	 * @param path
	 * @param maxMilliseconds
	 * @param frame
	 * 
	 */
	public WebElement waitOnElement(By element, int maxSeconds, String frame) {
		WebElement identifier = null;

		for (int i = 0; i < maxSeconds; i++) {
			try {
				if (frame == null)
					identifier = driver.findElement(element);
				else {
					driver.switchTo().defaultContent();
					identifier = driver.switchTo().frame(frame).findElement(element);
				}
				//if element is found return
				if (identifier != null) 
					break;
				Thread.sleep(WAIT_TO_CHECK);
			} catch (Exception e) {			
				identifier = null;
			}		
		}
		return identifier;
	}
		
	
	 /** Method for log4j implementation, here the input parameter is the class
	  name e.g: PaymentsHeaderTest.class It reads the log4j.xml and
	 * prints logging information accordingly.
	 */
	public static Log getLog(Class<?> string) {
		return getLog(string, "log4j.xml");
	}

	public static Log getLog (Class<?> className, String fileName) {
		Log logger = LogFactory.getLog(className);
		return logger;
	}
}
