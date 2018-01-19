package org.csstoxpath;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.csstoxpath.CssToXPath.cssToXPath;
import static org.testng.Assert.assertEquals;

/**
 * Created by Roman_Iovlev on 1/17/2018.
 */
public class Tests {
    @Test
    public void convertTest() {
        assertEquals(cssToXPath("div"), "//div");
        assertEquals(cssToXPath(".test-class"), "//*[contains(@class,'test-class')]");
        assertEquals(cssToXPath("#test-id"), "//*[@id='test-id']");
        assertEquals(cssToXPath("[type=testtype]"), "//*[@type='testtype']");
        assertEquals(cssToXPath("[data-type=testdata]"), "//*[@data-type='testdata']");
        assertEquals(cssToXPath("[class='test data cl']"), "//*[@class='test data cl']");
        assertEquals(cssToXPath("[data='test-data']"), "//*[@data='test-data']");
        assertEquals(cssToXPath("a[value=testvalue]"), "//a[@value='testvalue']");
        assertEquals(cssToXPath("[class=test][type=submit]"), "//*[@class='test' and @type='submit']");
        assertEquals(cssToXPath("div span"), "//div//span");
        assertEquals(cssToXPath("[class=testclass] button"), "//*[@class='testclass']//button");
        assertEquals(cssToXPath("a"), "//a");
        assertEquals(cssToXPath("a div span button"), "//a//div//span//button");
        assertEquals(cssToXPath("div[type=text][checked=false] span[dt-args='t b p']"), "//div[@type='text' and @checked='false']//span[@dt-args='t b p']");
        assertEquals(cssToXPath(".open [data-toggle]"), "//*[contains(@class,'open')]//*[@data-toggle]");
        assertEquals(cssToXPath(".profile-photo span"), "//*[contains(@class,'profile-photo')]//span");
    }
}
