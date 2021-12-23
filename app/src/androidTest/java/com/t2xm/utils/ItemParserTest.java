package com.t2xm.utils;

import com.t2xm.R;
import com.t2xm.entity.Item;

import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class ItemParserTest {
    @Test
    public void test() throws IOException, IllegalAccessException, XmlPullParserException, InstantiationException, NoSuchFieldException {
        ItemXmlParser.parseItems(ContextUtil.getContext().getResources().getXml(R.xml.do_in_xiamen));
    }

}
