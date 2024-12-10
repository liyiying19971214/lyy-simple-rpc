package com.example.utils;

import cn.hutool.setting.dialect.Props;
import io.netty.util.internal.ResourcesUtil;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author lyy
 * @Type cccc.java
 * @Desc
 * @date 2024/12/8 17:37
 */
public class Configutils {

      public   static <T> T loadConfig(Class<T> clazz,String prefix) throws Exception {

          String[] extensions = {".yml", ".json", ".properties"};
          //根据文件自动识别
          for (String ext : extensions) {
              StringBuffer sb = new StringBuffer("application");
              sb.append(ext);
              String filePath = sb.toString();
              String path = ResourcesUtil.class.getClassLoader().getResource(filePath).getPath();
              File file = new File(path);
              if (file.exists()) {
                  Props props = new Props(filePath);
                  return props.toBean(clazz, prefix);
              }
          }
          throw new Exception("找不到配置文件!");
      }
}
