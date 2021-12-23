package com.t2xm.utils;

import android.content.res.XmlResourceParser;

import com.t2xm.entity.Item;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ItemXmlParser {
    public static List<Item> parseItems(XmlResourceParser parser) throws XmlPullParserException, IOException, IllegalAccessException, NoSuchFieldException {
        List<Item> itemList = new ArrayList<>();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            if (parser.getName().equals("Item")) {
                Item item = new Item();
                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String attr = parser.getName();
                    if (attr.equals("images")) {
                        List<String> imageNameList = new ArrayList<>();
                        while (parser.next() != XmlPullParser.END_TAG) {
                            if (parser.getEventType() != XmlPullParser.START_TAG) {
                                continue;
                            }
                            if (parser.next() == XmlPullParser.TEXT) {
                                imageNameList.add(parser.getText());
                                parser.nextTag();
                            }
                        }
                        item.image = JsonUtil.mapObjectToJson(imageNameList);
                    }
                    if (parser.next() == XmlPullParser.TEXT) {
                        Field field = item.getClass().getDeclaredField(attr);
                        field.set(item, FieldCastUtil.castStringToType(parser.getText(), field.getType()));
                        parser.nextTag();
                    }
                }
                itemList.add(item);
            }
        }
        return itemList;
    }
}
