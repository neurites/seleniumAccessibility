seleniumAccessibility
=====================

Selenium Webdriver driven accessibility testing module to check for webpage accessibility compliance

Introduction
------------
   * Generic accessibility compliance test that runs through a webpage looking for accessibility errors and warnings
   * accessibilityCop-0.1.1.jar built with basic set of 14 accessibility checkpoints that applies to most webpages
   * Selenium Webdriver, TestNg and Java

Scope of version 0.1.1
----------------------
   * Design and develop seleniumAccessibility jar with basic accessibility checkpoints - 14 rules
   * Gather feedback from accessibility experts and further improve depth of testing and rules coverage
   * Socialize accessibility testing to be part of mainstream quality testing efforts

Usage
-----
   * Download latest version of accessibility test jar file from here : [accessibilityCop-0.1.1.jar](accessibilityCop/target/accessibilityCop-0.1.1.jar)
   * Include this jar as part of your existing selenium project
   * Create selenium test to feed in webpage urls that needs to be tested for accessibility compliance
   * Add appropriate assertions to validate errors/warnings returned by runAcopChecks() method

AcopChecksV1.java
-----------------
   * 14-point check for accessibility compliance
   * Adds errors, warnings to acopErrorList; Also prints log information related to accessibility checks
   * Returns acopErrorList to the testNg test
      * if acopErrorList is empty - no accessibility issues on webpage
      * if acopErrorList is not empty - accessibility issues found on webpage

Sample Test
-----------
	/**
	* sampleAcopChecks
	*
	*/
	@Test(groups = { "Accessibility"})
	public void sampleAcopChecks() {
		final String url = "http://www.intuit.com";
		driver.get(url);
		ACopChecksV1 sbc = new ACopChecksV1(driver);
		List<String> getAccessibilityErrors = sbc.runAcopChecks();
		Assert.assertTrue(getAccessibilityErrors.isEmpty(), " Accessibility errors found for webpage - " +url);
	}

Accessibility Checkpoints
=========================

### Standard Web Programming
`<!DOCTYPE Resource SYSTEM 'foo.dtd'>`
*   Doctype should be specified if frame or iframe elements exist on the page

### Appropriate Markup
*   HTML visual formatting elements like `<b></b>`, `<i></i>`, `<center></center>`, `<font></font>`, `<u></u>` should not be used. Use CSS for formatting instead

### Title
`<title></title>`  
*   Page title element should not be empty or missing
*   There should not be more than one page title

`<frameset><frame title=""...`  
*   Frame elements should have title attributes  
*   Frame elements should not have title attributes empty  

`<iframe title=""...`
*   iFrame elements should have title attributes  
*   iFrame elements should not have title attributes empty  

### Headings
`<body><p><h1>Heading 1</h1><h2>Heading 2</h2><p><h3>Heading 3</h3></p></body>`
*   Page must contain atleast one h1 element
*   The h1 element should have non empty text
*   Heading elements that follow the h1 element should be properly nested
*   All subheadings(h2..h6) should have non empty text

### HTML lang
`<html lang='en'></html>`
*   You should declare the primary language of a page with the html lang attribute

### Hyperlinks
`<body><a href="www.google.com">Go to Google</a></body>`
*   Hyperlinks should always have text associated with them
*   There should not be duplicate text for hyperlinks on the same page

### Images  
`<input type='image'...`  
*   Image inputs elements should have alt attributes  
*   Image inputs elements should not have alt attributes empty

`<img...`  
*   Image elements should have alt attributes  
*   Image elements should not have alt attributes empty  

`<a><img...`  
*   Image elements inside anchor tags should have empty alt attributes  

### Area
`<area shape='rect' coords='0,0,82,126' href='sun.htm' alt='Sun'>`  
*   Area elements should have an alt attribute  
*   Area element alt attribute cannot be empty

### Flashing content
*   The blink and marquee elements must not be used. Blinking and moving text are an accessibility problems for people with photosenstive epilepsy and visual impairments.

### Forms
`<form><textarea id='area' rows='3' cols='3'></textarea><label for='area'/></form>`
*   Every form element should have a corresponding label (the label 'for' attribute should match the form field 'id' attribute)

`<form><input id='in' type='text' value="input_value"/></form>`
*   Form input elements of type submit|reset|button should not have labels, instead have a non empty 'value' attribute

`<label for="label1">Label 1</label><label for="label2">Label 2</label>`
*   Labels for form controls should have non-empty text

`<legend>Legend 1</legend>`
*   Legends specified for fieldsets or otherwise should have non-empty text

`<button type="button">Button 1</button>`
*   Buttons should have non-empty text

### Tables
`<table summary="summary"><th>Table Heading</th><tr><td>Data 1</td></tr></table>`
*   Table should have a table header
*   Table should have a non empty summary attribute
*   Table headers should have a non empty scope attribute specifying whether it is for a row or column


Additional Resources
--------------------
[w3c Web Content Accessibility Guildelines](http://www.w3.org/TR/WCAG10/)


Credits
-------
   * Thanks to [Avinash Padmanabhan](http://eveningsamurai.wordpress.com) for teaming up to build accessibility rules and automated tests to help improve web accessibility compliance. Avinash has also developed a ruby version of accessibility tests and its publicly available at: [Ruby Acop Git Hub page](https://github.com/eveningsamurai/acop)
   * [Ted Drake](https://twitter.com/ted_drake)
   * Niru Anisetti


Bugs
----
Report bugs and requests at [seleniumAccessibility Github page](https://github.com/neurites/seleniumAccessibility)


Copyright
----------
Copyright 2014 by [Anil Suryanarayana](https://github.com/neurites/seleniumAccessibility) under the MIT license (see the [LICENSE file](https://github.com/neurites/seleniumAccessibility/blob/master/LICENSE)).
