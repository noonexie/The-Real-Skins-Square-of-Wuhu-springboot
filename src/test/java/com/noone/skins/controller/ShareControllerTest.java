package com.noone.skins.controller;

import com.noone.skins.entity.Share;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author xad
 * @create 2022-03-29 22:28
 */
public class ShareControllerTest {

    @Test
    public void changeLikes() {
        Share share = new Share();
        share.setId(26);
        share.setDataName("美国往事");
        share.setDataType("video");
        share.setDataUrl("http://www.rzhdjz.com.cn/play/j4nbqx-1-1.html");
        share.setDataText("浮生若梦");
        share.setLikes(6);
        ShareController controller = new ShareController();
        controller.changeLikes(share);
    }
}