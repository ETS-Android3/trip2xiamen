package com.t2xm.utils;

import com.t2xm.R;

import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class ItemParserTest {
    @Test
    public void test() throws IOException, IllegalAccessException, XmlPullParserException, InstantiationException, NoSuchFieldException {
        ItemXmlParser.parseItems(ContextUtil.getContext().getResources().getXml(R.xml.items));
    }

}
